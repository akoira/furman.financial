package by.dak.furman.financial.swing.item;

import by.dak.furman.financial.swing.item.action.RefreshItems;
import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import org.jdesktop.swingx.table.DatePickerCellEditor;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableCellEditor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

    public ItemsPanel init()
    {
        setLayout(new BorderLayout());

        treeTable = new JXTreeTable();

        scrollPane = new JScrollPane(getTreeTable());
        add(getScrollPane(), BorderLayout.CENTER);

        model = new DefaultTreeTableModel();
        treeTable.setTreeTableModel(model);

        RefreshItems refreshItems = new RefreshItems();
        refreshItems.setItemsPanel(this);
        refreshItems.action();

        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                TreeTableCellEditor editor = (TreeTableCellEditor) treeTable.getCellEditor(0, treeTable.getHierarchicalColumn());
                JTextField textField = (JTextField) editor.getComponent();

                DefaultListModel model = new DefaultListModel();
                model.addElement("String1");
                model.addElement("String2");
                model.addElement("String3");
                model.addElement("String4");
                model.addElement("String5");
                model.addElement("String6");

                AutoSuggestor autoSuggestor = new AutoSuggestor();
                autoSuggestor.setTextField(textField);
                autoSuggestor.setParentWindow(SwingUtilities.getWindowAncestor(ItemsPanel.this));
                autoSuggestor.setOpacity(0.7f);
                autoSuggestor.setListModel(model);
                autoSuggestor.init();


                //AutoCompleteDecorator.decorate(textField, values, false);
                textField.getDocument().addDocumentListener(new DocumentListener()
                {
                    @Override
                    public void insertUpdate(DocumentEvent e)
                    {
                        System.out.println(e);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e)
                    {
                        System.out.println(e);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e)
                    {
                        System.out.println(e);
                    }
                });

                treeTable.getColumnModel().getColumn(2).setCellEditor(new ComboBoxCellEditor(new JXComboBox()));
                treeTable.getColumnModel().getColumn(1).setCellEditor(new DatePickerCellEditor());
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
}
