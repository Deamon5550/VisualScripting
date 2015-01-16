package com.thevoxelbox.vsl.serialization;

import com.thevoxelbox.vsl.annotation.Output;
import com.thevoxelbox.vsl.util.Provider;

/**
 * Represents the an input to a node
 */
public class OutputRepresentation implements IORepresentation
{

    private final int index;
    private final Output annotation;
    private final String name;
    private final Provider<?> provider;

    public OutputRepresentation(int index, Output output, String name, Provider<?> provider)
    {
        this.index = index;
        this.annotation = output;
        this.name = output.name().equals("_") ? name : output.name();
        this.provider = provider;
    }

    public OutputRepresentation(int index, Output output, Provider<?> provider)
    {
        this(index, output, output.name(), provider);
    }

    public int getIndex()
    {
        return this.index;
    }

    public Output getInputAnnotation()
    {
        return this.annotation;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public Provider<?> getProvider()
    {
        return this.provider;
    }
}
