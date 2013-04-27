package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.swing.item.CategoryNode;
import by.dak.furman.financial.swing.item.ItemTypeNode;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 2:05 PM
 */
public class AddNewItemType extends AIAction<CategoryNode>
{
    @Override
    protected void makeAction()
    {
        ItemType itemType = new ItemType();
        itemType.setCategory(getNode().getValue());

        ItemTypeNode itemTypeNode = new ItemTypeNode();
        itemTypeNode.setValue(itemType);
        getNode().fillChildNode(itemTypeNode);
        getModel().insertNodeInto(itemTypeNode, getNode(), getNode().getChildCount());
    }


}
