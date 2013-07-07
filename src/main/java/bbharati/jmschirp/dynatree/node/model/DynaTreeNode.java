package bbharati.jmschirp.dynatree.node.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 20/04/13
 * Time: 8:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class DynaTreeNode {

    private String title;
    private boolean isFolder;
    private String key;
    List<DynaTreeNode> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setIsFolder(boolean folder) {
        isFolder = folder;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<DynaTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<DynaTreeNode> children) {
        this.children = children;
    }
}
