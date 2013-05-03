package by.dak.furman.financial.swing.category;

import by.dak.furman.financial.Department;

public class DepartmentNode extends ACNode<Department> {

	@Override
	public Department getDepartment() {
		return getValue();
	}

	@Override
	public void setDepartment(Department department) {
		setValue(department);
	}

	@Override
	public String getName() {
		return getValue().getName();
	}

	@Override
	public void setName(String name) {
		getValue().setName(name);
	}
}
