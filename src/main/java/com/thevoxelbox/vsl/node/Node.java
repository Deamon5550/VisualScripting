package com.thevoxelbox.vsl.node;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.MethodVisitor;

import com.thevoxelbox.vsl.IOType;
import com.thevoxelbox.vsl.api.INode;
import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.error.InvalidNodeTypeException;

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
    public void addInput(String name, IOType type, boolean required, Object defaultValue)
    {
        if(inputs.containsKey(name)) return;
        inputs.put(name, new NodeInput(name, type, required, defaultValue));
    }

    @Override
    public void addOutput(String name, IOType type, INode parent)
    {
        if(outputs.containsKey(name)) return;
        outputs.put(name, new NodeOutput(name, type, parent));
    }

    @Override
    public void mapInput(String input, NodeOutput source) throws InvalidNodeTypeException, NullPointerException
    {
        if(!inputs.containsKey(input))
        {
            throw new NullPointerException("Attempted to map to unknown input " + input);
        }
        if(inputs.get(input).getType() != source.getType())
        {
            throw new InvalidNodeTypeException("Invalid type " + source.getType().name() + " expected " + inputs.get(input).getType().name());
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
        for(String n: inputs.keySet())
        {
            if(inputs.get(n).getSource().get() == -1)
            {
                if(inputs.get(n).getSource() == null)
                {
                    if(inputs.get(n).isRequired())
                    {
                        throw new GraphCompilationException("Unfilled required input " + n + " " + this.name);
                    }
                    else
                    {
                        init = inputs.get(n).insertDefaultValue(mv, init);
                    }
                }
                else
                {
                    init = inputs.get(n).getSource().getParent().insert(mv, init);
                }
            }
        }
        return insertLocal(mv, localsIndex);
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
