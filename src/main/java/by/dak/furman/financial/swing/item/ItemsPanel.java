package by.dak.furman.financial.swing.item;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.ItemType;
import by.dak.furman.financial.swing.ATreeTablePanel;
import by.dak.furman.financial.swing.item.action.*;
import com.jgoodies.common.collect.LinkedListModel;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.table.DatePickerCellEditor;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;
import org.jdesktop.swingx.treetable.TreeTableCellEditor;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 11:23 AM
 */
public class ItemsPanel extends ATreeTablePanel {
	private AutoCompleter autoCompleter;

	private RefreshActionFactory refreshActionFactory;
	private IItemsPanelDelegate delegate;

	private LinkedListModel<String> itemTypes;

	protected RootNode createRootNode() {
		RootNode root = new RootNode();
		root.setProperties(root.createProperties(null));
		return root;
	}

	public void init() {
		super.init();
		refreshActionFactory = new RefreshActionFactory();
		getRefreshActionFactory().setPanel(this);
		getModel().addTreeModelListener(new TreeModelListener() {
			@Override
			public void treeNodesChanged(TreeModelEvent e) {
				Object[] children = e.getChildren();
				if (children != null && children.length > 0) {
					if (children[0] instanceof ItemTypeNode) {
						ItemTypeNode itemTypeNode = (ItemTypeNode) children[0];
						SaveItemType action = new SaveItemType();
						action.setPanel(ItemsPanel.this);
						action.setNode(itemTypeNode);
						action.action();
					} else if (children[0] instanceof ItemNode) {
						ItemNode itemNode = (ItemNode) children[0];
						SaveItem action = new SaveItem();
						action.setPanel(ItemsPanel.this);
						action.setNode(itemNode);
						action.action();
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
		});

		getTreeTable().getTreeSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TreePath treePath = e.getNewLeadSelectionPath();
				if (treePath != null && treePath.getLastPathComponent() instanceof AINode) {
					AINode aiNode = (AINode) treePath.getLastPathComponent();
					//if (aiNode.getCategory() != null && autoCompleter != null)
					//autoCompleter.setValues(getItemTypes(aiNode.getCategory()));
				}
			}
		});

		initActions();
	}

	public LinkedListModel<String> getItemTypes(Category category) {
		LinkedListModel<String> itemTypes = new LinkedListModel<String>();
		java.util.List<ItemType> values = getAppConfig().getItemTypeService().getAllBy(category);
		for (ItemType itemType : values) {
			itemTypes.add(itemType.getName());
		}
		return itemTypes;
	}

	@Override
	protected void initRenderersEditors() {
		super.initRenderersEditors();

		TreeTableCellEditor editor = (TreeTableCellEditor) getTreeTable().getCellEditor(0, getTreeTable().getHierarchicalColumn());
//        autoCompleter = new AutoCompleter();
//        autoCompleter.setCellEditor(editor);
//        autoCompleter.setParentWindow(SwingUtilities.getWindowAncestor(ItemsPanel.this));
//        autoCompleter.setOpacity(0.9f);
//        autoCompleter.setValues(Collections.emptyList());
//        autoCompleter.init();


		DefaultTableColumnModelExt columnModel = (DefaultTableColumnModelExt) getTreeTable().getColumnModel();

		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
		columnModel.getColumnExt(Item.PROPERTY_created).setCellEditor(new DatePickerCellEditor(dateFormat));
		DefaultTableRenderer defaultTableRenderer = new DefaultTableRenderer(new StringValue() {
			@Override
			public String getString(Object value) {
				if (value == null)
					return StringUtils.EMPTY;
				return value instanceof Date ? dateFormat.format(value) : value.toString();
			}
		});
		defaultTableRenderer.getComponentProvider().setHorizontalAlignment(JTextField.RIGHT);
		columnModel.getColumnExt(Item.PROPERTY_created).setCellRenderer(defaultTableRenderer);


	}

	public RefreshActionFactory getRefreshActionFactory() {
		return refreshActionFactory;
	}

	public IItemsPanelDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(IItemsPanelDelegate delegate) {
		this.delegate = delegate;
	}

	protected Action getActionDelete() {

		AbstractAction actionDelete = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Object node = getTreeTable().getTreeSelectionModel().getLeadSelectionPath().getLastPathComponent();
				if (node instanceof ItemTypeNode) {
					DeleteItemType action = new DeleteItemType();
					action.setNode((ItemTypeNode) node);
					action.setPanel(ItemsPanel.this);
					action.action();
				} else if (node instanceof ItemNode) {
					DeleteItem action = new DeleteItem();
					action.setNode((ItemNode) node);
					action.setPanel(ItemsPanel.this);
					action.action();
				}
			}
		};
		return actionDelete;
	}
}
