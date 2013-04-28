package by.dak.furman.financial.converter;

import by.dak.common.lang.ToStringConverter;
import by.dak.furman.financial.ItemType;

/**
 * User: akoyro
 * Date: 4/23/13
 * Time: 11:25 AM
 */
public class ItemType2StringConverter implements ToStringConverter<ItemType>
{
    @Override
    public String convert(ItemType entity)
    {
        if (entity.getId() == null)
            return "new type...";
        return entity.getName();
    }
}
