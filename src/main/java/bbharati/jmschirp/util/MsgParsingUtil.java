package bbharati.jmschirp.util;

import bbharati.jmschirp.dynatree.node.model.DynaTreeNode;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Author: binita.bharati@gmail.com
 * Date: 11/06/13
 * Time: 8:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class MsgParsingUtil {

    private static List<String> oobCollectionClassNames ;

    static {
        oobCollectionClassNames = new ArrayList<String>();
        oobCollectionClassNames.add("");
    }

    /**
     *
     * @param eachFieldClassName
     * @return
     * Primitive class names do not have a dot(.)
     * Primitive arrays have a [ , and no dot(.)  character
     *
     */
    public static boolean isPrimitive(String eachFieldClassName)
    {
        if(eachFieldClassName.indexOf(".") == -1 && eachFieldClassName.indexOf("[") == -1)
        {
            //a pure primitive type
            return  true;
        }
        return false;

    }

    /**
     *
     * @param eachFieldClassName
     * @return
     * Primitive arrays have a [ , and no dot(.)  character
     */
    public static boolean isPrimitiveArray(String eachFieldClassName)
    {
        if(eachFieldClassName.indexOf(".") == -1 && eachFieldClassName.indexOf("[") != -1)
        {
            //a pure primitive array
            return  true;
        }
        return false;
    }

    /**
     *
     * @param eachFieldClassName
     * @return
     */
    public static boolean isObject(String eachFieldClassName)
    {
        if(eachFieldClassName.indexOf(".") != -1 && eachFieldClassName.indexOf("[L") == -1)
        {
             return true;
        }
        return false;
    }

    public static boolean isOOBObject(String eachFieldClassName)
    {
        if(eachFieldClassName.indexOf(".") != -1 && eachFieldClassName.indexOf("[L") == -1)
        {
            if(eachFieldClassName.indexOf("java.lang.Character") != -1 ||
                    eachFieldClassName.indexOf("java.lang.String") != -1 ||
                    eachFieldClassName.indexOf("java.lang.Integer") != -1 ||
                    eachFieldClassName.indexOf("java.lang.Boolean") != -1 ||
                    eachFieldClassName.indexOf("java.lang.Byte") != -1 ||
                    eachFieldClassName.indexOf("java.lang.Long") != -1 ||
                    eachFieldClassName.indexOf("java.lang.Float") != -1 ||
                    eachFieldClassName.indexOf("java.lang.Double") != -1  ||
                    eachFieldClassName.indexOf("java.lang.Short") != -1
                    )
            {
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
    public static boolean isObjectArray(String eachFieldClassName)
    {
        if(eachFieldClassName.indexOf(".") != -1 && eachFieldClassName.indexOf("[L") != -1)
        {
            //a object array
            return  true;
        }
        return false;
    }

    /**
     *
     * @param eachFieldClassName
     * @return
     * All Object arrays start with [L
     */
    public static boolean isOOBObjectArray(String eachFieldClassName)
    {
        if(eachFieldClassName.indexOf(".") != -1 && eachFieldClassName.indexOf("[L") != -1)
        {
            if(eachFieldClassName.indexOf("java.lang.Character") != -1 ||
                    eachFieldClassName.indexOf("java.lang.String") != -1 ||
                    eachFieldClassName.indexOf("java.lang.Integer") != -1 ||
                    eachFieldClassName.indexOf("java.lang.Boolean") != -1 ||
                    eachFieldClassName.indexOf("java.lang.Byte") != -1 ||
                    eachFieldClassName.indexOf("java.lang.Long") != -1 ||
                    eachFieldClassName.indexOf("java.lang.Float") != -1 ||
                    eachFieldClassName.indexOf("java.lang.Double") != -1  ||
                    eachFieldClassName.indexOf("java.lang.Short") != -1
                    )
            {
                return  true;
            }


        }
        return false;
    }

    /**
     *
     * @param eachFieldClass
     * @return
     */
    public static boolean isCollection(Class eachFieldClass)
    {
        if(Collection.class.isAssignableFrom(eachFieldClass))
        {
              return true;
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
    public static boolean isOOBObjectCollection(Object object, Field eachField) throws Exception
    {
        if(Collection.class.isAssignableFrom(eachField.getType()))
        {
            Collection<?> coll = (Collection<?>)eachField.get(object);

            Iterator<?> collItr = coll.iterator();

            while (collItr.hasNext())
            {
                Object eachItem = collItr.next();
                String eachItemClassName = eachItem.getClass().getName();

                if(eachItemClassName.equals("java.lang.String") || eachItemClassName.equals("java.lang.Character")
                        || eachItemClassName.equals("java.lang.Integer") || eachItemClassName.equals("java.lang.Boolean")
                            || eachItemClassName.equals("java.lang.Byte") || eachItemClassName.equals("java.lang.Long")
                                || eachItemClassName.equals("java.lang.Float") || eachItemClassName.equals("java.lang.Double")
                                    || eachItemClassName.equals("java.lang.Short"))
                {
                    return true;//assuming all items in collection are of same type;
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
    public static boolean isMap(Class eachFieldClass)
    {
        if(Map.class.isAssignableFrom(eachFieldClass))
        {
            return true;
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
    public static boolean isOOBObjectMap(Object object, Field eachField) throws Exception
    {
        if(Map.class.isAssignableFrom(eachField.getType()))
        {
            Map<?,?> map = (Map<?,?>)eachField.get(object);

            Iterator<?> mapItr = map.keySet().iterator();

            while (mapItr.hasNext())
            {
                Object eachKey = mapItr.next();
                String eachKeyClassName = eachKey.getClass().getName();

                if(eachKeyClassName.equals("java.lang.String") || eachKeyClassName.equals("java.lang.Character")
                        || eachKeyClassName.equals("java.lang.Integer") || eachKeyClassName.equals("java.lang.Boolean")
                        || eachKeyClassName.equals("java.lang.Byte") || eachKeyClassName.equals("java.lang.Long")
                        || eachKeyClassName.equals("java.lang.Float") || eachKeyClassName.equals("java.lang.Double")
                        || eachKeyClassName.equals("java.lang.Short"))
                {
                    Object mapVal = map.get(eachKey);

                    String eachValClassName = mapVal.getClass().getName();

                    if(eachValClassName.equals("java.lang.String") || eachValClassName.equals("java.lang.Character")
                            || eachValClassName.equals("java.lang.Integer") || eachValClassName.equals("java.lang.Boolean")
                            || eachValClassName.equals("java.lang.Byte") || eachValClassName.equals("java.lang.Long")
                            || eachValClassName.equals("java.lang.Float") || eachValClassName.equals("java.lang.Double")
                            || eachValClassName.equals("java.lang.Short"))
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
     * @param object
     * @param eachField
     * @return
     * @throws Exception
     */
    public static DynaTreeNode parsePrimitiveField(Object object, Field eachField)  throws Exception
    {
        DynaTreeNode dynaTreeNode = new DynaTreeNode();

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
        return dynaTreeNode;
    }


    /**
     *
     * @param object
     * @param eachField
     * @return
     * @throws Exception
     */
    public static DynaTreeNode parsePrimitiveArrays(Object object, Field eachField)  throws Exception
    {
        eachField.setAccessible(true);

        DynaTreeNode retNode = new DynaTreeNode();
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
        return retNode;
    }


    public static DynaTreeNode parseOOBObject(Object object, Field eachField)  throws Exception
    {
        eachField.setAccessible(true);
        DynaTreeNode dynaTreeNode = new DynaTreeNode();

        if(eachField.getType().getName().equals("java.lang.Boolean"))
        {
            Boolean val = (Boolean)eachField.get(object)  ;
            dynaTreeNode.setTitle(eachField.getName()+" = "+val);
        }
        else if(eachField.getType().getName().equals("java.lang.Byte"))
        {
            Byte val = (Byte)eachField.get(object)  ;
            dynaTreeNode.setTitle(eachField.getName()+" = "+val);
        }
        else if(eachField.getType().getName().equals("java.lang.Character"))
        {
            Character val = (Character)eachField.get(object)  ;
            dynaTreeNode.setTitle(eachField.getName()+" = "+val);
        }
        else if(eachField.getType().getName().equals("java.lang.Double"))
        {
            Double val = (Double)eachField.get(object)  ;
            dynaTreeNode.setTitle(eachField.getName()+" = "+val);
        }
        else if(eachField.getType().getName().equals("java.lang.Float"))
        {
            Float val = (Float)eachField.get(object)  ;
            dynaTreeNode.setTitle(eachField.getName()+" = "+val);
        }
        else if(eachField.getType().getName().equals("java.lang.Integer"))
        {
            Integer val = (Integer)eachField.get(object)  ;
            dynaTreeNode.setTitle(eachField.getName()+" = "+val);
        }
        else if(eachField.getType().getName().equals("java.lang.Long"))
        {
            Long val = (Long)eachField.get(object)  ;
            dynaTreeNode.setTitle(eachField.getName()+" = "+val);
        }
        else if(eachField.getType().getName().equals("java.lang.Short"))
        {
            Short val = (Short)eachField.get(object)  ;
            dynaTreeNode.setTitle(eachField.getName()+" = "+val);
        }
        else if(eachField.getType().getName().equals("java.lang.String"))
        {
            String val = (String)eachField.get(object)  ;
            dynaTreeNode.setTitle(eachField.getName()+" = "+val);
        }
        eachField.setAccessible(false);
        return dynaTreeNode;
    }


    public static DynaTreeNode parseOOBObjectArrays(Object object, Field eachField)  throws Exception
    {
        eachField.setAccessible(true);
        DynaTreeNode retNode = new DynaTreeNode();
        retNode.setTitle(eachField.getName());

        List<DynaTreeNode> childNodeList = new ArrayList<DynaTreeNode>();

        if(eachField.getType().getName().indexOf("java.lang.Boolean") != -1)
        {
            Boolean[] boolAry = (Boolean[])eachField.get(object);

            for (int i = 0 ; i < boolAry.length ; i++)
            {
                DynaTreeNode childNode = new DynaTreeNode();
                childNode.setTitle("["+i+"] = "+boolAry[i]);
                childNodeList.add(childNode);
            }
        }
        else if(eachField.getType().getName().indexOf("java.lang.Byte") != -1)
        {
            Byte[] byteAry = (Byte[])eachField.get(object);

            for (int i = 0 ; i < byteAry.length ; i++)
            {
                DynaTreeNode childNode = new DynaTreeNode();
                childNode.setTitle("["+i+"] = "+byteAry[i]);
                childNodeList.add(childNode);
            }
        }
        else if(eachField.getType().getName().indexOf("java.lang.Character") != -1)
        {
            Character[] charAry = (Character[])eachField.get(object);

            for (int i = 0 ; i < charAry.length ; i++)
            {
                DynaTreeNode childNode = new DynaTreeNode();
                childNode.setTitle("["+i+"] = "+charAry[i]);
                childNodeList.add(childNode);
            }
        }
        else if(eachField.getType().getName().indexOf("java.lang.Double") != -1)
        {
            Double[] dblAry = (Double[])eachField.get(object);

            for (int i = 0 ; i < dblAry.length ; i++)
            {
                DynaTreeNode childNode = new DynaTreeNode();
                childNode.setTitle("["+i+"] = "+dblAry[i]);
                childNodeList.add(childNode);
            }
        }
        else if(eachField.getType().getName().indexOf("java.lang.Float") != -1)
        {
            Float[] floatAry = (Float[])eachField.get(object);

            for (int i = 0 ; i < floatAry.length ; i++)
            {
                DynaTreeNode childNode = new DynaTreeNode();
                childNode.setTitle("["+i+"] = "+floatAry[i]);
                childNodeList.add(childNode);
            }
        }
        else if(eachField.getType().getName().indexOf("java.lang.Integer") != -1)
        {
            Integer[] intAry = (Integer[])eachField.get(object);

            for (int i = 0 ; i < intAry.length ; i++)
            {
                DynaTreeNode childNode = new DynaTreeNode();
                childNode.setTitle("["+i+"] = "+intAry[i]);
                childNodeList.add(childNode);
            }
        }
        else if(eachField.getType().getName().indexOf("java.lang.Long") != -1)
        {
            Long[] longAry = (Long[])eachField.get(object);

            for (int i = 0 ; i < longAry.length ; i++)
            {
                DynaTreeNode childNode = new DynaTreeNode();
                childNode.setTitle("["+i+"] = "+longAry[i]);
                childNodeList.add(childNode);
            }
        }
        else if(eachField.getType().getName().indexOf("java.lang.Short") != -1)
        {
            Short[] shortAry = (Short[])eachField.get(object);

            for (int i = 0 ; i < shortAry.length ; i++)
            {
                DynaTreeNode childNode = new DynaTreeNode();
                childNode.setTitle("["+i+"] = "+shortAry[i]);
                childNodeList.add(childNode);
            }
        }
        else if(eachField.getType().getName().indexOf("java.lang.String") != -1)
        {
            String[] shortAry = (String[])eachField.get(object);

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

        return retNode;
    }

    public static DynaTreeNode parseOOBObjectCollection(Object object, Field eachField)  throws Exception
    {
        eachField.setAccessible(true);
        DynaTreeNode retNode = new DynaTreeNode();
        retNode.setTitle(eachField.getName());

        List<DynaTreeNode> childNodeList = new ArrayList<DynaTreeNode>();

        if(Collection.class.isAssignableFrom(eachField.getType()))
        {
            Collection<?> coll = (Collection<?>)eachField.get(object);

            Iterator<?> collItr = coll.iterator();
            int index = 0;
            while (collItr.hasNext())
            {
                Object eachItem = collItr.next();
                String eachItemClassName = eachItem.getClass().getName();

                if(eachItemClassName.equals("java.lang.String") || eachItemClassName.equals("java.lang.Character")
                        || eachItemClassName.equals("java.lang.Integer") || eachItemClassName.equals("java.lang.Boolean")
                        || eachItemClassName.equals("java.lang.Byte") || eachItemClassName.equals("java.lang.Long")
                        || eachItemClassName.equals("java.lang.Float") || eachItemClassName.equals("java.lang.Double")
                        || eachItemClassName.equals("java.lang.Short"))
                {
                      DynaTreeNode childNode = new DynaTreeNode();
                      childNode.setTitle("["+index+"] = "+eachItem);
                      childNodeList.add(childNode);
                      index++;
                }
            }
            retNode.setIsFolder(true);
            retNode.setChildren(childNodeList);
        }
        eachField.setAccessible(false);
        return retNode;
    }

    public static DynaTreeNode parseOOBObjectMap(Object object, Field eachField)  throws Exception
    {
        eachField.setAccessible(true);
        DynaTreeNode retNode = new DynaTreeNode();
        retNode.setTitle(eachField.getName());

        List<DynaTreeNode> childNodeList = new ArrayList<DynaTreeNode>();

        if(Map.class.isAssignableFrom(eachField.getType()))
        {
            Map<?,?> map = (Map<?,?>)eachField.get(object);

            Iterator<?> keyItr = map.keySet().iterator();
            childNodeList = new ArrayList<DynaTreeNode>();

            while (keyItr.hasNext())
            {
                Object eachKey = keyItr.next();
                String eachKeyClassName = eachKey.getClass().getName();

                if(eachKeyClassName.equals("java.lang.String") || eachKeyClassName.equals("java.lang.Character")
                        || eachKeyClassName.equals("java.lang.Integer") || eachKeyClassName.equals("java.lang.Boolean")
                        || eachKeyClassName.equals("java.lang.Byte") || eachKeyClassName.equals("java.lang.Long")
                        || eachKeyClassName.equals("java.lang.Float") || eachKeyClassName.equals("java.lang.Double")
                        || eachKeyClassName.equals("java.lang.Short"))
                {
                    Object eachVal = map.get(eachKey);
                    String eachValClassName = eachVal.getClass().getName();

                    if(eachValClassName.equals("java.lang.String") || eachValClassName.equals("java.lang.Character")
                            || eachValClassName.equals("java.lang.Integer") || eachValClassName.equals("java.lang.Boolean")
                            || eachValClassName.equals("java.lang.Byte") || eachValClassName.equals("java.lang.Long")
                            || eachValClassName.equals("java.lang.Float") || eachValClassName.equals("java.lang.Double")
                            || eachValClassName.equals("java.lang.Short"))
                    {
                        DynaTreeNode childNode = new DynaTreeNode();
                        childNode.setTitle(eachKey+" = "+eachVal);
                        childNodeList.add(childNode) ;

                    }


                }
            }
            retNode.setIsFolder(true);
            retNode.setChildren(childNodeList);
        }
        eachField.setAccessible(false);
        return retNode;
    }

    public static List<DynaTreeNode> parseCustomObject(Object msg)  throws Exception
    {
         List<DynaTreeNode> retList = new ArrayList<DynaTreeNode>();

        if(msg != null)
        {
            String objMsgClass =  msg.getClass().getName();

            Field[] fieldAry = msg.getClass().getDeclaredFields();
            for (Field eachField : fieldAry)
            {
                eachField.setAccessible(true);
                int modifier = eachField.getModifiers();
                /*if(modifier == 26) //public static final int
                {
                    continue;
                }*/
                String  eachFieldClassName =  eachField.getType().getName();

                if(MsgParsingUtil.isPrimitive(eachFieldClassName))
                {
                    retList.add(MsgParsingUtil.parsePrimitiveField(msg, eachField));
                }

                else if(MsgParsingUtil.isPrimitiveArray(eachFieldClassName))
                {
                    retList.add(MsgParsingUtil.parsePrimitiveArrays(msg, eachField));
                }

                else if(MsgParsingUtil.isObject(eachFieldClassName))
                {
                    if(MsgParsingUtil.isOOBObject(eachFieldClassName))
                    {
                        retList.add(MsgParsingUtil.parseOOBObject(msg, eachField));
                    }
                    else if(MsgParsingUtil.isCollection(eachField.getType()))
                    {
                        if(MsgParsingUtil.isOOBObjectCollection(msg, eachField))
                        {
                            retList.add(MsgParsingUtil.parseOOBObjectCollection(msg, eachField));
                        }
                        else
                        {
                            List<DynaTreeNode> retList1 = parseCustomObjectCollection((Collection<?> )eachField.get(msg)) ;
                            DynaTreeNode collectionHolderNode = new DynaTreeNode();
                            collectionHolderNode.setTitle(eachField.getName());
                            collectionHolderNode.setIsFolder(true);
                            collectionHolderNode.setChildren(retList1);

                            retList.add(collectionHolderNode);
                        }
                    }
                    else if(MsgParsingUtil.isMap(eachField.getType()))
                    {
                        if(MsgParsingUtil.isOOBObjectMap(msg, eachField))
                        {
                            retList.add(MsgParsingUtil.parseOOBObjectMap(msg, eachField));
                        }
                        else
                        {
                            List<DynaTreeNode> retList1 = parseCustomObjectMap((Map<?,?> )eachField.get(msg)) ;
                            DynaTreeNode mapHolderNode = new DynaTreeNode();
                            mapHolderNode.setTitle(eachField.getName());
                            mapHolderNode.setIsFolder(true);
                            mapHolderNode.setChildren(retList1);

                            retList.add(mapHolderNode);
                        }


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
                else if(MsgParsingUtil.isObjectArray(eachFieldClassName))
                {
                    if(MsgParsingUtil.isOOBObjectArray(eachFieldClassName))
                    {
                        retList.add(MsgParsingUtil.parseOOBObjectArrays(msg, eachField));
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
        }



      return retList;

    }

    public static List<DynaTreeNode> parseCustomObjectArray(Object[] objAry)  throws Exception
    {
        List<DynaTreeNode> retList = new ArrayList<DynaTreeNode>();

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

        return retList;
    }

    public static List<DynaTreeNode> parseCustomObjectCollection(Collection<?> objColl)  throws Exception
    {
        List<DynaTreeNode> retList = new ArrayList<DynaTreeNode>();

        Iterator<?> collItr = objColl.iterator();
        int index = 0;
        while(collItr.hasNext())
        {
            DynaTreeNode collNode = new DynaTreeNode();
            collNode.setTitle("["+index+"]");
            index++;
            Object collItem = collItr.next();
            if(collItem != null)
            {
                List<DynaTreeNode> retList1 = parseCustomObject(collItem) ;
                collNode.setIsFolder(true);
                collNode.setChildren(retList1);

                retList.add(collNode);
            }

        }

        return retList;
    }

    public static List<DynaTreeNode> parseCustomObjectMap(Map objMap)  throws Exception
    {
        List<DynaTreeNode> retList = new ArrayList<DynaTreeNode>();

        Iterator<?> mapItr = objMap.keySet().iterator();

        while(mapItr.hasNext())
        {
            String mapKey = (String)mapItr.next();
            DynaTreeNode mapNode = new DynaTreeNode();
            mapNode.setTitle(mapKey);

            Object mapVal = objMap.get(mapKey);

            if(mapVal != null)
            {
                List<DynaTreeNode> retList1 = parseCustomObject(mapVal) ;
                mapNode.setIsFolder(true);
                mapNode.setChildren(retList1);

                retList.add(mapNode);

            }

        }

        return retList;
    }



}
