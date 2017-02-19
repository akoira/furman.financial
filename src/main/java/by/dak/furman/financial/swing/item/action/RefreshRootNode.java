package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.AObject;
import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.category.APeriodNode;
import by.dak.furman.financial.swing.category.DepartmentNode;
import by.dak.furman.financial.swing.item.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:03 PM
 */
public class RefreshRootNode extends AIRefreshAction<RootNode, AObject, AINode> {

	//can be null
	private ACNode acNode;

	private RefreshRootNode() {

	}

	@Override
	protected void before() {
		super.before();
		if (acNode != null) {
			getNode().setDepartment(acNode.getDepartment());
			getNode().setCategory(acNode.getCategory());
			getNode().setPeriod(acNode.getPeriod());
			getNode().setItemType(null);
			getNode().setValue(acNode);
		}
	}

	@Override
	public List<AObject> getChildValues() {
		if (acNode == null || acNode.isTransient())
			return Collections.emptyList();
		if (acNode instanceof DepartmentNode) {
			return new ArrayList<>(getCategoryService().getAllBy(getNode().getDepartment()));
		} else if (acNode instanceof by.dak.furman.financial.swing.category.CategoryNode) {
			ArrayList<AObject> result = new ArrayList<AObject>();
			List<ItemType> itemTypes = getItemTypeService().getAllBy(getNode().getCategory());
			for (ItemType itemType : itemTypes) {
				if (!itemType.getDeleted())
					result.add(itemType);
				else {
					BigDecimal bigDecimal = getItemService().getSumBy(getItemService().getSearchFilter(null, null, itemType, getNode().getPeriod()));
					if (bigDecimal.compareTo(BigDecimal.ZERO) > 0)
						result.add(itemType);
				}
			}
			return result;
		} else if (acNode instanceof APeriodNode) {
			return new ArrayList<>(getCategoryService().getAllBy(getNode().getDepartment()));
		}
		return Collections.emptyList();

	}


	@Override
	public AINode createChildNode() {
		if (acNode instanceof DepartmentNode)
			return new CategoryNode();
		else if (acNode instanceof by.dak.furman.financial.swing.category.CategoryNode)
			return new ItemTypeNode();
		else if (acNode instanceof APeriodNode)
			return new CategoryNode();
		return null;
	}

	@Override
	public void refreshChildNode(AINode childNode) {
		if (acNode instanceof DepartmentNode || acNode instanceof APeriodNode) {
			childNode.setAmount(getItemService().getSumBy(getSearchFilter(childNode)));
			RefreshCategoryNode refreshCategoryNode = new RefreshCategoryNode();
			refreshCategoryNode.setNode((CategoryNode) childNode);
			refreshCategoryNode.setPanel(getPanel());
			refreshCategoryNode.action();
		} else if (acNode instanceof by.dak.furman.financial.swing.category.CategoryNode) {
			childNode.setAmount(getItemService().getSumBy(getSearchFilter(childNode)));
			RefreshItemTypeNode refreshItemTypeNode = new RefreshItemTypeNode();
			refreshItemTypeNode.setNode((ItemTypeNode) childNode);
			refreshItemTypeNode.setPanel(getPanel());
			refreshItemTypeNode.action();
		}
	}

	@Override
	protected void makeAction() {
		super.makeAction();

		if (getNode().getCategory() != null && getNode().getCategory().getId() != null) {
			reloadNode();

			AddNewItemType addNewItemType = new AddNewItemType();
			addNewItemType.setNode(getNode());
			addNewItemType.setPanel(getPanel());
			addNewItemType.action();
		}
	}


	public ACNode getACNode() {
		return acNode;
	}

	public void setACNode(ACNode acNode) {
		this.acNode = acNode;
	}


	public static RefreshRootNode valueOf(ItemsPanel panel, ACNode node) {
		RefreshRootNode refreshRootNode = new RefreshRootNode();
		refreshRootNode.setPanel(panel);
		refreshRootNode.setNode((by.dak.furman.financial.swing.item.RootNode) panel.getModel().getRoot());
		if (node != null && !node.isTransient()) {
			refreshRootNode.setACNode(node);
		}
		return refreshRootNode;
	}
}
