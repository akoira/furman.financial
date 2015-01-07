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
	private Executor executor = Executors.newSingleThreadExecutor();

	@Override
	protected void makeAction() {
		boolean needRepaint = false;
		TreeTableNode[] tableNodes = getModel().getPathToRoot(getNode());
		for (TreeTableNode node : tableNodes) {
			refreshNode((AINode) node);
			needRepaint = true;
		}

		if (getPanel().getDelegate() != null) {
			getPanel().getDelegate().refreshACNode(((RootNode) getRootNode()).getValue());
		}

		repaint(needRepaint);
	}

	private void repaint(boolean needRepaint) {
		if (needRepaint)
		{
			final Runnable runnable = new Runnable() {
				@Override
				public void run() {
					Runnable swingR = new Runnable() {
						@Override
						public void run() {
							getPanel().getTreeTable().repaint();
						}
					};
					try {
						SwingUtilities.invokeAndWait(swingR);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			executor.execute(runnable);
		}
	}

	private void refreshNode(final AINode node) {
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				getPanel().getRefreshActionFactory().getActionBy(node).reloadNode();
			}
		};

		executor.execute(runnable);
	}
}
