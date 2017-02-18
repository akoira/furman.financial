package by.dak.furman.financial.service.export

import by.dak.furman.financial.Category
import by.dak.furman.financial.Department
import by.dak.furman.financial.Period
import groovy.transform.CompileStatic
import groovy.transform.ToString

@ToString
@CompileStatic
class Export2ExcelRequest {
    Department department
    List<Period> periods
    List<Category> categories
}
