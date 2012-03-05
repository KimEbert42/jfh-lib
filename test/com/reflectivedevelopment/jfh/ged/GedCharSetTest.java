/*
 * Copyright (C) 2012 by Kim Ebert
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

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.reflectivedevelopment.jfh.ged.objects.GedObject;

public class GedCharSetTest {

	private String macHead = 
			"0 HEAD\n" +
			"1 CHAR MACINTOSH\n";

	private String utfHead = 
			"0 HEAD\n" +
			"1 CHAR UTF-8\n";

	private String ansiHead = 
			"0 HEAD\n" +
			"1 CHAR ANSI\n";
	
	private String asciiHead = 
			"0 HEAD\n" +
			"1 CHAR ASCII\n";

	private String anselHead = 
			"0 HEAD\n" +
			"1 CHAR ANSEL\n";
	
	
	
	private void runTest(InputStream input) throws IOException
	{
		GedBasicStreamParser parser = new GedBasicStreamParser(input);
		
		try {
			while(parser.getNextBlock() != null)
				;
			
			parser.closeStream();
		} catch (IOException e) {
			fail(e.toString());
		}		
	}
	
	@Test
	public void testMac() throws IOException
	{
		runTest(new ByteArrayInputStream(macHead.getBytes()));
	}
	
	@Test
	public void testUtf() throws IOException
	{
		runTest(new ByteArrayInputStream(utfHead.getBytes()));
	}
	
	@Test
	public void testAnsi() throws IOException
	{
		runTest(new ByteArrayInputStream(ansiHead.getBytes()));
	}

	@Test
	public void testAscii() throws IOException
	{
		runTest(new ByteArrayInputStream(asciiHead.getBytes()));
	}

/*
 * TODO: Fix unit test
 */
//	@Test
	public void testAnsel() throws IOException
	{
		runTest(new ByteArrayInputStream(anselHead.getBytes()));
	}
	
}
