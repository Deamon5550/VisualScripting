package com.thevoxelbox.vsl.api;

public interface ICachedRunnableFactory extends IRunnableFactory
{
    void registerGraph(String name, INodeGraph program);
    
    void registerGraph(String name, Class<? extends IRunnableGraph> cls);
    
    IRunnableGraph get(String name);
    
    void clearCache();
}
