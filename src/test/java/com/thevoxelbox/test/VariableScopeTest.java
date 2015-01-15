/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 The VoxelBox
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.thevoxelbox.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.base.Optional;
import com.thevoxelbox.vsl.VariableScope;
import com.thevoxelbox.vsl.api.IVariableHolder;
import com.thevoxelbox.vsl.nodes.vars.VariableGetNode;
import com.thevoxelbox.vsl.nodes.vars.VariableSetNode;
import com.thevoxelbox.vsl.util.Provider;
import com.thevoxelbox.vsl.util.RuntimeState;

/**
 * Sets for a {@link VariableScope}.
 */
public class VariableScopeTest extends StandardTest
{

    /**
     * 
     */
    @Test
    public void testInheritence()
    {
        VariableScope parent = new VariableScope();
        VariableScope child = new VariableScope(parent);
        parent.set("a", "a");
        child.set("b", "c");

        assertEquals("a", child.get("a").get());
        assertEquals("c", child.get("b").get());
    }

    /**
     * 
     */
    @Test
    public void testInheritence2()
    {
        VariableScope parent = new VariableScope();
        VariableScope child = new VariableScope();
        parent.set("a", "a");
        child.set("b", "c");
        child.setParent(parent);

        assertEquals("a", child.get("a").get());
        assertEquals("c", child.get("b").get());
    }

    /**
     * 
     */
    @Test
    public void testParenthood()
    {
        VariableScope parent = new VariableScope();
        VariableScope child = new VariableScope(parent);
        VariableScope child2 = new VariableScope(child);

        assertEquals(child, child2.getParent().get());
        assertEquals(parent, child.getParent().get());
        assertEquals(parent, child2.getHighestParent().get());
    }

    /**
     * 
     */
    @Test
    public void testParenthood2()
    {
        IVariableHolder parent = Mockito.mock(IVariableHolder.class);
        VariableScope child = new VariableScope(parent);
        VariableScope child2 = new VariableScope(child);

        assertEquals(child, child2.getParent().get());
        assertEquals(parent, child.getParent().get());
        assertEquals(parent, child2.getHighestParent().get());
    }

    /**
     * 
     */
    @Test
    public void testParenthood3()
    {
        VariableScope parent = new VariableScope();

        assertEquals(false, parent.getParent().isPresent());
        assertEquals(false, parent.getHighestParent().isPresent());
    }

    /**
     * 
     */
    @Test
    public void testParenthoodAbsent()
    {
        VariableScope parent = new VariableScope();

        assertEquals(Optional.absent(), parent.getParent());
    }

    /**
     * 
     */
    @Test
    public void testCaseSensitivity()
    {
        VariableScope parent = new VariableScope();
        parent.setCaseSensitive(false);
        parent.set("aString", "Hello");
        assertEquals("Hello", parent.get("astring").get());
    }

    @Test
    public void testAbsentGet1()
    {
        VariableScope parent = new VariableScope();
        assertEquals(false, parent.get(null).isPresent());
    }

    @Test
    public void testAbsentGet2()
    {
        VariableScope parent = new VariableScope();
        assertEquals(false, parent.get("").isPresent());
    }

    @Test
    public void testAbsentGet3()
    {
        VariableScope parent = new VariableScope();
        assertEquals(false, parent.get("ANonExistantKey").isPresent());
    }

    @Test
    public void testHasValue()
    {
        VariableScope parent = new VariableScope();
        parent.set("a", "b");

        assertEquals(true, parent.hasValue("a"));
    }

    @Test
    public void testHasValue2()
    {
        VariableScope parent = new VariableScope();

        assertEquals(false, parent.hasValue("a"));
    }

    @Test
    public void testParentHasValue()
    {
        VariableScope parent = new VariableScope();
        VariableScope child = new VariableScope(parent);
        parent.set("a", "a");

        assertEquals(true, child.hasValue("a"));
    }

    @Test
    public void testHasValueCaseInsensitive()
    {
        VariableScope parent = new VariableScope();
        parent.setCaseSensitive(false);
        parent.set("a", "b");

        assertEquals(true, parent.hasValue("A"));
    }

    @Test
    public void testTypedGet()
    {
        VariableScope parent = new VariableScope();
        parent.set("a", "b");

        assertEquals("b", parent.get("a", String.class).get());
    }

    @Test
    public void testTypedGet2()
    {
        VariableScope parent = new VariableScope();

        assertEquals(false, parent.get("a", String.class).isPresent());
    }

    @Test
    public void testTypedGetCased()
    {
        VariableScope parent = new VariableScope();
        parent.setCaseSensitive(false);
        parent.set("a", "b");

        assertEquals("b", parent.get("A", String.class).get());
    }

    @Test
    public void testParentedTypedGet()
    {
        VariableScope parent = new VariableScope();
        VariableScope child = new VariableScope(parent);
        parent.set("a", "b");

        assertEquals("b", child.get("a", String.class).get());
    }

    @Test
    public void testTypedGetNull()
    {
        VariableScope parent = new VariableScope();

        assertEquals(false, parent.get(null, String.class).isPresent());
    }

    @Test
    public void testTypedGetNull2()
    {
        VariableScope parent = new VariableScope();
        parent.set("a", "b");

        assertEquals("b", parent.get("a", null).get());
    }

    @Test
    public void testTypedGetEmpty()
    {
        VariableScope parent = new VariableScope();

        assertEquals(false, parent.get("", String.class).isPresent());
    }

    @Test
    public void testKeyset()
    {
        VariableScope parent = new VariableScope();
        parent.set("a", "b");

        assertEquals(1, parent.keyset().size());
        assertEquals(true, parent.keyset().contains("a"));
    }

    @Test
    public void testParentedKeyset()
    {
        VariableScope parent = new VariableScope();
        VariableScope child = new VariableScope(parent);
        parent.set("a", "b");
        child.set("b", "d");

        assertEquals(2, child.keyset().size());
    }

    @Test
    public void testParentedKeyset2()
    {
        VariableScope parent = new VariableScope();
        VariableScope child = new VariableScope(parent);
        parent.set("a", "b");
        child.set("a", "d");
        child.set("b", "d");

        assertEquals(2, child.keyset().size());
    }
}
