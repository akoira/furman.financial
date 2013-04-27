package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.swing.item.AINode;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/27/13
 * Time: 2:43 PM
 */
public abstract class ARefreshAction<N extends AINode> extends AIAction
{
    protected void removeNodes()
    {
        List<AINode> nodes = Collections.list((Enumeration<AINode>) getParentNode().children());
        for (AINode node : nodes)
        {
            getModel().removeNodeFromParent(node);
        }
    }
}
