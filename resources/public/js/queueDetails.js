var HANDLEBAR_TEMPLATES;

function init(connName, qName)
      {
        initProgressBar("tabSpecificProgressBar-"+qName);
   
      makeAjaxGETRequest('/queue-browser?connection='+connName+'&queue='+qName+'&isScrolled=N',queueDetailsCallBack, {"queueName" : qName,"invokedFirstTime" : true}); 


      }


$(function() {

        console.log('queueDetails js ');

         HANDLEBAR_TEMPLATES = {
         //msgDetailsDivTmpl :   Handlebars.compile($("#msgDetailsDiv-template").html()) ,
         msgDetailsDivTmpl1 :   Handlebars.compile($("#msgDetailsDiv-template1").html()) ,
         progressBarTemplate :   Handlebars.compile($("#progressBarTemplate").html()) ,
         msgLinkDivTemplate : Handlebars.compile($("#msgLinkDiv-template1").html())

     };

     $('.clear-filter').click(function (e) {
        e.preventDefault();
        $('table').trigger('footable_clear_filter');
      });

      $(".fooTblDiv").scroll(function(event)
      {
          console.log('scroll: entered ');

          /* Get current event target elemnt, and extract Id. From ID , extract the queueName */

          var target = event.target;//plain DOM node
          console.log('scroll: target node is '+target);

          var targetId = target.getAttribute('id');

          var queueName = targetId.substring(targetId.indexOf('-')+1);
          
          var scrolledDivId = targetId;//this.id;
          console.log('scroll: targetId = '+targetId+', queueName = '+queueName);

          var queueMap = (queueName in globalQueueData) ? globalQueueData[queueName] : {};

          var hasMore = queueMap['hasMore'];
          var lastScrollTop = ('lastScrollTop' in queueMap) ? queueMap['lastScrollTop'] : 0;
          var scrollTimer = ('scrollTimer' in queueMap) ? queueMap['scrollTimer'] : -1;

          console.log('scroll: queueMap data is - hasMore = '+hasMore+', lastScrollTop = '+lastScrollTop
              +', scrollTimer = '+scrollTimer);

          var currentSt = $("#"+scrolledDivId).scrollTop();
          if (currentSt > lastScrollTop)//scroll down event
          {
              if(hasMore == 'Y')
              {
                   if (scrollTimer != -1)
                  {
                      clearTimeout(scrollTimer);
                  }
                  scrollTimer = setTimeout(function(){scrollEnded(queueName)}, 500);//500ms should be enough for a user to complete scroll
                  queueMap['scrollTimer'] = scrollTimer;
                  globalQueueData[queueName] = queueMap;
                    
              }
          
          }
          //else, scroll top has happened, do nothing 
          console.log('scroll: at chk point');
          queueMap['lastScrollTop'] = currentSt;
          globalQueueData[queueName] = queueMap;
          
               
      });

     function scrollEnded(queueName)
     {

       console.log('scrollEnded: entered with queueName = '+queueName);

       var fooTblBodyNode = document.getElementById('fooTblBody-'+queueName);
       var lastTblRowNodeId = fooTblBodyNode.lastChild.id;
       var lastShownRowIdx = lastTblRowNodeId.substring(lastTblRowNodeId.lastIndexOf('-')+1);
       console.log('scrollEnded: lastShownRowIdx = '+lastShownRowIdx);


       var lastTdNodeId = queueName+"-msgLinkTd-"+(lastShownRowIdx - 1);

       var lastTdNodePosition = $("#"+lastTdNodeId).position();

       var progressBarOnScrollDiv = document.createElement('div');
       progressBarOnScrollDiv.id=queueName+"-progressBarOnScroll";
       progressBarOnScrollDiv.style.position='absolute';
       progressBarOnScrollDiv.style.zIndex = 1000;
       progressBarOnScrollDiv.style.top = lastTdNodePosition.top +'px';
       progressBarOnScrollDiv.style.left = lastTdNodePosition.left +'px';
       progressBarOnScrollDiv.style.width = '95.9%';

       var tblDiv = document.getElementById("fooTblDiv-"+queueName);
       tblDiv.appendChild(progressBarOnScrollDiv);

       initProgressBar(queueName+"-progressBarOnScroll");
       var jmsConnectionName= $("#connectionListDropDown option:selected").text();

        makeAjaxGETRequest('/queue-browser?connection='+jmsConnectionName+'&queue='+queueName+'&isScrolled=Y',queueDetailsCallBack,{"queueName" : queueName,"invokedFirstTime" : false});
         
     }
      
  });


  var queueDetailsCallBack = function(xmlhttpResponse, callBackInputObject)
  {
        if (xmlhttpResponse.status == 200)
        {
        var jsonResponse = JSON.parse(xmlhttpResponse.responseText);
        
        var queueName = callBackInputObject.queueName;
        console.log('queueDetailsCallBack: jsonResponse.hasMore ' +jsonResponse.hasMore+',jsonResponse.empty = '+jsonResponse.empty);
        
        var fooTblBodyChildrenLen = $("#fooTblBody-"+queueName).children().length;


        if(jsonResponse.empty && fooTblBodyChildrenLen == 0)
        {
            $("#fooTblDivEmptyQ-"+queueName).append("<p>"+jsonResponse.msgData[0]+"</p>");
            $("#fooTblDivEmptyQ-"+queueName).css("display", "block");
            destroyProgressBar("tabSpecificProgressBar-"+queueName);

            return;
        }
         
         var lastRetrievedMsgIndex = 0;
         var fooTblBodyNode = $('#fooTblBody-'+queueName);

         //var dataStore = fooTblBodyNode.data("store");
         var queueMap = (queueName in globalQueueData)? globalQueueData[queueName]: {};
         queueMap['hasMore'] = jsonResponse.hasMore;
         globalQueueData[queueName] = queueMap;

         if(fooTblBodyChildrenLen > 0)
         {
              //Implies, this is not the first time, this page has been requested. (Will happen on scroll)
               var domfooTblBodyNode = document.getElementById("fooTblBody-"+queueName);
               var lastTblRowNodeId = domfooTblBodyNode.lastChild.id;
               lastRetrievedMsgIndex = lastTblRowNodeId.substring(lastTblRowNodeId.lastIndexOf('-')+1);

         }
       
        console.log('queueDetailsCallBack: lastRetrievedMsgIndex = '+lastRetrievedMsgIndex);

       
        var msgData = jsonResponse.msgData;
        for (var i = 0 ; i < msgData.length ; i++)
        {
              lastRetrievedMsgIndex++;
              console.log('queueDetailsCallBack: each msgData = '+msgData[i]);

              var fooTblBody = document.getElementById('fooTblBody-'+queueName);

               var tdData = document.createElement("td");
               tdData.innerHTML = msgData[i].type;

               var tdData1 = document.createElement("td");
               tdData1.id=queueName+"-msgLinkTd-"+lastRetrievedMsgIndex;

               var msgId = msgData[i].JMSMessageID;
               
               tdData1.innerHTML = HANDLEBAR_TEMPLATES.msgLinkDivTemplate({queueName : queueName, lastRetrievedMsgIndex : lastRetrievedMsgIndex,
               msgId :  msgData[i].JMSMessageID});

      
               var tdData2 = document.createElement("td");
               tdData2.innerHTML = msgData[i].JMSRedelivered;

               var trRow = document.createElement("tr");
               trRow.id = queueName+"-trRow-"+lastRetrievedMsgIndex;

               trRow.appendChild(tdData1);
               trRow.appendChild(tdData2);
               trRow.appendChild(tdData);

               fooTblBody.appendChild(trRow);
      
          }          
          
         if(callBackInputObject.invokedFirstTime)
          {
            destroyProgressBar("tabSpecificProgressBar-"+queueName);
          }
          else
          {
            console.log('queueDetailsCallBack: in else part');
            var testId = queueName+"-progressBarOnScroll";
            console.log('queueDetailsCallBack: testId = '+testId);
            var test1 = $("#"+testId).length;
            console.log('queueDetailsCallBack: test1 = '+test1);

            $("#"+queueName+"-progressBarOnScroll").remove();
          } 
                   
          $('#footable-'+queueName).footable();
            
          $("#fooTblDiv-"+queueName).css("display", "block");

         /* globalData to manage tab panel height on switching tabs without reloading the existing panel.
         This page is teh Summary tab of connectionDetails , and tab id will always be 1. */

         handlePanelHeightHouseKeeping(queueName);

        //Ref : http://stackoverflow.com/questions/14969960/jquery-click-events-firing-multiple-times

         
        }
        else//Exception thrown by server
        {

        }

     };


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

        makeAjaxGETRequest('/msg-browser?connection='+jmsConnectionName+'&queue='+queueName+'&jmsMsgId='+msgIdVal,msgDetailsCallBack,{"clickedMsgId" : clickedMsgId, "queueName" : queueName});   
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


     function onMsgLinkClick(clickedElement, queueName)
     {
             console.log('msgLinkDiv:click');
             var clickedElementId = clickedElement.id;
             var msgIdVal = $("#"+clickedElementId).html();

              console.log('msgLinkDiv:click, clickedElementId = '+clickedElementId+', msgIdVal = '+msgIdVal);
              var jmsConnectionName= $("#connectionListDropDown option:selected").text();
              showMsgDetailsDiv1(null, jmsConnectionName, queueName, clickedElementId );
     }

  


