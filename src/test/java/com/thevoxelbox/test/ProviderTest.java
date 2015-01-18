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

import com.thevoxelbox.vsl.api.node.Node;
import com.thevoxelbox.vsl.error.InvalidNodeException;
import com.thevoxelbox.vsl.util.Provider;

/**
 * A set of tests for the {@link Provider} utility.
 */
public class ProviderTest extends StandardTest
{

    /**
     * 
     */
    @Test
    public void testOwner()
    {
        Node node = Mockito.mock(Node.class);
        Provider<?> p = new Provider(node);
        assertEquals(node, p.getOwner());
    }

    /**
     * 
     */
    @Test
    public void testStatic1()
    {
        Node node = Mockito.mock(Node.class);
        Provider<Object> p = new Provider<Object>(node);
        assertEquals(false, p.isStatic());
    }

    /**
     * 
     */
    @Test
    public void testStatic2()
    {
        Node node = Mockito.mock(Node.class);
        Provider<Object> p = new Provider<Object>(node, new Object());
        assertEquals(true, p.isStatic());
    }
    
}
