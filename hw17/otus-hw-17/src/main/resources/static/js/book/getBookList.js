function getBookList(){
    var resultBooks = $.ajax({
        type : "GET",
        contentType : "application/json",
        url : "/book",
        data : "",
        dataType : "json",
        success : function(result) {
            console.log("success get books!");
        },
        error : function(e) {
            console.error("ERROR: ", e);
        },
        async: false
    });
    return resultBooks.responseText;
}