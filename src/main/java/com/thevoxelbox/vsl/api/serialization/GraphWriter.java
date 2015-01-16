package com.thevoxelbox.vsl.api.serialization;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

import com.thevoxelbox.vsl.api.node.NodeGraph;
import com.thevoxelbox.vsl.error.InvalidNodeException;

/**
 * An interface for a serialization consumer for NodeGraphs.
 */
public interface GraphWriter extends Closeable, Flushable
{

    /**
     * Writes the given NodeGraph to the output.
     * 
     * @param graph The graph to write
     * @throws IOException If an error occurs while trying to write to the output.
     * @throws InvalidNodeException If the graph contains an invalid node for serialization
     */
    void write(NodeGraph graph) throws IOException, InvalidNodeException;
    
}
