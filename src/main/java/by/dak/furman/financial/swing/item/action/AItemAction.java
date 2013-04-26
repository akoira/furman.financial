package by.dak.furman.financial.swing.item.action;

import by.dak.common.swing.treetable.Property;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.swing.item.ItemsPanel;
import by.dak.furman.financial.swing.item.RootNode;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:09 PM
 */
public abstract class AItemAction
{
    private ItemsPanel itemsPanel;

    private ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(getClass());

    public void action()
    {
        before();
        if (validate())
            makeAction();
        after();
    }

    protected void after()
    {
    }

    protected void before()
    {
    }

    protected boolean validate()
    {
        return true;
    }

    protected abstract void makeAction();

    public DefaultTreeTableModel getModel()
    {
        return itemsPanel.getModel();
    }

    public RootNode getRootNode()
    {
        return (RootNode) getModel().getRoot();
    }

    public static List<Property> createProperties(Object value)
    {
        ArrayList<Property> properties = new ArrayList<Property>();
        if (value instanceof Item)
        {
            properties.add(Property.valueOf(Item.PROPERTY_itemType, true));
            properties.add(Property.valueOf(Item.PROPERTY_amount, true));
            properties.add(Property.valueOf(Item.PROPERTY_created, true));
        }
        else if (value == null || value instanceof ItemType)
        {
            properties.add(Property.valueOf(Item.PROPERTY_itemType, false));
            properties.add(Property.valueOf(Item.PROPERTY_amount, false));
            properties.add(Property.valueOf(Item.PROPERTY_created, false));
        }
        else
            throw new IllegalArgumentException();
        return properties;
    }

    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public ResourceMap getResourceMap(Class aClass)
    {
        return Application.getInstance().getContext().getResourceMap(aClass);
    }

    public ItemsPanel getItemsPanel()
    {
        return itemsPanel;
    }

    public void setItemsPanel(ItemsPanel itemsPanel)
    {
        this.itemsPanel = itemsPanel;
    }
}
