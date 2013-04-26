package by.dak.furman.financial.swing.category.action;

import by.dak.common.swing.treetable.ATreeTableNode;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 2:03 PM
 */
public abstract class NodeIterator
{
    private ATreeTableNode node;

    public void iterate(ATreeTableNode node)
    {
        int count = node.getChildCount();
        for (int i = 0; i < count; i++)
        {
            ATreeTableNode child = (ATreeTableNode) node.getChildAt(i);
            if (!action(child))
                break;
            iterate(child);
        }
    }

    protected abstract boolean action(ATreeTableNode child);

}
