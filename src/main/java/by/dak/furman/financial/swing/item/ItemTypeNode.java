package by.dak.furman.financial.swing.item;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.ItemType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 7:06 PM
 */
public class ItemTypeNode extends AINode<ItemType>
{
    private BigDecimal amount;
    private Date created;

    public ItemTypeNode()
    {
        setAllowsChildren(true);
    }

    @Override
    public Object getUserObject()
    {
        return super.getUserObject();
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

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
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
