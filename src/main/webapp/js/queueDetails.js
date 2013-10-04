var HANDLEBAR_TEMPLATES;

$(function() {

        console.log('queueDetails js ');

         HANDLEBAR_TEMPLATES = {
         //msgDetailsDivTmpl :   Handlebars.compile($("#msgDetailsDiv-template").html()) ,
         msgDetailsDivTmpl1 :   Handlebars.compile($("#msgDetailsDiv-template1").html()) ,
         progressBarTemplate :   Handlebars.compile($("#progressBarTemplate").html()) 

     };

    
  });

  function initProgressBar(tabSpecificProgressBarId)
 {
      $("#"+tabSpecificProgressBarId).append(HANDLEBAR_TEMPLATES.progressBarTemplate()); 
 }

  function destroyProgressBar(tabSpecificProgressBarId)
 { 
      $("#"+tabSpecificProgressBarId).empty();
 }

  function showMsgDetailsDiv( e, clickedMsgId)
  {
    console.log('click: clickedMsgId = '+clickedMsgId);

    var msgDivId = 'msDiv-'+clickedMsgId;

    $("body").append(HANDLEBAR_TEMPLATES.msgDetailsDivTmpl({msgDetailsDivId : msgDivId}));
    
    $("#"+msgDivId).offset({left:e.pageX,top:e.pageY});
  }
  
  var msgDetailsCallBack = function(xmlhttpResponse, inputObj)
  {
            if(xmlhttpResponse != null)
            {
                  var clickedMsgId = inputObj.clickedMsgId;
                  console.log('msgDetailsCallBack: clickedMsgId = '+clickedMsgId );

                  var index = clickedMsgId.substring(clickedMsgId.lastIndexOf('-')+1);
                  var tabId = clickedMsgId.substring(0,clickedMsgId.indexOf('-'));

                  var msgDivId = tabId+"-msgDetailsDiv-"+index;
                  var msgDetailsDivNode = $("#"+msgDivId);
                  
                  var msgDetailsDivId = msgDivId;
                  var msgDetailsButtonId = tabId+'-msgDetailsButton-'+index;
                  var msgDivTreeId = tabId+'-msgDetailsDivTree-'+index;
                  console.log('msgDetailsCallBack: index = '+index+', tabId = '+tabId );
  
                  $("#"+clickedMsgId).css("display","none");
                  $("#"+tabId+"-msgLinkTd-"+index).append(HANDLEBAR_TEMPLATES.msgDetailsDivTmpl1({
                  msgDetailsDivId : msgDetailsDivId, 
                  msgDetailsButtonId : msgDetailsButtonId, 
                  msgDivTreeId : msgDivTreeId
                }));

                  $(".closeIcon").click(function()
                  {
                        var closeIconId = this.id;
                        
                        var index = closeIconId.substring(closeIconId.lastIndexOf('-')+1);
                        var tabId = closeIconId.substring(0,closeIconId.indexOf('-'));

                        console.log('closeIcon: entered with tabId '+tabId+', index = '+index);
                         $("#"+tabId+"-msgDetailsDiv"+"-"+index).css("display","none");

                         $("#"+tabId+"-msgId-"+index).css("display","block");

                   });

                  if (xmlhttpResponse.status == 200)
                  {
                     var jsonResponse = JSON.parse(xmlhttpResponse.responseText);

                     if(jsonResponse.responseType == 'tree')
                     {
                           $("#"+msgDivTreeId).dynatree({
                        children : jsonResponse.value
                  
                      });

                     }
                     else
                     {
                        $("#"+msgDivTreeId).addClass('nonTreeMsgDiv');
                        $("#"+msgDivTreeId).append(jsonResponse.value);
                     }  
                     hideProgressStatus(inputObj.queueName, inputObj.clickedMsgId);

                  }
                  else
                  {
                        $("#"+msgDivTreeId).addClass('nonTreeMsgDiv');
                        $("#"+msgDivTreeId).append("Internal server error.");

                        hideProgressStatus(inputObj.queueName, inputObj.clickedMsgId);
                  }
            }
             
      
    };

  function showMsgDetailsDiv1( e, jmsConnectionName, queueName, clickedMsgId )
  {
    console.log('showMsgDetailsDiv1: clickedMsgId = '+clickedMsgId+', jmsConnectionName = '+jmsConnectionName);

    var index = clickedMsgId.substring(clickedMsgId.lastIndexOf('-')+1);
    var tabId = clickedMsgId.substring(0,clickedMsgId.indexOf('-'));

    var msgIdVal = $("#"+tabId+'-msgId-'+index).text();
    console.log('showMsgDetailsDiv1: msgIdVal = '+msgIdVal);
   
    var msgDivId = tabId+"-msgDetailsDiv-"+index;
    var msgDetailsDivNode = $("#"+msgDivId);
    
    /** 
    In the case of an element that does not exist on the page, jQuery will return an object with nothing in it - an empty object
    */
    if(msgDetailsDivNode.length > 0)
    {
        $("#"+clickedMsgId).css("display","none");
        msgDetailsDivNode.css("display","block");
    }
    else
    {
        showProgressStatus(queueName, clickedMsgId);

        makeAjaxGETRequest('../groovy/scripts/getMsgDetails.groovy?connection='+jmsConnectionName+'&queue='+queueName+'&jmsMsgId='+msgIdVal,msgDetailsCallBack,{"clickedMsgId" : clickedMsgId, "queueName" : queueName});   
    }
  
  }

  function showProgressStatus(queueName, clickedMsgId)
  {

       var index = clickedMsgId.substring(clickedMsgId.lastIndexOf('-')+1);

       var parentNode = $("#"+queueName+"-msgLinkTd-"+index);

       var progressBar = $("<div id='"+queueName+"-progressBar1-"+index+"' class='progressBar1'><img src='../images/loading2.gif'></div>");

       parentNode.append(progressBar);

  }

  function hideProgressStatus(queueName, clickedMsgId)
  {
     var index = clickedMsgId.substring(clickedMsgId.lastIndexOf('-')+1);

     console.log('hideProgressStatus: index = '+index);

     $("#"+queueName+"-progressBar1-"+index).remove();

  }

  function handlePanelHeightHouseKeeping(queueName)
  {

         console.log('handlePanelHeightHouseKeeping = '+$("#ui-id-2").text());
         $(".ui-tabs-nav").children().each(function()
         {
               var anchorId = $(this).children().attr('id');
               var anchorText = document.getElementById(anchorId).innerHTML;

               //var anchorText = anchor.text();
               console.log('handlePanelHeightHouseKeeping : anchorText - '+anchorText);
               if(anchorText == queueName.trim())
               {
               
                console.log('handlePanelHeightHouseKeeping: handling panel height issue for summary tab, anchorId - '+anchorId);
                var index = anchorId.substring(anchorId.lastIndexOf('-')+1);
                console.log('handlePanelHeightHouseKeeping: index = '+index);

                globalTabsData[queueName] = $("#ui-tabs-"+index).height();
                console.log('handlePanelHeightHouseKeeping: globalTabsData = '+globalTabsData[queueName]);


               }

         });

  }
  


