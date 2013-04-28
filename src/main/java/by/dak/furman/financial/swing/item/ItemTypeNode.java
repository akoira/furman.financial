package by.dak.furman.financial.swing.item;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.ItemType;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 7:06 PM
 */
public class ItemTypeNode extends AINode<ItemType>
{
    public ItemTypeNode()
    {
        setAllowsChildren(true);
    }

    @Override
    public void setName(String name)
    {
        getValue().setName(name);
    }

    @Override
    public String getName()
    {
        return getValue().getName();
    }

    @Override
    public ItemType getItemType()
    {
        return super.getValue();
    }

    @Override
    public void setItemType(ItemType itemType)
    {
        super.setValue(itemType);
    }

    @Override
    public Category getCategory()
    {
        return getValue().getCategory();
    }

    @Override
    public void setCategory(Category category)
    {
        getValue().setCategory(category);
    }
}
