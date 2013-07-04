package bbharati.jmschirp.provider.activemq;

import bbharati.jmschirp.provider.ProviderInfo;
import bbharati.jmschirp.provider.ProviderInterface;
import bbharati.jmschirp.util.AppLogger;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Session;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 28/06/13
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 *
 */
public class ActiveMQProviderImpl implements ProviderInterface {
    public volatile ProviderInfo info = null;
    private static MBeanServerConnection serverConnection;
    private static JMXConnector jmxConnector;
    private  ActiveMQConnectionFactory connectionFactory = null;
    private Connection connection = null;

    public ActiveMQProviderImpl(ProviderInfo info1) throws Exception
    {
        this.info = info1;

    }
    @Override
    public void setProviderInfo(ProviderInfo info) {
        this.info = info;
    }

    @Override
    public ConnectionFactory getConnectionFactory() throws Exception {
        connectionFactory = new ActiveMQConnectionFactory("tcp://"+info.getHost()+":"+info.getPort());
        return connectionFactory;
    }

    /* JMX can be used to get queue statistics.
    * */
    @Override
    public List<LinkedHashMap<String, String>> getQueueDetails() throws Exception {
        List<LinkedHashMap<String,String>> jmsQueueList = new ArrayList<LinkedHashMap<String,String>>();

        if (info.getJmxPort() != null)  //Use JMX to retrieve queue statistics
        {
            JMXConnector connector = null;
            if(info.getJmxUser() != null)
            {
                Map<String, String[]> env = new HashMap<String, String[]>();
                String[] credentials = {info.getJmxUser(), info.getJmxPassword()};
                env.put(JMXConnector.CREDENTIALS, credentials);
                connector = JMXConnectorFactory.connect(new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"+info.getHost()+":"+info.getJmxPort()+"/jmxrmi"), env);

            }
            else
            {
                connector = JMXConnectorFactory.connect(new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"+info.getHost()+":"+info.getJmxPort()+"/jmxrmi"));
            }
            connector.connect();
            MBeanServerConnection connection = connector.getMBeanServerConnection();

            ObjectName mbeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName="+info.getHost());
            BrokerViewMBean mbean = MBeanServerInvocationHandler.newProxyInstance(connection, mbeanName, BrokerViewMBean.class, true);

            ObjectName[] qObjAry = mbean.getQueues();

            for (ObjectName eachQObjectName : qObjAry)
            {
                //eachQObjectName = org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName=objQ3
                String objCanonicalName =  eachQObjectName.getCanonicalName();
                String queueName = objCanonicalName.substring(objCanonicalName.indexOf("destinationName=") + "destinationName=".length(), objCanonicalName.indexOf(",", objCanonicalName.indexOf("destinationName=")));

                long queueSize = (Long)connection.getAttribute(eachQObjectName, "QueueSize");
                long MemoryUsageByteCount = (Long)connection.getAttribute(eachQObjectName, "MemoryUsageByteCount");
                AppLogger.info("eachQObjectName = "+eachQObjectName+", queueSize = "+queueSize+", MemoryUsageByteCount = "+MemoryUsageByteCount);
                LinkedHashMap<String, String> innerMap = new LinkedHashMap<String, String>() ;
                innerMap.put("name", queueName);
                innerMap.put("pendingMessageCount", queueSize+"");
                innerMap.put("pendingMessageSize", MemoryUsageByteCount+"");
                jmsQueueList.add(innerMap);
            }
            connector.close();
        }
        return jmsQueueList;
    }

    @Override
    public Queue getQueue(String queueName) throws Exception
    {
           if(connectionFactory == null)
           {
               getConnectionFactory();
           }
           if(info.getAdminUser() != null && info.getAdminPasswd() != null)
           {
               connection = connectionFactory.createConnection(info.getAdminUser(),info.getAdminPasswd());
           }
           else
           {
               connection = connectionFactory.createConnection();
           }
           connection.start();
           Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
           Queue queue = session.createQueue(queueName);
           connection.close();
           session.close();
           return queue;

    }

    public static void main(String[] args) throws Exception {
        ProviderInfo provider = new ProviderInfo();
        provider.setHost("localhost");
        provider.setPort("61616");
        provider.setJmxPort("9999");
        provider.setJmxUser("admin");
        provider.setJmxPassword("activemq");

        ActiveMQProviderImpl  obj = new ActiveMQProviderImpl(provider)  ;
        obj.getQueueDetails();

    }

}
