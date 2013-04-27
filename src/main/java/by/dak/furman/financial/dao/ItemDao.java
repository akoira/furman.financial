package by.dak.furman.financial.dao;

import by.dak.common.persistence.CriteriaFiller;
import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.Item;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 5:46 PM
 */
@Repository
public class ItemDao extends ADao<Item> implements IItemDao
{
    @Override
    public BigDecimal getSumBy(SearchFilter filter)
    {
        Criteria criteria = createCriteria(getObjectClass());
        CriteriaFiller filler = new CriteriaFiller(criteria, filter);
        filler.fill();

        criteria.setProjection(Projections.sum(Item.PROPERTY_amount));
        BigDecimal result = (BigDecimal) criteria.uniqueResult();
        return result != null ? result : BigDecimal.ZERO;
    }

}
