package by.dak.furman.financial.service.export.xml

import by.dak.common.persistence.SearchFilter
import by.dak.furman.financial.AObject
import by.dak.furman.financial.Item
import by.dak.furman.financial.service.IItemService
import by.dak.furman.financial.service.export.ExportRequest

/**
 * User: akoiro
 * Date: 19/2/17
 */
class Export2Xml {
    IItemService itemService
    private List<Item> exportedItems = new LinkedList<>()
    private File file = new File(String.format("%s-export.xml", new Date().format("yyyyMMdd-HHmmss")))

    File export(ExportRequest request) {
        request.periods.each {
            SearchFilter searchFilter = itemService.getSearchFilter(request.department, null, null, it)
            List<Item> items = itemService.getAllBy(searchFilter)
            items.each {
                checkUuid(it)
                checkUuid(it.getItemType())
                checkUuid(it.getItemType().getCategory())
                checkUuid(it.getItemType().getCategory().getDepartment())
                itemService.save(it)
            }
            exportedItems.addAll(items)
        }
        return new XMLBuilder(items: exportedItems, file: file).save()
    }

    void deleteAll() {
        exportedItems.each {
            itemService.delete(it)
        }
    }

    File getFile() {
        return file.getAbsoluteFile()
    }

    private static void checkUuid(AObject aObject) {
        if (aObject.getUuid() == null) {
            aObject.setUuid(UUID.randomUUID().toString())
        }
        aObject.setExported(true)
    }
}
