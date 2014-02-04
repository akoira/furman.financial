package by.dak.furman.financial.service.export;

import by.dak.furman.financial.Item;
import by.dak.furman.financial.service.IItemService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExportService implements IExportService {

	@Autowired
	private IItemService itemService;

	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

	@Override
	public void export(List<Item> items, File file) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			XStream xStream = new XStream(new DomDriver());
			xStream.toXML(itemService.getAll(), out);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
}
