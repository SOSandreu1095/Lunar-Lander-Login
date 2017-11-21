/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var createMode = false;

$(document).ready(function () {
//Events

    $("#butUp").click(function () {
        $("#butIn").hide();
        $("#butUp").hide();
        $("#butBack").show();
        $("#butCreate").show();
        $("#labName").show();
        $("#inpName").show();
        createMode = true;
    });


    $("#butBack").click(function () {
        $("#butIn").show();
        $("#butUp").show();
        $("#butBack").hide();
        $("#butCreate").hide();
        $("#labName").hide();
        $("#inpName").hide();
        createMode = false;
    });

    $("#butIn").click(function () {
        if (checkLoginFields()) {
            sendLoginInformation();
        }
    });

    $("#butCreate").click(function () {
        if (checkCreateUserFields()) {
            sendCreateUserInformation();
        }
    });

    $("#inpPass").on('keyup', function (e) {
        if (e.keyCode == 13) {
            if (createMode == false) {
                $("#butIn").click();
            } else {
                $("#butCreate").click();
            }
        }
    });

    $("#inpUser").on('keyup', function (e) {
        if (e.keyCode == 13) {
            if (createMode == false) {
                $("#butIn").click();
            } else {
                $("#butCreate").click();
            }
        }
    });

    $("#inpName").on('keyup', function (e) {
        if (e.keyCode == 13) {
            $("#butCreate").click();
        }
    });

});


function checkLoginFields() {
    if ($("#inpUser").val().length == 0) {
        $("#inpUser").focus();
        alert("INSERT YOUR USERNAME");
        return false;
    }
    if ($("#inpPass").val().length == 0) {
        $("#inpPass").focus();
        alert("INSERT YOUR PASSWORD");
        return false;
    }
    return true;
}

function checkCreateUserFields() {
    if ($("#inpUser").val().length == 0) {
        $("#inpUser").focus();
        alert("INSERT YOUR USERNAME");
        return false;
    }
    if ($("#inpPass").val().length == 0) {
        $("#inpPass").focus();
        alert("INSERT YOUR PASSWORD");
        return false;
    }
    if ($("#inpName").val().length == 0) {
        $("#inpName").focus();
        alert("INSERT YOUR NAME");
        return false;
    }
    return true;
}

function sendLoginInformation() {

    var url = "loginServlet";
    var u = $("#inpUser").val();
    var p = $("#inpPass").val();
    $.ajax({
        method: "POST",
        url: url,
        data: {username: u, password: p},
        success: function (rsp) {
            alert(rsp["mess"]);
            location.reload();
            //document.location.href="./loginServlet";
        },
        error: function (e) {
            alert("Failed");
            if (e["responseJSON"] === undefined)
                alert("emess");
            else
                alert(e["responseJSON"]["error"]);
        }
    });
}

/**
 * 
 * @returns {undefined}
 */
function sendCreateUserInformation() {
    var url = "createUserServlet";
    var n = $("#inpName").val();
    var u = $("#inpUser").val();
    var p = $("#inpPass").val();
    $.ajax({
        method: "POST",
        url: url,
        data: {name: n, username: u, password: p},
        success: function (rsp) {
            alert(rsp["mess"]);
        },
        error: function (e) {
            alert("Failed");
            if (e["responseJSON"] === undefined)
                alert("emess");
            else
                alert(e["responseJSON"]["error"]);
        }
    });
}