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

import com.google.common.base.Optional;

/**
 * A VariableScope is a recursive {@link VariableHolder}.
 */
public interface VariableScope extends VariableHolder
{

    /**
     * Sets the parent {@link VariableScope}.
     * 
     * @param scope the new parent, cannot be null
     */
    void setParent(VariableHolder scope);

    /**
     * Returns the parent of this VariableScope or null if this VariableScope
     * has no parent.
     * 
     * @return the parent, may be null
     */
    Optional<VariableHolder> getParent();

    /**
     * Returns the highest parent VariableScope. That is the farthest parent
     * VariableScope by recursively calling {@link #getParent()} until a
     * VariableScope with no parent is reached. Returns null if this
     * VariableScope has no parent.
     * 
     * @return the highest parent, may be null
     */
    Optional<VariableHolder> getHighestParent();
}
