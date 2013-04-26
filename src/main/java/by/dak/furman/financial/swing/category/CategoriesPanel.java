package by.dak.furman.financial.swing.category;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.swing.category.action.AddCategory;
import by.dak.furman.financial.swing.category.action.ChangeCategory;
import by.dak.furman.financial.swing.category.action.DeleteCategory;
import by.dak.furman.financial.swing.category.action.RefreshCategories;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 11:23 AM
 */
public class CategoriesPanel extends JXPanel
{
    public static final String ACTION_deleteCategory = "deleteCategory";
    private JScrollPane scrollPane;
    private JXTreeTable tree;
    private DefaultTreeTableModel model;

    public CategoriesPanel init()
    {
        setLayout(new BorderLayout());
        tree = new JXTreeTable();
        tree.setTreeCellRenderer(new DefaultTreeTableRenderer());
        tree.setScrollsOnExpand(true);
        tree.setExpandsSelectedPaths(true);
        tree.setDefaultEditor(Category.class, new DefaultCellEditor(new JXTextField()));

        scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);


        model = new DefaultTreeTableModel();
        tree.setTreeTableModel(model);

        RefreshCategories refreshCategories = new RefreshCategories();
        refreshCategories.setCategoriesPanel(this);
        refreshCategories.action();


        DeleteCategory deleteCategory = new DeleteCategory();
        deleteCategory.setCategoriesPanel(this);
        tree.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,
                0), ACTION_deleteCategory);
        tree.getActionMap().put(ACTION_deleteCategory, createDeleteCategoryAction());

        model.addTreeModelListener(new TreeModelListener()
        {
            @Override
            public void treeNodesChanged(TreeModelEvent e)
            {
                Object node = e.getChildren()[0];
                if (node instanceof CategoryNode)
                {
                    CategoryNode cNode = (CategoryNode) node;
                    if (cNode.getParent() == getModel().getRoot())
                        AddCategory.valueOf(cNode, CategoriesPanel.this).action();
                    else
                        ChangeCategory.valueOf(cNode, CategoriesPanel.this).action();
                }
            }

            @Override
            public void treeNodesInserted(TreeModelEvent e)
            {
            }

            @Override
            public void treeNodesRemoved(TreeModelEvent e)
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void treeStructureChanged(TreeModelEvent e)
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        return this;
    }

    public DefaultTreeTableModel getModel()
    {
        return model;
    }

    public JXTreeTable getTree()
    {
        return tree;
    }

    private Action createDeleteCategoryAction()
    {
        AbstractAction result = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DeleteCategory deleteCategory = new DeleteCategory();
                deleteCategory.setCategoriesPanel(CategoriesPanel.this);
                Object node = getTree().getTreeSelectionModel().getLeadSelectionPath().getLastPathComponent();
                if (node instanceof CategoryNode)
                {
                    deleteCategory.setCategoryNode((CategoryNode) node);
                    deleteCategory.action();
                }
            }
        };
        return result;
    }
}
