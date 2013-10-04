
/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 23/08/13
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
 *
 *  This  is responsible for returning the msg after applying search criteria
 * to the queue.
 */

import bbharati.jmschirp.queue.QueueFilter

/*def jmsConnectionName = request.getParameter('connection')
def queueName = request.getParameter('queue')
def searchQUALIFIERJsonStr =  request.getParameter('searchQUALIFIER')
def newReq =  request.getParameter('newReq')  //Specifies if request is fresh or is a re-request.
def usrSession = request.getSession()*/

def jmsConnectionName = 'ems1'
def queueName = 'emsObjQ1'
def searchQUALIFIERJsonStr =   '{"bbharati.jmschirp.test.TestObjMsgInner3_Java":[{"field":"testObjMsgInner3Flag","QUALIFIER":"EQUAL","value":"1"},{"field":"testObjMsgInner3CharField","QUALIFIER":"EQUAL","value":"C"},{"field":"testObjMsgInner3ByteField","QUALIFIER":"EQUAL","value":"0"},{"field":"testObjMsgInner3ShortField","QUALIFIER":"EQUAL","value":"1"},{"field":"testObjMsgInner3IntField","QUALIFIER":"EQUAL","value":"345"},{"field":"testObjMsgInner3LongField","QUALIFIER":"EQUAL","value":"123"}],"bbharati.jmschirp.test.TestObjMsgInner2_Java":[{"field":"inner3StrField","QUALIFIER":"EQUAL","value":"aaa"},{"field":"inner3IntField","QUALIFIER":"EQUAL","value":"23"},{"field":"inner3ListField","QUALIFIER":"EQUAL","value":"b"},{"field":"inner3MapField","QUALIFIER":"EQUAL","value":"c"},{"field":"inner3LongAry","QUALIFIER":"EQUAL","value":"12"}],"bbharati.jmschirp.test.TestObjMsgInner1_Java":[{"field":"innerStrField","QUALIFIER":"EQUAL","value":"ww"}],"bbharati.jmschirp.test.TestObjMsg_Java":[{"field":"strField","QUALIFIER":"EQUAL","value":"xx"},{"field":"intField","QUALIFIER":"EQUAL","value":"23"}]}'


def newReq =   "Y"
def usrSession = "0x12345"

QueueFilter qFilter = new QueueFilter()
qFilter.init(jmsConnectionName, queueName, searchQUALIFIERJsonStr,newReq,usrSession)
def retJsonStr = qFilter.filterQueue()
println(retJsonStr)



