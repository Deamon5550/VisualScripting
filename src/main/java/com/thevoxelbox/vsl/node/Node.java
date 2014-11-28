package com.thevoxelbox.vsl.node;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.MethodVisitor;

import com.thevoxelbox.vsl.api.INode;
import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.error.InvalidNodeTypeException;
import com.thevoxelbox.vsl.type.Type;

/**
 * A node. A single piece of a {@link NodeGraph}.
 */
public abstract class Node implements INode, Serializable
{
    private static final long serialVersionUID = 4547770054027115676L;
    /**
     * The node's name.
     */
    private final String name;
    /**
     * The node's package name.
     */
    private final String packageName;
    /**
     * The outputs for this node.
     */
    private final Map<String, NodeOutput> outputs = new HashMap<String, NodeOutput>();
    /**
     * The inputs for this node.
     */
    private final Map<String, NodeInput> inputs = new HashMap<String, NodeInput>();

    /**
     * Creates a new node.
     * 
     * @param name the name, cannot be null or empty
     */
    public Node(String name)
    {
        this(name, "");
    }

    /**
     * Creates a new node.
     * 
     * @param name the name, cannot be null or empty
     * @param packageName the package name, cannot be null or empty
     */
    public Node(String name, String packageName)
    {
        checkNotNull(name, "Name cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        checkNotNull(packageName, "Name cannot be null!");
        checkArgument(!packageName.isEmpty(), "Name cannot be empty");
        this.name = name;
        this.packageName = packageName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInput(String name, Type type, boolean required, Object defaultValue)
    {
        checkNotNull(name, "Name cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        checkNotNull(type, "Type cannot be null!");
        inputs.put(name, new NodeInput(name, type, required, defaultValue));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addOutput(String name, Type type, INode parent)
    {
        checkNotNull(name, "Name cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        checkNotNull(type, "Type cannot be null!");
        checkNotNull(parent, "Parent cannot be null!");
        outputs.put(name, new NodeOutput(name, type, parent));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mapInput(String input, NodeOutput source) throws InvalidNodeTypeException, GraphCompilationException
    {
        checkNotNull(input, "input name cannot be null!");
        checkArgument(!input.isEmpty(), "Input name cannot be empty");
        checkNotNull(source, "Source output cannot be null!");
        if (!inputs.containsKey(input))
        {
            throw new GraphCompilationException("Attempted to map to unknown input " + input);
        }
        if (!inputs.get(input).getType().equals(source.getType()) && !inputs.get(input).getType().equals(Type.OBJECT))
        {
            throw new InvalidNodeTypeException("Invalid type " + source.getType().toString() + " expected " + inputs.get(input).getType().toString());
        }
        inputs.get(input).setSource(source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeOutput getOutput(String name)
    {
        checkNotNull(name, "Name cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        return outputs.get(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeInput getInput(String name)
    {
        checkNotNull(name, "Name cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        return inputs.get(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOutput(String name, int localIndex)
    {
        checkNotNull(name, "Name cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        checkArgument(localIndex >= 0, "Local variable index cannot be negative");
        getOutput(name).set(localIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insert(MethodVisitor mv, int localsIndex) throws GraphCompilationException
    {
        checkNotNull(mv, "MethodVisitor cannot be null!");
        checkArgument(localsIndex >= 0, "Local variable index cannot be negative");
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

    /**
     * Insert the node into the given {@link MethodVisitor}, performs no validation checks and is intended for internal use during compilation.
     * 
     * @param mv the {@link MethodVisitor}
     * @param localsIndex the next unused local variable index, cannot be null
     * @return the new next unused local variable index, cannot be negative
     * @throws GraphCompilationException if an error occurs
     */
    protected abstract int insertLocal(MethodVisitor mv, int localsIndex) throws GraphCompilationException;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExecutable()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPackage()
    {
        return this.packageName;
    }

}
