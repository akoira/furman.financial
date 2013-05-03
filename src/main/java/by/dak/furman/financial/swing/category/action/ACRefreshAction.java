package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.swing.ARefreshAction;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.category.CategoriesPanel;
import org.jdesktop.swingx.treetable.TreeTableNode;

import javax.swing.tree.TreePath;
import java.math.BigDecimal;

/**
 * User: akoyro
 * Date: 4/28/13
 * Time: 9:06 PM
 */
public abstract class ACRefreshAction<N extends ACNode, V, C extends ACNode> extends ARefreshAction<CategoriesPanel, N, V, C> {
	@Override
	public void reloadNode() {
		if (getNode().getCategory() != null && getNode().getCategory().getId() == null)
			getNode().setAmount(BigDecimal.ZERO);
		else
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
			getModel().getModelSupport().firePathChanged(new TreePath(getModel().getPathToRoot(node)));
			action.reloadChildren();
			i++;
		}
	}
}
