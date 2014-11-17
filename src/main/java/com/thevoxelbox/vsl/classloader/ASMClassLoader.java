package com.thevoxelbox.vsl.classloader;

import com.thevoxelbox.vsl.api.IGraphCompiler;

public class ASMClassLoader extends ClassLoader
{
    IGraphCompiler compiler;
    
    public ASMClassLoader(ClassLoader parent, IGraphCompiler compiler)
    {
        super(parent);
        this.compiler = compiler;
    }
    
    public IGraphCompiler getCompiler()
    {
        return this.compiler;
    }
    
    public void setCompiler(IGraphCompiler compiler)
    {
        this.compiler = compiler;
    }

    public Class<?> defineClass(String name, byte[] cls)
    {
        return defineClass(name, cls, 0, cls.length);
    }

    public boolean isClassLoaded(String name)
    {
        return findLoadedClass(name) != null;
    }
}
