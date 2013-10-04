package bbharati.jmschirp.test;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bbharati
 * Date: 09/06/13
 * Time: 8:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestObjMsgInner2_Java implements Serializable {

    private static final long serialVersionUID = 1L;

    private String inner3StrField;

    private int inner3IntField;

    private List inner3ListField; //list of strings

    private Map<String, String> inner3MapField;  //map of (string, string)

    /* {TestObjMsgInner2Key1 = [TestObjMsgInner3_1, TestObjMsgInner3_2], TestObjMsgInner2Key2=[TestObjMsgInner3_3, TestObjMsgInner3_4]}  */
    private Map<String, List<TestObjMsgInner3_Java>> inner3MapField2;

    private long[] inner3LongAry;

    private Long[] inner3LongObjAry;

    public String getInner3StrField() {
        return inner3StrField;
    }

    public void setInner3StrField(String inner3StrField) {
        this.inner3StrField = inner3StrField;
    }

    public int getInner3IntField() {
        return inner3IntField;
    }

    public void setInner3IntField(int inner3IntField) {
        this.inner3IntField = inner3IntField;
    }

    public List getInner3ListField() {
        return inner3ListField;
    }

    public void setInner3ListField(List inner3ListField) {
        this.inner3ListField = inner3ListField;
    }

    public Map<String, String> getInner3MapField() {
        return inner3MapField;
    }

    public void setInner3MapField(Map<String, String> inner3MapField) {
        this.inner3MapField = inner3MapField;
    }

    public Map<String, List<TestObjMsgInner3_Java>> getInner3MapField2() {
        return inner3MapField2;
    }

    public void setInner3MapField2(Map<String, List<TestObjMsgInner3_Java>> inner3MapField2) {
        this.inner3MapField2 = inner3MapField2;
    }

    public long[] getInner3LongAry() {
        return inner3LongAry;
    }

    public void setInner3LongAry(long[] inner3LongAry) {
        this.inner3LongAry = inner3LongAry;
    }

    public Long[] getInner3LongObjAry() {
        return inner3LongObjAry;
    }

    public void setInner3LongObjAry(Long[] inner3LongObjAry) {
        this.inner3LongObjAry = inner3LongObjAry;
    }
}
