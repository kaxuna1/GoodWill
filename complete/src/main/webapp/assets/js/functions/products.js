/**
 * Created by kakha on 11/12/2015.
 */
function loadProductsData(index, search) {
    $.getJSON("getproducts?index=" + index + "&search=" + search, function (result) {
        $("#dataGridHeader").html("");
        $("#dataGridBody").html("");
        $("#paginationUl").html("");
        for (i = 0; i < productColumns.length; i++) {
            var currentElement = productColumns[i];
            $("#dataGridHeader").append("<th>" + currentElement + "</th>")
        }

        console.log(result);
        currentData = result;
        var dataArray = result["content"];
        var totalPages = result["totalPages"];
        var totalElements = result["totalElements"];
        for (i = 0; i < dataArray.length; i++) {
            var currentElement = dataArray[i];

            $("#dataGridBody").append("<tr>" +
                "<td><input value='"+currentElement["id"]+"' class='checkboxParcel' type='checkbox' /></td>" +
                "<td value='" + i + "' class='gridRow'>" + currentElement["name"] + "</td>" +
                "</tr>");

        }
        var checkboxParcel=$(".checkboxParcel");
        checkboxParcel.unbind();
        checkboxParcel.change(function(){
        })
        var gridRow=$('.gridRow');
        gridRow.css('cursor', 'pointer');
        gridRow.unbind();
        for (i = 0; i < totalPages; i++) {
            $("#paginationUl").append('<li value="' + i + '" class="paginate_button ' + (index == i ? 'active"' : '') + '"<a href="#">' + (i + 1) + '</a></li>');
        }
        $(".paginate_button").click(function () {
            //console.log($(this).val())

            currentPage=$(this).val();
            loadParcelsData($(this).val(), "")

        });
        if (canCreateProduct){
            $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark"><i class="fa fa-plus"></i>ახალი პროდუცქტის დამატება </button>');
            $("#addNewButton").click(function () {
                $("#myModalLabel").html("ახალი პროდუქტის დამატება");
                var modalBody = $("#modalBody");
                modalBody.html(productRegistrationFormTemplate);
                $("#registrationModalSaveButton").unbind();

                $("#registrationModalSaveButton").click(function () {
                    var registerData = {
                        name: $("#nameField").val().trim(),
                        quantType: $("#massTypeIdField").val().trim()
                    }
                    var valid = true;
                    for (key in registerData) {
                        if (registerData[key] == "") {
                            valid = false
                        }
                    }
                    if (valid) {
                        $.ajax({
                            url: "createproduct",
                            method: "POST",
                            data: registerData
                        }).done(function (msg) {
                            if (msg) {
                                loadProductsData(0, "")
                                $('#myModal').modal("hide");
                            } else {
                                $('#myModal').modal("hide");
                                alert("მოხმდა შეცდომა. შეცდომის ხშირი განმეორების შემთხვევაში დაუკავშირდით ადმინისტრაციას.")
                            }
                        })
                    } else {
                        alert("შეავსეთ ყველა ველი რეგისტრაციისთვის")
                    }


                });
                $('#myModal').modal("show");

            })
        }
        else {
            $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark"><i class="fa fa-plus"></i>პროდუქციის მოთხოვნა</button>')
            $("#addNewButton").click(function (){
                $("#myModalLabel4").html("პროდუქციის მოთხოვნა");
                $.getJSON("/getmyfilialproductslist", function (result) {
                    console.log(result);
                    var filialProductsDataTable=$("#productsRequestDataTable2");
                    filialProductsDataTable.html("");
                    for(key in result){
                        filialProductsDataTable.append("<tr><td> "+result[key].name+": <input class='requestInput' type='number' name='"+result[key].id+"'></td></tr>")
                    }
                    var makeRequestBTN=$("#makeRequestBTN");
                    makeRequestBTN.unbind();
                    makeRequestBTN.click(function () {
                        var sendData=[];
                        $(".requestInput").each(function(){
                            if(this.value){
                                sendData.push(JSON.stringify({id:this.name,sum:this.value}));
                            }

                        });
                        $.ajax({
                            url:"/requestProducts",
                            data:{productRequests:JSON.stringify(sendData)},
                            type: "POST"
                        }).done(function (result) {
                            if(result){
                                $('#myModal4').modal("hide");
                                alert("მოთხოვნა მიღებულია");


                            }
                        });
                        console.log(sendData);
                    });

                    $('#myModal4').modal("show");
                });

            });
        }


    })
}

function showGiveZoneDialog(k){
    var popupTemplate =
        '<div id="zoneModal" class="modal fade">' +
        '  <div class="modal-dialog">' +
        '    <div class="modal-content">' +
        '      <div class="modal-header">' +
        '        <button type="button" class="close" data-dismiss="modal">&times;</button>' +
        '        <h4 class="modal-title">ზონის მინიჭება</h4>' +
        '      </div>' +
        '      <div class="modal-body">' +
        '<form>' +
        '<div class="form-group">' +
        '<label for="zoneIdField">ზონა</label>: ' +
        ' <select name="zone" id="zoneIdField"></select></div>' +
        '</div>' +
        '</form>' +
        '      <div class="modal-footer">' +
        '        <button type="button" class="btn btn-primary" id="saveParcelZone" >შენახვა</button>' +
        '        <button type="button" class="btn btn-link" data-dismiss="modal">გაუქმება</button>' +
        '      </div>' +
        '    </div>' +
        '  </div>' +
        '</div>';
    $("#zoneModal").remove();
    $("body").append(popupTemplate);
    var optionsTemplate=""
    $.getJSON("api/getzonesformanager", function (result) {
        for (i = 0; i < result.length; i++) {
            optionsTemplate+="<option value='" + result[i]["id"] + "'>" + result[i]["name"] + "</option>";
        }
        $("#zoneIdField").html(optionsTemplate);


        $("#zoneModal").modal("show")
        $("#saveParcelZone").unbind();
        $("#saveParcelZone").click(function () {
            $.ajax({
                url:'api/giveparcelzone',
                data:{
                    parcelIds: k.toString(),
                    zoneId: $("#zoneIdField").val()
                }
            }).done(function (msg) {
                if(msg){
                    if (msg["code"] == 0) {
                        loadParcelsData(currentPage, "")
                        $('#zoneModal').modal("hide");
                        $('#myModal').modal("hide");
                    } else {
                        alert(msg["message"]);
                    }
                }else{

                }
            })
        })
    })

}