package com.thevoxelbox.vsl.api;

import com.thevoxelbox.vsl.classloader.ASMClassLoader;

public interface IClassLoaderFactory
{
    
    ASMClassLoader getClassLoader(Object target);
    
    void clearClassLoader(Object target);
    
}
