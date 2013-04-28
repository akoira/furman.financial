package by.dak.furman.financial.swing.item.action;

import by.dak.furman.financial.swing.item.ItemTypeNode;

/**
 * User: akoyro
 * Date: 4/29/13
 * Time: 12:09 AM
 */
public class DeleteItemType extends AIAction<ItemTypeNode>
{
    @Override
    protected void makeAction()
    {
        getItemTypeService().delete(getNode().getValue());
        getModel().removeNodeFromParent(getNode());
    }

    @Override
    protected boolean validate()
    {
        return getNode().getValue().getId() != null;
    }

}