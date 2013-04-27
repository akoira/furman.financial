package by.dak.furman.financial.service;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.ItemType;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 12:30 PM
 */
public interface IItemTypeService extends IService<ItemType>
{
    List<ItemType> getAllBy(Category value);

    ItemType getBy(Category category, String name);
}
