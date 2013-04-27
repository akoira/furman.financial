package by.dak.furman.financial.swing.category.action;

import by.dak.common.swing.treetable.ATreeTableNode;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.app.AppConfig;
import by.dak.furman.financial.service.ICategoryService;
import by.dak.furman.financial.swing.category.CategoryNode;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:59 PM
 */
public class DeleteCategory extends ACAction
{
    private CategoryNode categoryNode;
    private Category category;

    private ICategoryService categoryService = AppConfig.getAppConfig().getCategoryService();

    @Override
    protected void before()
    {
        category = categoryNode.getValue();
        categoryService.delete(category);
    }

    @Override
    protected boolean validate()
    {
        //not new cat node
        return categoryNode.getParent() != getRootNode();
    }

    @Override
    protected void makeAction()
    {
        NodeIterator iterator = new NodeIterator()
        {
            @Override
            protected boolean action(ATreeTableNode child)
            {
                if (child.getValue() == category)
                    getModel().removeNodeFromParent(child);
                return true;
            }
        };
        iterator.iterate(getRootNode());
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
