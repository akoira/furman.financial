package by.dak.furman.financial.swing.category.action;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.swing.ARefreshAction;
import by.dak.furman.financial.swing.category.CategoriesPanel;
import by.dak.furman.financial.swing.category.CategoryNode;
import by.dak.furman.financial.swing.category.MonthNode;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 3:59 PM
 */
public class RefreshMonthNode extends ARefreshAction<CategoriesPanel, MonthNode, Category, CategoryNode>
{
    @Override
    public List<Category> getChildValues()
    {
        return getPanel().getAppConfig().getCategoryService().getAll();
    }

    @Override
    public CategoryNode createChildNode()
    {
        CategoryNode categoryNode = new CategoryNode();
        SearchFilter searchFilter = getItemService().getSearchFilter(getNode().getCategory(), null, getNode().getPeriod());
        categoryNode.setAmount(getItemService().getSumBy(searchFilter));
        return categoryNode;
    }

    @Override
    public void refreshChildNode(CategoryNode childNode)
    {

    }
}
