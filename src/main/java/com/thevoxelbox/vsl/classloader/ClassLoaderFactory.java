package com.thevoxelbox.vsl.classloader;

import java.util.Map;
import java.util.WeakHashMap;

import com.thevoxelbox.vsl.api.IClassLoaderFactory;

public class ClassLoaderFactory implements IClassLoaderFactory
{

    Map<Object, ASMClassLoader> loaders = new WeakHashMap<Object, ASMClassLoader>();

    public ClassLoaderFactory()
    {

    }

    @Override
    public ASMClassLoader getClassLoader(Object target)
    {
        if (!loaders.containsKey(target))
        {
            loaders.put(target, new ASMClassLoader());
        }
        return loaders.get(target);
    }

    @Override
    public void clearClassLoader(Object target)
    {
        loaders.remove(target);
    }

}
