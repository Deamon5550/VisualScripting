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
package com.thevoxelbox.vsl.util;

import java.util.UUID;

import com.google.common.base.Optional;
import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.api.runtime.GraphRuntime;

/**
 * Tracks values for an input or output of a node and stores them with an
 * associated UUID.
 * 
 * @param <T> The value type
 */
public class Provider<T>
{

    private final T value;
    private final boolean isStatic;
    private final UUID uuid;
    private Node callback;

    /**
     * Creates a new {@link Provider} which will perform a callback to the given
     * node if the value is not set and a call to {@link #get(GraphRuntime)}
     * occurs.
     * 
     * @param n The node to call back to
     */
    public Provider(Node n)
    {
        this.callback = n;
        this.isStatic = false;
        this.value = null;
        this.uuid = UUID.randomUUID();
    }

    /**
     * Creates a new {@link Provider} with the given static value.
     * 
     * @param n The node as reference
     * @param value The static value
     */
    public Provider(Node n, T value)
    {
        this.callback = n;
        this.value = value;
        this.isStatic = true;
        this.uuid = UUID.randomUUID();
    }

    /**
     * Gets the owner of this provider.
     * 
     * @return The owner
     */
    public Node getOwner()
    {
        return this.callback;
    }

    /**
     * Gets whether this provider has a static value, or receives its value from
     * its owner.
     * 
     * @return Is static
     */
    public boolean isStatic()
    {
        return this.isStatic;
    }

    /**
     * Gets the default value of this provider, if it is a static provider. If
     * this provider is not a static provider then null will be returned.
     * 
     * @return The default value, if static
     */
    public T getDefaultValue()
    {
        return this.value;
    }

    /**
     * Gets the value for this provider from the given runtime using the
     * provider's UUID as key.
     * 
     * @param state The runtime
     * @return The value
     */
    public T get(GraphRuntime state)
    {
        if (this.isStatic)
        {
            return this.value;
        }
        Optional<T> ovalue = state.<T> get(this.uuid);
        if (ovalue.isPresent())
        {
            return ovalue.get();
        } else
        {
            this.callback.exec(state);
        }
        return state.<T> get(this.uuid).orNull();
    }

    /**
     * Sets the given value into the given runtime using the provider's UUID as
     * key.
     * 
     * @param value The new value
     * @param state The runtime
     */
    public void set(T value, GraphRuntime state)
    {
        state.<T> set(this.uuid, value);
    }

}
