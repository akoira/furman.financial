package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Department;
import by.dak.furman.financial.swing.category.DepartmentNode;
import by.dak.furman.financial.swing.category.RootNode;
import org.apache.commons.lang3.StringUtils;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 12:27 PM
 */
public class SaveDepartment extends ACAction<DepartmentNode> {
	private Department department;

	@Override
	protected void before() {
		department = getNode().getValue();
	}

	protected void makeAction() {
		if (department.getId() == null) {
			getDepartmentService().add(department);
			getModel().removeNodeFromParent(getNode());
			addDepartmentNodes();

			AddNewDepartment addNewDepartment = new AddNewDepartment();
			addNewDepartment.setNode((RootNode) getRootNode());
			addNewDepartment.setPanel(getPanel());
			addNewDepartment.action();

		} else {
			getDepartmentService().save(department);
		}

	}

	protected boolean validate() {
		if (StringUtils.stripToNull(department.getName()) == null) {
			setMessage("department.name cannot be null");
			return false;
		}
		Department found = getDepartmentService().getByName(department.getName());
		if (found != null && (department.getId() == null || department.getId().equals(found.getId()))) {
			setMessage(String.format("Department with name %s already exist", department.getName()));
			return false;
		}
		return true;
	}

	private void addDepartmentNodes() {
		DepartmentNode departmentNode = new DepartmentNode();
		departmentNode.setValue(department);
		getRootNode().fillChildNode(departmentNode);
		getModel().insertNodeInto(departmentNode, getRootNode(), getRootNode().getChildCount());

		RefreshDepartmentNode refreshDepartmentNode = new RefreshDepartmentNode();
		refreshDepartmentNode.setNode(departmentNode);
		refreshDepartmentNode.setPanel(getPanel());
		refreshDepartmentNode.action();
	}

}
