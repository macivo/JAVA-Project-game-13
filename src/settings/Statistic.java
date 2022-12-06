package settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(propOrder= {"topLevel", "date", "move"})
public class Statistic implements Comparable<Statistic>{
	private int topLevel;
	private String date;
	private int move;
	private void write(StatisticList stateList){
		
		try {
			JAXBContext context = JAXBContext.newInstance(settings.Statistic.StatisticList.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(stateList, new File(getClass().getResource("Statistic.xml").getFile()));
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public StatisticList readStatistic(){
		
		StatisticList stateList = new StatisticList();
		try {
			JAXBContext context = JAXBContext.newInstance(settings.Statistic.StatisticList.class);
			Unmarshaller um = context.createUnmarshaller();
			stateList = (StatisticList) um.unmarshal(new FileReader(getClass().getResource("Statistic.xml").getFile()));
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return stateList;
	}
	
	@XmlRootElement 
	public static class StatisticList{
		@XmlElementWrapper
		@XmlElement(name="stateList")
		private ArrayList<Statistic> stateList = new ArrayList<>();

		public void add(Statistic state) {
			stateList.add(state);		
		}
		public ArrayList<Statistic> getStateList() {
			return stateList;
		}
		public void clear() {
			stateList.clear();
		}
	}
	
	public void checkStatistic(int topLevel, int move) {
		StatisticList stateListObj = new StatisticList();
		stateListObj = readStatistic();
		ArrayList<Statistic> stateListTemp = new ArrayList<>(); 
		stateListTemp.addAll(stateListObj.getStateList());

		this.topLevel = topLevel;
		Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy HH:mm");  
        this.date = dateFormat.format(date); 
        this.move = move;
		stateListTemp.add(this);
		Collections.sort(stateListTemp);
		while (stateListTemp.size() >= 5) {
		stateListTemp.remove(stateListTemp.size()-1);
		}
		stateListObj.clear();
		for (Statistic s: stateListTemp) {
			stateListObj.add(s);
		}
		write(stateListObj);
	}
	
	public int getTopLevel() {
		return topLevel;
	}
	public void setTopLevel(int topLevel) {
		this.topLevel = topLevel;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getMove() {
		return move;
	}
	public void setMove(int move) {
		this.move = move;
	}

	@Override
	public int compareTo(Statistic o) {
		int sumThis = (this.getTopLevel()*10000)+this.getMove();
		int sumO =(o.getTopLevel()*10000)+o.getMove();
		return(sumO - sumThis);
	}
	
	
}
