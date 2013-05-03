package by.dak.furman.financial;

import by.dak.common.lang.StringValue;
import by.dak.furman.financial.converter.DepartmentRenderer;

import javax.persistence.Entity;

@Entity
@StringValue(converterClass = DepartmentRenderer.class)
public class Department extends AObject {
}
