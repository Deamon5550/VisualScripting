package com.thevoxelbox.vsl.api;

/**
 * A factory for registering graph compilers against the classes they are designed to compile.
 */
public interface IGraphCompilerFactory
{

    /**
     * Returns the graph compiler for the given graph type, or null if no compiler has been registered for this graph type.
     * 
     * @param graphType the graph type to fetch the compiler for
     * @return the graph compiler
     */
    IGraphCompiler getCompilerForClass(Class<?> graphType);

    /**
     * Registers a graph compiler for the given graph type.
     * 
     * @param graphType the graph type to register, cannot be null
     * @param compiler the compiler to use for this graph type, cannot be null
     */
    void registerCompiler(Class<?> graphType, IGraphCompiler compiler);

}
