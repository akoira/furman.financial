package by.dak.furman.financial.swing;

import by.dak.common.swing.treetable.ATreeTableNode;
import by.dak.furman.financial.service.IItemService;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 4:14 PM
 */
public abstract class ARefreshAction<P extends ATreeTablePanel, N extends ATreeTableNode, V, C extends ATreeTableNode> extends AAction<P, N>
{

    @Override
    protected void before()
    {
        removeNodes();
    }

    @Override
    protected void makeAction()
    {
        List<V> values = getChildValues();
        for (V value : values)
        {
            C childNode = createChildNode();
            childNode.setValue(value);
            getNode().fillChildNode(childNode);
            getModel().insertNodeInto(childNode, getNode(), getNode().getChildCount());
            refreshChildNode(childNode);
        }
    }

    protected void removeNodes()
    {
        List<ATreeTableNode> nodes = Collections.list((Enumeration<ATreeTableNode>) getNode().children());
        for (ATreeTableNode node : nodes)
        {
            getModel().removeNodeFromParent(node);
        }
    }

    public IItemService getItemService()
    {
        return getPanel().getAppConfig().getItemService();
    }

    public abstract List<V> getChildValues();

    public abstract C createChildNode();

    public abstract void refreshChildNode(C childNode);
}
