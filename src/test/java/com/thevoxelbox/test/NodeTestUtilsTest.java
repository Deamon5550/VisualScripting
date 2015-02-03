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
package com.thevoxelbox.test;

import static com.thevoxelbox.test.util.MockUtility.mock;

import org.junit.Test;

import com.thevoxelbox.test.util.CheckRunNode;
import com.thevoxelbox.test.util.StringArrayCheckNode;

/**
 * Tests the testing utility nodes.
 */
public class NodeTestUtilsTest extends StandardTest
{

    /**
     * 
     */
    @Test
    public void testStringCheckArrayNode()
    {
        StringArrayCheckNode check = new StringArrayCheckNode(mock("hello"), "hello");

        check.exec(this.state);
        check.end();
    }

    /**
     * 
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testStringCheckArrayNodeTooFew()
    {
        StringArrayCheckNode check = new StringArrayCheckNode(mock("hello"), "hello", "world");

        check.exec(this.state);
        check.end();
    }

    /**
     * 
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testStringCheckArrayNodeTooMany()
    {
        StringArrayCheckNode check = new StringArrayCheckNode(mock("hello"), "hello");

        check.exec(this.state);
        check.exec(this.state);
        check.end();
    }

    /**
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testStringCheckArrayNodeInvalid()
    {
        StringArrayCheckNode check = new StringArrayCheckNode(mock("world"), "hello");

        check.exec(this.state);
        check.end();
    }

    /**
     * Tests the expected behavior of a {@link CheckRunNode}.
     */
    @Test
    public void testCheckRunNode()
    {
        CheckRunNode check = new CheckRunNode(3);
        check.exec(this.state);
        check.exec(this.state);
        check.exec(this.state);
        check.end();
    }

    /**
     * Tests the expected behavior of a {@link CheckRunNode}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testCheckRunNodeTooFew()
    {
        CheckRunNode check = new CheckRunNode(1);
        check.end();
    }

    /**
     * Tests the expected behavior of a {@link CheckRunNode}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testCheckRunNodeTooMany()
    {
        CheckRunNode check = new CheckRunNode(1);
        check.exec(this.state);
        check.exec(this.state);
        check.exec(this.state);
        check.end();
    }
}
