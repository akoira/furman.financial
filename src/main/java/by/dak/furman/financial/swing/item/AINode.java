package by.dak.furman.financial.swing.item;

import by.dak.common.swing.treetable.ATreeTableNode;
import by.dak.common.swing.treetable.Property;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.Period;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 11:49 AM
 */
public abstract class AINode<V> extends ATreeTableNode<V, AINode>
{
    private Category category;
    private ItemType itemType;
    private Period period;

    public abstract void setName(String name);

    public abstract String getName();

    public abstract BigDecimal getAmount();

    public abstract void setAmount(BigDecimal amount);

    public abstract Date getCreated();

    public abstract void setCreated(Date created);

    public static List<Property> createProperties(Object value)
    {
        ArrayList<Property> properties = new ArrayList<Property>();
        if (value instanceof Item)
        {
            properties.add(Property.valueOf(Item.PROPERTY_name, true));
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


    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
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
        child.setCategory(getCategory());
        child.setItemType(getItemType());
        child.setPeriod(getPeriod());
        child.setProperties(createProperties(child.getValue()));
    }

    public void setPeriod(Period period)
    {
        this.period = period;
    }

    public Period getPeriod()
    {
        return period;
    }
}
