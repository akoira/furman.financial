package by.dak.furman.financial.swing.category;

import org.jdesktop.swingx.treetable.TreeTableNode;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:27 PM
 */
public class RootNode extends APeriodNode {

	public APeriodNode getCurrentNode() {
		return getCurrentNode(this);
	}

	private APeriodNode getCurrentNode(APeriodNode node) {
		int count = node.getChildCount();
		for (int i = 0; i < count; i++) {
			TreeTableNode child = node.getChildAt(i);
			if (child instanceof APeriodNode && ((APeriodNode) child).getValue().isCurrent()) {
				return getCurrentNode((APeriodNode) child);
			}
		}
		return node;
	}
}
