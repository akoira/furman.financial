package by.dak.furman.financial.swing.category;

import by.dak.common.swing.treetable.ATreeTableNode;
import by.dak.common.swing.treetable.Property;
import by.dak.furman.financial.AObject;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.Period;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 6:24 PM
 */
public abstract class ACNode<V> extends ATreeTableNode<V, ACNode>
{
    public static final String PROPERTY_amount = "amount";

    private BigDecimal amount = BigDecimal.ZERO;

    private Category category;
    private Period period;
    private String name;

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public Period getPeriod()
    {
        return period;
    }

    public void setPeriod(Period period)
    {
        this.period = period;
    }

    public void fillChildNode(ACNode child)
    {
        child.setCategory(getCategory());
        child.setPeriod(getPeriod());
        child.setProperties(createProperties(child.getValue()));
    }


    public static List<Property> createProperties(Object value)
    {
        ArrayList<Property> properties = new ArrayList<Property>();
        if (value instanceof Period)
        {
            properties.add(Property.valueOf(AObject.PROPERTY_name, false));
            properties.add(Property.valueOf(ACNode.PROPERTY_amount, false));
        }
        else if (value instanceof Category)
        {
            properties.add(Property.valueOf(AObject.PROPERTY_name, true));
            properties.add(Property.valueOf(ACNode.PROPERTY_amount, false));
        }
        else
            throw new IllegalArgumentException();
        return properties;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
