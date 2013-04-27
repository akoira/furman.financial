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
public class RefreshItemTypeNode extends ARefreshAction<ItemTypeNode>
{
    @Override
    protected void makeAction()
    {
        removeNodes();

        List<Item> items = getItemsPanel().getAppConfig().getItemService().getAll();
        for (Item item : items)
        {
            ItemNode itemNode = new ItemNode();
            getParentNode().fillChildNode(itemNode);
            itemNode.setValue(item);

            getModel().insertNodeInto(itemNode, getParentNode(), getParentNode().getChildCount());
        }
    }
}
