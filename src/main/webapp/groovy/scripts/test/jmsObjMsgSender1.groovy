package test

import bbharati.jmschirp.provider.ProviderInfo
import bbharati.jmschirp.provider.ProviderInterface
import bbharati.jmschirp.provider.ProviderMapping
import bbharati.jmschirp.test.JmsObjectFactory_Java
import bbharati.jmschirp.test.Test1Obj
import com.cisco.ros.ng.common.alert.model.Alert

import javax.jms.Connection
import javax.jms.ConnectionFactory
import javax.jms.Session;
import javax.jms.Queue
import javax.jms.MessageProducer;
import javax.jms.Message


/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 29/03/13
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 * This script uses Java class objects as message.
 */

def connectionName;
def host = '10.76.85.211';
def port = 7222;
def adminUser = 'admin';
def adminPasswd = "";
def vendorType= 'EMS';
def vendorVersion;

//EMS specific
def jndiName = 'FTQueueConnectionFactory';
def jndiUser = 'admin';
def jndiPasswd = "";

//ActiveMQ specific
def jmxPort = "";
def jmxUser = "";
def jmxPassword = "";

def vendorQueueName = 'emsObjQ6'

def providerInfo = new ProviderInfo()
providerInfo.vendorType = vendorType
providerInfo.vendorVersion = vendorVersion
providerInfo.host = host
providerInfo.port = port
providerInfo.adminUser = adminUser
providerInfo.adminPasswd = adminPasswd
providerInfo.jndiName = jndiName
providerInfo.jndiUser = jndiUser
providerInfo.jndiPasswd = jndiPasswd
providerInfo.jmxPort = jmxPort
providerInfo.jmxUser = jmxUser
providerInfo.jmxPassword = jmxPassword

sendMsg(providerInfo, vendorQueueName)

def sendMsg(providerInfo, vendorQueueName)
{
    /*
   vendorType = EMS
   host = 10.76.85.211
   adminUser = admin
   adminPasswd =
   jndiName =   FTQueueConnectionFactory
   vendorQueueName =  jmschirpObjMsgQ1
   */


    Class providerImplClass = Class.forName(ProviderMapping.providerMapping.get(providerInfo.vendorType))
    ProviderInterface provider = providerImplClass.newInstance()
    provider.setProviderInfo(providerInfo)

    ConnectionFactory queueConnectionFactory = provider.getConnectionFactory()
    Queue  queue = provider.getQueue(vendorQueueName)


    Connection connection = queueConnectionFactory.createConnection();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    MessageProducer msgProducer = session.createProducer(queue);

    for (int i = 1; i < 100 ; i++)
    {
        //def msg = JmsObjectFactory_Java.generateObject(i, vendorQueueName) ;

        Alert alert = null
        try
        {
             alert = new Alert();
        }
        catch(java.io.FileNotFoundException ex)
        {
            //swallow
            println('here')
        }
        if (alert != null)
        {
            alert.setAlarmCode("ALRM_CODE -"+i)  ;
            alert.setAttribute("ATTR - "+i);
            alert.setAlarmComponent("ALRM CMP - "+i)  ;
            alert.setAlarmIdentifier("ALRM ID - "+i);
            alert.setAlertClassification("ALRT CLASS - "+i);
            alert.setAlertText("ALERT TXT - "+i);
            alert.setAlternateAlertText("ALT ALERT TXT - "+i);
            alert.setCommonInfo("COMMON INFO - "+i);
            alert.setCompanyId("CPYXXXXXX");
            alert.setComponentId("REXXXXXXXXXX-"+i) ;
            long date = new Date().getTime();
            alert.setCorrelationTime((int)date);
            alert.setFirstOccurrence(new Date());
            alert.setLastOccurrence(new Date());
            alert.setDeviceId("DEVIDXXXXXX-"+i) ;
            Alert.HealthImpacting hlthImpct =  Alert.HealthImpacting.NO ;
            alert.setIsHealthImpacting(hlthImpct);
            alert.setCriticality(Alert.Criticality.HIGH) ;
            alert.setEmsType(1);
            alert.setDeviceName("DEV NAME - "+i);
            alert.setIsProblem(1);
            alert.setServiceProduct(Alert.ServiceProduct.AVAILABILITY) ;
            alert.setSeverity(Alert.Severity.CRITICAL) ;
        }




        Message jmsMsg = session.createObjectMessage(alert);
        msgProducer.send(jmsMsg);

    }


}