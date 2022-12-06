package settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlType(propOrder= {"col", "rol", "value", "tileIndex"})
public class BoardSync {

    private int col;
    private int rol;
    private int value;
    private int tileIndex;	
    
	public BoardSync() {
	}

	public BoardSync(int col, int rol, int value, int tileIndex) {
		super();
		this.col = col;
		this.rol = rol;
		this.value = value;
		this.tileIndex = tileIndex;
	}

	public void sync(OnePaneTiles aPane){
		
		try {
			JAXBContext context = JAXBContext.newInstance(settings.OnePaneTiles.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(aPane, new File(getClass().getResource("BoardData.xml").getFile()));
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}
	public OnePaneTiles getBoard() {

		OnePaneTiles pane = new OnePaneTiles();
		try {
			JAXBContext context = JAXBContext.newInstance(settings.OnePaneTiles.class);
			Unmarshaller um = context.createUnmarshaller();
			pane = (OnePaneTiles) um.unmarshal(new FileReader(getClass().getResource("BoardData.xml").getFile()));
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return pane;
	}
	

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@XmlAttribute
	public int getTileIndex() {
		return tileIndex;
	}

	public void setTileIndex(int tileIndex) {
		this.tileIndex = tileIndex;
	}

}
