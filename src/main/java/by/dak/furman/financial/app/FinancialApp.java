package by.dak.furman.financial.app;


import bibliothek.extension.gui.dock.theme.EclipseTheme;
import bibliothek.gui.DockController;
import bibliothek.gui.dock.DefaultDockable;
import bibliothek.gui.dock.SplitDockStation;
import bibliothek.gui.dock.station.split.SplitDockGrid;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.category.CategoriesPanel;
import by.dak.furman.financial.swing.category.ICategoriesPanelDelegate;
import by.dak.furman.financial.swing.category.RootNode;
import by.dak.furman.financial.swing.category.action.AddNewDepartment;
import by.dak.furman.financial.swing.category.action.RefreshHierarchy;
import by.dak.furman.financial.swing.item.IItemsPanelDelegate;
import by.dak.furman.financial.swing.item.ItemsPanel;
import by.dak.furman.financial.swing.item.action.RefreshRootNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.derby.drda.NetworkServerControl;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.swingx.JXFrame;

import java.awt.*;
import java.net.InetAddress;
import java.util.Locale;

public class FinancialApp extends SingleFrameApplication {

	private JXFrame mainFrame;
	private AppConfig appConfig;
	private CategoriesPanel categoriesPanel;
	private ItemsPanel itemsPanel;

	@Override
	protected void startup() {
		Locale.setDefault(new Locale("ru", "RU"));
		initDbServer();
		appConfig = AppConfig.getAppConfig();
		getContext().getResourceManager().setResourceFolder(StringUtils.EMPTY);

		mainFrame = new JXFrame("Financial Manager");

		SplitDockStation splitDockStation = initDocking();

		setMainFrame(mainFrame);

		Container content = mainFrame.getContentPane();
		content.setLayout(new BorderLayout());
		content.add(splitDockStation.getComponent(), BorderLayout.CENTER);
		show(getMainView());
	}

	private SplitDockStation initDocking() {
		DockController dockController = new DockController();
		dockController.setRootWindow(mainFrame);
		dockController.setTheme(new EclipseTheme());


		SplitDockGrid splitDockGrid = new SplitDockGrid();
		DefaultDockable dockable = new DefaultDockable(getCategoriesPanel(), "Отделы");
		splitDockGrid.addDockable(0, 0, 1, 1, dockable);
		splitDockGrid.addDockable(1, 0, 3, 3, new DefaultDockable(getItemsPanel(), "Платежи"));
		SplitDockStation splitDockStation = new SplitDockStation();
		splitDockStation.dropTree(splitDockGrid.toTree());


//		splitDockGrid = new SplitDockGrid();
//		splitDockGrid.addDockable(0, 1, 1, 5, splitDockStation);
//		JXErrorPane errorPane = new JXErrorPane();
//		errorPane.setErrorInfo(new ErrorInfo(StringUtils.EMPTY,StringUtils.EMPTY,
//				StringUtils.EMPTY,StringUtils.EMPTY,null, Level.FINE, Collections.EMPTY_MAP));
//		splitDockGrid.addDockable(0, 0, 1, 1, new DefaultDockable(errorPane, "Сообщения"));
//		splitDockStation = new SplitDockStation();
//		splitDockStation.dropTree(splitDockGrid.toTree());

		dockController.add(splitDockStation);
		return splitDockStation;
	}

	private void initDbServer() {
		try {
			NetworkServerControl server = new NetworkServerControl
					(InetAddress.getByName("localhost"), 1527);
			server.start(null);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}


	@Override
	protected void shutdown() {
		super.shutdown();
	}

	public static void main(String[] args) {
		Application.launch(FinancialApp.class, args);
	}

	public CategoriesPanel getCategoriesPanel() {
		if (categoriesPanel == null) {
			categoriesPanel = new CategoriesPanel();
			categoriesPanel.setAppConfig(appConfig);
			categoriesPanel.setDelegate(new ICategoriesPanelDelegate() {
				@Override
				public void selectNode(ACNode node) {
					RefreshRootNode refreshRootNode = new RefreshRootNode();
					refreshRootNode.setPanel(getItemsPanel());
					refreshRootNode.setNode((by.dak.furman.financial.swing.item.RootNode) getItemsPanel().getModel().getRoot());
					if (node != null) {
						refreshRootNode.setACNode(node);
					}
					refreshRootNode.action();
				}
			});
			categoriesPanel.init();

			by.dak.furman.financial.swing.category.action.RefreshRootNode action = new by.dak.furman.financial.swing.category.action.RefreshRootNode();
			action.setPanel(categoriesPanel);
			action.setNode((RootNode) action.getRootNode());
			action.action();

			AddNewDepartment addNewDepartment = new AddNewDepartment();
			addNewDepartment.setPanel(categoriesPanel);
			addNewDepartment.setNode((RootNode) addNewDepartment.getRootNode());
			addNewDepartment.action();

		}
		return categoriesPanel;
	}

	public ItemsPanel getItemsPanel() {
		if (itemsPanel == null) {
			itemsPanel = new ItemsPanel();
			itemsPanel.setAppConfig(appConfig);
			itemsPanel.setDelegate(new IItemsPanelDelegate() {
				@Override
				public void refreshACNode(ACNode acNode) {
					RefreshHierarchy action = new RefreshHierarchy();
					action.setPanel(getCategoriesPanel());
					action.setNode(acNode);
					action.action();
				}
			});
			itemsPanel.init();
		}
		return itemsPanel;
	}
}
