/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 The VoxelBox
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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

    /**
     * Creates a new {@link OutputRepresentation}.
     * 
     * @param index The index
     * @param output The output annotation
     * @param name the output name
     * @param provider The output provider
     */
    public OutputRepresentation(int index, Output output, String name, Provider<?> provider)
    {
        this.index = index;
        this.annotation = output;
        this.name = output.name().equals("_") ? name : output.name();
        this.provider = provider;
    }

    /**
     * Creates a new {@link OutputRepresentation}.
     * 
     * @param index The index
     * @param output The output annotation
     * @param provider The output provider
     */
    public OutputRepresentation(int index, Output output, Provider<?> provider)
    {
        this(index, output, output.name(), provider);
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
     * Gets the output annotation.
     * 
     * @return The annotation
     */
    public Output getInputAnnotation()
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
     * Gets the output provider.
     * 
     * @return The provider
     */
    public Provider<?> getProvider()
    {
        if (this.provider == null)
        {
            throw new UnsupportedOperationException("Cannot get the provider of a raw output representation");
        }
        return this.provider;
    }
}
