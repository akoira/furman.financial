package by.dak.furman.financial.service.export;

import by.dak.furman.financial.AObject;
import by.dak.furman.financial.service.IItemService;
import by.dak.furman.financial.service.IService;
import by.dak.furman.financial.service.export.xml.Export2Xml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;


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


	@Override
	@Transactional
	public File export(ExportRequest request) {
		Export2Xml export2Xml = new Export2Xml();
		export2Xml.setItemService(itemService);

		export2Xml.export(request);
		if (delete) {
			export2Xml.deleteAll();
		}
		return export2Xml.getFile();
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
}
