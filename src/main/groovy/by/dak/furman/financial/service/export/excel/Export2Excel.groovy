package by.dak.furman.financial.service.export.excel

import by.dak.common.persistence.SearchFilter
import by.dak.furman.financial.Category
import by.dak.furman.financial.Department
import by.dak.furman.financial.Period
import by.dak.furman.financial.service.ICategoryService
import by.dak.furman.financial.service.IItemService
import by.dak.furman.financial.service.IItemTypeService
import by.dak.furman.financial.service.export.ExportRequest
import groovy.transform.CompileStatic
import org.apache.poi.xssf.usermodel.XSSFWorkbook

@CompileStatic
class Export2Excel {
    ICategoryService categoryService
    IItemTypeService itemTypeService
    IItemService itemService

    void export(Department department, List<Period> periods) {
        ExportRequest request = new ExportRequest().with {
            it.department = department
            it.periods = periods
            it
        }

        export { request }
    }

    File export(ExportRequest request) {
        return export { request }
    }

    File export(Closure<ExportRequest> closure) {

        ExportRequest request = closure.call()

        ExcelBuilder builder = new ExcelBuilder().with {
            it.workbook = new XSSFWorkbook()
            it.department = request.department
            it
        }
        List<Category> categories = categoryService.getAllBy(request.department)
        request.periods.each { period ->
            builder.addPeriod(period)

            categories.each { category ->
                builder.addCategory(category)
                itemTypeService.getAllBy(category).each { itemType ->
                    SearchFilter searchFilter = itemService.getSearchFilter(request.department, category, itemType, period)
                    builder.addItemType(itemType, itemService.getSumBy(searchFilter))
                }
            }
        }
        return builder.save()
    }
}
