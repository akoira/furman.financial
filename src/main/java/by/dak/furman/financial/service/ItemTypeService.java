package by.dak.furman.financial.service;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.dao.IItemTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 5:59 PM
 */
@Service
public class ItemTypeService extends AService<ItemType> implements IItemTypeService {
	@Autowired
	private IItemTypeDao dao;

	protected IItemTypeDao getDao() {
		return dao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemType> getAllBy(Category value) {
		SearchFilter filter = SearchFilter.instanceUnbound();
		filter.eq(ItemType.PROPERTY_category, value);
		filter.addAscOrder(ItemType.PROPERTY_name);
		return getAllBy(filter);
	}

	@Override
	@Transactional(readOnly = true)
	public ItemType getBy(Category category, String name) {
		SearchFilter filter = SearchFilter.instanceSingle();
		filter.eq(ItemType.PROPERTY_category, category);
		filter.ilike(ItemType.PROPERTY_name, name);
		return getDao().getBy(filter);
	}
}
