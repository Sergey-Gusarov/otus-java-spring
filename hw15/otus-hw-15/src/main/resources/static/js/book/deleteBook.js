 function deleteBook(bookId){
        var formData = {
            id: bookId
        }
        $.ajax({
            type : "DELETE",
            contentType : "application/json",
            url : "/book/" + bookId,
            data : JSON.stringify(formData),
            dataType : "json",
            success : function(result) {
                console.log(result);
                markupBooksTable(result);
            },
            error : function(e) {
                console.error("ERROR: ", e);
            }
        });
}