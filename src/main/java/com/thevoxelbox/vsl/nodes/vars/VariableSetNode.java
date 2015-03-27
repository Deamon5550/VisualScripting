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

import com.thevoxelbox.vsl.annotation.Input;
import com.thevoxelbox.vsl.annotation.NodeInfo;
import com.thevoxelbox.vsl.api.runtime.GraphRuntime;
import com.thevoxelbox.vsl.node.AbstractNode;
import com.thevoxelbox.vsl.util.Provider;

/**
 * Sets a variable within the runtime variables.
 * 
 * @param <T> The value type
 */
@NodeInfo(name = "VariableSet")
public class VariableSetNode<T> extends AbstractNode
{

    @Input
    private final Provider<String> varName;
    @Input
    private final Provider<T> value;

    /**
     * Creates a new {@link VariableSetNode}.
     * 
     * @param name The variable name
     * @param value The new value
     */
    public VariableSetNode(Provider<String> name, Provider<T> value)
    {
        this.varName = name;
        this.value = value;
    }

    /**
     * Creates a new {@link VariableSetNode}.
     * 
     * @param name The variable name
     * @param value The new value
     */
    public VariableSetNode(String name, Provider<T> value)
    {
        this.varName = new Provider<String>(this, name);
        this.value = value;
    }

    /**
     * Creates a new {@link VariableSetNode}.
     * 
     * @param name The variable name
     * @param value The new value
     */
    public VariableSetNode(Provider<String> name, T value)
    {
        this.varName = name;
        this.value = new Provider<T>(this, value);
    }

    /**
     * Creates a new {@link VariableSetNode}.
     * 
     * @param name The variable name
     * @param value The new value
     */
    public VariableSetNode(String name, T value)
    {
        this.varName = new Provider<String>(this, name);
        this.value = new Provider<T>(this, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exec(GraphRuntime state)
    {
        state.getVars().set(this.varName.get(state), this.value.get(state));
    }

}
