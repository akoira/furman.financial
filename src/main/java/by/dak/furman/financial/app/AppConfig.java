package by.dak.furman.financial.app;

import by.dak.furman.financial.service.ICategoryService;
import by.dak.furman.financial.service.IDepartmentService;
import by.dak.furman.financial.service.IItemService;
import by.dak.furman.financial.service.IItemTypeService;
import by.dak.furman.financial.service.export.IExportService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: akoyro
 * Date: 4/24/13
 * Time: 5:29 PM
 */
public class AppConfig {
	private static final AppConfig APP_CONFIG = new AppConfig();
	private ApplicationContext applicationContext;

	public AppConfig() {
		init();
	}

	public static AppConfig getAppConfig() {
		return APP_CONFIG;
	}

	private void init() {
		applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring/data-source.xml"});
	}

	public ICategoryService getCategoryService() {
		return applicationContext.getBean(ICategoryService.class);
	}

	public IItemService getItemService() {
		return applicationContext.getBean(IItemService.class);
	}

	public IItemTypeService getItemTypeService() {
		return applicationContext.getBean(IItemTypeService.class);
	}

	public IDepartmentService getDepartmentService() {
		return applicationContext.getBean(IDepartmentService.class);
	}

	public IExportService getExportService() {
		return applicationContext.getBean(IExportService.class);
	}
}
