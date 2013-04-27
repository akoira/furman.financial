package by.dak.furman.financial.swing.category;

import by.dak.common.lang.StringValueAnnotationProcessor;
import by.dak.furman.financial.Period;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 12:36 PM
 */
public class PeriodNode extends ACategoryNode<Period>
{

    public String getName()
    {
        return StringValueAnnotationProcessor.getProcessor().convert(getValue());
    }

    public PeriodNode()
    {
        setAllowsChildren(true);
    }
}

