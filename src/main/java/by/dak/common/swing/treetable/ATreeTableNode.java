package by.dak.common.swing.treetable;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 8:47 PM
 */
public abstract class ATreeTableNode<V> extends AbstractMutableTreeTableNode
{
    private List<Property> properties = new ArrayList<Property>();

    public V getValue()
    {
        return (V) getUserObject();
    }

    public void setValue(V value)
    {
        setUserObject(value);
    }

    @Override
    public Object getValueAt(int column)
    {
        try
        {
            Property property = properties.get(column);
            return BeanUtilsBean.getInstance().getProperty(this, property.getKey());
        }
        catch (Exception e)
        {
            return getUserObject();
        }
    }

    @Override
    public void setValueAt(Object aValue, int column)
    {
        try
        {
            Property property = properties.get(column);
            BeanUtilsBean.getInstance().setProperty(this, property.getKey(), aValue);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public int getColumnCount()
    {
        return properties.size();
    }

    @Override
    public boolean isEditable(int column)
    {
        return properties.get(column).isEditable();
    }

    public List<Property> getProperties()
    {
        return properties;
    }

    public void setProperties(List<Property> properties)
    {
        this.properties.clear();
        this.properties.addAll(properties);
    }
}
