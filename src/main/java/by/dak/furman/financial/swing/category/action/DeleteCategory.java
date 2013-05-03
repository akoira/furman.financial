package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.app.AppConfig;
import by.dak.furman.financial.service.ICategoryService;
import by.dak.furman.financial.swing.ATreeTableNode;
import by.dak.furman.financial.swing.category.CategoryNode;

import java.util.ArrayList;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:59 PM
 */
public class DeleteCategory extends ACAction {
	private CategoryNode categoryNode;
	private Category category;

	private ICategoryService categoryService = AppConfig.getAppConfig().getCategoryService();

	@Override
	protected void before() {
		category = categoryNode.getValue();
		categoryService.delete(category);
	}

	@Override
	protected boolean validate() {
		//not new cat node
		return categoryNode.getParent() != getRootNode();
	}

	@Override
	protected void makeAction() {
		final ArrayList<ATreeTableNode> result = new ArrayList<ATreeTableNode>();
		NodeIterator iterator = new NodeIterator() {
			@Override
			protected boolean action(ATreeTableNode child) {
				if (child.getValue() == category)
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

	public CategoryNode getCategoryNode() {
		return categoryNode;
	}

	public void setCategoryNode(CategoryNode categoryNode) {
		this.categoryNode = categoryNode;
	}
}
