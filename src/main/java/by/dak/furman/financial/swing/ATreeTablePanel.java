package by.dak.furman.financial.swing;

import by.dak.common.swing.treetable.ATreeTableNode;
import by.dak.furman.financial.app.AppConfig;
import by.dak.furman.financial.swing.category.DefaultTreeTableRenderer;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

import javax.swing.*;
import java.awt.*;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 4:26 PM
 */
public abstract class ATreeTablePanel extends JXPanel
{
    private JXTreeTable treeTable;
    private DefaultTreeTableModel model;
    private AppConfig appConfig;

    public void init()
    {
        setLayout(new BorderLayout());
        treeTable = new JXTreeTable();
        getTreeTable().setTreeCellRenderer(new DefaultTreeTableRenderer());
        getTreeTable().setScrollsOnExpand(true);
        getTreeTable().setExpandsSelectedPaths(true);

        JScrollPane scrollPane = new JScrollPane(getTreeTable());
        add(scrollPane, BorderLayout.CENTER);

        model = new DefaultTreeTableModel(createRootNode());
        getTreeTable().setTreeTableModel(getModel());
    }

    public JXTreeTable getTreeTable()
    {
        return treeTable;
    }

    public DefaultTreeTableModel getModel()
    {
        return model;
    }

    public abstract ATreeTableNode createRootNode();

    public AppConfig getAppConfig()
    {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig)
    {
        this.appConfig = appConfig;
    }
}
