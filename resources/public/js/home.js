$(document).ready(function () 
{
	var HANDLEBAR_TEMPLATES = {
         connectionsUL :   Handlebars.compile($("#connectionsUL-template").html()) ,
         connectionsTabListing : Handlebars.compile($("#connectionsTabListing-template").html()) ,
         connectionsListItemHover : Handlebars.compile($("#connectionsListItemHover-template").html()),
         emptyConnectionList :  Handlebars.compile($("#emptyConnectionList-template").html())

     };

     //jquery dialog - start

    var connectionName = $( "#connectionName" ),
      connectionHost = $( "#connectionHost" ),
      connectionPort = $("#connectionPort"),
      connectionUser = $( "#connectionUser" ),
      connectionPassword = $( "#connectionPassword" ),
      connectionVendorType = $( "#connectionVendorType" ),
      connectionVendorVersion = $( "#connectionVendorVersion" ),
      jndiName = $("#jndiName"),
      jndiUser = $("#jndiUser"),
      jndiPassword = $("#jndiPassword"),
      jmxPort = $("#jmxPort"),
      jmxUser = $("#jmxUser"),
      jmxPassword = $("#jmxPassword"),
      allFields = 
      $( [] ).add( 
        connectionName
         ).add(
          connectionHost
           ).add(
           connectionPort
           ).add( 
           connectionUser
            ).add(
            connectionPassword
            ).add(
            connectionVendorType
            ).add(
            connectionVendorVersion
            ).add(
            jndiName
            ).add(
            jndiUser
            ).add(
            jndiPassword
            ).add(
            jmxPort
            ).add(
            jmxUser
            ).add(
            jmxPassword
            ),
      tips = $( ".validateTips" )
      ;

    var connectionDialogMandatoryParms =["connectionName","connectionHost","connectionPort","connectionUser"];

    var saveBtnAction;
    var isUnique = true;
    var currentEditedConnectionId;

    function checkConnectionNameUniquness(name)
    {
        console.log('checkConnectionNameUniquness: name = '+name); 
        $("#connectionsUL").children().each(function(){
          //get DOM existing connection name from anchor html    
            var anchor = $(this).find('.homeConnectionListanchor');
            var domConnectionName = anchor.html();

            console.log('checkConnectionNameUniquness: domConnectionName = '+domConnectionName); 

            if(saveBtnAction == 0)
            {

                    if(domConnectionName == name)
                    {
                          isUnique = false;
                          console.log('checkConnectionNameUniquness: onCreate isUnique = '+isUnique); 
                    }
            }
            else if(saveBtnAction == 1)
            {
                var currentEditedConnectionName = $("#"+currentEditedConnectionId).text().trim();
                if(childListOnClickVal == name && currentEditedConnectionName != name)
                {
                      isUnique = false;
                      console.log('checkConnectionNameUniquness: onEdit isUnique = '+isUnique); 
                }
            }
            

        });

    }

    function updateTips( t ) {
      tips
        .text( t )
        .addClass( "ui-state-highlight" );
      setTimeout(function() {
        tips.removeClass( "ui-state-highlight", 1500 );
      }, 500 );
    }
 
    function checkLength( o, n, min, max ) {
      if(!o.val())
      {
        o.addClass( "ui-state-error" );
        //updateTips( "Length of " + n + " must be between " +
          //min + " and " + max + "." );
        
        return false;
      }
      else if ( o.val().length > max || o.val().length < min ) {
        o.addClass( "ui-state-error" );
        //updateTips( "Length of " + n + " must be between " +
          //min + " and " + max + "." );
        return false;
      } else {
        return true;
      }
    }

    function checkSelectedVendorType()
    {
         if($("#connectionVendorType").val() == -1)
        {
              $("#connectionVendorType").addClass( "ui-state-error" );
              //updateTips( "Please select a Provider from the drop-down" );
              return false;
        }
        return true;
    }
    
    function validateInput()
    {
        var isValid = true;      
        //connectionDialogMandatoryParms.forEach(function(entry){

          for(var i = 0 ; i < connectionDialogMandatoryParms.length ; i++)
          {
           console.log('validateInput: checking '+connectionDialogMandatoryParms[i]);
           var isValid1 = checkLength($("#"+connectionDialogMandatoryParms[i]));
           if(!isValid1)
           {
                isValid = false;
           }

          }
          
        //});

        //var isValid = checkSelectedVendorType(connectionVendorType);
        return isValid;
    }
    $( "#connectionDialog" ).dialog({
      autoOpen: false,
      height: 600,
      width: 500,
      modal: true,
      buttons: {
        "Save": function() {
          var bValid = true;
          allFields.removeClass( "ui-state-error" );
 
         /* bValid = bValid && checkLength( connectionName, "Name", 1, 40 );
         bValid = bValid && checkLength( connectionHost, "Host", 1, 40 );
         bValid = bValid && checkLength( connectionPort, "Port", 1, 40 );
         bValid = bValid && checkLength( connectionUser, "Admin user", 1, 40 );
         bValid = bValid && checkLength( connectionPassword, "Admin password", 1, 40 );
         bValid = checkSelectedVendorType(); */
         bValid = validateInput();
         var bValid1 = checkSelectedVendorType();
         bValid = bValid && bValid1;

         if($("#connectionVendorType").val() == 0 )//Tibco EMS
         {
            //bValid = bValid && checkLength( connectionUser, "Admin user", 1, 40 );
            //bValid = bValid && checkLength( connectionPassword, "Admin password", 1, 40 );
            //bValid = bValid && checkLength( jndiName, "JNDI name", 1, 40 );
            $("#jmxPort").prop('disabled', true);
            $("#jmxUser").prop('disabled', true);
            $("#jmxPassword").prop('disabled', true);



         }
         else if($("#connectionVendorType").val() == 1 )//ActiveMQ
         {
             
              //bValid = bValid && checkLength( jmxPort, "JMX port", 1, 40 );
         }
         
          if ( bValid ) 
          {
                var connectionVendorTypeDom = document.getElementById('connectionVendorType');
                var selectedVendorType = connectionVendorTypeDom.options[connectionVendorTypeDom.selectedIndex].text;
                var connectionVendorVersionDom = document.getElementById('connectionVendorVersion');
                var selectedVendorVersion = connectionVendorVersionDom.options[connectionVendorVersionDom.selectedIndex].text;

                 var postData = "connectionName="+$("#connectionName").val()+"&vendorType="+selectedVendorType+"&vendorVersion="+selectedVendorVersion
                 +"&host="+$("#connectionHost").val()+"&port="+$("#connectionPort").val()+"&adminUser="+$("#connectionUser").val()+"&adminPasswd="+$("#connectionPassword").val()
                 +"&jndiName="+$("#jndiName").val()+"&jndiUser="+$("#jndiUser").val()+"&jndiPassword="+$("#jndiPassword").val()+"&jmxPort="+$("#jmxPort").val()+"&jmxUser="+$("#jmxUser").val()+
                 "&jmxPassword="+$("#jmxPassword").val();

                

                 var callBackInputObj = {jmsConnectionName : $("#connectionName").val()};

                  if(saveBtnAction == 0)//CreateConnection
                  {
                      checkConnectionNameUniquness($("#connectionName").val());
                      console.log('saveConnection: isUnique = '+isUnique);
                      if(!isUnique)
                      {
                              //show error dialog
                              var errorDialog =  $("<div id='editConnectionErrorDialog' title='Oops!'><p>The name should be unqiue!</p></div>");
                              //$("#jmsConnectionDataOuter").append(errorDialog);

                              //reset isUnique global var
                              isUnique = true;

                              errorDialog.dialog({dialogClass: "errorDialog", 
                                modal: true,
                                buttons: 
                                {
                                      OK: function() {
                                      $( this ).dialog( "close" );
                                }}});

                              $(".errorDialog .ui-widget-content").css("background-color", "white");
                              /* $(".errorDialog .ui-widget-header").css("background-color", "#C6E1FA");
                              $(".errorDialog .ui-widget-header").css("background-image", "none"); */

                              $(".errorDialog .ui-dialog-buttonpane").css("margin-top", "0");
                              $(".errorDialog .ui-widget-content").css("border-top-style", "none"); 
                              $(".errorDialog .ui-dialog-content").css("padding-top", "2em "); 
                              //$(".errorDialog .ui-state-default").css("color", "#1C94C4"); 
                              //$(".errorDialog .ui-state-default").css("border-color", "#CCCCCC"); 

                      }
                      else
                      {
                                 postData= postData+"&action=0";
                                 console.log("saveConnection: postData is "+postData);
                                 makeAjaxPOSTRequest('/save-connections',postData,processSaveConnectionCallBack, callBackInputObj );
                                 $( this ).dialog( "close" );
                      }
                  } 
                  else//EditConnection
                  {
                       
                         
                                  postData= postData+"&action=1";
                                  console.log("saveConnection: postData is "+postData);
                                  makeAjaxPOSTRequest('/save-connections',postData,processSaveConnectionCallBack, callBackInputObj );
                                  $( this ).dialog( "close" );
                         
                  }
                 
                
          }

          
        },
        Cancel: function() {
          $( this ).dialog( "close" );
        }
      },
      close: function() {
        allFields.val( "" ).removeClass( "ui-state-error" );
      }
    });

    //jquery dialog - end

    $("#connectionVendorType").click(function(e)
     {
          e.stopPropagation();
          var selectedVendorType = $("#connectionVendorType").val();
          console.log('connectionVendorType: onclick - selectedVendorType = '+selectedVendorType);
          if(selectedVendorType != -1)
          {
              $("#connectionVendorVersion").val(selectedVendorType);
              if(selectedVendorType == 0)//Tibco EMS
              {
                  document.getElementById("jmxPort").disabled=true;
                  document.getElementById("jmxUser").disabled=true;
                  document.getElementById("jmxPassword").disabled=true;
                  var jndiNameLabelSpan = document.getElementById('jndiNameLabelSpan'); 
                  if(jndiNameLabelSpan != null)// if(jndiNameLabelSpan == null) check not working !
                  {
                     return;

                  }
                   $("#jndiNameLabel").append('<span id="jndiNameLabelSpan" class="mandatoryIcon"/>');
                   connectionDialogMandatoryParms.push('jndiName');
                  
              }
              else
              {
                  document.getElementById("jmxPort").disabled=false;
                  document.getElementById("jmxUser").disabled=false;
                  document.getElementById("jmxPassword").disabled=false;
                  $("#jndiNameLabelSpan").remove();
                  jndiName.removeClass( "ui-state-error" );
                  var index = connectionDialogMandatoryParms.indexOf('jndiName');
                  if(index != -1)
                  {
                      connectionDialogMandatoryParms.splice(index, 1);
                  }
                  
              }
          }

      

     });

     function updateJmsConnectionInfo(jmsConnectionInfo)
   {
        //Populate left pane 

        var theTemplateScript = $("#connectionsUL-template").html(); 
        var theTemplate = Handlebars.compile(theTemplateScript); 
        $("#connectionsUL").append(HANDLEBAR_TEMPLATES.connectionsUL(jmsConnectionInfo));

        //Populate JMS Connections pane 

        var theTemplateScript1 = $("#connectionsTabListing-template").html(); 
        var theTemplate1 = Handlebars.compile(theTemplateScript1); 
        $("#jmsConnectionList").append(HANDLEBAR_TEMPLATES.connectionsTabListing(jmsConnectionInfo));      
            
    }

     var listConnectionCallBack = function(xmlhttp){
         if (xmlhttp.status == 200)
         {
              var jsonFormattedResponse = JSON.parse(xmlhttp.responseText);
              if(jsonFormattedResponse)
              {
                  if(jsonFormattedResponse.length == 0)
                  {
                      $("#connectionsUL").append(HANDLEBAR_TEMPLATES.emptyConnectionList());

                  }
                  else
                  {
                      for (i = 0 ; i < jsonFormattedResponse.length ; i++)
                      {
                        $("#emptyConnectionListDiv").remove();
                        updateJmsConnectionInfo({jmsConnectionId: 'tabConnection-'+i,itemCount:i, jmsConnectionName:jsonFormattedResponse[i].name});
                      }
                  }
                  
              }

         }
        
    };
     
    var showConnectionDetailsCallBack = function(xmlhttp){
        if (xmlhttp.status == 200)
        {
             var jsonFormattedResponse = JSON.parse(xmlhttp.responseText);

            console.log('showConnectionDetailsCallBack: jsonFormattedResponse = '+jsonFormattedResponse);
            var queueList = jsonFormattedResponse.queueList;

            for(var i = 0 ; i < queueList.length ; i++)
            {
                var eachQ = queueList[i].name;

                console.log('showConnectionDetailsCallBack: eachQ is '+eachQ);
            }
        }
       
    };

    function populateJmsVendorInfoCallBack(xmlhttp)
    {
        if (xmlhttp.status == 200)
        {
            var jsonFormattedResponse = JSON.parse(xmlhttp.responseText);

            var jmsVendorTypeStr = "<option value='-1'>Select</option>";

            var jmsVendorVersionStr = "<option value='-1'>None</option>";

            for (i = 0 ; i < jsonFormattedResponse.length ; i++)
            {
                var jmsVendorType = jsonFormattedResponse[i].type;

                var jmsVendorVersion = jsonFormattedResponse[i].version;

                //Make html string for jmsVendorType and jmsVendorVersion respectively.

                jmsVendorTypeStr = jmsVendorTypeStr + "<option value='"+i+"'>"+jmsVendorType+"</option>";

                jmsVendorVersionStr = jmsVendorVersionStr+ "<option value='"+i+"'>"+jmsVendorVersion+"</option>";


            }
            /* Clearing/removing select options dont work using replaceChild.But, adding the select options via append works fine.
             Remove and then adding, so , as to avoid duplicates. */
            $('#connectionVendorType').children().remove();
            $('#connectionVendorVersion').children().remove();

            $("#connectionVendorType").append(jmsVendorTypeStr);
            $("#connectionVendorVersion").append(jmsVendorVersionStr);
            document.getElementById("connectionVendorType").disabled=false;//Who the hell disabled it ? That u are having to use this now!
            document.getElementById("connectionVendorVersion").disabled=true;
        }
        
    }

    function processSaveConnectionCallBack(xmlhttp, callBackInputObj)
    {
            if (xmlhttp.status == 200)
            {
                console.log('processSaveConnectionCallBack: entered');
                if (xmlhttp.status == 200)
                {
                    if(saveBtnAction == 0)
                    {
                      var lastIdx = 0;

                      //Get lastConnectionDiv id
                      var lastItem  = $('.connectionsListItem').last();
                      if(lastItem.length != 0)//not undefined or null
                      {
                          var lastListItemId = lastItem.attr('id');
                          
                          if(lastListItemId)
                          {
                              var temp = lastListItemId.substring(lastListItemId.lastIndexOf('-')+1);
                              lastIdx = ++temp;
                              console.log('processSaveConnectionCallBack: lastIdx = '+lastIdx);

                          }      
                
                      }

                       var callBackInputObj1 = {itemCount:(lastIdx), jmsConnectionName: callBackInputObj.jmsConnectionName}; 
                        //Removes emptyConnectionListDiv if present.
                        $("#emptyConnectionListDiv").remove();

                        $("#connectionsUL").append(HANDLEBAR_TEMPLATES.connectionsUL(callBackInputObj1));
                  
                    }
                }
          
            }
            
    }


   $(document).on("mouseenter", ".connectionsListItem", function(e)
   {
     console.log('connectionsListItem: mouseenter');

     var targetId = e.target.id;
     var index = targetId.substring(targetId.indexOf('-')+1);

     console.log('connectionsListItem: mouseenter - index = '+index);

     /*$("#connectionsListItem-"+index).append(HANDLEBAR_TEMPLATES.connectionsListItemHover({connectionsListItemHoverId : 'hoverDiv-'+index, editHoverId : 'editHoverId-'+index, 
      deleteHoverId :'deleteHoverId-'+index , connectionName : "\'test\'"}));*/
     $("#rightDiv-"+index).css("display","block");
     $("#connectionsListItem-"+index).addClass('connectionsListItemHover');


    });

    $(document).on("mouseleave", ".connectionsListItem", function(e){

     console.log('connectionsListItem: mouseleave');
     var targetId = e.currentTarget.id;
     var index = targetId.substring(targetId.indexOf('-')+1);

     console.log('connectionsListItem: mouseenter - index = '+index);

     //$("#hoverDiv-"+index).remove();
     $("#rightDiv-"+index).css("display","none");
     $("#connectionsListItem-"+index).removeClass('connectionsListItemHover');

    });

     $("#newConnection").click(function(e){
        console.log('newConnection : click entered');
        e.stopPropagation();
        saveBtnAction = 0;//New connection
        document.getElementById("connectionName").disabled=false;//was set to true during editConnection click.Reset to false now.       

        /** Undoing the chnages that could have happened to the conectionDialog's DOM, while it was open earlier */
        document.getElementById("connectionVendorType").disabled=false;
        document.getElementById("jmxPort").disabled=false;
        document.getElementById("jmxUser").disabled=false;
        document.getElementById("jmxPassword").disabled=false;
        $("#jndiNameLabelSpan").remove();
        var index = connectionDialogMandatoryParms.indexOf('jndiName');
        if(index != -1)
        {
            connectionDialogMandatoryParms.splice(index,1);
        }
        
        
        $( "#connectionDialog" ).dialog( "open" );

        /* If you use, just jqueryui modalDialog (without nesting it inside a jquery tab,
          then , the modalDilaog finally rendered,gets a title div too. The title div is
          given the innerHTML same as the value of the button that opened the dialog. 
          But, due to some bug of making jquery tab and dialog work together, am manually 
          setting the title of the dilaog. ui-dialog-title is created dynamically by jquery
          ui modal dialog JS.- $( "#connectionDialog" ).dialog call adds this element. */
        //$(".ui-dialog-title").replaceWith("New connection");

    });

     /* jquery - onclick can not be registered on dynamically added elements. Use 'on' API on any static
     parent element while specifying the selector as the reqd dynamic element.
     */
    $('#connectionsUL').on('click', '.editConnection', function(e){
        saveBtnAction = 1;//Edit connection
        var targetId = e.target.id;
        console.log('editConnection: targetId = '+targetId);
        var idx = targetId.substring(targetId.lastIndexOf('-')+1);
        var connectionName = $("#leftTabConnectionListAnchor-"+idx).html();
        makeAjaxGETRequest('get-connection-info?name='+connectionName,getConnectionDetailsCallBack);
    });

    $('#connectionsUL').on('click', '.deleteConnection', function(e){
        
        var targetId = e.target.id;
        console.log('editConnection: targetId = '+targetId);
        var idx = targetId.substring(targetId.lastIndexOf('-')+1);
        var connectionName = $("#leftTabConnectionListAnchor-"+idx).html();

        //Show confirmation dialog
        var errorDialog =  $("<div id='editConnectionErrorDialog' title='Confirm!'><p>OK to delete ?</p></div>");
        errorDialog.dialog({dialogClass: "errorDialog", 
          modal: true,
          buttons: 
          {
                OK: function() {
                $( this ).dialog( "close" );
                        var deleteConnectionCallBackInputObj = {index : idx};
                        makeAjaxGETRequest('/delete-connection?name='+connectionName,deleteConnectionCallBack, deleteConnectionCallBackInputObj );
          }}});

         $(".errorDialog .ui-widget-content").css("background-color", "white");
          /* $(".errorDialog .ui-widget-header").css("background-color", "#C6E1FA");
          $(".errorDialog .ui-widget-header").css("background-image", "none"); */

          $(".errorDialog .ui-dialog-buttonpane").css("margin-top", "0");
          $(".errorDialog .ui-widget-content").css("border-top-style", "none"); 
          $(".errorDialog .ui-dialog-content").css("padding-top", "2em "); 
          //$(".errorDialog .ui-state-default").css("color", "#1C94C4"); 
          //$(".errorDialog .ui-state-default").css("border-color", "#CCCCCC"); 


        
    });

    function deleteConnectionCallBack(xmlhttp, deleteConnectionCallBackInputObj)
    {
        if (xmlhttp.status == 200)
        {
            $("#connectionsListItem-"+deleteConnectionCallBackInputObj.index).remove();

            var lastItem  = $('.connectionsListItem').last();

            if(lastItem.length == 0)//empty object - no more connections left
            {
                 $("#connectionsUL").append(HANDLEBAR_TEMPLATES.emptyConnectionList());
            }

        }
        
    }

    function getConnectionDetailsCallBack(xmlhttp)
   {
        if (xmlhttp.status == 200)
        {
            var jsonFormattedResponse = JSON.parse(xmlhttp.responseText);
            console.log('getConnectionDetailsCallBack: jsonFormattedResponse = '+jsonFormattedResponse);

            var connectionName = jsonFormattedResponse.connectionName;
            var vendorType = jsonFormattedResponse.vendorType;
            var vendorVersion = jsonFormattedResponse.vendorVersion;
            var host = jsonFormattedResponse.host;
            var port = jsonFormattedResponse.port;
            var adminUser = jsonFormattedResponse.adminUser;
            var adminPasswd = jsonFormattedResponse.adminPasswd;
            var jndiName = jsonFormattedResponse.jndiName;
            var jndiUser = jsonFormattedResponse.jndiUser;
            var jndiPasswd = jsonFormattedResponse.jndiPasswd;
            var jmxPort = jsonFormattedResponse.jmxPort;
            var jmxUser = jsonFormattedResponse.jmxUser;
            var jmxPassword = jsonFormattedResponse.jmxPassword;

            console.log('getConnectionDetailsCallBack: host = '+host +', port = '+port);

            //Set the connectionDialog params from jsonResponse
            $("#connectionName").val(connectionName);
            document.getElementById("connectionName").disabled=true;
            $("#connectionVendorType option").each(function()
            {
              if($(this).text() == vendorType)
              {
                $("#connectionVendorType").val($(this).val());
                $("#connectionVendorVersion").val($(this).val());
                return;
              }
            })
            
            document.getElementById("connectionVendorType").disabled=true;
            $("#connectionVendorVersion").append('<option value=\"'+vendorVersion+'\">'+vendorVersion+'</option>');
            $("#connectionHost").val(host);
            $("#connectionPort").val(port);
            $("#connectionUser").val(adminUser);
            $("#connectionPassword").val(adminPasswd);
            $("#jndiName").val(jndiName);
            $("#jndiUser").val(jndiUser);
            $("#jndiPassword").val(jndiPasswd);
            $("#jmxPort").val(jmxPort);
            $("#jmxUser").val(jmxUser);
            $("#jmxPassword").val(jmxPassword);

             if($("#connectionVendorType").val() == 0 )//Tibco EMS
            {   
              $("#jmxPort").prop('disabled', true);
              $("#jmxUser").prop('disabled', true);
              $("#jmxPassword").prop('disabled', true);

            }
            else
            {
              $("#jmxPort").prop('disabled', false);
              $("#jmxUser").prop('disabled', false);
              $("#jmxPassword").prop('disabled', false);

            }           
            $( "#connectionDialog" ).dialog( "open" );
        }


   }


     makeAjaxGETRequest('list-connections',listConnectionCallBack);

     makeAjaxGETRequest('get-vendor-details',populateJmsVendorInfoCallBack);
 });
