package com.thevoxelbox.vsl.api;

import com.thevoxelbox.vsl.classloader.ASMClassLoader;
import com.thevoxelbox.vsl.error.GraphCompilationException;

public interface IGraphCompiler
{

    Class<?> compile(ASMClassLoader cl, INodeGraph graph) throws NullPointerException, GraphCompilationException;
    
}
