package by.dak.furman.financial.swing;

import by.dak.common.lang.ItemList;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.Department;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.Period;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 8:47 PM
 */
public abstract class ATreeTableNode<V, C extends ATreeTableNode> extends AbstractMutableTreeTableNode implements ItemList<C> {
	public static final String PROPERTY_amount = Item.PROPERTY_amount;

	private ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(getClass());

	private List<Property> properties = new ArrayList<Property>();

	private Department department;
	private Category category;
	private Period period;
	private String name;
	private BigDecimal amount = BigDecimal.ZERO;


	public V getValue() {
		return (V) getUserObject();
	}

	public void setValue(V value) {
		setUserObject(value);
	}

	@Override
	public Object getValueAt(int column) {
		try {
			Property property = properties.get(column);
			return new PropertyUtilsBean().getProperty(this, property.getKey());
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public void setValueAt(Object aValue, int column) {
		try {
			Property property = properties.get(column);
			BeanUtilsBean.getInstance().setProperty(this, property.getKey(), aValue);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public int getColumnCount() {
		return properties.size();
	}

	@Override
	public boolean isEditable(int column) {
		return properties.get(column).isEditable();
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties.clear();
		this.properties.addAll(properties);
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void fillChildNode(ATreeTableNode child) {
		if (child.getCategory() == null)
			child.setCategory(getCategory());
		if (child.getPeriod() == null)
			child.setPeriod(getPeriod());
		if (child.getDepartment() == null)
			child.setDepartment(getDepartment());
		child.setProperties(createProperties(child.getValue()));

	}

	protected abstract List<Property> createProperties(Object value);

	public abstract List<String> getColumnIdentifiers();

	public String getColumnName(String id) {
		return getResourceMap().getString(String.format("column.%s", id));
	}


	public ResourceMap getResourceMap() {
		return resourceMap;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}


	@Override
	public C get(int i) {
		return (C) getChildAt(i);
	}

	@Override
	public int size() {
		return getChildCount();
	}

	public boolean isTransient() {
		return false;
	}
}
