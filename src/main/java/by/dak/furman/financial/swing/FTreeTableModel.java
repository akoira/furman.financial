package by.dak.furman.financial.swing;

import org.jdesktop.swingx.tree.TreeModelSupport;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

/**
 * User: akoyro
 * Date: 4/29/13
 * Time: 12:32 AM
 */
public class FTreeTableModel extends DefaultTreeTableModel
{

    public FTreeTableModel(ATreeTableNode root)
    {
        super(root);
    }

    public TreeModelSupport getModelSupport()
    {
        return modelSupport;
    }
}
