package com.thevoxelbox.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.thevoxelbox.vsl.VariableScope;
import com.thevoxelbox.vsl.api.IChainableNodeGraph;
import com.thevoxelbox.vsl.api.IChainedRunnableGraph;
import com.thevoxelbox.vsl.api.IGraphCompilerFactory;
import com.thevoxelbox.vsl.api.IRunnableGraph;
import com.thevoxelbox.vsl.api.IVariableHolder;
import com.thevoxelbox.vsl.classloader.ASMClassLoader;
import com.thevoxelbox.vsl.classloader.ChainableGraphCompiler;
import com.thevoxelbox.vsl.classloader.GraphCompilerFactory;
import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.error.InvalidNodeTypeException;
import com.thevoxelbox.vsl.node.ChainableNodeGraph;
import com.thevoxelbox.vsl.node.debug.PrintNode;
import com.thevoxelbox.vsl.node.variables.ChainedInputNode;
import com.thevoxelbox.vsl.node.variables.ChainedOutputNode;
import com.thevoxelbox.vsl.node.variables.StringValueNode;

public class ChainedNodeGraphTest
{
    IVariableHolder vars;
    ASMClassLoader classloader;

    @Before
    public void setup()
    {
        vars = new VariableScope();
        IGraphCompilerFactory factory = new GraphCompilerFactory();
        factory.registerCompiler(IChainableNodeGraph.class, new ChainableGraphCompiler());
        classloader = new ASMClassLoader(this.getClass().getClassLoader(), factory);
    }

    @Test
    public void test() throws NullPointerException, InvalidNodeTypeException, InstantiationException, IllegalAccessException, GraphCompilationException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(out);

        StringValueNode string = new StringValueNode("Hello");
        StringValueNode string2 = new StringValueNode(" World");
        ChainedOutputNode out1 = new ChainedOutputNode("first", String.class);
        ChainedOutputNode out2 = new ChainedOutputNode("second", String.class);
        out1.mapInput("value", string.getOutput("value"));
        out2.mapInput("value", string2.getOutput("value"));
        out1.setNextNode(out2);

        IChainableNodeGraph tree1 = new ChainableNodeGraph("Chained Graph 1");
        tree1.setStartNode(out1);
        @SuppressWarnings("unchecked")
        Class<? extends IChainedRunnableGraph> chain1 = (Class<? extends IChainedRunnableGraph>) classloader.getCompiler(IChainableNodeGraph.class).compile(classloader, tree1);
        
        ChainedInputNode in1 = new ChainedInputNode("first", String.class);
        ChainedInputNode in2 = new ChainedInputNode("second", String.class);
        PrintNode print = new PrintNode();
        PrintNode print2 = new PrintNode();
        print.mapInput("msg", in1.getOutput("value"));
        print2.mapInput("msg", in2.getOutput("value"));
        print.setNextNode(print2);
        
        IChainableNodeGraph tree2 = new ChainableNodeGraph("Chained Graph 2");
        tree2.setStartNode(print);
        @SuppressWarnings("unchecked")
        Class<? extends IChainedRunnableGraph> chain2 = (Class<? extends IChainedRunnableGraph>) classloader.getCompiler(IChainableNodeGraph.class).compile(classloader, tree2);
        
        IChainedRunnableGraph graph1 = chain1.newInstance();
        IChainedRunnableGraph graph2 = chain2.newInstance();
        graph1.chain(graph2);
        
        graph1.run(vars);

        String s = new String(baos.toByteArray());
        s = s.replace("\n", "");
        s = s.replace("\r", "");
        assertEquals("Hello World", s);
        System.setOut(oldOut);
    }
    
}
