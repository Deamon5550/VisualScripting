package com.thevoxelbox.vsl.api;

public interface IChainableNodeGraph
{
    boolean doInputsMatch(IChainableNodeGraph nextGraph);
    
    void setInputAsChainable(String name);
    
    void setOutputAsChainable(String name);
}
