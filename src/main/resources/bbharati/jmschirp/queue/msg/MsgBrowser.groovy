package bbharati.jmschirp.queue.msg

import bbharati.jmschirp.provider.ProviderInterface
import bbharati.jmschirp.provider.ProviderMapping
import bbharati.jmschirp.util.AppLogger
import bbharati.jmschirp.util.ConnectionUtil
import bbharati.jmschirp.util.MsgParsingUtil
import groovy.json.JsonBuilder

import javax.jms.*
import javax.naming.Context
import javax.naming.InitialContext

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 23/05/13
 * Time: 12:21 PM
 **/
class MsgBrowser {

    def jmsProviderDetails
    def queueName
    def jmsMsgId
    def usrSession

    def init(def jmsConnectionName, def queueName1, def jmsMsgId1, def usrSession1)
    {
        jmsProviderDetails = ConnectionUtil.getJmsProviderInfo(jmsConnectionName)
        queueName = queueName1
        usrSession = usrSession1
        jmsMsgId = jmsMsgId1

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
        AppLogger.info('inspectMsg: jmsMsgId = '+jmsMsgId)
        while (queueDataEnum.hasMoreElements())
        {
            Object msg = queueDataEnum.nextElement()

            def qMsgId =  msg.getJMSMessageID()
            AppLogger.info('inspectMsg: qMsgId = '+qMsgId)
            if (qMsgId == jmsMsgId)
            {
                AppLogger.info('inspectMsg: yeaa :) found the qMsgId = '+qMsgId)

                if (msg instanceof ObjectMessage)
                {

                    ObjectMessage objMsg = (ObjectMessage)msg;

                    def dynaTreeList = MsgParsingUtil.parseCustomObject(objMsg.getObject())

                    //def dynaTreeJsonStr = new JsonBuilder(dynaTreeList).toPrettyString();
                    AppLogger.info('inspectMsg: exiting with dynaTreeList = '+dynaTreeList)

                    connection.close();
                    session.close();
                    queueBrowser.close();

                    retMap['responseType']  = 'tree'
                    retMap['value']  = dynaTreeList

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

        }

        //AppLogger.info('inspectMsg: exiting with retMap = '+retMap)
        return retMap

    }

}