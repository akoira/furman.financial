package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.category.CategoryNode;

import java.util.Collections;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 4:00 PM
 */
public class RefreshCategoryNode extends ACRefreshAction<CategoryNode, ItemType, ACNode> {
	@Override
	public List<ItemType> getChildValues() {
		return Collections.emptyList();
	}

	@Override
	public ACNode createChildNode() {
		return null;
	}

	@Override
	public void refreshChildNode(ACNode childNode) {
	}
}
