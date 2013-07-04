import bbharati.jmschirp.queue.msg.MsgBrowser
import bbharati.jmschirp.util.AppLogger

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 29/03/13
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */

def jmsConnectionName = request.getParameter('connection')
def queueName = request.getParameter('queue')
def jmsMsgId = request.getParameter('jmsMsgId')

def usrSession = request.getSession()
def id = usrSession.getId()
AppLogger.info('sessionId = '+id)

def msgBrowser = new MsgBrowser()
msgBrowser.init(jmsConnectionName, queueName, jmsMsgId, usrSession)

def finalOp = msgBrowser.browseMsgDetails()

println(finalOp)
