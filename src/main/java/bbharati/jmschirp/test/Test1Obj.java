package bbharati.jmschirp.test;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: bbharati
 * Date: 03/07/13
 * Time: 5:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test1Obj implements  Serializable{

    String test;

    public Test1Obj()
    {}

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
