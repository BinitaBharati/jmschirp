 $(document).ready(function()
{
   
     var HANDLEBAR_TEMPLATES = {
     // searchQObjMsgCN :   Handlebars.compile($("#searchQObjMsgCN-template").html()),
      eachSearchCriteria :   Handlebars.compile($("#eachSearchCriteria-template").html())
     };

	 //$('table').footable();
     $('#queueListingTable').footable();


	 $('.clear-filter').click(function (e) {
        e.preventDefault();
        $('table').trigger('footable_clear_filter');
      });

	 
	 /* globalData to manage tab panel height on switching tabs without reloading the existing panel.
	 This page is the Summary tab of connectionDetails , and tab id will always be 1. */

	 $(".ui-tabs-nav").children().each(function()
	 {
	 	 var eachHtml = $(this).text().trim();
         console.log('connectionDetailsTab1 : handling panel height issue for summary tab - '+eachHtml);
         if(eachHtml == 'Summary')
         {
         	var anchor = $(this).children();
         	var anchorId = anchor.attr('id');
         	console.log('connectionDetailsTab1: handling panel height issue for summary tab, anchorId - '+anchorId);
         	var index = anchorId.substring(anchorId.lastIndexOf('-')+1);
	        console.log('connectionDetailsTab1: index = '+index);

         	globalTabsData['Summary'] = $("#ui-tabs-"+index).height();
         	console.log('connectionDetailsTab1: globalTabsData = '+globalTabsData['Summary']);

         }

	 });
  
      //jquery dialog - start
      $( "#searchQDialog" ).dialog({
      autoOpen: false,
      height: 600,
      width: 500,
      modal: true,
      buttons: {
        "Search": function() {

          var searchCriteriaMap = {};
          
          $(".searchCriteriaInput").each(function(index, element)
            {
                var className = $(element).data("objmodelcn");//HTML5 data attribute. This works !
                var fieldName = $(element).attr('title');
                var fieldVal = $(element).val();
                console.log('Search: className = '+className+', fieldName = '+fieldName+' fieldVal = '+fieldVal);

               if(searchCriteriaMap[className] === undefined)//className not present in searchCriteriaMap
                {
                    var innerfieldMap = {};
                    innerfieldMap['field']=fieldName;
                    innerfieldMap['QUALIFIER']="EQUAL";
                    innerfieldMap['value']=fieldVal;

                    //fieldMap[fieldName] = innerfieldMap; //This is how you can put a variable as a key in map. This syntax {fieldName : fieldVal} wont work !

                    var fieldAry = [innerfieldMap];
                    searchCriteriaMap[className] = fieldAry;
                }
                else
                {
                      var fieldAry = searchCriteriaMap[className];

                      var innerfieldMap = {};
                      innerfieldMap['field']=fieldName;
                      innerfieldMap['QUALIFIER']="EQUAL";
                      innerfieldMap['value']=fieldVal;

                      //fieldMap[fieldName] = fieldVal; //This is how you can put a variable as a key in map. This syntax {fieldName : fieldVal} wont work !

                      fieldAry.push(innerfieldMap);
                      searchCriteriaMap[className] = fieldAry;

                }

            });
             console.log('Search : after populating, searchCriteriaMap = '+searchCriteriaMap);

             var searchDialogTitle = $(".searchQDialogAddClass .ui-dialog-title").text();
             var queueName = searchDialogTitle.substring(searchDialogTitle.indexOf('-')+1).trim();
             console.log('Search: queueName = '+queueName);

             var connectionName = $("#connectionListDropDown :selected").text();
             console.log('Search: connectionName = '+connectionName);
            
             /* All data passd to server from client has to be some string . If you have complicated obj data ,
              *  ensure that obj data complies to JSON format, so that you can stringify while sending to server */
             var searchCriteriaMapJsonStr = JSON.stringify(searchCriteriaMap);

             addTab("Search - "+queueName, "../templates/queueSearchResult.html?connection="+connectionName+"&queue="+queueName+"&searchCriteria="+searchCriteriaMapJsonStr);
             $( this ).dialog( "close" );



         
        },
        Cancel: function() {
          $( this ).dialog( "close" );

          $("#searchCriteriaContent").empty();

          $("#objModelClassListing").children().remove();
          $("#objModelClassListing").append("<option value='NA'>Select</option>");


          $("#classFieldListing").children().remove();
          $("#classFieldListing").append("<option value='NA'>Select</option>");
          $("#classFieldListing").prop("disabled", true);
        }
      },
      close: function() {
         $("#searchCriteriaContent").empty();

         $("#objModelClassListing").children().remove();
          $("#objModelClassListing").append("<option value='NA'>Select</option>");


          $("#classFieldListing").children().remove();
          $("#classFieldListing").append("<option value='NA'>Select</option>");
          $("#classFieldListing").prop("disabled", true);
      }
    });

    //jquery dialog - end
    
    //on selection of a specific className in searchQDialog for ObjectMessage type
    $(".objModelClassListing").click(function(){
        var selectedId = this.id;

        var selectedVal = $("#"+selectedId).val();
        var selectedText = $("#"+selectedId).find('option:selected').text();

        console.log('defaultSearchQClassName:selectedVal = '+selectedVal +', selectedText = '+selectedText);

        if(selectedVal != 'NA')
        {
                  /* get queue name from jquery-ui modal dilaog title. 
                   * jquery-ui emits the dilaog title like this :
                   * <span id="ui-id-1" class="ui-dialog-title">Search queue - emsObjQ1</span>
                   * */

                  var searchDialogTitle = $(".searchQDialogAddClass .ui-dialog-title").text();
                  var queueName = searchDialogTitle.substring(searchDialogTitle.indexOf('-')+1).trim();
                  console.log('click: objModelClassListing, queueName = '+queueName);
                  
                  console.log('click: objModelClassListing, CONN_DETAILS_TAB1_SEARCHQ_GLOBAL = '+CONN_DETAILS_TAB1_SEARCHQ_GLOBAL);

                  var queueMetaData = CONN_DETAILS_TAB1_SEARCHQ_GLOBAL[queueName];
                  console.log('click: objModelClassListing, queueMetaData = '+queueMetaData);

                   var objModel = queueMetaData.model;
                    console.log('click: objModelClassListing, objModel = '+objModel);


                   for(var i = 0 ; i < objModel.length ; i++)
                    {
                          var className = objModel[i].className;
                          console.log('click: objModelClassListing, className = '+className);

                          if(className == selectedText)
                          {
                               console.log('click: objModelClassListing : found match');

                               //enable classFieldListing
                                var classFieldListing = "<option value='NA'>Select</option>";

                                var fieldDescList = objModel[i].fieldDescList;
                           
                              for(var j = 0 ; j < fieldDescList.length ; j++)
                              {
                                  var fieldClassName = fieldDescList[j].fieldClassName;
                                  var fieldName = fieldDescList[j].fieldName;
                                  var hasMultipleValues = fieldDescList[j].hasMultipleValues;
                                  console.log('click: objModelClassListing, fieldClassName = '+fieldClassName+", fieldName =  "+fieldName);

                                  classFieldListing = classFieldListing + "<option value='"+j+"'>"+fieldName+"</option>";

                              }
                               //Clear existing select options and add fresh options.
                               $("#classFieldListing").children().remove();
                               $("#classFieldListing").append(classFieldListing);

                               $("#classFieldListing").prop("disabled", false);


                          }
                      }  

        }
       else        
         //Default out fieldName drop down to Select (NA)
        {
            $("#classFieldListing").val("NA");
            $("#classFieldListing").prop("disabled", true);

        }
    });



    $("#addSearchCriteria").click(function(){

      var classListingIdx =  $("#objModelClassListing").find('option:selected').val();
      var classFieldListingIdx =  $("#classFieldListing").find('option:selected').val();

      if(classListingIdx =='NA')
      {
          showErrorDialog("Please select a class from drop-down !");
          return;
      }
      else if(classFieldListingIdx == 'NA')
      {
           showErrorDialog("Please select a class field from drop-down !");
           return;


      }
          
      //Never use colon as delimiter in jquery for elemntId, elemntClass etc. Thats why I used hyphen.
      var index = classListingIdx + "-" + classFieldListingIdx;
      console.log("addSearchCriteria: click, index = "+index);
      
      var test = $("#searchCriteria-"+"0-0").length;
       console.log("addSearchCriteria: test,  = "+test);


       if($("#searchCriteria-"+index).length > 0)
      {
         console.log("addSearchCriteria: searchCriteria with index "+index+' already exists !. Nothing new to add');

        return;
      }
    

      var eachFieldName = $("#classFieldListing").find('option:selected').text();
      console.log("addSearchCriteria: click, eachFieldName = "+eachFieldName);

      var eachFieldClassName =  $("#objModelClassListing").find('option:selected').text();
      console.log("addSearchCriteria: click, eachFieldClassName = "+eachFieldClassName);

             
      $("#searchCriteriaContent").append(HANDLEBAR_TEMPLATES.eachSearchCriteria(
          {index : index, eachFieldName : eachFieldName, eachFieldClassName : eachFieldClassName}));
      
    });
      
     /* jquery - onclick can not be registered on dynamically added elements. Use 'on' API on any static
     parent element while specifying the selector as the reqd dynamic element.
     */

      $("#searchCriteriaContent").on('click', '.deleteSearchCriteriaImg', function(e){
      var targetId = e.target.id;
      console.log('deleteSearchCriteriaImg: entered for '+targetId);

      var index = targetId.substring(targetId.indexOf('-')+1).trim();
      console.log('deleteSearchCriteriaImg:index = '+index);

      $("#searchCriteria-"+index).remove();

    });


	 
});

    var CONN_DETAILS_TAB1_SEARCHQ_GLOBAL = {};

   
    //On click SearchQ button
    function searchQAction(connectionName, qName)
    {
       console.log('searchQAction: entered with '+connectionName + " " +qName);

        var postData = 'connection='+connectionName+'&queue='+qName;

        makeAjaxPOSTRequest("../groovy/scripts/qSearcher.groovy",postData,searchQCallBack,{connection : connectionName, queue : qName});


    }
      /* Search queue callback -- this will display the search dialog */
     function searchQCallBack(xmlhttp, searchQCallBackInput)
     {
           console.log('searchQCallBack: entered');
           if (xmlhttp.status == 200)
           {
                 var jsonFormattedResponse = JSON.parse(xmlhttp.responseText);
                 console.log('searchQCallBack: jsonFormattedResponse = '+jsonFormattedResponse);

                  CONN_DETAILS_TAB1_SEARCHQ_GLOBAL[searchQCallBackInput.queue] = jsonFormattedResponse;

                 if(jsonFormattedResponse.msgType == 'ObjectMessage')
                 {
                      var objModel = jsonFormattedResponse.model;

                       var objModelClassListing = "<option value='NA'>Select</option>";

                      for(var i = 0 ; i < objModel.length ; i++)
                      {
                          var className = objModel[i].className;
                          console.log('searchQCallBack: className = '+className);
                          objModelClassListing = objModelClassListing +  "<option value='"+i+"'>"+className+"</option>";
                                                 
              
                      }
                      console.log('searchQCallBack: objModelClassListing = '+objModelClassListing);
                      
                       //Clear existing select options and add fresh options.
                      $("#objModelClassListing").children().remove();
                      $("#objModelClassListing").append(objModelClassListing);
                      
                      $("#classFieldListing").children().remove();
                      $("#classFieldListing").append("<option value='NA'>Select</option>");
                      $("#classFieldListing").prop("disabled", true);

                      
                       //Setting jquery-ui modal dialog title dynamically.
                       $( "#searchQDialog" ).dialog('option', 'title', 'Search queue - '+searchQCallBackInput.queue);
                       /* Added additional class to this jquery-ui modal dialog.  Later, we will be required to 
                        * extract the queueName from the dialog's title. And, the dialog title gets emitted by
                        * jqueryui as - 
                        * <span id="ui-id-1" class="ui-dialog-title">Search queue - emsObjQ1</span>
                        * Now, when, we get element by class ("ui-dialog-title"), there could be more 
                        * than one dialogs in DOM with the same class,causing a retrun of corrupt data.
                        *
                        *  
                        * */
                       var testDialogInitialization =$('#searchQDialog').is('dialog');
                       console.log('searchQCallBack: testDialogInitialization = '+testDialogInitialization);
                       //loadSearchModalDialog();
                       $( "#searchQDialog" ).dialog('option', 'dialogClass', 'searchQDialogAddClass');

                       $( "#searchQDialog" ).dialog( "open" );
                       $("#searchQInfo").val("Enter search criteria for - "+searchQCallBackInput.queue);


                 }


           }    
        
     }

