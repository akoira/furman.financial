package by.dak.furman.financial.converter;

import by.dak.common.lang.ToStringConverter;
import by.dak.furman.financial.Period;

import java.text.SimpleDateFormat;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 4:54 PM
 */
public class Period2StringConverter implements ToStringConverter<Period>
{
    @Override
    public String convert(Period period)
    {
        switch (period.getPeriodType())
        {

            case YEAR:
                SimpleDateFormat format = new SimpleDateFormat("YYYY");
                return format.format(period.getStartDate());

            case MONTH:
                format = new SimpleDateFormat("MMMM");
                return format.format(period.getStartDate());
            case ALL:
                return "ALL";
            default:
                throw new IllegalArgumentException();
        }
    }
}
