package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.swing.category.ACNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

/**
 * User: akoyro
 * Date: 4/28/13
 * Time: 11:22 PM
 */
public class RefreshHierarchy extends ACAction<ACNode> {
	@Override
	protected void makeAction() {
		TreeTableNode[] tableNodes = getModel().getPathToRoot(getNode());
		for (TreeTableNode node : tableNodes) {
			ACRefreshAction action = getPanel().getRefreshActionFactory().getActionBy((ACNode) node);
			action.reloadNode();
			getPanel().getTreeTable().repaint();
			action.reloadChildren();
		}


	}


}
