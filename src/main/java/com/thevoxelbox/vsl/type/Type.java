package com.thevoxelbox.vsl.type;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;
import com.thevoxelbox.vsl.node.Node;

/**
 * A factory for {@link Node} input/output types.
 */
public class Type implements Serializable
{
    private static final long serialVersionUID = 3262111026913495545L;

    public static Type INTEGER = new Type("INTEGER", "java/lang/Integer", TypeDepth.SINGLE);
    public static Type FLOAT = new Type("FLOAT", "java/lang/Double", TypeDepth.SINGLE);
    public static Type STRING = new Type("STRING", "java/lang/String", TypeDepth.SINGLE);
    public static Type BOOLEAN = new Type("BOOLEAN", "java/lang/Boolean", TypeDepth.SINGLE);
    public static Type OBJECT = new Type("OBJECT", "java/lang/Object", TypeDepth.SINGLE);

    /**
     * A Map of registered types.
     */
    private static Map<String, Type> types = new HashMap<String, Type>();

    static
    {
        types.put("INTEGER:SINGLE", INTEGER);
        types.put("FLOAT:SINGLE", FLOAT);
        types.put("STRING:SINGLE", STRING);
        types.put("BOOLEAN:SINGLE", BOOLEAN);
        types.put("OBJECT:SINGLE", OBJECT);
    }

    /**
     * Registers a type with this factory.
     * 
     * @param name the name of the type, cannot be null or empty
     * @param internal the internal name of the type, cannot be null or empty
     */
    public static void registerType(String name, String internal)
    {
        checkNotNull(name, "Name cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        checkNotNull(internal, "Internal name cannot be null!");
        checkArgument(!internal.isEmpty(), "Internal name cannot be empty");
        types.put(name.toUpperCase() + ":" + TypeDepth.SINGLE.name(), new Type(name, internal, TypeDepth.SINGLE));
    }

    /**
     * Fetches a Type from the factory.
     * 
     * @param name the name of the type to fetch, will be converted to all upper case, cannot be null or empty
     * @param depth the {@link TypeDepth} of the type
     * @return the type
     */
    public static Optional<Type> getType(String name, TypeDepth depth)
    {
        checkNotNull(name, "Name cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        checkNotNull(depth, "TypeDepth cannot be null!");
        name = name.toUpperCase();
        if (types.containsKey(name + ":" + depth.name()))
        {
            return Optional.of(types.get(name + ":" + depth.name()));
        } else if (types.containsKey(name + ":" + TypeDepth.SINGLE))
        {
            types.put(name + ":" + depth.name(), new Type(name, types.get(name + ":" + TypeDepth.SINGLE).getInternalName(), depth));
            return Optional.of(types.get(name + ":" + depth.name()));
        }
        return Optional.absent();
    }

    /**
     * The name of the type.
     */
    private String name;
    /**
     * The internal name of the type, forward-slash separated.
     */
    private String internalName;
    /**
     * The {@link TypeDepth} of this type.
     */
    private TypeDepth depth;

    /**
     * Creates a new type.
     * 
     * @param name the name of the type, cannot be null or empty
     * @param internal the internal name of the type, cannot be null or empty
     * @param depth the {@link TypeDepth}, cannot be null
     */
    private Type(String name, String internal, TypeDepth depth)
    {
        checkNotNull(name, "Name cannot be null!");
        checkArgument(!name.isEmpty(), "Name cannot be empty");
        checkNotNull(internal, "Internal name cannot be null!");
        checkArgument(!internal.isEmpty(), "Internal name cannot be empty");
        checkNotNull(depth, "TypeDepth cannot be null!");
        this.name = name;
        this.internalName = internal;
        this.depth = depth;
    }

    /**
     * Returns the name of the type.
     * 
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the internal name of the type.
     * 
     * @return the internal name
     */
    public String getInternalName()
    {
        return internalName;
    }

    /**
     * Returns the {@link TypeDepth} of the type.
     * 
     * @return the depth
     */
    public TypeDepth getDepth()
    {
        return depth;
    }

    /**
     * Tests object equality.
     * 
     * @param t the object to test
     * @return whether the types are equal
     */
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

    /**
     * Returns a {@link String} representation of this type.
     * 
     * @return the string
     */
    public String toString()
    {
        return "Type(" + name + ", " + internalName + ", " + depth.name() + ")";
    }

}
