package by.dak.furman.financial;

import by.dak.common.lang.StringValue;
import by.dak.furman.financial.converter.PeriodRenderer;

import java.util.Date;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 4:52 PM
 */
@StringValue(converterClass = PeriodRenderer.class)
public class Period extends AObject {
	private Date startDate;
	private Date endDate;
	private PeriodType periodType;
	private boolean current;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public PeriodType getPeriodType() {
		return periodType;
	}

	public void setPeriodType(PeriodType periodType) {
		this.periodType = periodType;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}
}
