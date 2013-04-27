package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.Item;
import by.dak.furman.financial.swing.item.ItemNode;
import by.dak.furman.financial.swing.item.ItemTypeNode;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 3:14 PM
 */
public class RefreshItemTypeNode extends AIRefreshAction<ItemTypeNode, Item, ItemNode>
{
    @Override
    public List<Item> getChildValues()
    {
        return getItemService().getAllBy(getSearchFilter(getNode()));
    }

    @Override
    public ItemNode createChildNode()
    {
        return new ItemNode();
    }

    @Override
    public void refreshChildNode(ItemNode childNode)
    {
    }
}
