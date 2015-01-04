/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 The Voxel Plugineering Team
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

import org.junit.Test;

import com.thevoxelbox.vsl.node.NodeGraph;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;
import com.thevoxelbox.vsl.nodes.math.AdditionNode;
import com.thevoxelbox.vsl.nodes.math.compare.NumberEqualsNode;

public class NumberNodeTest extends StandardTest
{


    @Test
    public void testAddition()
    {
        output.setup();

        StaticValueNode<Integer> a = new StaticValueNode<Integer>(5);
        StaticValueNode<Integer> b = new StaticValueNode<Integer>(8);
        AdditionNode add = new AdditionNode(a.getValue(), b.getValue(), false);
        PrintNode print = new PrintNode(add.getResult());

        NodeGraph graph = new NodeGraph("Test Graph");
        graph.setNext(print);
        graph.run(vars);

        output.check("13");
        output.reset();
    }
    
    @Test
    public void testEquals()
    {
        output.setup();

        StaticValueNode<Integer> a = new StaticValueNode<Integer>(5);
        StaticValueNode<Integer> b = new StaticValueNode<Integer>(5);
        NumberEqualsNode add = new NumberEqualsNode(a.getValue(), b.getValue(), false);
        PrintNode print = new PrintNode(add.getComparisonResult());

        NodeGraph graph = new NodeGraph("Test Graph");
        graph.setNext(print);
        graph.run(vars);

        output.check("true");
        output.reset();
    }
    
}