
/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 27/02/13
 * Time: 5:48 PM
 * To change this template use File | Settings | File Templates.
 */


import bbharati.jmschirp.dynatree.node.model.DynaTreeNode
import bbharati.jmschirp.dynatree.node.model.DynaTreeNode
import bbharati.jmschirp.dynatree.node.model.DynaTreeNode
import groovy.json.JsonBuilder;

import com.tibco.tibjms.admin.QueueInfo
import com.tibco.tibjms.admin.TibjmsAdmin
import bbharati.jmschirp.util.AppLogger;


def jmsVendorType = request.getParameter('jmsVendorType');

def jmsConnectionUrl =   request.getParameter('jmsProviderUrl');

def jmsConnectionUser =   request.getParameter('jmsUserName');

def jmsConnectionPasswd = request.getParameter('jmsPasswd');

def jmsConnectionName = request.getParameter('jmsConnectionName');

/*def jmsVendorType = "EMS";

def jmsConnectionUrl =   "tcp://10.76.85.194:7222";

def jmsConnectionUser =   "admin";

def jmsConnectionPasswd = "";  */

AppLogger.info('listQueues : jmsVendorType =*'+jmsVendorType+'*, jmsConnectionUrl = '+jmsConnectionUrl+', jmsConnectionUser = '+
        jmsConnectionUser + ', jmsConnectionPasswd = *'+(jmsConnectionPasswd == '""') + '*s');

AppLogger.info('jmsConnectionPasswd '+jmsConnectionPasswd);

if (jmsVendorType == 'EMS')
{

    if (jmsConnectionPasswd == '""')
    {
        AppLogger.info(' jmsConnectionPasswd is null value');
        jmsConnectionPasswd = null;
    }
    listEMSQueues(jmsConnectionUrl,jmsConnectionUser,jmsConnectionPasswd,jmsConnectionName);

}



//listEMSQueues('tcp://10.76.85.194:7222','admin',null);
def listEMSQueues(jmsConnectionUrl,jmsConnectionUser,jmsConnectionPasswd,jmsConnectionName )
{
    //TibjmsConnectionFactory tibjmsConnectionFactory  = new TibjmsConnectionFactory();
    // tibjmsConnectionFactory.setServerUrl("tcp://10.76.85.194:7222");
    //Connection connection = tibjmsConnectionFactory.createConnection();

    def  jmsQueueList = [];

    TibjmsAdmin emsAdmin = new TibjmsAdmin(jmsConnectionUrl,jmsConnectionUser,jmsConnectionPasswd);
    QueueInfo[] queueInfo = emsAdmin.getQueues();

    for (i = 0 ; i < queueInfo.length ; i++)
    {
        def eachQ = queueInfo[i];
        //println("listEMSQueues: eachQ name is "+eachQ.getName());
        def dynaTreeNode = new DynaTreeNode()
        dynaTreeNode.setTitle(eachQ.getName())
        dynaTreeNode.setKey("${jmsConnectionName}-${eachQ.getName()}")
        dynaTreeNode.setNodeType(3)

        jmsQueueList.add(dynaTreeNode);
    }


    def json = new JsonBuilder(jmsQueueList);

    println(json.toPrettyString());
}




