package by.dak.furman.financial.swing.category.action;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.Period;
import by.dak.furman.financial.PeriodType;
import by.dak.furman.financial.swing.ARefreshAction;
import by.dak.furman.financial.swing.category.CategoriesPanel;
import by.dak.furman.financial.swing.category.MonthNode;
import by.dak.furman.financial.swing.category.YearNode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 3:59 PM
 */
public class RefreshYearNode extends ARefreshAction<CategoriesPanel, YearNode, Period, MonthNode>
{
    @Override
    public List<Period> getChildValues()
    {
        ArrayList<Period> periods = new ArrayList<Period>();

        for (int month = 0; month < 12; month++)
        {
            Period period = new Period();
            period.setPeriodType(PeriodType.MONTH);
            Calendar calendar = Calendar.getInstance();
            int current = calendar.get(Calendar.MONTH);
            period.setCurrent(current == month);
            calendar.setTime(getNode().getValue().getStartDate());
            Period.resetTime(calendar);
            calendar.set(Calendar.MONTH, month);
            period.setStartDate(calendar.getTime());
            calendar.add(Calendar.MONTH, 1);
            period.setEndDate(calendar.getTime());
            periods.add(period);
        }
        return periods;
    }

    @Override
    public MonthNode createChildNode()
    {
        MonthNode node = new MonthNode();
        SearchFilter searchFilter = getItemService().getSearchFilter(getNode().getCategory(), null, getNode().getPeriod());
        node.setAmount(getItemService().getSumBy(searchFilter));
        return node;
    }

    @Override
    public void refreshChildNode(MonthNode childNode)
    {
        RefreshMonthNode action = new RefreshMonthNode();
        action.setNode(childNode);
        action.setPanel(getPanel());
        action.action();
    }
}
