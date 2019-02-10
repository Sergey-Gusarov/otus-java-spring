function createAuthorEditForm(author) {

    var root = document.getElementById("root");
    while (root.firstChild) {
        root.removeChild(root.firstChild);
    }

    var root = document.getElementById("root");

//<form th:object="${author}" id="editAuthorForm">
    var divFormEditAuthor = document.createElement("DIV");
    var editAuthorForm = document.createElement("FORM");
    editAuthorForm.setAttribute("id", "editAuthorForm");

    // <h1>Authors:</h1>
    var h1Authors = document.createElement("H1");
    h1Authors.innerHTML = "Authors:";
    editAuthorForm.appendChild(h1Authors);

    //             <p>Author name: <input type="text" id="name"/></p>
    var pAuthorId = document.createElement("P");
    pAuthorId.innerHTML = "Author id: ";
    var authorIdInput = document.createElement("INPUT");
    authorIdInput.setAttribute("type", "text");
    authorIdInput.setAttribute("id", "id");
    authorIdInput.setAttribute("readonly", "readonly");
    authorIdInput.value = author["id"];
    pAuthorId.appendChild(authorIdInput);
    editAuthorForm.appendChild(pAuthorId);

    var pAuthorName = document.createElement("P");
    pAuthorName.innerHTML = "Author name: ";
    var authorNameInput = document.createElement("INPUT");
    authorNameInput.setAttribute("type", "text");
    authorNameInput.setAttribute("id", "name");
    authorNameInput.value = author["name"];
    pAuthorName.appendChild(authorNameInput);
    editAuthorForm.appendChild(pAuthorName);

    event.preventDefault();

    var buttonEditAuthorForm = document.createElement("BUTTON");
    buttonEditAuthorForm.innerHTML = "Edit author";
    buttonEditAuthorForm.setAttribute("type", "submit");

    buttonEditAuthorForm.onclick =
        function () {
            editAuthor();
        };

    editAuthorForm.appendChild(buttonEditAuthorForm);

    divFormEditAuthor.appendChild(editAuthorForm);
    root.appendChild(divFormEditAuthor);
}