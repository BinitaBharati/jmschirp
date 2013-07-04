package bbharati.jmschirp.test

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 15/04/13
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
class TestObjMsgInner2 implements Serializable{

    static final long serialVersionUID = 1L;

    String inner3StrField;

    int inner3IntField;

    List inner3ListField; //list of strings

    Map inner3MapField;  //map of (string, string)

    /* {TestObjMsgInner2Key1 = [TestObjMsgInner3_1, TestObjMsgInner3_2], TestObjMsgInner2Key2=[TestObjMsgInner3_3, TestObjMsgInner3_4]}  */
    Map inner3MapField2;

    long[] inner3LongAry;

    Long[] inner3LongObjAry;


}
