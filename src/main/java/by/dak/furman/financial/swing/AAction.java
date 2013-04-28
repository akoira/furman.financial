package by.dak.furman.financial.swing;

import by.dak.furman.financial.service.ICategoryService;
import by.dak.furman.financial.service.IItemService;
import by.dak.furman.financial.service.IItemTypeService;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 4:15 PM
 */
public abstract class AAction<P extends ATreeTablePanel, N extends ATreeTableNode>
{
    private P panel;
    private N node;

    private ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(getClass());


    public void action()
    {
        before();
        if (validate())
            makeAction();
        after();
    }

    public DefaultTreeTableModel getModel()
    {
        return getPanel().getModel();
    }

    public ATreeTableNode getRootNode()
    {
        return (ATreeTableNode) getModel().getRoot();
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

    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public ResourceMap getResourceMap(Class aClass)
    {
        return Application.getInstance().getContext().getResourceMap(aClass);
    }

    public P getPanel()
    {
        return panel;
    }

    public void setPanel(P panel)
    {
        this.panel = panel;
    }

    public N getNode()
    {
        return node;
    }

    public void setNode(N node)
    {
        this.node = node;
    }

    public ICategoryService getCategoryService()
    {
        return getPanel().getAppConfig().getCategoryService();
    }

    public IItemTypeService getItemTypeService()
    {
        return getPanel().getAppConfig().getItemTypeService();
    }

    public IItemService getItemService()
    {
        return getPanel().getAppConfig().getItemService();
    }
}
