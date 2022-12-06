package settings;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class InfoListenerModel {
	
	private int labelLevel;
	private int labelNextLevel;
	private int star;
	private PropertyChangeSupport support;
	GameSetting setting = new GameSetting();
	
	public static final String propLevel = "labelLevel";
	public static final String propNextLevel = "labelNextLevel";
	
	
	public InfoListenerModel() {	
		setting.init();
		support = new PropertyChangeSupport(this);
		this.setLabelLevel(setting.getBestLevel());
		this.setLabelNextLevel(setting.getToLevel());
		this.setStar(setting.getStar());
	}
	
	
	public int getLabelLevel() {
		return labelLevel;
	}


	public void setLabelLevel(int labelLevel) {
		support.firePropertyChange(propLevel,  this.labelLevel, labelLevel);
		this.labelLevel = labelLevel;
	}


	public int getLabelNextLevel() {
		return labelNextLevel;
	}
	
	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public void setLabelNextLevel(int labelNextLevel) {
		support.firePropertyChange(propNextLevel,  this.labelNextLevel, labelNextLevel);
		this.labelNextLevel = labelNextLevel;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		    support.addPropertyChangeListener(listener);
	}
	

}
