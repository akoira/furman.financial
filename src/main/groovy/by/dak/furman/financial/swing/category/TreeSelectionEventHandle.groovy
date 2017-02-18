package by.dak.furman.financial.swing.category

import javax.swing.event.TreeSelectionEvent
import javax.swing.event.TreeSelectionListener
import javax.swing.tree.TreeSelectionModel

import static by.dak.furman.financial.swing.category.Type.*

/**
 * User: akoiro
 * Date: 18/2/17
 */
class TreeSelectionEventHandle implements TreeSelectionListener {
    ICategoriesPanelDelegate delegate
    TreeSelectionModel treeSelectionModel


    @Override
    void valueChanged(TreeSelectionEvent e) {
        List<ACNode> nodes = treeSelectionModel.getSelectionPaths().collect { (ACNode) it.lastPathComponent }
        switch (valueOf(nodes)) {
            case single:
                ACNode node = nodes.first()
                handleSingle(node)
                break
            case multiple:
                handleMultiple(nodes)
                break
            case empty:
                handleEmpty()
                break
            default:
                throw new IllegalArgumentException()
        }
    }

    private void handleSingle(ACNode node) {
        if (delegate != null) {
            delegate.selectNode(node)
        }

    }

    private void handleMultiple(List<ACNode> nodes) {
        if (delegate != null) {
            delegate.selectNodes(nodes)
        }
    }

    private void handleEmpty() {
        if (delegate != null) {
            delegate.cleanSelection()
        }
    }
}

enum Type {
    single,
    multiple,
    empty

    static Type valueOf(List<ACNode> paths) {
        if (paths.size() == 1)
            return single
        else if (paths.size() > 1)
            return multiple
        else
            return empty
    }
}
