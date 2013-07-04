import com.tibco.tibjms.TibjmsConnectionFactory

import javax.jms.ConnectionFactory
import javax.naming.Context
import javax.naming.InitialContext

import javax.jms.*

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 30/06/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 * Place ActiveMQ specific jndi.properties  in the classpath.
 */

InitialContext jndiContext = new InitialContext();

ConnectionFactory queueConnectionFactory =
    (ConnectionFactory)jndiContext.lookup("connectionFactory");

Destination queue = (Destination)jndiContext.lookup("objQ1")

Connection connection = queueConnectionFactory.createConnection();

Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

javax.jms.QueueBrowser queueBrowser = session.createBrowser(queue);

Enumeration queueDataEnum = queueBrowser.getEnumeration();

while (queueDataEnum.hasMoreElements())
{
    Object msg = queueDataEnum.nextElement()

    def eachQData =  [:]

    eachQData['JMSMessageID'] = msg.getJMSMessageID()

    eachQData['JMSRedelivered'] = msg.getJMSRedelivered()

    eachQData['JMSCorrelationID']   = msg.getJMSCorrelationID()

    println(eachQData)

}

queueBrowser.close()
connection.close()
