package bbharati.jmschirp.util;

import java.lang.reflect.Field;
import java.util.*;

import bbharati.jmschirp.dynatree.node.model.DynaTreeNode;

/**
 * Created with IntelliJ IDEA.
 * Author: binita.bharati@gmail.com
 * Date: 11/06/13
 * Time: 8:52 AM
 * To change this template use File | Settings | File Templates.
 *
 * Class, Field modifier types
 * Modifier and Type		ConstantField	Value
 public static final int		ABSTRACT	1024
 public static final int		FINAL	 	16
 public static final int		INTERFACE	512
 public static final int		NATIVE		256
 public static final int		PRIVATE		2
 public static final int		PROTECTED	4
 public static final int		PUBLIC		1
 public static final int		STATIC		8
 public static final int		STRICT		2048
 public static final int		SYNCHRONIZED	32
 public static final int		TRANSIENT	128
 public static final int		VOLATILE	64
 */
public class MsgParsingUtil {

    private String customObjectTopLevelPkg;

    private static List<String> oobCollectionClassNames ;


    static {
        oobCollectionClassNames = new ArrayList<String>();
        oobCollectionClassNames.add("");
    }

    public String getCustomObjectTopLevelPkg() {
        return customObjectTopLevelPkg;
    }

    public void setCustomObjectTopLevelPkg(String customObjectTopLevelPkg) {
        this.customObjectTopLevelPkg = customObjectTopLevelPkg;
    }

    /**
     *
     * @param eachFieldClassName
     * @return
     * Primitive class names do not have a dot(.)
     * Primitive arrays have a [ , and no dot(.)  character
     *
     */
    public boolean isPrimitive(String eachFieldClassName)
    {
        if(eachFieldClassName != null)
        {
            if(eachFieldClassName.indexOf(".") == -1 && eachFieldClassName.indexOf("[") == -1)
            {
                //a pure primitive type
                return  true;
            }
        }

        return false;

    }

    /**
     *
     * @param eachFieldClassName
     * @return
     * Primitive arrays have a [ , and no dot(.)  character
     */
    public boolean isPrimitiveArray(String eachFieldClassName)
    {
        if(eachFieldClassName != null)
        {
            if(eachFieldClassName.indexOf(".") == -1 && eachFieldClassName.indexOf("[") != -1)
            {
                //a pure primitive array
                return  true;
            }
        }

        return false;
    }

