package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.swing.item.AINode;
import by.dak.furman.financial.swing.item.RootNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

import javax.swing.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * User: akoyro
 * Date: 4/28/13
 * Time: 11:22 PM
 */
public class RefreshHierarchy extends AIAction<AINode> {
	private Executor executor = Executors.newCachedThreadPool();

	@Override
	protected void makeAction() {
		TreeTableNode[] tableNodes = getModel().getPathToRoot(getNode());
		for (TreeTableNode node : tableNodes) {
			refreshNode((AINode) node);
		}

		if (getPanel().getDelegate() != null) {
			getPanel().getDelegate().refreshACNode(((RootNode) getRootNode()).getValue());
		}
	}

	private void refreshNode(final AINode node) {
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				getPanel().getRefreshActionFactory().getActionBy(node).reloadNode();

				if (node != getNode())
					getPanel().getTreeTable().repaint();

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
