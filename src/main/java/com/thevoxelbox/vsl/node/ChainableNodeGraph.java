package com.thevoxelbox.vsl.node;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.thevoxelbox.vsl.api.IChainableNodeGraph;

/**
 * A node graph which may be chained from and to another {@link ChainableNodeGraph}.
 */
public class ChainableNodeGraph extends NodeGraph implements IChainableNodeGraph
{
    private static final long serialVersionUID = -3351853188744638203L;
    /**
     * Inputs to be received from a preceding {@link ChainableNodeGraph}.
     */
    private Map<String, Class<?>> inputs;
    /**
     * Outputs to be sent to a following {@link ChainableNodeGraph}.
     */
    private Map<String, Class<?>> outputs;

    /**
     * Creates a new {@link ChainableNodeGraph}.
     * 
     * @param name the name for this graph
     */
    public ChainableNodeGraph(String name)
    {
        super(name);
        this.inputs = new HashMap<String, Class<?>>();
        this.outputs = new HashMap<String, Class<?>>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean doInputsMatch(IChainableNodeGraph nextGraph)
    {
        Map<String, Class<?>> nextInputs = nextGraph.getInputs();
        for (String n : this.outputs.keySet())
        {
            if (nextInputs.containsKey(n))
            {
                Class<?> outCls = this.outputs.get(n);
                Class<?> inCls = nextInputs.get(n);
                if (outCls != inCls)
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addChainedInput(String name, Class<?> type)
    {
        this.inputs.put(name, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addChainedOutput(String name, Class<?> type)
    {
        this.outputs.put(name, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Class<?>> getInputs()
    {
        return Collections.unmodifiableMap(this.inputs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Class<?>> getOutputs()
    {
        return Collections.unmodifiableMap(this.outputs);
    }

}
