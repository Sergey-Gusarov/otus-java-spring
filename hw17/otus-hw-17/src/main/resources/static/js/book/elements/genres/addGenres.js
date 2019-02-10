function addGenre(){
    event.preventDefault();
    var formData = {
        name :  $("#genreName").val(),
    }
    $.ajax({
        type : "POST",
        url : "/book/addGenreToBook/" + book.id,
        data : JSON.stringify(formData),
        contentType : "application/json; charset=utf-8",
        dataType : "json",
        success : function(result) {
            $("#genreName").val("");
            $("#name").innerHTML = "";
            markupGenreTable(result);
            console.log("success add genre!");
        },
        error : function(e) {
            console.error("ERROR: ", e);
        }
    });
}