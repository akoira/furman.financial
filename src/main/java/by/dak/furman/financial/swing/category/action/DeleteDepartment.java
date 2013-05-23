package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Department;
import by.dak.furman.financial.swing.ADeleteAction;
import by.dak.furman.financial.swing.ATreeTableNode;
import by.dak.furman.financial.swing.category.CategoriesPanel;
import by.dak.furman.financial.swing.category.DepartmentNode;

import java.util.ArrayList;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:59 PM
 */
public class DeleteDepartment extends ADeleteAction<CategoriesPanel, DepartmentNode> {

	private Department department;

	@Override
	protected void before() {
		super.before();
		department = getNode().getDepartment();
	}

	@Override
	protected boolean validate() {
		if (department.getId() == null) {
			setMessage("department.id is null");
			return false;
		}
		if (getCategoryService().getAllBy(department).size() > 0) {
			setMessage(String.format("Category > 0 for department %s", department.getName()));
			return false;
		}
		return true;
	}

	@Override
	protected void makeAction() {
		getDepartmentService().delete(department);
		final ArrayList<ATreeTableNode> result = new ArrayList<ATreeTableNode>();
		NodeIterator iterator = new NodeIterator() {
			@Override
			protected boolean action(ATreeTableNode child) {
				if (child instanceof DepartmentNode && department.getId().equals(child.getDepartment().getId()))
					result.add(child);
				return true;
			}
		};
		iterator.iterate(getRootNode());
		for (ATreeTableNode node : result) {
			getModel().removeNodeFromParent(node);
		}
		getPanel().getTreeTable().repaint();
	}
}
