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
        var tenderDataTable=$("#tenderDataTable");
        gridRow.css('cursor', 'pointer');
        gridRow.unbind();
        gridRow.click(function () {
            var modal6=$("#myModal6");
            tenderDataTable.html("");
            for(key in currentElement){
                console.log(returnTenderDataListItem(key,currentElement[key]));
                tenderDataTable.append(returnTenderDataListItem(key,currentElement[key]));
            }
            var tenderRequestsTree=$("#tenderRequestsTree");
            tenderRequestsTree.html("");
            tenderRequestsTree.jstree({
                'core': {
                    'data': [{
                        'text': 'My pictures',
                        "icon": "fa fa-picture-o c-red",
                        'state': {
                            'selected': false
                        }
                    }, {
                        'text': 'My videos',
                        "icon": "fa fa-film c-orange",
                        'state': {
                            'opened': true,
                            'selected': false
                        },
                        'children': [{
                            'text': 'Video 1',
                            "icon": "fa fa-film c-orange"
                        }, {
                            'text': 'Video 2',
                            "icon": "fa fa-film c-orange"
                        }]
                    }, {
                        'text': 'My documents',
                        "icon": "fa fa-folder-o c-purple",
                        'state': {
                            'selected': false
                        },
                        'children': [{
                            'text': 'Document 1',
                            "icon": "fa fa-folder-o c-purple",
                        }, {
                            'text': 'Document 2',
                            "icon": "fa fa-folder-o c-purple",
                        }]
                    }, {
                        'text': 'Events',
                        "icon": "fa fa-calendar c-green",
                        'state': {
                            'opened': false,
                            'selected': false
                        }
                    }, {
                        'text': 'Messages',
                        "icon": "fa fa-envelope-o",
                        'state': {
                            'opened': false,
                            'selected': false
                        }
                    }, ]
                }
            });
            for(key in currentElement["productRequests"]){
                //tenderRequestsTable.append("<tr><td>"+currentElement["productRequests"][key]["filial"]["name"]+"</td>" +
                //    "<td>"+currentElement["productRequests"][key]["filial"]["name"]+"</td>" +
                //    "</tr>");
            }
            modal6.modal("show");
        });

    });
    function returnTenderDataListItem(key,data){
        switch (key){
            case "active":
                return "<tr><td>სტატუსი: </td><td>"+(data?"აქტიური":"არა აქტიური")+"</td></tr>";
            case "createDate":
                return "<tr><td>შექმნის დრო: </td><td>"+moment(new Date(data)).locale("ka").format("LLLL") +"</td></tr>";
            case "startDate":
                return "<tr><td>დაწყების დრო: </td><td>"+moment(new Date(data)).locale("ka").format("LLLL") +"</td></tr>";
            case "endDate":
                return "<tr><td>დასრულების დრო: </td><td>"+moment(new Date(data)).locale("ka").format("LLLL") +"</td></tr>";
            case "name":
                return "<tr><td>ტენდერის სახელი:</td><td>"+ data +"</td></tr>";
            case "ended":
                return "<tr><td>დასრულებული: </td><td>"+(data?"დასრულდა":"არ დასრულებულა")+"</td></tr>";
            case "bids":
                return "<tr><td>ბიჯების რაოდენობა:</td><td>"+ data.length +"</td></tr>";
            case "winningBid":
                return "<tr><td>ტენდერი მოგიო:</td><td>"+ (data?(data.user.name+" "+data.user.surname):"არ არის ცნობილი")+"</td></tr>";
        }
    }
}