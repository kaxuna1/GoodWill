/**
 * Created by vakhtanggelashvili on 12/24/15.
 */

function loadTenders(index) {
    $.getJSON("/getactivetenders?index="+index, function (result) {
        $("#dataGridHeader").html("");
        $("#dataGridBody").html("");
        $("#paginationUl").html("");
        currentData = result;
        var dataArray = result["content"];
        var totalPages = result["totalPages"];
        var totalElements = result["totalElements"];
        for (i = 0; i < tenderColumns.length; i++) {
            var currentElement = tenderColumns[i];
            $("#dataGridHeader").append("<th>" + currentElement + "</th>")
        }
        for (i = 0; i < totalPages; i++) {
            $("#paginationUl").append('<li value="' + i + '" class="paginate_button '
                + (index == i ? 'active"' : '') + '"<a href="#">' + (i + 1) + '</a></li>');
        }
        $(".paginate_button").click(function () {
            //console.log($(this).val())
            loadProductRequestsData($(this).val())

        });
        for (i = 0; i < dataArray.length; i++) {
            var currentElement = dataArray[i];

            $("#dataGridBody").append("<tr>" +
                "<td><input value='"+currentElement["id"]+"' class='checkboxParcel' type='checkbox' /></td>" +
                "<td value='" + i + "' class='gridRow'>" + currentElement["name"] + "</td>" +
                "<td value='" + i + "' class='gridRow'>" + moment(new Date(currentElement["createDate"])).locale("ka").format("LLLL") + "</td>" +
                "<td value='" + i + "' class='gridRow'>" + moment(new Date(currentElement["startDate"])).locale("ka").format("LLLL") + "</td>" +
                "<td value='" + i + "' class='gridRow'>" + moment(new Date(currentElement["endDate"])).locale("ka").format("LLLL")   + "</td>" +
                "</tr>");
        }
        var gridRow=$('.gridRow');
        gridRow.css('cursor', 'pointer');
        gridRow.unbind();
        gridRow.click(function () {

        });

    })
}