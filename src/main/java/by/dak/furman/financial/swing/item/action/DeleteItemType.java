package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.swing.item.ItemTypeNode;

/**
 * User: akoyro
 * Date: 4/29/13
 * Time: 12:09 AM
 */
public class DeleteItemType extends AIDeleteAction<ItemTypeNode> {
	@Override
	protected void makeAction() {
		getItemTypeService().delete(getNode().getValue());
		getModel().removeNodeFromParent(getNode());
	}

	@Override
	protected boolean validate() {
		if (getNode().getValue().getId() == null) {
			setMessage("ItemType.id is null");
			return false;
		}
//		if (getItemService().getAllBy(getNode().getValue()).size() > 0) {
//			setMessage(String.format("Item > 0  for itemType %s", getNode().getValue().getName()));
//			return false;
//		}
		return true;
	}

}