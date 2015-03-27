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
package com.thevoxelbox.vsl.nodes.math;

import com.thevoxelbox.vsl.annotation.Input;
import com.thevoxelbox.vsl.annotation.NodeInfo;
import com.thevoxelbox.vsl.annotation.Output;
import com.thevoxelbox.vsl.api.runtime.GraphRuntime;
import com.thevoxelbox.vsl.node.AbstractNode;
import com.thevoxelbox.vsl.util.Provider;

/**
 * Performs the {@link Math#sqrt(double)} on the given number.
 */
@NodeInfo(name = "Sqrt")
public class SqrtNode extends AbstractNode
{

    @Output
    private final Provider<Number> value;
    @Input
    private final Provider<? extends Number> a;

    /**
     * Creates a new {@link SqrtNode}.
     * 
     * @param a The number to square root
     */
    public SqrtNode(Provider<? extends Number> a)
    {
        this.a = a;
        this.value = new Provider<Number>(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exec(GraphRuntime state)
    {
        this.value.set(Math.sqrt(this.a.get(state).doubleValue()), state);
    }

    /**
     * Gets the resultant value.
     * 
     * @return The result
     */
    public Provider<Number> getResult()
    {
        return this.value;
    }

}
