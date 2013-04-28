package by.dak.furman.financial.swing.category;

import by.dak.common.lang.StringValueAnnotationProcessor;
import by.dak.furman.financial.swing.ATreeTableNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 1:53 PM
 */
public class DefaultTreeTableRenderer extends DefaultTreeCellRenderer
{

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        if (value instanceof ATreeTableNode)
        {
            value = ((ATreeTableNode) value).getValue();
        }
        value = StringValueAnnotationProcessor.getProcessor().convert(value);
        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    }
}
