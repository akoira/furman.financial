package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.swing.category.CategoryNode;
import by.dak.furman.financial.swing.category.RootNode;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:08 PM
 */
public class AddNewCategory extends ACAction<RootNode>
{
    @Override
    protected void makeAction()
    {
        Category category = new Category();
        CategoryNode categoryNode = SaveCategory.createCategoryNode(category, (RootNode) getRootNode());
        getModel().insertNodeInto(categoryNode, getRootNode(), getRootNode().getChildCount());
    }
}
