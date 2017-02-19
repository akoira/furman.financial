package by.dak.furman.financial.service.export.excel

import by.dak.furman.financial.Category
import by.dak.furman.financial.Department
import by.dak.furman.financial.ItemType
import by.dak.furman.financial.Period
import org.apache.poi.ss.usermodel.*

import java.text.DecimalFormat

import static org.apache.commons.io.IOUtils.closeQuietly
import static org.apache.commons.lang3.time.DateFormatUtils.format


class ExcelBuilder {

    Department department

    Workbook workbook
    Sheet sheet
    int rowIndex = 0

    void addPeriod(Period period) {
        String name = format(period.getStartDate(), "MM-yyyy")
        sheet = workbook.createSheet(name)
        rowIndex = 0
        Row row = sheet.createRow(rowIndex)

        CellStyle style = workbook.createCellStyle()
        style.fillBackgroundColor = IndexedColors.GREY_50_PERCENT.index
        style.fillPattern = FillPatternType.BIG_SPOTS
        style.fillForegroundColor = IndexedColors.WHITE.index

        Cell cell = row.createCell(0)
        cell.cellValue = 'Категория'
        cell.cellStyle = style

        cell = row.createCell(1)
        cell.cellValue = 'Тип'
        cell.cellStyle = style


        style = workbook.createCellStyle()
        style.fillBackgroundColor = IndexedColors.GREY_50_PERCENT.index
        style.fillPattern = FillPatternType.BIG_SPOTS
        style.alignment = HorizontalAlignment.RIGHT
        style.fillForegroundColor = IndexedColors.WHITE.index

        cell = row.createCell(2)
        cell.cellValue = 'Сумма'
        cell.cellStyle = style

        rowIndex++
    }

    void addCategory(Category category) {
        Row row = sheet.createRow(rowIndex)

        CellStyle style = workbook.createCellStyle()
        style.fillBackgroundColor = IndexedColors.GREY_25_PERCENT.index
        style.fillPattern = FillPatternType.BIG_SPOTS
        style.fillForegroundColor = IndexedColors.WHITE.index

        Cell cell = row.createCell(0)
        cell.cellValue = category.name
        cell.cellStyle = style

        cell = row.createCell(1)
        cell.cellStyle = style
        cell.cellValue = ''

        cell = row.createCell(2)
        cell.cellStyle = style
        cell.cellValue = ''


        rowIndex++
    }

    void addItemType(ItemType itemType, BigDecimal amount) {
        Row row = sheet.createRow(rowIndex)
        row.createCell(0).cellValue = ''

        row.createCell(1).cellValue = itemType.name

        DecimalFormat df = new DecimalFormat('0.00')

        Cell cell = row.createCell(2)
        CellStyle cellStyle = workbook.createCellStyle()
        cellStyle.alignment = HorizontalAlignment.RIGHT
        cell.cellStyle = cellStyle
        cell.cellValue = df.format(amount.toDouble())
        rowIndex++
    }

    File save() {
        FileOutputStream fileOut = null
        try {
            File file = new File(String.format("%s.xlsx", department.getName()))
            fileOut = new FileOutputStream(file)
            workbook.sheetIterator().each { sheet ->
                sheet.autoSizeColumn(0)
                sheet.autoSizeColumn(1)
                sheet.autoSizeColumn(2)
            }
            workbook.write(fileOut)
            return file.getAbsoluteFile()
        } catch (IOException e) {
            throw new IllegalArgumentException(e)
        } finally {
            closeQuietly(fileOut)
        }
    }
}
