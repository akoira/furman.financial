package by.dak.furman.financial.swing.category;

import by.dak.common.lang.StringValue;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.converter.Category2StringConverter;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 4:39 PM
 */
@StringValue(converterClass = Category2StringConverter.class)
public class CategoryNode extends ACNode<Category>
{

    public CategoryNode()
    {
        setAllowsChildren(false);
    }

    public String getName()
    {
        return getValue().getName();
    }

    public void setName(String value)
    {
        getValue().setName(value);
    }
}
