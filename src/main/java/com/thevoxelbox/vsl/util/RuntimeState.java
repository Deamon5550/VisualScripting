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

import com.thevoxelbox.vsl.api.variables.VariableHolder;
import com.thevoxelbox.vsl.node.RunnableNodeGraph;

/**
 * A container for the values associated with a single runtime instance of a
 * {@link RunnableNodeGraph}.
 */
public class RuntimeState
{

    private final UUID uuid;
    private final VariableHolder vars;

    /**
     * Creates a new {@link RuntimeState}.
     * 
     * @param vars
     */
    public RuntimeState(VariableHolder vars)
    {
        this.vars = vars;
        this.uuid = UUID.randomUUID();
    }

    /**
     * Gets the {@link UUID} of this runtime instance.
     * 
     * @return The UUID
     */
    public UUID getUUID()
    {
        return this.uuid;
    }

    /**
     * Gets the runtime variable holder for this runtime instance.
     * 
     * @return The variables
     */
    public VariableHolder getVars()
    {
        return this.vars;
    }

}
