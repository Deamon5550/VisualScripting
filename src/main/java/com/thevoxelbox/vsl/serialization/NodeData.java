package com.thevoxelbox.vsl.serialization;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;
import com.thevoxelbox.vsl.annotation.NodeInfo;
import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.ReflectionHelper;


public class NodeData
{
    
    private final Node node;
    private final NodeInfo info;

    public NodeData(Node n)
    {
        this.node = n;
        this.info = n.getClass().getAnnotation(NodeInfo.class);
    }
    
    public Node getNode()
    {
        return this.node;
    }
    
    public NodeInfo getInfo()
    {
        return this.info;
    }
    
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

    public Class<? extends Node> getNodeClass()
    {
        return this.node.getClass();
    }
    
}
