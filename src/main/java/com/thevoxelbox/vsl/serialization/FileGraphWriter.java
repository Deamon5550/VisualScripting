package com.thevoxelbox.vsl.serialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;
import com.thevoxelbox.vsl.api.node.NodeGraph;
import com.thevoxelbox.vsl.api.serialization.GraphWriter;
import com.thevoxelbox.vsl.error.InvalidNodeException;

/**
 * A {@link GraphWriter} which outputs to a {@link File}.
 */
public class FileGraphWriter implements GraphWriter
{

    private static final int FORMAT_VERSION = 1;

    private PrintWriter outputStream;

    /**
     * Creates a new {@link FileGraphWriter}.
     * 
     * @param output The file to output to
     * @throws FileNotFoundException If the file cannot be found
     */
    public FileGraphWriter(File output) throws FileNotFoundException
    {
        this.outputStream = new PrintWriter(output);
    }

    /**
     * Creates a new {@link FileGraphWriter}.
     * 
     * @param writer The writer to output to
     */
    public FileGraphWriter(PrintWriter writer)
    {
        this.outputStream = writer;
    }

    /**
     * Creates a new {@link FileGraphWriter}.
     * 
     * @param stream The {@link PrintStream} to output to
     */
    public FileGraphWriter(PrintStream stream)
    {
        this.outputStream = new PrintWriter(stream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(NodeGraph graph) throws IOException, InvalidNodeException
    {
        DirectedGraphRepresentation rep = new DirectedGraphRepresentation(graph);
        this.outputStream.println("#version " + FORMAT_VERSION);
        this.outputStream.println("name " + graph.getName());
        writeDigraphToStream(rep);
    }

    private void writeDigraphToStream(DirectedGraphRepresentation rep)
    {
        writeNodeDict(rep);
        writeNodeInstances(rep);
        writeEdges(rep);
    }

    private void writeEdges(DirectedGraphRepresentation rep)
    {
        for (EdgeRepresentation e : rep.getEdges())
        {
            this.outputStream.println("e " + e.getOutputNode() + ":" + e.getOutputIndex() + " " + e.getInputNode() + ":" + e.getInputIndex());
        }
    }

    private void writeNodeInstances(DirectedGraphRepresentation rep)
    {
        String s = "";
        for (NodeInstanceRepresentation r : rep.getInstances())
        {
            if (!s.isEmpty())
            {
                s += " ";
            }
            s += r.getRepresentation().getIndex();
        }
        this.outputStream.println("i " + s);
    }

    private void writeNodeDict(DirectedGraphRepresentation rep)
    {
        for (NodeRepresentation node : rep.getOrderedDict())
        {
            String s = "p " + node.getName();
            List<IORepresentation> io = Lists.newArrayList();
            for (InputRepresentation i : node.getRawInputs())
            {
                io.add(i);
            }
            for (OutputRepresentation o : node.getRawOutputs())
            {
                io.add(o);
            }
            Collections.sort(io, new Comparator<IORepresentation>()
            {

                @Override
                public int compare(IORepresentation o1, IORepresentation o2)
                {
                    return o1.getIndex() - o2.getIndex();
                }

            });
            for (IORepresentation i : io)
            {
                s += " " + i.getName();
            }
            this.outputStream.println(s);
        }
    }

    /**
     * 
     */
    @Override
    public void close() throws IOException
    {
        this.outputStream.close();
    }

    /**
     * 
     */
    @Override
    public void flush() throws IOException
    {
        this.outputStream.flush();
    }

}
