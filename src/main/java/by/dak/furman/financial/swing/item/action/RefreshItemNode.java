package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.AObject;
import by.dak.furman.financial.swing.item.AINode;
import by.dak.furman.financial.swing.item.ItemNode;

import java.util.Collections;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 10:51 PM
 */
public class RefreshItemNode extends AIRefreshAction<ItemNode, AObject, AINode> {
	@Override
	public List<AObject> getChildValues() {
		return Collections.emptyList();
	}

	@Override
	public AINode createChildNode() {
		return null;
	}

	@Override
	public void refreshChildNode(AINode childNode) {
	}

	@Override
	public void reloadNode() {
	}
}
