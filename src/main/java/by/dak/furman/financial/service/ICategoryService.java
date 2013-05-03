package by.dak.furman.financial.service;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.Department;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 12:30 PM
 */
public interface ICategoryService extends IService<Category> {
	@Transactional(readOnly = true)
	public List<Category> getAllBy(Department department);
}
