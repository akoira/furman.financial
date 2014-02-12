package by.dak.furman.financial.service.export;

import by.dak.furman.financial.AObject;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.service.IItemService;
import by.dak.furman.financial.service.IService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;


@Service
public class ExportService implements IExportService {


	@Value("${export.delete}")
	private boolean delete;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private IItemService itemService;

	private <O extends AObject> IService<O> getServiceBy(Class<O> oClass) {
		String className = String.format("by.dak.furman.financial.service.I%sService", oClass.getSimpleName());
		try {
			return (IService<O>) applicationContext.getBean(Class.forName(className));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private void checkUuid(AObject aObject) {
		if (aObject.getUuid() == null) {
			aObject.setUuid(UUID.randomUUID().toString());
		}
		aObject.setExported(true);
	}

	@Override
	@Transactional
	public void export(List<Item> items, File file) {
		OutputStream out = null;
		try {
			for (Item item : items) {
				checkUuid(item);
				checkUuid(item.getItemType());
				checkUuid(item.getItemType().getCategory());
				checkUuid(item.getItemType().getCategory().getDepartment());
				itemService.save(item);
			}
			out = new FileOutputStream(file);

			DomDriver domDriver = new DomDriver();
			XStream xStream = new XStream(domDriver);
			xStream.toXML(items, out);
			if (delete) {
				for (Item item : items) {
					itemService.delete(item);
				}
			}

		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
}
