package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.swing.ARefreshAction;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.category.CategoriesPanel;
import org.jdesktop.swingx.treetable.TreeTableNode;

/**
 * User: akoyro
 * Date: 4/28/13
 * Time: 9:06 PM
 */
public abstract class ACRefreshAction<N extends ACNode, V, C extends ACNode> extends ARefreshAction<CategoriesPanel, N, V, C> {
	@Override
	public void reloadNode() {
		getNode().setAmount(getItemService().getSumBy(getItemService().getSearchFilter(getNode().getDepartment(),
				getNode().getCategory(), null, getNode().getPeriod())));
	}


	public void reloadChildren() {
		int count = getNode().getChildCount();
		int i = 0;
		while (i < count) {
			TreeTableNode node = getNode().getChildAt(i);
			ACRefreshAction action = getPanel().getRefreshActionFactory().getActionBy((ACNode) node);
			action.reloadNode();
			getPanel().getTreeTable().repaint();
			action.reloadChildren();
			i++;
		}
	}
}
