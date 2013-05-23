package by.dak.furman.financial.swing.item.action;

import by.dak.common.lang.Utils;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.swing.item.AINode;
import by.dak.furman.financial.swing.item.ItemNode;
import by.dak.furman.financial.swing.item.ItemTypeNode;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.util.Comparator;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 1:43 PM
 */
public class SaveItemType extends AIAction<ItemTypeNode> {
	private AINode parentNode;
	private ItemType itemType;
	private boolean isNew;

	@Override
	protected void before() {
		itemType = getNode().getItemType();
		parentNode = (AINode) getNode().getParent();
	}

	@Override
	protected void makeAction() {
		if (itemType.getId() == null) {
			getModel().removeNodeFromParent(getNode());
			getItemTypeService().add(itemType);

			ItemTypeNode itemTypeNode = new ItemTypeNode();
			itemTypeNode.setValue(itemType);

			parentNode.fillChildNode(itemTypeNode);
			setNode(itemTypeNode);

			getModel().insertNodeInto(getNode(), parentNode, calcIndexForNewItemTypeNode());

			RefreshItemTypeNode refreshItemTypeNode = new RefreshItemTypeNode();
			refreshItemTypeNode.setNode(getNode());
			refreshItemTypeNode.setPanel(getPanel());
			refreshItemTypeNode.action();

			selectNewItemNode();

			AddNewItemType addNewItemType = new AddNewItemType();
			addNewItemType.setPanel(getPanel());
			addNewItemType.setNode((AINode) getNode().getParent());
			addNewItemType.action();
		} else
			getItemTypeService().save(itemType);
	}

	private int calcIndexForNewItemTypeNode() {
		return Utils.getIndexInSortedList(parentNode, getNode(), new Comparator<AINode>() {
			@Override
			public int compare(AINode o1, AINode o2) {
				return o1.getItemType().getName().compareTo(o2.getItemType().getName());
			}
		});
	}

	private void selectNewItemNode() {
		Runnable runnable = new Runnable() {
			public void run() {
				ItemNode itemNode = (ItemNode) getNode().getChildAt(getNode().getChildCount() - 1);
				selectColumn(itemNode, Item.PROPERTY_amount);
			}
		};
		SwingUtilities.invokeLater(runnable);
	}

	@Override
	protected boolean validate() {
		if (StringUtils.trimToNull(itemType.getName()) == null) {
			setMessage("ItemType.name cannot be null");
			return false;
		}
		ItemType found = getItemTypeService().getBy(itemType.getCategory(), itemType.getName());
		if (found != null && (itemType.getId() == null || itemType.getId().equals(found.getId()))) {
			setMessage(String.format("itemType with name %s already exist", itemType.getName()));
			return false;
		}
		return true;
	}
}
