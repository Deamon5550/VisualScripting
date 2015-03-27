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
package com.thevoxelbox.vsl.runtime;

import java.util.Map;
import java.util.UUID;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.thevoxelbox.vsl.api.node.NodeGraph;
import com.thevoxelbox.vsl.api.runtime.GraphRuntime;
import com.thevoxelbox.vsl.api.variables.VariableHolder;
import com.thevoxelbox.vsl.variables.SimpleVariableHolder;

/**
 * A {@link GraphRuntime} which runs the nodes in place using variable storage
 * within the runtime.
 */
public class ObjectRuntime implements GraphRuntime
{

    private final VariableHolder vars;
    private final Map<UUID, Object> values;

    /**
     * Creates a new {@link ObjectRuntime}.
     */
    public ObjectRuntime()
    {
        this(new SimpleVariableHolder());
    }

    /**
     * Creates a new {@link ObjectRuntime}.
     * 
     * @param vars The initial runtime variables
     */
    public ObjectRuntime(VariableHolder vars)
    {
        this.vars = vars;
        this.values = Maps.newHashMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(NodeGraph... graphs)
    {
        for (NodeGraph graph : graphs)
        {
            graph.run(this);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(UUID uuid)
    {
        return Optional.fromNullable((T) this.values.get(uuid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void set(UUID uuid, T value)
    {
        this.values.put(uuid, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VariableHolder getVars()
    {
        return this.vars;
    }

}
