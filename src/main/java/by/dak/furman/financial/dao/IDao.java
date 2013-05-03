package by.dak.furman.financial.dao;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.AObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 6:00 PM
 */
public interface IDao<O extends AObject> {
	List<O> getAll();

	List<O> getAllBy(SearchFilter filter);

	void add(O object);

	void save(O object);

	void delete(O object);

	O getById(Long id);

	BigDecimal getSum(String property);

	O getByName(String name);

	O getBy(SearchFilter searchFilter);
}
