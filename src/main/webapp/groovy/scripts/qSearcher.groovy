
/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 23/08/13
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.

 * This class contains the utility methods that returns ObjectMessage meta-data info
 * to the browser. This meta-data will be displayed in the Queue - Search dialog.

 */

import bbharati.jmschirp.queue.msg.QueueSearcher;

def jmsConnectionName = request.getParameter('connection')
def queueName = request.getParameter('queue')

QueueSearcher qSearcher = new QueueSearcher()
qSearcher.init(jmsConnectionName, queueName)
def retJsonStr = qSearcher.browseMsgDetails()
println(retJsonStr)


