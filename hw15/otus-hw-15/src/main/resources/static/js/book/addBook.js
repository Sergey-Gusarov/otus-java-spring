function addBook(){
    event.preventDefault();
    var formData = {
        title :  $("#title").val(),
    }
    $.ajax({
        type : "POST",
        url : "/book",
        data : JSON.stringify(formData),
        contentType : "application/json; charset=utf-8",
        dataType : "json",
        success : function(result) {
            $("#title").val("");
            $("#title").innerHTML = "";
            markupBooksTable(result);
            console.log("success add book!");
        },
        error : function(e) {
            console.error("ERROR: ", e);
            alert('Duplicate title = ' + $("#title").val());
        }
    });
}