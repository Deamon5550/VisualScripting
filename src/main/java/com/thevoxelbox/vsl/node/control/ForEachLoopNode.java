package com.thevoxelbox.vsl.node.control;

import static com.google.common.base.Preconditions.checkNotNull;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.node.ExecutableNode;
import com.thevoxelbox.vsl.type.Type;
import com.thevoxelbox.vsl.type.TypeDepth;

/**
 * A node to loop over all objects in an array and operate on them.
 */
public class ForEachLoopNode extends ExecutableNode implements Opcodes
{
    private static final long serialVersionUID = 5228396915590984962L;
    /**
     * The loop body.
     */
    private ExecutableNode body;

    /**
     * Creates a new {@link ForEachLoopNode}.
     * 
     * @param body the loop body, cannot be null
     * @param type the array type, cannot be null must be an array
     * @throws GraphCompilationException if the type is not an array
     */
    public ForEachLoopNode(ExecutableNode body, Type type) throws GraphCompilationException
    {
        super("for-each", "control");
        checkNotNull(body, "Loop body cannot be null!");
        checkNotNull(type, "Type cannot be null");
        if (type.getDepth() != TypeDepth.ARRAY)
        {
            throw new GraphCompilationException("Input type for ArrayIndexNode is not an array type");
        }
        addInput("array", type, true, null);
        addOutput("next", Type.getType(type.getName(), TypeDepth.SINGLE), this);
        addOutput("index", Type.INTEGER, this);
        this.body = body;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int insertLocal(MethodVisitor mv, int localsIndex) throws GraphCompilationException
    {
        /*
         
         ICONST_0
         ISTORE i
         ALOAD array
         ARRAYLENGTH
         ISTORE i+1
         GOTO L2
        L1
         ALOAD array
         ILOAD i
         AALOAD
         ASTORE next
         
         insert body nodes here
         
         IINC i 1
        L2
         ILOAD i
         ILOAD i+1
         IF_ICMPLT L1
         
         */
        if (body == null)
        {
            return localsIndex;
        }

        int array = getInput("array").getSource().get();
        int i = localsIndex++;
        int target = localsIndex++;
        int next = localsIndex++;

        mv.visitVarInsn(ALOAD, array); // [arrayref]
        mv.visitInsn(ARRAYLENGTH); // [integer]
        mv.visitVarInsn(ISTORE, target); // []
        mv.visitInsn(ICONST_0); // [integer]
        mv.visitVarInsn(ISTORE, i); // []
        Label l2 = new Label();
        mv.visitJumpInsn(GOTO, l2);
        Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitVarInsn(ALOAD, array); // [arrayref]
        mv.visitVarInsn(ILOAD, i); // [arrayref, integer]
        mv.visitInsn(AALOAD); // [string]
        mv.visitVarInsn(ASTORE, next); // []
        setOutput("next", next);
        setOutput("index", i);
        ExecutableNode current = this.body;
        while (current != null)
        {
            localsIndex = current.insert(mv, localsIndex);
            current = current.getNextNode();
        }
        mv.visitIincInsn(i, 1); // []
        mv.visitLabel(l2);
        mv.visitVarInsn(ILOAD, i); // [integer]
        mv.visitVarInsn(ILOAD, target); // [integer, integer]
        mv.visitJumpInsn(IF_ICMPLT, l3); // []

        /*mv.visitInsn(ICONST_0);
        mv.visitVarInsn(ISTORE, i);
        mv.visitVarInsn(ALOAD, array);
        mv.visitInsn(ARRAYLENGTH);
        mv.visitVarInsn(ISTORE, target);
        Label l2 = new Label();
        mv.visitJumpInsn(GOTO, l2);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitVarInsn(ALOAD, array);
        mv.visitVarInsn(ILOAD, i);
        mv.visitInsn(AALOAD);
        mv.visitVarInsn(ASTORE, next);
        setOutput("next", next);
        setOutput("index", i);
        ExecutableNode current = this.body;
        while(current != null)
        {
            localsIndex = current.insert(mv, localsIndex);
            current = current.getNextNode();
        }
        mv.visitIincInsn(i, 1);
        mv.visitLabel(l2);
        mv.visitVarInsn(ILOAD, i);
        mv.visitVarInsn(ILOAD, target);
        mv.visitJumpInsn(IF_ICMPLT, l1);*/
        return localsIndex;
    }
}
