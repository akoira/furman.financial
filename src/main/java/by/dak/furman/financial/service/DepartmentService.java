package by.dak.furman.financial.service;

import by.dak.furman.financial.Department;
import by.dak.furman.financial.dao.IDepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService extends AService<Department> implements IDepartmentService {
	@Autowired
	private IDepartmentDao dao;

	@Override
	protected IDepartmentDao getDao() {
		return dao;
	}

}

