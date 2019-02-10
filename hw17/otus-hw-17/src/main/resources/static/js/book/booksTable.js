function markupBooksTable(bookData){
    if(bookData){
        var booksTableData = bookData;
        var booksTable = document.getElementById("booksTable");
        booksTable. parentNode.removeChild(booksTable);
        var booksTableWrapper = document.getElementById("booksTable_wrapper");
        booksTableWrapper.parentNode.removeChild(booksTableWrapper);
    }else {
        var booksTableData = window.books;
    }
    var booksTableDiv = document.getElementById("booksTableDiv");

    var table = document.createElement("table");
    table.setAttribute("id", "booksTable");
    table.className = "blueTable";

    var thead = document.createElement("thead");
    var trHead = document.createElement("tr");
    var thIdBook = document.createElement("th");
    thIdBook.innerHTML = "Book id";
    var thTitle = document.createElement("th");
    thTitle.innerHTML = "Book title";
    var thEditBook = document.createElement("th");
    thEditBook.innerHTML = "Action edit";
    var thDeleteBook = document.createElement("th");
    thDeleteBook.innerHTML = "Action delete";

    trHead.appendChild(thIdBook);
    trHead.appendChild(thTitle);
    trHead.appendChild(thEditBook);
    trHead.appendChild(thDeleteBook);
    thead.appendChild(trHead);
    table.appendChild(thead);
    var tableBody = document.createElement("tbody");

    booksTableData.forEach(function(rowData) {
        var tr = document.createElement("tr");
        // Id
        var tdId = document.createElement("td");
        tdId.setAttribute("id", "idBook");
        var idBookText = document.createTextNode(rowData["id"]);
        tdId.appendChild(idBookText);
        // Title
        var tdTitle = document.createElement("td");
        tdTitle.innerHTML = rowData["title"];
        // Edit
        var tdEdit = document.createElement("td");
        tdEdit.setAttribute("Edit", "idEditLink");
        var linkEdit = document.createElement("a");
        linkEdit.href = "/book?id="+ rowData["id"];
        var linkEditText = document.createTextNode("edit");
        linkEdit.onclick = function(){
            createBookEditForm(rowData);
        };
        linkEdit.appendChild(linkEditText);
        tdEdit.appendChild(linkEdit);

        // Delete
        var tdButtonDelete = document.createElement("td");
        var buttonDelete = document.createElement("BUTTON");
        var textButtonDelete = document.createTextNode("del");
        buttonDelete.appendChild(textButtonDelete);
        buttonDelete.onclick = function(){
            deleteBook(rowData["id"]);
        };
        tdButtonDelete.appendChild(buttonDelete);

        tr.appendChild(tdId);
        tr.appendChild(tdTitle);
        tr.appendChild(tdEdit);
        tr.appendChild(tdButtonDelete);
        tableBody.appendChild(tr);
    });

    table.appendChild(tableBody);
    booksTableDiv.appendChild(table);

    window.$('#booksTable').DataTable();
}