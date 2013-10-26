package bbharati.jmschirp.test;

import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: bbharati
 * Date: 09/06/13
 * Time: 8:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class JmsObjectFactory_Java {

    public static TestObjMsg_Java generateObject(int i)
    {
        TestObjMsgInner2_Java testObjMsgInner2 = new TestObjMsgInner2_Java();
        testObjMsgInner2.setInner3StrField("I AM TestObjMsgInner2's Inner3StrField - "+i) ;
        testObjMsgInner2.setInner3IntField(i);

        ArrayList<String> testObjMsgInner2ListField = new ArrayList<String>();
        for (int j = i ; j < i + 5 ; j++ )
        {
            testObjMsgInner2ListField.add("TestObjMsgInner2's ListField - "+j) ;
        }
        testObjMsgInner2.setInner3ListField(testObjMsgInner2ListField);

        LinkedHashMap<String, String> testObjMsgInner2MapField  = new LinkedHashMap<String, String>();
        for (int j = i ; j < i + 5 ; j++ )
        {
            testObjMsgInner2MapField.put("TestObjMsgInner2's MapKey - "+i,i+"");
        }
        testObjMsgInner2.setInner3MapField(testObjMsgInner2MapField) ;

        ArrayList<TestObjMsgInner3_Java>  testObjMsgInner3List = new ArrayList<TestObjMsgInner3_Java>();

        for (int j = 0 ; j < 5 ; j++ )
        {
            TestObjMsgInner3_Java testObjMsgInner3 = new TestObjMsgInner3_Java();
            testObjMsgInner3.setTestObjMsgInner3ByteField(new Byte(j+""));
            testObjMsgInner3.setTestObjMsgInner3CharField('C');
            testObjMsgInner3.setTestObjMsgInner3DoubleField(123.4);
            testObjMsgInner3.setTestObjMsgInner3Flag(true);
            testObjMsgInner3.setTestObjMsgInner3FloatField(123.4f);
            testObjMsgInner3.setTestObjMsgInner3IntField(444);
            testObjMsgInner3.setTestObjMsgInner3LongField(9999L);
            testObjMsgInner3.setTestObjMsgInner3ShortField((short)1000);
            testObjMsgInner3List.add(testObjMsgInner3);
        }


        LinkedHashMap<String, List<TestObjMsgInner3_Java>>  testObjMsgInner3Map2 = new LinkedHashMap<String, List<TestObjMsgInner3_Java>>();
        for (int j = i ; j < i + 5 ; j++ )
        {
            testObjMsgInner3Map2.put("testObjMsgInner3Map2Key-"+j, testObjMsgInner3List);

        }


        testObjMsgInner2.setInner3MapField2(testObjMsgInner3Map2);



        long[] inner3LongAry = new long[]{1,2,3,4,5};
        testObjMsgInner2.setInner3LongAry(inner3LongAry) ;
        Long[] inner3LongObjAry = new Long[4];
        inner3LongObjAry[0] = 10L;
        inner3LongObjAry[1] = 11L;
        inner3LongObjAry[2] = 12L;
        inner3LongObjAry[3] = 13L;

        testObjMsgInner2.setInner3LongObjAry(inner3LongObjAry);

        LinkedHashMap<String, TestObjMsgInner2_Java>  testObjMsgInner2Map = new LinkedHashMap<String, TestObjMsgInner2_Java>();
        for (int j = i ; j < i + 5 ; j++ )
        {
            testObjMsgInner2Map.put("testObjMsgInner2  - "+j, testObjMsgInner2);

        }



        TestObjMsgInner1_Java testObjMsgInner1 = new TestObjMsgInner1_Java();
        testObjMsgInner1.setInnerStrField("TestObjMsgInner1's str field - "+i);
        testObjMsgInner1.setInner2ObjMap(testObjMsgInner2Map);

        TestObjMsg_Java msg = new TestObjMsg_Java();
        msg.setIntField(i);
        msg.setStrField("objQ2 - "+i);


        List<TestObjMsgInner1_Java> listField = new ArrayList<TestObjMsgInner1_Java>();
        for (int j = i ; j < i + 5 ; j++ )
        {
            listField.add(testObjMsgInner1) ;
        }

        msg.setListField(listField) ;

        return msg ;

    }
    
}
