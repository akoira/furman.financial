package by.dak.furman.financial.swing.category;

import org.jdesktop.swingx.treetable.TreeTableNode;

import javax.swing.tree.TreePath;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:27 PM
 */
public class RootNode extends APeriodNode
{

    public TreePath getCurrentPeriodPath()
    {
        TreePath path = new TreePath(this);
        return initPath(this, path);
    }

    private TreePath initPath(TreeTableNode node, TreePath path)
    {
        int count = node.getChildCount();
        for (int i = 0; i < count; i++)
        {
            TreeTableNode child = node.getChildAt(i);
            if (child instanceof APeriodNode && ((APeriodNode) child).getValue().isCurrent())
            {
                path = path.pathByAddingChild(child);
                path = initPath(child, path);
            }
        }
        return path;
    }
}
