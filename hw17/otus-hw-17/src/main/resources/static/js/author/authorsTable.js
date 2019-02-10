function markupAuthorsTable(authorData){
    if(authorData){
        var authorsTableData = authorData;
        var authorsTable = document.getElementById("authorsTable");
        authorsTable.parentNode.removeChild(authorsTable);
        var authorsTableWrapper = document.getElementById("authorsTable_wrapper");
        authorsTableWrapper.parentNode.removeChild(authorsTableWrapper);
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
    var thEditAuthor = document.createElement("th");
    thEditAuthor.innerHTML = "Action edit";
    var thDeleteAuthor = document.createElement("th");
    thDeleteAuthor.innerHTML = "Action delete";

    trHead.appendChild(thIdAuthor);
    trHead.appendChild(thName);
    trHead.appendChild(thEditAuthor);
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

        // Edit
        var tdEdit = document.createElement("td");
        tdEdit.setAttribute("Edit", "LinkEditById");
        var linkEdit = document.createElement("a");
        linkEdit.href = "/author?id="+ rowData["id"];
        linkEdit.onclick = function(){
            createAuthorEditForm(rowData);
        };
        var linkEditText = document.createTextNode("edit");
        linkEdit.appendChild(linkEditText);
        tdEdit.appendChild(linkEdit);

        // Delete
        var tdButtonDelete = document.createElement("td");
        var buttonDelete = document.createElement("BUTTON");
        var textButtonDelete = document.createTextNode("del");
        buttonDelete.appendChild(textButtonDelete);
        buttonDelete.onclick = function(){
            deleteAuthor(rowData["id"]);
        };
        tdButtonDelete.appendChild(buttonDelete);

        tr.appendChild(tdId);
        tr.appendChild(tdName);
        tr.appendChild(tdEdit);
        tr.appendChild(tdButtonDelete);
        tableBody.appendChild(tr);
    });

    table.appendChild(tableBody);
    authorsTableDiv.appendChild(table);

    window.$('#authorsTable').DataTable();
}