package com.thevoxelbox.vsl;

import com.thevoxelbox.vsl.api.INodeGraph;
import com.thevoxelbox.vsl.api.IRunnableFactory;
import com.thevoxelbox.vsl.api.IRunnableGraph;

public class RunnableFactory implements IRunnableFactory
{
    
    public RunnableFactory()
    {
        
    }

    @Override
    public Class<? extends IRunnableGraph> compile(INodeGraph graph)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRunnableGraph get(INodeGraph graph)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRunnableGraph get(Class<? extends IRunnableGraph> cls)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
