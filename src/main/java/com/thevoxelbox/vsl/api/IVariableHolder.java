package com.thevoxelbox.vsl.api;

public interface IVariableHolder
{
    Object get(String name);

    void set(String name, Object value);
}
