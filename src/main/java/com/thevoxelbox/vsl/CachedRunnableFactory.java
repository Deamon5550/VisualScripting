package com.thevoxelbox.vsl;

import com.thevoxelbox.vsl.api.ICachedRunnableFactory;
import com.thevoxelbox.vsl.api.INodeGraph;
import com.thevoxelbox.vsl.api.IRunnableGraph;

public class CachedRunnableFactory implements ICachedRunnableFactory
{

    public CachedRunnableFactory()
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

    @Override
    public void registerGraph(String name, INodeGraph program)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void registerGraph(String name, Class<? extends IRunnableGraph> cls)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public IRunnableGraph get(String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void clearCache()
    {
        // TODO Auto-generated method stub

    }

}
