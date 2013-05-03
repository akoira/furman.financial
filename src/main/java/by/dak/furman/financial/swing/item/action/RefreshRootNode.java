package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.AObject;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.item.AINode;
import by.dak.furman.financial.swing.item.CategoryNode;
import by.dak.furman.financial.swing.item.ItemTypeNode;
import by.dak.furman.financial.swing.item.RootNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:03 PM
 */
public class RefreshRootNode extends AIRefreshAction<RootNode, AObject, AINode> {

	private ACNode acNode;


	@Override
	protected void before() {
		super.before();
		getNode().setCategory(acNode.getCategory());
		getNode().setValue(acNode);
		getNode().setPeriod(acNode.getPeriod());
		getNode().setItemType(null);
	}

	@Override
	public List<AObject> getChildValues() {
		if (getNode().getCategory() == null)
			return new ArrayList<AObject>(getCategoryService().getAll());
		else if (getNode().getCategory().getId() != null)
			return new ArrayList<AObject>(getItemTypeService().getAllBy(getNode().getCategory()));
		else
			return Collections.emptyList();
	}


	@Override
	public AINode createChildNode() {
		if (getNode().getCategory() == null)
			return new CategoryNode();
		else
			return new ItemTypeNode();
	}

	@Override
	public void refreshChildNode(AINode childNode) {
		childNode.setAmount(getItemService().getSumBy(getSearchFilter(childNode)));
		if (getNode().getCategory() == null) {
			RefreshCategoryNode refreshCategoryNode = new RefreshCategoryNode();
			refreshCategoryNode.setNode((CategoryNode) childNode);
			refreshCategoryNode.setPanel(getPanel());
			refreshCategoryNode.action();
		} else {
			RefreshItemTypeNode refreshItemTypeNode = new RefreshItemTypeNode();
			refreshItemTypeNode.setNode((ItemTypeNode) childNode);
			refreshItemTypeNode.setPanel(getPanel());
			refreshItemTypeNode.action();
		}
	}

	@Override
	protected void after() {
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
}
