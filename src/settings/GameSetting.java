package settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="GameData")
@XmlType(propOrder= {"col", "rol", "star", "bestLevel", "toLevel" })
public class GameSetting {

	 private static int col;
	 private static int rol;
	 private static int star;
	 private static int bestLevel;
	 private static int toLevel;

	public void init(){
			
			GameSetting gameData = new GameSetting();
			try {
				JAXBContext context = JAXBContext.newInstance(GameSetting.class);
				Unmarshaller um = context.createUnmarshaller();
				gameData = (GameSetting) um.unmarshal(new FileReader(getClass().getResource("GameData.xml").getFile()));
			} catch (JAXBException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			col = gameData.getCol();
			rol = gameData.getRol();
			star = gameData.getStar();
			bestLevel = gameData.getBestLevel();
			toLevel = gameData.getToLevel();

	}
	public void write(GameSetting setting){
			
			try {
				JAXBContext context = JAXBContext.newInstance(settings.GameSetting.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(setting, new File(getClass().getResource("GameData.xml").getFile()));
			} catch (JAXBException e) {
				e.printStackTrace();
			} 
	
	
		}
	
	 public int getCol() {
		return col;
	}

	public void setCol(int col) {
		GameSetting.col = col;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		GameSetting.rol = rol;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		GameSetting.star = star;
	}

	public int getBestLevel() {
		return bestLevel;
	}

	public void setBestLevel(int bestLevel) {
		GameSetting.bestLevel = bestLevel;
	}

	public int getToLevel() {
		return toLevel;
	}

	public void setToLevel(int toLevel) {
		GameSetting.toLevel = toLevel;
	}
	
	public void changeBestLevel(int bestLevel) {
		GameSetting gameData = new GameSetting();
		gameData.setBestLevel(bestLevel);
		write(gameData);
	}
	
	public void changeStar(int star) {
		GameSetting gameData = new GameSetting();
		gameData.setStar(star);
		write(gameData);	
	}
	
	public void changeDimension(int dim) {
		GameSetting gameData = new GameSetting();
		gameData.setCol(dim);
		gameData.setRol(dim);
		write(gameData);	
	}
	
	public void changeToLevel(int toLevel) {
		GameSetting gameData = new GameSetting();
		gameData.setToLevel(toLevel);
		write(gameData);
	}
	
}
