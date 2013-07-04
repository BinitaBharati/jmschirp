import bbharati.jmschirp.provider.ProviderInfo
import bbharati.jmschirp.provider.ProviderInterface
import bbharati.jmschirp.provider.ProviderMapping
import bbharati.jmschirp.test.Test1Obj
import bbharati.jmschirp.util.AppLogger
import bbharati.jmschirp.test.TestObjMsg_Java
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
def vendorVersion = '4.4.1';

//EMS specific
def jndiName = 'FTQueueConnectionFactory';
def jndiUser = 'admin';
def jndiPasswd = "";

//ActiveMQ specific
def jmxPort = "";
def jmxUser = "";
def jmxPassword = "";

def vendorQueueName = 'objQ2'

def providerInfo = new ProviderInfo()
providerInfo.vendorType = vendorType
providerInfo.vendorVersion = vendorVersion
providerInfo.host = host
providerInfo.port = port
providerInfo.adminUser = adminUser
providerInfo.adminPasswd = adminPasswd
providerInfo.jndiName = jndiName
providerInfo.jndiUser = jndiUser
providerInfo.jndiPasswd = jndiPasswd
providerInfo.jmxPort = jmxPort
providerInfo.jmxUser = jmxUser
providerInfo.jmxPassword = jmxPassword


sendMsg(providerInfo, vendorQueueName)

def sendMsg(providerInfo, vendorQueueName)
{
    /*
   vendorType = ems
   vendorUrl = tibjmsnaming://10.76.85.194:7222
   vendorUsr = admin
   vendorPaswd =
   vendorQueueName =  jmschirpObjMsgQ1
   jndiName =   FTQueueConnectionFactory
   */


    Class providerImplClass = Class.forName(ProviderMapping.providerMapping.get(providerInfo.vendorType))
    ProviderInterface provider = providerImplClass.newInstance()
    provider.setProviderInfo(providerInfo)

    ConnectionFactory queueConnectionFactory = provider.getConnectionFactory()
    Queue  queue = provider.getQueue(vendorQueueName)


    Connection connection = queueConnectionFactory.createConnection();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    MessageProducer msgProducer = session.createProducer(queue);

    for (int i = 1; i < 100 ; i++)
    {
        def msg = new Test1Obj()
        msg.test = "weeee"


        Message jmsMsg = session.createObjectMessage(msg);
        msgProducer.send(jmsMsg);

    }


}