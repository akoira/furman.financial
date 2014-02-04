package by.dak.furman.financial.service;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.Department;
import by.dak.furman.financial.dao.ICategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 3:04 PM
 */
@Service
public class CategoryService extends AService<Category> implements ICategoryService {
	@Autowired
	private ICategoryDao dao;

	protected ICategoryDao getDao() {
		return dao;
	}

	@Override
	public List<Category> getAllBy(Department department) {

		SearchFilter searchFilter = SearchFilter.instanceUnbound();
		searchFilter.eq(Category.PROPERTY_department, department);
		searchFilter.addAscOrder(Category.PROPERTY_name);
		return getDao().getAllBy(searchFilter);
	}


	@Override
	public Category getByDepartmentName(Department department, String name) {
		SearchFilter searchFilter = SearchFilter.instanceSingle();
		searchFilter.eq(Category.PROPERTY_department, department);
		searchFilter.eq(Category.PROPERTY_name, name);
		return getDao().getBy(searchFilter);
	}
}
