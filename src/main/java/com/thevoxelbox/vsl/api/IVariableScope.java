package com.thevoxelbox.vsl.api;

public interface IVariableScope extends IVariableHolder
{
    void setParent(IVariableScope scope);

    IVariableScope getParent();

    IVariableScope getHighestParent();
}
