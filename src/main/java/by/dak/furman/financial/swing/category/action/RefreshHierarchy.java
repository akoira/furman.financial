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
	private Executor executor = Executors.newSingleThreadExecutor();

	@Override
	protected void makeAction() {
		boolean needToRepaint = false;
		TreeTableNode[] tableNodes = getModel().getPathToRoot(getNode());
		for (TreeTableNode node : tableNodes) {
			refreshNode((ACNode) node);
			needToRepaint = true;
		}
		repaint(needToRepaint);
	}

	private void repaint(boolean needRepaint) {
		if (needRepaint)
		{
			final Runnable runnable = () -> {
                Runnable swingR = () -> getPanel().getTreeTable().repaint();
                try {
                    SwingUtilities.invokeAndWait(swingR);
                } catch (Exception e) {

                }
            };
			executor.execute(runnable);
		}
	}


	private void refreshNode(final ACNode node) {
		final Runnable runnable = () -> {
            ACRefreshAction action = getPanel().getRefreshActionFactory().getActionBy(node);
            action.reloadNode();
            action.reloadChildren();
        };
		executor.execute(runnable);
	}


}
