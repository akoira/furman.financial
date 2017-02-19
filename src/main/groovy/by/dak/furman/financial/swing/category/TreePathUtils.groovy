package by.dak.furman.financial.swing.category

import by.dak.furman.financial.service.export.ExportRequest
import groovy.transform.CompileStatic

import javax.swing.tree.TreePath

@CompileStatic
class TreePathUtils {
    static List<ACNode> convert(TreePath[] paths) {
        return paths.collect { TreePath path -> (ACNode) path.lastPathComponent }
    }

    static ExportRequest buildRequest(List<ACNode> nodes) {
        ExportRequest request = new ExportRequest()
        request.periods = new LinkedList<>()

        nodes = nodes.findAll({ it instanceof MonthNode })

        if (nodes.size() > 0 ) {
            request.department = nodes.first().department
            request.periods.addAll(nodes.findAll({ request.department }).collect({ it.period }))
        }
        return request
    }
}
