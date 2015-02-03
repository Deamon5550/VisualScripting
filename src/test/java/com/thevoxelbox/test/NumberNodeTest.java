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

import static com.thevoxelbox.test.util.MockUtility.mock;

import org.junit.Before;
import org.junit.Test;

import com.thevoxelbox.vsl.nodes.math.AbsNode;
import com.thevoxelbox.vsl.nodes.math.AdditionNode;
import com.thevoxelbox.vsl.nodes.math.DivisionNode;
import com.thevoxelbox.vsl.nodes.math.ModuloNode;
import com.thevoxelbox.vsl.nodes.math.MultiplicationNode;
import com.thevoxelbox.vsl.nodes.math.SqrtNode;
import com.thevoxelbox.vsl.nodes.math.SubtractionNode;

/**
 * Tests for number based nodes.
 */
public class NumberNodeTest extends StandardTest
{

    @Before
    public void setup()
    {
        super.setup();
    }

    /**
     * 
     */
    @Test
    public void testAddition()
    {

        AdditionNode add = new AdditionNode(mock(6), mock(5), false);

        add.exec(this.state);

        assertResult(11L, add.getResult());
    }

    /**
     * 
     */
    @Test
    public void testSubtraction()
    {
        SubtractionNode add = new SubtractionNode(mock(5), mock(6), false);

        add.exec(this.state);

        assertResult(-1L, add.getResult());
    }

    /**
     * 
     */
    @Test
    public void testMultiplication()
    {
        MultiplicationNode add = new MultiplicationNode(mock(6), mock(5), false);

        add.exec(this.state);

        assertResult(30L, add.getResult());
    }

    /**
     * 
     */
    @Test
    public void testDivision()
    {
        DivisionNode add = new DivisionNode(mock(14), mock(6), false);

        add.exec(this.state);

        assertResult(2L, add.getResult());
    }

    /**
     * 
     */
    @Test
    public void testModulo()
    {
        ModuloNode add = new ModuloNode(mock(8), mock(6), false);

        add.exec(this.state);

        assertResult(2L, add.getResult());
    }

    /**
     * 
     */
    @Test
    public void testSqrt()
    {
        SqrtNode add = new SqrtNode(mock(16));

        add.exec(this.state);

        assertResult(4.0, add.getResult());
    }

    /**
     * 
     */
    @Test
    public void testAbs()
    {
        AbsNode add = new AbsNode(mock(-5), false);

        add.exec(this.state);

        assertResult(5L, add.getResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatAbs()
    {
        AbsNode add = new AbsNode(mock(-5.2), true);

        add.exec(this.state);

        assertResult(5.2, add.getResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatAddition()
    {
        AdditionNode add = new AdditionNode(mock(5.2), mock(6.3), true);

        add.exec(this.state);

        assertResult(11.5, add.getResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatSubtraction()
    {
        SubtractionNode add = new SubtractionNode(mock(5.2), mock(6.2), true);

        add.exec(this.state);

        assertResult(-1.0, add.getResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatMultiplication()
    {
        MultiplicationNode add = new MultiplicationNode(mock(2.5), mock(5.0), true);

        add.exec(this.state);

        assertResult(12.5, add.getResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatDivision()
    {
        DivisionNode add = new DivisionNode(mock(7.5), mock(3.0), true);

        add.exec(this.state);

        assertResult(2.5, add.getResult());
    }

    /**
     * 
     */
    @Test
    public void testFloatModulo()
    {
        ModuloNode add = new ModuloNode(mock(5.4), mock(2.2), true);

        add.exec(this.state);

        assertResult(1.0, add.getResult());
    }

}
