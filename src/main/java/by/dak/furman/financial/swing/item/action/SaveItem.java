package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.Item;
import by.dak.furman.financial.swing.item.ItemNode;
import by.dak.furman.financial.swing.item.ItemTypeNode;

import java.math.BigDecimal;

/**
 * User: akoyro
 * Date: 4/28/13
 * Time: 11:04 PM
 */
public class SaveItem extends AIAction<ItemNode> {
	@Override
	protected void makeAction() {
		Item item = getNode().getValue();
		if (item.getId() == null) {
			getItemService().add(item);

			AddNewItem addNewItem = new AddNewItem();
			addNewItem.setNode((ItemTypeNode) getNode().getParent());
			addNewItem.setPanel(getPanel());
			addNewItem.action();

			if (addNewItem.getResult() != null) {
				selectColumn(addNewItem.getResult(), Item.PROPERTY_name);
			}
		} else
			getItemService().save(item);
	}


	@Override
	protected void after() {
		RefreshHierarchy refreshHierarchy = new RefreshHierarchy();
		refreshHierarchy.setNode(getNode());
		refreshHierarchy.setPanel(getPanel());
		refreshHierarchy.action();
	}

	@Override
	protected boolean validate() {
		return getNode().getValue().getAmount() != null &&
				getNode().getValue().getAmount().compareTo(BigDecimal.ZERO) != 0 &&
				getNode().getCreated() != null;
	}
}
