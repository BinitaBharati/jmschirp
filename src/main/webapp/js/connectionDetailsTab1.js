 $(document).ready(function()
{
	 $('table').footable();

	 $('.clear-filter').click(function (e) {
        e.preventDefault();
        $('table').trigger('footable_clear_filter');
      });

	 
	 /* globalData to manage tab panel height on switching tabs without reloading the existing panel.
	 This page is teh Summary tab of connectionDetails , and tab id will always be 1. */

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

	 
});