package by.dak.furman.financial.swing.category.action;

import by.dak.common.persistence.SearchFilter;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.swing.category.CategoryNode;
import by.dak.furman.financial.swing.category.RootNode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ActionExport extends ACAction<RootNode> {

	@Override
	protected void makeAction() {
		List<Item> items = getItemService().getAll();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String fileName = String.format("%s-export.xml", dateFormat.format(new Date()));

		File file = new File(fileName);
		getPanel().getAppConfig().getExportService().export(items, file);
	}

}
