package by.dak.furman.financial.converter;

import by.dak.common.lang.ToStringConverter;
import by.dak.furman.financial.Item;

/**
 * User: akoyro
 * Date: 4/23/13
 * Time: 11:25 AM
 */
public class Item2StringConverter implements ToStringConverter<Item>
{
    @Override
    public String convert(Item entity)
    {
        if (entity.getId() == null)
            return "new item...";
        return entity.getName();
    }
}
