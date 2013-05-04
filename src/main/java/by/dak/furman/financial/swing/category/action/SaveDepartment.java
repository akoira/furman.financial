package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.Department;
import by.dak.furman.financial.swing.category.*;
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

			AddNewDepartment addNewDepartment = new AddNewDepartment();
			addNewDepartment.setNode((RootNode) getRootNode());
			addNewDepartment.setPanel(getPanel());
			addNewDepartment.action();

			addDepartmentNodes();
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
		int count = getRootNode().getChildCount();
		for (int i = 0; i < count; i++) {
			if (getRootNode().getChildAt(i) instanceof APeriodNode) {
				APeriodNode yearNode = (APeriodNode) getRootNode().getChildAt(i);
				int yCount = yearNode.getChildCount();
				for (int k = 0; k < yCount; k++) {
					APeriodNode monthNode = (APeriodNode) yearNode.getChildAt(k);
					DepartmentNode departmentNode = new DepartmentNode();
					departmentNode.setValue(department);
					monthNode.fillChildNode(departmentNode);
					getModel().insertNodeInto(departmentNode, monthNode, monthNode.getChildCount());

					RefreshDepartmentNode refreshDepartmentNode = new RefreshDepartmentNode();
					refreshDepartmentNode.setNode(departmentNode);
					refreshDepartmentNode.setPanel(getPanel());
					refreshDepartmentNode.action();

					if (monthNode.getPeriod().isCurrent()) {
						ExpandNode expandNode = new ExpandNode();
						expandNode.setPanel(getPanel());
						expandNode.setNode(departmentNode);
						expandNode.action();
					}
				}
			}
		}
	}

	public static CategoryNode createCategoryNode(Category category, ACNode parent) {
		CategoryNode categoryNode = new CategoryNode();
		categoryNode.setValue(category);
		parent.fillChildNode(categoryNode);
		return categoryNode;
	}
}
