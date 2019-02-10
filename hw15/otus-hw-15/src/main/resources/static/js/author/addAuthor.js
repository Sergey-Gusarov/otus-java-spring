function addAuthor(){
    event.preventDefault();
    var formData = {
        name :  $("#authorName").val(),
    }
    $.ajax({
        type : "POST",
        url : "/author",
        data : JSON.stringify(formData),
        contentType : "application/json; charset=utf-8",
        dataType : "json",
        success : function(result) {
            $("#authorName").val("");
            $("#authorName").innerHTML = "";
            markupAuthorsTable(result);
            console.log("success add author!");
        },
        error : function(e) {
            console.error("ERROR: ", e);
            alert('Maybe duplicate name = ' + $("#authorName").val());
        }
    });
}