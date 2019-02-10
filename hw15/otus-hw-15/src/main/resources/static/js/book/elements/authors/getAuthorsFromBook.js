function getAuthorsFromBook(){
    var resultAuthors = $.ajax({
        type : "GET",
        contentType : "application/json",
        url : "/book/getAuthorsFromBook/" + window.book.id,
        data : "",
        dataType : "json",
        success : function(result) {
            console.log("success get authors!");
        },
        error : function(e) {
            console.error("ERROR: ", e);
        },
        async : false
    });
    return resultAuthors.responseText;
}