function markupGenreTable(genreData){
    if(genreData){
        var genresTableData = genreData;
        var genresTable = document.getElementById("genresTable");
        genresTable.parentNode.removeChild(genresTable);
    }else {
        var genresTableData = window.genres;
    }
    var bookData = window.book;

    var genresTableDiv = document.getElementById("genresTableDiv");
    
    var table = document.createElement("table");
    table.setAttribute("id", "genresTable");
    table.className = "blueTable";

    var trHead = document.createElement("tr");
    var thGenreName = document.createElement("th");
    thGenreName.innerHTML = "Genre";
    var thDeleteGenre = document.createElement("th");
    thDeleteGenre.innerHTML = "Action";

    trHead.appendChild(thGenreName);
    trHead.appendChild(thDeleteGenre);

    var i = 0;

    genresTableData.forEach(function(rowData) {
        var trBody = document.createElement("tr");
        // Name
        var tdName = document.createElement("td");
        var inputName = document.createElement("input");
        inputName.setAttribute("type", "text");
        inputName.setAttribute("id", "genres"+i+".name");
        inputName.setAttribute("name", "genres["+i+"].name");
        inputName.setAttribute("value", rowData["name"]);
        i = i + 1;
        tdName.appendChild(inputName);

        // Delete
        var tdButtonDelete = document.createElement("td");
        var buttonDelete = document.createElement("BUTTON");
        var textButtonDelete = document.createTextNode("del");
        buttonDelete.appendChild(textButtonDelete);
        buttonDelete.onclick = function(){
            deleteGenre(rowData["name"]);
        };
        tdButtonDelete.appendChild(buttonDelete);

        trBody.appendChild(tdName);
        trBody.appendChild(tdButtonDelete);

        table.appendChild(trBody);
    });
    genresTableDiv.appendChild(table);
}