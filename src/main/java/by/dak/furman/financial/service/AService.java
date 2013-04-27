package by.dak.furman.financial.service;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 3:05 PM
 */

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.AObject;
import by.dak.furman.financial.dao.IDao;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public abstract class AService<O extends AObject> implements IService<O>
{
    protected abstract IDao<O> getDao();

    @Transactional(readOnly = true)
    public List<O> getAll()
    {
        return getDao().getAll();
    }

    @Transactional(readOnly = true)
    public O getById(Long id)
    {
        return getDao().getById(id);
    }

    @Transactional(readOnly = true)
    public List<O> getAll(SearchFilter filter)
    {
        return getDao().getAll(filter);
    }

    @Override
    @Transactional(readOnly = false)
    public void add(O object)
    {
        getDao().add(object);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(O object)
    {
        getDao().save(object);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(O object)
    {
        getDao().delete(object);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getSum(String property)
    {
        return getDao().getSum(property);
    }

    @Override
    @Transactional(readOnly = true)
    public O getByName(String name)
    {
        return getDao().getByName(name);
    }
}



