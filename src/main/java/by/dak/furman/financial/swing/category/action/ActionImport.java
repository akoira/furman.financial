package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.swing.category.RootNode;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.io.FileFilter;

public class ActionImport extends ACAction<RootNode> {

	@Override
	protected void makeAction() {
		FileFilter fileFilter = new RegexFileFilter("[0-9]{8}-[0-9]{6}-export.xml");
		File dir = new File(".");
		File[] files =  dir.listFiles(fileFilter);
		for (File file : files) {
			getPanel().getAppConfig().getImportService().importData(file);
		}

	}
}
