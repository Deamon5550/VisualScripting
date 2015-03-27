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

import com.thevoxelbox.test.util.CheckRunNode;
import com.thevoxelbox.test.util.StringArrayCheckNode;
import com.thevoxelbox.vsl.api.runtime.GraphRuntime;
import com.thevoxelbox.vsl.nodes.StaticValueNode;
import com.thevoxelbox.vsl.nodes.control.ForEachNode;
import com.thevoxelbox.vsl.nodes.control.ForLoop;
import com.thevoxelbox.vsl.nodes.control.IfStatement;
import com.thevoxelbox.vsl.runtime.ObjectRuntime;

/**
 * Tests the control flow nodes.
 */
public class ProgramFlowTest extends StandardTest
{
    
    private GraphRuntime state;

    @Before
    public void setup()
    {
        super.setup();
        this.state = new ObjectRuntime();
    }

    /**
     * 
     */
    @Test
    public void testForEachLoop()
    {

        StaticValueNode<String[]> array = new StaticValueNode<String[]>(new String[] { "Hel", "lo ", "Wor", "ld" });

        ForEachNode<String> forloop = new ForEachNode<String>(array.getValue());
        StringArrayCheckNode check = new StringArrayCheckNode(forloop.getNextValue(), "Hel", "lo ", "Wor", "ld");
        forloop.setBody(check);

        forloop.exec(this.state);
        check.end();
    }

    /**
     * 
     */
    @Test
    public void isIf()
    {
        IfStatement ifs = new IfStatement(mock(true));

        CheckRunNode check = new CheckRunNode(1);
        ifs.setBody(check);

        ifs.exec(this.state);
        check.end();
    }

    /**
     * 
     */
    @Test
    public void isIfFalse()
    {
        IfStatement ifs = new IfStatement(mock(false));

        CheckRunNode check = new CheckRunNode(0);
        ifs.setBody(check);

        ifs.exec(this.state);
        check.end();
    }

    /**
     * 
     */
    @Test
    public void testFor1()
    {
        // Provider<Integer> init = new Provider<Integer>(0);
        // Provider<Integer> target = new Provider<Integer>(2);
        // Provider<Integer> increment = new Provider<Integer>(1);

        ForLoop foor = new ForLoop(0, 2, 1);

        CheckRunNode check = new CheckRunNode(2);
        foor.setBody(check);

        foor.exec(this.state);
        check.end();
    }

    /**
     * 
     */
    @Test
    public void testFor2()
    {
        ForLoop foor = new ForLoop(0, 2, mock(1));

        CheckRunNode check = new CheckRunNode(2);
        foor.setBody(check);

        foor.exec(this.state);
        check.end();
    }

    /**
     * 
     */
    @Test
    public void testFor3()
    {
        ForLoop foor = new ForLoop(0, mock(2), 1);

        CheckRunNode check = new CheckRunNode(2);
        foor.setBody(check);

        foor.exec(this.state);
        check.end();
    }

    /**
     * 
     */
    @Test
    public void testFor4()
    {
        ForLoop foor = new ForLoop(0, mock(2), mock(1));

        CheckRunNode check = new CheckRunNode(2);
        foor.setBody(check);

        foor.exec(this.state);
        check.end();
    }

    /**
     * 
     */
    @Test
    public void testFor5()
    {
        ForLoop foor = new ForLoop(mock(0), 2, 1);

        CheckRunNode check = new CheckRunNode(2);
        foor.setBody(check);

        foor.exec(this.state);
        check.end();
    }

    /**
     * 
     */
    @Test
    public void testFor6()
    {
        ForLoop foor = new ForLoop(mock(0), 2, mock(1));

        CheckRunNode check = new CheckRunNode(2);
        foor.setBody(check);

        foor.exec(this.state);
        check.end();
    }

    /**
     * 
     */
    @Test
    public void testFor7()
    {
        ForLoop foor = new ForLoop(mock(0), mock(2), 1);

        CheckRunNode check = new CheckRunNode(2);
        foor.setBody(check);

        foor.exec(this.state);
        check.end();
    }

    /**
     * 
     */
    @Test
    public void testFor8()
    {
        ForLoop foor = new ForLoop(mock(0), mock(2), mock(1));

        CheckRunNode check = new CheckRunNode(2);
        foor.setBody(check);

        foor.exec(this.state);
        check.end();
    }

}
