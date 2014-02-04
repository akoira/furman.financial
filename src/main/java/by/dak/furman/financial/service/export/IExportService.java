package by.dak.furman.financial.service.export;

import by.dak.furman.financial.Item;

import java.io.File;
import java.util.List;

public interface IExportService {
	public void export(List<Item> items, File file);
}
