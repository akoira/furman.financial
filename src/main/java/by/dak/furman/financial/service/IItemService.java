package by.dak.furman.financial.service;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 12:30 PM
 */
public interface IItemService extends IService<Item> {
	@Transactional(readOnly = true)
	List<Item> getAllBy(ItemType itemType);

	@Transactional(readOnly = true)
	BigDecimal getSumBy(SearchFilter searchFilter);

	SearchFilter getSearchFilter(Department department, Category category, ItemType itemType, Period period);
}
