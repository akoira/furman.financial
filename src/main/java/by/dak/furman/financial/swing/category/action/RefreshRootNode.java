package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.AObject;
import by.dak.furman.financial.Department;
import by.dak.furman.financial.swing.category.DepartmentNode;
import by.dak.furman.financial.swing.category.RootNode;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:03 PM
 */
public class RefreshRootNode extends ACRefreshAction<RootNode, Department, DepartmentNode> {
	@Override
	public List<Department> getChildValues() {
		return getPanel().getAppConfig().getDepartmentService().getAllSortedBy(AObject.PROPERTY_name);
	}

	@Override
	public DepartmentNode createChildNode() {
		return new DepartmentNode();
	}

	@Override
	public void refreshChildNode(DepartmentNode childNode) {
		RefreshDepartmentNode departmentNode = new RefreshDepartmentNode();
		departmentNode.setPanel(getPanel());
		departmentNode.setNode(childNode);
		departmentNode.action();
	}

	@Override
	protected void after() {
		super.after();
		if (getNode().getPeriod().isCurrent()) {
			AddNewDepartment addNewDepartment = new AddNewDepartment();
			addNewDepartment.setPanel(getPanel());
			addNewDepartment.setNode((RootNode) addNewDepartment.getRootNode());
			addNewDepartment.action();
		}
	}
}
