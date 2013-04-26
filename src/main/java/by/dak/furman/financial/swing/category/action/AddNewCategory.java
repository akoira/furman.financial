package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.swing.category.CategoryNode;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:08 PM
 */
public class AddNewCategory extends ACategoryAction
{
    public static final String RESOURCE_KEY_newCategoryName = "newCategoryName";

    @Override
    protected void makeAction()
    {
        Category category = new Category();
        category.setName(getResourceMap().getString(RESOURCE_KEY_newCategoryName));
        CategoryNode categoryNode = AddCategory.createCategoryNode(category);
        getModel().insertNodeInto(categoryNode, getRootNode(), 0);
    }
}
