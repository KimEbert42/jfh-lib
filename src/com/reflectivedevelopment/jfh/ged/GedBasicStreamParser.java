/*
 * Copyright (C) 2011 by Kim Ebert
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */

package com.reflectivedevelopment.jfh.ged;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import com.reflectivedevelopment.io.UnbufferedLineNumberReader;
import com.reflectivedevelopment.jfh.ged.objects.GedObject;
import com.reflectivedevelopment.jfh.ged.objects.head.GedObjectChar;

public class GedBasicStreamParser {

	protected InputStream mDataStream = null;
	protected InputStreamReader mInputStreamReader = null;
	protected LineNumberReader mLineReader = null;
	private String[] mLine = null;
	
	public GedBasicStreamParser(InputStream dataStream) throws IOException
	{
		super();
		mDataStream = dataStream;	
/*
 * TODO: Add ANSEL support.
 * 
 * We currently only support byte types 0-127 until a charset is defined!
 * 
 * We don't have ANSEL support as part of Java, so we need to add it.
 * 
 * We also cannot buffer until we reopen the data stream.
 * 
 * The 8-Bit ANSEL (American National Standard for Extended Latin Alphabet Coded Character Set for
 * Bibliographic Use, Z39.47-1985 copyright) is the preferred character set for GEDCOM. It is used for all
 * transmissions of information unless another character set is specified.
 */
		mLineReader = new UnbufferedLineNumberReader(mDataStream);
		nextLine();
	}
	
	private void reopenDataStream(GedObjectChar charObject) throws IOException
	{
		if (mInputStreamReader != null)
			throw new IOException("Cannot re-open stream more than once!");
		mInputStreamReader = new InputStreamReader(mDataStream, charObject.getEncoding());
		mLineReader = new LineNumberReader(mInputStreamReader);
	}

	private String[] getLine()
	{
		return mLine;
	}
	
	private void nextLine() throws IOException
	{
		String tmpLine = mLineReader.readLine();
		
		if (tmpLine == null)
			mLine = null;
		else
			mLine = tmpLine.split(" ", 3);
	}

	private GedObject parseNextBlock() throws IOException
	{
		GedObject last = null;
		GedObject first = null;
		GedObject current = null;

		int depth = 0;
		do {
			last = current;	
			if (first == null)
				first = last;
			
			String[] tmpLine = getLine();
			if (tmpLine == null)
				return first;
			depth = new Integer(getLine()[0]);
			
			if (last != null)
			{
				while (depth <= last.getDepth())
				{
					last = last.getParent();
					if (last == null)
						break;
				}
				if (last == null)
					break;
			}
			
			if (tmpLine.length == 3)
				current = GedObject.createGedObject(first, last, depth, tmpLine[1], tmpLine[2]);
			else
				current = GedObject.createGedObject(first, last, depth, tmpLine[1], null);
			
			if (current instanceof GedObjectChar)
				reopenDataStream((GedObjectChar)current);
				
			if (last != null)
				last.addChild(current);
			
			nextLine();
		} while (true);
		
		return first;
	}
	
	public GedObject getNextBlock() throws IOException
	{
		return parseNextBlock();
	}
	
	public void closeStream() throws IOException
	{
		mDataStream.close();
	}
	
}
