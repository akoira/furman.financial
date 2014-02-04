package by.dak.furman.financial.swing.category;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.Period;
import by.dak.furman.financial.PeriodType;
import by.dak.furman.financial.swing.ATreeTableNode;
import by.dak.furman.financial.swing.ATreeTablePanel;
import by.dak.furman.financial.swing.category.action.*;
import org.jdesktop.swingx.JXTextField;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 11:23 AM
 */
public class CategoriesPanel extends ATreeTablePanel {


	private RefreshActionFactory refreshActionFactory;

	private ICategoriesPanelDelegate delegate;

	private TreeSelectionListener treeSelectionListener;
	private TreeModelListener treeModelListener;

	private Action actionDelete;
	private Action actionExport;

	@Override
	public ATreeTableNode createRootNode() {
		Period period = new Period();
		period.setPeriodType(PeriodType.ALL);
		period.setCurrent(true);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, 1);
		period.setEndDate(calendar.getTime());
		period.setStartDate(new Date(0));

		RootNode rootNode = new RootNode();
		rootNode.setPeriod(period);
		rootNode.setValue(period);
		rootNode.setProperties(rootNode.createProperties(period));

		return rootNode;
	}

	public void init() {
		super.init();

		refreshActionFactory = new RefreshActionFactory();
		refreshActionFactory.setPanel(this);

		getTreeTable().setDefaultEditor(Category.class, new DefaultCellEditor(new JXTextField()));

		getModel().addTreeModelListener(getTreeModelListener());
		getTreeTable().getTreeSelectionModel().addTreeSelectionListener(getTreeSelectionListener());
	}


	private TreeModelListener getTreeModelListener() {
		if (treeModelListener == null)
			treeModelListener = new TreeModelListener() {
				@Override
				public void treeNodesChanged(TreeModelEvent e) {
					if (e.getChildren() != null && e.getChildren().length > 0) {
						Object node = e.getChildren()[0];
						if (node instanceof CategoryNode) {
							CategoryNode cNode = (CategoryNode) node;
							SaveCategory saveCategory = new SaveCategory();
							saveCategory.setNode(cNode);
							saveCategory.setPanel(CategoriesPanel.this);
							saveCategory.action();
						} else if (node instanceof DepartmentNode) {
							DepartmentNode dNode = (DepartmentNode) node;
							SaveDepartment saveDepartment = new SaveDepartment();
							saveDepartment.setNode(dNode);
							saveDepartment.setPanel(CategoriesPanel.this);
							saveDepartment.action();
						}
					}
				}

				@Override
				public void treeNodesInserted(TreeModelEvent e) {
				}

				@Override
				public void treeNodesRemoved(TreeModelEvent e) {
				}

				@Override
				public void treeStructureChanged(TreeModelEvent e) {
				}
			};
		return treeModelListener;


	}

	private TreeSelectionListener getTreeSelectionListener() {
		if (treeSelectionListener == null)
			treeSelectionListener = new TreeSelectionListener() {
				@Override
				public void valueChanged(TreeSelectionEvent e) {
					if (delegate != null) {
						if (e.getNewLeadSelectionPath() != null) {
							ACNode node = (ACNode) e.getNewLeadSelectionPath().getLastPathComponent();
							delegate.selectNode(node);
						}
					}
				}
			};
		return treeSelectionListener;

	}

	protected Action getActionDelete() {
		if (actionDelete == null)
			actionDelete = new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Object node = getTreeTable().getTreeSelectionModel().getLeadSelectionPath().getLastPathComponent();
					if (node instanceof CategoryNode) {
						DeleteCategory deleteCategory = new DeleteCategory();
						deleteCategory.setPanel(CategoriesPanel.this);
						deleteCategory.setNode((CategoryNode) node);
						deleteCategory.action();
					} else if (node instanceof DepartmentNode) {
						DeleteDepartment deleteDepartment = new DeleteDepartment();
						deleteDepartment.setPanel(CategoriesPanel.this);
						deleteDepartment.setNode((DepartmentNode) node);
						deleteDepartment.action();
					}


				}
			};
		return actionDelete;
	}

	public ICategoriesPanelDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(ICategoriesPanelDelegate delegate) {
		this.delegate = delegate;
	}

	public RefreshActionFactory getRefreshActionFactory() {
		return refreshActionFactory;
	}

	public void export() {
		TreePath selection = getTreeTable().getTreeSelectionModel().getLeadSelectionPath();
		if (selection != null) {
			Object node = selection.getLastPathComponent();
			if (node instanceof CategoryNode) {
				ActionExport export = new ActionExport();
				export.setPanel(this);
				export.setNode((CategoryNode) node);
				export.action();
			}
		}
	}
}
