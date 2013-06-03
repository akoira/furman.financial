package by.dak.furman.financial.swing;

import by.dak.furman.financial.service.ICategoryService;
import by.dak.furman.financial.service.IDepartmentService;
import by.dak.furman.financial.service.IItemService;
import by.dak.furman.financial.service.IItemTypeService;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.Collections;
import java.util.logging.Level;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 4:15 PM
 */
public abstract class AAction<P extends ATreeTablePanel, N extends ATreeTableNode> {
	private P panel;
	private N node;

	private String message;

	private ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(getClass());

	public void action() {
		try {
			before();
			if (validate()) {
				makeAction();

				Runnable runnable = new Runnable() {
					public void run() {
						after();
					}
				};
				SwingUtilities.invokeLater(runnable);
			} else if (message != null)
				JXErrorPane.showDialog(getPanel(),
						new ErrorInfo(message,
								message,
								message,
								message,
								null,
								Level.WARNING, Collections.EMPTY_MAP));
		} catch (Exception e) {
			JXErrorPane.showDialog(getPanel(),
					new ErrorInfo("Unexpected Exception",
							"Unexpected Exception",
							"Unexpected Exception",
							"Unexpected Exception",
							e,
							Level.SEVERE, Collections.EMPTY_MAP));

		}

	}

	public FTreeTableModel getModel() {
		return getPanel().getModel();
	}

	public ATreeTableNode getRootNode() {
		return (ATreeTableNode) getModel().getRoot();
	}

	protected void after() {
	}

	protected void before() {
	}

	protected boolean validate() {
		return true;
	}

	protected abstract void makeAction();

	public ResourceMap getResourceMap() {
		return resourceMap;
	}

	public ResourceMap getResourceMap(Class aClass) {
		return Application.getInstance().getContext().getResourceMap(aClass);
	}

	public P getPanel() {
		return panel;
	}

	public void setPanel(P panel) {
		this.panel = panel;
	}

	public N getNode() {
		return node;
	}

	public void setNode(N node) {
		this.node = node;
	}

	public IDepartmentService getDepartmentService() {
		return getPanel().getAppConfig().getDepartmentService();
	}

	public ICategoryService getCategoryService() {
		return getPanel().getAppConfig().getCategoryService();
	}

	public IItemTypeService getItemTypeService() {
		return getPanel().getAppConfig().getItemTypeService();
	}

	public IItemService getItemService() {
		return getPanel().getAppConfig().getItemService();
	}

	public void selectColumn(ATreeTableNode node, int column) {
		getPanel().getTreeTable().getTreeSelectionModel().setSelectionPath(new TreePath(getModel().getPathToRoot(node)));
		getPanel().getTreeTable().getColumnModel().getSelectionModel().setSelectionInterval(0, 0);
		getPanel().getTreeTable().getColumnModel().getSelectionModel().setSelectionInterval(column, column);
	}


	public void selectColumn(final ATreeTableNode node, final String property) {
		Runnable runnable = new Runnable() {
			public void run() {
				int column = getPanel().getTreeTable().getColumnModel().getColumnIndex(property);
				selectColumn(node, column);
			}
		};
		SwingUtilities.invokeLater(runnable);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
