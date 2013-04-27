package by.dak.furman.financial.service;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.dao.IItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 5:59 PM
 */
@Service
public class ItemService extends AService<Item> implements IItemService
{
    @Autowired
    private IItemDao dao;

    protected IItemDao getDao()
    {
        return dao;
    }

    public List<Item> getAllBy(ItemType itemType)
    {
        SearchFilter filter = new SearchFilter();
        filter.eq(Item.PROPERTY_itemType, itemType);
        filter.addAscOrder(Item.PROPERTY_created);
        return getAll(filter);
    }

    @Override
    public BigDecimal getSumBy(SearchFilter searchFilter)
    {
        return getDao().getSumBy(searchFilter);
    }
}
