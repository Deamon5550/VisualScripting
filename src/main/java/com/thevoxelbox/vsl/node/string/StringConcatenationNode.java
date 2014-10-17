package com.thevoxelbox.vsl.node.string;

import org.objectweb.asm.MethodVisitor;

import com.thevoxelbox.vsl.IOType;
import com.thevoxelbox.vsl.node.Node;

public class StringConcatenationNode extends Node
{

    public StringConcatenationNode()
    {
        super("String Concatenation", "string");
        addInput("first", IOType.STRING, true, null);
        addInput("second", IOType.STRING, true, null);
        addOutput("result", IOType.STRING, this);
    }

    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex)
    {
        // TODO Auto-generated method stub
        return 0;
    }

}
