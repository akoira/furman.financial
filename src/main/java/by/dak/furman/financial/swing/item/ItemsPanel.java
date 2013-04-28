package by.dak.furman.financial.swing.item;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.swing.ATreeTablePanel;
import by.dak.furman.financial.swing.item.action.SaveItemType;
import com.jgoodies.common.collect.LinkedListModel;
import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.table.DatePickerCellEditor;
import org.jdesktop.swingx.treetable.TreeTableCellEditor;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.TreePath;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Date;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 11:23 AM
 */
public class ItemsPanel extends ATreeTablePanel
{
    private AutoCompleter autoCompleter;

    private LinkedListModel<String> itemTypes;

    protected RootNode createRootNode()
    {
        RootNode root = new RootNode();
        root.setProperties(root.createProperties(null));
        return root;
    }

    public void init()
    {
        super.init();
        getModel().addTreeModelListener(new TreeModelListener()
        {
            @Override
            public void treeNodesChanged(TreeModelEvent e)
            {
                Object[] children = e.getChildren();
                if (children != null && children.length > 0 && children[0] instanceof ItemTypeNode)
                {
                    ItemTypeNode itemTypeNode = (ItemTypeNode) children[0];
                    SaveItemType action = new SaveItemType();
                    action.setPanel(ItemsPanel.this);
                    action.setNode(itemTypeNode);
                    action.action();
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
        });

        getTreeTable().getTreeSelectionModel().addTreeSelectionListener(new TreeSelectionListener()
        {
            @Override
            public void valueChanged(TreeSelectionEvent e)
            {
                TreePath treePath = e.getNewLeadSelectionPath();
                if (treePath != null && treePath.getLastPathComponent() instanceof AINode)
                {
                    AINode aiNode = (AINode) treePath.getLastPathComponent();
                    if (aiNode.getCategory() != null)
                        autoCompleter.setValues(getItemTypes(aiNode.getCategory()));
                }
            }
        });
    }

    public LinkedListModel<String> getItemTypes(Category category)
    {
        LinkedListModel<String> itemTypes = new LinkedListModel<String>();
        java.util.List<ItemType> values = getAppConfig().getItemTypeService().getAllBy(category);
        for (ItemType itemType : values)
        {
            itemTypes.add(itemType.getName());
        }
        return itemTypes;
    }

    @Override
    protected void initEditors()
    {
        TreeTableCellEditor editor = (TreeTableCellEditor) getTreeTable().getCellEditor(0, getTreeTable().getHierarchicalColumn());
        autoCompleter = new AutoCompleter();
        autoCompleter.setCellEditor(editor);
        autoCompleter.setParentWindow(SwingUtilities.getWindowAncestor(ItemsPanel.this));
        autoCompleter.setOpacity(0.9f);
        autoCompleter.setValues(Collections.emptyList());
        autoCompleter.init();


        getTreeTable().getColumnModel().getColumns();
        getTreeTable().setDefaultEditor(Date.class, new DatePickerCellEditor());
        NumberFormat moneyFormat = NumberFormat.getNumberInstance();
        moneyFormat.setMinimumFractionDigits(2);
        JXFormattedTextField field = new JXFormattedTextField();
        field.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(
                moneyFormat)));
        getTreeTable().setDefaultEditor(BigDecimal.class, new DefaultCellEditor(field));
    }
}
