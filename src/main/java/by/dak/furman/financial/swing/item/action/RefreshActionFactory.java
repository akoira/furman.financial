package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.swing.item.*;

/**
 * User: akoyro
 * Date: 4/28/13
 * Time: 11:25 PM
 */
public class RefreshActionFactory {
	private ItemsPanel panel;

	AIRefreshAction getActionBy(AINode node) {
		AIRefreshAction action;
		if (node instanceof ItemNode)
			action = new RefreshItemNode();
		else if (node instanceof ItemTypeNode)
			action = new RefreshItemTypeNode();
		else if (node instanceof CategoryNode)
			action = new RefreshCategoryNode();
		else if (node instanceof RootNode)
			action = new RefreshRootNode();
		else
			throw new IllegalArgumentException();
		action.setNode(node);
		action.setPanel(getPanel());
		return action;
	}

	public ItemsPanel getPanel() {
		return panel;
	}

	public void setPanel(ItemsPanel panel) {
		this.panel = panel;
	}
}
