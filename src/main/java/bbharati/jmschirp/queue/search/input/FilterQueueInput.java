package bbharati.jmschirp.queue.search.input;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 29/09/13
 * Time: 1:13 PM
 * To change this template use File | Settings | File Templates.
 * This class can be used to store teh searchCriteria sent by the client.
 */
public class FilterQueueInput {

    private String className;
    List<EachFieldCriteria> eachFieldCriteria;

    public enum CriteriaEnum {
        EQUAL(0),
        NOT_EQUAL(1);

        private int id;

        private CriteriaEnum(int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return id;
        }


        public static CriteriaEnum valueOf(int statusId)
        {
            CriteriaEnum[] values = values();
            for (CriteriaEnum status : values)
            {
                if (status.id == statusId)
                {
                    return status;
                }
            }

            return null;
        }


    }

    class EachFieldCriteria
    {
        private String fieldName;

        private CriteriaEnum predicate;/* EQUAL, NOT_EQUAL . Predicates may be joined by conditional operators like
        AND, OR. Currently, design assumes only AND operator. */

         public EachFieldCriteria( String fieldName, CriteriaEnum predicate)
         {
              this.fieldName = fieldName;
              this.predicate =  predicate;
         }
    }


}
