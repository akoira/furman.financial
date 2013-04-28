package by.dak.furman.financial.swing.item.action;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.swing.ARefreshAction;
import by.dak.furman.financial.swing.item.AINode;
import by.dak.furman.financial.swing.item.ItemsPanel;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 11:29 PM
 */
public abstract class AIRefreshAction<N extends AINode, V, C extends AINode> extends ARefreshAction<ItemsPanel, N, V, C>
{

    protected SearchFilter getSearchFilter(AINode childNode)
    {
        return getItemService().getSearchFilter(childNode.getCategory(),
                childNode.getItemType(), childNode.getPeriod());
    }

    @Override
    public void reloadNode()
    {
        getNode().setAmount(getItemService().getSumBy(getItemService().getSearchFilter(getNode().getCategory(), getNode().getItemType(), getNode().getPeriod())));
    }
}
