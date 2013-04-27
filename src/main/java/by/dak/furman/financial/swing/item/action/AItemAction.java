package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.swing.item.ItemsPanel;
import by.dak.furman.financial.swing.item.RootNode;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

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
