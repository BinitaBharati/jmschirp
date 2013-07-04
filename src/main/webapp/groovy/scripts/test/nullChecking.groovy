import bbharati.jmschirp.test.EnumerationTest
import bbharati.jmschirp.test.TestObjMsg
import bbharati.jmschirp.test.TestObjMsgInner1
import bbharati.jmschirp.test.TestObjMsg

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 14/06/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 *
 *
 */

TestObjMsg obj1 = new TestObjMsg()
obj1.intField = 45
obj1.strField = "hello"

TestObjMsg obj2 = null

List<TestObjMsg>  enumList = new ArrayList<TestObjMsg>()
enumList.add(obj1)

EnumerationTest enumTest = new EnumerationTest();
enumTest.init(enumList)

EnumerationTest enumTest1 = null


def flag = isObjectNULL(obj1)

def flag2 = isObjectNULL(obj2)

def flag3 = isObjectNULL(enumTest)

def flag4 = isObjectNULL(enumTest1)


def isObjectNULL(obj)
{
      if (obj)
      {
          println("obj is not NULL")
          return false
      }
    else
      {
          println("obj is nULL")
          return true
      }
}


