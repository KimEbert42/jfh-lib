package com.reflectivedevelopment.io;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;

public class UnbufferedLineNumberReader extends LineNumberReader {

	private int lineNumber = 0;
	private boolean skipLf = false;
	private InputStream mInputStream = null;

	public UnbufferedLineNumberReader(InputStream mDataStream) {
		super(new CharArrayReader("".toCharArray()));
		mInputStream = mDataStream;
	}

	@Override
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	@Override
	public int getLineNumber() {
		return lineNumber;
	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public int read() throws IOException {
		return mInputStream.read();
	}

	@Override
	public String readLine() throws IOException {
		String line = "";
		int in = this.read();

		if (in == -1)
			return null;

		do {
			if (((char) in) == '\n') {
				if (skipLf) {
					skipLf = false;
					continue;
				}
				return line;
			}
			if (((char) in) == '\r') {
				skipLf = true;
				return line;
			}
			line += ((char) in);
		} while ((in = this.read())!= -1);
		return line;
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		return -1;
	}

}
