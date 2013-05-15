package by.dak.furman.financial.swing;

import org.jdesktop.swingx.treetable.TreeTableNode;

public abstract class ADeleteAction<P extends ATreeTablePanel, N extends ATreeTableNode> extends AAction<P, N> {

	private TreeTableNode parent;

	@Override
	protected void before() {
		parent = getNode().getParent();
	}

	@Override
	protected void after() {
		if (parent.getColumnCount() > 0)
			selectColumn((ATreeTableNode) parent.getChildAt(parent.getChildCount() - 1), 0);
	}
}
