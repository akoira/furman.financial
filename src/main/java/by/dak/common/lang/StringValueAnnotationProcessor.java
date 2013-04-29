package by.dak.common.lang;

import org.apache.commons.lang3.StringUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.lang.annotation.Annotation;
import java.util.HashMap;

public class StringValueAnnotationProcessor
{
    private final static StringValueAnnotationProcessor PROCESSOR = new StringValueAnnotationProcessor();

    private HashMap<Class, ToStringConverter> converterMap = new HashMap<Class, ToStringConverter>();

    public static StringValueAnnotationProcessor getProcessor()
    {
        return PROCESSOR;
    }


    public static class DefaultConverter implements ToStringConverter
    {
        public String convert(Object entity)
        {

            return entity != null ? entity.toString() : StringUtils.EMPTY;
        }

        @Override
        public Icon convert2Icon(Object entity)
        {
            return null;
        }

        @Override
        public ResourceMap getResourceMap()
        {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setResourceMap(ResourceMap resourceMap)
        {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    public <T> String convert(T entity)
    {
        if (entity != null)
        {
            return getEntityToStringConverter(entity.getClass()).convert(entity);
        }
        else
        {
            return Converter.NULL_STRING;
        }
    }

    public <T> Icon convert2Icon(T entity)
    {
        if (entity != null)
        {
            return getEntityToStringConverter(entity.getClass()).convert2Icon(entity);
        }
        else
        {
            return null;
        }
    }

    public ToStringConverter getEntityToStringConverter(Class annotatedClass)
    {
        ToStringConverter toStringConverter = converterMap.get(annotatedClass);
        if (toStringConverter != null)
        {
            return toStringConverter;
        }
        else
        {
            toStringConverter = createConverter(annotatedClass);
            converterMap.put(annotatedClass, toStringConverter);
            return toStringConverter;
        }
    }

    private ToStringConverter createConverter(Class annotatedClass)
    {
        Annotation annotation = annotatedClass.getAnnotation(StringValue.class);

        if (annotation == null)
        {
            return new DefaultConverter();
        }
        else
        {
            try
            {
                Class converter = ((StringValue) annotation).converterClass();
                ToStringConverter toStringConverter = (ToStringConverter) converter.newInstance();
                toStringConverter.setResourceMap(Application.getInstance().getContext().getResourceMap(converter));
                return toStringConverter;
            }
            catch (InstantiationException e)
            {
                throw new IllegalArgumentException(e);
            }
            catch (IllegalAccessException e)
            {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
