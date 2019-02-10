function markupAuthorSelectorForBook(authorsData){
    if(authorsData){
        var authorsSelectorData = authorsData;
        var authorsSelector = document.getElementById("comboBoxAuthor");
        authorsSelector.parentNode.removeChild(authorsSelector);
    }else {
        var authorsSelectorData = window.allAuthors;
    }

    var comboBoxAuthorDiv = document.getElementById("comboBoxAuthorDiv");

    var comboBoxAuthors = document.createElement("select");
    comboBoxAuthors.setAttribute("id", "comboBoxAuthor");
    comboBoxAuthors.setAttribute("name", "comboBoxAuthor");
    book.authors = JSON.parse(getAuthorsFromBook());
    window.authors = book.authors;
    authorsSelectorData.forEach(function(rowData) {
        var authorIdForExclude = window.authors.filter(function(val) {
            return val.name == rowData["name"];
        })[0];
        if(authorIdForExclude == null) {
            var optionAuthor = document.createElement("option");
            optionAuthor.text = rowData["name"];
            optionAuthor.value = rowData["id"];
            comboBoxAuthors.appendChild(optionAuthor);
        }

    });
    comboBoxAuthorDiv.appendChild(comboBoxAuthors);
}