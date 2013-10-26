
$(document).ready(function()
{

     
//jquery ui tab - start
//var tabTitle = $( "#tab_title" ),
//tabContent = $( "#tab_content" ),
 //tabTemplate = "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>",
//tabCounter = 2;
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

        var oldTabName = oldAnchor.text();
        var newTabName = newAnchor.text();

         console.log('beforeActivate: oldTabName = '+oldTabName+', newTabName = '+newTabName);

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
                    var selectedTabName = newAnchor.text();

                    console.log(globalTabsData);
                    newPanel.height(globalTabsData[selectedTabName]);

                    /* Reload in this case. Reason being that , this tab representing the connectionDetailsTab1.html
                     * also contains a jquery-ui modal dialog - searchQDialog.On, switching tabs from Summary to 
                     * some other tab, and then back to Summary, the dialog needs to be re-initilaized. Else, all dilaog ops
                     * end up with this error - 
                     * Error: cannot call methods on dialog prior to initialization; attempted to call method 'option' */
                    if(selectedTabName == 'Summary')                   
                    {
                         $(".ui-dialog").remove();

                    }
                    else
                    {
                         newAnchor.attr("href", 'http://dumb.com');//Dont re-load tab 

                    }
            }    
    }
},

heightStyle: "content" 

});

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
 TAB_GLOBAL_PARAMS['tabs'] = tabs;
 console.log();


  });





