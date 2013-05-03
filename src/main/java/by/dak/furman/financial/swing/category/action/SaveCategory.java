package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.category.APeriodNode;
import by.dak.furman.financial.swing.category.CategoryNode;
import by.dak.furman.financial.swing.category.DepartmentNode;
import org.apache.commons.lang3.StringUtils;

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
		return StringUtils.stripToNull(category.getName()) != null && getCategoryService().getByName(category.getName()) == null;
	}

	private void addCategoryNode() {
		int count = getRootNode().getChildCount();
		for (int i = 0; i < count; i++) {
			if (getRootNode().getChildAt(i) instanceof APeriodNode) {
				APeriodNode yearNode = (APeriodNode) getRootNode().getChildAt(i);
				int yCount = yearNode.getChildCount();
				for (int k = 0; k < yCount; k++) {
					APeriodNode monthNode = (APeriodNode) yearNode.getChildAt(k);
					int mCount = monthNode.getChildCount();
					for (int m = 0; m < mCount; m++) {
						DepartmentNode dChild = (DepartmentNode) monthNode.getChildAt(m);
						if (departmentNode.getDepartment().getId().equals(dChild.getDepartment().getId())) {
							CategoryNode categoryNode = createCategoryNode(category, dChild);
							getModel().insertNodeInto(categoryNode, dChild, dChild.getChildCount());
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
		}
	}

	public static CategoryNode createCategoryNode(Category category, ACNode parent) {
		CategoryNode categoryNode = new CategoryNode();
		categoryNode.setValue(category);
		parent.fillChildNode(categoryNode);
		return categoryNode;
	}
}
