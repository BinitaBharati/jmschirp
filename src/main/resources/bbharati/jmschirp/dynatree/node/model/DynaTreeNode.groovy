package bbharati.jmschirp.dynatree.node.model

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 20/04/13
 * Time: 8:10 PM
 * To change this template use File | Settings | File Templates.
 */
class DynaTreeNode {

    def title
    def isFolder
    def key
    def children
    def expand //Boolean, used to specify if the node should be initially expanded or not
    def isLazy //To enable lazy loading of a node
    def generateIds;

    /* Add additional data attributes associated to each node
     Uses html 5 data attribute
     http://stackoverflow.com/questions/6012734/dynatree-where-can-i-store-additional-info-in-each-node
     http://api.jquery.com/data/
    *
    */

    def jmsVendorDetailInfo
    /** Another additional attribute to specify the node type.
     * 0 - Root Node
     * 1 - JMS ConnectionNode
     * 2 - Queues Aggregate Node
     * 3 - Each Queue node underneath 'Queues Aggregate Node'*/
    def nodeType
}
