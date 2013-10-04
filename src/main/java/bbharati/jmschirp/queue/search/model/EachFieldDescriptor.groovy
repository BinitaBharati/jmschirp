package bbharati.jmschirp.queue.search.model;

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 25/08/13
 * Time: 11:33 PM
 * To change this template use File | Settings | File Templates.
 * Used by SearchQueueModel. This model is used to represent the ObjMsg meta-data in the queue.
 */
class EachFieldDescriptor
{
    private String fieldName;

    private String fieldClassName;

    private boolean hasMultipleValues;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldClassName() {
    return fieldClassName
}

    public void setFieldClassName(String fieldClassName) {
        this.fieldClassName = fieldClassName
    }

    public boolean getHasMultipleValues() {
        return hasMultipleValues
    }

    public void setHasMultipleValues(boolean hasMultipleValues) {
        this.hasMultipleValues = hasMultipleValues
    }
}