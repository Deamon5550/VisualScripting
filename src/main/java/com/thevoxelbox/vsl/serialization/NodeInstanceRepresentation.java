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
import com.thevoxelbox.vsl.annotation.Output;
import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.util.Provider;

/**
 * Represents a specific instance of a node.
 */
public class NodeInstanceRepresentation
{

    private final int index;
    private final Node node;
    private final NodeRepresentation rep;
    private final List<InputRepresentation> inputs;
    private final List<OutputRepresentation> outputs;

    /**
     * Creates a new node instance.
     * 
     * @param index The instance index
     * @param node The node
     * @param rep The node prototype
     */
    public NodeInstanceRepresentation(int index, Node node, NodeRepresentation rep)
    {
        this.index = index;
        this.node = node;
        this.rep = rep;
        this.inputs = Lists.newArrayList();
        this.outputs = Lists.newArrayList();
        int in = 0;
        List<Field> search = Lists.newArrayList();
        Class<?> next = node.getClass();
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
                f.setAccessible(true);
                try
                {
                    this.inputs.add(new InputRepresentation(in++, f.getAnnotation(Input.class), f.getName(), (Provider<?>) f.get(node)));
                } catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                    continue;
                } catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                    continue;
                }
            }
            if (f.isAnnotationPresent(Output.class))
            {
                f.setAccessible(true);
                try
                {
                    this.outputs.add(new OutputRepresentation(in++, f.getAnnotation(Output.class), f.getName(), (Provider<?>) f.get(node)));
                } catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                    continue;
                } catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    /**
     * Gets the instance index.
     * 
     * @return The index
     */
    public int getIndex()
    {
        return this.index;
    }

    /**
     * Gets the node.
     * 
     * @return The node
     */
    public Node getNode()
    {
        return this.node;
    }

    /**
     * Gets the node prototype.
     * 
     * @return The node prototype
     */
    public NodeRepresentation getRepresentation()
    {
        return this.rep;
    }

    /**
     * Gets the inputs for this instance.
     * 
     * @return The inputs
     */
    public List<InputRepresentation> getInputs()
    {
        return this.inputs;
    }

    /**
     * Gets the outputs for this instance.
     * 
     * @return The outputs
     */
    public List<OutputRepresentation> getOutputs()
    {
        return this.outputs;
    }

    /**
     * Gets the output matching the given provider.
     * 
     * @param provider The provider to match
     * @return The output
     */
    public OutputRepresentation getOutput(Provider<?> provider)
    {
        for (OutputRepresentation output : this.outputs)
        {
            if (output.getProvider() == provider)
            {
                return output;
            }
        }
        return null;
    }
}
