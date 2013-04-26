package by.dak.common.lang;

import org.apache.commons.lang3.StringUtils;

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
