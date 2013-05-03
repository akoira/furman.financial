package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.swing.category.ACNode;

import javax.swing.tree.TreePath;

/**
 * User: akoyro
 * Date: 4/28/13
 * Time: 2:48 AM
 */
public class ExpandNode extends ACAction<ACNode> {
	@Override
	protected void makeAction() {
		TreePath path = new TreePath(getModel().getPathToRoot(getNode()));
		getPanel().getTreeTable().expandPath(path);
		getPanel().getTreeTable().getTreeSelectionModel().setSelectionPath(path);
	}
}
