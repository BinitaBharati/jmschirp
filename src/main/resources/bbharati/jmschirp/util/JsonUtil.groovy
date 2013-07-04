package bbharati.jmschirp.util

import bbharati.jmschirp.dynatree.node.model.DynaTreeNode
import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 30/05/13
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
class JsonUtil {

    def static traverseJson(def jsonObject, def parentNode)
    {
        def dynaTreeList = []

        def keySet = jsonObject.keySet();
        //AppLogger.info('inspectEMSQueue: keySet = '+keySet)

        def keySetItr = keySet.iterator()

        while(keySetItr.hasNext())
        {
            def key = keySetItr.next()

            def val = jsonObject.get(key)

            //ppLogger.info('inspectEMSQueue: val1 = '+val)

            def className = val.getClass().getName()

            if (!(val.getClass().getName() == 'org.codehaus.groovy.grails.web.json.JSONObject' ||
                    val.getClass().getName() == 'org.codehaus.groovy.grails.web.json.JSONArray')
            )
            {   //Implies plain object values
                def treeNode = new DynaTreeNode()
                treeNode.setTitle(key+" = "+val)

                dynaTreeList.add(treeNode)

            }

            else if (className == 'org.codehaus.groovy.grails.web.json.JSONObject')
            {

                def treeNode = new DynaTreeNode()
                treeNode.setTitle(key)
                treeNode.isFolder = true

                def dynaTreeList1 = traverseJson(val, treeNode)

                dynaTreeList.add(treeNode)


            }

            else if (className == 'org.codehaus.groovy.grails.web.json.JSONArray')
            {
                def treeNode = new DynaTreeNode()
                treeNode.setTitle(key)

                treeNode.isFolder = true

                def retTreeList = traverseJsonArray(val, treeNode)

                if (parentNode != null)
                {
                    parentNode.children = treeNode
                }


                dynaTreeList.add(treeNode)


            }

        }
        //AppLogger.info('111 -- dynaTreeList '+dynaTreeList.size())
        if (parentNode != null)
        {
            parentNode.children = dynaTreeList
        }

        return dynaTreeList


    }

    def static traverseJsonArray(def jsonAry, def parentNode)
    {

          def retTreeList = []

           def aryItr = jsonAry.iterator()

           def count = 0
           while(aryItr.hasNext())
           {
               def each = aryItr.next()

               def arrayItemTreeNode = new DynaTreeNode()
               arrayItemTreeNode.setTitle("ary["+count+"]")
               arrayItemTreeNode.isFolder = true

               //parentNode.children =   arrayItemTreeNode

               count++


               if (!(each.getClass().getName() == 'org.codehaus.groovy.grails.web.json.JSONObject' ||
                       each.getClass().getName() == 'org.codehaus.groovy.grails.web.json.JSONArray')
                 )
               {   //Implies plain object values

                     def treeNode = new DynaTreeNode()
                     treeNode.setTitle(each)
                     //return treeNode

                   arrayItemTreeNode.children = treeNode

                   retTreeList.add(arrayItemTreeNode)

               }
               else  if (each.getClass().getName() == 'org.codehaus.groovy.grails.web.json.JSONObject')
               {
                   //iterate JsonObject
                   //AppLogger.info('222 -- gng to call traverseJson ')
                   def retChildTreeList = traverseJson(each, arrayItemTreeNode)
                   //AppLogger.info('222 -- retChildTreeList '+retTreeList.size())

                   retTreeList.add(arrayItemTreeNode)

               }
               else if (each.getClass().getName() == 'org.codehaus.groovy.grails.web.json.JSONArray')
               {
                   def parentNode1 = new DynaTreeNode()
                   parentNode1.setTitle(each)
                   parentNode1.isFolder = true


                   def retChildTreeList = traverseJsonArray(each, parentNode1)

                   retTreeList.add(parentNode1)
               }
           }

        parentNode.children = retTreeList
        return retTreeList
    }

    def static parseJson(def jsonStr)
    {
        def jsonObj = new JSONObject(jsonStr)

        def retList = traverseJson(jsonObj, null)

        //AppLogger.info('parseJson: retList = '+retList)

        return retList


    }

    def static parse1()
    {

    }


}
