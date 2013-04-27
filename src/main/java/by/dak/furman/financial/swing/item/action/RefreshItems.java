package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.app.AppConfig;
import by.dak.furman.financial.service.ICategoryService;
import by.dak.furman.financial.service.IItemService;
import by.dak.furman.financial.service.IItemTypeService;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.item.AINode;
import by.dak.furman.financial.swing.item.CategoryNode;
import by.dak.furman.financial.swing.item.RootNode;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:03 PM
 */
public class RefreshItems extends ARefreshAction<RootNode>
{
    private ICategoryService categoryService = AppConfig.getAppConfig().getCategoryService();
    private IItemService itemService = AppConfig.getAppConfig().getItemService();
    private IItemTypeService iItemTypeService = AppConfig.getAppConfig().getItemTypeService();

    private ACNode categoryNode;

    protected void makeAction()
    {
        removeNodes();

        getRootNode().setCategory(categoryNode != null ? categoryNode.getCategory() : null);

        if (needCategoryNode())
        {
            List<Category> list = categoryService.getAll();
            for (Category category : list)
            {
                CategoryNode node = createCategoryNode(category);

                RefereshCategoryNode refereshCategoryNode = new RefereshCategoryNode();
                refereshCategoryNode.setItemsPanel(this.getItemsPanel());
                refereshCategoryNode.setParentNode(node);
                refereshCategoryNode.action();
            }
        }

/*        addNewItemNode();

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

    private void addNewItemTypeNode(CategoryNode node)
    {
        AddNewItemType addNewItemType = new AddNewItemType();
        addNewItemType.setParentNode(node);
        addNewItemType.setItemsPanel(getItemsPanel());
        addNewItemType.action();
    }

    private CategoryNode createCategoryNode(Category category)
    {
        CategoryNode node = new CategoryNode();
        node.setValue(category);
        getParentNode().fillChildNode(node);

        node.setCategory(category);
        getModel().insertNodeInto(node, getParentNode(), getParentNode().getChildCount());
        return node;
    }

    private void addNewItemNode(AINode parent)
    {
        AddNewItem addNewItem = new AddNewItem();
        addNewItem.setParentNode(parent);
        addNewItem.setItemsPanel(getItemsPanel());
        addNewItem.action();

    }

    public ACNode getCategoryNode()
    {
        return categoryNode;
    }

    public void setCategoryNode(ACNode categoryNode)
    {
        this.categoryNode = categoryNode;
    }

    public boolean needCategoryNode()
    {
        return !(categoryNode instanceof by.dak.furman.financial.swing.category.CategoryNode);
    }
}
