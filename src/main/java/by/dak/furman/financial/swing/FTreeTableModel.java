package by.dak.furman.financial.swing;

import org.jdesktop.swingx.tree.TreeModelSupport;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableNode;

import javax.swing.tree.TreePath;

/**
 * User: akoyro
 * Date: 4/29/13
 * Time: 12:32 AM
 */
public class FTreeTableModel extends DefaultTreeTableModel {

	public FTreeTableModel(ATreeTableNode root) {
		super(root);
	}

	public TreeModelSupport getModelSupport() {
		return modelSupport;
	}

	@Override
	public String getColumnName(int column) {
		String id = super.getColumnName(column);
		return ((ATreeTableNode) getRoot()).getColumnName(id);
	}


	public Object getColumnIdentifier(int modelIndex) {
		return ((ATreeTableNode) getRoot()).getColumnIdentifiers().get(modelIndex);
	}

	private boolean isValidTreeTableNode(Object node) {
		boolean result = false;

		if (node instanceof TreeTableNode) {
			TreeTableNode ttn = (TreeTableNode) node;

			while (!result && ttn != null) {
				result = ttn == root;

				ttn = ttn.getParent();
			}
		}

		return result;
	}

	@Override
	public void setValueAt(Object value, Object node, int column) {

		if (!isValidTreeTableNode(node)) {
			throw new IllegalArgumentException(
					"node must be a valid node managed by this model");
		}

		if (column < 0 || column >= getColumnCount()) {
			throw new IllegalArgumentException("column must be a valid index");
		}

		TreeTableNode ttn = (TreeTableNode) node;

		if (column < ttn.getColumnCount()) {

			if (!isValueEquals(ttn.getValueAt(column), value)) {
				ttn.setValueAt(value, column);

				modelSupport.firePathChanged(new TreePath(getPathToRoot(ttn)));
			}
		}
	}

	public boolean isValueEquals(Object v1, Object v2) {
		return v1 == null && v2 == null || v1 != null && v1.equals(v2);
	}
}
