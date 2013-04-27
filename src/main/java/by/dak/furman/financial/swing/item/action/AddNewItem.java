package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.swing.item.AItemNode;
import by.dak.furman.financial.swing.item.ItemNode;
import by.dak.furman.financial.swing.item.ItemTypeNode;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 9:50 PM
 */
public class AddNewItem extends AItemAction
{
    private Category category;
    private AItemNode parentNode;

    @Override
    protected void makeAction()
    {
        Item item = new Item();
        item.setCategory(getCategory());
        item.setItemType(getItemType());
        ItemNode node = new ItemNode();
        node.setProperties(AItemNode.createProperties(item));
        node.setValue(item);

        getModel().insertNodeInto(node, parentNode, parentNode.getChildCount());
    }

    public AItemNode getParentNode()
    {
        return parentNode;
    }

    public void setParentNode(AItemNode parentNode)
    {
        this.parentNode = parentNode;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public ItemType getItemType()
    {
        return parentNode instanceof ItemTypeNode ? ((ItemTypeNode) getParentNode()).getValue() : null;
    }
}
