package by.dak.furman.financial.app;


import bibliothek.extension.gui.dock.theme.EclipseTheme;
import bibliothek.gui.DockController;
import bibliothek.gui.dock.DefaultDockable;
import bibliothek.gui.dock.SplitDockStation;
import bibliothek.gui.dock.ToolbarDockStation;
import bibliothek.gui.dock.ToolbarItemDockable;
import bibliothek.gui.dock.station.split.SplitDockGrid;
import bibliothek.gui.dock.toolbar.expand.ExpandedState;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.category.CategoriesPanel;
import by.dak.furman.financial.swing.category.ICategoriesPanelDelegate;
import by.dak.furman.financial.swing.category.action.RefreshHierarchy;
import by.dak.furman.financial.swing.item.IItemsPanelDelegate;
import by.dak.furman.financial.swing.item.ItemsPanel;
import by.dak.furman.financial.swing.item.action.RefreshRootNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.apache.derby.drda.NetworkServerControl;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Task;
import org.jdesktop.application.session.TableProperty;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.action.AbstractActionExt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FinancialApp extends SingleFrameApplication {

	private static final Log LOGGER = LogFactoryImpl.getLog(FinancialApp.class);

	private JXFrame mainFrame;
	private AppConfig appConfig;
	private CategoriesPanel categoriesPanel;
	private ItemsPanel itemsPanel;
	private DockController dockController;
	private ToolbarDockStation toolbarDockStation;

	public static void main(String[] args) {
		Locale.setDefault(new Locale("ru", "RU"));
		Application.launch(FinancialApp.class, args);
	}

	public FinancialApp() {
		getContext().getResourceManager().setResourceFolder(StringUtils.EMPTY);
	}

	@Override
	protected void startup() {
		initDbServer();
		appConfig = AppConfig.getAppConfig();

		mainFrame = new JXFrame(getContext().getResourceMap().getString("Application.title"));
		mainFrame.setIconImage(getContext().getResourceMap().getImageIcon("Application.icon").getImage());
		setMainFrame(mainFrame);

		SplitDockStation splitDockStation = initDocking();
		initToolBar();
		restoreComponents();


		Container content = mainFrame.getContentPane();
		content.setLayout(new BorderLayout());
		content.add(splitDockStation.getComponent(), BorderLayout.CENTER);
		show(getMainView());
		getContext().getTaskService().execute(getLeftPanelRefreshTask());

	}

	private void restoreComponents() {
		try {

			Object o = getContext().getLocalStorage().load("FinancialApp.ini");
			if (o instanceof Map) {
				TableProperty property = new TableProperty();
				Map<String, Object> map = (Map<String, Object>) o;

				Object state = map.get(getCategoriesPanel().getName());
				property.setSessionState(getCategoriesPanel().getTreeTable(), state);

				state = map.get(getItemsPanel().getName());
				property.setSessionState(getItemsPanel().getTreeTable(), state);
			}
		} catch (Exception e) {
			if (LOGGER.isDebugEnabled())
				LOGGER.debug(e.getMessage(), e);
		}
	}

	private void saveComponents() {
		try {
			Map<String, Object> stateMap = new HashMap<String, Object>();
			TableProperty property = new TableProperty();

			Object state = property.getSessionState(getCategoriesPanel().getTreeTable());
			stateMap.put(getCategoriesPanel().getName(), state);

			state = property.getSessionState(getItemsPanel().getTreeTable());
			stateMap.put(getItemsPanel().getName(), state);

			getContext().getLocalStorage().save(stateMap, "FinancialApp.ini");
		} catch (Exception e) {
			if (LOGGER.isDebugEnabled())
				LOGGER.debug(e.getMessage(), e);
		}
	}

	private SplitDockStation initDocking() {
		dockController = new DockController();
		dockController.setRootWindow(mainFrame);
		dockController.setTheme(new EclipseTheme());


		SplitDockGrid splitDockGrid = new SplitDockGrid();
		DefaultDockable dockable = new DefaultDockable(getCategoriesPanel(), getContext().getResourceMap().getString("departments.label"));
		dockable.setTitleIcon(getContext().getResourceMap().getIcon("departments.icon"));
		splitDockGrid.addDockable(0, 0, 1, 1, dockable);
		dockable = new DefaultDockable(getItemsPanel(), getContext().getResourceMap().getString("payments.label"));
		dockable.setTitleIcon(getContext().getResourceMap().getIcon("payments.icon"));
		splitDockGrid.addDockable(1, 0, 3, 3, dockable);
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

	private void initToolBar() {
		toolbarDockStation = new ToolbarDockStation();
		toolbarDockStation.setExpandedState(ExpandedState.SHRUNK);
		dockController.add(toolbarDockStation);
		categoriesPanel.add(toolbarDockStation.getComponent(), BorderLayout.NORTH);

		addAction(getActionExport());
		addAction(getActionImport());
		addAction(getActionRefresh());
	}


	private Action createActionBy(final Object target, final String method, String keyPrefix) {

		AbstractActionExt action = new AbstractActionExt() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final Task task = new Task(FinancialApp.this) {
					@Override
					protected Object doInBackground() throws Exception {
						target.getClass().getMethod(method).invoke(target);
						return null;
					}
				};
				getContext().getTaskService().execute(task);
			}
		};
		action.setLargeIcon(getContext().getResourceMap().getIcon(keyPrefix + "icon"));
		action.setSmallIcon(getContext().getResourceMap().getIcon(keyPrefix + "icon"));
		action.setLongDescription(getContext().getResourceMap().getString(keyPrefix + "label"));
		return action;
	}

	private Action getActionExport() {
		return createActionBy(getCategoriesPanel(), "exportData", "export.");
	}

	private Action getActionImport() {

		return createActionBy(getCategoriesPanel(), "importData", "import.");
	}

	private Action getActionRefresh() {
		return createActionBy(getCategoriesPanel(), "refreshData", "refresh.");
	}


	private void addAction(Action action) {
		ToolbarItemDockable item = new ToolbarItemDockable();

		JXButton button = new JXButton(action);
		button.setBorderPainted(false);
		button.setFocusable(false);

		button.setPreferredSize(new Dimension(18, 18));
		item.setComponent(button, ExpandedState.SHRUNK);
		toolbarDockStation.drop(item);
	}


	private void initDbServer() {
		new DbServer().start();
	}

	@Override
	protected void shutdown() {
		saveComponents();
		super.shutdown();
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

	private Task getLeftPanelRefreshTask() {
		return new Task(this) {
			@Override
			protected Object doInBackground() throws Exception {
				categoriesPanel.refreshData();
				return null;
			}
		};
	}

	public static class DbServer {
		public void start() {
			try {
				NetworkServerControl server = new NetworkServerControl
						(InetAddress.getByName("localhost"), 1527);
				server.start(null);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}
	}
}

