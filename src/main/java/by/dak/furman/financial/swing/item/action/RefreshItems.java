package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.app.AppConfig;
import by.dak.furman.financial.service.ICategoryService;
import by.dak.furman.financial.service.IItemService;
import by.dak.furman.financial.service.IItemTypeService;
import by.dak.furman.financial.swing.category.ACategoryNode;
import by.dak.furman.financial.swing.item.AItemNode;
import by.dak.furman.financial.swing.item.CategoryNode;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:03 PM
 */
public class RefreshItems extends AItemAction
{
    private ICategoryService categoryService = AppConfig.getAppConfig().getCategoryService();
    private IItemService itemService = AppConfig.getAppConfig().getItemService();
    private IItemTypeService iItemTypeService = AppConfig.getAppConfig().getItemTypeService();

    private ACategoryNode categoryNode;

    protected void makeAction()
    {
        removeNodes();

        if (needCategoryNode())
        {
            List<Category> list = categoryService.getAll();
            for (Category category : list)
            {
                CategoryNode node = createCategoryNode(category);
                getModel().insertNodeInto(node, getRootNode(), getRootNode().getChildCount());
                addNewItem(node);
            }
        }

/*        addNewItem();

        List<ItemType> itemTypes = iItemTypeService.getAll();
        for (ItemType itemType : itemTypes)
        {
            ItemTypeNode itemTypeNode = new ItemTypeNode();
            itemTypeNode.setValue(itemType);
            itemTypeNode.setProperties(createProperties(itemType));

            SearchFilter filter = SearchFilter.valueOf(searchFilter);
            filter.eq(Item.PROPERTY_itemType, itemType);
            List<Item> items = itemService.getAll(filter);
            for (Item item : items)
            {
                ItemNode itemNode = new ItemNode();
                itemNode.setValue(item);
                itemNode.setProperties(createProperties(item));
                itemTypeNode.add(itemNode);
            }

        } */
    }

    private void removeNodes()
    {
        List<AItemNode> nodes = Collections.list((Enumeration<AItemNode>) getRootNode().children());
        for (AItemNode node : nodes)
        {
            getModel().removeNodeFromParent(node);
        }
    }

    private CategoryNode createCategoryNode(Category category)
    {
        CategoryNode node = new CategoryNode();
        node.setItemService(itemService);
        node.setValue(category);
        node.setProperties(AItemNode.createProperties(category));
        return node;
    }

    private void addNewItem(AItemNode parent)
    {
        AddNewItem addNewItem = new AddNewItem();
        addNewItem.setParentNode(parent);
        addNewItem.setItemsPanel(getItemsPanel());
        addNewItem.action();

    }

    public ACategoryNode getCategoryNode()
    {
        return categoryNode;
    }

    public void setCategoryNode(ACategoryNode categoryNode)
    {
        this.categoryNode = categoryNode;
    }

    public boolean needCategoryNode()
    {
        return !(categoryNode instanceof by.dak.furman.financial.swing.category.CategoryNode);
    }
}
