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

    
   
   