package interfaces;

import java.sql.Date;
import java.util.List;

import javax.ejb.Remote;

import entities.ReclamStatistics;

@Remote
public interface IReclamStatisticsRemote {

	void AddStatReclam(ReclamStatistics Cs);
	List<ReclamStatistics> GetAllStatReclam();
	List<ReclamStatistics> GetStatReclamByDate(Date d);

}
