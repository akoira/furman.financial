package by.dak.furman.financial.swing;

import by.dak.furman.financial.Item;
import by.dak.furman.financial.app.AppConfig;
import by.dak.furman.financial.swing.category.DefaultTreeTableRenderer;
import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 4:26 PM
 */
public abstract class ATreeTablePanel extends JXPanel
{
    public static final String ACTION_deleteCategory = "deleteCategory";

    private JXTreeTable treeTable;
    private FTreeTableModel model;
    private AppConfig appConfig;

    public void init()
    {
        setLayout(new BorderLayout());
        treeTable = new JXTreeTable();
        getTreeTable().setTreeCellRenderer(new DefaultTreeTableRenderer());
        getTreeTable().setScrollsOnExpand(true);
        getTreeTable().setExpandsSelectedPaths(true);
        getTreeTable().getTreeSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        getTreeTable().getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getTreeTable().setShowVerticalLines(true);
        getTreeTable().setShowHorizontalLines(true);
        getTreeTable().setColumnControlVisible(true);
        getTreeTable().addHighlighter(HighlighterFactory.createAlternateStriping());

        JScrollPane scrollPane = new JScrollPane(getTreeTable());
        add(scrollPane, BorderLayout.CENTER);

        model = new FTreeTableModel(createRootNode());
        model.setColumnIdentifiers(((ATreeTableNode) model.getRoot()).getColumnIdentifiers());
        getTreeTable().setTreeTableModel(getModel());

        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                initRenderersEditors();
            }
        };
        SwingUtilities.invokeLater(runnable);
        initActions();
    }

    public JXTreeTable getTreeTable()
    {
        return treeTable;
    }

    public FTreeTableModel getModel()
    {
        return model;
    }

    protected abstract ATreeTableNode createRootNode();

    protected void initRenderersEditors()
    {
        DefaultTableColumnModelExt columnModel = (DefaultTableColumnModelExt) getTreeTable().getColumnModel();

        final NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMinimumIntegerDigits(1);
        JXFormattedTextField field = new JXFormattedTextField();
        field.setHorizontalAlignment(JTextField.RIGHT);
        field.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(
                numberFormat)));
        columnModel.getColumnExt(Item.PROPERTY_amount).setCellEditor(new DefaultCellEditor(field));
        DefaultTableRenderer renderer = new DefaultTableRenderer(new StringValue()
        {
            @Override
            public String getString(Object value)
            {
                if (value == null)
                    value = BigDecimal.ZERO;

                return value instanceof BigDecimal ? numberFormat.format(value) : value.toString();
            }
        });
        renderer.getComponentProvider().setHorizontalAlignment(JTextField.RIGHT);
        columnModel.getColumnExt(Item.PROPERTY_amount).setCellRenderer(renderer);
    }

    protected void initActions()
    {
        getTreeTable().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,
                0), ACTION_deleteCategory);
        getTreeTable().getActionMap().put(ACTION_deleteCategory, getActionDelete());
    }

    protected abstract Action getActionDelete();

    public AppConfig getAppConfig()
    {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig)
    {
        this.appConfig = appConfig;
    }
}
