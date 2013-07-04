import bbharati.jmschirp.test.JmsObjectFactory_Java
import bbharati.jmschirp.test.TestObjMsg
import bbharati.jmschirp.test.TestStreamMsgObj
import bbharati.jmschirp.util.AppLogger
import com.tibco.tibjms.TibjmsConnectionFactory
import com.tibco.tibjms.naming.TibjmsFederatedQueue
import groovy.json.JsonBuilder

import javax.jms.Connection
import javax.jms.ConnectionFactory
import javax.jms.QueueBrowser
import javax.jms.Session;
import javax.jms.Queue
import javax.jms.TextMessage;
import javax.jms.MessageProducer;
import javax.jms.MessageConsumer;
import javax.jms.Message
import javax.jms.StreamMessage;
import javax.naming.Context
import javax.naming.InitialContext;

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 29/03/13
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */

Hashtable env = new Hashtable();
env.put(Context.INITIAL_CONTEXT_FACTORY,
        "com.tibco.tibjms.naming.TibjmsInitialContextFactory");
env.put(Context.PROVIDER_URL,"tibjmsnaming://10.76.85.211:7222");
env.put(Context.SECURITY_PRINCIPAL, "admin");
env.put(Context.SECURITY_CREDENTIALS, "");

InitialContext jndiContext = new InitialContext(env);

ConnectionFactory queueConnectionFactory =
    (TibjmsConnectionFactory)jndiContext.lookup("FTQueueConnectionFactory");

TibjmsFederatedQueue queue = (TibjmsFederatedQueue)jndiContext.lookup("strmQ1");

sendMessages(queueConnectionFactory, queue);
//browseMessages(queueConnectionFactory, queue);

def sendMessages(queueConnectionFactory, queue)
{

    Connection connection = queueConnectionFactory.createConnection();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    MessageProducer msgProducer = session.createProducer(queue);

    for (int i = 1; i < 100 ; i++)
    {

        StreamMessage streamMsg = session.createStreamMessage();
        streamMsg.writeBoolean(true);
        byte b = 1;
        streamMsg.writeByte(b);

        char c = 'A';
        streamMsg.writeChar(c);

        streamMsg.writeDouble(123.456);

        streamMsg.writeFloat(123f);

        streamMsg.writeInt(1234);


        streamMsg.writeShort((short)1)

        streamMsg.writeLong(1234)

        streamMsg.writeString("strmQ1")

        byte[] byteAry = new byte[2]
        byteAry[0]=Byte.parseByte("1")
        byteAry[1]=Byte.parseByte("2")

        streamMsg.writeBytes(byteAry)



        //write object - end  , object means out of box objects like integer , date etc
        /*def testStreamMsgObj = new TestStreamMsgObj()
        testStreamMsgObj.setProp1("A")
        testStreamMsgObj.setProp2("B")
        testStreamMsgObj.setProp3("C") */
        //streamMsg.writeObject(new TestObjMsg());

        msgProducer.send(streamMsg);



    }



}

//AppLogger - msg is StreamMessage={ Header={ JMSMessageID={ID:EMS-SERVER.57BE5157F1D471:12} JMSDestination={Queue[jmschirp]} JMSReplyTo={null} JMSDeliveryMode={PERSISTENT} JMSRedelivered={false} JMSCorrelationID={null} JMSType={null} JMSTimestamp={Mon Apr 01 23:30:37 GMT+05:30 2013} JMSExpiration={0} JMSPriority={4} } Properties={ } Fields={ {Boolean:true} {Byte:1} {Character:A} {Double:123.456} {Float:123.0} {Integer:1234} } }
def browseMessages(queueConnectionFactory, queue) throws Exception
{
    Connection connection = queueConnectionFactory.createConnection();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    QueueBrowser queueBrowser = session.createBrowser(queue);

    Enumeration queueDataEnum = queueBrowser.getEnumeration();

    AppLogger.info("browseMessages: starting to print queue content");

    def msgData = [:]
    def eachMsgList = []
    while (queueDataEnum.hasMoreElements())
    {
        Object msg = queueDataEnum.nextElement();

        //AppLogger.info("msg is "+msg);

        def msg1 = msg.toString()

        if (msg instanceof StreamMessage)
        {
            StreamMessage streamMsg = (StreamMessage)msg;

            println(streamMsg)

            def eachMsg = msg1.substring(msg1.indexOf("Fields=")+"Fields=".length(),msg1.lastIndexOf("}"))
            println("msgData is "+msgData);
            eachMsgList.add(eachMsg)

        }

        msgData["msgData"] = eachMsgList

        def json = new JsonBuilder(msgData)
        println(json.toPrettyString())
    }

    AppLogger.info("browseMessages: ended printing queue content");


}