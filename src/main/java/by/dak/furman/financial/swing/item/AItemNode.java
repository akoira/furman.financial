package by.dak.furman.financial.swing.item;

import by.dak.common.persistence.SearchFilter;
import by.dak.common.swing.treetable.ATreeTableNode;
import by.dak.common.swing.treetable.Property;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.service.IItemService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 11:49 AM
 */
public abstract class AItemNode<V> extends ATreeTableNode<V>
{
    private IItemService itemService;

    public abstract void setName(String name);

    public abstract String getName();

    public abstract BigDecimal getAmount();

    public abstract void setAmount(BigDecimal amount);

    public abstract Date getCreated();

    public abstract void setCreated(Date created);

    public IItemService getItemService()
    {
        return itemService;
    }

    public void setItemService(IItemService itemService)
    {
        this.itemService = itemService;
    }

    public SearchFilter getSearchFilter()
    {
        AItemNode parent = (AItemNode) getParent();
        SearchFilter searchFilter;
        if (parent != null)
            searchFilter = SearchFilter.valueOf(parent.getSearchFilter());
        else
            searchFilter = SearchFilter.instanceUnbound();
        return searchFilter;
    }


    public static List<Property> createProperties(Object value)
    {
        ArrayList<Property> properties = new ArrayList<Property>();
        if (value instanceof Item)
        {
            properties.add(Property.valueOf(Item.PROPERTY_name, true));
            properties.add(Property.valueOf(Item.PROPERTY_amount, true));
            properties.add(Property.valueOf(Item.PROPERTY_created, true));
        }
        else if (value == null || value instanceof ItemType || value instanceof Category)
        {
            properties.add(Property.valueOf(Item.PROPERTY_name, false));
            properties.add(Property.valueOf(Item.PROPERTY_amount, false));
            properties.add(Property.valueOf(Item.PROPERTY_created, false));
        }
        else
            throw new IllegalArgumentException();
        return properties;
    }

}
