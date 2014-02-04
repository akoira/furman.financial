package by.dak.furman.financial.swing.category;

import by.dak.furman.financial.AObject;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.Department;
import by.dak.furman.financial.Period;
import by.dak.furman.financial.swing.ATreeTableNode;
import by.dak.furman.financial.swing.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 6:24 PM
 */
public abstract class ACNode<V> extends ATreeTableNode<V, ACNode> {
	protected List<Property> createProperties(Object value) {
		ArrayList<Property> properties = new ArrayList<Property>();
		if (value instanceof Period) {
			properties.add(Property.valueOf(AObject.PROPERTY_name, false));
			properties.add(Property.valueOf(ACNode.PROPERTY_amount, false));
		} else if (value instanceof Category) {
			properties.add(Property.valueOf(AObject.PROPERTY_name, true));
			properties.add(Property.valueOf(ACNode.PROPERTY_amount, false));
		} else if (value instanceof Department) {
			properties.add(Property.valueOf(AObject.PROPERTY_name, true));
			properties.add(Property.valueOf(ACNode.PROPERTY_amount, false));
		} else
			throw new IllegalArgumentException();
		return properties;
	}

	@Override
	public List<String> getColumnIdentifiers() {
		return Arrays.asList(AObject.PROPERTY_name, ACNode.PROPERTY_amount);
	}
}
