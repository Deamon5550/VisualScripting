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
package com.thevoxelbox.vsl.node;

import com.thevoxelbox.vsl.api.INode;
import com.thevoxelbox.vsl.api.IVariableHolder;
import com.thevoxelbox.vsl.util.RuntimeState;

public class NodeGraph extends Node
{
    String name;
    NodeGraph next = null;
    
    public NodeGraph(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void run(IVariableHolder vars)
    {
        RuntimeState state = new RuntimeState(vars);
        exec(state);
    }

    @Override
    public void exec(RuntimeState state)
    {
        INode next = this.getNext();
        while(next != null)
        {
            next.exec(state);
            next = next.getNext();
        }
        if(this.next != null)
        {
            this.next.exec(state);
        }
    }

    public void chain(NodeGraph next)
    {
        this.next = next;
    }

}