package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.ItemType;
import org.apache.commons.lang3.StringUtils;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 1:43 PM
 */
public class SaveItemType extends AIAction
{
    private ItemType itemType;

    @Override
    protected void makeAction()
    {
        if (itemType.getId() == null)
            getItemsPanel().getAppConfig().getItemTypeService().add(itemType);
        else
            getItemsPanel().getAppConfig().getItemTypeService().save(itemType);
    }

    @Override
    protected boolean validate()
    {
        return StringUtils.trimToNull(itemType.getName()) != null
                && getItemsPanel().getAppConfig().getItemTypeService().getByName(itemType.getName()) == null;
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
