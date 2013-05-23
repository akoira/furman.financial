package by.dak.furman.financial.swing.item;

import by.dak.furman.financial.Item;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 7:06 PM
 */
public class ItemNode extends AINode<Item> {
	private String name;

	public void setName(String string) {
		getValue().setName(string);
	}

	public String getName() {
		return getValue().getName();
	}

	public BigDecimal getAmount() {
		return getValue().getAmount();
	}

	public void setAmount(BigDecimal amount) {
		getValue().setAmount(amount);
	}

	public Date getCreated() {
		return getValue().getCreated();
	}

	public void setCreated(Date created) {
		getValue().setCreated(created);
	}
}

