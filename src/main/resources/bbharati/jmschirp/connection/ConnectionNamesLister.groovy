package bbharati.jmschirp.connection

import bbharati.jmschirp.util.AppLogger

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 16/05/13
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
class ConnectionNamesLister {

    def jmsConnectionName


    def listConnectionNames()
    {

        def  connectionNameList = [];

        def USRHOME = System.getProperty("user.home");
        AppLogger.info('USER HOME DIR '+USRHOME);

        def file = new File(USRHOME+'/.jms-chirp/.jmsVendorInfo').text;

        def fileItems = file.readLines();

        for (def i=0; i<fileItems.size();i++)
        {
            def eachLine =  fileItems.get(i)
            LinkedHashMap evaluatedMap = Eval.me(eachLine)

            def fileConnectionName = evaluatedMap.get('name')
            AppLogger.info('listQueues: fileConnectionName = '+fileConnectionName)

            connectionNameList.add(fileConnectionName)

        }

        return  connectionNameList


    }
}
