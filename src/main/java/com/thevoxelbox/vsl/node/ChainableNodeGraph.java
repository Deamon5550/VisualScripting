package com.thevoxelbox.vsl.node;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.thevoxelbox.vsl.api.IChainableNodeGraph;

public class ChainableNodeGraph extends NodeGraph implements IChainableNodeGraph
{
    private Map<String, Class<?>> inputs;
    private Map<String, Class<?>> outputs;

    public ChainableNodeGraph(String name)
    {
        super(name);
        this.inputs = new HashMap<String, Class<?>>();
        this.outputs = new HashMap<String, Class<?>>();
    }

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

    @Override
    public void addChainedInput(String name, Class<?> type)
    {
        this.inputs.put(name, type);
    }

    @Override
    public void addChainedOutput(String name, Class<?> type)
    {
        this.outputs.put(name, type);
    }

    @Override
    public Map<String, Class<?>> getInputs()
    {
        return Collections.unmodifiableMap(this.inputs);
    }

    @Override
    public Map<String, Class<?>> getOutputs()
    {
        return Collections.unmodifiableMap(this.outputs);
    }

}
