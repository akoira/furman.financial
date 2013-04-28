package by.dak.furman.financial.swing.category;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.Period;
import by.dak.furman.financial.PeriodType;
import by.dak.furman.financial.swing.ATreeTableNode;
import by.dak.furman.financial.swing.ATreeTablePanel;
import by.dak.furman.financial.swing.category.action.DeleteCategory;
import by.dak.furman.financial.swing.category.action.SaveCategory;
import org.jdesktop.swingx.JXTextField;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 11:23 AM
 */
public class CategoriesPanel extends ATreeTablePanel
{
    public static final String ACTION_deleteCategory = "deleteCategory";

    private ICategoriesPanelDelegate delegate;

    private TreeSelectionListener treeSelectionListener;
    private TreeModelListener treeModelListener;

    private Action actionDelete;

    @Override
    public ATreeTableNode createRootNode()
    {
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

    public void init()
    {
        super.init();

        getTreeTable().setDefaultEditor(Category.class, new DefaultCellEditor(new JXTextField()));

        getModel().addTreeModelListener(getTreeModelListener());
        getTreeTable().getTreeSelectionModel().addTreeSelectionListener(getTreeSelectionListener());

        initActions();
    }

    private void initActions()
    {

        getTreeTable().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,
                0), ACTION_deleteCategory);
        getTreeTable().getActionMap().put(ACTION_deleteCategory, getActionDelete());
    }

    private TreeModelListener getTreeModelListener()
    {
        if (treeModelListener == null)
            treeModelListener = new TreeModelListener()
            {
                @Override
                public void treeNodesChanged(TreeModelEvent e)
                {
                    Object node = e.getChildren()[0];
                    if (node instanceof CategoryNode)
                    {
                        CategoryNode cNode = (CategoryNode) node;
                        SaveCategory saveCategory = new SaveCategory();
                        saveCategory.setNode(cNode);
                        saveCategory.setPanel(CategoriesPanel.this);
                        saveCategory.action();
                    }
                }

                @Override
                public void treeNodesInserted(TreeModelEvent e)
                {
                }

                @Override
                public void treeNodesRemoved(TreeModelEvent e)
                {
                }

                @Override
                public void treeStructureChanged(TreeModelEvent e)
                {
                }
            };
        return treeModelListener;


    }

    private TreeSelectionListener getTreeSelectionListener()
    {
        if (treeSelectionListener == null)
            treeSelectionListener = new TreeSelectionListener()
            {
                @Override
                public void valueChanged(TreeSelectionEvent e)
                {
                    if (delegate != null)
                    {
                        if (e.getNewLeadSelectionPath() != null)
                        {
                            ACNode node = (ACNode) e.getNewLeadSelectionPath().getLastPathComponent();
                            delegate.selectNode(node);
                        }
                    }
                }
            };
        return treeSelectionListener;

    }

    private Action getActionDelete()
    {
        if (actionDelete == null)
            actionDelete = new AbstractAction()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    DeleteCategory deleteCategory = new DeleteCategory();
                    deleteCategory.setPanel(CategoriesPanel.this);
                    Object node = getTreeTable().getTreeSelectionModel().getLeadSelectionPath().getLastPathComponent();
                    if (node instanceof CategoryNode)
                    {
                        deleteCategory.setCategoryNode((CategoryNode) node);
                        deleteCategory.action();
                    }
                }
            };
        return actionDelete;
    }

    public ICategoriesPanelDelegate getDelegate()
    {
        return delegate;
    }

    public void setDelegate(ICategoriesPanelDelegate delegate)
    {
        this.delegate = delegate;
    }

    @Override
    protected void initEditors()
    {
    }
}
