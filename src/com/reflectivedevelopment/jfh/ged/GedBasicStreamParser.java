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

import com.reflectivedevelopment.jfh.ged.objects.GedObject;

public class GedBasicStreamParser {

	protected InputStream mDataStream = null;
	protected LineNumberReader mLineReader = null;
	private String[] mLine = null;
	
	public GedBasicStreamParser(InputStream dataStream) throws IOException
	{
		super();
		mDataStream = dataStream;
		InputStreamReader tmpReader = new InputStreamReader(mDataStream, "UTF-8");
		mLineReader = new LineNumberReader(tmpReader);
		nextLine();
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

	private void parseNextBlock() throws IOException
	{
		int depth = 0;
		do {
			String[] tmpLine = getLine();
			if (tmpLine == null)
				return;
			depth = new Integer(getLine()[0]);
			
			System.out.println(depth + ": " + tmpLine[1]);
			
			nextLine();
		} while (depth > -1);
	}
	
	public GedObject getNextBlock() throws IOException
	{
		parseNextBlock();
		return null;
	}
	
	public void closeStream() throws IOException
	{
		mDataStream.close();
	}
	
}
