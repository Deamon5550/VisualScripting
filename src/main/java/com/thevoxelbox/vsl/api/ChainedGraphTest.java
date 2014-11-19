package com.thevoxelbox.vsl.api;

public class ChainedGraphTest implements IChainedRunnableGraph
{
    
    IChainedRunnableGraph next = null;

    @Override
    public void run(IVariableHolder vars)
    {
        String a = "Hello ";
        String b = "World!";
        if(next != null)
        {
            next.run(vars);
        }
    }

    @Override
    public String getName()
    {
        return "Name";
    }

    @Override
    public void chain(IChainedRunnableGraph next)
    {
        this.next = next;
    }

}
