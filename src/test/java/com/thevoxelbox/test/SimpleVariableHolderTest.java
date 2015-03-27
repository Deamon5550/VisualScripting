package com.thevoxelbox.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thevoxelbox.vsl.variables.SimpleVariableHolder;

/**
 * A set of tests for the {@link SimpleVariableHolder}.
 */
public class SimpleVariableHolderTest
{

    /**
     * 
     */
    @Test
    public void testCaseSensitivity()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();
        parent.setCaseSensitive(false);
        parent.set("aString", "Hello");
        assertEquals("Hello", parent.get("astring").get());
    }

    /**
     * 
     */
    @Test
    public void testAbsentGet1()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();
        assertEquals(false, parent.get(null).isPresent());
    }

    /**
     * 
     */
    @Test
    public void testAbsentGet2()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();
        assertEquals(false, parent.get("").isPresent());
    }

    /**
     * 
     */
    @Test
    public void testAbsentGet3()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();
        assertEquals(false, parent.get("ANonExistantKey").isPresent());
    }

    /**
     * 
     */
    @Test
    public void testHasValue()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();
        parent.set("a", "b");

        assertEquals(true, parent.hasValue("a"));
    }

    /**
     * 
     */
    @Test
    public void testHasValue2()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();

        assertEquals(false, parent.hasValue("a"));
    }

    /**
     * 
     */
    @Test
    public void testHasValueCaseInsensitive()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();
        parent.setCaseSensitive(false);
        parent.set("a", "b");

        assertEquals(true, parent.hasValue("A"));
    }

    /**
     * 
     */
    @Test
    public void testTypedGet()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();
        parent.set("a", "b");

        assertEquals("b", parent.get("a", String.class).get());
    }

    /**
     * 
     */
    @Test
    public void testTypedGet2()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();

        assertEquals(false, parent.get("a", String.class).isPresent());
    }

    /**
     * 
     */
    @Test
    public void testTypedGetCased()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();
        parent.setCaseSensitive(false);
        parent.set("a", "b");

        assertEquals("b", parent.get("A", String.class).get());
    }

    /**
     * 
     */
    @Test
    public void testTypedGetNull()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();

        assertEquals(false, parent.get(null, String.class).isPresent());
    }

    /**
     * 
     */
    @Test
    public void testTypedGetNull2()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();
        parent.set("a", "b");

        assertEquals("b", parent.get("a", null).get());
    }

    /**
     * 
     */
    @Test
    public void testTypedGetEmpty()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();

        assertEquals(false, parent.get("", String.class).isPresent());
    }

    /**
     * 
     */
    @Test
    public void testKeyset()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();
        parent.set("a", "b");

        assertEquals(1, parent.keyset().size());
        assertEquals(true, parent.keyset().contains("a"));
    }
    
    /**
     * 
     */
    @Test
    public void testClear()
    {
        SimpleVariableHolder parent = new SimpleVariableHolder();
        parent.set("a", "b");

        assertEquals(true, parent.hasValue("a"));
        
        parent.clear();

        assertEquals(false, parent.hasValue("a"));
    }

}
