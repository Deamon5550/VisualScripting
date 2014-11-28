package com.thevoxelbox.vsl.error;

/**
 * An exception for errors within the graph compilation process.
 */
public class GraphCompilationException extends Exception
{
    private static final long serialVersionUID = -5939203417969472574L;

    /**
     * Creates a new exception.
     * 
     * @param msg the included description
     */
    public GraphCompilationException(String msg)
    {
        super(msg == null ? "" : msg);
    }
}
