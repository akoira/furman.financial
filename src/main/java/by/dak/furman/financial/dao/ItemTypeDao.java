package by.dak.furman.financial.dao;

import by.dak.furman.financial.ItemType;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 5:46 PM
 */
@Repository
public class ItemTypeDao extends ADao<ItemType> implements IItemTypeDao {

	public void delete(ItemType object) {
		object.setModified(new Date());
		object.setDeleted(Boolean.TRUE);
		getSessionFactory().getCurrentSession().update(object);
	}
}
