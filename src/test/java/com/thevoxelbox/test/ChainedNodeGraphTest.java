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

import com.thevoxelbox.vsl.node.RunnableNodeGraph;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;
import com.thevoxelbox.vsl.nodes.vars.ChainedInputNode;
import com.thevoxelbox.vsl.nodes.vars.ChainedOutputNode;

/**
 * A test for two node graphs chained together. TODO cleanup
 */
public class ChainedNodeGraphTest extends StandardTest
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
    public void test()
    {
        this.output.setup();

        StaticValueNode<String> string = new StaticValueNode<String>("Hello");
        StaticValueNode<String> string2 = new StaticValueNode<String>(" World");
        ChainedOutputNode<String> out1 = new ChainedOutputNode<String>("first", string.getValue());
        ChainedOutputNode<String> out2 = new ChainedOutputNode<String>("second", string2.getValue());

        RunnableNodeGraph graph1 = new RunnableNodeGraph("Chained Graph 1");
        graph1.setNext(out1);
        out1.setNext(out2);

        ChainedInputNode<String> in1 = new ChainedInputNode<String>("first");
        ChainedInputNode<String> in2 = new ChainedInputNode<String>("second");
        PrintNode print = new PrintNode(in1.getValue());
        PrintNode print2 = new PrintNode(in2.getValue());
        print.setNext(print2);

        RunnableNodeGraph graph2 = new RunnableNodeGraph("Chained Graph 2");
        graph2.setNext(print);
        graph1.chain(graph2);

        graph1.run(this.vars);

        assertEquals(graph2, graph1.getNextGraph());

        this.output.check("Hello World");
        this.output.reset();
    }

}
