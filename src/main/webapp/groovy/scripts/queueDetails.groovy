import bbharati.jmschirp.queue.QueueBrowser1


/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 29/03/13
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */

def jmsConnectionName = request.getParameter('connection')
def queueName = request.getParameter('queue')
def isScrolled =   request.getParameter('isScrolled')
def usrSession = request.getSession()

def queueBrowser = new QueueBrowser1()
queueBrowser.init(jmsConnectionName, queueName, usrSession, isScrolled)
def finalOp = queueBrowser.browseQueue()

println(finalOp)
