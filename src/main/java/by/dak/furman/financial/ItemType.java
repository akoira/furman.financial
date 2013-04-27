package by.dak.furman.financial;

import by.dak.common.lang.StringValue;
import by.dak.furman.financial.converter.ItemType2StringConverter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 11:53 AM
 */
@Entity
@StringValue(converterClass = ItemType2StringConverter.class)
public class ItemType extends AObject
{
    public static String PROPERTY_category = "category";

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }
}
