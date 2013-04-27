package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.swing.category.CategoryNode;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 5:30 PM
 */
public class ChangeCategory extends ACAction
{
    private CategoryNode categoryNode;

    @Override
    protected void makeAction()
    {
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
