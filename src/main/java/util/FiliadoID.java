package main.java.util;

import java.util.List;

import main.java.model.dao.DAO;
import main.java.model.dao.DAOImpl;

public class FiliadoID {
	private long qtd;

	private static DAO<FiliadoID> dao;
	private static FiliadoID ref;

	static {
		dao = new DAOImpl<FiliadoID>(FiliadoID.class);
		ref = getInstance();
	}

	private FiliadoID() {
		this.qtd = 0;
	}

	public synchronized static long getNextID() {
		ref.qtd++;
		dao.save(ref);
		return ref.qtd;
	}

	private static FiliadoID getInstance() {
		List<FiliadoID> list = dao.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			FiliadoID fid = new FiliadoID();
			dao.save(fid);
			return fid;
		}
	}
}
