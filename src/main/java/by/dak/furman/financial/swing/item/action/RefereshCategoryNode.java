package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.swing.item.CategoryNode;
import by.dak.furman.financial.swing.item.ItemTypeNode;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 2:42 PM
 */
public class RefereshCategoryNode extends ARefreshAction<CategoryNode>
{
    @Override
    protected void makeAction()
    {
        removeNodes();
        List<ItemType> itemTypes = getItemsPanel().getAppConfig().getItemTypeService().getAll();
        for (ItemType itemType : itemTypes)
        {
            ItemTypeNode itemTypeNode = new ItemTypeNode();
            getParentNode().fillChildNode(itemTypeNode);
            itemTypeNode.setItemType(itemType);
            itemTypeNode.setValue(itemType);

            getModel().insertNodeInto(itemTypeNode, getParentNode(), getParentNode().getChildCount());

            RefreshItemTypeNode refreshItemTypeNode = new RefreshItemTypeNode();
            refreshItemTypeNode.setItemsPanel(getItemsPanel());
            refreshItemTypeNode.setParentNode(getParentNode());
            refreshItemTypeNode.action();
        }

        AddNewItem addNewItem = new AddNewItem();
        addNewItem.setItemsPanel(getItemsPanel());
        addNewItem.setParentNode(getParentNode());
        addNewItem.action();
    }
}
