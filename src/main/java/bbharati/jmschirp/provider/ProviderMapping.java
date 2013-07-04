package bbharati.jmschirp.provider;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created with IntelliJ IDEA.
 * User: bbharati
 * Date: 28/06/13
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProviderMapping {

    public static LinkedHashMap<String, String> providerMapping = new LinkedHashMap<String, String>();

    static
    {
        providerMapping.put("EMS","bbharati.jmschirp.provider.ems.TibcoEMSProviderImpl");
        providerMapping.put("ActiveMQ","bbharati.jmschirp.provider.activemq.ActiveMQProviderImpl");
    }

}
