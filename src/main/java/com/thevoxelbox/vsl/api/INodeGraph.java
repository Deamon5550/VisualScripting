package com.thevoxelbox.vsl.api;

import com.thevoxelbox.vsl.node.ExecutableNode;

public interface INodeGraph
{
    void setStartNode(ExecutableNode start);

    String getName();

    ExecutableNode getStart();

    int getIncrement();

    void incrementName();
}
