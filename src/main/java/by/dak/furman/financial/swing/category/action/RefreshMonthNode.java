package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Department;
import by.dak.furman.financial.swing.category.DepartmentNode;
import by.dak.furman.financial.swing.category.MonthNode;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 3:59 PM
 */
public class RefreshMonthNode extends ACRefreshAction<MonthNode, Department, DepartmentNode> {
	@Override
	public List<Department> getChildValues() {
		return getPanel().getAppConfig().getDepartmentService().getAll();
	}

	@Override
	public DepartmentNode createChildNode() {
		DepartmentNode node = new DepartmentNode();
		return node;
	}

	@Override
	public void refreshChildNode(DepartmentNode childNode) {
		RefreshDepartmentNode refreshDepartmentNode = new RefreshDepartmentNode();
		refreshDepartmentNode.setNode(childNode);
		refreshDepartmentNode.setPanel(getPanel());
		refreshDepartmentNode.action();
	}
}
