package com.thevoxelbox.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.nodes.vars.VariableGetNode;
import com.thevoxelbox.vsl.nodes.vars.VariableSetNode;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.RuntimeState;
import com.thevoxelbox.vsl.variables.ParentedVariableScope;

/**
 * A set of tests for the variable nodes.
 */
public class VariableNodeTest
{
    
    Node mockNode;
    
    @Before
    public void setup()
    {
        this.mockNode = Mockito.mock(Node.class);
    }

    /**
     * 
     */
    @Test
    public void testVariableGetNode()
    {
        ParentedVariableScope vars = new ParentedVariableScope();
        vars.set("a", "b");
        RuntimeState state = new RuntimeState(vars);

        VariableGetNode<String> get = new VariableGetNode<String>("a");
        get.exec(state);

        assertEquals("b", get.getValue().get(state));
    }

    /**
     * 
     */
    @Test
    public void testVariableGetNode2()
    {
        ParentedVariableScope vars = new ParentedVariableScope();
        vars.set("a", "b");
        RuntimeState state = new RuntimeState(vars);
        Provider<String> target = new Provider<String>(this.mockNode, "a");

        VariableGetNode<String> get = new VariableGetNode<String>(target);
        get.exec(state);

        assertEquals("b", get.getValue().get(state));
    }

    /**
     * 
     */
    @Test
    public void testVariableSetNode1()
    {
        ParentedVariableScope vars = new ParentedVariableScope();
        RuntimeState state = new RuntimeState(vars);

        VariableSetNode<String> get = new VariableSetNode<String>("a", "b");
        get.exec(state);

        assertEquals("b", vars.get("a").get());
    }

    /**
     * 
     */
    @Test
    public void testVariableSetNode2()
    {
        ParentedVariableScope vars = new ParentedVariableScope();
        RuntimeState state = new RuntimeState(vars);

        Provider<String> value = new Provider<String>(this.mockNode, "b");

        VariableSetNode<String> get = new VariableSetNode<String>("a", value);
        get.exec(state);

        assertEquals("b", vars.get("a").get());
    }

    /**
     * 
     */
    @Test
    public void testVariableSetNode3()
    {
        ParentedVariableScope vars = new ParentedVariableScope();
        RuntimeState state = new RuntimeState(vars);

        Provider<String> target = new Provider<String>(this.mockNode, "a");

        VariableSetNode<String> get = new VariableSetNode<String>(target, "b");
        get.exec(state);

        assertEquals("b", vars.get("a").get());
    }

    /**
     * 
     */
    @Test
    public void testVariableSetNode4()
    {
        ParentedVariableScope vars = new ParentedVariableScope();
        RuntimeState state = new RuntimeState(vars);

        Provider<String> target = new Provider<String>(this.mockNode, "a");
        Provider<String> value = new Provider<String>(this.mockNode, "b");

        VariableSetNode<String> get = new VariableSetNode<String>(target, value);
        get.exec(state);

        assertEquals("b", vars.get("a").get());
    }
}
