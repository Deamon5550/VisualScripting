package com.thevoxelbox.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.thevoxelbox.vsl.VariableScope;
import com.thevoxelbox.vsl.api.IGraphCompiler;
import com.thevoxelbox.vsl.api.INodeGraph;
import com.thevoxelbox.vsl.api.IRunnableGraph;
import com.thevoxelbox.vsl.api.IVariableHolder;
import com.thevoxelbox.vsl.classloader.ASMClassLoader;
import com.thevoxelbox.vsl.classloader.NodeGraphCompiler;
import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.error.InvalidNodeTypeException;
import com.thevoxelbox.vsl.node.NodeGraph;
import com.thevoxelbox.vsl.node.debug.PrintNode;
import com.thevoxelbox.vsl.node.variables.StringValueNode;

public class NodeGraphTest
{
    IVariableHolder vars;
    IGraphCompiler compiler;

    @Before
    public void setup()
    {
        vars = new VariableScope();
        compiler = new NodeGraphCompiler();
    }

    @Test
    public void testStringNodes() throws IOException, NullPointerException, InvalidNodeTypeException, InstantiationException, IllegalAccessException,
            GraphCompilationException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(out);

        StringValueNode string = new StringValueNode("Hello");
        StringValueNode string2 = new StringValueNode(" World");
        PrintNode print = new PrintNode();
        PrintNode print2 = new PrintNode();
        print.mapInput("msg", string.getOutput("value"));
        print2.mapInput("msg", string2.getOutput("value"));
        print.setNextNode(print2);

        INodeGraph tree = new NodeGraph("Test Graph");
        tree.setStartNode(print);
        @SuppressWarnings("unchecked")
        Class<? extends IRunnableGraph> compiled = (Class<? extends IRunnableGraph>) compiler.compile(ASMClassLoader.getGlobalClassLoader(), tree);
        IRunnableGraph graph = compiled.newInstance();
        graph.run(vars);

        String s = new String(baos.toByteArray());
        s = s.replace("\n", "");
        s = s.replace("\r", "");
        assertEquals("Hello World", s);
        System.setOut(oldOut);
    }

}
