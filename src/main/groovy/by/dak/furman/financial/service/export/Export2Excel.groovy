package by.dak.furman.financial.service.export

import by.dak.common.persistence.SearchFilter
import by.dak.furman.financial.Department
import by.dak.furman.financial.Period
import by.dak.furman.financial.service.ICategoryService
import by.dak.furman.financial.service.IItemService
import by.dak.furman.financial.service.IItemTypeService
import by.dak.furman.financial.swing.category.ACNode
import by.dak.furman.financial.swing.category.APeriodNode
import by.dak.furman.financial.swing.category.DepartmentNode
import groovy.transform.CompileStatic
import org.apache.poi.xssf.usermodel.XSSFWorkbook

@CompileStatic
class Export2Excel {
    ICategoryService categoryService
    IItemTypeService itemTypeService
    IItemService itemService

    void export(Department department, List<Period> periods) {
        Export2ExcelRequest request = new Export2ExcelRequest().with {
            it.department = department
            it.periods = periods
            it.categories = categoryService.getAllBy(department)
            it
        }

        export { request }
    }

    File export(Export2ExcelRequest request) {
        request.categories = categoryService.getAllBy(request.department)
        return export {request}
    }

    File export(Closure<Export2ExcelRequest> closure) {

        Export2ExcelRequest request = closure.call()

        ExcelBuilder builder = new ExcelBuilder().with {
            it.workbook = new XSSFWorkbook()
            it.department = request.department
            it
        }
        request.periods.each { period ->
            builder.addPeriod(period)

            request.categories.each { category ->
                builder.addCategory(category)
                itemTypeService.getAllBy(category).each { itemType ->
                    SearchFilter searchFilter = itemService.getSearchFilter(request.department, category, itemType, period)
                    builder.addItemType(itemType, itemService.getSumBy(searchFilter))
                }
            }
        }
        return builder.save()
    }

    private Export2ExcelRequest buildFromPeriodNode(Closure<APeriodNode> nodeClosure) {
        Export2ExcelRequest request = new Export2ExcelRequest()
        APeriodNode node = nodeClosure.call()
        request.department = node.department

        request.periods = [node.period]
        request.categories = categoryService.getAllBy(request.department)
        return request
    }

    private Export2ExcelRequest buildFromDepartmentNode(Closure<DepartmentNode> departmentNode) {
        Export2ExcelRequest request = new Export2ExcelRequest()
        DepartmentNode node = departmentNode.call()
        request.department = node.department

        request.periods = node.children.collect({ ((ACNode) it).children }).flatten().collect { ((ACNode) it).period }
        request.categories = categoryService.getAllBy(request.department)
        return request
    }

}
