package bbharati.jmschirp.provider.ems;


import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import bbharati.jmschirp.provider.ProviderInfo;
import bbharati.jmschirp.provider.ProviderInterface;
import com.tibco.tibjms.TibjmsConnectionFactory;
import com.tibco.tibjms.admin.QueueInfo;
import com.tibco.tibjms.admin.TibjmsAdmin;
import com.tibco.tibjms.naming.TibjmsFederatedQueue;

/**
 * Created with IntelliJ IDEA.
 * User:  binita.bharati@gmail.com
 * Date: 27/06/13
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 * Tib EMS uses JNDI as defacto. Includes a in-built JNDI server, haven't seen egs of using it without JNDI.
 */
public class TibcoEMSProviderImpl implements ProviderInterface {

    public volatile ProviderInfo info = null;

    public TibcoEMSProviderImpl(ProviderInfo info1) throws Exception
    {
        this.info = info1;

    }

    @Override
    public void setProviderInfo(ProviderInfo info) {
        this.info = info;
    }

    @Override
    public ConnectionFactory getConnectionFactory() throws Exception{
        //Use JNDI to get ConnectionFactory and Queue reference
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.tibco.tibjms.naming.TibjmsInitialContextFactory");
        env.put(Context.PROVIDER_URL,"tibjmsnaming://"+info.getHost()+":"+info.getPort());
        env.put(Context.SECURITY_PRINCIPAL, info.getJndiUser());
        env.put(Context.SECURITY_CREDENTIALS, info.getJndiPasswd());
        InitialContext jndiContext = new InitialContext(env);

        ConnectionFactory queueConnectionFactory =
                (TibjmsConnectionFactory)jndiContext.lookup(info.getJndiName());

        return queueConnectionFactory;
    }

    @Override
    public List<LinkedHashMap<String,String>> getQueueDetails() throws Exception{
        List<LinkedHashMap<String,String>> jmsQueueList = new ArrayList<LinkedHashMap<String,String>>();

        TibjmsAdmin emsAdmin = new TibjmsAdmin("tcp://"+info.getHost()+":"+info.getPort(),info.getAdminUser(),info.getAdminPasswd());
        QueueInfo[] queueInfo = emsAdmin.getQueues();

        for (int i = 0 ; i < queueInfo.length ; i++)
        {
            QueueInfo eachQ = queueInfo[i];

            if(eachQ.getName().startsWith("$")) //System queues - ignore
            {
                continue;

            }
            long deliveredMsgCount = eachQ.getDeliveredMessageCount();
            long transitMsgCount = eachQ.getInTransitMessageCount();
            int maxRedelivery = eachQ.getMaxRedelivery();
            int receiverCount = eachQ.getReceiverCount();
            long pendingMessageCount = eachQ.getPendingMessageCount();
            long pendingMessageSize = eachQ.getPendingMessageSize();


            LinkedHashMap<String, String> innerMap = new LinkedHashMap<String, String>() ;
            innerMap.put("name", eachQ.getName());
            innerMap.put("pendingMessageCount", pendingMessageCount+"");
            innerMap.put("pendingMessageSize", pendingMessageSize+"");
            jmsQueueList.add(innerMap);
        }
        return jmsQueueList;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Queue getQueue(String queueName) throws Exception{

        //Use JNDI to get ConnectionFactory and Queue reference
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.tibco.tibjms.naming.TibjmsInitialContextFactory");
        env.put(Context.PROVIDER_URL,"tibjmsnaming://"+info.getHost()+":"+info.getPort());
        env.put(Context.SECURITY_PRINCIPAL, info.getJndiUser());
        env.put(Context.SECURITY_CREDENTIALS, info.getJndiPasswd());
        InitialContext jndiContext = new InitialContext(env);

        TibjmsFederatedQueue queue = (TibjmsFederatedQueue)jndiContext.lookup(queueName);
        return queue;
    }

}
