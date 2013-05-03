package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Department;
import by.dak.furman.financial.swing.category.DepartmentNode;
import by.dak.furman.financial.swing.category.RootNode;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:08 PM
 */
public class AddNewDepartment extends ACAction<RootNode> {
	@Override
	protected void makeAction() {
		Department department = new Department();
		DepartmentNode node = new DepartmentNode();
		node.setValue(department);
		getNode().fillChildNode(node);
		getModel().insertNodeInto(node, getNode(), getNode().getChildCount());
	}
}
