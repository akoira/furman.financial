package by.dak.furman.financial.service.export;

import by.dak.furman.financial.*;
import by.dak.furman.financial.service.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
public class ImportService implements IImportService {

	@Autowired
	private IItemService itemService;

	@Autowired
	private IItemTypeService itemTypeService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IDepartmentService departmentService;

	@Autowired
	private IImportedFileService importedFileService;

	@Override
	@Transactional(readOnly = false)
	public void importData(File file) {
		ImportedFile importedFile = importedFileService.getByName(file.getName());
		if (importedFile != null)
			return;

		XStream xStream = new XStream(new DomDriver("UTF-8"));
		xStream.omitField(Item.class, Item.PROPERTY_id);
		xStream.omitField(ItemType.class, ItemType.PROPERTY_id);
		xStream.omitField(Category.class, Category.PROPERTY_id);
		xStream.omitField(Department.class, Department.PROPERTY_id);


		List<Item> items = (List<Item>) xStream.fromXML(file);

		for (Item item : items) {
			String uuid = item.getItemType().getUuid();
			ItemType itemType = itemTypeService.getByUuid(uuid);
			if (itemType != null) {
				item.setItemType(itemType);
			} else {
				uuid = item.getItemType().getCategory().getUuid();
				Category category = categoryService.getByUuid(uuid);
				if (category != null) {
					item.getItemType().setCategory(category);
				} else {
					uuid = item.getItemType().getCategory().getDepartment().getUuid();
					Department department = departmentService.getByUuid(uuid);
					if (department != null) {
						item.getItemType().getCategory().setDepartment(department);
					}
				}
			}
			itemService.add(item);
		}

		importedFile = new ImportedFile();
		importedFile.setName(file.getName());
		importedFileService.add(importedFile);
	}
}
