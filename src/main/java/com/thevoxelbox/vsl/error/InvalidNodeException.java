package com.thevoxelbox.vsl.error;

/**
 * An exception to indicate that a node is invalid.
 */
public class InvalidNodeException extends Exception
{
    private static final long serialVersionUID = -5414120143027252485L;

    /**
     * Creates a new InvalidNodeException with the given message.
     * 
     * @param msg The message
     */
    public InvalidNodeException(String msg)
    {
        super(msg);
    }
    
}
