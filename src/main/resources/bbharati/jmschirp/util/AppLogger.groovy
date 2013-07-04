package bbharati.jmschirp.util

import groovy.util.logging.Log4j

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 19/03/13
 * Time: 11:41 PM
 * To change this template use File | Settings | File Templates.
 */

@Log4j
class AppLogger {

    def static info(String logEntry)
    {
         log.info(logEntry)  ;
    }
}
