package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.swing.item.AINode;
import by.dak.furman.financial.swing.item.RootNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

import javax.swing.tree.TreePath;

/**
 * User: akoyro
 * Date: 4/28/13
 * Time: 11:22 PM
 */
public class RefreshHierarchy extends AIAction<AINode>
{
    @Override
    protected void makeAction()
    {
        TreeTableNode[] tableNodes = getModel().getPathToRoot(getNode());
        for (TreeTableNode node : tableNodes)
        {
            getPanel().getRefreshActionFactory().getActionBy((AINode) node).reloadNode();

            if (node != getNode())
                getModel().getModelSupport().firePathChanged(new TreePath(getModel().getPathToRoot(node)));
        }

        if (getPanel().getDelegate() != null)
        {
            getPanel().getDelegate().refreshACNode(((RootNode) getRootNode()).getValue());
        }
    }
}
