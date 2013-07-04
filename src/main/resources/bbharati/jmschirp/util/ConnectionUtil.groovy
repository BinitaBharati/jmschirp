package bbharati.jmschirp.util

import bbharati.jmschirp.provider.ProviderInfo

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 23/05/13
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 * Gets connection details
 */
class ConnectionUtil {

    def static ProviderInfo getJmsProviderInfo(def jmsConnectionName)
    {
            AppLogger.info('getJmsProviderInfo: entered with jmsConnectionName = '+jmsConnectionName)

            def USRHOME = System.getProperty("user.home");
            AppLogger.info('USER HOME DIR '+USRHOME);

            def file = new File(USRHOME+'/.jms-chirp/.jmsVendorInfo').text;

            def fileItems = file.readLines();

            for (def i=0; i<fileItems.size();i++)
            {
                def eachLine =  fileItems.get(i)
                LinkedHashMap evaluatedMap = Eval.me(eachLine)

                def fileConnectionName = evaluatedMap.get('name')
                AppLogger.info('listQueues: fileConnectionName = '+fileConnectionName)
                if (fileConnectionName == jmsConnectionName)
                {
                    def providerInfo = new   ProviderInfo()
                    providerInfo.connectionName = evaluatedMap.get('name')
                    providerInfo.vendorType = evaluatedMap.get('type')
                    providerInfo.vendorVersion = evaluatedMap.get('version')
                    providerInfo.host = evaluatedMap.get('host')
                    providerInfo.port = evaluatedMap.get('port')
                    providerInfo.adminUser = evaluatedMap.get('adminUser')
                    providerInfo.adminPasswd = evaluatedMap.get('adminPasswd') == '""' ?  null :  evaluatedMap.get('adminPasswd')
                    providerInfo.jndiName = evaluatedMap.get('jndiName') == '""' ?  null :  evaluatedMap.get('jndiName')
                    providerInfo.jndiUser = evaluatedMap.get('jndiUser') == '""' ?  null :  evaluatedMap.get('jndiUser')
                    providerInfo.jndiPasswd = evaluatedMap.get('jndiPasswd') == '""' ?  null :  evaluatedMap.get('jndiPasswd')
                    providerInfo.jmxPort = evaluatedMap.get('jmxPort') == '""' ?  null :  evaluatedMap.get('jmxPort')
                    providerInfo.jmxUser = evaluatedMap.get('jmxUser') == '""' ?  null :  evaluatedMap.get('jmxUser')
                    providerInfo.jmxPassword = evaluatedMap.get('jmxPasswd') == '""' ?  null :  evaluatedMap.get('jmxPasswd')

                    return providerInfo

                }
            }

        return null

    }
}
