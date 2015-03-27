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

import com.thevoxelbox.vsl.annotation.NodeInfo;
import com.thevoxelbox.vsl.api.runtime.GraphRuntime;
import com.thevoxelbox.vsl.util.Provider;

/**
 * Returns the sub of two integer or floating-point numbers.
 */
@NodeInfo(name = "Addition")
public class AdditionNode extends NumberOperatorNode
{

    /**
     * Creates a new {@link AdditionNode}.
     * 
     * @param a The first input
     * @param b The second input
     * @param floating Whether to use floating point percision
     */
    public AdditionNode(Provider<? extends Number> a, Provider<? extends Number> b, boolean floating)
    {
        super(a, b, floating);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exec(GraphRuntime state)
    {
        if (this.floating)
        {
            this.value.set(this.a.get(state).doubleValue() + this.b.get(state).doubleValue(), state);
        } else
        {
            this.value.set(this.a.get(state).longValue() + this.b.get(state).longValue(), state);
        }
    }

}
