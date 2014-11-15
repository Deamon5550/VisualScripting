package com.thevoxelbox.vsl.api;

public interface IRunnableGraph
{
    void run(IVariableHolder vars);

    String getName();
}
