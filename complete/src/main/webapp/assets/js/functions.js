/**
 * Created by KGelashvili on 10/26/2015.
 */



$.getJSON("/getsessionstatus", function (result) {
    if (!result["isactive"]) {
        eraseCookie("projectSessionId");
        window.location.href = "/login.html";
    }
});
$(document).ready(function () {
    console.log($("#loadParcelsButton"));

    $("#logoutBtn").click(function () {
        $.getJSON("/logout", function (result) {
            if (result) {
                eraseCookie("projectSessionId");
                window.location.href = "/login.html";
            }
        })

    });
    var navigation = $("#navigationUl");
    if (readCookie("projectUserType") === "1" || readCookie("projectUserType") === "2") {

        navigation.append('<li id="loadProductsButton" class="k">' +
            '<a href="#"><i class="icon-note"></i><span data-translate="პროდუქცია">პროდუქცია</span></a></li>');
        navigation.append('<li id="loadFilialsButton" class="k">' +
            '<a href="#"><i class="icon-screen-desktop"></i><span data-translate="ფილიალები">ფილიალები</span></a></li>');
        navigation.append('<li id="loadUsersButton" class="k">' +
            '<a href="#"><i class="icon-picture"></i><span data-translate="მომხმარებლები">მომხმარებლები</span></a></li>');
        navigation.append('<li id="loadProductRequestsButton" class="k">' +
            '<a href="#"><i class="icon-note"></i><span data-translate="პროდუქციის მოთხოვნები">პროდუქციის მოთხოვნები</span></a></li>');
        navigation.append('<li id="loadZonesButton" class="k">' +
            '<a href="#"><i class="icon-layers"></i><span data-translate="ზონები">ზონები</span></a></li>');
        $("#loadFilialsButton").click(function () {
            $(".k").attr("class", "k");
            $(this).attr("class", "k nav-active active");
            $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark"><i class="fa fa-plus"></i>ახალი ფილიალის დამატება </button>')
            $("#addNewButton").click(function () {
                $("#myModalLabel").html("ახალი ფილიალის დამატება")
                var modalBody = $("#modalBody");
                modalBody.html(filialsRegistrationFormTemplate);
                $('#myModal').modal("show");
                $("#registrationModalSaveButton").unbind()
                $("#registrationModalSaveButton").click(function () {
                    var registerData = {
                        name: $("#nameField").val().trim(),
                        address: $("#addressField").val().trim()
                    }
                    var valid = true;
                    for (key in registerData) {
                        if (registerData[key] == "") {
                            valid = false
                        }
                    }
                    if (valid) {
                        $.ajax({
                            url: "/createfilial",
                            method: "POST",
                            data: registerData
                        }).done(function (msg) {
                            if (msg) {
                                loadFilialsData(0, "")
                                $('#myModal').modal("hide");
                            } else {
                                $('#myModal').modal("hide");
                                alert("მოხმდა შეცდომა. შეცდომის ხშირი განმეორების შემთხვევაში დაუკავშირდით ადმინისტრაციას.")
                            }
                        })
                    } else {
                        alert("შეავსეთ ყველა ველი რეგისტრაციისთვის")
                    }

                    console.log(registerData);
                })
            })
            loadFilialsData(0, "");
        });
        $("#loadUsersButton").click(function () {
            $(".k").attr("class", "k");
            $(this).attr("class", "k nav-active active");
            loadUsersData(0, "");
        });
        $("#loadProductsButton").click(function () {
            $(".k").attr("class", "k");
            $(this).attr("class", "k nav-active active");
            $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark"><i class="fa fa-plus"></i>ახალი ფორმატის დამატება </button>')
            $("#addNewButton").click(function () {
                $("#myModalLabel").html("ახალი ფორმატის დამატება");
                var modalBody = $("#modalBody");
                modalBody.html(formatRegistrationFormTemplate);
                $('#myModal').modal("show");
                $("#registrationModalSaveButton").unbind()
                $("#registrationModalSaveButton").click(function () {
                    var registerData = {
                        name: $("#nameField").val().trim(),
                        price: $("#priceField").val().trim()
                    }
                    var valid = true;
                    for (key in registerData) {
                        if (registerData[key] == "") {
                            valid = false
                        }
                    }
                    if (valid) {
                        $.ajax({
                            url: "api/createformat",
                            method: "POST",
                            data: registerData
                        }).done(function (msg) {
                            if (msg) {
                                loadFormatsData(0, "")
                                $('#myModal').modal("hide");
                            } else {
                                $('#myModal').modal("hide");
                                alert("მოხმდა შეცდომა. შეცდომის ხშირი განმეორების შემთხვევაში დაუკავშირდით ადმინისტრაციას.")
                            }
                        })
                    } else {
                        alert("შეავსეთ ყველა ველი რეგისტრაციისთვის")
                    }


                })
            })
            loadProductsData(0, "");


        });
        $("#loadProductRequestsButton").click(function () {
            $(".k").attr("class", "k");
            $(this).attr("class", "k nav-active active");
            $("#addNewDiv").html('');
            $("#addNewButton").click(function () {
                $("#myModalLabel").html("ახალი სერვისის ტიპის დამატება");
                var modalBody = $("#modalBody");
                modalBody.html(serviceTypeRegistrationFormTemplate);
                $('#myModal').modal("show");
                $("#registrationModalSaveButton").unbind()
                $("#registrationModalSaveButton").click(function () {
                    var registerData = {
                        name: $("#nameField").val().trim(),
                        price: $("#priceField").val().trim()
                    }
                    var valid = true;
                    for (key in registerData) {
                        if (registerData[key] == "") {
                            valid = false
                        }
                    }
                    if (valid) {
                        $.ajax({
                            url: "api/createservicetype",
                            method: "POST",
                            data: registerData
                        }).done(function (msg) {
                            if (msg) {
                                loadProductRequestsData()
                                $('#myModal').modal("hide");
                            } else {
                                $('#myModal').modal("hide");
                                alert("მოხმდა შეცდომა. შეცდომის ხშირი განმეორების შემთხვევაში დაუკავშირდით ადმინისტრაციას.")
                            }
                        })
                    } else {
                        alert("შეავსეთ ყველა ველი რეგისტრაციისთვის")
                    }


                })
            })
            loadProductRequestsData(0);

        });
        $("#loadZonesButton").click(function () {
            $(".k").attr("class", "k");
            $(this).attr("class", "k nav-active active");
            $("#addNewDiv").html('<button id="addNewButton" data-target="#myModal" class="btn btn-sm btn-dark"><i class="fa fa-plus"></i>ახალი ზონის დამატება</button>')
            $("#addNewButton").click(function () {

                $("#myModalLabel").html("ახალი ზონის დამატება");
                var modalBody = $("#modalBody");
                modalBody.html(zoneRegistrationFormTemplate);
                $.getJSON("api/getregions", function (result) {
                    if (result) {
                        for (i = 0; i < result.length; i++) {
                            $("#regionIdField").append("<option value='" + result[i]["id"] + "'>" + result[i]["name"] + "</option>")
                        }
                    }
                })
                $('#myModal').modal("show");
                $("#registrationModalSaveButton").unbind();
                $("#registrationModalSaveButton").click(function () {
                    var registerData = {
                        zoneName: $("#nameField").val().trim(),
                        regionId: $("#regionIdField").val().trim()
                    }
                    var valid = true;
                    for (key in registerData) {
                        if (registerData[key] == "") {
                            valid = false
                        }
                    }
                    if (valid) {
                        $.ajax({
                            url: "api/createzone",
                            method: "POST",
                            data: registerData
                        }).done(function (msg) {
                            if (msg) {
                                loadZonesData()
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
            })
            loadZonesData()
        })
    }

    if (readCookie("projectUserType") === "3") {
        navigation.append('<li id="loadProductsButton" class="k">' +
            '<a href="#"><i class="icon-note"></i><span data-translate="პროდუქცია">პროდუქცია</span></a></li>');
        $("#loadProductsButton").click(function () {
            $(".k").attr("class", "k");
            $(this).attr("class", "k nav-active active");
            loadProductsData(0, "");


        });
    }

    if (readCookie("projectUserType") === "1" || readCookie("projectUserType") === "2" || readCookie("projectUserType") === "3") {
        canCreateUsers = true;
    }
    if (readCookie("projectUserType") === "1" || readCookie("projectUserType") === "2") {
        canCreateProduct = true;
    }


    loadProductsData(0, "");
});

