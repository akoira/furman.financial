package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.category.CategoryNode;

import java.math.BigDecimal;
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

	@Override
	public void reloadNode() {
		if (getNode().getValue().getId() == null)
			getNode().setAmount(BigDecimal.ZERO);
		else
			super.reloadNode();
	}
}
