package com.thevoxelbox.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.thevoxelbox.vsl.VariableScope;
import com.thevoxelbox.vsl.api.IGraphCompilerFactory;
import com.thevoxelbox.vsl.api.INodeGraph;
import com.thevoxelbox.vsl.api.IRunnableGraph;
import com.thevoxelbox.vsl.api.IVariableHolder;
import com.thevoxelbox.vsl.classloader.ASMClassLoader;
import com.thevoxelbox.vsl.classloader.GraphCompilerFactory;
import com.thevoxelbox.vsl.classloader.NodeGraphCompiler;
import com.thevoxelbox.vsl.node.NodeGraph;
import com.thevoxelbox.vsl.node.control.ForEachLoopNode;
import com.thevoxelbox.vsl.node.debug.PrintNode;
import com.thevoxelbox.vsl.node.variables.StringArrayValueNode;

public class ForLoopTest
{
    IVariableHolder vars;
    ASMClassLoader classloader;

    @Before
    public void setup()
    {
        vars = new VariableScope();
        IGraphCompilerFactory factory = new GraphCompilerFactory();
        factory.registerCompiler(INodeGraph.class, new NodeGraphCompiler());
        classloader = new ASMClassLoader(this.getClass().getClassLoader(), factory);
    }

    @Test
    public void test() throws Exception
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(out);

        StringArrayValueNode array = new StringArrayValueNode("Hel", "lo ", "Wor", "ld");
        PrintNode print = new PrintNode();
        
        ForEachLoopNode forloop = new ForEachLoopNode(print, String.class);
        forloop.mapInput("array", array.getOutput("value"));
        print.mapInput("msg", forloop.getOutput("next"));
        forloop.setBody(print);
        
        INodeGraph tree = new NodeGraph("Test Graph");
        tree.setStartNode(forloop);
        @SuppressWarnings("unchecked")
        Class<? extends IRunnableGraph> compiled = (Class<? extends IRunnableGraph>) classloader.getCompiler(INodeGraph.class).compile(classloader, tree);
        
        IRunnableGraph graph = compiled.newInstance();
        graph.run(vars);

        String s = new String(baos.toByteArray());
        s = s.replace("\n", "");
        s = s.replace("\r", "");
        assertEquals("Hello World", s);
        System.setOut(oldOut);
    }
    
}
