package settings;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class OnePaneTiles {
	@XmlElementWrapper
	@XmlElement(name="Tile")
	private ArrayList<BoardSync> listToSync = new ArrayList<>();

	public void add(BoardSync tile) {
		this.listToSync.add(tile);
	}

	public ArrayList<BoardSync> getListToSync() {
		return listToSync;
	}

	
	
}
