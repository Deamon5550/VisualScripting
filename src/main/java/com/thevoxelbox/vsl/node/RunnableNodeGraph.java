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
package com.thevoxelbox.vsl.node;

import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.api.node.NodeGraph;
import com.thevoxelbox.vsl.api.variables.VariableHolder;
import com.thevoxelbox.vsl.util.RuntimeState;

/**
 * A node graph is a directed acyclic graph of connected nodes which when traversed will execute the program function. It is itself a node and may be
 * used in another {@link RunnableNodeGraph} recursively.
 */
public class RunnableNodeGraph extends AbstractNode implements NodeGraph
{

    String name;
    RunnableNodeGraph nextgraph = null;

    /**
     * Creates a new {@link RunnableNodeGraph}
     * 
     * @param name The name
     */
    public RunnableNodeGraph(String name)
    {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(VariableHolder vars)
    {
        RuntimeState state = new RuntimeState(vars);
        exec(state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exec(RuntimeState state)
    {
        Node next = this.getStart();
        while (next != null)
        {
            next.exec(state);
            next = next.getNext();
        }
        if (this.nextgraph != null)
        {
            this.nextgraph.exec(state);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chain(RunnableNodeGraph next)
    {
        this.nextgraph = next;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RunnableNodeGraph getNextGraph()
    {
        return this.nextgraph;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getStart()
    {
        return this.getNext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStart(Node node)
    {
        this.setNext(node);
    }

}
