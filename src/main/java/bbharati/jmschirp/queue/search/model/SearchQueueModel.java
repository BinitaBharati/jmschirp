package bbharati.jmschirp.queue.search.model;

import bbharati.jmschirp.queue.search.model.EachFieldDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 24/08/13
 * Time: 11:34 PM
 * To change this template use File | Settings | File Templates.
 * This model is used to represent the ObjMsg meta-data in the queue.
 */
public class SearchQueueModel {

    private String className;

    private List<EachFieldDescriptor> fieldDescList;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<EachFieldDescriptor> getFieldDescList() {
        return fieldDescList;
    }

    public void setFieldDescList(List<EachFieldDescriptor> fieldDescList) {
        this.fieldDescList = fieldDescList;
    }

    public void addFieldDescriptor(EachFieldDescriptor fieldDescriptor)
    {
            if(fieldDescList == null || fieldDescList.size() == 0)
            {
                fieldDescList = new ArrayList<EachFieldDescriptor>();
            }
        fieldDescList.add(fieldDescriptor);
    }

    @Override
    public boolean equals(Object obj)
    {
            if(obj instanceof  SearchQueueModel)
            {
                SearchQueueModel thisObj = (SearchQueueModel)obj;
                if(thisObj.getClassName().equals(this.getClassName()))
                {
                    return true;
                }

            }
            return false;
    }
    @Override
    public int hashCode()
    {
        return 1;
    }


}
