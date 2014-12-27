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
import com.thevoxelbox.vsl.api.IVariableHolder;
import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.util.Provider;

public class ForLoop extends Node
{

    private INode body;
    private final Provider<Integer> init;
    private final Provider<Integer> target;
    private final Provider<Integer> increment;
    
    public ForLoop(Provider<Integer> i, Provider<Integer> t, Provider<Integer> n)
    {
        this.init = i;
        this.target = t;
        this.increment = n;
    }
    public ForLoop(int i, Provider<Integer> t, Provider<Integer> n)
    {
        this.init = new Provider<Integer>(i);
        this.target = t;
        this.increment = n;
    }
    public ForLoop(Provider<Integer> i, int t, Provider<Integer> n)
    {
        this.init = i;
        this.target = new Provider<Integer>(t);
        this.increment = n;
    }

    public ForLoop(int i, int t, Provider<Integer> n)
    {
        this.init = new Provider<Integer>(i);
        this.target = new Provider<Integer>(t);
        this.increment = n;
    }

    public ForLoop(Provider<Integer> i, Provider<Integer> t, int n)
    {
        this.init = i;
        this.target = t;
        this.increment = new Provider<Integer>(n);
    }

    public ForLoop(int i, int t, int n)
    {
        this.init = new Provider<Integer>(i);
        this.target = new Provider<Integer>(t);
        this.increment = new Provider<Integer>(n);
    }

    public ForLoop(Provider<Integer> i, int t, int n)
    {
        this.init = i;
        this.target = new Provider<Integer>(t);
        this.increment = new Provider<Integer>(n);
    }

    public ForLoop(int i, Provider<Integer> t, int n)
    {
        this.init = new Provider<Integer>(i);
        this.target = t;
        this.increment = new Provider<Integer>(n);
    }


    @Override
    public void exec(IVariableHolder vars)
    {
        int i = init.get(vars);
        int t = target.get(vars);
        int n = increment.get(vars);
        for(; i < t; i += n)
        {
            body.exec(vars);
        }
    }
    
    public void setBody(Node n)
    {
        this.body = n;
    }
    
}
