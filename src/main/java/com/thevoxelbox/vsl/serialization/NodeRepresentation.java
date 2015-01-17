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

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;
import com.thevoxelbox.vsl.annotation.Input;
import com.thevoxelbox.vsl.annotation.NodeInfo;
import com.thevoxelbox.vsl.annotation.Output;
import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.error.InvalidNodeException;

/**
 * A node prototype representation.
 */
public class NodeRepresentation
{

    private final int index;
    private final NodeInfo info;
    private final List<InputRepresentation> inputs;
    private final List<OutputRepresentation> outputs;

    /**
     * Creates a new {@link NodeRepresentation}.
     * 
     * @param n The node
     * @param index The prototype index
     * @throws InvalidNodeException If the node is invalid
     */
    public NodeRepresentation(Node n, int index) throws InvalidNodeException
    {
        this.index = index;
        Class<?> cls = n.getClass();
        if (!cls.isAnnotationPresent(NodeInfo.class))
        {
            throw new InvalidNodeException("Node " + n.toString() + " was lacking a @NodeInfo annotation.");
        }
        this.info = cls.getAnnotation(NodeInfo.class);
        this.inputs = Lists.newArrayList();
        this.outputs = Lists.newArrayList();
        int in = 0;
        List<Field> search = Lists.newArrayList();
        Class<?> next = cls;
        while (next != null)
        {
            for (Field f : next.getDeclaredFields())
            {
                search.add(f);
            }
            next = next.getSuperclass();
        }
        for (Field f : search)
        {
            if (f.isAnnotationPresent(Input.class))
            {
                this.inputs.add(new InputRepresentation(in++, f.getAnnotation(Input.class), f.getName(), null));
            }
            if (f.isAnnotationPresent(Output.class))
            {
                this.outputs.add(new OutputRepresentation(in++, f.getAnnotation(Output.class), f.getName(), null));
            }
        }
    }

    /**
     * Gets the node name;
     * 
     * @return The name
     */
    public String getName()
    {
        return this.info.name();
    }

    /**
     * Gets the index of this node prototype.
     * 
     * @return The index
     */
    public int getIndex()
    {
        return this.index;
    }

    /**
     * Gets the raw input prototypes.
     * 
     * @return The raw inputs
     */
    public List<InputRepresentation> getRawInputs()
    {
        return this.inputs;
    }

    /**
     * Gets the raw output prototypes.
     * 
     * @return The raw outputs
     */
    public List<OutputRepresentation> getRawOutputs()
    {
        return this.outputs;
    }

}
