import bbharati.jmschirp.util.AppLogger

def USERHOME = System.getProperty("user.home");
AppLogger.info("saveConnectin: USERHOME is "+USERHOME);

def jmsConnectionName  = 'junk3';

def jmsVendorType = 'pp';

def jmsVendorVersion = '4.4';

def jmsConnectionUrl =   'tcp://j.1.2.3:9090';

def jmsConnectionUser =   'tuuu';

def jmsConnectionPasswd = 'nnnn';

def jndi = 'jjjj' //connection factory jndi name


    //File file = new File(USERHOME+"/.jms-chirp/.jmsVendorInfo") ;

new File(USERHOME+"/.jms-chirp/.tmp").withWriter { bufferedWriter ->

    new File(USERHOME+"/.jms-chirp/.jmsVendorInfo").eachLine { line ->
        if (line.indexOf('name:\"'+jmsConnectionName))
        {
                LinkedHashMap    evaluatedMap = [:]
            evaluatedMap['type'] = "\"$jmsVendorType\""
            evaluatedMap['version'] = "\"$jmsVendorVersion\""
            evaluatedMap['name'] = "\"$jmsConnectionName\""
            evaluatedMap['url'] = "\"$jmsConnectionUrl\""
            evaluatedMap['user'] = "\"$jmsConnectionUser\""
            evaluatedMap['password'] = "\"$jmsConnectionPasswd\""
            evaluatedMap['jndi'] = "\"$jndi\""
            AppLogger.info('saveConnectin: evaluatedMap '+evaluatedMap.toString())
            def str = evaluatedMap.toString();

            bufferedWriter.writeLine(str)

        }

        else
        {
            bufferedWriter.writeLine(line)
        }

    }

}





