package com.thevoxelbox.vsl.node;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.MethodVisitor;

import com.thevoxelbox.vsl.api.INode;
import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.error.InvalidNodeTypeException;
import com.thevoxelbox.vsl.type.Type;

public abstract class Node implements INode
{

    private final String name;
    private final String packageName;
    private final Map<String, NodeOutput> outputs = new HashMap<String, NodeOutput>();
    private final Map<String, NodeInput> inputs = new HashMap<String, NodeInput>();

    public Node(String name)
    {
        this(name, "");
    }

    public Node(String name, String packageName)
    {
        this.name = name;
        this.packageName = packageName;
    }

    @Override
    public void addInput(String name, Type type, boolean required, Object defaultValue)
    {
        if (inputs.containsKey(name)) return;
        inputs.put(name, new NodeInput(name, type, required, defaultValue));
    }

    @Override
    public void addOutput(String name, Type type, INode parent)
    {
        if (outputs.containsKey(name)) return;
        outputs.put(name, new NodeOutput(name, type, parent));
    }

    @Override
    public void mapInput(String input, NodeOutput source) throws InvalidNodeTypeException, NullPointerException
    {
        if (!inputs.containsKey(input))
        {
            throw new NullPointerException("Attempted to map to unknown input " + input);
        }
        if (source == null)
        {
            throw new NullPointerException("Attempted to map from null output");
        }
        if (!inputs.get(input).getType().equals(source.getType()) && !inputs.get(input).getType().equals(Type.OBJECT))
        {
            throw new InvalidNodeTypeException("Invalid type " + source.getType().toString() + " expected " + inputs.get(input).getType().toString());
        }
        inputs.get(input).setSource(source);
    }

    @Override
    public NodeOutput getOutput(String name)
    {
        return outputs.get(name);
    }

    @Override
    public NodeInput getInput(String name)
    {
        return inputs.get(name);
    }

    @Override
    public void setOutput(String name, int localIndex)
    {
        getOutput(name).set(localIndex);
    }

    @Override
    public int insert(MethodVisitor mv, int localsIndex) throws GraphCompilationException
    {
        int init = localsIndex;
        for (String n : inputs.keySet())
        {
            if (inputs.get(n).getSource() == null)
            {
                if (inputs.get(n).isRequired())
                {
                    throw new GraphCompilationException("Unfilled required input " + n + " " + this.name);
                }
            } else if (inputs.get(n).getSource().get() == -1)
            {
                init = inputs.get(n).getSource().getParent().insert(mv, init);
            }
        }
        return insertLocal(mv, init);
    }

    protected abstract int insertLocal(MethodVisitor mv, int localsIndex) throws GraphCompilationException;

    @Override
    public boolean isExecutable()
    {
        return false;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getPackage()
    {
        return this.packageName;
    }

}