    /**
     *
     * @param eachFieldClassName
     * @return
     *
     */
    public boolean isObject(String eachFieldClassName)
    {
        if(eachFieldClassName != null)
        {
            if(eachFieldClassName.indexOf(".") != -1 && eachFieldClassName.indexOf("[L") == -1)
            {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param eachFieldClass
     * @return
     */
    public boolean isOOBObject(Class eachFieldClass)
    {
        if(eachFieldClass != null)
        {
            String eachFieldClassName = eachFieldClass.getName();
            if(eachFieldClassName.indexOf(".") != -1 && eachFieldClassName.indexOf("[L") == -1)
            {
                if(!isCollection(eachFieldClass) && !isMap(eachFieldClass) )
                {
                    if(eachFieldClassName.startsWith("java"))
                    {
                        return true;
                    }
                }


            }
        }

        return false;
    }

    /**
     *
     * @param eachFieldClassName
     * @return
     * All Object arrays start with [L
     */
    public boolean isObjectArray(String eachFieldClassName)
    {
        if(eachFieldClassName != null)
        {
            if(eachFieldClassName.indexOf(".") != -1 && eachFieldClassName.indexOf("[L") != -1)
            {
                //a object array
                return  true;
            }
        }

        return false;
    }

    /**
     *
     * @param eachFieldClassName
     * @return
     * All Object arrays start with [L
     */
    public boolean isOOBObjectArray(String eachFieldClassName)
    {
        if(eachFieldClassName != null)
        {
            if(eachFieldClassName.indexOf(".") != -1 && eachFieldClassName.indexOf("[L") != -1)
            {

                if(eachFieldClassName.startsWith("java"))
                {
                    return true;
                }

            }
        }

        return false;
    }

    /**
     *
     * @param eachFieldClass
     * @return
     */
    public boolean isCollection(Class eachFieldClass)
    {
        if(eachFieldClass != null)
        {
            if(Collection.class.isAssignableFrom(eachFieldClass))
            {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param object
     * @param eachField
     * @return
     * @throws Exception
     */
    public boolean isOOBObjectCollection(Object object, Field eachField) throws Exception
    {
        if(eachField != null)
        {
            if(Collection.class.isAssignableFrom(eachField.getType()))
            {
                Collection<?> coll = (Collection<?>)eachField.get(object);

                if(coll != null)
                {
                    Iterator<?> collItr = coll.iterator();

                    while (collItr.hasNext())
                    {
                        Object eachItem = collItr.next();
                        String eachItemClassName = eachItem.getClass().getName();

                        if(eachItemClassName.startsWith("java"))
                        {
                            return true;
                        }
                    }
                }
             }
        }

        return false;
    }

    /**
     *
     * @param eachFieldClass
     * @return
     */
    public boolean isMap(Class eachFieldClass)
    {
        if(eachFieldClass != null)
        {
            if(Map.class.isAssignableFrom(eachFieldClass))
            {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param object
     * @param eachField
     * @return
     * @throws Exception
     */
    public boolean isOOBObjectMap(Object object, Field eachField) throws Exception
    {
        if(eachField != null)
        {
            if(Map.class.isAssignableFrom(eachField.getType()))
            {
                Map<?,?> map = (Map<?,?>)eachField.get(object);

                if(map != null)
                {
                    Iterator<?> mapItr = map.keySet().iterator();

                    while (mapItr.hasNext())
                    {
                        Object eachKey = mapItr.next();
                        String eachKeyClassName = eachKey.getClass().getName();

                        if(eachKeyClassName.startsWith("java"))
                        {
                            Object mapVal = map.get(eachKey);

                            String eachValClassName = mapVal.getClass().getName();

                            if(eachValClassName.startsWith("java"))
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


    /**
     *
     * @param object
     * @param eachField
     * @return
     * @throws Exception
     */
    public DynaTreeNode parsePrimitiveField(Object object, Field eachField)  throws Exception
    {
        DynaTreeNode dynaTreeNode = null;
        if(eachField != null)
        {
             dynaTreeNode = new DynaTreeNode();

        /*To work around :  can not access a member of class bbharati.binita.jmschirp.test.TestObjMsg_Java with modifiers "private"
        */
            eachField.setAccessible(true);

            if(eachField.getType().getName().equals("boolean"))
            {
                dynaTreeNode.setTitle(eachField.getName()+" = "+eachField.getBoolean(object));
            }
            else if(eachField.getType().getName().equals("byte"))
            {
                dynaTreeNode.setTitle(eachField.getName()+" = "+eachField.getInt(object));
            }
            else if(eachField.getType().getName().equals("char"))
            {
                dynaTreeNode.setTitle(eachField.getName()+" = "+eachField.getChar(object));
            }
            else if(eachField.getType().getName().equals("double"))
            {
                dynaTreeNode.setTitle(eachField.getName()+" = "+eachField.getDouble(object));
            }
            else if(eachField.getType().getName().equals("float"))
            {
                dynaTreeNode.setTitle(eachField.getName()+" = "+eachField.getFloat(object));
            }
            else if(eachField.getType().getName().equals("int"))
            {
                dynaTreeNode.setTitle(eachField.getName()+" = "+eachField.getInt(object));
            }
            else if(eachField.getType().getName().equals("long"))
            {
                dynaTreeNode.setTitle(eachField.getName()+" = "+eachField.getLong(object));
            }
            else if(eachField.getType().getName().equals("short"))
            {
                dynaTreeNode.setTitle(eachField.getName()+" = "+eachField.getShort(object));
            }
            eachField.setAccessible(false);
        }

        return dynaTreeNode;
    }


    /**
     *
     * @param object
     * @param eachField
     * @return
     * @throws Exception
     */
    public DynaTreeNode parsePrimitiveArrays(Object object, Field eachField)  throws Exception
    {
        DynaTreeNode retNode = null;
        if(eachField != null)
        {
            eachField.setAccessible(true);

            retNode = new DynaTreeNode();
            retNode.setTitle(eachField.getName());

            List<DynaTreeNode> childNodeList = new ArrayList<DynaTreeNode>();

            if(eachField.getType().getName().equals("[Z"))
            {
                boolean[] boolAry = (boolean[])eachField.get(object);

                for (int i = 0 ; i < boolAry.length ; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("["+i+"] = "+boolAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if(eachField.getType().getName().equals("[B"))
            {
                byte[] byteAry = (byte[])eachField.get(object);

                for (int i = 0 ; i < byteAry.length ; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("["+i+"] = "+byteAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if(eachField.getType().getName().equals("[C"))
            {
                char[] charAry = (char[])eachField.get(object);

                for (int i = 0 ; i < charAry.length ; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("["+i+"] = "+charAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if(eachField.getType().getName().equals("[D"))
            {
                double[] dblAry = (double[])eachField.get(object);

                for (int i = 0 ; i < dblAry.length ; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("["+i+"] = "+dblAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if(eachField.getType().getName().equals("[F"))
            {
                float[] floatAry = (float[])eachField.get(object);

                for (int i = 0 ; i < floatAry.length ; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("["+i+"] = "+floatAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if(eachField.getType().getName().equals("[I"))
            {
                int[] intAry = (int[])eachField.get(object);

                for (int i = 0 ; i < intAry.length ; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("["+i+"] = "+intAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if(eachField.getType().getName().equals("[J"))
            {
                long[] longAry = (long[])eachField.get(object);

                for (int i = 0 ; i < longAry.length ; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("["+i+"] = "+longAry[i]);
                    childNodeList.add(childNode);
                }
            }
            else if(eachField.getType().getName().equals("[S"))
            {
                short[] shortAry = (short[])eachField.get(object);

                for (int i = 0 ; i < shortAry.length ; i++)
                {
                    DynaTreeNode childNode = new DynaTreeNode();
                    childNode.setTitle("["+i+"] = "+shortAry[i]);
                    childNodeList.add(childNode);
                }
            }
            retNode.setIsFolder(true);
            retNode.setChildren(childNodeList);
            eachField.setAccessible(false);
        }

        return retNode;
    }

    /**
     *
     * @param object
     * @param eachField
     * @return
     * @throws Exception
     * Parses OOB plain java Objects in java.* packages , except java.util.Collection and java.util.Map
     */
    public DynaTreeNode parseOOBObject(Object object, Field eachField)  throws Exception
    {
        DynaTreeNode dynaTreeNode = null;
        if(eachField != null)
        {
            eachField.setAccessible(true);
            dynaTreeNode = new DynaTreeNode();
            dynaTreeNode.setTitle(eachField.getName()+"("+eachField.getType().getName()+") = "+eachField.get(object)+"");

            eachField.setAccessible(false);
        }
        else
        {
            dynaTreeNode = new DynaTreeNode();
            dynaTreeNode.setTitle(object+"");

        }

        return dynaTreeNode;
    }


    public DynaTreeNode parseOOBObjectArrays(Object object, Field eachField)  throws Exception
    {
        DynaTreeNode retNode = null;
        if(eachField != null)
        {
            eachField.setAccessible(true);
            retNode = new DynaTreeNode();
            retNode.setTitle(eachField.getName());

            List<DynaTreeNode> childNodeList = new ArrayList<DynaTreeNode>();

            Object[] objAry = (Object[])eachField.get(object);
            for (int i = 0 ; i < objAry.length ; i++)
            {
                DynaTreeNode childNode = new DynaTreeNode();
                childNode.setTitle("["+i+"] = "+objAry[i]+"");
                childNodeList.add(childNode);
            }

            retNode.setIsFolder(true);

            retNode.setChildren(childNodeList);
            eachField.setAccessible(false);

        }

        return retNode;
    }

    public DynaTreeNode parseOOBObjectCollection(Object object, Field eachField)  throws Exception
    {
        DynaTreeNode retNode = null;
        if(eachField != null)
        {
            eachField.setAccessible(true);
            retNode = new DynaTreeNode();
            retNode.setTitle(eachField.getName()+"("+eachField.getGenericType()+")");

            List<DynaTreeNode> childNodeList = new ArrayList<DynaTreeNode>();

            if(Collection.class.isAssignableFrom(eachField.getType()))
            {
                Collection<?> coll = (Collection<?>)eachField.get(object);
                if(coll != null)
                {
                    Iterator<?> collItr = coll.iterator();
                    int index = 0;
                    while (collItr.hasNext())
                    {
                        Object eachItem = collItr.next();
                        String eachItemClassName = eachItem.getClass().getName();

                        if(eachItemClassName.startsWith("java"))
                        {
                            DynaTreeNode childNode = new DynaTreeNode();
                            childNode.setTitle("["+index+"] = "+eachItem+"");
                            childNodeList.add(childNode);
                            index++;
                        }
                    }
                    retNode.setIsFolder(true);
                    retNode.setChildren(childNodeList);
                }

            }
            eachField.setAccessible(false);
        }

        return retNode;
    }

    public DynaTreeNode parseOOBObjectMap(Object object, Field eachField)  throws Exception
    {
        DynaTreeNode retNode = null;
        if(eachField != null)
        {
            eachField.setAccessible(true);
            retNode = new DynaTreeNode();
            retNode.setTitle(eachField.getName());

            List<DynaTreeNode> childNodeList = new ArrayList<DynaTreeNode>();

            if(Map.class.isAssignableFrom(eachField.getType()))
            {
                Map<?,?> map = (Map<?,?>)eachField.get(object);

                if(map != null)
                {
                    Iterator<?> keyItr = map.keySet().iterator();
                    childNodeList = new ArrayList<DynaTreeNode>();

                    while (keyItr.hasNext())
                    {
                        Object eachKey = keyItr.next();
                        String eachKeyClassName = eachKey.getClass().getName();

                        if(eachKeyClassName.startsWith("java"))
                        {
                            Object eachVal = map.get(eachKey);
                            String eachValClassName = eachVal.getClass().getName();

                            if(eachValClassName.startsWith("java"))
                            {
                                DynaTreeNode childNode = new DynaTreeNode();
                                childNode.setTitle(eachKey+" = "+eachVal+"");
                                childNodeList.add(childNode) ;

                            }

                        }
                    }
                    retNode.setIsFolder(true);
                    retNode.setChildren(childNodeList);
                }
            }
            eachField.setAccessible(false);
        }
        return retNode;
    }

    public List<DynaTreeNode> parseCustomObject(Object msg)  throws Exception
    {
         List<DynaTreeNode> retList = new ArrayList<DynaTreeNode>();

        if(msg != null)
        {
            String objMsgClass =  msg.getClass().getName();
            AppLogger.info("parseCustomObject: objMsgClass = "+objMsgClass +", customObjectTopLevelPkg = "+customObjectTopLevelPkg);
            if(objMsgClass.startsWith(customObjectTopLevelPkg))
            {
                /* Need to check if Object has a customized super class. Private fields of super class also needs to be obtained.
                ie , if child class can access (get and set)  the super class private fields, then those fields also needs to be got.
                */
                Class superClass = msg.getClass().getSuperclass();
                if(superClass.getName().startsWith(customObjectTopLevelPkg))
                {
                    Field[] superFieldAry = msg.getClass().getSuperclass().getDeclaredFields();
                    retList = parseCustomObject2(superFieldAry, msg) ;
                }

                Field[] fieldAry = msg.getClass().getDeclaredFields();
                List<DynaTreeNode> retList1 = parseCustomObject2(fieldAry, msg) ;

                retList.addAll(retList1);
            }

        }

      return retList;

    }

    private List<DynaTreeNode> parseCustomObject2(Field[] fieldAry, Object msg) throws Exception
    {
           List<DynaTreeNode> retList = new ArrayList<DynaTreeNode>();

            for (Field eachField : fieldAry)
            {
                eachField.setAccessible(true);
                int modifier = eachField.getModifiers();
                if(modifier > 8) //public static final int
                {
                    continue;
                }
                String  eachFieldClassName =  eachField.getGenericType().toString();
                AppLogger.info("parseCustomObject: eachField name = "+eachField.getName()+" mod = "+modifier+", eachFieldClassName = "+eachFieldClassName);

                if(isPrimitive(eachFieldClassName))
                {
                    retList.add(parsePrimitiveField(msg, eachField));
                }
                else if(isPrimitiveArray(eachFieldClassName))
                {
                    retList.add(parsePrimitiveArrays(msg, eachField));
                }
                else if(isObject(eachFieldClassName))
                {
                    if(isOOBObject(eachField.getType()))
                    {
                        retList.add(parseOOBObject(msg, eachField));
                    }
                    else if(isCollection(eachField.getType()))
                    {
                            DynaTreeNode collectionHolderNode = parseCollection((Collection<?>) eachField.get(msg), eachField) ;
                            retList.add(collectionHolderNode);
                    }
                    else if(isMap(eachField.getType()))
                    {
                            DynaTreeNode mapHolderNode = parseMap((Map<?, ?>) eachField.get(msg), eachField) ;
                            retList.add(mapHolderNode);
                    }
                    else
                    {
                        List<DynaTreeNode> retList1 = parseCustomObject(eachField.get(msg)) ;
                        DynaTreeNode objHolderNode = new DynaTreeNode();
                        objHolderNode.setTitle(eachField.getName());
                        objHolderNode.setIsFolder(true);
                        objHolderNode.setChildren(retList1);
                        retList.add(objHolderNode);

                    }

            } //end - object check
            else if(isObjectArray(eachFieldClassName))
            {
                if(isOOBObjectArray(eachFieldClassName))
                {
                    retList.add(parseOOBObjectArrays(msg, eachField));
                }
                else
                {
                    List<DynaTreeNode> retList1 = parseCustomObjectArray((Object[] )eachField.get(msg)) ;
                    DynaTreeNode arrayHolderNode = new DynaTreeNode();
                    arrayHolderNode.setTitle(eachField.getName());
                    arrayHolderNode.setIsFolder(true);
                    arrayHolderNode.setChildren(retList1);

                    retList.add(arrayHolderNode);
                }

            }
            eachField.setAccessible(false);
        }

         return retList;
    }

    public List<DynaTreeNode> parseCustomObjectArray(Object[] objAry)  throws Exception
    {
        List<DynaTreeNode> retList = new ArrayList<DynaTreeNode>();

        if(objAry != null)
        {
            for (int i = 0 ; i < objAry.length ; i++)
            {
                DynaTreeNode arrayNode = new DynaTreeNode();
                arrayNode.setTitle("["+i+"]");

                Object eachAryItem = objAry[i];
                if (eachAryItem != null)
                {
                    List<DynaTreeNode> retList1 = parseCustomObject(eachAryItem) ;
                    arrayNode.setIsFolder(true);
                    arrayNode.setChildren(retList1);

                    retList.add(arrayNode);

                }

            }

        }

        return retList;
    }

    @Override
    public int hashCode() {
        return super.hashCode();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public MsgParsingUtil() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public List<DynaTreeNode> parseInnerCollection(Collection<?> objColl) throws Exception
    {
        List<DynaTreeNode> retList = new ArrayList<DynaTreeNode>();
        if(objColl != null)
        {
            int collIdx = 0;
            Iterator<?> collItr = objColl.iterator();
            while(collItr.hasNext())
            {
                DynaTreeNode collNode = new DynaTreeNode();
                collNode.setTitle("["+collIdx+"]");
                collNode.setIsFolder(true);
                collIdx++;

                List<DynaTreeNode> eachCollNodeDetails = new ArrayList<DynaTreeNode>();
                Object collItem = collItr.next();
                if(collItem != null)
                {
                    if(isOOBObject(collItem.getClass()))
                    {
                        DynaTreeNode retNode1 = parseOOBObject(collItem, null);
                        eachCollNodeDetails.add(retNode1);
                    }
                    else if(isCollection(collItem.getClass()))
                    {
                        List<DynaTreeNode> retNode1 = parseInnerCollection((Collection)collItem);
                        eachCollNodeDetails.addAll(retNode1);
                    }
                    else if(isMap(collItem.getClass()))
                    {
                        List<DynaTreeNode> retNode1 = parseInnerMap((Map) collItem);
                        eachCollNodeDetails.addAll(retNode1);
                    }
                    else
                    {
                        List<DynaTreeNode> retList1 = parseCustomObject(collItem) ;
                        eachCollNodeDetails.addAll(retList1);
                    }
                }
                collNode.setChildren(eachCollNodeDetails);
                retList.add(collNode);

            }

        }

        return retList;
    }

    public DynaTreeNode parseCollection(Collection<?> objColl, Field collectionField)  throws Exception
    {
        String collItemCN = null;

        DynaTreeNode retNode = new DynaTreeNode();
        retNode.setIsFolder(true);

        if(objColl != null)
        {
            List<DynaTreeNode> collNodeList = new ArrayList<DynaTreeNode>();
            Iterator<?> collItr = objColl.iterator();
            int index = 0;
            while(collItr.hasNext())
            {
                DynaTreeNode collNode = new DynaTreeNode();
                collNode.setTitle("["+index+"]");
                collNode.setIsFolder(true);


                List<DynaTreeNode> retList = new ArrayList<DynaTreeNode>();

                index++;
                Object collItem = collItr.next();
                if(collItem != null)
                {
                    if(collItemCN == null)
                    {
                        collItemCN = collItem.getClass().getName();
                    }
                    if(isOOBObject(collItem.getClass()))
                    {
                        DynaTreeNode retNode1 = parseOOBObject(collItem, null);
                        retList.add(retNode1);
                    }
                    else if(isCollection(collItem.getClass()))
                    {
                           List<DynaTreeNode> retNode1 = parseInnerCollection((Collection)collItem);
                           retList.addAll(retNode1);
                    }
                    else if(isMap(collItem.getClass()))
                    {
                        List<DynaTreeNode> retNode1 = parseInnerMap((Map) collItem);
                        retList.addAll(retNode1);
                    }
                    else
                    {
                        List<DynaTreeNode> retList1 = parseCustomObject(collItem) ;
                        /*DynaTreeNode retNode1 = new DynaTreeNode();
                        retNode1.setTitle(collItem.getClass().getName());
                        retNode1.setChildren(retList1);*/
                        retList.addAll(retList1);
                    }

                }

                collNode.setChildren(retList);
                collNodeList.add(collNode);

            }
            if(collectionField != null)
            {
                retNode.setTitle(collectionField.getName()+"("+objColl.getClass().getName()+"["+collItemCN+"])");
            }
            else
            {
                retNode.setTitle("("+collItemCN+")");
            }
            retNode.setChildren(collNodeList);

        }
        if(retNode == null)
        {

            retNode = new DynaTreeNode();
            retNode.setTitle(collectionField.getName()+"("+collectionField.getType().getName()+")");

        }
        return retNode;
    }

    public List<DynaTreeNode> parseInnerMap(Map objMap) throws Exception
    {
        List<DynaTreeNode> retList = new ArrayList<DynaTreeNode>();
        if(objMap != null)
        {
            Iterator<?> mapItr = objMap.keySet().iterator();

            while(mapItr.hasNext())
            {
                List<DynaTreeNode> eachMapDetails = new ArrayList<DynaTreeNode>();
                Object mapKey1 = mapItr.next();
                if(isOOBObject(mapKey1.getClass()))
                {
                    String mapKey = (String)(mapKey1 + "");
                    DynaTreeNode mapNode = new DynaTreeNode();
                    mapNode.setTitle(mapKey);
                    mapNode.setIsFolder(true);

                    Object mapVal = objMap.get(mapKey);

                    if(mapVal != null)
                    {
                        if(isOOBObject(mapVal.getClass()))
                        {
                            DynaTreeNode retNode = parseOOBObject(mapVal, null) ;
                            eachMapDetails.add(retNode);
                        }
                        else if(isCollection(mapVal.getClass()))
                        {
                            List<DynaTreeNode> retNode =  parseInnerCollection((Collection) mapVal);
                            eachMapDetails.addAll(retNode);
                        }
                        else if(isMap(mapVal.getClass()))
                        {
                            List<DynaTreeNode> retNode =  parseInnerMap((Map)mapVal);
                            eachMapDetails.addAll(retNode);
                        }
                        else
                        {
                            List<DynaTreeNode> retList1 = parseCustomObject(mapVal) ;
                            eachMapDetails.addAll(retList1);
                        }


                    }

                    mapNode.setChildren(eachMapDetails);
                    retList.add(mapNode);

                }

            }
        }
        return retList;
    }

    public DynaTreeNode parseMap(Map objMap, Field eachField)  throws Exception
    {
        String mapCN = null;
        DynaTreeNode mapHolderNode = null;

        if(objMap != null)
        {
            mapHolderNode = new DynaTreeNode();
            List<DynaTreeNode> mapNodeList = new ArrayList<DynaTreeNode>();

            Iterator<?> mapItr = objMap.keySet().iterator();

            while(mapItr.hasNext())
            {
                List<DynaTreeNode> retList = new ArrayList<DynaTreeNode>();

                Object mapKey1 = mapItr.next();
                if(isOOBObject(mapKey1.getClass()))
                {
                    String mapKey = (String)(mapKey1 + "");
                    DynaTreeNode mapNode = new DynaTreeNode();
                    mapNode.setTitle(mapKey);
                    mapNode.setIsFolder(true);

                    Object mapVal = objMap.get(mapKey);

                    if(mapVal != null)
                    {
                        if(mapCN == null)
                        {
                            mapCN = mapKey1.getClass().getName() +", "+ mapVal.getClass().getName();
                        }

                        if(isOOBObject(mapVal.getClass()))
                        {
                             DynaTreeNode retNode = parseOOBObject(mapVal, null) ;
                             retList.add(retNode);
                        }
                        else if(isCollection(mapVal.getClass()))
                        {
                            List<DynaTreeNode> retNode =  parseInnerCollection((Collection) mapVal);
                            retList.addAll(retNode);
                        }
                        else if(isMap(mapVal.getClass()))
                        {
                            List<DynaTreeNode> retNode =  parseInnerMap((Map) mapVal);
                            retList.addAll(retNode);
                        }
                        else
                        {
                            List<DynaTreeNode> retList1 = parseCustomObject(mapVal) ;
                            retList.addAll(retList1);
                        }

                    }
                    mapNode.setChildren(retList);
                    mapNodeList.add(mapNode);

                }

            }
            mapHolderNode.setTitle(eachField.getName()+"("+objMap.getClass().getName()+"["+mapCN+"])");
            mapHolderNode.setIsFolder(true);
            mapHolderNode.setChildren(mapNodeList);

        }
        if(mapHolderNode == null)
        {
            mapHolderNode = new DynaTreeNode();
            mapHolderNode.setTitle(eachField.getName()+"("+eachField.getType().getName()+")");

        }
        return mapHolderNode;
    }
}
