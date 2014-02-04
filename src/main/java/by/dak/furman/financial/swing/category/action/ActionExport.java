package by.dak.furman.financial.swing.category.action;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.swing.category.CategoryNode;

import java.io.File;
import java.util.List;

public class ActionExport extends ACAction<CategoryNode> {

	@Override
	protected void makeAction() {

		Category category = getNode().getCategory();
		SearchFilter searchFilter = getItemService().getSearchFilter(null, category, null, null);

		List<Item> items = getItemService().getAllBy(searchFilter);

		getPanel().getAppConfig().getExportService().export(items, new File("/Users/akoyro/temp/export.xml"));
	}

}
