import bbharati.jmschirp.util.AppLogger
import org.apache.activemq.ActiveMQConnection
import org.apache.activemq.ActiveMQConnectionFactory
import org.apache.activemq.ActiveMQSession
import org.apache.activemq.command.ActiveMQMessage
import org.apache.activemq.command.ActiveMQQueue

import javax.jms.*

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 29/06/13
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
def browseWay1()
{
    //ConnectionFactory out = new ActiveMQConnectionFactory("tcp://localhost:61616?jms.prefetchPolicy.all=10000");
    ConnectionFactory out = new ActiveMQConnectionFactory("tcp://localhost:61616");
    ActiveMQConnection connection = (ActiveMQConnection) out.createConnection();

    connection.start();
    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    Set<ActiveMQQueue> amqs = connection.getDestinationSource().getQueues();
    Iterator<ActiveMQQueue> queues = amqs.iterator();

    int numMsgs = 0;

    while ( queues.hasNext() )
    {
        ActiveMQQueue queue_t = queues.next();
        String q_name = queue_t.getPhysicalName();
        List<ActiveMQMessage> msgList = ((ActiveMQSession) session).getUnconsumedMessages();

        //System.out.println( "\nQueue = " + q_name);
         if (q_name == 'objQ2')
         {
             QueueBrowser queueBrowser = session.createBrowser(queue_t);
             Enumeration e = queueBrowser.getEnumeration();


             while(e.hasMoreElements())
             {
                 Message message = (Message) e.nextElement();
                 numMsgs++;
             }

             queueBrowser.close();
         }

    }
    System.out.println("No of messages = " + numMsgs);
    session.close();
    connection.close();
}
/* http://grokbase.com/t/activemq/users/131s0qeff4/queuebrowser-not-picking-all-the-messages
So, need to repeat the procedure in a loop , till  msges recieved = total no of msges in the queue.
*/

def browseWay2()
{
    System.out.println('entering at = '+new Date()) ;
    int count = 0 ;
    //for (int i = 0 ; i < 22 ; i++)
    //{
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616?jms.prefetchPolicy.all=10000")

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("objQ2");

        javax.jms.QueueBrowser queueBrowser = session.createBrowser(destination);


        Enumeration queueDataEnum = queueBrowser.getEnumeration();

        while (queueDataEnum.hasMoreElements())
        {
            Object msg = queueDataEnum.nextElement()

            def eachQData =  [:]

            eachQData['JMSMessageID'] = msg.getJMSMessageID()

            eachQData['JMSRedelivered'] = msg.getJMSRedelivered()

            eachQData['JMSCorrelationID']   = msg.getJMSCorrelationID()

            System.out.println("eachQData = " + eachQData);

            count++;

        }
        queueBrowser.close();
        session.close();
        connection.close();

    //}

    System.out.println("count = " + count);
    System.out.println('exiting at = '+new Date()) ;
}

 browseWay2();

