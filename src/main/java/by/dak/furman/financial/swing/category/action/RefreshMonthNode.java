package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.swing.category.CategoryNode;
import by.dak.furman.financial.swing.category.MonthNode;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 3:59 PM
 */
public class RefreshMonthNode extends ACRefreshAction<MonthNode, Category, CategoryNode> {
	@Override
	public List<Category> getChildValues() {
		return getPanel().getAppConfig().getCategoryService().getAllBy(getNode().getDepartment());
	}

	@Override
	public CategoryNode createChildNode() {
		CategoryNode node = new CategoryNode();
		return node;
	}

	@Override
	public void refreshChildNode(CategoryNode childNode) {
		RefreshCategoryNode refreshCategoryNode = new RefreshCategoryNode();
		refreshCategoryNode.setNode(childNode);
		refreshCategoryNode.setPanel(getPanel());
		refreshCategoryNode.action();
	}
}
