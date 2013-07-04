package bbharati.jmschirp.test

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 15/04/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
class TestObjMsgInner1 implements Serializable{

    static final long serialVersionUID = 1L;

    String innerStrField;

    LinkedHashMap inner2ObjMap;    //map of String to TestObjMsgInner2 objects

    /*String toString() {
        return "junkinnerStrField = ${innerStrField} , junkinner2ObjMap = ${inner2ObjMap}";

    } */
}
