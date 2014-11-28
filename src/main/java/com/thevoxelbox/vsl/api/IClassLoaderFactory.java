package com.thevoxelbox.vsl.api;

import com.thevoxelbox.vsl.classloader.ASMClassLoader;

/**
 * A Factory for registering and distributing ClassLoaders. The purpose for this factory is to facilitate properly dereferencing the classloaders
 * after their use is fulfilled and therefore allowing all compiled node graphs loaded by them to be garbage collected.
 */
public interface IClassLoaderFactory
{

    /**
     * Returns the classloader for this Object key. If no classloader exists for this key then a new one is created and returned.
     * 
     * @param key the key for the new or existing class loader, cannot be null
     * @return the classloader
     */
    ASMClassLoader getClassLoader(Object key);

    /**
     * Clears the classloader associated with the given key, thereby allowing all classes loaded by it to be garbage collected.
     * 
     * @param key the key to unload, cannot be null
     */
    void clearClassLoader(Object key);

}
