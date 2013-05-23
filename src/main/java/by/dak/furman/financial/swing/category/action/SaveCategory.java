package by.dak.furman.financial.swing.category.action;

import by.dak.common.lang.Utils;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.category.APeriodNode;
import by.dak.furman.financial.swing.category.CategoryNode;
import by.dak.furman.financial.swing.category.DepartmentNode;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 12:27 PM
 */
public class SaveCategory extends ACAction<CategoryNode> {
	private Category category;
	private DepartmentNode departmentNode;

	@Override
	protected void before() {
		category = getNode().getValue();
		departmentNode = (DepartmentNode) getNode().getParent();
	}

	protected void makeAction() {
		if (category.getId() == null) {
			getCategoryService().add(category);
			getModel().removeNodeFromParent(getNode());

			addCategoryNode();

			AddNewCategory addNewCategory = new AddNewCategory();
			addNewCategory.setNode(departmentNode);
			addNewCategory.setPanel(getPanel());
			addNewCategory.action();
		} else {
			getCategoryService().save(category);
		}

	}

	protected boolean validate() {
		if (StringUtils.stripToNull(category.getName()) == null) {
			setMessage("Category.name cannot be null");
			return false;
		}
		Category found = getCategoryService().getByDepartmentName(departmentNode.getDepartment(), category.getName());
		if (found != null && (category.getId() == null || category.getId().equals(found.getId()))) {
			setMessage(String.format("Category with name %s already exist", category.getName()));
			return false;
		}
		return true;
	}

	private void addCategoryNode() {
		int count = departmentNode.getChildCount();
		for (int i = 0; i < count; i++) {
			if (departmentNode.getChildAt(i) instanceof APeriodNode) {
				APeriodNode yearNode = (APeriodNode) departmentNode.getChildAt(i);
				int yCount = yearNode.getChildCount();
				for (int k = 0; k < yCount; k++) {
					APeriodNode monthNode = (APeriodNode) yearNode.getChildAt(k);
					CategoryNode categoryNode = createCategoryNode(category, monthNode);
					int index = calcIndexForNewCategoryNode(monthNode, categoryNode);
					getModel().insertNodeInto(categoryNode, monthNode, index);
					if (monthNode.getPeriod().isCurrent()) {
						ExpandNode expandNode = new ExpandNode();
						expandNode.setPanel(getPanel());
						expandNode.setNode(categoryNode);
						expandNode.action();
					}
				}
			}
		}
	}

	private int calcIndexForNewCategoryNode(APeriodNode monthNode, CategoryNode categoryNode) {
		return Utils.getIndexInSortedList(monthNode, categoryNode, new Comparator<ACNode>() {
			@Override
			public int compare(ACNode o1, ACNode o2) {
				return o1.getCategory().getName().compareTo(o2.getCategory().getName());
			}
		});
	}


	public static CategoryNode createCategoryNode(Category category, ACNode parent) {
		CategoryNode categoryNode = new CategoryNode();
		categoryNode.setValue(category);
		parent.fillChildNode(categoryNode);
		return categoryNode;
	}
}
