package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Period;
import by.dak.furman.financial.PeriodType;
import by.dak.furman.financial.swing.category.DepartmentNode;
import by.dak.furman.financial.swing.category.YearNode;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 2:42 PM
 */
public class RefreshDepartmentNode extends ACRefreshAction<DepartmentNode, Period, YearNode> {

	@Override
	public List<Period> getChildValues() {
		Calendar today = Calendar.getInstance();

		ArrayList<Period> result = new ArrayList<Period>();
		boolean next = true;
		int index = -1;
		while (next) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.addYears(today.getTime(), index));
			Period period = createYearPeriod(calendar, false);
			if (getItemService().getSumBy(getItemService().getSearchFilter(null, null, null, period)).compareTo(BigDecimal.ZERO) == 1) {
				result.add(period);
				index -= 1;
			} else {
				next = false;
			}
		}

		result.add(createYearPeriod(today, true));

		return result;
	}

	@Override
	public YearNode createChildNode() {
		return new YearNode();
	}

	@Override
	public void refreshChildNode(YearNode childNode) {
		RefreshYearNode refreshYearNode = new RefreshYearNode();
		refreshYearNode.setNode(childNode);
		refreshYearNode.setPanel(getPanel());
		refreshYearNode.action();
	}

	@Override
	protected void after() {
		super.after();
		if (getNode().getPeriod().isCurrent()) {
			AddNewCategory addNewCategory = new AddNewCategory();
			addNewCategory.setNode(getNode());
			addNewCategory.setPanel(getPanel());
			addNewCategory.action();
		}
	}

	@Override
	public void reloadNode() {
		if (getNode().getValue().getId() == null)
			getNode().setAmount(BigDecimal.ZERO);
		else
			super.reloadNode();
	}


	private Period createYearPeriod(Calendar calendar, boolean current) {
		Period period = new Period();
		period.setPeriodType(PeriodType.YEAR);
		period.setCurrent(current);

		calendar = DateUtils.truncate(calendar, Calendar.YEAR);

		period.setStartDate(calendar.getTime());

		calendar.add(Calendar.YEAR, 1);
		period.setEndDate(calendar.getTime());
		return period;
	}
}
