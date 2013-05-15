package by.dak.furman.financial.swing.category.action;

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
		return getPanel().getAppConfig().getDepartmentService().getAll();
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
}
