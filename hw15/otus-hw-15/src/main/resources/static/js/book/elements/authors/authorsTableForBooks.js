function markupAuthorsTableForBook(authorData){
    if(authorData){
        var authorsTableData = authorData;
        var authorsTable = document.getElementById("authorsTable");
        authorsTable.parentNode.removeChild(authorsTable);
    }else {
        var authorsTableData = window.authors;

    }
    var authorsTableDiv = document.getElementById("authorsTableDiv");

    var table = document.createElement("table");
    table.setAttribute("id", "authorsTable");
    table.className = "blueTable";

    var thead = document.createElement("thead");
    var trHead = document.createElement("tr");
    var thIdAuthor = document.createElement("th");
    thIdAuthor.innerHTML = "Author id";
    var thName = document.createElement("th");
    thName.innerHTML = "Author name";
    var thDeleteAuthor = document.createElement("th");
    thDeleteAuthor.innerHTML = "Action delete";

    trHead.appendChild(thIdAuthor);
    trHead.appendChild(thName);
    trHead.appendChild(thDeleteAuthor);
    thead.appendChild(trHead);
    table.appendChild(thead);
    var tableBody = document.createElement("tbody");

    authorsTableData.forEach(function(rowData) {
        var tr = document.createElement("tr");
        // Id
        var tdId = document.createElement("td");
        tdId.setAttribute("id", "idAuthor");
        var idAuthorText = document.createTextNode(rowData["id"]);
        tdId.appendChild(idAuthorText);

        // Name
        var tdName = document.createElement("td");
        tdName.innerHTML = rowData["name"];

        // Delete
        var tdButtonDelete = document.createElement("td");
        var buttonDelete = document.createElement("BUTTON");
        var textButtonDelete = document.createTextNode("del");
        buttonDelete.appendChild(textButtonDelete);
        buttonDelete.onclick = function(){
            deleteAuthorFromBook(rowData["id"]);
        };
        tdButtonDelete.appendChild(buttonDelete);

        tr.appendChild(tdId);
        tr.appendChild(tdName);
        tr.appendChild(tdButtonDelete);
        tableBody.appendChild(tr);
    });

    table.appendChild(tableBody);
    authorsTableDiv.appendChild(table);

}