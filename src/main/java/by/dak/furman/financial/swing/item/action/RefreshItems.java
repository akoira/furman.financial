package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.Item;
import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.app.AppConfig;
import by.dak.furman.financial.service.IItemService;
import by.dak.furman.financial.service.IItemTypeService;
import by.dak.furman.financial.swing.item.ItemNode;
import by.dak.furman.financial.swing.item.ItemTypeNode;
import by.dak.furman.financial.swing.item.RootNode;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:03 PM
 */
public class RefreshItems extends AItemAction
{
    private IItemService itemService = AppConfig.getAppConfig().getItemService();
    private IItemTypeService iItemTypeService = AppConfig.getAppConfig().getItemTypeService();

    protected void makeAction()
    {
        RootNode root = createRoot();
        getModel().setRoot(root);

        AddNewItem addNewItem = new AddNewItem();
        addNewItem.setItemsPanel(getItemsPanel());
        addNewItem.action();

        List<ItemType> itemTypes = iItemTypeService.getAll();
        for (ItemType itemType : itemTypes)
        {
            ItemTypeNode itemTypeNode = new ItemTypeNode();
            itemTypeNode.setValue(itemType);
            itemTypeNode.setProperties(createProperties(itemType));

            List<Item> items = itemService.getAllBy(itemType);
            for (Item item : items)
            {
                ItemNode itemNode = new ItemNode();
                itemNode.setValue(item);
                itemNode.setProperties(createProperties(item));
                itemTypeNode.add(itemNode);
            }

            getModel().insertNodeInto(itemTypeNode, root, root.getChildCount());
        }
    }

    private RootNode createRoot()
    {
        RootNode root = new RootNode();
        root.setProperties(createProperties(null));
        return root;
    }
}
