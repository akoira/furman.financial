package by.dak.furman.financial.swing;

import org.jdesktop.swingx.treetable.TreeTableNode;

public abstract class ADeleteAction<P extends ATreeTablePanel, N extends ATreeTableNode> extends AAction<P, N> {

	private TreeTableNode parent;
	private int nodeIndex;

	@Override
	protected void before() {
		parent = getNode().getParent();
		nodeIndex = parent.getIndex(getNode());
		nodeIndex = Math.min(parent.getColumnCount() - 1, nodeIndex);
	}

	@Override
	protected void after() {
		if (parent.getChildCount() > 0)
			selectColumn((ATreeTableNode) parent.getChildAt(nodeIndex), 0);
	}
}
