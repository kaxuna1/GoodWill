/**
 * Created by vakhtanggelashvili on 12/24/15.
 */

function loadTenders(index,type) {

    $.getJSON("/getactivetenders?index=" + index+"&type="+type, function (result) {
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
                "<td><input value='" + currentElement["id"] + "' class='checkboxParcel' type='checkbox' /></td>" +
                "<td value='" + i + "' class='gridRow'>" + currentElement["name"] + "</td>" +
                "<td value='" + i + "' class='gridRow'>" + moment(new Date(currentElement["createDate"])).locale("ka").format("LLLL") + "</td>" +
                "<td value='" + i + "' class='gridRow'>" + moment(new Date(currentElement["startDate"])).locale("ka").format("LLLL") + "</td>" +
                "<td value='" + i + "' class='gridRow'>" + moment(new Date(currentElement["endDate"])).locale("ka").format("LLLL") + "</td>" +
                "</tr>");
        }
        var gridRow = $('.gridRow');
        var tenderDataTable = $("#tenderDataTable");
        gridRow.css('cursor', 'pointer');
        gridRow.unbind();
        gridRow.click(function () {
            var currentElement=dataArray[$(this).attr("value")];
            var modal6 = $("#myModal6");
            var tab2_2 = $("#tab2_2");
            var tab2_3 = $("#tab2_3");
            tab2_2.html('<div id="tenderRequestsTree"></div>');
            tab2_3.html('<div id="tenderProductsTree"></div>');
            tenderDataTable.html("");
            $("#tenderPartNav").remove();
            $("#tab2_4").remove();
            for (key in currentElement) {
                //console.log(returnTenderDataListItem(key, currentElement[key]));
                tenderDataTable.append(returnTenderDataListItem(key, currentElement[key]));
            }
            if(readCookie("projectUserType") === "1"&& type==1){
                tenderDataTable.append("<tr><td>მოქმედებები</td><td><button id='deleteTenderBtn' class='btn'>წაშლა</button></td></tr>");
                $("#deleteTenderBtn").click(function () {
                    $.getJSON("/deletetender?id="+currentElement["id"], function (result) {
                        if(result){
                            loadTenders(0,1);
                            modal6.modal("hide");
                        }
                    })
                })
            }
            if(readCookie("projectUserType") === "4"&& type==2){
                var tenderTabs=$("#tenderTabs");
                var tenderNavigation=$("#tenderNavigation");
                tenderNavigation.append('<li id="tenderPartNav" class=""><a href="#tab2_4" data-toggle="tab" aria-expanded="false">მონაწილეობა</a></li>');
                tenderTabs.append('<div class="tab-pane fade" id="tab2_4">' +
                    '<table class="table">' +
                    '<thead><tr><th>პროდუქციის ტიპი</th><th>შეთავაზება</th><th>მიმდინარე</th></tr></thead>' +
                    '<tbody id="tenderPartTable"></tbody>' +
                    '</table>' +
                    '</div>');

            }
            var tenderRequestsTree = $("#tenderRequestsTree");
            var tenderProductsTree = $("#tenderProductsTree");
            //tenderRequestsTree.html("");
            var tenderTreeObject = {
                'core': {
                    'data':[

                    ]
                }
            };
            var tenderProductsTreeObject={
                'core': {
                    'data':[

                    ]
                }
            };
            var tenderProductsDataObject={};

            for (key in currentElement['productRequests']) {


                var productRequest={
                    'text': currentElement['productRequests'][key]["filial"]["name"]+" "+moment(new Date(currentElement['productRequests'][key]["requestDate"])).locale("ka").format("LLLL"),
                    "icon": "fa fa-folder-o c-purple",
                    'state': {
                        'selected': false
                    },
                    'children': []
                };
                for(key2 in currentElement['productRequests'][key]["productRequestElements"]){
                    if(tenderProductsDataObject[currentElement['productRequests'][key]["productRequestElements"][key2]["product"]["name"]]){
                        tenderProductsDataObject[currentElement['productRequests'][key]["productRequestElements"][key2]["product"]["name"]]['sum']
                            =tenderProductsDataObject[currentElement['productRequests'][key]["productRequestElements"][key2]["product"]["name"]]['sum']+parseInt(currentElement['productRequests'][key]["productRequestElements"][key2]['quantity']);
                    }else{
                        tenderProductsDataObject[currentElement['productRequests'][key]["productRequestElements"][key2]["product"]["name"]]={};
                        tenderProductsDataObject[currentElement['productRequests'][key]["productRequestElements"][key2]["product"]["name"]]['sum']
                            =parseInt(currentElement['productRequests'][key]["productRequestElements"][key2]['quantity']);
                        tenderProductsDataObject[currentElement['productRequests'][key]["productRequestElements"][key2]["product"]["name"]]['quantType']
                            =quantTypes[currentElement['productRequests'][key]["productRequestElements"][key2]["product"]["quantType"]];
                        tenderProductsDataObject[currentElement['productRequests'][key]["productRequestElements"][key2]["product"]["name"]]['id']
                            =currentElement['productRequests'][key]["productRequestElements"][key2]["product"]["id"];
                    }

                    productRequest["children"].push({
                        'text': currentElement['productRequests'][key]["productRequestElements"][key2]["product"]["name"]+
                        " "+currentElement['productRequests'][key]["productRequestElements"][key2]['quantity']+" "
                        +quantTypes[currentElement['productRequests'][key]["productRequestElements"][key2]["product"]["quantType"]],
                        "icon": "fa fa-calendar"
                    });
                    //fa fa-calendar
                }
                tenderTreeObject['core']['data'].push(productRequest);
            }
            for(key in tenderProductsDataObject){
                $("#tenderPartTable").append("<tr>" +
                    "<td>"+ key +" "+ tenderProductsDataObject[key]['sum']+" "+tenderProductsDataObject[key]['quantType'] +"</td>" +
                    "<td><input id='input"+tenderProductsDataObject[key]['id']+"' pattern='[0-9]+([\.,][0-9]+)?' step='0.01' class='form-control' style='width: 50%;' type='number'>" +
                    "<button value='"
                    +tenderProductsDataObject[key]['id']+"' class='submitBid btn btn-dark'>შეთავაზების გაკეთება</button></td>" +
                    "<td id='productBestBid"+tenderProductsDataObject[key]['id']+"'>0.0</td>" +
                    "</tr>");

                tenderProductsTreeObject['core']['data'].push({
                    'text': key +" "+ tenderProductsDataObject[key]['sum']+" "+tenderProductsDataObject[key]['quantType'] ,
                    "icon": "fa fa-star-o",
                    'state': {
                        'selected': false
                    }
                });
            }
            $(".submitBid").click(function () {
                var currentObject=this;
                $.ajax({
                    url:"/makebid",
                    data:{
                        tenderId:currentElement['id'],
                        productId:$(this).attr("value"),
                        bidSum:$("#input"+$(currentObject).attr("value")).val()
                    }
                }).done(function (result) {
                    if(result){
                        $("#input"+$(currentObject).attr("value")).val("");
                        alert("შემოთავაზება მიღებულია");
                    }else{
                        alert("მოხდა შეცდომა");
                    }
                })
            });
            var t = setInterval(function(){
                $.getJSON('/gettenderbestbids?id='+currentElement["id"], function (result) {
                    console.log(result);
                    for(key in result){
                        $("#productBestBid"+result[key]['product']['id']).html(result[key]['bid'])
                    }
                })
            },2000);
            modal6.on('hidden.bs.modal', function () {
                clearInterval(t);
            });
            tenderProductsTree.jstree(tenderProductsTreeObject);
            tenderRequestsTree.jstree(tenderTreeObject);
            for (key in currentElement["productRequests"]) {
                //tenderRequestsTable.append("<tr><td>"+currentElement["productRequests"][key]["filial"]["name"]+"</td>" +
                //    "<td>"+currentElement["productRequests"][key]["filial"]["name"]+"</td>" +
                //    "</tr>");
            }
            modal6.modal("show");
        });

    });
    function returnTenderDataListItem(key, data) {
        switch (key) {
            case "active":
                return "<tr><td>სტატუსი: </td><td>" + (data ? "აქტიური" : "არა აქტიური") + "</td></tr>";
            case "createDate":
                return "<tr><td>შექმნის დრო: </td><td>" + moment(new Date(data)).locale("ka").format("LLLL") + "</td></tr>";
            case "startDate":
                return "<tr><td>დაწყების დრო: </td><td>" + moment(new Date(data)).locale("ka").format("LLLL") + "</td></tr>";
            case "endDate":
                return "<tr><td>დასრულების დრო: </td><td>" + moment(new Date(data)).locale("ka").format("LLLL") + "</td></tr>";
            case "name":
                return "<tr><td>ტენდერის სახელი:</td><td>" + data + "</td></tr>";
            case "ended":
                return "<tr><td>დასრულებული: </td><td>" + (data ? "დასრულდა" : "არ დასრულებულა") + "</td></tr>";
            case "bids":
                return "<tr><td>ბიჯების რაოდენობა:</td><td>" + data.length + "</td></tr>";
            case "winningBid":
                return "<tr><td>ტენდერი მოგიო:</td><td>" + (data ? (data.user.name + " " + data.user.surname) : "არ არის ცნობილი") + "</td></tr>";
        }
    }
}