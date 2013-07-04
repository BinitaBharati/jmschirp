import bbharati.jmschirp.util.AppLogger
import com.tibco.tibjms.TibjmsConnectionFactory
import com.tibco.tibjms.naming.TibjmsFederatedQueue

import javax.jms.Connection
import javax.jms.ConnectionFactory
import javax.jms.JMSException
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


Hashtable env = new Hashtable();
env.put(Context.INITIAL_CONTEXT_FACTORY,
        "com.tibco.tibjms.naming.TibjmsInitialContextFactory");
env.put(Context.PROVIDER_URL,"tibjmsnaming://10.76.85.211:7222");
env.put(Context.SECURITY_PRINCIPAL, "admin");
env.put(Context.SECURITY_CREDENTIALS, "");

InitialContext jndiContext = new InitialContext(env);

ConnectionFactory queueConnectionFactory =
    (TibjmsConnectionFactory)jndiContext.lookup("FTQueueConnectionFactory");

TibjmsFederatedQueue queue = (TibjmsFederatedQueue)jndiContext.lookup("txtQ1");

sendMessages(queueConnectionFactory, queue);
//browseMessages(queueConnectionFactory, queue);

def sendMessages(queueConnectionFactory, queue)
{
    Connection connection = queueConnectionFactory.createConnection();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    MessageProducer msgProducer = session.createProducer(queue);

    for (int i = 1; i < 100 ; i++)
    {
        StringBuffer strMsg = new StringBuffer("txtQ1")

        Message msg = session.createTextMessage("txtQ1 - strrrrr msg - "+i);
        msgProducer.send(msg);

    }

}

//msg is TextMessage={ Header={ JMSMessageID={ID:EMS-SERVER.57BE5157F1D4F:8} JMSDestination={Queue[jmschirp]} JMSReplyTo={null} JMSDeliveryMode={PERSISTENT} JMSRedelivered={false} JMSCorrelationID={null} JMSType={null} JMSTimestamp={Sun Mar 31 15:35:34 GMT+05:30 2013} JMSExpiration={0} JMSPriority={4} } Properties={ } Text={Hello , I am msg no - 6} }

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

        if (msg instanceof TextMessage)
        {
            TextMessage txtMsg = (TextMessage)msg;
            AppLogger.info("textMsg is "+txtMsg.getText());

        }


    }

    AppLogger.info("browseMessages: ended printing queue content");


}