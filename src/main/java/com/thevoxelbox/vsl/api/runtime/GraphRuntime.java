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
package com.thevoxelbox.vsl.api.runtime;

import java.util.UUID;

import com.google.common.base.Optional;
import com.thevoxelbox.vsl.api.node.NodeGraph;
import com.thevoxelbox.vsl.api.variables.VariableHolder;

/**
 * An runtime within which {@link NodeGraph}s may be executed.
 */
public interface GraphRuntime
{

    /**
     * Gets a value from the runtime's variable storage with the given unique
     * id.
     * 
     * @param uuid The unique Id
     * @param <T> The type
     * @return The value, if available
     */
    <T> Optional<T> get(UUID uuid);

    /**
     * Sets a value into the runtime's variable storage with the given unique id
     * as key.
     * 
     * @param uuid The unique Id
     * @param <T> The type
     * @param value The new value
     */
    <T> void set(UUID uuid, T value);

    /**
     * Gets the internal variables of thus runtime.
     * 
     * @return The variables
     */
    VariableHolder getVars();

    /**
     * Runs the given sequence of {@link NodeGraph}s within the context of this
     * runtime.
     * 
     * @param graphs THe graphs
     */
    void run(NodeGraph... graphs);

}
