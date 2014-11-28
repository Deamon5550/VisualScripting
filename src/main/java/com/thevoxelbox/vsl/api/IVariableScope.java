package com.thevoxelbox.vsl.api;

/**
 * A VariableScope is a recursive {@link IVariableHolder}.
 */
public interface IVariableScope extends IVariableHolder
{
    /**
     * Sets the parent {@link IVariableScope}.
     * 
     * @param scope the new parent, cannot be null
     */
    void setParent(IVariableScope scope);

    /**
     * Returns the parent of this VariableScope or null if this VariableScope has no parent.
     * 
     * @return the parent, may be null
     */
    IVariableScope getParent();

    /**
     * Returns the highest parent VariableScope. That is the farthest parent VariableScope by recursively calling {@link #getParent()} until a
     * VariableScope with no parent is reached. Returns null if this VariableScope has no parent.
     * 
     * @return the highest parent, may be null
     */
    IVariableScope getHighestParent();
}
