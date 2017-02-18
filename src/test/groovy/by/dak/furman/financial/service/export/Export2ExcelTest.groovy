package by.dak.furman.financial.service.export

import by.dak.furman.financial.Period
import by.dak.furman.financial.service.ICategoryService
import by.dak.furman.financial.swing.category.ACNode
import by.dak.furman.financial.swing.category.DepartmentNode
import org.junit.Test

/**
 * Created by pervoliner on 18.02.2017.
 */
class Export2ExcelTest {

    @Test
    public void test() {
        DepartmentNode departmentNode
        ICategoryService categoryService

        Export2ExcelRequest request = new Export2ExcelRequest()
        request.department = departmentNode.department
        request.periods = {
            List<Period> periods = new LinkedList<>()
            int count = departmentNode.childCount
            int index = 0
            while (index < count) {
                periods.add(((ACNode) departmentNode.getChildAt(index)).period)
                index++
            }
            return periods
        }.call()

        request.categories = categoryService.getAllBy(request.department)

    }
}
