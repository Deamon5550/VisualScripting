package com.thevoxelbox.vsl.classloader;

import java.util.HashMap;
import java.util.Map;

import com.thevoxelbox.vsl.api.IGraphCompiler;
import com.thevoxelbox.vsl.api.IGraphCompilerFactory;

public class GraphCompilerFactory implements IGraphCompilerFactory
{
    private Map<Class<?>, IGraphCompiler> compilers;
    
    public GraphCompilerFactory()
    {
        this.compilers = new HashMap<Class<?>, IGraphCompiler>();
    }

    @Override
    public IGraphCompiler getCompilerForClass(Class<?> cls)
    {
        return this.compilers.get(cls);
    }

    @Override
    public void registerCompiler(Class<?> cls, IGraphCompiler compiler)
    {
        this.compilers.put(cls, compiler);
    }

}
