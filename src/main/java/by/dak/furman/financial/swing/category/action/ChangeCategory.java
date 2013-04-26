package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.swing.category.CategoriesPanel;
import by.dak.furman.financial.swing.category.CategoryNode;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 5:30 PM
 */
public class ChangeCategory extends ACategoryAction
{
    private CategoryNode categoryNode;

    @Override
    protected void makeAction()
    {
    }

    public static ChangeCategory valueOf(CategoryNode categoryNode, CategoriesPanel categoriesPanel)
    {
        ChangeCategory action = new ChangeCategory();
        action.setCategoryNode(categoryNode);
        action.setCategoriesPanel(categoriesPanel);
        return action;
    }

    public CategoryNode getCategoryNode()
    {
        return categoryNode;
    }

    public void setCategoryNode(CategoryNode categoryNode)
    {
        this.categoryNode = categoryNode;
    }
}
