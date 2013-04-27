package by.dak.furman.financial.swing.item;

import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.service.IItemTypeService;
import com.jgoodies.common.collect.LinkedListModel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableCellEditor;

import javax.swing.*;
import java.awt.*;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 11:23 AM
 */
public class ItemsPanel extends JXPanel
{
    private JXTreeTable treeTable;
    private JScrollPane scrollPane;

    private DefaultTreeTableModel model;

    private LinkedListModel<ItemType> itemTypes;

    private IItemTypeService itemTypeService;


    private RootNode createRoot()
    {
        RootNode root = new RootNode();
        root.setProperties(RootNode.createProperties(null));
        return root;
    }

    public ItemsPanel init()
    {
        setLayout(new BorderLayout());

        treeTable = new JXTreeTable();

        scrollPane = new JScrollPane(getTreeTable());
        add(getScrollPane(), BorderLayout.CENTER);

        model = new DefaultTreeTableModel(createRoot());
        treeTable.setTreeTableModel(model);

        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                TreeTableCellEditor editor = (TreeTableCellEditor) treeTable.getCellEditor(0, treeTable.getHierarchicalColumn());
                AutoCompleter autoCompleter = new AutoCompleter();
                autoCompleter.setCellEditor(editor);
                autoCompleter.setParentWindow(SwingUtilities.getWindowAncestor(ItemsPanel.this));
                autoCompleter.setOpacity(0.9f);
                autoCompleter.setValues(getItemTypes());
                autoCompleter.init();
            }
        };

        SwingUtilities.invokeLater(runnable);
        return this;
    }

    public JXTreeTable getTreeTable()
    {
        return treeTable;
    }

    public JScrollPane getScrollPane()
    {
        return scrollPane;
    }

    public DefaultTreeTableModel getModel()
    {
        return model;
    }

    public LinkedListModel<ItemType> getItemTypes()
    {
        if (itemTypes == null)
        {
            itemTypes = new LinkedListModel<ItemType>(itemTypeService.getAll());
        }
        return itemTypes;
    }

    public IItemTypeService getItemTypeService()
    {
        return itemTypeService;
    }

    public void setItemTypeService(IItemTypeService itemTypeService)
    {
        this.itemTypeService = itemTypeService;
    }
}
