package by.dak.furman.financial.service;

import by.dak.furman.financial.ImportedFile;
import by.dak.furman.financial.dao.IDao;
import by.dak.furman.financial.dao.IImportedFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportedFileService extends AService<ImportedFile> implements IImportedFileService {

	@Autowired
	private IImportedFileDao dao;

	@Override
	protected IDao<ImportedFile> getDao() {
		return dao;
	}
}
