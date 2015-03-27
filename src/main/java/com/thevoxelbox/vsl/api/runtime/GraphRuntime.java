package com.thevoxelbox.vsl.api.runtime;

import java.util.UUID;

import com.google.common.base.Optional;
import com.thevoxelbox.vsl.api.node.NodeGraph;
import com.thevoxelbox.vsl.api.variables.VariableHolder;

/**
 * An runtime within which {@link NodeGraph}s may be executed.
 */
public interface GraphRuntime
{

    /**
     * Gets a value from the runtime's variable storage with the given unique
     * id.
     * 
     * @param uuid The unique Id
     * @return The value, if available
     */
    <T> Optional<T> get(UUID uuid);

    /**
     * Sets a value into the runtime's variable storage with the given unique id
     * as key.
     * 
     * @param uuid The unique Id
     * @param value The new value
     */
    <T> void set(UUID uuid, T value);

    /**
     * Gets the internal variables of thus runtime.
     * 
     * @return The variables
     */
    VariableHolder getVars();

    /**
     * Runs the given sequence of {@link NodeGraph}s within the context of this
     * runtime.
     * 
     * @param graphs THe graphs
     */
    void run(NodeGraph... graphs);

}
