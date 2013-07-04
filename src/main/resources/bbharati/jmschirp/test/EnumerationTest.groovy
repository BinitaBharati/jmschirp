package bbharati.jmschirp.test
/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 14/06/13
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
class EnumerationTest implements Enumeration<TestObjMsg>{

     List<TestObjMsg> enumElment
     int index = 0;

    def init(List<TestObjMsg> enumElment1)
    {
        enumElment = enumElment1

    }

    @Override
    boolean hasMoreElements() {
        return true  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    TestObjMsg nextElement() {
        return enumElment.get(index)  //To change body of implemented methods use File | Settings | File Templates.
    }
}
