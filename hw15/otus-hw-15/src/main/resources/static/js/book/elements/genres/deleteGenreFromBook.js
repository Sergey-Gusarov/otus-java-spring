function deleteGenre(genreName){
    event.preventDefault();

    var formData = {
        genreName: genreName,
        bookid: window.book.id
    }
    $.ajax({
        type : "DELETE",
        contentType : "application/json",
        url : "/book/deleteGenreFromBook/" + window.book.id,
        data : JSON.stringify(formData),
        dataType : "json",
        success : function(result) {
            console.log(result);
            markupGenreTable(result);
        },
        error : function(e) {
            console.error("ERROR: ", e);
        }
    });
}