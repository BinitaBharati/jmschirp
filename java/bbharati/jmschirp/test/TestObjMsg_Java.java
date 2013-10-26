package bbharati.jmschirp.test;

import java.util.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: bbharati
 * Date: 09/06/13
 * Time: 8:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestObjMsg_Java implements  Serializable{

    private static final long serialVersionUID = 1L;

    private String strField;

    private Integer intField;

    private List<TestObjMsgInner1_Java> listField;  //list of object sof type TestObjMsgInner1_Java

    public String getStrField() {
        return strField;
    }

    public void setStrField(String strField) {
        this.strField = strField;
    }

    public Integer getIntField() {
        return intField;
    }

    public void setIntField(Integer intField) {
        this.intField = intField;
    }

    public List<TestObjMsgInner1_Java> getListField() {
        return listField;
    }

    public void setListField(List<TestObjMsgInner1_Java> listField) {
        this.listField = listField;
    }

    public String toString() {
        return "strField = "+strField+", intField = "+intField+", listField = "+listField;

    }
}
