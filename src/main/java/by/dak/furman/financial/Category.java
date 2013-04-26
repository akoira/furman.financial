package by.dak.furman.financial;

import by.dak.common.lang.StringValue;
import by.dak.furman.financial.converter.Category2StringConverter;

import javax.persistence.Entity;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 11:00 AM
 */
@Entity
@StringValue(converterClass = Category2StringConverter.class)
public class Category extends AObject
{
}
