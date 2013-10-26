package bbharati.jmschirp.test;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: bbharati
 * Date: 09/06/13
 * Time: 8:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestObjMsgInner1_Java implements Serializable {

    private static final long serialVersionUID = 1L;

    private String innerStrField;

    private LinkedHashMap<String,TestObjMsgInner2_Java> inner2ObjMap;    //map of String to TestObjMsgInner2 objects

    public String getInnerStrField() {
        return innerStrField;
    }

    public void setInnerStrField(String innerStrField) {
        this.innerStrField = innerStrField;
    }

    public LinkedHashMap<String, TestObjMsgInner2_Java> getInner2ObjMap() {
        return inner2ObjMap;
    }

    public void setInner2ObjMap(LinkedHashMap<String, TestObjMsgInner2_Java> inner2ObjMap) {
        this.inner2ObjMap = inner2ObjMap;
    }
}
