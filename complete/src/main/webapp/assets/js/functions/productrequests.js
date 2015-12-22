/**
 * Created by kakha on 11/12/2015.
 */

function loadProductRequestsData(index) {
    $.getJSON("/getallproductrequests?index=" + index, function (result) {
        $("#dataGridHeader").html("");
        $("#dataGridBody").html("");
        $("#paginationUl").html("");
        for (i = 0; i < productRequestsColumns.length; i++) {
            var currentElement = productRequestsColumns[i];
            $("#dataGridHeader").append("<th>" + currentElement + "</th>")
        }
    });
}