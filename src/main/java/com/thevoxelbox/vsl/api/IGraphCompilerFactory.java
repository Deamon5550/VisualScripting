package com.thevoxelbox.vsl.api;

public interface IGraphCompilerFactory
{
    IGraphCompiler getCompilerForClass(Class<?> cls);

    void registerCompiler(Class<?> cls, IGraphCompiler compiler);
}
