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
import com.thevoxelbox.vsl.nodes.control.IfStatement;
import com.thevoxelbox.vsl.nodes.debug.PrintNode;

public class IfStatementTest extends StandardTest
{

    @Test
    public void isTrue() throws Exception
    {
        output.setup();

        StaticValueNode<String> string = new StaticValueNode<String>("Hello");
        StaticValueNode<Boolean> statement = new StaticValueNode<Boolean>(true);
        StaticValueNode<String> string2 = new StaticValueNode<String>(" World");
        PrintNode print = new PrintNode(string.getValue());
        IfStatement ifs = new IfStatement(statement.getValue());
        PrintNode print2 = new PrintNode(string2.getValue());
        print.setNext(ifs);
        ifs.setBody(print2);

        NodeGraph graph = new NodeGraph("Test Graph");
        graph.setNext(print);
        graph.run(vars);

        output.check("Hello World");
        output.reset();
    }
    
    @Test
    public void isFalse() throws Exception
    {
        output.setup();

        StaticValueNode<String> string = new StaticValueNode<String>("Hello World");
        StaticValueNode<Boolean> statement = new StaticValueNode<Boolean>(false);
        StaticValueNode<String> string2 = new StaticValueNode<String>(" World");
        PrintNode print = new PrintNode(string.getValue());
        IfStatement ifs = new IfStatement(statement.getValue());
        PrintNode print2 = new PrintNode(string2.getValue());
        print.setNext(ifs);
        ifs.setBody(print2);

        NodeGraph graph = new NodeGraph("Test Graph");
        graph.setNext(print);
        graph.run(vars);

        output.check("Hello World");
        output.reset();
    }
}
