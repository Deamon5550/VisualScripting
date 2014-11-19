package com.thevoxelbox.vsl.api;

public interface IChainedRunnableGraph extends IRunnableGraph
{
    void chain(IChainedRunnableGraph next);
}
