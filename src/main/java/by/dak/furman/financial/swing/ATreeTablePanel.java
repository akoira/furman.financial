package by.dak.furman.financial.swing;

import by.dak.furman.financial.Item;
import by.dak.furman.financial.app.AppConfig;
import by.dak.furman.financial.swing.category.APeriodNode;
import by.dak.furman.financial.swing.category.DefaultTreeTableRenderer;
import com.jidesoft.swing.JideScrollPane;
import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.*;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.table.ColumnFactory;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;
import org.jdesktop.swingx.table.TableColumnExt;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 4:26 PM
 */
public abstract class ATreeTablePanel extends JXPanel {
	public static final String ACTION_deleteCategory = "deleteCategory";
	private JXTreeTable treeTable;
	private FTreeTableModel model;
	private AppConfig appConfig;

	public void init() {
		setName(this.getClass().getSimpleName());
		setLayout(new BorderLayout());
		treeTable = new JXTreeTable() {
			@Override
			public Component prepareEditor(TableCellEditor editor, int row, int column) {
				Component component = super.prepareEditor(editor, row, column);
				if (component instanceof JTextField)
					((JTextField) component).selectAll();
				return component;
			}
		};
		treeTable.setName(JXTreeTable.class.getSimpleName());
		getTreeTable().setTreeCellRenderer(new DefaultTreeTableRenderer());
		getTreeTable().setScrollsOnExpand(true);
		getTreeTable().setExpandsSelectedPaths(true);
		getTreeTable().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		getTreeTable().getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getTreeTable().setShowVerticalLines(true);
		getTreeTable().setShowHorizontalLines(true);
		getTreeTable().setColumnControlVisible(true);

		getTreeTable().addHighlighter(HighlighterFactory.createAlternateStriping());
		ColorHighlighter colorHighlighter = new ColorHighlighter();
		colorHighlighter.setSelectedBackground(new Color(168, 200, 255));
		colorHighlighter.setHighlightPredicate(new HighlightPredicate() {
			@Override
			public boolean isHighlighted(Component renderer, ComponentAdapter adapter) {
				return getTreeTable().getSelectedColumn() == adapter.column && adapter.isEditable();
			}
		});
		getTreeTable().addHighlighter(colorHighlighter);

		AbstractHighlighter currentNodeH = new AbstractHighlighter() {
			private Font orig;

			@Override
			protected Component doHighlight(Component component, ComponentAdapter adapter) {
				if (adapter.column == 0) {
					if (orig == null)
						orig = component.getFont();

					TreePath path = getTreeTable().getPathForRow(adapter.row);
					ATreeTableNode treeTableNode = (ATreeTableNode) path.getLastPathComponent();
					if ((treeTableNode instanceof APeriodNode && ((APeriodNode) treeTableNode).getValue().isCurrent()))
						component.setFont(orig.deriveFont(Font.BOLD));
					else
						component.setFont(orig);
				}
				return component;
			}
		};

		getTreeTable().addHighlighter(currentNodeH);
		AbstractAction expendPathAction = new
				AbstractAction() {
					@Override
					public void actionPerformed(ActionEvent e) {

						if (getTreeTable().getCellEditor() != null)
							getTreeTable().getCellEditor().stopCellEditing();
						TreePath treePath = getTreeTable().getTreeSelectionModel().getLeadSelectionPath();
						getTreeTable().expandPath(treePath);
					}
				};

		getTreeTable().getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "expendPath");
		getTreeTable().getActionMap().put("expendPath", expendPathAction);

		getTreeTable().setColumnFactory(new ColumnFactory() {
			@Override
			public void configureTableColumn(TableModel model, TableColumnExt columnExt) {
				super.configureTableColumn(model, columnExt);
				columnExt.setIdentifier(ATreeTablePanel.this.model.getColumnIdentifier(columnExt.getModelIndex()));
			}
		});

		JideScrollPane scrollPane = new JideScrollPane(getTreeTable());
		scrollPane.setName(JScrollPane.class.getSimpleName());
		add(scrollPane, BorderLayout.CENTER);

		model = new FTreeTableModel(createRootNode());
		model.setColumnIdentifiers(((ATreeTableNode) model.getRoot()).getColumnIdentifiers());
		getTreeTable().setTreeTableModel(getModel());

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				initRenderersEditors();
			}
		};
		SwingUtilities.invokeLater(runnable);
		initActions();
	}

	public JXTreeTable getTreeTable() {
		return treeTable;
	}

	public FTreeTableModel getModel() {
		return model;
	}

	protected abstract ATreeTableNode createRootNode();

	protected void initRenderersEditors() {
		DefaultTableColumnModelExt columnModel = (DefaultTableColumnModelExt) getTreeTable().getColumnModel();

		final NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMinimumIntegerDigits(1);
		JXFormattedTextField field = new JXFormattedTextField();
		field.setHorizontalAlignment(JTextField.RIGHT);
		field.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(
				numberFormat)));
		columnModel.getColumnExt(Item.PROPERTY_amount).setCellEditor(new DefaultCellEditor(field));
		DefaultTableRenderer renderer = new DefaultTableRenderer(new StringValue() {
			@Override
			public String getString(Object value) {
				if (value == null)
					value = BigDecimal.ZERO;

				return value instanceof BigDecimal ? numberFormat.format(value) : value.toString();
			}
		});
		renderer.getComponentProvider().setHorizontalAlignment(JTextField.RIGHT);
		columnModel.getColumnExt(Item.PROPERTY_amount).setCellRenderer(renderer);
	}

	protected void initActions() {
		getTreeTable().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,
				0), ACTION_deleteCategory);
		getTreeTable().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE,
				KeyEvent.ALT_MASK), ACTION_deleteCategory);

		getTreeTable().getActionMap().put(ACTION_deleteCategory, getActionDelete());


	}

	protected abstract Action getActionDelete();

	public AppConfig getAppConfig() {
		return appConfig;
	}

	public void setAppConfig(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
}
