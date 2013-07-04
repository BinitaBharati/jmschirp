package bbharati.jmschirp.test.activemq;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

import java.util.Enumeration;

import static org.junit.Assert.*;

public class QueueBrowsingTest1 {

    private static final Logger LOG = LoggerFactory.getLogger(QueueBrowsingTest1.class);

    private ActiveMQConnectionFactory factory;

    @Before
    public void startBroker() throws Exception {
        factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    @Test
    public void testBrowsing() throws JMSException {

        int messageToSend = 2000;

        ActiveMQQueue queue = new ActiveMQQueue("hello4");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(queue);

        String data = "";
        for( int i=0; i < 1024*2; i++ ) {
            data += "x";
        }


        for( int i=0; i < messageToSend; i++ ) {
            producer.send(session.createTextMessage(data));
        }

        QueueBrowser browser = session.createBrowser(queue);
        Enumeration enumeration = browser.getEnumeration();
        int received = 0;
        while (enumeration.hasMoreElements()) {
            Message m = (Message) enumeration.nextElement();
            received++;
        }

        browser.close();
        System.out.println(received);
        assertEquals(messageToSend, received);
    }

}
