package by.dak.furman.financial.converter;

import by.dak.furman.financial.Period;

import javax.swing.*;
import java.text.SimpleDateFormat;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 4:54 PM
 */
public class PeriodRenderer extends AObjectRenderer<Period> {
	@Override
	public String convert(Period period) {
		switch (period.getPeriodType()) {

			case YEAR:
				SimpleDateFormat format = new SimpleDateFormat("yyyy");
				return format.format(period.getStartDate());
			case MONTH:
				format = new SimpleDateFormat("MMMM");
				return format.format(period.getStartDate());
			case ALL:
				return getResourceMap().getString("label.all");
			default:
				throw new IllegalArgumentException();
		}
	}

	@Override
	public Icon convert2Icon(Period entity) {
		return getResourceMap().getIcon("icon");
	}
}
