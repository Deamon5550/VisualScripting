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
package com.thevoxelbox.vsl.nodes.control;

import com.thevoxelbox.vsl.annotation.NodeInfo;
import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.api.runtime.GraphRuntime;
import com.thevoxelbox.vsl.node.AbstractNode;
import com.thevoxelbox.vsl.util.Provider;

/**
 * Executed a node if the given boolean is true. TODO: else support
 */
@NodeInfo(name = "If")
public class IfStatement extends AbstractNode
{

    private final Provider<Boolean> statement;
    private Node body;

    /**
     * Creates a new {@link IfStatement}.
     * 
     * @param statement The boolean provider
     */
    public IfStatement(Provider<Boolean> statement)
    {
        this.statement = statement;
    }

    /**
     * Sets the body of this if statement
     * 
     * @param n The body
     */
    public void setBody(Node n)
    {
        this.body = n;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exec(GraphRuntime state)
    {
        if (this.statement.get(state))
        {
            Node next = this.body;
            while (next != null)
            {
                next.exec(state);
                next = next.getNext();
            }
        }
    }

}
