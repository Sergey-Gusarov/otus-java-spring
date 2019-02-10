$(document).ready(function() {
    $("#addAuthorForm").submit(function (event) {
        event.preventDefault();
        addAuthorToBook();
    });
    function addAuthorToBook(){
        var formData = {
            id :  $("#comboBoxAuthor").val(),
        }
        $.ajax({
            type : "POST",
            url : "/book/addAuthorToBook/" + window.book.id,
            data : JSON.stringify(formData),
            contentType : "application/json; charset=utf-8",
            dataType : "json",
            success : function(result) {
                markupAuthorsTableForBook(result);
                markupAuthorSelectorForBook(result);
                console.log("success add author!");
            },
            error : function(e) {
                console.error("ERROR: ", e);
            }
        });
    }
})
function addAuthorToBook(){
    event.preventDefault();
    var formData = {
        id :  $("#comboBoxAuthor").val(),
    }
    $.ajax({
        type : "POST",
        url : "/book/addAuthorToBook/" + window.book.id,
        data : JSON.stringify(formData),
        contentType : "application/json; charset=utf-8",
        dataType : "json",
        success : function(result) {
            markupAuthorsTableForBook(result);
            markupAuthorSelectorForBook(window.allAuthors);
            console.log("success add author!");
        },
        error : function(e) {
            console.error("ERROR: ", e);
        }
    });
}