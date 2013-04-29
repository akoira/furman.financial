package by.dak.common.lang;

import org.jdesktop.application.ResourceMap;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 27.12.2008
 * Time: 18:44:51
 */
public interface ToStringConverter<E> extends Converter<E, String>
{
    @Override
    String convert(E entity);

    Icon convert2Icon(E entity);

    public ResourceMap getResourceMap();

    public void setResourceMap(ResourceMap resourceMap);
}
