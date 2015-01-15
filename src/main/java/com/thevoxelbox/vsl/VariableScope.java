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
package com.thevoxelbox.vsl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.thevoxelbox.vsl.api.IVariableHolder;
import com.thevoxelbox.vsl.api.IVariableScope;

/**
 * A recursive variable holder.
 */
public class VariableScope implements IVariableScope, Serializable
{

    private static final long serialVersionUID = 9032180603411264823L;
    /**
     * The parent variable holder.
     */
    private transient IVariableHolder parent = null;
    /**
     * The variable map.
     */
    private final Map<String, Object> vars = new HashMap<String, Object>();
    private boolean caseSensitive = true;

    /**
     * Creates a new VariableScope.
     */
    public VariableScope()
    {

    }

    /**
     * Creates a new VariableScope with the given Scope as its parent.
     * 
     * @param parent the parent
     */
    public VariableScope(IVariableHolder parent)
    {
        this.parent = parent;
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
        } else if (this.parent != null)
        {
            return this.parent.get(name);
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
    public void setParent(IVariableHolder scope)
    {
        this.parent = scope;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IVariableHolder> getParent()
    {
        return Optional.fromNullable(this.parent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IVariableHolder> getHighestParent()
    {
        if (this.parent == null)
        {
            Optional.absent();
        }
        IVariableHolder next = this.parent;
        if (next instanceof IVariableScope)
        {
            IVariableScope nextScope = (IVariableScope) next;
            while (nextScope.getParent().isPresent())
            {
                next = nextScope.getParent().get();
                if (next instanceof IVariableScope)
                {
                    nextScope = (IVariableScope) next;
                } else
                {
                    break;
                }
            }
        }
        return Optional.fromNullable(next);
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
        if (this.vars.containsKey(name))
        {
            return true;
        }
        if (this.parent != null)
        {
            return this.parent.hasValue(name);
        }
        return false;
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
        } else if (this.parent != null)
        {
            return this.parent.get(name, type);
        }
        return Optional.absent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> keyset()
    {
        Set<String> keyset = Sets.newHashSet();
        keyset.addAll(this.vars.keySet());
        if (this.parent != null)
        {
            keyset.addAll(this.parent.keyset());
        }
        return keyset;
    }

}
