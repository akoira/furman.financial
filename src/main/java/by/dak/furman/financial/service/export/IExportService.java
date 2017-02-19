package by.dak.furman.financial.service.export;

import java.io.File;

public interface IExportService {
	File export(ExportRequest request);
}
