$(document).ready(function() {
    $("#getBooks").click(function (event) {
        event.preventDefault();
        createBookListForm(JSON.parse(getBookList()));
    });
})