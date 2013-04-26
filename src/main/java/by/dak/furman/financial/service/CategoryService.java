package by.dak.furman.financial.service;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.dao.ICategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 3:04 PM
 */
@Service
public class CategoryService extends AService<Category> implements ICategoryService
{
    @Autowired
    private ICategoryDao dao;

    protected ICategoryDao getDao()
    {
        return dao;
    }
}
