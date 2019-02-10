function deleteAuthorFromBook(authorId){
    event.preventDefault();

    var formData = {
        authorId: authorId,
        bookid: window.book.id
    }
    $.ajax({
        type : "DELETE",
        contentType : "application/json",
        url : "/book/deleteAuthorFromBook/" + window.book.id,
        data : JSON.stringify(formData),
        dataType : "json",
        success : function(result) {
            console.log(result);
            markupAuthorsTableForBook(result);
            markupAuthorSelectorForBook(window.allAuthors);
        },
        error : function(e) {
            console.error("ERROR: ", e);
        }
    });
}