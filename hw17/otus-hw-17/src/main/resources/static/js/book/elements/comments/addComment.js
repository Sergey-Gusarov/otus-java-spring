function addComment(){
    event.preventDefault();
    var formData = {
        text :  $("#commentText").val(),
    }
    $.ajax({
        type : "POST",
        url : "/book/addCommentToBook/" + window.book.id,
        data : JSON.stringify(formData),
        contentType : "application/json; charset=utf-8",
        dataType : "json",
        success : function(result) {
            $("#commentText").val("");
            $("#commentText").innerHTML = "";
            markupCommentTable(result);
            console.log("success add comment!");
        },
        error : function(e) {
            console.error("ERROR: ", e);
        }
    });
}