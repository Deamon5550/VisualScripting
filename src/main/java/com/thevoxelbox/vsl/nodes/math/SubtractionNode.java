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
package com.thevoxelbox.vsl.nodes.math;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.util.Input;
import com.thevoxelbox.vsl.util.Output;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.RuntimeState;

public class SubtractionNode extends Node
{
    @Output
    private final Provider<Number> value;
    @Input
    private final Provider<Number> a;
    @Input
    private final Provider<Number> b;
    private final boolean floating;

    public SubtractionNode(Provider<Number> a, Provider<Number> b, boolean floating)
    {
        this.a = a;
        this.b = b;
        this.value = new Provider<Number>(this);
        this.floating = floating;
    }

    @Override
    public void exec(RuntimeState state)
    {
        if (floating)
        {
            this.value.set(a.get(state).doubleValue() - b.get(state).doubleValue(), state.getUUID());
        } else
        {
            this.value.set(a.get(state).longValue() - b.get(state).longValue(), state.getUUID());
        }
    }

    public Provider<Number> getResult()
    {
        return this.value;
    }

}
