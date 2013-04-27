package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.Item;
import by.dak.furman.financial.swing.item.ItemNode;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 9:50 PM
 */
public class AddNewItem extends AIAction
{

    @Override
    protected void makeAction()
    {
        Item item = new Item();
        item.setCategory(getParentNode().getCategory());
        item.setItemType(getParentNode().getItemType());

        ItemNode node = new ItemNode();
        getParentNode().fillChildNode(node);
        node.setValue(item);

        getModel().insertNodeInto(node, getParentNode(), getParentNode().getChildCount());
    }

}

