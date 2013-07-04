import org.apache.activemq.ActiveMQConnectionFactory

import javax.jms.*
/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 30/06/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */

ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616")

// Create a Connection
Connection connection = connectionFactory.createConnection();
//connection.start();

// Create a Session
Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

Queue replyTo = session.createTemporaryQueue();
MessageConsumer consumer = session.createConsumer(replyTo);

Queue testQueue = session.createQueue("objQ2");
MessageProducer producer = session.createProducer(null);

String queueName = "ActiveMQ.Statistics.Destination." + testQueue.getQueueName()
Queue query = session.createQueue(queueName);

Message msg = session.createMessage();

producer.send(testQueue, msg)
msg.setJMSReplyTo(replyTo);
producer.send(query, msg);
MapMessage reply = (MapMessage) consumer.receive();


for (Enumeration e = reply.getMapNames();e.hasMoreElements();) {
    String name = e.nextElement().toString();
    System.out.println(name + "=" + reply.getObject(name));
}

session.close();
connection.close();
