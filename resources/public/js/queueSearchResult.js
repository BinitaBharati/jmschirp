$(document).ready(function(){

  HANDLEBAR_TEMPLATES = {
                progressBarTemplate :   Handlebars.compile($("#progressBarTemplate-SR").html()) 

     };


  $("#viewSearchDetails-SR").click(function(){
  });


   
});

   function initProgressBar(tabSpecificProgressBarId)
 {
      $("#"+tabSpecificProgressBarId).append(HANDLEBAR_TEMPLATES.progressBarTemplate()); 
 }


/* The below function - loadSearchCriteria cant be inside document.ready section of this script, as this is being invoked from jquery on ready within 
 * the queueSearchResult.html, and at that time, document.onReady of js is not invoked. Prolly, becacuse entire documemt isnt loaded yet.
 * jquery onReady defined inside queueSearchResult.html gets invoked as it is embedded javacsript. 
 * PS :  Groovlet is somehow converting the input jsonStr to a object while invoking this function.
 *  searchCriteriaMap format = {cn1 : [{fn1 : "fv1"}, {fn2 : "fv2"}], cn2 :  [{fn1 : "fv1"}, {fn2 : "fv2"}]}; */

 function loadSearchCriteria(searchCriteriaMap, HANDLEBAR_TEMPLATES)
{
        console.log('loadSearchCriteria: searchCriteriaMap = '+searchCriteriaMap);
        //var jsonObjMap = JSON.parse(searchCriteriaJsonStr); --  Groovlet is somehow converting the input jsonStr to a object while invoking this function
       
        //Iterate through map in javscript. Dont know map keys yet!
        var tmpClassIndex = 0;
        for(var key in searchCriteriaMap)
        {
           console.log('loadSearchCriteria: className =' +key);
           $("#searchCriteriaDisplayDiv").append(HANDLEBAR_TEMPLATES.searchCriteriaResultClassLevelTemplate({index : tmpClassIndex, className : key}));

                var fieldMapAry = searchCriteriaMap[key];
                console.log('loadSearchCriteria: fieldMapAry =' +fieldMapAry);

                for(var j= 0 ; j < fieldMapAry.length ; j++)
                {
                    var eachFieldDetailMap = fieldMapAry[j];
                    for(var fieldKey in eachFieldDetailMap )
                    {
                        console.log('loadSearchCriteria : eachFieldKey = '+fieldKey);
                        var eachFieldVal = eachFieldDetailMap[fieldKey];
                        console.log('loadSearchCriteria: eachFieldVal = '+eachFieldVal);

                        $("#searchCriteriaResultClassLevelDiv-"+tmpClassIndex).append(HANDLEBAR_TEMPLATES.searchCriteriaResultFieldLevelTemplate({index : j , 
                          fieldName : fieldKey,fieldValue : eachFieldVal})); 

                    }
                }
                            
                 tmpClassIndex++;
        }





}


