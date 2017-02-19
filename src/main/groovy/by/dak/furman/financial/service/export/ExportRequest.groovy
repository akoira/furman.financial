package by.dak.furman.financial.service.export

import by.dak.furman.financial.Department
import by.dak.furman.financial.Period
import groovy.transform.CompileStatic
import groovy.transform.ToString

@ToString
@CompileStatic
class ExportRequest {
    Department department
    List<Period> periods

    boolean isValid() {
        department != null && periods != null && periods.size() > 0
    }
}
