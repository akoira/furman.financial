package by.dak.furman.financial.swing.category;

import by.dak.furman.financial.Category;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 4:39 PM
 */
public class CategoryNode extends ACNode<Category> {

	public CategoryNode() {
		setAllowsChildren(false);
	}

	public String getName() {
		return getValue().getName();
	}

	public void setName(String value) {
		getValue().setName(value);
	}

	@Override
	public Category getCategory() {
		return super.getValue();
	}

	@Override
	public void setCategory(Category category) {
		super.setValue(category);
	}
}
