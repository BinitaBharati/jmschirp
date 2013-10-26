package bbharati.jmschirp.test1;

import java.io.Serializable;
import java.util.List;

public class TstClass1 implements Serializable{
	
	private String tc1StrField;
	
	List<TstClass2> tc1TstClass2ListField;

	public String getTc1StrField() {
		return tc1StrField;
	}

	public void setTc1StrField(String tc1StrField) {
		this.tc1StrField = tc1StrField;
	}

	public List<TstClass2> getTc1TstClass2ListField() {
		return tc1TstClass2ListField;
	}

	public void setTc1TstClass2ListField(List<TstClass2> tc1TstClass2ListField) {
		this.tc1TstClass2ListField = tc1TstClass2ListField;
	}
	
	

}
