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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.api.runtime.GraphRuntime;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;
import com.thevoxelbox.vsl.runtime.ObjectRuntime;
import com.thevoxelbox.vsl.util.Provider;

/**
 * A test for program debug nodes
 */
public class DebugNodeTest extends StandardTest
{
    
    private GraphRuntime state;

    /**
     * 
     */
    @Before
    public void setup()
    {
        super.setup();
        this.state = new ObjectRuntime();
    }

    /**
     * 
     */
    @Test
    public void testStaticValueObject()
    {

        String s = "hello";

        StaticValueNode<String> stat = new StaticValueNode<String>(s);

        stat.exec(this.state);

        assertEquals(s, stat.getValue().get(this.state));

    }

    /**
     * 
     */
    @Test
    public void testStaticValueBoxedPrimative()
    {

        int i = 5;

        StaticValueNode<Integer> stat = new StaticValueNode<Integer>(i);

        stat.exec(this.state);

        assertEquals((Integer) i, stat.getValue().get(this.state));

    }

    /**
     * 
     */
    @Test
    public void testPrint()
    {

        this.output.setup();
        Node node = Mockito.mock(Node.class);
        Provider<String> s = new Provider<String>(node, "hello");

        PrintNode print = new PrintNode(s);

        print.exec(this.state);

        this.output.check("hello");

        this.output.reset();

    }

}
