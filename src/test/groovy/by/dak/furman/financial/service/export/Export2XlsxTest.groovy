package by.dak.furman.financial.service.export

import by.dak.common.persistence.SearchFilter
import by.dak.furman.financial.Category
import by.dak.furman.financial.Department
import by.dak.furman.financial.ItemType
import by.dak.furman.financial.Period
import by.dak.furman.financial.app.AppConfig
import by.dak.furman.financial.app.FinancialApp
import org.apache.commons.lang3.time.DateUtils
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.Test

import java.text.DecimalFormat

import static by.dak.furman.financial.PeriodType.MONTH
import static java.util.Calendar.DAY_OF_MONTH
import static java.util.Calendar.YEAR
import static org.apache.commons.io.IOUtils.closeQuietly
import static org.apache.commons.lang3.time.DateFormatUtils.format
import static org.apache.commons.lang3.time.DateUtils.parseDate

/**
 * User: akoiro
 * Date: 18/2/17
 */
class Export2XlsxTest {

    @Test
    void test() throws IOException {
        new FinancialApp.DbServer().start()
        AppConfig appConfig = AppConfig.getAppConfig()

        List<Department> departments = appConfig.getDepartmentService().getAllSortedBy(Department.PROPERTY_name)
        List<Period> periods = getPeriods()

        Department department = appConfig.departmentService.getByName('Алюминий')
        Period period = new Period().with { self ->
            use(DateUtils) {
                self.startDate = parseDate('01-01-2016', 'dd-MM-yyyy').truncate(DAY_OF_MONTH)
                self.endDate = self.startDate.addMonths(1)
                self.periodType = MONTH
            }
            self
        }


        ExcelBuilder processor = new ExcelBuilder().with {
            it.workbook = new XSSFWorkbook()
            it.department = department
            it
        }
        processor.addPeriod(period)
        appConfig.getCategoryService().getAllBy(department).each { category ->
            processor.addCategory(category)

            appConfig.itemTypeService.getAllBy(category).each { itemType ->
                SearchFilter searchFilter = appConfig.itemService.getSearchFilter(department, category, itemType, period)
                processor.addItemType(itemType, appConfig.itemService.getSumBy(searchFilter))
            }
        }
        processor.save()
    }

    private List<Period> getPeriods() {
        List<Period> result = new LinkedList<>()

        use(DateUtils) {
            Date today = new Date()
            Period period = new Period().with {
                it.periodType = MONTH
                it.startDate = today.truncate(YEAR).addYears(-1)
                it.endDate = it.startDate.addMonths(1)
                it
            }
            result.add(period)
            while (period.endDate.before(today)) {
                period = new Period().with {
                    it.periodType = period.periodType
                    it.startDate = period.endDate
                    it.endDate = period.endDate.addMonths(1)
                    it
                }
                result.add(period)
            }
        }
        return result
    }
}

