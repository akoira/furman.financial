package by.dak.furman.financial.dao;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.Item;

import java.math.BigDecimal;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 6:21 PM
 */
public interface IItemDao extends IDao<Item> {
	public BigDecimal getSumBy(SearchFilter filter);
}
