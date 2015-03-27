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
