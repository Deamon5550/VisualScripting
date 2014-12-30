package com.thevoxelbox.vsl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;
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
    private transient IVariableScope parent = null;
    /**
     * The variable map.
     */
    private final Map<String, Object> vars = new HashMap<String, Object>();

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
    public VariableScope(IVariableScope parent)
    {
        this.parent = parent;
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
        this.vars.put(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParent(IVariableScope scope)
    {
        this.parent = scope;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IVariableScope getParent()
    {
        return this.parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IVariableScope> getHighestParent()
    {
        if (this.parent == null)
        {
            Optional.absent();
        }
        IVariableScope next = this.parent;
        while (next.getParent() != null)
        {
            next = next.getParent();
        }
        return Optional.of(next);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasValue(String name)
    {
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

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(String name, Class<T> type)
    {
        if (name == null || name.isEmpty())
        {
            return Optional.absent();
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

}
