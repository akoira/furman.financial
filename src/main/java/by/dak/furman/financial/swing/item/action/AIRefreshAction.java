package by.dak.furman.financial.swing.item.action;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.AObject;
import by.dak.furman.financial.swing.ARefreshAction;
import by.dak.furman.financial.swing.item.AINode;
import by.dak.furman.financial.swing.item.ItemsPanel;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 11:29 PM
 */
public abstract class AIRefreshAction<N extends AINode, V, C extends AINode> extends ARefreshAction<ItemsPanel, N, V, C> {

	protected SearchFilter getSearchFilter(AINode childNode) {
		return getItemService().getSearchFilter(
				childNode.getDepartment(),
				childNode.getCategory(),
				childNode.getItemType(), childNode.getPeriod());
	}

	@Override
	public void reloadNode() {
		if (getNode().getValue() instanceof AObject && ((AObject) getNode().getValue()).getId() != null)
			return;
		getNode().setAmount(getItemService().getSumBy(getItemService().getSearchFilter(
				getNode().getDepartment(),
				getNode().getCategory(),
				getNode().getItemType(),
				getNode().getPeriod())));
	}
}
