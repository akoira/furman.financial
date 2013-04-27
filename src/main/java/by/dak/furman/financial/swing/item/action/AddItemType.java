package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.swing.item.AItemNode;
import by.dak.furman.financial.swing.item.ItemTypeNode;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 1:43 PM
 */
public class AddItemType extends AItemAction
{
    private ItemType itemType;

    @Override
    protected void makeAction()
    {
        ItemTypeNode node = new ItemTypeNode();
        node.setValue(itemType);
        node.setProperties(AItemNode.createProperties(itemType));


    }

    public ItemType getItemType()
    {
        return itemType;
    }

    public void setItemType(ItemType itemType)
    {
        this.itemType = itemType;
    }
}
