package by.dak.furman.financial.converter;

import by.dak.common.lang.ToStringConverter;
import by.dak.furman.financial.AObject;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;

/**
 * User: akoyro
 * Date: 4/29/13
 * Time: 2:35 PM
 */
public class AObjectRenderer<O extends AObject> implements ToStringConverter<O> {
	private ResourceMap resourceMap;

	@Override
	public String convert(O entity) {
		if (entity.getId() == null)
			return getResourceMap().getString("label.new");
		return entity.getName();
	}

	@Override
	public Icon convert2Icon(O entity) {
		if (entity.getId() == null)
			return getResourceMap().getIcon("icon.new");
		return getResourceMap().getIcon("icon");
	}

	public ResourceMap getResourceMap() {
		return resourceMap;
	}

	public void setResourceMap(ResourceMap resourceMap) {
		this.resourceMap = resourceMap;
	}
}
