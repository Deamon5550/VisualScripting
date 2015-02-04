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
package com.thevoxelbox.vsl.nodes;

import com.thevoxelbox.vsl.annotation.NodeInfo;
import com.thevoxelbox.vsl.node.AbstractNode;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.RuntimeState;

/**
 * A node for inserting a static value to a program.
 * 
 * @param <T> The value type
 */
@NodeInfo(name = "StaticValue", outputs = {"value"}, inputs = {"input"})
public class StaticValueNode<T> extends AbstractNode
{

    private final Provider<T> value;
    private final Provider<T> input;

    /**
     * Creates a new {@link StaticValueNode}.
     * 
     * @param value The value
     */
    public StaticValueNode(T value)
    {
        this.input = new Provider<T>(this, value);
        this.value = new Provider<T>(this);
    }

    /**
     * Creates a new {@link StaticValueNode}.
     * 
     * @param value The value
     */
    public StaticValueNode(Provider<T> value)
    {
        this.input = value;
        this.value = new Provider<T>(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exec(RuntimeState state)
    {
        this.value.set(this.input.get(state), state.getUUID());
    }

    /**
     * Gets the value.
     * 
     * @return The value
     */
    public Provider<T> getValue()
    {
        return this.value;
    }

}
