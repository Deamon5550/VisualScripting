package com.thevoxelbox.vsl.api;

import com.google.common.base.Optional;

/**
 * A storage container for named variables.
 */
public interface IVariableHolder
{

    /**
     * Returns the variable with the given name, or null if there is no variable with that name in this storage container.
     * 
     * @param name the name of the object to fetch, cannot be null
     * @return the value or null if no value is found
     */
    Optional<Object> get(String name);

    <T> Optional<T> get(String name, Class<T> type);

    /**
     * Puts a named value into this storage container
     * 
     * @param name the name of the value to add, cannot be null
     * @param value the value to add
     */
    void set(String name, Object value);

    /**
     * Checks if this storage container has a value with the given name.
     * 
     * @param name the name to check
     * @return whether a value with this name exists
     */
    boolean hasValue(String name);

}
