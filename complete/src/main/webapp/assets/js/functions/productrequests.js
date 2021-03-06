/**
 * Created by kakha on 11/12/2015.
 */

function loadProductRequestsData(index) {
    $.getJSON("/getallproductrequests?index=" + index, function (result) {
        $("#dataGridHeader").html("");
        $("#dataGridBody").html("");
        $("#paginationUl").html("");
        currentData = result;
        var dataArray = result["content"];
        var totalPages = result["totalPages"];
        var totalElements = result["totalElements"];
        for (i = 0; i < productRequestsColumns.length; i++) {
            var currentElement = productRequestsColumns[i];
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
                "<td value='" + i + "' class='gridRow'>" + currentElement["filial"]["name"] + "</td>" +
                "<td value='" + i + "' class='gridRow'>" + moment(new Date(currentElement["requestDate"])).locale("ka").format("LLLL")+ "</td>" +
                "<td value='" + i + "' class='gridRow'>" + currentElement["productRequestElements"].length + "</td>" +
                "</tr>");
        }
        var gridRow=$('.gridRow');
        gridRow.css('cursor', 'pointer');
        gridRow.unbind();
        gridRow.click(function () {
            var modal3=$("#myModal3");
            var confirmBtn=$("#confirmBtn").unbind();
            var declineBtn=$("#declineBtn").unbind();
            if(readCookie("projectUserType") === "3"){
                confirmBtn.remove();
                declineBtn.remove();
            }
            console.log(dataArray[$(this).attr("value")]);
            var currentElement = dataArray[$(this).attr("value")];
            var myModalLabel3=$("#myModalLabel3");
            myModalLabel3.html("ფილიალი "+currentElement["filial"]["name"]);
            var productsRequestDataTable=$("#productsRequestDataTable");
            productsRequestDataTable.html("");
            for(key in currentElement['productRequestElements']){
                console.log(currentElement['productRequestElements'][key]);
                productsRequestDataTable.append("<tr><td>"
                    +currentElement['productRequestElements'][key]["product"]["name"]
                    +"</td>" +
                    "<td>" +
                    currentElement['productRequestElements'][key]['quantity'] +" "
                    +quantTypes[currentElement['productRequestElements'][key]["product"]["quantType"]]+
                    "</td></tr>")
            }

            confirmBtn.click(function () {
                $.getJSON("/confirmrequest?id="+currentElement["id"],function(result){
                    if(result){
                        loadProductRequestsData(0);
                        alert("წარმატებით მოხდა მონაცემების განახლება");
                        modal3.modal("hide");
                    }
                    else{
                        alert("მოხდა შეცდომა");
                        modal3.modal("hide");
                    }

                })
            });
            declineBtn.click(function () {
                var comment=prompt("შეიყვანეთ კომენტარი","");
                $.getJSON("/declinerequest?id="+currentElement["id"]+"&comment="+comment,function(result){
                    if(result){
                        loadProductRequestsData(0);
                        alert("წარმატებით მოხდა მონაცემების განახლება");
                        modal3.modal("hide");
                    }
                    else{
                        alert("მოხდა შეცდომა");
                        modal3.modal("hide");
                    }

                })
            })

            modal3.modal("show");

        })
    });
}