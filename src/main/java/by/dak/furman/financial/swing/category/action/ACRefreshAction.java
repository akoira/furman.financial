package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.swing.ARefreshAction;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.category.CategoriesPanel;

/**
 * User: akoyro
 * Date: 4/28/13
 * Time: 9:06 PM
 */
public abstract class ACRefreshAction<N extends ACNode, V, C extends ACNode> extends ARefreshAction<CategoriesPanel, N, V, C>
{
    @Override
    public void reloadNode()
    {
        getNode().setAmount(getItemService().getSumBy(getItemService().getSearchFilter(getNode().getCategory(), null, getNode().getPeriod())));
    }
}
