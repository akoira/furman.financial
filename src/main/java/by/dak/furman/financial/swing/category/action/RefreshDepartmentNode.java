package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.swing.category.CategoryNode;
import by.dak.furman.financial.swing.category.DepartmentNode;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 2:42 PM
 */
public class RefreshDepartmentNode extends ACRefreshAction<DepartmentNode, Category, CategoryNode> {

	@Override
	public List<Category> getChildValues() {
		return getCategoryService().getAllBy(getNode().getValue());
	}

	@Override
	public CategoryNode createChildNode() {
		return new CategoryNode();
	}

	@Override
	public void refreshChildNode(CategoryNode childNode) {
		RefreshCategoryNode refreshCategoryNode = new RefreshCategoryNode();
		refreshCategoryNode.setNode(childNode);
		refreshCategoryNode.setPanel(getPanel());
		refreshCategoryNode.action();
	}

	@Override
	protected void after() {
		super.after();
		if (getNode().getPeriod().isCurrent()) {
			AddNewCategory addNewCategory = new AddNewCategory();
			addNewCategory.setNode(getNode());
			addNewCategory.setPanel(getPanel());
			addNewCategory.action();
		}
	}

	@Override
	public void reloadNode() {
		if (getNode().getValue().getId() == null)
			getNode().setAmount(BigDecimal.ZERO);
		else
			super.reloadNode();
	}
}
