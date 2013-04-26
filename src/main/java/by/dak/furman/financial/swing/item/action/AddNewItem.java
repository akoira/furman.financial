package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.Item;
import by.dak.furman.financial.swing.item.ItemNode;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 9:50 PM
 */
public class AddNewItem extends AItemAction
{
    @Override
    protected void makeAction()
    {
        Item item = new Item();
        ItemNode node = new ItemNode();
        node.setProperties(createProperties(item));
        node.setValue(item);

        getModel().insertNodeInto(node, getRootNode(), 0);
    }
}
