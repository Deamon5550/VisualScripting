package com.thevoxelbox.vsl.classloader;

import com.thevoxelbox.vsl.api.IGraphCompiler;
import com.thevoxelbox.vsl.api.IGraphCompilerFactory;

public class ASMClassLoader extends ClassLoader
{
    IGraphCompilerFactory compiler;

    public ASMClassLoader(ClassLoader parent, IGraphCompilerFactory compiler)
    {
        super(parent);
        this.compiler = compiler;
    }

    public IGraphCompilerFactory getCompilerFactory()
    {
        return this.compiler;
    }

    public IGraphCompiler getCompiler(Class<?> cls)
    {
        return this.compiler.getCompilerForClass(cls);
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
