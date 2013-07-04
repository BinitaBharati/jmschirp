$(function()
{

//$('.selectpicker').selectpicker();

//jquery ui tab - start
//var tabTitle = $( "#tab_title" ),
//tabContent = $( "#tab_content" ),
var tabTemplate = "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>",
tabCounter = 2;
var selectedConnectionName;
var tabs = $( "#tabs" ).tabs({
    
    /*
    Dont reload already loaded tabs 
    */
    beforeActivate: function(event, ui) {
        
        var newTab = $(ui.newTab);
        var newPanel = $(ui.newPanel);
        var newPanelId = newPanel.attr('id');
		var newAnchor = newTab.find('a');
		var newSpan = newTab.find('span');
		//Chnage active tab styling
        newTab.css('background-color', '#4863A0');
        newTab.css('background-image', 'none');
		newAnchor.css('color', '#EB8F00');
		
  
        var oldTab = $(ui.oldTab);
        var oldPanel = $(ui.oldPanel);
        var oldAnchor = oldTab.find('a');
        //Chnage non-active tab styling
        if(oldTab.length != 0)
        {
	        oldTab.css('background-color', 'white');
	        newTab.css('background-image', 'none');
			oldAnchor.css('color', '#EB8F00');
        }
        
        if(newPanel.length > 0)
        {
            //newTabPanel is not empty

            var hasChildren = (newPanel.children().length > 0 );

            console.log('hasChildren = '+hasChildren); 

            if(hasChildren)//Implies panel data is already loaded, dont load again
            {  
                    var anchorId = newAnchor.attr('id');

                    //Extract current to be actives tabId from anchorId
                    var currentActivatingTabId = anchorId.substring(anchorId.lastIndexOf('-')+1);
                    var selectedQueueName = newAnchor.text();
                    
                    newAnchor.attr("href", 'http://dumb.com');
                    console.log(globalTabsData);
                    newPanel.height(globalTabsData[selectedQueueName]);
            }
        
    }
},

heightStyle: "content" 

});
// modal dialog init: custom buttons and a "close" callback reseting the form inside
var dialog = $( "#dialog" ).dialog({
autoOpen: false,
modal: true,
buttons: {
Add: function() {
addTab();
$( this ).dialog( "close" );
},
Cancel: function() {
$( this ).dialog( "close" );
}
},
close: function() {
form[ 0 ].reset();
}
});

// actual addTab function: adds new tab using the input from the form above
/**
add queueDetails tab
*/
function addTab(selectedConnectionName1, selectedQueueName) {
//var label = tabTitle.val() || "Tab " + tabCounter,
//id = "tabs-" + tabCounter,

selectedConnectionName = selectedConnectionName1;

//Check if tab already exists,then dont add it again.
var tabExists = false;
var filteredResult = $('.ui-tabs-anchor').filter(function() {
    //alert($(this).text());
    if($(this).text() == selectedQueueName)
    {
       //alert($(this).attr('id'));
       $(this).click();
       tabExists = true;
       return;
    
    }
    
});

if(!tabExists)
{
    li = $( tabTemplate.replace( /#\{href\}/g, "../templates/queueDetails.html?connection="+selectedConnectionName+"&queue="+selectedQueueName).replace( /#\{label\}/g, selectedQueueName ) ),
//li = $( tabTemplate.replace( /#\{idTest\}/g, "li-id-"+ tabCounter).replace( /#\{href\}/g, "test3.html" ).replace( /#\{label\}/g, tabTitle ) ),
//tabContentHtml = tabContent.val() || "Tab " + tabCounter + " content.";
tabs.find( ".ui-tabs-nav" ).append( li );
//tabs.append( "<div id='" + id + "'><p>" + tabContentHtml + "</p></div>" );

//doTabHouseKeepingForMainTabHeight();

tabs.tabs( "refresh" );

var newActiveTab = document.getElementById('ui-id-'+tabCounter);
newActiveTab.click();

tabCounter++;
}


}
// addTab button: just opens the dialog
/*$( "#add_tab" )
.button()
.click(function() {
dialog.dialog( "open" );
});*/
// close icon: removing the tab on click
tabs.delegate( "span.ui-icon-close", "click", function() 
{
var listBeClosedId = $( this ).closest( "li" ).attr( "aria-controls" );

var anchorHtml = document.getElementById('ui-id-'+listBeClosedId.substring(listBeClosedId.lastIndexOf('-')+1)).innerHTML;

var panelId =  $( this ).closest( "li" ).remove().attr( "aria-controls" );

$( "#" + panelId ).remove();
tabs.tabs( "refresh" );





});
tabs.bind( "keyup", function( event ) {
if ( event.altKey && event.keyCode === $.ui.keyCode.BACKSPACE ) {
var panelId = tabs.find( ".ui-tabs-active" ).remove().attr( "aria-controls" );
$( "#" + panelId ).remove();
tabs.tabs( "refresh" );
}
});

 //jquery ui tab - end

  $("#connectionListDropDown").change(function()
{
    var selectedConnectionName = $("#connectionListDropDown :selected").val();
    window.location.href = selectedConnectionName;
    
});

 $(".queueListItem").click(function()
{
    var clickedElementId = this.id.trim();//This is how you can get the ID of the dynamically generated element.
    //On click of queue name, browse the queue content

    console.log('queueListItem : click , clickedElementId = '+clickedElementId);

    
    var queueContentTabName = $('#'+clickedElementId).text().trim();

     var selectedConnectionName = $("#connectionListDropDown :selected").text();

    console.log('queueListItem : click , selectedConnectionName = '+selectedConnectionName+' , queueContentTabName = '+queueContentTabName);

    addTab(selectedConnectionName, queueContentTabName);


    
});

 


});