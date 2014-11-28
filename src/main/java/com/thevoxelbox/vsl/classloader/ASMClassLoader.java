package com.thevoxelbox.vsl.classloader;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import com.thevoxelbox.vsl.api.IGraphCompiler;
import com.thevoxelbox.vsl.api.IGraphCompilerFactory;

/**
 * A classloader to expose the {@link #defineClass(String, byte[], int, int)} method from {@link ClassLoader}.
 */
public class ASMClassLoader extends ClassLoader
{
    /**
     * A graph compiler factory for this classloader.
     */
    private IGraphCompilerFactory compiler;

    /**
     * Creates a new classloader.
     * 
     * @param parent the parent classloader, cannot be null
     * @param compiler a graph compiler instance, may be null
     */
    public ASMClassLoader(ClassLoader parent, IGraphCompilerFactory compiler)
    {
        super(parent);
        this.compiler = compiler;
    }

    /**
     * Returns the graph compiler factory associated with this classloader.
     * 
     * @return the graph compiler factory
     */
    public IGraphCompilerFactory getCompilerFactory()
    {
        return this.compiler;
    }

    /**
     * Returns the graph compiler for the given node graph type.
     * 
     * @param cls the node graph type, cannot be null
     * @return the graph compiler or null if no graph compiler factory was set for this classloader
     */
    public IGraphCompiler getCompiler(Class<?> cls)
    {
        if (this.compiler == null)
        {
            return null;
        }
        checkNotNull(cls, "Graph type cannot be null!");
        return this.compiler.getCompilerForClass(cls);
    }

    /**
     * Defines and loads a new class from the given byte array.
     * 
     * @param name the name of the new class, cannot be null or empty
     * @param cls a byte array representation of the class, cannot be null
     * @return the new class
     */
    public Class<?> defineClass(String name, byte[] cls)
    {
        checkNotNull(name, "Name cannot be null!");
        checkNotNull(cls, "Graph type cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        return defineClass(name, cls, 0, cls.length);
    }

    /**
     * Checks if a class with the given name is loaded by this classloader or a parent class loader. Makes a check that
     * {@link #findLoadedClass(String)} does not return null.
     * 
     * @param name the name to check, cannot be null or empty
     * @return whether the class exists
     */
    public boolean isClassLoaded(String name)
    {
        checkNotNull(name, "Name cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        return findLoadedClass(name) != null;
    }
}
