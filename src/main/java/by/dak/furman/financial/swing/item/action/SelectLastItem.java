package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.Item;
import by.dak.furman.financial.swing.item.ItemNode;
import by.dak.furman.financial.swing.item.ItemTypeNode;

public class SelectLastItem extends AIAction<ItemTypeNode> {

	@Override
	protected void makeAction() {
		if (getNode().getChildCount() > 0) {
			ItemNode lastNode = (ItemNode) getNode().get(getNode().getChildCount() - 1);
			selectColumn(lastNode, Item.PROPERTY_amount);
		}
	}
}
