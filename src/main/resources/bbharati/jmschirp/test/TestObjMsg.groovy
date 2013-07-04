package bbharati.jmschirp.test

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 14/04/13
 * Time: 12:28 AM
 * To change this template use File | Settings | File Templates.
 */
class TestObjMsg implements Serializable{

     static final long serialVersionUID = 1L;

     String strField;

     Integer intField;

     ArrayList listField;  //list of object sof type TestObjMsgInner1


    String toString() {
         return "junkStrField = ${strField} , junkintField = ${intField}, junklistField = ${listField}, junkmapField = ${mapField}";

     }


}

