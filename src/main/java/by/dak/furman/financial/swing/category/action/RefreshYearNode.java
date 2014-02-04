package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Period;
import by.dak.furman.financial.PeriodType;
import by.dak.furman.financial.swing.category.MonthNode;
import by.dak.furman.financial.swing.category.YearNode;
import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 3:59 PM
 */
public class RefreshYearNode extends ACRefreshAction<YearNode, Period, MonthNode> {

	private boolean isSameMonth(Calendar calendar1, Calendar calendar2) {
		return calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
				calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
	}


	@Override
	public List<Period> getChildValues() {
		ArrayList<Period> periods = new ArrayList<Period>();

		for (int month = 0; month < 12; month++) {
			Period period = new Period();
			period.setPeriodType(PeriodType.MONTH);
			Calendar current = Calendar.getInstance();

			Calendar start = Calendar.getInstance();
			start.setTime(getNode().getValue().getStartDate());
			start = DateUtils.truncate(start, Calendar.MONTH);
			start.set(Calendar.MONTH, month);
			period.setCurrent(isSameMonth(current, start));

			period.setStartDate(start.getTime());
			start.add(Calendar.MONTH, 1);
			period.setEndDate(start.getTime());

			periods.add(period);
		}
		return periods;
	}

	@Override
	public MonthNode createChildNode() {
		return new MonthNode();
	}

	@Override
	public void refreshChildNode(MonthNode childNode) {
		RefreshMonthNode action = new RefreshMonthNode();
		action.setNode(childNode);
		action.setPanel(getPanel());
		action.action();
	}
}
