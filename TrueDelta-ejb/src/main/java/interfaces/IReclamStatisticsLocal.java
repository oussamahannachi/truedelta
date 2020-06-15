package interfaces;

import java.sql.Date;
import java.util.List;

import javax.ejb.Local;


import entities.ReclamStatistics;

@Local

public interface IReclamStatisticsLocal {

	void AddStatReclam(ReclamStatistics Cs);
	List<ReclamStatistics> GetAllStatReclam();
	List<ReclamStatistics> GetStatReclamByDate(Date d);

	
	
}
