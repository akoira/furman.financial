package by.dak.furman.financial.swing.item;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.Item;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 11:48 AM
 */
public class CategoryNode extends AItemNode<Category>
{
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
        return getItemService().getSumBy(getSearchFilter());
    }

    @Override
    public void setAmount(BigDecimal amount)
    {
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
    public SearchFilter getSearchFilter()
    {
        SearchFilter filter = super.getSearchFilter();
        filter.eq(Item.PROPERTY_category, getValue());
        return filter;
    }
}
