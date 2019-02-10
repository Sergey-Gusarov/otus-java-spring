function createBookListForm(booksData1){

    var root = document. getElementById("root");
    while (root.firstChild) {
        root.removeChild(root.firstChild);
    }
    var root = document.getElementById("root");

    var h1Books = document.createElement("H1");
    h1Books.innerHTML = "Books:";
    root.appendChild(h1Books);

    // <div>
    //         <form id="addBookForm">
    var divFormAddBook = document.createElement("DIV");
    var addBookForm = document.createElement("FORM");
    addBookForm.setAttribute("id", "addBookForm");
    //             <p>Author name: <input type="text" id="name"/></p>
    var pBookTitle = document.createElement("P");
    pBookTitle.innerHTML = "Book title: ";
    var bookTitleInput = document.createElement("INPUT");
    bookTitleInput.setAttribute("type", "text");
    bookTitleInput.setAttribute("id", "title");
    pBookTitle.appendChild(bookTitleInput);
    //             <p><input type="submit" value="Add book"/></p>
    var pBookTitleSubmit = document.createElement("P");
    var bookTitleInputSubmit = document.createElement("INPUT");
    bookTitleInputSubmit.setAttribute("type", "submit");
    bookTitleInputSubmit.setAttribute("value", "Add book");
    bookTitleInputSubmit.onclick = function () {
        addBook();
    };
    pBookTitleSubmit.appendChild(bookTitleInputSubmit);

    addBookForm.appendChild(pBookTitle);
    addBookForm.appendChild(pBookTitleSubmit);
    divFormAddBook.appendChild(addBookForm);
    root.appendChild(divFormAddBook);

    //<div id="booksTableDiv">
    var divBooksTable = document.createElement("DIV");
    divBooksTable.setAttribute("id", "booksTableDiv");
    root.appendChild(divBooksTable)

    window.books = booksData1;

    markupBooksTable();
}