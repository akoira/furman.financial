package by.dak.furman.financial.swing;

import by.dak.furman.financial.swing.item.AINode;
import org.jdesktop.swingx.treetable.TreeTableNode;

public abstract class ADeleteAction<P extends ATreeTablePanel, N extends ATreeTableNode> extends AAction<P, N> {

	private TreeTableNode parent;

	@Override
	protected void before() {
		parent = getNode().getParent();
	}

	@Override
	protected void after() {
		if (parent.getColumnCount() > 1)
			selectColumn((AINode) parent.getChildAt(parent.getChildCount() - 2), 0);
	}
}
