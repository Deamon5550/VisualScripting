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
import com.thevoxelbox.vsl.classloader.ASMClassLoader;
import com.thevoxelbox.vsl.classloader.GraphCompilerFactory;
import com.thevoxelbox.vsl.classloader.NodeGraphCompiler;
import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.error.InvalidNodeTypeException;
import com.thevoxelbox.vsl.node.NodeGraph;
import com.thevoxelbox.vsl.node.debug.PrintNode;
import com.thevoxelbox.vsl.node.string.ToStringNode;
import com.thevoxelbox.vsl.node.variables.StringValueNode;
import com.thevoxelbox.vsl.node.variables.VariableGetNode;
import com.thevoxelbox.vsl.node.variables.VariableSetNode;

public class VariableScopeTest
{
    private VariableScope parent;
    private VariableScope child;
    private VariableScope child2;
    
    ASMClassLoader classloader;

    @Before
    public void setup()
    {
        IGraphCompilerFactory factory = new GraphCompilerFactory();
        factory.registerCompiler(INodeGraph.class, new NodeGraphCompiler());
        classloader = new ASMClassLoader(this.getClass().getClassLoader(), factory);
        
        parent = new VariableScope();
        child = new VariableScope(parent);
        child2 = new VariableScope(child);

        parent.set("a", "a");
        parent.set("b", "b");

        child.set("b", "c");
    }

    @Test
    public void testInheritence()
    {
        assertEquals("a", child.get("a"));
        assertEquals("c", child.get("b"));
    }

    @Test
    public void testParenthood()
    {
        assertEquals(child, child2.getParent());
        assertEquals(parent, child2.getHighestParent());
    }

    @Test
    public void testVariableNodes() throws NullPointerException, InvalidNodeTypeException, GraphCompilationException, InstantiationException,
            IllegalAccessException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(out);

        StringValueNode value = new StringValueNode("Hello World");
        VariableSetNode set = new VariableSetNode("name", String.class);
        set.mapInput("value", value.getOutput("value"));
        VariableGetNode get = new VariableGetNode("name", String.class);
        ToStringNode toString = new ToStringNode();
        toString.mapInput("value", get.getOutput("value"));
        PrintNode print = new PrintNode();
        print.mapInput("msg", toString.getOutput("result"));
        set.setNextNode(print);

        INodeGraph tree = new NodeGraph("Test Graph");
        tree.setStartNode(set);
        @SuppressWarnings("unchecked")
        Class<? extends IRunnableGraph> compiled = (Class<? extends IRunnableGraph>) classloader.getCompiler(INodeGraph.class).compile(classloader, tree);
        IRunnableGraph graph = compiled.newInstance();
        graph.run(child);

        String s = new String(baos.toByteArray());
        s = s.replace("\n", "");
        s = s.replace("\r", "");
        assertEquals("Hello World", s);
        System.setOut(oldOut);
    }
}
