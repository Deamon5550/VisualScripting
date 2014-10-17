package com.thevoxelbox.vsl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.thevoxelbox.vsl.api.IVariableScope;

public class VariableScope implements IVariableScope, Serializable
{
    private static final long serialVersionUID = 9032180603411264823L;
    
    private transient IVariableScope parent = null;
    private final Map<String, Object> vars = new HashMap<String, Object>();
    
    public VariableScope()
    {
        
    }
    
    public VariableScope(IVariableScope parent)
    {
        this.parent = parent;
    }

    @Override
    public Object get(String name)
    {
        if(this.vars.containsKey(name))
        {
            return this.vars.get(name);
        }
        else if(this.parent != null)
        {
            return this.parent.get(name);
        }
        return null;
    }

    @Override
    public void set(String name, Object value)
    {
        this.vars.put(name, value);
    }

    @Override
    public void setParent(IVariableScope scope)
    {
        this.parent = scope;
    }

    @Override
    public IVariableScope getParent()
    {
        return this.parent;
    }

    @Override
    public IVariableScope getHighestParent()
    {
        IVariableScope next = this.parent;
        while(next.getParent() != null)
        {
            next = next.getParent();
        }
        return next;
    }

}
