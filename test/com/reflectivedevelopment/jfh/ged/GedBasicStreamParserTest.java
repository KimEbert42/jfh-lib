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

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import com.reflectivedevelopment.jfh.ged.objects.GedObject;

public class GedBasicStreamParserTest {

	private String basicHead = 
		"0 HEAD\n" +
		"1 SOUR Reunion\n" +
		"2 VERS V8.0\n" +
		"2 CORP Leister Productions\n" +
		"1 DEST Reunion\n" +
		"1 DATE 11 FEB 2006\n" +
		"1 FILE test\n" +
		"1 GEDC\n" +
		"2 VERS 5.5\n" +
		"1 CHAR MACINTOSH\n";

	@Test
	public void testConstruction() throws IOException {
		
		ByteArrayInputStream byteInput = new ByteArrayInputStream(basicHead.getBytes());
		
		GedBasicStreamParser parser = new GedBasicStreamParser(byteInput);
		
		try {
			parser.closeStream();
		} catch (IOException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testHead() throws IOException {
		
		ByteArrayInputStream byteInput = new ByteArrayInputStream(basicHead.getBytes());
		
		GedBasicStreamParser parser = new GedBasicStreamParser(byteInput);
		
		try {
			System.out.println(parser.getNextBlock());
			
			parser.closeStream();
		} catch (IOException e) {
			fail(e.toString());
		}
	}

	private String simpleGed =
			"0 HEAD\n" +
			"1 SOUR PAF\n" +
			"2 NAME Personal Ancestral File\n" +
			"2 VERS 5.2.18.0\n" +
			"2 CORP The Church of Jesus Christ of Latter-day Saints\n" +
			"3 ADDR 50 East North Temple Street\n" +
			"4 CONT Salt Lake City, UT 84150\n" +
			"4 CONT USA\n" +
			"1 DEST Other\n" +
			"1 DATE 4 Mar 2012\n" +
			"2 TIME 14:13:01\n" +
			"1 FILE test.ged\n" +
			"1 GEDC\n" +
			"2 VERS 5.5\n" +
			"2 FORM LINEAGE-LINKED\n" +
			"1 CHAR UTF-8\n" +
			"1 LANG English\n" +
			"1 SUBM @SUB1@\n" +
			"0 @SUB1@ SUBM\n" +
			"1 NAME test test\n" +
			"1 ADDR test\n" +
			"2 CONT test test test\n" +
			"2 CTRY test\n" +
			"1 EMAIL test@example.com\n" +
			"0 @I1@ INDI\n" +
			"1 NAME John /Doe/\n" +
			"2 SURN Doe\n" +
			"2 GIVN John\n" +
			"1 SEX M\n" +
			"1 BIRT\n" +
			"2 DATE 1 Jan 2000\n" +
			"1 _UID 23095F8EA6BC944491836CAD02919821CC39\n" +
			"1 FAMC @F1@\n" +
			"1 CHAN\n" +
			"2 DATE 4 Mar 2012\n" +
			"3 TIME 14:11:59\n" +
			"0 @I2@ INDI\n" +
			"1 NAME John /Doe/\n" +
			"2 SURN Doe\n" +
			"2 GIVN John\n" +
			"1 SEX M\n" +
			"1 _UID 3C92B2EA39933C46985671571EB749D6622F\n" +
			"1 FAMS @F1@\n" +
			"1 CHAN\n" +
			"2 DATE 4 Mar 2012\n" +
			"3 TIME 14:12:05\n" +
			"0 @I3@ INDI\n" +
			"1 NAME Jane //\n" +
			"2 GIVN Jane\n" +
			"1 SEX F\n" +
			"1 _UID 2FD1AD1FE4AFF0449F8C94C60ACA327D9BBA\n" +
			"1 FAMS @F1@\n" +
			"1 CHAN\n" +
			"2 DATE 4 Mar 2012\n" +
			"3 TIME 14:12:12\n" +
			"0 @F1@ FAM\n" +
			"1 _UID 3F952DCC7E3A874494E1A826C5BC435CB32C\n" +
			"1 HUSB @I2@\n" +
			"1 WIFE @I3@\n" +
			"1 CHIL @I1@\n" +
			"0 TRLR\n";
			
	@Test
	public void testSimpleGed() throws IOException {
		ByteArrayInputStream byteInput = new ByteArrayInputStream(simpleGed.getBytes());
		
		GedBasicStreamParser parser = new GedBasicStreamParser(byteInput);
		
		try {
			GedObject current = null;
			while((current = parser.getNextBlock()) != null)
				System.out.println(current);
			
			parser.closeStream();
		} catch (IOException e) {
			fail(e.toString());
		}
	}

}
