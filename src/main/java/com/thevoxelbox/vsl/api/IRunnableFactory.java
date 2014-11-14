package com.thevoxelbox.vsl.api;

public interface IRunnableFactory
{

    Class<? extends IRunnableGraph> compile(INodeGraph graph);

    IRunnableGraph get(INodeGraph graph);

    IRunnableGraph get(Class<? extends IRunnableGraph> cls);

}
