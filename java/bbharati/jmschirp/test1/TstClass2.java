package bbharati.jmschirp.test1;

import java.io.Serializable;

public class TstClass2 implements Serializable{
	
	private int id;
	
	private TstClass3 tc2TstClass3Field;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TstClass3 getTc2TstClass3Field() {
		return tc2TstClass3Field;
	}

	public void setTc2TstClass3Field(TstClass3 tc2TstClass3Field) {
		this.tc2TstClass3Field = tc2TstClass3Field;
	}

	
}
