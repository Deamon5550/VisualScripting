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
package com.thevoxelbox.vsl.node;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;
import com.thevoxelbox.vsl.annotation.NodeInfo;
import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.ReflectionHelper;

/**
 * An abstract node.
 */
public abstract class AbstractNode implements Node
{

    private Node next;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNext(Node n)
    {
        this.next = n;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getNext()
    {
        return this.next;
    }

    /**
     * Gets all input nodes to this node which are not static providers sourced
     * by this node.
     * 
     * @return The input nodes.
     */
    public Node[] getInputs()
    {
        List<Node> inputs = Lists.newArrayList();

        for (String s : this.getClass().getAnnotation(NodeInfo.class).inputs())
        {
            try
            {
                Field f = ReflectionHelper.getField(this.getClass(), s);
                if (f == null)
                {
                    continue;
                }
                Provider<?> p = (Provider<?>) f.get(this);
                if (p.getOwner() != this && !inputs.contains(p.getOwner()))
                {
                    inputs.add(p.getOwner());
                }
            } catch (Exception ignored)
            {
                ignored.printStackTrace();
                continue;
            }
        }

        return inputs.toArray(new Node[inputs.size()]);
    }
}
