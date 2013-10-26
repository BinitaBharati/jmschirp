package bbharati.jmschirp.test1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class TstClassObjGenerator {
	
	public static TstClass1 generateObj(int i)
	{
		
			TstClass3 tc3 = new TstClass3();
			tc3.setTc3IntField(i);
			tc3.setTc3IntField1(new Integer(i));
			tc3.setTc3StrField("TC3- STR field = "+i);
			
			List<String> tc3ListField = new ArrayList<String>();
			tc3ListField.add("tc3ListField - "+i);
			tc3ListField.add("tc3ListField - "+(i+1));
			tc3.setTc3ListField(tc3ListField);
			
			Map<String, String> tc3MapField = new LinkedHashMap<String, String>();
			tc3MapField.put("tc3MapFieldKey - "+i, "tc3MapFieldVal - "+i);
			tc3MapField.put("tc3MapFieldKey - "+(i+1), "tc3MapFieldVal - "+(i+1));
			tc3.setTc3MapField(tc3MapField);
			
			TstClass2 tc2 = new TstClass2();
			tc2.setId(i);
			tc2.setTc2TstClass3Field(tc3);
			
			TstClass1 tc1 = new TstClass1();
			tc1.setTc1StrField("TC1 STR field = "+i);
			
			List<TstClass2> tc1Tc2List = new ArrayList<TstClass2>();
			tc1Tc2List.add(tc2);
			tc1.setTc1TstClass2ListField(tc1Tc2List);
			
			return tc1;
				
	}

}
