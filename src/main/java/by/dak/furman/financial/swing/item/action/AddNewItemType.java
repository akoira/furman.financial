package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.swing.item.ItemTypeNode;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 2:05 PM
 */
public class AddNewItemType extends AIAction
{
    @Override
    protected void makeAction()
    {
        ItemType itemType = new ItemType();

        ItemTypeNode itemTypeNode = new ItemTypeNode();
        getParentNode().fillChildNode(itemTypeNode);
        itemTypeNode.setValue(itemType);
        itemTypeNode.setItemType(itemType);

        getModel().insertNodeInto(itemTypeNode, getParentNode(), getParentNode().getChildCount());
    }


}
