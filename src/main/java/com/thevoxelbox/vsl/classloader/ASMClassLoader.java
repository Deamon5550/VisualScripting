package com.thevoxelbox.vsl.classloader;

public class ASMClassLoader extends ClassLoader
{
    
    public ASMClassLoader()
    {
        
    }
    
    public ASMClassLoader(ClassLoader parent)
    {
        super(parent);
    }

    public Class<?> defineClass(String name, byte[] cls)
    {
        return defineClass(name, cls, 0, cls.length);
    }

    public boolean isClassLoaded(String name)
    {
        return findLoadedClass(name) != null;
    }

    private static ASMClassLoader GLOBAL = null;

    public static ASMClassLoader getGlobalClassLoader()
    {
        if (GLOBAL == null)
        {
            GLOBAL = new ASMClassLoader();
        }
        return GLOBAL;
    }

    public static void resetGlobalClassLoader()
    {
        GLOBAL = new ASMClassLoader();
        System.gc();
    }
}
