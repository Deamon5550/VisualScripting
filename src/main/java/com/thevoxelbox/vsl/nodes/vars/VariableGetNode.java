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
package com.thevoxelbox.vsl.nodes.vars;

import com.thevoxelbox.vsl.node.Node;
import com.thevoxelbox.vsl.util.Input;
import com.thevoxelbox.vsl.util.Output;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.RuntimeState;

/**
 * Gets a variable from the runtime variables with the given name.
 * 
 * @param <T> The value type
 */
public class VariableGetNode<T> extends Node
{
    @Input
    private final Provider<String> varName;
    @Output
    private final Provider<T> value;

    /**
     * Creates a new {@link VariableGetNode}.
     * 
     * @param name The variable name
     */
    public VariableGetNode(Provider<String> name)
    {
        this.varName = name;
        this.value = new Provider<T>(this);
    }

    /**
     * Creates a new {@link VariableGetNode}.
     * 
     * @param name The variable name
     */
    public VariableGetNode(String name)
    {
        this.varName = new Provider<String>(name);
        this.value = new Provider<T>(this);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void exec(RuntimeState state)
    {
        this.value.set((T) state.getVars().get(this.varName.get(state)).get(), state.getUUID());
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
