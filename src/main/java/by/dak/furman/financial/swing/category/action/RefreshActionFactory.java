package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.swing.category.*;

/**
 * User: akoyro
 * Date: 4/28/13
 * Time: 11:25 PM
 */
public class RefreshActionFactory
{
    private CategoriesPanel panel;

    public ACRefreshAction getActionBy(ACNode node)
    {
        ACRefreshAction action;
        if (node instanceof CategoryNode)
            action = new RefreshCategoryNode();
        else if (node instanceof MonthNode)
            action = new RefreshMonthNode();
        else if (node instanceof YearNode)
            action = new RefreshYearNode();
        else if (node instanceof RootNode)
            action = new RefreshRootNode();
        else
            throw new IllegalArgumentException();
        action.setNode(node);
        action.setPanel(getPanel());
        return action;
    }

    public CategoriesPanel getPanel()
    {
        return panel;
    }

    public void setPanel(CategoriesPanel panel)
    {
        this.panel = panel;
    }
}
