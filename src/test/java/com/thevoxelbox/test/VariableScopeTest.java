package com.thevoxelbox.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.thevoxelbox.vsl.VariableScope;

public class VariableScopeTest
{
    private VariableScope parent;
    private VariableScope child;
    private VariableScope child2;
    
    @Before
    public void setup()
    {
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
}
