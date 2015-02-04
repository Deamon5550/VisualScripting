package com.thevoxelbox.vsl.util;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;

public class ReflectionHelper
{

    public static Field[] getAllFields(Class<?> cls)
    {
        List<Field> fields = Lists.newArrayList();

        Class<?> next = cls;
        while (next != null)
        {
            for (Field f : next.getDeclaredFields())
            {
                fields.add(f);
            }

            next = next.getSuperclass();
        }

        return fields.toArray(new Field[fields.size()]);
    }

    public static Field getField(Class<?> cls, String s)
    {
        Class<?> next = cls;
        Field f = null;

        while (next != null)
        {
            try
            {
                if ((f = next.getDeclaredField(s)) != null)
                {
                    f.setAccessible(true);
                    return f;
                }
            }
            catch(NoSuchFieldException ignored)
            {
                
            }

            next = next.getSuperclass();
        }
        return null;
    }

}
