$(function()
{
 
$("#connectionListDropDown").change(function()
{
    var selectedConnectionName = $("#connectionListDropDown :selected").val();
    window.location.href = selectedConnectionName;
    
});

 $(".queueListItem").click(function(event)
{
    var clickedElementId = this.id.trim();//This is how you can get the ID of the dynamically generated element.
    //On click of queue name, browse the queue content

    console.log('queueListItem : click , clickedElementId = '+clickedElementId);

    
    var queueContentTabName = $('#'+clickedElementId).text().trim();

     var selectedConnectionName = $("#connectionListDropDown :selected").text();

    console.log('queueListItem : click , selectedConnectionName = '+selectedConnectionName+' , queueContentTabName = '+queueContentTabName);
    
    //addTab defined in common.js
    addTab(queueContentTabName, "/queue-details?connection="+selectedConnectionName+"&queue="+queueContentTabName);

    
});

});

