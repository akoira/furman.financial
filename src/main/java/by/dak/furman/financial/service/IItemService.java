package by.dak.furman.financial.service;

import by.dak.furman.financial.Item;
import by.dak.furman.financial.ItemType;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 12:30 PM
 */
public interface IItemService extends IService<Item>
{
    List<Item> getAllBy(ItemType itemType);
}
