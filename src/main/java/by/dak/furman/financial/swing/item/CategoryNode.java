package by.dak.furman.financial.swing.item;

import by.dak.furman.financial.Category;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 11:48 AM
 */
public class CategoryNode extends AINode<Category>
{
    private BigDecimal amount;

    @Override
    public void setName(String name)
    {
    }

    @Override
    public String getName()
    {
        return getValue().getName();
    }

    @Override
    public BigDecimal getAmount()
    {
        return amount;
    }

    @Override
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    @Override
    public Date getCreated()
    {
        return null;
    }

    @Override
    public void setCreated(Date created)
    {
    }

    @Override
    public Category getCategory()
    {
        return getValue();
    }

    @Override
    public void setCategory(Category category)
    {
        setValue(category);
    }
}
