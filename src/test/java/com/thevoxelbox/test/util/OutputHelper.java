/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 The VoxelBox
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
 */
package com.thevoxelbox.test.util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * A testing utility for capturing output passed to standard out.
 */
public class OutputHelper
{

    private ByteArrayOutputStream newOut;
    private PrintStream oldOut;

    /**
     * Sets up a new capture of standard out.
     */
    public void setup()
    {
        this.newOut = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(this.newOut);
        this.oldOut = System.out;
        System.setOut(out);
    }

    /**
     * Checks of the output to standard out matches the given string. Newlines are omitted.
     * 
     * @param s The string to check
     */
    public void check(String s)
    {
        String output = new String(this.newOut.toByteArray());
        output = output.replace("\n", "");
        output = output.replace("\r", "");
        assertEquals(output, s);
    }

    /**
     * Resets standard out to the original {@link PrintStream}.
     */
    public void reset()
    {
        System.setOut(this.oldOut);
    }
}
