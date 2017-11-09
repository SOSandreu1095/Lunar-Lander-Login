/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
//Events
    $("#butUp").click(function () {
        $("#butIn").hide();
        $("#butUp").hide();
        $("#butBack").show();
        $("#butCreate").show();
        $("#labName").show();
        $("#inpName").show();
    });


    $("#butBack").click(function () {
        $("#butIn").show();
        $("#butUp").show();
        $("#butBack").hide();
        $("#butCreate").hide();
        $("#labName").hide();
        $("#inpName").hide();
    });

    $("#butIn").click(function () {
        if(checkLoginFields()){
            sendLoginInformation();
        }
    });
    
    $("#butCreate").click(function () {
        if(checkCreateFields()){
            sendCreateInformation();
        }
    });
    
});


function checkLoginFields(){
    if ($("#inpUser").val().length == 0){
        $("#inpUser").focus();
        alert("INSERT YOUR USERNAME");
        return false;
    }
    if ($("#inpPass").val().length == 0){
        $("#inpPass").focus();
        alert("INSERT YOUR PASSWORD");
        return false;
    }
    return true;
}

function checkCreateFields(){
    if ($("#inpUser").val().length == 0){
        $("#inpUser").focus();
        alert("INSERT YOUR USERNAME");
        return false;
    }
    if ($("#inpPass").val().length == 0){
        $("#inpPass").focus();
        alert("INSERT YOUR PASSWORD");
        return false;
    }
    if ($("#inpName").val().length == 0){
        $("#inpName").focus();
        alert("INSERT YOUR NAME");
        return false;
    }
    return true;
}

function sendLoginInformation(){
    alert("The informations is send")
}
