package com.thevoxelbox.vsl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
    public Object get(String name)
    {
        if (name == null || name.isEmpty())
        {
            return null;
        }
        if (this.vars.containsKey(name))
        {
            return this.vars.get(name);
        } else if (this.parent != null)
        {
            return this.parent.get(name);
        }
        return null;
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
    public IVariableScope getHighestParent()
    {
        if (this.parent == null)
        {
            return null;
        }
        IVariableScope next = this.parent;
        while (next.getParent() != null)
        {
            next = next.getParent();
        }
        return next;
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

}
