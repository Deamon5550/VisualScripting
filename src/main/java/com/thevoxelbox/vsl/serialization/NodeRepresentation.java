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

    public String getName()
    {
        return this.info.name();
    }

    public int getIndex()
    {
        return this.index;
    }

    public List<InputRepresentation> getRawInputs()
    {
        return this.inputs;
    }

    public List<OutputRepresentation> getRawOutputs()
    {
        return this.outputs;
    }

}
