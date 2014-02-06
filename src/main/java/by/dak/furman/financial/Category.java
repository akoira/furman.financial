package by.dak.furman.financial;

import by.dak.common.lang.StringValue;
import by.dak.furman.financial.converter.CategoryRenderer;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 11:00 AM
 */
@Entity
@StringValue(converterClass = CategoryRenderer.class)
public class Category extends AObject {
	public static final String PROPERTY_department = "department";

	@ManyToOne
	@JoinColumn(nullable = false)
	@Cascade(value = {CascadeType.SAVE_UPDATE})
	private Department department;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
