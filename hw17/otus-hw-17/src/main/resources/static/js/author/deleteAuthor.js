 function deleteAuthor(authorId){
        var formData = {
            id: authorId
        }
        $.ajax({
            type : "DELETE",
            contentType : "application/json",
            url : "/author/" + authorId,
            data : JSON.stringify(formData),
            dataType : "json",
            success : function(result) {
                console.log(result);
                markupAuthorsTable(result);
            },
            error : function(e) {
                console.error("ERROR: ", e);
            }
        });
}