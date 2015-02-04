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
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.RuntimeState;

/**
 * Performs the modulo operator between two given numbers.
 */
@NodeInfo(name = "Modulo", inputs = { "a", "b", "floating" }, outputs = "value")
public class ModuloNode extends NumberOperatorNode
{

    /**
     * Creates a new {@link ModuloNode}.
     * 
     * @param a The first number
     * @param b The second number
     * @param floating Whether to use floating point precision
     */
    public ModuloNode(Provider<? extends Number> a, Provider<? extends Number> b, boolean floating)
    {
        super(a, b, floating);
    }

    /**
     * Creates a new {@link ModuloNode}.
     * 
     * @param a The first number
     * @param b The second number
     * @param floating Whether to use floating point precision
     */
    public ModuloNode(Provider<? extends Number> a, Provider<? extends Number> b, Provider<Boolean> floating)
    {
        super(a, b, floating);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exec(RuntimeState state)
    {
        if (this.floating.get(state))
        {
            this.value.set(this.a.get(state).doubleValue() % this.b.get(state).doubleValue(), state.getUUID());
        } else
        {
            this.value.set(this.a.get(state).longValue() % this.b.get(state).longValue(), state.getUUID());
        }
    }

}
