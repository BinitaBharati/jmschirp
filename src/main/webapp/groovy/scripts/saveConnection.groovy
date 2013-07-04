import bbharati.jmschirp.util.AppLogger

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 27/02/13
 * Time: 5:48 PM
 * To change this template use File | Settings | File Templates.
 */

/* Save the JMS Vendor details somewhere. (A file will be OK for now )
* File operations like writing to a file is cake-walk in Groovy as compared to Java !
* http://codetojoy.blogspot.in/2008/08/groovy-file-io-staying-at-ritz.html*/

def connectionName  = request.getParameter('connectionName');

def vendorType = request.getParameter('vendorType');

def vendorVersion = request.getParameter('vendorVersion');

def host =   request.getParameter('host');

def port = request.getParameter('port');

def adminUser =  request.getParameter('adminUser');

def adminPasswd = request.getParameter('adminPasswd');

def jndiName = request.getParameter('jndiName'); //connection factory jndi name

def jndiUser = request.getParameter('jndiUser'); //connection factory jndi user

def jndiPassword = request.getParameter('jndiPassword'); //connection factory jndi password

def jmxPort = request.getParameter('jmxPort'); //connection factory jndi name

def jmxUser = request.getParameter('jmxUser'); //connection factory jndi user

def jmxPasswd = request.getParameter('jmxPassword'); //connection factory jndi password

def action = request.getParameter('action'); //connection factory jndi name
AppLogger.info("saveConnectin: action is "+action);

def USERHOME = System.getProperty("user.home");
AppLogger.info("saveConnectin: USERHOME is "+USERHOME);

def file = new File(USERHOME+"/.jms-chirp/.jmsVendorInfo") ;

if (action == '0')//New connecton
{
    def entryMap = [name:"\"$connectionName\"",
            type:"\"$vendorType\"",
            version:"\"$vendorVersion\"" ,
            host:"\"$host\"",
            port:"\"$port\"",
            adminUser:"\"$adminUser\"",
            adminPasswd:"\"$adminPasswd\"",
            jndiName:"\"$jndiName\"",
            jndiUser:"\"$jndiUser\"",
            jndiPasswd:"\"$jndiPassword\"",
            jmxPort:"\"$jmxPort\"",
            jmxUser:"\"$jmxUser\"",
            jmxPasswd:"\"$jmxPasswd\""];

    AppLogger.info("saveConnectin: entryMap is "+entryMap);

    file.append(entryMap);
    file.append("\n");

}
else if (action == '1' )  //Update connection
{
    /* Not possible to just replace a line within a file, need to write required content to another temp file, and then copy back.

    */

    //Make temp file - start
    def tmpFile =   new File(USERHOME+"/.jms-chirp/.tmp")
    BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile, true));

    def fileItems = file.readLines();

    for (i=0; i<fileItems.size();i++)
    {
        def eachLine =  fileItems.get(i);
        LinkedHashMap evaluatedMap = Eval.me(eachLine) ;
        //eachLine of format : [type:"EMS", version:"4.4.1", name:"Tibco1", url:"tcp://10.76.85.194:7222", user:"admin", password:""
        if (evaluatedMap.get('name') == connectionName)
        {

                //ovveride map values
                evaluatedMap['name'] = "\"$connectionName\""
                evaluatedMap['type'] = "\"$vendorType\""
                evaluatedMap['version'] = "\"$vendorVersion\""
                evaluatedMap['host'] = "\"$host\""
                evaluatedMap['port'] = "\"$port\""
                evaluatedMap['adminUser'] = "\"$adminUser\""
                evaluatedMap['adminPasswd'] = "\"$adminPasswd\""
                evaluatedMap['jndiName'] = "\"$jndiName\""
                evaluatedMap['jndiUser'] = "\"$jndiUser\""
                evaluatedMap['jndiPasswd'] = "\"$jndiPassword\""
                evaluatedMap['jmxPort'] = "\"$jmxPort\""
                evaluatedMap['jmxUser'] = "\"$jmxUser\""
                evaluatedMap['jmxPasswd'] = "\"$jmxPasswd\""
                AppLogger.info('saveConnectin: evaluatedMap '+evaluatedMap.toString())

                bw.write(evaluatedMap.toString());
                bw.newLine();

        }
        else
        {
            bw.write(eachLine);
            bw.newLine();
        }

    }
    bw.close();

    //Make temp file - end

    //Mv temp file to main file
    tmpFile.renameTo(file)

}








