package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.swing.ADeleteAction;
import by.dak.furman.financial.swing.ATreeTableNode;
import by.dak.furman.financial.swing.category.CategoriesPanel;
import by.dak.furman.financial.swing.category.CategoryNode;

import java.util.ArrayList;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:59 PM
 */
public class DeleteCategory extends ADeleteAction<CategoriesPanel, CategoryNode> {
	private Category category;

	@Override
	protected void before() {
		super.before();
		category = getNode().getCategory();
	}

	@Override
	protected boolean validate() {
		if (category.getId() == null) {
			setMessage("category id is null");
			return false;
		}
		if (getItemTypeService().getAllBy(category).size() > 0) {
			setMessage(String.format("ItemType > 0 for category %s", category.getName()));
			return false;
		}
		return true;
	}

	@Override
	protected void makeAction() {
		getCategoryService().delete(category);
		final ArrayList<ATreeTableNode> result = new ArrayList<ATreeTableNode>();
		NodeIterator iterator = new NodeIterator() {
			@Override
			protected boolean action(ATreeTableNode child) {
				if (child instanceof CategoryNode && category.getId().equals(((CategoryNode) child).getValue().getId()))
					result.add(child);
				return true;
			}
		};
		iterator.iterate((ATreeTableNode) getNode().getParent().getParent().getParent());
		for (ATreeTableNode node : result) {
			getModel().removeNodeFromParent(node);
		}
		getPanel().getTreeTable().repaint();
	}
}
