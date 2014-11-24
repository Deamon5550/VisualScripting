package com.thevoxelbox.vsl.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Type implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 3262111026913495545L;
    public static Type INTEGER = new Type("INTEGER", "java/lang/Integer", TypeDepth.SINGLE);
    public static Type FLOAT = new Type("FLOAT", "java/lang/Double", TypeDepth.SINGLE);
    public static Type STRING = new Type("STRING", "java/lang/String", TypeDepth.SINGLE);
    public static Type BOOLEAN = new Type("BOOLEAN", "java/lang/Boolean", TypeDepth.SINGLE);
    public static Type OBJECT = new Type("OBJECT", "java/lang/Object", TypeDepth.SINGLE);

    static List<String> types = new ArrayList<String>();

    static
    {
        types.add("INTEGER");
        types.add("FLOAT");
        types.add("STRING");
        types.add("BOOLEAN");
        types.add("OBJECT");
    }

    public static void registerType(String name)
    {
        types.add(name.toUpperCase());
    }

    public static Type getType(String name, String internal, TypeDepth depth)
    {
        name = name.toUpperCase();
        if (types.contains(name))
        {
            return new Type(name, internal, depth);
        }
        return null;
    }

    private String name;
    private String internalName;
    private TypeDepth depth;

    protected Type(String name, String internal, TypeDepth depth)
    {
        this.name = name;
        this.internalName = internal;
        this.depth = depth;
    }

    public String getName()
    {
        return name;
    }

    public String getInternalName()
    {
        return internalName;
    }

    public TypeDepth getDepth()
    {
        return depth;
    }

    @Override
    public boolean equals(Object t)
    {
        if (t instanceof Type)
        {
            Type other = (Type) t;
            if (other.name.equalsIgnoreCase(this.name) && other.depth == this.depth && other.internalName.equalsIgnoreCase(this.internalName))
            {
                return true;
            }
        }
        return false;
    }

    public String toString()
    {
        return "Type(" + name + ", " + internalName + ", " + depth.name() + ")";
    }

}
