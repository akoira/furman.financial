package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.swing.category.ACNode;

import javax.swing.*;
import javax.swing.tree.TreePath;

/**
 * User: akoyro
 * Date: 4/28/13
 * Time: 2:48 AM
 */
public class ExpandNode extends ACAction<ACNode> {
	@Override
	protected void makeAction() {
		Runnable runnable = new Runnable() {
			public void run() {
				TreePath path = new TreePath(getModel().getPathToRoot(getNode()));
				getPanel().getTreeTable().expandPath(path);
				getPanel().getTreeTable().getTreeSelectionModel().setSelectionPath(path);
			}
		};
		SwingUtilities.invokeLater(runnable);
	}
}
