package by.dak.furman.financial.swing.item;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.swing.ATreeTableNode;
import by.dak.furman.financial.swing.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 11:49 AM
 */
public abstract class AINode<V> extends ATreeTableNode<V, AINode>
{
    private ItemType itemType;
    private Date created;


    protected List<Property> createProperties(Object value)
    {
        ArrayList<Property> properties = new ArrayList<Property>();
        if (value instanceof Item)
        {
            properties.add(Property.valueOf(Item.PROPERTY_name, false));
            properties.add(Property.valueOf(Item.PROPERTY_amount, true));
            properties.add(Property.valueOf(Item.PROPERTY_created, true));
        }
        else if (value instanceof ItemType)
        {
            properties.add(Property.valueOf(Item.PROPERTY_name, true));
            properties.add(Property.valueOf(Item.PROPERTY_amount, false));
            properties.add(Property.valueOf(Item.PROPERTY_created, false));
        }
        else if (value == null || value instanceof Category)
        {
            properties.add(Property.valueOf(Item.PROPERTY_name, false));
            properties.add(Property.valueOf(Item.PROPERTY_amount, false));
            properties.add(Property.valueOf(Item.PROPERTY_created, false));
        }
        else
            throw new IllegalArgumentException();
        return properties;
    }

    @Override
    public List<String> getColumnIdentifiers()
    {
        return Arrays.asList(Item.PROPERTY_name, Item.PROPERTY_amount, Item.PROPERTY_created);
    }

    public ItemType getItemType()
    {
        return itemType;
    }

    public void setItemType(ItemType itemType)
    {
        this.itemType = itemType;
    }

    public void fillChildNode(AINode child)
    {
        if (child.getCategory() == null)
            child.setCategory(getCategory());
        if (child.getItemType() == null)
            child.setItemType(getItemType());
        if (child.getPeriod() == null)
            child.setPeriod(getPeriod());
        child.setProperties(createProperties(child.getValue()));
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }
}
