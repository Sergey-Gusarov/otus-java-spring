function createBookEditForm(book) {
    event.preventDefault();
    var root = document.getElementById("root");
    while (root.firstChild) {
        root.removeChild(root.firstChild);
    }

    var root = document.getElementById("root");
    window.book = book;

    var divFormEditBook = document.createElement("DIV");
    var editBookForm = document.createElement("FORM");
    editBookForm.setAttribute("id", "editBookForm");

    var h1Books = document.createElement("H1");
    h1Books.innerHTML = "Book:";
    editBookForm.appendChild(h1Books);

    var pBookId = document.createElement("P");
    pBookId.innerHTML = "Book id: ";
    var bookIdInput = document.createElement("INPUT");
    bookIdInput.setAttribute("type", "text");
    bookIdInput.setAttribute("id", "id");
    bookIdInput.setAttribute("readonly", "readonly");
    bookIdInput.value = book["id"];
    pBookId.appendChild(bookIdInput);
    editBookForm.appendChild(pBookId);

    var pBookTitle = document.createElement("P");
    pBookTitle.innerHTML = "Book title: ";
    var bookTitleInput = document.createElement("INPUT");
    bookTitleInput.setAttribute("type", "text");
    bookTitleInput.setAttribute("id", "title");
    bookTitleInput.value = book["title"];
    pBookTitle.appendChild(bookTitleInput);
    editBookForm.appendChild(pBookTitle);

    var buttonEditBookForm = document.createElement("BUTTON");
    buttonEditBookForm.innerHTML = "Edit book";
    buttonEditBookForm.setAttribute("type", "submit");

    buttonEditBookForm.onclick =
        function () {
            editBook();
        };
    editBookForm.appendChild(buttonEditBookForm);
    divFormEditBook.appendChild(editBookForm);
    root.appendChild(divFormEditBook);


    //Genre
    window.genres = book.genres;

    var h2Genres = document.createElement("H2");
    h2Genres.innerHTML = "Genres:";
    root.appendChild(h2Genres);

    var divFormAddGenre = document.createElement("DIV");
    var addGenreFormBook = document.createElement("FORM");
    addGenreFormBook.setAttribute("id", "addGenreForm");

    var pGenreName = document.createElement("P");
    pGenreName.innerHTML = "Genre name: ";
    var genreNameInput = document.createElement("INPUT");
    genreNameInput.setAttribute("type", "text");
    genreNameInput.setAttribute("id", "genreName");
    pGenreName.appendChild(genreNameInput);

    var pGenreNameSubmit = document.createElement("P");
    var genreNameInputSubmit = document.createElement("INPUT");
    genreNameInputSubmit.setAttribute("type", "submit");
    genreNameInputSubmit.setAttribute("value", "Add genre");
    genreNameInputSubmit.onclick = function () {
        addGenre();
    };
    pGenreNameSubmit.appendChild(genreNameInputSubmit);

    addGenreFormBook.appendChild(pGenreName);
    addGenreFormBook.appendChild(pGenreNameSubmit);
    divFormAddGenre.appendChild(addGenreFormBook);
    root.appendChild(divFormAddGenre);

    var divGenresTable = document.createElement("DIV");
    divGenresTable.setAttribute("id", "genresTableDiv");
    root.appendChild(divGenresTable)

    markupGenreTable();

    //Authors
    window.authors = book.authors;
    window.allAuthors = JSON.parse(getAuthorList());

    var h2Authors = document.createElement("H2");
    h2Authors.innerHTML = "Authors:";
    root.appendChild(h2Authors);

    var divFormAddAuthor = document.createElement("DIV");
    var addAuthorFormBook = document.createElement("FORM");
    addAuthorFormBook.setAttribute("id", "addAuthorForm");

    var divComboBoxAuthor = document.createElement("DIV");
    divComboBoxAuthor.setAttribute("id", "comboBoxAuthorDiv");
    addAuthorFormBook.appendChild(divComboBoxAuthor);
    var pAuthorSubmit = document.createElement("DIV");
    var authorInputSubmit = document.createElement("BUTTON");
    authorInputSubmit.setAttribute("type", "submit");
    authorInputSubmit.setAttribute("value", "Add selected author");
    authorInputSubmit.innerHTML = "Add selected author";
    authorInputSubmit.class = "addAuthorInBook";
    authorInputSubmit.onclick = function () {
        addAuthorToBook();
    };
    pAuthorSubmit.appendChild(authorInputSubmit);
    addAuthorFormBook.appendChild(pAuthorSubmit);
    divFormAddAuthor.appendChild(addAuthorFormBook);

    root.appendChild(divFormAddAuthor);
    markupAuthorSelectorForBook();

    var divAuthorsTable = document.createElement("DIV");
    divAuthorsTable.setAttribute("id", "authorsTableDiv");
    root.appendChild(divAuthorsTable);
    markupAuthorsTableForBook();


    //Comment
    window.comments = book.bookComments;

    var h2Comments = document.createElement("H2");
    h2Comments.innerHTML = "Comments:";
    root.appendChild(h2Comments);

    var divFormAddComment = document.createElement("DIV");
    var addCommentFormBook = document.createElement("FORM");
    addCommentFormBook.setAttribute("id", "addCommentForm");

    var pCommentName = document.createElement("P");
    pCommentName.innerHTML = "Comment text: ";
    var commentNameInput = document.createElement("INPUT");
    commentNameInput.setAttribute("type", "CommentText");
    commentNameInput.setAttribute("id", "commentText");
    pCommentName.appendChild(commentNameInput);

    var pCommentNameSubmit = document.createElement("P");
    var commentNameInputSubmit = document.createElement("INPUT");
    commentNameInputSubmit.setAttribute("type", "submit");
    commentNameInputSubmit.setAttribute("value", "Add comment");
    commentNameInputSubmit.onclick = function () {
        addComment();
    };
    pCommentNameSubmit.appendChild(commentNameInputSubmit);

    addCommentFormBook.appendChild(pCommentName);
    addCommentFormBook.appendChild(pCommentNameSubmit);
    divFormAddComment.appendChild(addCommentFormBook);
    root.appendChild(divFormAddComment);

    var divCommentsTable = document.createElement("DIV");
    divCommentsTable.setAttribute("id", "commentsDiv");

    var divCommentsfieldset = document.createElement("fieldset");
    divCommentsfieldset.setAttribute("id", "commentsFieldset");
    divCommentsTable.appendChild(divCommentsfieldset);
    root.appendChild(divCommentsTable);

    markupCommentTable();

}