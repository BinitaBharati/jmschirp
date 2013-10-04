package bbharati.jmschirp.util;

import bbharati.jmschirp.dynatree.node.model.DynaTreeNode;
import bbharati.jmschirp.queue.search.model.EachFieldDescriptor;
import bbharati.jmschirp.queue.search.model.SearchQueueModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: binita.bharati@gmail.com
 * This class contains the utility methods that returns ObjectMessage meta-data info
 * to the browser. This meta-data will be displayed in the Queue - Search dialog.
 */

public class QueueSearcherUtil
{
    private String customObjectTopLevelPkg;
    private static List<String> oobCollectionClassNames = new ArrayList();

    public String getCustomObjectTopLevelPkg()
    {
        return this.customObjectTopLevelPkg;
    }

    public void setCustomObjectTopLevelPkg(String customObjectTopLevelPkg) {
        this.customObjectTopLevelPkg = customObjectTopLevelPkg;
    }

    public boolean isPrimitive(String eachFieldClassName)
    {
        if (eachFieldClassName != null)
        {
            if ((eachFieldClassName.indexOf(".") == -1) && (eachFieldClassName.indexOf("[") == -1))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isPrimitiveArray(String eachFieldClassName)
    {
        if (eachFieldClassName != null)
        {
            if ((eachFieldClassName.indexOf(".") == -1) && (eachFieldClassName.indexOf("[") != -1))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isObject(String eachFieldClassName)
    {
        if (eachFieldClassName != null)
        {
            if ((eachFieldClassName.indexOf(".") != -1) && (eachFieldClassName.indexOf("[L") == -1))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isOOBObject(Class eachFieldClass)
    {
        if (eachFieldClass != null)
        {
            String eachFieldClassName = eachFieldClass.getName();
            if ((eachFieldClassName.indexOf(".") != -1) && (eachFieldClassName.indexOf("[L") == -1))
            {
                if ((!isCollection(eachFieldClass)) && (!isMap(eachFieldClass)))
                {
                    if (eachFieldClassName.indexOf("java") != -1)
                    {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public boolean isObjectArray(String eachFieldClassName)
    {
        if (eachFieldClassName != null)
        {
            if ((eachFieldClassName.indexOf(".") != -1) && (eachFieldClassName.indexOf("[L") != -1))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isOOBObjectArray(String eachFieldClassName)
    {
        if (eachFieldClassName != null)
        {
            if ((eachFieldClassName.indexOf(".") != -1) && (eachFieldClassName.indexOf("[L") != -1))
            {
                if (eachFieldClassName.indexOf("java") != -1)
                {
                    return true;
                }
            }

        }

        return false;
    }

    public boolean isCollection(Class eachFieldClass)
    {
        if (eachFieldClass != null)
        {
            if (Collection.class.isAssignableFrom(eachFieldClass))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isOOBObjectCollection(Object object, Field eachField)
            throws Exception
    {
        if (eachField != null)
        {
            if (Collection.class.isAssignableFrom(eachField.getType()))
            {
                Collection coll = (Collection)eachField.get(object);

                if (coll != null)
                {
                    Iterator collItr = coll.iterator();

                    while (collItr.hasNext())
                    {
                        Object eachItem = collItr.next();
                        String eachItemClassName = eachItem.getClass().getName();

                        if (eachItemClassName.indexOf("java") != -1)
                        {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean isMap(Class eachFieldClass)
    {
        if (eachFieldClass != null)
        {
            if (Map.class.isAssignableFrom(eachFieldClass))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isOOBObjectMap(Object object, Field eachField)
            throws Exception
    {
        if (eachField != null)
        {
            if (Map.class.isAssignableFrom(eachField.getType()))
            {
                Map map = (Map)eachField.get(object);

                if (map != null)
                {
                    Iterator mapItr = map.keySet().iterator();

                    while (mapItr.hasNext())
                    {
                        Object eachKey = mapItr.next();
                        String eachKeyClassName = eachKey.getClass().getName();

                        if (eachKeyClassName.indexOf("java") != -1)
                        {
                            Object mapVal = map.get(eachKey);

                            String eachValClassName = mapVal.getClass().getName();

                            if (eachValClassName.indexOf("java") != -1)
                            {
                                return true;
                            }
                        }
                    }
                }

            }

        }

        return false;
    }

    public DynaTreeNode parsePrimitiveField(Object object, Field eachField)
            throws Exception
    {
        DynaTreeNode dynaTreeNode = null;
        if (eachField != null)
        {
            dynaTreeNode = new DynaTreeNode();

            eachField.setAccessible(true);

            if (eachField.getType().getName().equals("boolean"))
            {
                dynaTreeNode.setTitle(eachField.getName() + " = " + eachField.getBoolean(object));
            }
            else if (eachField.getType().getName().equals("byte"))
            {
                dynaTreeNode.setTitle(eachField.getName() + " = " + eachField.getInt(object));
            }
            else if (eachField.getType().getName().equals("char"))
            {
                dynaTreeNode.setTitle(eachField.getName() + " = " + eachField.getChar(object));
            }
            else if (eachField.getType().getName().equals("double"))
            {
                dynaTreeNode.setTitle(eachField.getName() + " = " + eachField.getDouble(object));
            }
            else if (eachField.getType().getName().equals("float"))
            {
                dynaTreeNode.setTitle(eachField.getName() + " = " + eachField.getFloat(object));
            }
            else if (eachField.getType().getName().equals("int"))
            {
                dynaTreeNode.setTitle(eachField.getName() + " = " + eachField.getInt(object));
            }
            else if (eachField.getType().getName().equals("long"))
            {
                dynaTreeNode.setTitle(eachField.getName() + " = " + eachField.getLong(object));
            }
            else if (eachField.getType().getName().equals("short"))
            {
                dynaTreeNode.setTitle(eachField.getName() + " = " + eachField.getShort(object));
            }
            eachField.setAccessible(false);
        }

        return dynaTreeNode;
    }

    public DynaTreeNode parsePrimitiveArrays(Object object, Field eachField)
            throws Exception
    {
        DynaTreeNode retNode = null;
        if (eachField != null)
        {
            eachField.setAccessible(true);

            retNode = new DynaTreeNode();
            retNode.setTitle(eachField.getName());

            List childNodeList = new ArrayList();

            if (eachField.getType().getName().equals("[Z"))
            {
                boolean[] boolAry = (boolean[])(boolean[])eachField.get(object);

                for (int i = 0; i < boolAry.length; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("[" + i + "] = " + boolAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if (eachField.getType().getName().equals("[B"))
            {
                byte[] byteAry = (byte[])(byte[])eachField.get(object);

                for (int i = 0; i < byteAry.length; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("[" + i + "] = " + byteAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if (eachField.getType().getName().equals("[C"))
            {
                char[] charAry = (char[])(char[])eachField.get(object);

                for (int i = 0; i < charAry.length; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("[" + i + "] = " + charAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if (eachField.getType().getName().equals("[D"))
            {
                double[] dblAry = (double[])(double[])eachField.get(object);

                for (int i = 0; i < dblAry.length; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("[" + i + "] = " + dblAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if (eachField.getType().getName().equals("[F"))
            {
                float[] floatAry = (float[])(float[])eachField.get(object);

                for (int i = 0; i < floatAry.length; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("[" + i + "] = " + floatAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if (eachField.getType().getName().equals("[I"))
            {
                int[] intAry = (int[])(int[])eachField.get(object);

                for (int i = 0; i < intAry.length; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("[" + i + "] = " + intAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if (eachField.getType().getName().equals("[J"))
            {
                long[] longAry = (long[])(long[])eachField.get(object);

                for (int i = 0; i < longAry.length; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("[" + i + "] = " + longAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if (eachField.getType().getName().equals("[S"))
            {
                short[] shortAry = (short[])(short[])eachField.get(object);

                for (int i = 0; i < shortAry.length; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("[" + i + "] = " + shortAry[i]);
                    childNodeList.add(childNode);
                }
            }
            retNode.setIsFolder(Boolean.valueOf(true));
            retNode.setChildren(childNodeList);
            eachField.setAccessible(false);
        }

        return retNode;
    }

    public DynaTreeNode parseOOBObject(Object object, Field eachField)
            throws Exception
    {
        DynaTreeNode dynaTreeNode = null;
        if (eachField != null)
        {
            eachField.setAccessible(true);
            dynaTreeNode = new DynaTreeNode();
            dynaTreeNode.setTitle(eachField.getName() + "(" + eachField.getType().getName() + ") = " + eachField.get(object) + "");

            eachField.setAccessible(false);
        }
        else
        {
            dynaTreeNode = new DynaTreeNode();
            dynaTreeNode.setTitle(object + "");
        }

        return dynaTreeNode;
    }

    public DynaTreeNode parseOOBObjectArrays(Object object, Field eachField)
            throws Exception
    {
        DynaTreeNode retNode = null;
        if (eachField != null)
        {
            eachField.setAccessible(true);
            retNode = new DynaTreeNode();
            retNode.setTitle(eachField.getName());

            List childNodeList = new ArrayList();

            Object[] objAry = (Object[])(Object[])eachField.get(object);
            for (int i = 0; i < objAry.length; i++)
            {
                DynaTreeNode childNode = new DynaTreeNode();
                childNode.setTitle("[" + i + "] = " + objAry[i] + "");
                childNodeList.add(childNode);
            }

            retNode.setIsFolder(Boolean.valueOf(true));

            retNode.setChildren(childNodeList);
            eachField.setAccessible(false);
        }

        return retNode;
    }

    public DynaTreeNode parseOOBObjectCollection(Object object, Field eachField) throws Exception
    {
        DynaTreeNode retNode = null;
        if (eachField != null)
        {
            eachField.setAccessible(true);
            retNode = new DynaTreeNode();
            retNode.setTitle(eachField.getName() + "(" + eachField.getGenericType() + ")");

            List childNodeList = new ArrayList();

            if (Collection.class.isAssignableFrom(eachField.getType()))
            {
                Collection coll = (Collection)eachField.get(object);
                if (coll != null)
                {
                    Iterator collItr = coll.iterator();
                    int index = 0;
                    while (collItr.hasNext())
                    {
                        Object eachItem = collItr.next();
                        String eachItemClassName = eachItem.getClass().getName();

                        if (eachItemClassName.indexOf("java") != -1)
                        {
                            DynaTreeNode childNode = new DynaTreeNode();
                            childNode.setTitle("[" + index + "] = " + eachItem + "");
                            childNodeList.add(childNode);
                            index++;
                        }
                    }
                    retNode.setIsFolder(Boolean.valueOf(true));
                    retNode.setChildren(childNodeList);
                }
            }

            eachField.setAccessible(false);
        }

        return retNode;
    }

    public DynaTreeNode parseOOBObjectMap(Object object, Field eachField) throws Exception
    {
        DynaTreeNode retNode = null;
        if (eachField != null)
        {
            eachField.setAccessible(true);
            retNode = new DynaTreeNode();
            retNode.setTitle(eachField.getName());

            List childNodeList = new ArrayList();

            if (Map.class.isAssignableFrom(eachField.getType()))
            {
                Map map = (Map)eachField.get(object);

                if (map != null)
                {
                    Iterator keyItr = map.keySet().iterator();
                    childNodeList = new ArrayList();

                    while (keyItr.hasNext())
                    {
                        Object eachKey = keyItr.next();
                        String eachKeyClassName = eachKey.getClass().getName();

                        if (eachKeyClassName.indexOf("java") != -1)
                        {
                            Object eachVal = map.get(eachKey);
                            String eachValClassName = eachVal.getClass().getName();

                            if (eachValClassName.indexOf("java") != -1)
                            {
                                DynaTreeNode childNode = new DynaTreeNode();
                                childNode.setTitle(eachKey + " = " + eachVal + "");
                                childNodeList.add(childNode);
                            }
                        }

                    }

                    retNode.setIsFolder(Boolean.valueOf(true));
                    retNode.setChildren(childNodeList);
                }
            }
            eachField.setAccessible(false);
        }
        return retNode;
    }

    public List<SearchQueueModel> parseCustomObject(Object msg)
            throws Exception
    {
        List retList = new ArrayList();

        if (msg != null)
        {
            String objMsgClass = msg.getClass().getName();
            AppLogger.info("parseCustomObject: objMsgClass = " + objMsgClass + ", customObjectTopLevelPkg = " + this.customObjectTopLevelPkg);
            if (objMsgClass.startsWith(this.customObjectTopLevelPkg))
            {
                Class superClass = msg.getClass().getSuperclass();
                if (superClass.getName().startsWith(this.customObjectTopLevelPkg))
                {
                    Field[] superFieldAry = msg.getClass().getSuperclass().getDeclaredFields();
                    List model = parseCustomObject2(superFieldAry, msg, superClass);
                    retList.addAll(model);
                }

                Field[] fieldAry = msg.getClass().getDeclaredFields();
                List model = parseCustomObject2(fieldAry, msg, msg.getClass());
                retList.addAll(model);
            }

        }

        return retList;
    }

    private List<SearchQueueModel> parseCustomObject2(Field[] fieldAry, Object msg, Class thisClass)
            throws Exception
    {
        List retSearchQModelList = new ArrayList();

        SearchQueueModel searchQModel = new SearchQueueModel();
        searchQModel.setClassName(thisClass.getName());

        List eachFieldDescriptorList = new ArrayList();

        for (Field eachField : fieldAry)
        {
            eachField.setAccessible(true);
            int modifier = eachField.getModifiers();
            if (modifier > 8)
            {
                continue;
            }
            String eachFieldClassName = eachField.getGenericType().toString();
            AppLogger.info("parseCustomObject: eachField name = " + eachField.getName() + " mod = " + modifier + ", eachFieldClassName = " + eachFieldClassName);

            Map map = new LinkedHashMap();
            if (isPrimitive(eachFieldClassName))
            {
                EachFieldDescriptor eachFieldDesc = new EachFieldDescriptor();
                eachFieldDesc.setFieldName(eachField.getName());
                eachFieldDesc.setFieldClassName(eachFieldClassName);
                eachFieldDesc.setHasMultipleValues(false);

                searchQModel.addFieldDescriptor(eachFieldDesc);
            }
            else if (isPrimitiveArray(eachFieldClassName))
            {
                EachFieldDescriptor eachFieldDesc = new EachFieldDescriptor();
                eachFieldDesc.setFieldName(eachField.getName());
                eachFieldDesc.setFieldClassName(eachFieldClassName);
                eachFieldDesc.setHasMultipleValues(true);

                searchQModel.addFieldDescriptor(eachFieldDesc);
            }
            else if (isObject(eachFieldClassName))
            {
                if (isOOBObject(eachField.getType()))
                {
                    EachFieldDescriptor eachFieldDesc = new EachFieldDescriptor();
                    eachFieldDesc.setFieldName(eachField.getName());
                    eachFieldDesc.setFieldClassName(eachFieldClassName);
                    eachFieldDesc.setHasMultipleValues(false);

                    searchQModel.addFieldDescriptor(eachFieldDesc);
                }
                else if (isCollection(eachField.getType()))
                {
                    List collList = parseCollection((Collection)eachField.get(msg), eachField, searchQModel);

                    retSearchQModelList.addAll(collList);
                }
                else if (isMap(eachField.getType()))
                {
                    List mapList = parseMap((Map)eachField.get(msg), eachField, searchQModel);

                    retSearchQModelList.addAll(mapList);
                }
                else
                {
                    List objList = parseCustomObject(eachField.get(msg));
                    retSearchQModelList.addAll(objList);
                }

            }
            else if (isObjectArray(eachFieldClassName))
            {
                if (isOOBObjectArray(eachFieldClassName))
                {
                    EachFieldDescriptor eachFieldDesc = new EachFieldDescriptor();
                    eachFieldDesc.setFieldName(eachField.getName());
                    eachFieldDesc.setFieldClassName(eachFieldClassName);
                    eachFieldDesc.setHasMultipleValues(true);

                    searchQModel.addFieldDescriptor(eachFieldDesc);
                }
                else
                {
                    List objList = parseCustomObjectArray((Object[])(Object[])eachField.get(msg), searchQModel);
                    retSearchQModelList.addAll(objList);
                }

            }

            eachField.setAccessible(false);
        }

        retSearchQModelList.add(searchQModel);

        return retSearchQModelList;
    }

    public List<SearchQueueModel> parseCustomObjectArray(Object[] objAry, SearchQueueModel searchQueueModel) throws Exception
    {
        List retList = new ArrayList();

        if (objAry != null)
        {
            int i = 0; if (i < objAry.length)
        {
            Object eachAryItem = objAry[i];
            if (eachAryItem != null)
            {
                List retList1 = parseCustomObject(eachAryItem);
                retList.addAll(retList1);
            }

        }

        }

        return retList;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    public List<SearchQueueModel> parseInnerCollection(Collection<?> objColl, SearchQueueModel searchQueueModel)
            throws Exception
    {
        List retList = new ArrayList();
        if (objColl != null)
        {
            int collIdx = 0;
            Iterator collItr = objColl.iterator();
            if (collItr.hasNext())
            {
                Object collItem = collItr.next();
                if (collItem != null)
                {
                    if (!isOOBObject(collItem.getClass()))
                    {
                        if (isCollection(collItem.getClass()))
                        {
                            List parsedInnerCollection = parseInnerCollection((Collection)collItem, searchQueueModel);
                            retList.addAll(parsedInnerCollection);
                        }
                        else if (isMap(collItem.getClass()))
                        {
                            List parsedInnerMapList = parseInnerMap((Map)collItem, searchQueueModel);
                            retList.addAll(parsedInnerMapList);
                        }
                        else
                        {
                            List parsedCustomObjList = parseCustomObject(collItem);
                            retList.addAll(parsedCustomObjList);
                        }
                    }
                }
            }

        }

        return retList;
    }

    public List<SearchQueueModel> parseCollection(Collection<?> objColl, Field collectionField, SearchQueueModel searchQueueModel) throws Exception
    {
        List retList = new ArrayList();

        String collItemCN = null;

        if (objColl != null)
        {
            Iterator collItr = objColl.iterator();
            int index = 0;
            if (collItr.hasNext())
            {
                index++;
                Object collItem = collItr.next();
                if (collItem != null)
                {
                    if (collItemCN == null)
                    {
                        collItemCN = collItem.getClass().getName();
                    }
                    if (isOOBObject(collItem.getClass()))
                    {
                        EachFieldDescriptor eachFieldDescriptor = new EachFieldDescriptor();
                        eachFieldDescriptor.setFieldName(collectionField.getName());
                        eachFieldDescriptor.setFieldClassName(objColl.getClass().getName() + "<" + collItemCN + ">");
                        eachFieldDescriptor.setHasMultipleValues(true);
                        searchQueueModel.addFieldDescriptor(eachFieldDescriptor);
                    }
                    else if (isCollection(collItem.getClass()))
                    {
                        List parsedCollList = parseInnerCollection((Collection)collItem, searchQueueModel);
                        retList.addAll(parsedCollList);
                    }
                    else if (isMap(collItem.getClass()))
                    {
                        List parsedMapList = parseInnerMap((Map)collItem, searchQueueModel);
                        retList.addAll(parsedMapList);
                    }
                    else
                    {
                        List parsedCustomObjList = parseCustomObject(collItem);

                        retList.addAll(parsedCustomObjList);
                    }

                }

            }

        }

        return retList;
    }

    public List<SearchQueueModel> parseInnerMap(Map objMap, SearchQueueModel searchQueueModel) throws Exception
    {
        List retList = new ArrayList();
        if (objMap != null)
        {
            Iterator mapItr = objMap.keySet().iterator();

            if (mapItr.hasNext())
            {
                Object mapKey1 = mapItr.next();
                if (isOOBObject(mapKey1.getClass()))
                {
                    String mapKey = mapKey1 + "";
                    DynaTreeNode mapNode = new DynaTreeNode();
                    mapNode.setTitle(mapKey);
                    mapNode.setIsFolder(Boolean.valueOf(true));

                    Object mapVal = objMap.get(mapKey);

                    if (mapVal != null)
                    {
                        if (!isOOBObject(mapVal.getClass()))
                        {
                            if (isCollection(mapVal.getClass()))
                            {
                                List parseInnerCollList = parseInnerCollection((Collection)mapVal, searchQueueModel);
                                retList.addAll(parseInnerCollList);
                            }
                            else if (isMap(mapVal.getClass()))
                            {
                                List parsedInnerMapList = parseInnerMap((Map)mapVal, searchQueueModel);
                                retList.addAll(parsedInnerMapList);
                            }
                            else
                            {
                                List parsedCustomObjList = parseCustomObject(mapVal);
                                retList.addAll(parsedCustomObjList);
                            }
                        }
                    }

                }

            }

        }

        return retList;
    }

    public List<SearchQueueModel> parseMap(Map objMap, Field eachField, SearchQueueModel searchQueueModel) throws Exception
    {
        String mapCN = null;
        List retList = new ArrayList();

        if (objMap != null)
        {
            Iterator mapItr = objMap.keySet().iterator();

            if (mapItr.hasNext())
            {
                Object mapKey1 = mapItr.next();
                if (isOOBObject(mapKey1.getClass()))
                {
                    String mapKey = mapKey1 + "";

                    Object mapVal = objMap.get(mapKey);

                    if (mapVal != null)
                    {
                        if (mapCN == null)
                        {
                            mapCN = mapKey1.getClass().getName() + ", " + mapVal.getClass().getName();
                        }

                        if (isOOBObject(mapVal.getClass()))
                        {
                            EachFieldDescriptor eachFieldDescriptor = new EachFieldDescriptor();
                            eachFieldDescriptor.setFieldName(eachField.getName());
                            eachFieldDescriptor.setFieldClassName(objMap.getClass().getName() + "<" + mapCN + ">");
                            eachFieldDescriptor.setHasMultipleValues(true);
                            searchQueueModel.addFieldDescriptor(eachFieldDescriptor);
                        }
                        else if (isCollection(mapVal.getClass()))
                        {
                            List parsedInnerCollList = parseInnerCollection((Collection)mapVal, searchQueueModel);
                            retList.addAll(parsedInnerCollList);
                        }
                        else if (isMap(mapVal.getClass()))
                        {
                            List parsedInnerMapList = parseInnerMap((Map)mapVal, searchQueueModel);
                            retList.addAll(parsedInnerMapList);
                        }
                        else
                        {
                            List parsedCustomObjList = parseCustomObject(mapVal);
                            retList.addAll(parsedCustomObjList);
                        }

                    }

                }

            }

        }

        return retList;
    }

    static
    {
        oobCollectionClassNames.add("");
    }
}