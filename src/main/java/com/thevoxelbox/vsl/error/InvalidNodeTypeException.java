package com.thevoxelbox.vsl.error;

import com.thevoxelbox.vsl.type.Type;

/**
 * An exception for invalid node I/O {@link Type}s.
 */
public class InvalidNodeTypeException extends Exception
{
    private static final long serialVersionUID = 405765579920039993L;

    /**
     * Creates a new InvalidNodeTypeException.
     * 
     * @param msg the included message
     */
    public InvalidNodeTypeException(String msg)
    {
        super(msg == null ? "" : msg);
    }

}
