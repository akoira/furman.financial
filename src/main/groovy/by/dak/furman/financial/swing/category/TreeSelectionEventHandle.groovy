package by.dak.furman.financial.swing.category

import javax.swing.event.TreeSelectionEvent
import javax.swing.event.TreeSelectionListener
import javax.swing.tree.TreeSelectionModel

/**
 * User: akoiro
 * Date: 18/2/17
 */
class TreeSelectionEventHandle implements TreeSelectionListener {
    ICategoriesPanelDelegate delegate
    TreeSelectionModel treeSelectionModel


    @Override
    void valueChanged(TreeSelectionEvent e) {
        List<ACNode> nodes = TreePathUtils.convert(treeSelectionModel.getSelectionPaths())
        delegate.refreshSelection(nodes)
    }
}