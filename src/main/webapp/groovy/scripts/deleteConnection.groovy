import bbharati.jmschirp.util.AppLogger
import groovy.json.JsonBuilder

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 25/06/13
 * Time: 8:53 PM
 * To change this template use File | Settings | File Templates.
 */

def name = request.getParameter('name');
AppLogger.info("deleteConnection: name is "+name);

def USERHOME = System.getProperty("user.home");
AppLogger.info("saveConnectin: USERHOME is "+USERHOME);


def file = new File(USERHOME+"/.jms-chirp/.jmsVendorInfo") ;


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
    if (evaluatedMap.get('name') == name)
    {

        //for delete dont write this li9ne back to tmp file
        continue

    }
    else
    {
        bw.write(eachLine)
        bw.newLine()
    }

}
bw.close();

//Make temp file - end

//Mv temp file to main file

tmpFile.renameTo(file)

def retMap = [status : true]


println(new JsonBuilder(retMap))




