package by.dak.furman.financial.service;

import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.dao.IItemTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 5:59 PM
 */
@Service
public class ItemTypeService extends AService<ItemType> implements IItemTypeService
{
    @Autowired
    private IItemTypeDao dao;

    protected IItemTypeDao getDao()
    {
        return dao;
    }
}
