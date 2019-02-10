$(document).ready(function() {
    $("#getAuthors").click(function (event) {
        event.preventDefault();
        createAuthorListForm(JSON.parse(getAuthorList()));
    });
})