package Beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.chartistjsf.model.chart.AspectRatio;
import org.chartistjsf.model.chart.Axis;
import org.chartistjsf.model.chart.AxisType;
import org.chartistjsf.model.chart.BarChartModel;
import org.chartistjsf.model.chart.BarChartSeries;
import org.chartistjsf.model.chart.ChartSeries;
import org.primefaces.event.ItemSelectEvent;


import services.ReclamationImp;

@ManagedBean
public class BarChartDataBean implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private BarChartModel barChartModel;
@EJB
ReclamationImp recImp;
public BarChartDataBean() {

}
@PostConstruct
public void init() {
barChartModel = new BarChartModel();
barChartModel.setAspectRatio(AspectRatio.GOLDEN_SECTION);


BarChartSeries series1 = new BarChartSeries();
BarChartSeries series2 = new BarChartSeries();
BarChartSeries series3 = new BarChartSeries();
List<Object[]> listO = recImp.NbTrimestreOpenedRec("Opened");
List<Object[]> listIP = recImp.NbTrimestreOpenedRec("in_progress");
List<Object[]> list = recImp.NbTrimestreRec();



//for(Object[] object : list){ 
for(int j=0;j<list.size() ;j++) {
	
	
	barChartModel.addLabel("Trimestre"+" "+(j+1));
	int x= ((Long)list.get(j)[1]).intValue();
	int y= ((Long)listO.get(j)[1]).intValue();
	
	int z = ((Long)listIP.get(j)[1]).intValue();
	
	series1.set(x);
	
	series2.set(y);
	
	series3.set(z);
	
	
} 

/*for(Object[] object1 : listO){ 
	 
	
	
	series2.set(y);
} 

for(Object[] object : listIP){ 
	

	int Z= ((Long)object[1]).intValue();
	
	series3.set(Z);
	
} */

series1.setName("nombre de Relamations");
series2.setName("nombre de Relamations Opened");
series3.setName("nombre de Relamations InProgress");


barChartModel.addSeries(series1);
barChartModel.addSeries(series2);
barChartModel.addSeries(series3);

Axis xAxis = barChartModel.getAxis(AxisType.X);
xAxis.setShowGrid(false);


barChartModel.setShowTooltip(true);
barChartModel.setSeriesBarDistance(15);
barChartModel.setAnimateAdvanced(true);
}
public BarChartModel getBarChartModel() {
return barChartModel;
}
public void setBarChartModel(BarChartModel barChartModel) {
this.barChartModel = barChartModel;
}
public void barItemSelect(ItemSelectEvent event) {
FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Item Value: "
+ ((ChartSeries) barChartModel.getSeries().get(event.getSeriesIndex())).getData().get(
event.getItemIndex()) + ", Series name:"
+ ((ChartSeries) barChartModel.getSeries().get(event.getSeriesIndex())).getName());
FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);
}
}