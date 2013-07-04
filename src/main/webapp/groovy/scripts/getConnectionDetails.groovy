import bbharati.jmschirp.provider.ProviderInfo
import bbharati.jmschirp.util.AppLogger
import groovy.json.JsonBuilder

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 25/06/13
 * Time: 8:53 PM
 * To change this template use File | Settings | File Templates.
 */


def USRHOME = System.getProperty("user.home");
AppLogger.info('USER HOME DIR '+USRHOME);

def file = new File(USRHOME+'/.jms-chirp/.jmsVendorInfo').text;

def fileItems = file.readLines();

def  jmsConnectionName = request.getParameter('name');

def retJsonVendorInfoStr;

for (i=0; i<fileItems.size();i++)
{
    def eachLine =  fileItems.get(i);
    LinkedHashMap evaluatedMap = Eval.me(eachLine) ;

    if (evaluatedMap.get('name') == jmsConnectionName)
    {
        AppLogger.info('getConnectionDetails: match found!')

         def jmsVendorDetails = new ProviderInfo();
         jmsVendorDetails.connectionName =  evaluatedMap.get('name')
         jmsVendorDetails.vendorType =  evaluatedMap.get('type')
         jmsVendorDetails.vendorVersion =  evaluatedMap.get('version')
         jmsVendorDetails.host =  evaluatedMap.get('host')
         jmsVendorDetails.port =  evaluatedMap.get('port')
         jmsVendorDetails.adminUser =  evaluatedMap.get('adminUser')
         jmsVendorDetails.adminPasswd =  evaluatedMap.get('adminPasswd')
         jmsVendorDetails.jndiName =  evaluatedMap.get('jndiName')
         jmsVendorDetails.jndiUser =  evaluatedMap.get('jndiUser')
         jmsVendorDetails.jndiPasswd =  evaluatedMap.get('jndiPasswd')
         jmsVendorDetails.jmxPort =  evaluatedMap.get('jmxPort')
         jmsVendorDetails.jmxUser =  evaluatedMap.get('jmxUser')
         jmsVendorDetails.jmxPassword =  evaluatedMap.get('jmxPasswd')

        retJsonVendorInfoStr = new JsonBuilder(jmsVendorDetails);

        println(retJsonVendorInfoStr)
    }

}
