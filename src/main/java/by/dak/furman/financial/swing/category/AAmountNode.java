package by.dak.furman.financial.swing.category;

import by.dak.common.swing.treetable.ATreeTableNode;

import java.math.BigDecimal;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 6:24 PM
 */
public abstract class AAmountNode<V> extends ATreeTableNode<V>
{
    public static final String PROPERTY_amount = "amount";

    private BigDecimal amount = BigDecimal.ZERO;

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
}
