package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.Item;
import by.dak.furman.financial.swing.item.ItemNode;
import by.dak.furman.financial.swing.item.ItemTypeNode;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 9:50 PM
 */
public class AddNewItem extends AIAction<ItemTypeNode>
{

    @Override
    protected void makeAction()
    {
        Item item = new Item();
        item.setItemType(getNode().getItemType());

        ItemNode node = new ItemNode();
        node.setValue(item);
        getNode().fillChildNode(node);

        getModel().insertNodeInto(node, getNode(), getNode().getChildCount());
    }

}

