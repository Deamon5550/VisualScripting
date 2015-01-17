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
        if (this.provider == null)
        {
            throw new UnsupportedOperationException("Cannot get the provider of a raw input representation");
        }
        return this.provider;
    }
}
