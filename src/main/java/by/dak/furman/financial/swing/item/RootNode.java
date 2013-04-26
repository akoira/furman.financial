package by.dak.furman.financial.swing.item;

import by.dak.common.swing.treetable.ATreeTableNode;
import by.dak.furman.financial.ItemType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 8:55 PM
 */
public class RootNode extends ATreeTableNode
{
    private BigDecimal amount;
    private Date created;
    private ItemType itemType;

    public RootNode()
    {
        setAllowsChildren(true);
    }

    public void setItemType(ItemType itemType)
    {
        this.itemType = itemType;
    }

    public ItemType getItemType()
    {
        return this.itemType;
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
}
