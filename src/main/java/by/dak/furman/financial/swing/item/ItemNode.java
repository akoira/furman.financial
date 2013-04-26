package by.dak.furman.financial.swing.item;

import by.dak.common.swing.treetable.ATreeTableNode;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.ItemType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 7:06 PM
 */
public class ItemNode extends ATreeTableNode<Item>
{
    public void setItemType(ItemType itemType)
    {
        getValue().setItemType(itemType);
    }

    public ItemType getItemType()
    {
        return getValue().getItemType();
    }

    public BigDecimal getAmount()
    {
        return getValue().getAmount();
    }

    public void setAmount(BigDecimal amount)
    {
        getValue().setAmount(amount);
    }

    public Date getCreated()
    {
        return getValue().getCreated();
    }

    public void setCreated(Date created)
    {
        getValue().setCreated(created);
    }

}

