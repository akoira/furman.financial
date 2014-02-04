package by.dak.furman.financial.service;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.*;
import by.dak.furman.financial.dao.IItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 5:59 PM
 */
@Service
public class ItemService extends AService<Item> implements IItemService {
	@Autowired
	private IItemDao dao;

	protected IItemDao getDao() {
		return dao;
	}

	public List<Item> getAllBy(ItemType itemType) {
		SearchFilter filter = new SearchFilter();
		filter.eq(Item.PROPERTY_itemType, itemType);
		filter.addAscOrder(Item.PROPERTY_created);
		return getAllBy(filter);
	}

	@Override
	public BigDecimal getSumBy(SearchFilter searchFilter) {
		return getDao().getSumBy(searchFilter);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Item> getAllBy(SearchFilter filter) {
		filter.addAscOrder(Item.PROPERTY_created);
		return super.getAllBy(filter);
	}

	public SearchFilter getSearchFilter(Department department, Category category, ItemType itemType, Period period) {
		SearchFilter searchFilter = SearchFilter.instanceUnbound();
		if (department != null)
			searchFilter.eq(department, Item.PROPERTY_itemType, ItemType.PROPERTY_category, Category.PROPERTY_department);
		if (category != null)
			searchFilter.eq(category, Item.PROPERTY_itemType, ItemType.PROPERTY_category);
		if (itemType != null)
			searchFilter.eq(Item.PROPERTY_itemType, itemType);
		if (period != null) {
			searchFilter.ge(Item.PROPERTY_created, period.getStartDate());
			searchFilter.lt(Item.PROPERTY_created, period.getEndDate());
		}
		return searchFilter;
	}

}
