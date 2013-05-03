package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.swing.category.CategoryNode;
import by.dak.furman.financial.swing.category.DepartmentNode;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:08 PM
 */
public class AddNewCategory extends ACAction<DepartmentNode> {
	@Override
	protected void makeAction() {
		Category category = new Category();
		category.setDepartment(getNode().getDepartment());
		CategoryNode categoryNode = SaveCategory.createCategoryNode(category, getNode());
		getModel().insertNodeInto(categoryNode, getNode(), getNode().getChildCount());
	}
}
