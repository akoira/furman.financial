package by.dak.furman.financial.swing.item;

import by.dak.furman.financial.Category;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 11:48 AM
 */
public class CategoryNode extends AINode<Category> {
	@Override
	public void setName(String name) {
		getValue().setName(name);
	}

	@Override
	public String getName() {
		return getValue().getName();
	}

	@Override
	public Category getCategory() {
		return getValue();
	}

	@Override
	public void setCategory(Category category) {
		setValue(category);
	}
}
