package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Period;
import by.dak.furman.financial.PeriodType;
import by.dak.furman.financial.swing.category.DepartmentNode;
import by.dak.furman.financial.swing.category.YearNode;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 2:42 PM
 */
public class RefreshDepartmentNode extends ACRefreshAction<DepartmentNode, Period, YearNode> {

	@Override
	public List<Period> getChildValues() {
		return Collections.singletonList(createYearPeriod(new Date()));
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


	private Period createYearPeriod(Date date) {
		Period period = new Period();
		period.setPeriodType(PeriodType.YEAR);
		period.setCurrent(true);

		Calendar calendar = Calendar.getInstance();
		calendar = DateUtils.truncate(calendar, Calendar.YEAR);

		period.setStartDate(calendar.getTime());

		calendar.add(Calendar.YEAR, 1);
		period.setEndDate(calendar.getTime());
		return period;
	}
}
