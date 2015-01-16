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
package com.thevoxelbox.vsl.api.variables;

import java.util.Set;

import com.google.common.base.Optional;

/**
 * A storage container for named variables.
 */
public interface VariableHolder
{

    /**
     * Sets whether the variable holder should use case sensitive keys for its
     * variable storage.
     * 
     * @param cs Case sensitive keys
     */
    void setCaseSensitive(boolean cs);

    /**
     * Returns the variable with the given name, or null if there is no variable
     * with that name in this storage container.
     * 
     * @param name the name of the object to fetch, cannot be null
     * @return the value or null if no value is found
     */
    Optional<Object> get(String name);

    /**
     * Returns the variable with the given name, or null if there is no variable
     * with that name in this storage container.
     * 
     * @param name the name of the object to fetch, cannot be null
     * @param type The expected value type
     * @return the value or null if no value is found
     */
    <T> Optional<T> get(String name, Class<T> type);

    /**
     * Puts a named value into this storage container
     * 
     * @param name the name of the value to add, cannot be null
     * @param value the value to add
     */
    void set(String name, Object value);

    /**
     * Checks if this storage container has a value with the given name.
     * 
     * @param name the name to check
     * @return whether a value with this name exists
     */
    boolean hasValue(String name);

    /**
     * Gets a Set of all keys stored within this variable holder.
     * 
     * @return The keyset
     */
    Set<String> keyset();

}
