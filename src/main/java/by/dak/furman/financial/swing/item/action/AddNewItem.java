package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.Item;
import by.dak.furman.financial.swing.item.ItemNode;
import by.dak.furman.financial.swing.item.ItemTypeNode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 9:50 PM
 */
public class AddNewItem extends AIAction<ItemTypeNode> {
	private ItemNode result;

	@Override
	protected void makeAction() {
		Item item = new Item();
		item.setName(getNode().getItemType().getName());
		item.setItemType(getNode().getItemType());
		if (getNode().getPeriod() == null || getNode().getPeriod().isCurrent())
			item.setCreated(new Date());
		else
			item.setCreated(getNode().getPeriod().getStartDate());
		item.setAmount(BigDecimal.ZERO);

		ItemNode node = new ItemNode();
		node.setValue(item);
		getNode().fillChildNode(node);
		getModel().insertNodeInto(node, getNode(), getNode().getChildCount());

		result = node;
	}


	public ItemNode getResult() {
		return result;
	}
}

