package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.AObject;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.category.APeriodNode;
import by.dak.furman.financial.swing.category.DepartmentNode;
import by.dak.furman.financial.swing.item.*;

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
		getNode().setDepartment(acNode.getDepartment());
		getNode().setCategory(acNode.getCategory());
		getNode().setPeriod(acNode.getPeriod());
		getNode().setItemType(null);
		getNode().setValue(acNode);
	}

	@Override
	public List<AObject> getChildValues() {
		if (acNode instanceof DepartmentNode) {
			if (getNode().getDepartment() != null && getNode().getDepartment().getId() != null)
				return new ArrayList<AObject>(getCategoryService().getAllBy(getNode().getDepartment()));
		} else if (acNode instanceof by.dak.furman.financial.swing.category.CategoryNode) {
			if (getNode().getCategory() != null && getNode().getCategory().getId() != null)
				return new ArrayList<AObject>(getItemTypeService().getAllBy(getNode().getCategory()));
		} else if (acNode instanceof APeriodNode)
			return new ArrayList<AObject>(getItemService().getAllBy(getItemService().getSearchFilter(
					acNode.getDepartment(),
					acNode.getCategory(),
					null,
					acNode.getPeriod())));
		return Collections.emptyList();

	}


	@Override
	public AINode createChildNode() {
		if (acNode instanceof DepartmentNode)
			return new CategoryNode();
		else if (acNode instanceof by.dak.furman.financial.swing.category.CategoryNode)
			return new ItemTypeNode();
		else if (acNode instanceof APeriodNode)
			return new ItemNode();
		return null;
	}

	@Override
	public void refreshChildNode(AINode childNode) {
		if (acNode instanceof DepartmentNode) {
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
