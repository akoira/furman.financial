package by.dak.furman.financial.swing.item;

import by.dak.furman.financial.Item;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 8:55 PM
 */
public class RootNode extends AINode<Item>
{
    private BigDecimal amount;
    private Date created;
    private String name;

    public RootNode()
    {
        setAllowsChildren(true);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
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
