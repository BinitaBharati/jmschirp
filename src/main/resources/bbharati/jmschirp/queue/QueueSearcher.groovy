package bbharati.jmschirp.queue.msg

import bbharati.jmschirp.provider.ProviderInterface
import bbharati.jmschirp.provider.ProviderMapping
import bbharati.jmschirp.util.AppLogger
import bbharati.jmschirp.util.ConnectionUtil
import bbharati.jmschirp.util.MsgParsingUtil
import bbharati.jmschirp.util.PackageNamingUtil
import bbharati.jmschirp.util.QueueSearcherUtil
import groovy.json.JsonBuilder

import javax.jms.*
import javax.naming.Context
import javax.naming.InitialContext

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 23/05/13
 * Time: 12:21 PM
 *
 *
 * Author: binita.bharati@gmail.com
 * This class contains the utility methods that returns ObjectMessage meta-data info
 * to the browser. This meta-data will be displayed in the Queue - Search dialog.

 **/
class QueueSearcher {

    def jmsProviderDetails
    def queueName

    def init(def jmsConnectionName, def queueName1)
    {
        jmsProviderDetails = ConnectionUtil.getJmsProviderInfo(jmsConnectionName)
        queueName = queueName1

    }

    def browseMsgDetails()
    {
        def queueData

        Class providerImplClass = Class.forName(ProviderMapping.providerMapping.get(jmsProviderDetails.vendorType))
        ProviderInterface provider = providerImplClass.newInstance()
        provider.setProviderInfo(jmsProviderDetails)

        ConnectionFactory queueConnectionFactory = provider.getConnectionFactory()
        Queue  queue = provider.getQueue(queueName)

        queueData = inspectMsg(queueConnectionFactory, queue)

        return queueData
    }

    def inspectMsg(ConnectionFactory queueConnectionFactory, Queue queue)
    {

        def retMap = [:]

        Enumeration queueDataEnum

        Connection connection = queueConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        javax.jms.QueueBrowser queueBrowser = session.createBrowser(queue);

        queueDataEnum = queueBrowser.getEnumeration();
        while (queueDataEnum.hasMoreElements())
        {
            Object msg = queueDataEnum.nextElement()


                if (msg instanceof ObjectMessage)
                {

                    ObjectMessage objMsg = (ObjectMessage)msg;
                    Object obj1 = objMsg.getObject()

                    QueueSearcherUtil qParsingUtil = new QueueSearcherUtil()
                    qParsingUtil.setCustomObjectTopLevelPkg(PackageNamingUtil.getTopLevelPackages(obj1.getClass().getName()))

                    def retList = qParsingUtil.parseCustomObject(obj1)

                    //def dynaTreeJsonStr = new JsonBuilder(dynaTreeList).toPrettyString();
                    AppLogger.info('inspectMsg: exiting with dynaTreeList = '+retList)

                    connection.close();
                    session.close();
                    queueBrowser.close();

                    retMap['msgType']  = 'ObjectMessage'
                    retMap['model']  = retList

                    return (new JsonBuilder(retMap).toPrettyString())

                }
                else  if (msg instanceof  TextMessage)
                {
                    TextMessage txtMsg = (TextMessage)msg

                    retMap['responseType']  = 'plain'
                    retMap['value']  = txtMsg.getText()

                    return (new JsonBuilder(retMap).toPrettyString())
                }
                else  if (msg instanceof  StreamMessage)
                {
                    StreamMessage strmMsg = (StreamMessage)msg

                    String strmMsgStr = strmMsg.toString()

                    def eachMsg = strmMsgStr.substring(strmMsgStr.indexOf("Fields=")+"Fields=".length(),strmMsgStr.lastIndexOf("}"))

                    retMap['responseType']  = 'plain'
                    retMap['value']  = eachMsg

                    return (new JsonBuilder(retMap).toPrettyString())
                }
                else  if (msg instanceof  MapMessage)
                {
                    MapMessage mapMsg = (MapMessage)msg

                    String mapMsgStr = mapMsg.toString()

                    def eachMsg = mapMsgStr.substring(mapMsgStr.indexOf("Fields=")+"Fields=".length(),mapMsgStr.lastIndexOf("}"))

                    retMap['responseType']  = 'plain'
                    retMap['value']  = eachMsg

                    return (new JsonBuilder(retMap).toPrettyString())
                }
                else if (msg instanceof  BytesMessage)
                {
                    BytesMessage byteMessage = (BytesMessage)msg

                    def byteMsgLen = byteMessage.getBodyLength()
                    byte[] byteAryToRead = new byte[byteMsgLen]
                    byteMessage.readBytes(byteAryToRead)

                    StringBuffer buf = new StringBuffer();

                    //Convert ByteMsg body into Hexa format - start
                    def HEX_DIGITS = "0123456789abcdef";

                    for (int i = 0; i != byteAryToRead.length; i++)
                    {
                        int v = byteAryToRead[i] & 0xff;

                        buf.append(HEX_DIGITS.charAt(v >> 4));
                        buf.append(HEX_DIGITS.charAt(v & 0xf));

                        buf.append(" ");
                        //Convert ByteMsg body into Hexa format - end
                    }
                    retMap['responseType']  = 'plain'
                    retMap['value']  = buf.toString()
                    return (new JsonBuilder(retMap).toPrettyString())

                }

                break

        }
        return retMap

    }

}