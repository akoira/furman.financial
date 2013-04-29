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
public class RefreshCategoryNode extends AIRefreshAction<CategoryNode, ItemType, ItemTypeNode>
{

    @Override
    public List<ItemType> getChildValues()
    {
        return getItemTypeService().getAllBy(getNode().getValue());
    }

    @Override
    public ItemTypeNode createChildNode()
    {
        return new ItemTypeNode();
    }

    @Override
    public void refreshChildNode(ItemTypeNode childNode)
    {
        RefreshItemTypeNode refreshItemTypeNode = new RefreshItemTypeNode();
        refreshItemTypeNode.setNode(childNode);
        refreshItemTypeNode.setPanel(getPanel());
        refreshItemTypeNode.action();
    }

    @Override
    protected void after()
    {
        super.after();
        AddNewItemType addNewItemType = new AddNewItemType();
        addNewItemType.setNode(getNode());
        addNewItemType.setPanel(getPanel());
        addNewItemType.action();
    }
}
