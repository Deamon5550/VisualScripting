package com.thevoxelbox.vsl.runtime;

import java.util.Map;
import java.util.UUID;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.thevoxelbox.vsl.api.node.NodeGraph;
import com.thevoxelbox.vsl.api.runtime.GraphRuntime;
import com.thevoxelbox.vsl.api.variables.VariableHolder;
import com.thevoxelbox.vsl.variables.SimpleVariableHolder;

/**
 * A {@link GraphRuntime} which runs the nodes in place using variable storage
 * within the runtime.
 */
public class ObjectRuntime implements GraphRuntime
{

    private final VariableHolder vars;
    private final Map<UUID, Object> values;

    /**
     * Creates a new {@link ObjectRuntime}.
     */
    public ObjectRuntime()
    {
        this(new SimpleVariableHolder());
    }

    /**
     * Creates a new {@link ObjectRuntime}.
     * 
     * @param vars
     *            The initial runtime variables
     */
    public ObjectRuntime(VariableHolder vars)
    {
        this.vars = vars;
        this.values = Maps.newHashMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(NodeGraph... graphs)
    {
        for (NodeGraph graph : graphs)
        {
            graph.run(this);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(UUID uuid)
    {
        return Optional.fromNullable((T) this.values.get(uuid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void set(UUID uuid, T value)
    {
        this.values.put(uuid, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VariableHolder getVars()
    {
        return this.vars;
    }

}
