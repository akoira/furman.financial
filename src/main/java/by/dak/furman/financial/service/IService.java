package by.dak.furman.financial.service;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.AObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 3:06 PM
 */
public interface IService<O extends AObject>
{
    List<O> getAll();

    List<O> getAll(SearchFilter filter);

    void add(O object);

    void save(O object);

    void delete(O object);

    O getById(Long id);

    BigDecimal getSum(String property);
}
