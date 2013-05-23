package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.swing.category.ACNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

import javax.swing.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * User: akoyro
 * Date: 4/28/13
 * Time: 11:22 PM
 */
public class RefreshHierarchy extends ACAction<ACNode> {
	private Executor executor = Executors.newCachedThreadPool();

	@Override
	protected void makeAction() {
		TreeTableNode[] tableNodes = getModel().getPathToRoot(getNode());
		for (TreeTableNode node : tableNodes) {
			refreshNode((ACNode) node);
		}
	}


	private void refreshNode(final ACNode node) {
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				ACRefreshAction action = getPanel().getRefreshActionFactory().getActionBy(node);
				action.reloadNode();
				getPanel().getTreeTable().repaint();
				action.reloadChildren();
			}
		};

		Runnable oRunnable = new Runnable() {
			@Override
			public void run() {
				try {
					SwingUtilities.invokeAndWait(runnable);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		executor.execute(oRunnable);
	}


}
