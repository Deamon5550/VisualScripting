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
import com.thevoxelbox.vsl.node.AbstractNode;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.RuntimeState;

/**
 * Loops over every value in the given array and calls another {@link Node} as
 * the body.
 * 
 * @param <T> The array type
 */
@NodeInfo(name = "ForEach", inputs = {"array"}, outputs = {"next", "index"})
public class ForEachNode<T> extends AbstractNode
{

    private Node body;
    
    private final Provider<T[]> array;
    private final Provider<T> next;
    private final Provider<Integer> index;

    /**
     * Creates a new {@link ForEachNode}.
     * 
     * @param array The array to loop over
     */
    public ForEachNode(Provider<T[]> array, Class<T> type)
    {
        this.array = array;
        this.index = new Provider<Integer>(this, Integer.class);
        this.next = new Provider<T>(this, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exec(RuntimeState state)
    {
        int i = 0;
        T[] arr = this.array.get(state);
        for (T obj : arr)
        {
            this.next.set(obj, state.getUUID());
            this.index.set(i, state.getUUID());
            Node next = this.body;
            while (next != null)
            {
                next.exec(state);
                next = next.getNext();
            }
            i++;
        }
    }

    /**
     * Gets the last array value fetched from the array.
     * 
     * @return The value
     */
    public Provider<T> getNextValue()
    {
        return this.next;
    }

    /**
     * Sets the {@link Node} to use as the body of this loop.
     * 
     * @param body The body
     */
    public void setBody(AbstractNode body)
    {
        this.body = body;
    }

}
