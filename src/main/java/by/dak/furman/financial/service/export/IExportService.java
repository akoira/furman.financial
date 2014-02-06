package by.dak.furman.financial.service.export;

import by.dak.furman.financial.Item;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

public interface IExportService {
	public void export(List<Item> items, File file);
}
