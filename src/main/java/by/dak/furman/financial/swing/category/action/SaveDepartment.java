package by.dak.furman.financial.swing.category.action;

import by.dak.common.lang.Utils;
import by.dak.furman.financial.Department;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.category.DepartmentNode;
import by.dak.furman.financial.swing.category.RootNode;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

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
		getModel().insertNodeInto(departmentNode, getRootNode(), calcIndexForNewDepartmentNode(departmentNode));

		ExpandNode expandNode = new ExpandNode();
		expandNode.setPanel(getPanel());
		expandNode.setNode(departmentNode);
		expandNode.action();

		RefreshDepartmentNode refreshDepartmentNode = new RefreshDepartmentNode();
		refreshDepartmentNode.setNode(departmentNode);
		refreshDepartmentNode.setPanel(getPanel());
		refreshDepartmentNode.action();
	}

	private int calcIndexForNewDepartmentNode(DepartmentNode departmentNode) {
		return Utils.getIndexInSortedList(getRootNode(), departmentNode, new Comparator<ACNode>() {
			@Override
			public int compare(ACNode o1, ACNode o2) {
				return o1.getDepartment().getName().compareTo(o2.getDepartment().getName());
			}
		});
	}


}
