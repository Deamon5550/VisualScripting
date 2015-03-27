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
package com.thevoxelbox.vsl.variables;

import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.thevoxelbox.vsl.api.variables.VariableHolder;

/**
 * A basic implementation of {@link VariableHolder}.
 */
public class SimpleVariableHolder implements VariableHolder
{

    private final Map<String, Object> vars;
    private boolean caseSensitive = true;

    /**
     * Creates a new {@link SimpleVariableHolder}.
     */
    public SimpleVariableHolder()
    {
        this.vars = Maps.newConcurrentMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCaseSensitive(boolean cs)
    {
        this.caseSensitive = cs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Object> get(String name)
    {
        if (name == null || name.isEmpty())
        {
            return Optional.absent();
        }
        if (!this.caseSensitive)
        {
            name = name.toLowerCase();
        }
        if (this.vars.containsKey(name))
        {
            return Optional.fromNullable(this.vars.get(name));
        }
        return Optional.absent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(String name, Object value)
    {
        if (!this.caseSensitive)
        {
            name = name.toLowerCase();
        }
        this.vars.put(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasValue(String name)
    {
        if (!this.caseSensitive)
        {
            name = name.toLowerCase();
        }
        return this.vars.containsKey(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(String name, Class<T> type)
    {
        if (type == null)
        {
            return (Optional<T>) get(name);
        }
        if (name == null || name.isEmpty())
        {
            return Optional.absent();
        }
        if (!this.caseSensitive)
        {
            name = name.toLowerCase();
        }
        if (this.vars.containsKey(name))
        {
            return Optional.fromNullable((T) this.vars.get(name));
        }
        return Optional.absent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> keyset()
    {
        return this.vars.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear()
    {
        this.vars.clear();
    }

}
