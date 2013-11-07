/** Common utilities java script. */

/* Common function to invoke a ajax GET request. 
*/

function makeAjaxGETRequest(url, callBack, callBackInputObj)
{

        var xmlhttp;

        if (window.XMLHttpRequest)
        {// code for IE7+, Firefox, Chrome, Opera, Safari
              xmlhttp=new XMLHttpRequest();
        }
        else
        {// code for IE6, IE5
              xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        
        xmlhttp.onreadystatechange=function() {
          if (xmlhttp.readyState == 4) 
          {     
               if(callBackInputObj != null)
                {
                  //callBack(xmlhttp.responseText, callBackInputObj);
                  callBack(xmlhttp, callBackInputObj);
                }
                else //if(callBackInputObj === null)
                {
                  //callBack(xmlhttp.responseText);
                  callBack(xmlhttp);
                }
                //callBack(xmlhttp.responseText);
            
          }
        };

        xmlhttp.open("GET",url,true);
        xmlhttp.send();

    }

 /* Common function to invoke a ajax POST request. 
    The callBackInputObj is optional, and if present, it should be in JSON format */
    function makeAjaxPOSTRequest(url, postBody, callBack, callBackInputObj)
    {
        var xmlhttp;
        
        if (window.XMLHttpRequest)
        {// code for IE7+, Firefox, Chrome, Opera, Safari
              xmlhttp=new XMLHttpRequest();
        }
        else
        {// code for IE6, IE5
              xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }

        
        xmlhttp.onreadystatechange = function() {

        if( xmlhttp.readyState == 4 ) 
        {
            if(callBackInputObj != null)
            {
              callBack(xmlhttp, callBackInputObj);
            }
            else //if(callBackInputObj === null)
            {
              callBack(xmlhttp);
            }

        }

    };
 
        xmlhttp.open("POST",url,true);
        xmlhttp.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        xmlhttp.send(postBody);


    }

 function showErrorDialog(errorMsg)
    {
           var errorDialog =  $("<div id='editConnectionErrorDialog' title='Oops!'><p>"+errorMsg+"</p></div>");
             errorDialog.dialog({dialogClass: "errorDialog", 
                                modal: true,
                                buttons: 
                                {
                                      OK: function() {
                                      $( this ).dialog( "close" );
                                }}});

                              $(".errorDialog .ui-widget-content").css("background-color", "white");

                               $(".errorDialog .ui-dialog-buttonpane").css("margin-top", "0");
                              $(".errorDialog .ui-widget-content").css("border-top-style", "none"); 
                              $(".errorDialog .ui-dialog-content").css("padding-top", "2em "); 



    }

   /* Add new tab to jquery-ui tabs */
   // actual addTab function: adds new tab using the input from the form above
/**
add queueDetails tab
*/
function addTab(tabName, tabContentUrl) {
//var label = tabTitle.val() || "Tab " + tabCounter,
//id = "tabs-" + tabCounter,

//Check if tab already exists,then dont add it again.
var tabExists = false;
var filteredResult = $('.ui-tabs-anchor').filter(function() {
    //alert($(this).text());
    if($(this).text() == tabName)
    {
       //alert($(this).attr('id'));
       $(this).click();
       tabExists = true;
       return;   
    }
    
});

if(!tabExists)
{
  var tabTemplate = TAB_GLOBAL_PARAMS.tabTemplate;
  var tabs =  TAB_GLOBAL_PARAMS.tabs;

    li = $( tabTemplate.replace( /#\{href\}/g, tabContentUrl).replace( /#\{label\}/g, tabName ) ),
//li = $( tabTemplate.replace( /#\{idTest\}/g, "li-id-"+ tabCounter).replace( /#\{href\}/g, "test3.html" ).replace( /#\{label\}/g, tabTitle ) ),
//tabContentHtml = tabContent.val() || "Tab " + tabCounter + " content.";
tabs.find( ".ui-tabs-nav" ).append( li );
//tabs.append( "<div id='" + id + "'><p>" + tabContentHtml + "</p></div>" );

//doTabHouseKeepingForMainTabHeight();


tabs.tabs( "refresh" );

var tabCounter =  TAB_GLOBAL_PARAMS.tabCounter;

var newActiveTab = document.getElementById('ui-id-'+tabCounter);
newActiveTab.click();

tabCounter++;

TAB_GLOBAL_PARAMS.tabCounter = tabCounter;
}}

function chkElementIdValidity(elemId)
{
    console.log('chkElementIdValidity: entered with '+elemId);
    var newElemId = elemId;

    if(elemId.indexOf('.') != -1) 
    {
         newElemId = elemId.replace(/\./g , "-");
    }
    console.log('chkElementIdValidity: exiting with newElemId = '+newElemId);
    return newElemId;
}


 
   
    

         
   
   
