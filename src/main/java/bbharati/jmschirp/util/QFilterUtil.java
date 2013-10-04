package bbharati.jmschirp.util;

import java.lang.reflect.Field;
import java.util.*;

import bbharati.jmschirp.dynatree.node.model.DynaTreeNode;
import bbharati.jmschirp.queue.search.input.FilterQueueInput;
import org.codehaus.groovy.grails.web.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * Author: binita.bharati@gmail.com
 * Date: 11/06/13
 * Time: 8:52 AM
 * To change this template use File | Settings | File Templates.
 *
 * This class is responsible for returning the msg after applying search criteria
 * to the queue.
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
public class QFilterUtil {


  private JSONObject searchCriteria;

    public List<DynaTreeNode> parseMainObject(Object msg)
    {
        return null;
    }



}
