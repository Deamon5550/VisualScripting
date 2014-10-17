package com.thevoxelbox.vsl.api;

import com.thevoxelbox.vsl.classloader.ASMClassLoader;
import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.node.ExecutableNode;

public interface INodeGraph
{
    void setStartNode(ExecutableNode start);
    
    Class<?> compile(ASMClassLoader cl) throws NullPointerException, GraphCompilationException;
    
    void run(IVariableHolder vars) throws InstantiationException, IllegalAccessException;
    
    String getName();
}
