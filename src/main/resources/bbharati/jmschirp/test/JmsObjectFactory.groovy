package bbharati.jmschirp.test
/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 17/04/13
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
class JmsObjectFactory {

    def static generateObject(i)
    {
        def testObjMsgInner2 = new TestObjMsgInner2_Java()
        testObjMsgInner2.setInner3StrField("I AM TestObjMsgInner2's Inner3StrField -  ${i}")
        testObjMsgInner2.setInner3IntField(i)

        def testObjMsgInner2ListField = []
        for (def j = i ; j < i + 5 ; j++ )
        {
            testObjMsgInner2ListField.add("TestObjMsgInner2's ListField - "+j)
        }
        testObjMsgInner2.setInner3ListField(testObjMsgInner2ListField)

        def testObjMsgInner2MapField  = [:]
        for (def j = i ; j < i + 5 ; j++ )
        {
            testObjMsgInner2MapField["TestObjMsgInner2's MapKey - $i"]  =  i
        }
        testObjMsgInner2.setInner3MapField(testObjMsgInner2MapField)

        def  testObjMsgInner3List = []

        for (def j = i ; j < i + 5 ; j++ )
        {
            def testObjMsgInner3 = new TestObjMsgInner3_Java();
            testObjMsgInner3.setTestObjMsgInner3ByteField(Byte.parseByte(j+""));
            testObjMsgInner3.setTestObjMsgInner3CharField('C'.charAt(0));
            testObjMsgInner3.setTestObjMsgInner3DoubleField(123.4);
            testObjMsgInner3.setTestObjMsgInner3Flag(true);
            testObjMsgInner3.setTestObjMsgInner3FloatField(123.4f)
            testObjMsgInner3.setTestObjMsgInner3IntField(444);
            testObjMsgInner3.setTestObjMsgInner3LongField(9999L)
            testObjMsgInner3.setTestObjMsgInner3ShortField((short)1000)
            testObjMsgInner3List.add(testObjMsgInner3)
        }


        def  testObjMsgInner3Map2 = [:]
        for (def j = i ; j < i + 5 ; j++ )
        {
            testObjMsgInner3Map2["testObjMsgInner3Map2Key-"+j] = testObjMsgInner3List
        }


        testObjMsgInner2.setInner3MapField2(testObjMsgInner3Map2)



        long[] inner3LongAry =[1,2,3,4,5]
        testObjMsgInner2.setInner3LongAry(inner3LongAry)
        Long[]  inner3LongObjAry = new Long[4];
        inner3LongObjAry[0] = 10
        inner3LongObjAry[1] = 11
        inner3LongObjAry[2] = 12
        inner3LongObjAry[3] = 13

        testObjMsgInner2.setInner3LongObjAry(inner3LongObjAry)

        def  testObjMsgInner2Map = [:]
        for (def j = i ; j < i + 5 ; j++ )
        {
            testObjMsgInner2Map["testObjMsgInner2  - ${j}"] =  testObjMsgInner2
        }



        def testObjMsgInner1 = new TestObjMsgInner1_Java()
        testObjMsgInner1.setInnerStrField("TestObjMsgInner1's str field - ${i}")
        testObjMsgInner1.setInner2ObjMap(testObjMsgInner2Map)

        def msg = new TestObjMsg_Java();
        msg.setIntField(i);
        msg.setStrField("hello am str field - "+i);


        def listField = [];
        for (def j = i ; j < i + 5 ; j++ )
        {
            listField.add(testObjMsgInner1)
        }

        msg.setListField(listField) ;

        return msg

    }
}
