import bbharati.jmschirp.test.JmsObjectFactory_Java
import bbharati.jmschirp.test.JmsObjectFactory_Java
import bbharati.jmschirp.test.TestObjMsgInner1_Java
import bbharati.jmschirp.test.TestObjMsgInner2_Java
import bbharati.jmschirp.test.TestObjMsgInner3_Java
import bbharati.jmschirp.util.AppLogger
import bbharati.jmschirp.test.TestObjMsg_Java
import com.tibco.tibjms.TibjmsConnectionFactory
import com.tibco.tibjms.naming.TibjmsFederatedQueue
import groovy.json.JsonBuilder

import javax.jms.Connection
import javax.jms.ConnectionFactory
import javax.jms.JMSException
import javax.jms.ObjectMessage
import javax.jms.QueueBrowser
import javax.jms.Session;
import javax.jms.Queue
import javax.jms.TextMessage;
import javax.jms.MessageProducer;
import javax.jms.MessageConsumer;
import javax.jms.Message
import javax.naming.Context
import javax.naming.InitialContext;

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 29/03/13
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */



TibjmsConnectionFactory queueConnectionFactory  = new TibjmsConnectionFactory();
queueConnectionFactory.setServerUrl("tcp://10.76.85.194:7222");
queueConnectionFactory.setUserName("admin");
queueConnectionFactory.setUserPassword("");

Connection conn = queueConnectionFactory.createConnection()
Session session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE)

Queue queue = null



//sendMessages(queueConnectionFactory, queue);
browseMessages(queueConnectionFactory, queue);

def sendMessages(queueConnectionFactory, queue)
{

    Connection connection = queueConnectionFactory.createConnection();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    MessageProducer msgProducer = session.createProducer(queue);

    for (int i = 1; i < 15 ; i++)
    {
        def msg = JmsObjectFactory_Java.generateObject(i)

        Message jmsMsg = session.createObjectMessage(msg);
        msgProducer.send(jmsMsg);

    }



}

//msg is ObjectMessage={ Header={ JMSMessageID={ID:EMS-SERVER.57BE5157F1D464:12} JMSDestination={Queue[jmschirp]} JMSReplyTo={null} JMSDeliveryMode={PERSISTENT} JMSRedelivered={false} JMSCorrelationID={null} JMSType={null} JMSTimestamp={Mon Apr 01 22:59:47 GMT+05:30 2013} JMSExpiration={0} JMSPriority={4} } Properties={ } Object={bbharati.binita.jmschirp.test.ObjectMsg@55e29b99} }
def browseMessages(queueConnectionFactory, queue) throws Exception
{
    Connection connection = queueConnectionFactory.createConnection();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    QueueBrowser queueBrowser = session.createBrowser(queue);

    Enumeration queueDataEnum = queueBrowser.getEnumeration();

    AppLogger.info("browseMessages: starting to print queue content");


    while (queueDataEnum.hasMoreElements())
    {
        Object msg = queueDataEnum.nextElement();

        AppLogger.info("msg is "+msg);

        if (msg instanceof ObjectMessage)
        {
            ObjectMessage objMsg = (ObjectMessage)msg;
            //Get the Class of the Object contained inside objMsg
            def objMsgClass =  objMsg.getObject().getClass();
            AppLogger.info("objMsgClass is "+objMsgClass);

            //objMsgClass.fields.each {println("each field is "+it.name+" : "+it.type)}     --> This call doesnt work :(

            /* Check if toString has been defined for the class. If yes, use the toString() method , to populate
            the msgData. If no, then use reflection to look up values for class members.
            */
            def msgData = [:];
            //def toStringFound = false;
            //objMsgClass.metaClass.methods.each {  //Lists all the methods , including super classe's (Object) toString() method
            //objMsgClass.declaredMethods.each {
            /* This also lists   all the methods , including super classe's (Object) toString() method.
           But, in this , the super's toString() shows up like this : super$1$toString. ie easy to differentiate whether method added in super
           or over-ridden in child
           */
            /*eachMethod -> println "each method is ${eachMethod.name} "
                if (eachMethod.name == 'toString')
                {
                    AppLogger.info("Found a toString method :) ")
                    toStringFound = true;

                    //use toString

                    msgData[msgData] = objMsg.getObject()."${eachMethod.name}"()
                }

        }   */

            def innerDataMap = [:]

            AppLogger.info("toString method not found :( , have to figure out myself ")

            objMsgClass.metaClass.properties.each {  //This method also returns  class = class java.lang.Class property. Ignore it
            p -> println "each field is ${p.name} = ${p.type}"
                if (p.name == 'class')
                {       return
                }
                def partGetFieldName = p.name.capitalize()
                innerDataMap["${partGetFieldName}"] = objMsg.getObject()."get${partGetFieldName}"()
                println("after putting ${partGetFieldName} , innerDataMap is ${innerDataMap}")


            }

            msgData["msgData"]   = innerDataMap


            println(msgData)

            def json = new JsonBuilder(msgData)

            println(json.toPrettyString())

        }


    }

    AppLogger.info("browseMessages: ended printing queue content");


}