package by.dak.furman.financial.swing;

import org.jdesktop.swingx.tree.TreeModelSupport;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

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
}
