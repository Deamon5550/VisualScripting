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

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.thevoxelbox.vsl.api.node.NodeGraph;
import com.thevoxelbox.vsl.error.InvalidNodeException;
import com.thevoxelbox.vsl.node.RunnableNodeGraph;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;
import com.thevoxelbox.vsl.nodes.math.AdditionNode;
import com.thevoxelbox.vsl.serialization.FileGraphWriter;

/**
 * A set of tests for the brush serialization.
 */
public class SerializationTest extends StandardTest
{

    /**
     * 
     */
    @Before
    public void setup()
    {
        super.setup();
    }

    /**
     * 
     */
    @Test
    public void testException()
    {
        InvalidNodeException e = new InvalidNodeException("msg");
        assertEquals("msg", e.getMessage());
    }

    /**
     * @throws InvalidNodeException ignored
     * @throws IOException ignored
     */
    @Test
    public void testSerialization() throws IOException, InvalidNodeException
    {
        this.output.setup();

        FileGraphWriter writer = new FileGraphWriter(System.out);
        NodeGraph graph = new RunnableNodeGraph("TestGraph");
        StaticValueNode<Integer> a = new StaticValueNode<Integer>(5);
        StaticValueNode<Integer> b = new StaticValueNode<Integer>(6);
        AdditionNode add = new AdditionNode(a.getValue(), b.getValue(), false);
        PrintNode print = new PrintNode(add.getResult());
        graph.setStart(add);
        add.setNext(print);
        writer.write(graph);
        writer.close();

        this.output
                .check("#version 1name TestGraphp Addition value a bp StaticValue valuep Print msgi 0 1 1 2e 1:0 0:1e 2:0 0:2e 0:0 3:0e 0:-1 3:-1");
        this.output.reset();
    }
}
