package bbharati.jmschirp.provider;

import javax.jms.*;
import javax.jms.Queue;
import javax.naming.NamingException;

import bbharati.jmschirp.provider.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 27/06/13
 * Time: 10:09 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ProviderInterface {
    public final String[] implClassNames = {};
    public void setProviderInfo(ProviderInfo info);

    public ConnectionFactory  getConnectionFactory() throws Exception;

    public List<LinkedHashMap<String,String>> getQueueDetails() throws Exception;

    public Queue getQueue(String queueName) throws Exception;

}
