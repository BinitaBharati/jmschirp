import bbharati.jmschirp.provider.ProviderInterface
import bbharati.jmschirp.provider.ProviderMapping
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
import javax.jms.DeliveryMode
import javax.jms.Destination
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

import org.apache.activemq.ActiveMQConnectionFactory



/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 29/03/13
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 * This script uses Java class objects as message.
 */

def connectionName;
def host = '10.76.85.211';
def port = 7222;
def adminUser = 'admin';
def adminPasswd = "";
def vendorType= 'EMS';
def vendorVersion;

//EMS specific
def jndiName = 'FTQueueConnectionFactory';
def jndiUser = 'admin';
def jndiPasswd = "";

//ActiveMQ specific
def jmxPort = "";
def jmxUser = "";
def jmxPassword = "";

def vendorQueueName

sendMsg(vendorType, host, port, jndiUser, jndiPasswd, vendorQueueName)

def sendMsg(vendorType, vendorUrl, vendorUsr, vendorPaswd, vendorQueueName)
{

    if (vendorType == 'ems')//Tibco
    {
        /*
       vendorType = ems
       vendorUrl = tibjmsnaming://10.76.85.194:7222
       vendorUsr = admin
       vendorPaswd =
       vendorQueueName =  jmschirpObjMsgQ1
       jndiName =   FTQueueConnectionFactory
       */

        Class providerImplClass = Class.forName(ProviderMapping.providerMapping.get(jmsProviderDetails.vendorType))
        ProviderInterface provider = providerImplClass.newInstance()
        provider.setProviderInfo(jmsProviderDetails)

        ConnectionFactory queueConnectionFactory = provider.getConnectionFactory()
        Queue  queue = provider.getQueue(queueName)


        Connection connection = queueConnectionFactory.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer msgProducer = session.createProducer(queue);

        for (int i = 1; i < 3000 ; i++)
        {
            def msg = JmsObjectFactory_Java.generateObject(i)

            Message jmsMsg = session.createObjectMessage(msg);
            msgProducer.send(jmsMsg);

        }


    }
    else if (vendorType == 'activemq')
    {
        /*
       vendorType = activemq
       vendorUrl = tcp://localhost:61616
       vendorUsr = ?
       vendorPaswd = ?
       vendorQueueName =  activeMQObjMsgQueue
       In ActiveMQ you do not have to create destinations up front before you can use them.
       The ActiveMQ broker auto-creates the physical resources associated with a destination on demand (i.e. when messages are sent to a new destination on a broker).
       */

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(vendorUrl)

        // Create a Connection
        Connection connection = connectionFactory.createConnection("admin","pass");
        //connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue(vendorQueueName);

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer msgProducer = session.createProducer(destination);
        msgProducer.setDeliveryMode(DeliveryMode.PERSISTENT)

        for (int i = 1; i < 10000 ; i++)
        {
            def msg = JmsObjectFactory_Java.generateObject(i)

            Message jmsMsg = session.createObjectMessage(msg);
            msgProducer.send(jmsMsg);

        }


    }
}
