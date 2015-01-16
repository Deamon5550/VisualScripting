package com.thevoxelbox.vsl.serialization;

import com.thevoxelbox.vsl.annotation.Input;
import com.thevoxelbox.vsl.util.Provider;

/**
 * Represents the an input to a node
 */
public class InputRepresentation implements IORepresentation
{

    private final int index;
    private final Input annotation;
    private final String name;
    private final Provider<?> provider;

    /**
     * Creates a new {@link InputRepresentation}.
     * 
     * @param index The input index
     * @param input The input annotation
     * @param name The input field name
     * @param provider The provider
     */
    public InputRepresentation(int index, Input input, String name, Provider<?> provider)
    {
        this.index = index;
        this.annotation = input;
        this.name = input.name().equals("_") ? name : input.name();
        this.provider = provider;
    }

    /**
     * Creates a new {@link InputRepresentation}.
     * 
     * @param index The input index
     * @param input The input annotation
     * @param provider The provider
     */
    public InputRepresentation(int index, Input input, Provider<?> provider)
    {
        this(index, input, input.name(), provider);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIndex()
    {
        return this.index;
    }

    /**
     * Gets the input annotation.
     * 
     * @return The annotation
     */
    public Input getInputAnnotation()
    {
        return this.annotation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        return this.name;
    }

    /**
     * Gets the input provider.
     * 
     * @return The input provider
     */
    public Provider<?> getProvider()
    {
        return this.provider;
    }
}
