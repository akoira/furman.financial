package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.swing.AAction;
import by.dak.furman.financial.swing.item.AINode;
import by.dak.furman.financial.swing.item.ItemsPanel;

import javax.swing.tree.TreePath;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:09 PM
 */
public abstract class AIAction<N extends AINode> extends AAction<ItemsPanel, N>
{
    public void selectColumn(AINode node, String property)
    {
        getPanel().getTreeTable().getTreeSelectionModel().setSelectionPath(new TreePath(getModel().getPathToRoot(node)));
        int column = getPanel().getTreeTable().getColumnModel().getColumnIndex(property);
        getPanel().getTreeTable().getColumnModel().getSelectionModel().setSelectionInterval(column, column);
    }
}
