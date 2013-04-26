package by.dak.furman.financial;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 11:00 AM
 */

@Entity
public class Item extends AObject
{
    public static String PROPERTY_amount = "amount";
    public static String PROPERTY_category = "category";
    public static String PROPERTY_itemType = "itemType";


    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ItemType itemType;

    @Column(nullable = false)
    private BigDecimal amount;

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public ItemType getItemType()
    {
        return itemType;
    }

    public void setItemType(ItemType itemType)
    {
        this.itemType = itemType;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
}
