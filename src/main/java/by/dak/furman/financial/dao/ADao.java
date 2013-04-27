package by.dak.furman.financial.dao;

import by.dak.common.persistence.CriteriaFiller;
import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.AObject;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 6:00 PM
 */
public abstract class ADao<O extends AObject> implements IDao<O>
{
    @Autowired
    private SessionFactory sessionFactory;

    private Class<O> objectClass;


    public Class<O> getObjectClass()
    {
        if (objectClass == null)
        {
            objectClass = (Class<O>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return objectClass;
    }


    public void add(O object)
    {
        object.setCreated(new Date());
        object.setModified(new Date());
        object.setDeleted(Boolean.FALSE);
        sessionFactory.getCurrentSession().save(object);
    }

    public void save(O object)
    {
        object.setModified(new Date());
        sessionFactory.getCurrentSession().update(object);
    }

    public void delete(O object)
    {
        object.setModified(new Date());
        object.setDeleted(Boolean.TRUE);
        sessionFactory.getCurrentSession().delete(object);
//        sessionFactory.getCurrentSession().update(object);
    }


    public O getById(Long id)
    {
        return (O) sessionFactory.getCurrentSession().get(getObjectClass(), id);
    }

    public List<O> getAll()
    {
        Criteria criteria = createCriteria(getObjectClass());

        return criteria.list();
    }

    protected Criteria createCriteria(Class clazz)
    {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        return criteria;
    }

    @Override
    public List<O> getAll(final SearchFilter filter)
    {
        Criteria execCriteria = createCriteria(getObjectClass());
        fillFilter(execCriteria, filter);
        return execCriteria.list();
    }

    @Override
    public BigDecimal getSum(String property)
    {
        Criteria criteria = createCriteria(getObjectClass());
        criteria.setProjection(Projections.sum(property));
        BigDecimal result = (BigDecimal) criteria.uniqueResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public O getByName(String name)
    {
        SearchFilter searchFilter = SearchFilter.instanceSingle();
        searchFilter.ilike(AObject.PROPERTY_name, name);
        return null;
    }

    protected void fillFilter(Criteria execCriteria, SearchFilter filter)
    {
        CriteriaFiller filler = new CriteriaFiller(execCriteria, filter);
        filler.fill();
    }

}
