import bbharati.jmschirp.util.AppLogger

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 23/05/13
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 *
 *
 */

QueueBrowser qBrowser = new QueueBrowser()
AppLogger.info('started at '+System.currentTimeMillis())
qBrowser.init('Tibco1', 'jmschirpObjMsgQ1')
qBrowser.browseQueue()
AppLogger.info('ended at '+System.currentTimeMillis())
