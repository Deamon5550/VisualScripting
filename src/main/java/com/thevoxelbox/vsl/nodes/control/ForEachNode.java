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
package com.thevoxelbox.vsl.nodes.control;

import com.thevoxelbox.vsl.api.INode;
import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.util.Input;
import com.thevoxelbox.vsl.util.Output;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.RuntimeState;

public class ForEachNode<T> extends Node
{

    private INode body;
    @Input
    private final Provider<T[]> array;
    @Output
    private final Provider<T> next;
    @Output
    private final Provider<Integer> index;

    public ForEachNode(Provider<T[]> array)
    {
        this.array = array;
        this.index = new Provider<Integer>(this);
        this.next = new Provider<T>(this);
    }

    @Override
    public void exec(RuntimeState state)
    {
        int i = 0;
        T[] arr = array.get(state);
        for (T obj : arr)
        {
            this.next.set(obj, state.getUUID());
            this.index.set(i, state.getUUID());
            INode next = this.body;
            while (next != null)
            {
                next.exec(state);
                next = next.getNext();
            }
            i++;
        }
    }

    public Provider<T> getNextValue()
    {
        return this.next;
    }

    public void setBody(Node n)
    {
        this.body = n;
    }

}
