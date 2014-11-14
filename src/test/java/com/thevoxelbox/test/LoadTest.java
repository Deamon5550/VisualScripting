package com.thevoxelbox.test;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thevoxelbox.vsl.api.INodeGraph;
import com.thevoxelbox.vsl.classloader.ASMClassLoader;
import com.thevoxelbox.vsl.error.GraphCompilationException;
import com.thevoxelbox.vsl.error.InvalidNodeTypeException;
import com.thevoxelbox.vsl.node.NodeGraph;
import com.thevoxelbox.vsl.node.debug.PrintNode;
import com.thevoxelbox.vsl.node.variables.StringValueNode;

public class LoadTest
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Starting load test in 5 seconds");
        Thread.sleep(5000);
        List<WeakReference<Class<?>>> classes = new ArrayList<WeakReference<Class<?>>>();
        for (int i = 0; i < 1000; i++)
        {
            ASMClassLoader.resetGlobalClassLoader();
            checkClasses(classes);
            for (int o = 0; o < 100; o++)
            {
                classes.add(new WeakReference<Class<?>>(createClass()));
            }
            System.out.println(((i + 1) * 100) + " classes created " + classes.size() + " classes still loaded");
        }
        int t = 0;
        while (true)
        {
            Thread.sleep(1000);
            checkClasses(classes);
            System.out.println(++t + " seconds after end of test " + classes.size() + " classes still loaded");
        }
    }

    public static void checkClasses(List<WeakReference<Class<?>>> classes)
    {
        for (Iterator<WeakReference<Class<?>>> it = classes.iterator(); it.hasNext();)
        {
            WeakReference<Class<?>> cls = it.next();
            if (cls.get() == null)
            {
                it.remove();
            }
        }

    }

    public static Class<?> createClass() throws NullPointerException, InvalidNodeTypeException, GraphCompilationException
    {
        StringValueNode string = new StringValueNode("Hello World");
        PrintNode print = new PrintNode();
        print.mapInput("msg", string.getOutput("value"));

        INodeGraph tree = new NodeGraph("Test Graph");
        tree.setStartNode(print);
        return tree.compile(ASMClassLoader.getGlobalClassLoader());
    }
}
