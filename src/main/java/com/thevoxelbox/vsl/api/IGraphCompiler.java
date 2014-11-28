package com.thevoxelbox.vsl.api;

import com.thevoxelbox.vsl.classloader.ASMClassLoader;
import com.thevoxelbox.vsl.error.GraphCompilationException;

/**
 * A compiler for compiling {@link INodeGraph}s to java bytecode.
 */
public interface IGraphCompiler
{

    /**
     * Compiles the given graph to bytecode and then loads it with the given classloader.
     * 
     * @param cl the classloader to use to load the newly created class, cannot be null
     * @param graph the graph to compile, cannot be null
     * @return the newly created class
     * @throws NullPointerException if the starting point of the given graph is null
     * @throws GraphCompilationException if there is an error while compiling the graph
     */
    Class<?> compile(ASMClassLoader cl, INodeGraph graph) throws NullPointerException, GraphCompilationException;

}
