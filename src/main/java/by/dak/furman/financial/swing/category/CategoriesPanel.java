package by.dak.furman.financial.swing.category;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.Period;
import by.dak.furman.financial.PeriodType;
import by.dak.furman.financial.service.export.Export2Excel;
import by.dak.furman.financial.service.export.Export2ExcelRequest;
import by.dak.furman.financial.swing.ATreeTableNode;
import by.dak.furman.financial.swing.ATreeTablePanel;
import by.dak.furman.financial.swing.category.action.*;
import by.dak.furman.financial.swing.item.ItemsPanel;
import org.jdesktop.swingx.JXTextField;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 11:23 AM
 */
public class CategoriesPanel extends ATreeTablePanel {

    private ItemsPanel itemsPanel;

    private RefreshActionFactory refreshActionFactory;

    private ICategoriesPanelDelegate delegate;

    private TreeSelectionEventHandle treeSelectionEventHandle;
    private TreeModelListener treeModelListener;

    private Action actionDelete;

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
        registerTreeSelectionEventHandle();
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

    private TreeSelectionEventHandle getTreeSelectionEventHandle() {
        if (treeSelectionEventHandle == null) {
            treeSelectionEventHandle = new TreeSelectionEventHandle();
            treeSelectionEventHandle.setDelegate(delegate);
            treeSelectionEventHandle.setTreeSelectionModel(getTreeTable().getTreeSelectionModel());
        }
        return treeSelectionEventHandle;

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


    public void exportExcel() {
        List<ACNode> nodes = TreePathUtils.convert(getTreeTable().getTreeSelectionModel().getSelectionPaths());

        Export2ExcelRequest request = TreePathUtils.buildRequest(nodes);

        if (request != null) {
            Export2Excel export2Excel = new Export2Excel();
            export2Excel.setItemTypeService(getAppConfig().getItemTypeService());
            export2Excel.setCategoryService(getAppConfig().getCategoryService());
            export2Excel.setItemService(getAppConfig().getItemService());
            File file = export2Excel.export(request);

            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file.getParentFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void exportData() {
        ActionExport export = new ActionExport();
        export.setPanel(this);
        export.setNode((RootNode) getTreeTable().getTreeTableModel().getRoot());
        export.action();

        refreshData();
    }

    public void importData() {
        ActionImport actionImport = new ActionImport();
        actionImport.setPanel(this);
        actionImport.setNode((RootNode) getTreeTable().getTreeTableModel().getRoot());
        actionImport.action();

        refreshData();
    }

    public void refreshData() {
        RefreshRootNode refreshRootNode = new RefreshRootNode();
        refreshRootNode.setNode((RootNode) getTreeTable().getTreeTableModel().getRoot());
        refreshRootNode.setPanel(this);
        refreshRootNode.action();
    }

    public void unregisterTreeSelectionEventHandle() {
        getTreeTable().getTreeSelectionModel().removeTreeSelectionListener(getTreeSelectionEventHandle());
    }

    public void registerTreeSelectionEventHandle() {
        getTreeTable().getTreeSelectionModel().addTreeSelectionListener(getTreeSelectionEventHandle());
    }


    public ItemsPanel getItemsPanel() {
        return itemsPanel;
    }

    public void setItemsPanel(ItemsPanel itemsPanel) {
        this.itemsPanel = itemsPanel;
    }
}
