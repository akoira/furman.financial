package by.dak.common.swing.treetable;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 9:33 PM
 */
public class Property
{
    private String key;
    private boolean editable;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public boolean isEditable()
    {
        return editable;
    }

    public void setEditable(boolean editable)
    {
        this.editable = editable;
    }

    public static Property valueOf(String key, boolean editable)
    {
        Property property = new Property();
        property.setEditable(editable);
        property.setKey(key);
        return property;
    }
}
