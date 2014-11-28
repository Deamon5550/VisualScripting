package com.thevoxelbox.vsl.classloader;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.HashMap;
import java.util.Map;

import com.thevoxelbox.vsl.api.IGraphCompiler;
import com.thevoxelbox.vsl.api.IGraphCompilerFactory;

/**
 * A graph compiler factory.
 */
public class GraphCompilerFactory implements IGraphCompilerFactory
{
    /**
     * A Map of compilers with the node graph types as keys.
     */
    private Map<Class<?>, IGraphCompiler> compilers;

    /**
     * Creates a new GraphCompilerFactory.
     */
    public GraphCompilerFactory()
    {
        this.compilers = new HashMap<Class<?>, IGraphCompiler>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGraphCompiler getCompilerForClass(Class<?> cls)
    {
        checkNotNull(cls, "Class cannot be null!");
        return this.compilers.get(cls);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerCompiler(Class<?> cls, IGraphCompiler compiler)
    {
        checkNotNull(cls, "Class cannot be null!");
        checkNotNull(compiler, "Compiler cannot be null!");
        this.compilers.put(cls, compiler);
    }

}
