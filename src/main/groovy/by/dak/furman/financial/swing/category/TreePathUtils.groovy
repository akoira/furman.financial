package by.dak.furman.financial.swing.category

import by.dak.furman.financial.service.export.Export2ExcelRequest
import groovy.transform.CompileStatic

import javax.swing.tree.TreePath

@CompileStatic
class TreePathUtils {
    static List<ACNode> convert(TreePath[] paths) {
        return paths.collect { TreePath path -> (ACNode) path.lastPathComponent }
    }

    static Export2ExcelRequest buildRequest(List<ACNode> nodes) {
        Export2ExcelRequest request = new Export2ExcelRequest()
        request.periods = new LinkedList<>()

        nodes = nodes.findAll({ it instanceof MonthNode })

        if (nodes.size() > 0 ) {
            request.department = nodes.first().department
            request.periods.addAll(nodes.findAll({request.department }).collect({ it.period }))
            return request
        } else {
            return null
        }
    }
}
