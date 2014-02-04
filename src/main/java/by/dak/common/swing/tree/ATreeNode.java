package by.dak.common.swing.tree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 4:50 PM
 */
public class ATreeNode<V> extends DefaultMutableTreeNode {
	private V value;

	public V getValue() {
		return (V) getUserObject();
	}

	public void setValue(V value) {
		setUserObject(value);
	}
}
