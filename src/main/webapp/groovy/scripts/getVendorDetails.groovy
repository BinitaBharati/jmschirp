import bbharati.jmschirp.provider.ProviderInfo
import groovy.json.JsonBuilder;
import bbharati.jmschirp.util.AppLogger;

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 25/02/13
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */

def CWD = System.getProperty("user.home");
AppLogger.info("getVendorDetails: CWD = "+CWD);

//def closure =  {file -> println file.getName();}
def supportedVendorDetailsFile = new File(CWD+'/.jms-chirp/.supportedJmsVendorInfo').text;

ArrayList evaluatedContentList =  Eval.me(supportedVendorDetailsFile);
def allJmsVendorInfo = [];

evaluatedContentList.collect {
    each ->
    if (each instanceof Map)
    {
        Map map = (Map)each;
        def jmsProviderDetails = new ProviderInfo();
        jmsProviderDetails.vendorType = map.get("type");
        jmsProviderDetails.vendorVersion = map.get("version");
        allJmsVendorInfo.add(jmsProviderDetails);

    }
}

//def jmsVendorMap = [vendorDetails: allJmsVendorInfo];

//print(jmsVendorMap);

def json = new JsonBuilder(allJmsVendorInfo);
println(json.toPrettyString())  ;



