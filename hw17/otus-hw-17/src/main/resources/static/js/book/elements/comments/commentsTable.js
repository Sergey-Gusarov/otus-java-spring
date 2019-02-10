function markupCommentTable(commentData){
    if(commentData){
        var commentsTableData = commentData;
        var commentsTable = document.getElementById("commentsTable");
        commentsTable.parentNode.removeChild(commentsTable);
    }else {
        var commentsTableData = window.comments;
    }
    var bookData = window.book;

    var commentsTableDiv = document.getElementById("commentsFieldset");
    
    var table = document.createElement("table");
    table.setAttribute("id", "commentsTable");
    //table.className = "blueTable";

    var trHead = document.createElement("tr");
    var thCommentName = document.createElement("th");
    thCommentName.innerHTML = "Comment";
    var thDeleteComment = document.createElement("th");
    thDeleteComment.innerHTML = "Action";

    trHead.appendChild(thCommentName);
    trHead.appendChild(thDeleteComment);

    var i = 0;
    commentsTableData.forEach(function(rowData) {
        var trBody = document.createElement("tr");
        // Text
        var tdCommentText = document.createElement("td");
        var inputCommentText = document.createElement("input");
        inputCommentText.setAttribute("type", "text");
        inputCommentText.setAttribute("id", "bookComments"+i+".text");
        inputCommentText.setAttribute("name", "bookComments["+i+"].text");
        inputCommentText.setAttribute("value", rowData["text"]);
        i = i + 1;
        tdCommentText.appendChild(inputCommentText);
        // Delete
        var tdButtonDelete = document.createElement("td");
        var buttonDelete = document.createElement("BUTTON");
        var textButtonDelete = document.createTextNode("del");
        buttonDelete.appendChild(textButtonDelete);
        buttonDelete.onclick = function(){
            deleteComment(rowData["text"]);
        };
        tdButtonDelete.appendChild(buttonDelete);

        trBody.appendChild(tdCommentText);
        trBody.appendChild(tdButtonDelete);

        table.appendChild(trBody);
    });
    commentsTableDiv.appendChild(table);

}