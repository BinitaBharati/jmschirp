package bbharati.jmschirp.queue

import bbharati.jmschirp.provider.ProviderInterface
import bbharati.jmschirp.provider.ProviderMapping
import bbharati.jmschirp.util.AppLogger
import bbharati.jmschirp.util.ConnectionUtil
import bbharati.jmschirp.util.JMSCHIRPConstants
import bbharati.jmschirp.util.HouseKeepingUtil
import bbharati.jmschirp.util.QFilterUtil
import groovy.json.JsonBuilder
import org.codehaus.groovy.grails.web.json.JSONObject

import javax.jms.*

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 23/05/13
 * Time: 12:21 PM
 **/
class QueueFilter {

    def jmsProviderDetails
    def queueName
    def searchCriteria;
    def newReq
    def usrSession


    def init(def jmsConnectionName, def queueName1, def searchCriteriaJsonStr, def newReq1, def usrSession1)
    {
        jmsProviderDetails = ConnectionUtil.getJmsProviderInfo(jmsConnectionName)
        queueName = queueName1
        searchCriteria = new JSONObject(searchCriteriaJsonStr)
        newReq = newReq1
        usrSession = usrSession1

    }

    def filterQueue()
    {
        def queueData

        Class providerImplClass = Class.forName(ProviderMapping.providerMapping.get(jmsProviderDetails.vendorType))
        ProviderInterface provider = providerImplClass.newInstance()
        provider.setProviderInfo(jmsProviderDetails)

        ConnectionFactory queueConnectionFactory = provider.getConnectionFactory()
        Queue  queue = provider.getQueue(queueName)

        queueData = inspectQueue(queueConnectionFactory, queue)
        return queueData
    }

    def inspectQueue(ConnectionFactory queueConnectionFactory, Queue queue)
    {
        //AppLogger.info('inspectQueue: entered with isScrolled = '+isScrolled)
        Enumeration queueDataEnum

        //def jmsProviderSessionData = usrSession.getAttribute(jmsProviderDetails.host+':'+jmsProviderDetails.port)

        Connection connection = null;
        Session session = null;
        javax.jms.QueueBrowser queueBrowser = null;

        if (newReq == 'Y')   //fresh request
        {
            connection = queueConnectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            queueBrowser = session.createBrowser(queue);

            queueDataEnum = queueBrowser.getEnumeration();

            //Save data in session.

            /*if (HouseKeepingUtil.isNULL(jmsProviderSessionData))
            {
                AppLogger.info('inspectQueue: in check1 ')
                def jmsProviderSessionDataMap = [:]
                jmsProviderSessionDataMap[queueName]  =  queueDataEnum

                usrSession.setAttribute(jmsProviderDetails.host+':'+jmsProviderDetails.port,jmsProviderSessionDataMap)
            }
            else
            {
                AppLogger.info('inspectQueue: in check2 ')
                jmsProviderSessionData[queueName]  =  queueDataEnum

                usrSession.setAttribute(jmsProviderDetails.host+':'+jmsProviderDetails.port,jmsProviderSessionData)
            } */

        }
        else if (newReq == 'N')//Look up queueDataEnum from session - its a scroll request.
        {
            AppLogger.info('inspectQueue: in check3 ')
            //queueDataEnum =  jmsProviderSessionData.get(queueName)
        }

        AppLogger.info('inspectQueue:  queueDataEnum = '+queueDataEnum)

        def queueHouseKeepingData = [:]
        def queueData = []
        def rowCount = 0 ;
        def  lastShownJmsMsgIdFound = false
        def flagHasMoreElements =   queueDataEnum.hasMoreElements()
        println('hasMore = '+flagHasMoreElements)
        while (queueDataEnum.hasMoreElements())
        {
            Object msg = queueDataEnum.nextElement()

            def eachQData =  [:]

            eachQData['JMSMessageID'] = msg.getJMSMessageID()

            eachQData['JMSRedelivered'] = msg.getJMSRedelivered()

            eachQData['JMSCorrelationID']   = msg.getJMSCorrelationID()

            if (msg instanceof ObjectMessage)
            {
                ObjectMessage objMsg = (ObjectMessage)msg;

                eachQData['type'] = 'ObjectMessage'
                QFilterUtil filterUtil = new QFilterUtil()
                filterUtil.setFilterCriteria(searchCriteria)
                filterUtil.parseMainObject(objMsg.getObject())

            }
            else if (msg instanceof  TextMessage)
            {
                eachQData['type'] = 'TextMessage'
            }
            else if (msg instanceof  BytesMessage)
            {
                eachQData['type'] = 'BytesMessage'
            }
            else if (msg instanceof  StreamMessage)
            {
                eachQData['type'] = 'StreamMessage'
            }
            else if (msg instanceof  MapMessage)
            {
                eachQData['type'] = 'MapMessage'
            }

            queueData.add(eachQData)
            rowCount++
            if (rowCount >= JMSCHIRPConstants.renderedMaxRows)
            {
                queueHouseKeepingData['hasMore'] = 'Y'
                queueHouseKeepingData['msgData'] = queueData

                def retOp = new JsonBuilder(queueHouseKeepingData).toPrettyString();
                AppLogger.info('inspectQueue: max rowCount reached.Exiting with retOp = '+retOp)
                /* No need to check if session already contains the attribute, Even, if it contains,
                * it will get overridden. */

                usrSession.setAttribute("queueDataEnum",queueDataEnum)

                //usrSession.setAttribute("lastShownJmsMsgId", msg.getJMSMessageID())
                return retOp
            }

        }
        queueHouseKeepingData['hasMore'] = 'N'
        queueHouseKeepingData['msgData'] = queueData
        def retOp = new JsonBuilder(queueHouseKeepingData).toPrettyString();
        AppLogger.info('inspectQueue: exiting with retOp = '+retOp)

        /* connection.close();
        session.close();
        queueBrowser.close();*/

        return retOp

    }


}
