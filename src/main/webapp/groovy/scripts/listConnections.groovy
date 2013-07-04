import groovy.json.JsonBuilder;
import bbharati.jmschirp.provider.ProviderInfo
import bbharati.jmschirp.util.AppLogger;

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 25/02/13
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */


def USRHOME = System.getProperty("user.home");
AppLogger.info('USER HOME DIR '+USRHOME);

def file = new File(USRHOME+'/.jms-chirp/.jmsVendorInfo').text;

def fileItems = file.readLines();

def  jmsConnectionInfoList = [];

def jmsConnectionName;

for (i=0; i<fileItems.size();i++)
{

    def eachLine =  fileItems.get(i);
    LinkedHashMap evaluatedMap = Eval.me(eachLine) ;

    def jmsVendorDetails = new ProviderInfo(['connectionName':evaluatedMap.get('name')]);

    jmsConnectionInfoList.add(jmsVendorDetails) ;

}

def json = new JsonBuilder(jmsConnectionInfoList);

println(json.toPrettyString());

