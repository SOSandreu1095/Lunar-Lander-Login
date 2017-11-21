var y = 5; // altura inicial y0=10%, debe leerse al iniciar si queremos que tenga alturas diferentes dependiendo del dispositivo
var v = 0;
var g = 1.622;
var a = g; //a= -g es para motor encendido
var dt = 0.016683;
var timer;
var gasolina = 100;
var dificultad = "media";
var gasolinaTotal = 100;
var intentos = 1;
var modeloNave = "estandar";
var modeloLuna = "amarilla";
var timerFuel = null;

var arrayConfiguraciones = [];

window.onload = function () {

    mostrarMenuPrincipal();
    cambiarDificultad();
    cambiarModeloNave();
    cambiarModeloLuna();
    //initConfig();

    $("#btnDeteteConfig").click(function () {
        //borrarConfiguracion();
    });


    $("#btnLoad").click(function () {
        var i = $("#selectConf").val();
        dificultad = arrayConfiguraciones[i].dificultad;
        modeloNave = arrayConfiguraciones[i].modeloNave;
        modeloLuna = arrayConfiguraciones[i].modeloLuna;

        cambiarDificultad();
        cambiarModeloNave();
        cambiarModeloLuna();
    });

    $("#btnNewConfig").click(function () {
        mostrarAjustes();
    });

    $("#btnSave").click(function () {
       // saveConfig()();
    });


    //CAMBIAR LA DIFICULTAD DEL JUEGO
    $("#dificultadFac").click(function () {
        dificultad = "facil";
        cambiarDificultad();
    });
//    document.getElementById("dificultadFac").onclick = function () {
//        dificultad = "facil";
//        cambiarDificultad();
//    }

    $("#dificultadMed").click(function () {
        dificultad = "media";
        cambiarDificultad();
    });
//    document.getElementById("dificultadMed").onclick = function () {
//        dificultad = "media";
//        cambiarDificultad();
//    }

    $("#dificultadDif").click(function () {
        dificultad = "dificil";
        cambiarDificultad();
    });
//    document.getElementById("dificultadDif").onclick = function () {
//        dificultad = "dificil";
//        cambiarDificultad();
//    }

    //CAMBIAR LA IMAGEN DE LA LUNA
    $("#modeloLunaAma").click(function () {
        modeloLuna = "amarilla";
        cambiarModeloLuna();
    });
//    document.getElementById("modeloLunaAma").onclick = function () {
//        modeloLuna = "amarilla";
//        cambiarModeloLuna();
//    }

    $("#modeloLunaGri").click(function () {
        modeloLuna = "gris";
        cambiarModeloLuna();
    });
//    document.getElementById("modeloLunaGri").onclick = function () {
//        modeloLuna = "gris";
//        cambiarModeloLuna();
//    }

    //CAMBIAR LA IMAGEN DE LA NAVE Y EL MOTOR
    $("#modeloNaveEst").click(function () {
        modeloNave = "estandar"
        cambiarModeloNave();
    });
//    document.getElementById("modeloNaveEst").onclick = function () {
//        modeloNave = "estandar"
//        cambiarModeloNave();
//    }

    $("#modeloNaveSW").click(function () {
        modeloNave = "starWars"
        cambiarModeloNave();
    });
//    document.getElementById("modeloNaveSW").onclick = function () {
//        modeloNave = "starWars"
//        cambiarModeloNave();
//    }

    //CAPTURA SI EL DISPOSITIVO RECIBE EVENTOS OUNTOUCH (SMARTPHONE)
    function is_touch_device() {
        if ('ontouchstart' in window) {
            document.getElementById("botonOn").style.display = "inline-block";
        }
    }
    is_touch_device();
    //CAPTURANDO EVENTOS DEL PANEL DERECHA
    $("#renaudar").click(function () {
        renaudar();
    });
    $("#pausa").click(function () {
        pausar();
    });
    $("#reinicia").click(function () {
        reiniciarJuego();
    });
    $("#instrucciones").click(function () {
        mostrarInstrucciones();
    });
//    document.getElementById("reanudar").onclick = function () {
//        reanudar();
//    };
//    document.getElementById("pausa").onclick = function () {
//        pausar();
//    };
//    document.getElementById("reinicia").onclick = function () {
//        reiniciarJuego();
//    };
//    document.getElementById("instrucciones").onclick = function () {
//        mostrarInstrucciones();
//    };


    $("#botonAjustes").click(function () {
        $("#myModal").modal("show");
    });
//    document.getElementById("botonAjustes").onclick = function () {
//        $("#myModal").modal("show");
//    };
    //CAPTURANDO EVENTOS PARA EL PANcEL DERECHO EN SMARTPHONE
    $("#reanudaSmartphone").click(function () {
        reanudarSmartphone();
    });
    $("#pausaSmartphone").click(function () {
        pausarSmartphone();
    });
    $("#reiniciaSmartphone").click(function () {
        reiniciarJuegoSmartphone();
    });
    $("#ayudaSmartphone").click(function () {
        mostrarInstruccionesSmartphone();
    });
    $("#botonAjustesSmartphone").click(function () {
        mostrarAjustesSmartphone();
    });
//    document.getElementById("reanudaSmartphone").onclick = function () {
//        reanudarSmartphone();
//    };
//    document.getElementById("pausaSmartphone").onclick = function () {
//        pausarSmartphone();
//    };
//    document.getElementById("reiniciaSmartphone").onclick = function () {
//        reiniciarJuegoSmartphone();
//    };
//    document.getElementById("ayudaSmartphone").onclick = function () {
//        mostrarInstruccionesSmartphone();
//    };
//    document.getElementById("botonAjustesSmartphone").onclick = function () {
//        mostrarAjustesSmartphone();
//    };
    //EVENTOS DE FIN DEL JUEGO
    $("#jugarOtraVez").click(function () {
        reiniciarJuego();
    });
//    document.getElementById("jugarOtraVez").onclick = function () {
//        reiniciarJuego();
//    };
    $("#jugarOtraVezSmartphone").click(function () {
        reiniciarJuegoSmartphone();
    });
//    document.getElementById("jugarOtraVezSmartphone").onclick = function () {
//        reiniciarJuegoSmartphone();
//    };
    $("#jugarAgain").click(function () {
        reiniciarJuego();
    });
//    document.getElementById("jugarAgain").onclick = function () {
//        reiniciarJuego();
//    };
    $("#jugarAgainSmartphone").click(function () {
        reiniciarJuegoSmartphone();
    });
//    document.getElementById("jugarAgainSmartphone").onclick = function () {
//        reiniciarJuegoSmartphone();
//    };

    //ASIGNAR EVENTOS TOUCH SCREEN PARA LA VERSION SMARTPHONE
    var botonOnSmartphone = document.getElementById("botonOn");
    botonOnSmartphone.addEventListener("touchstart", handlerFunction, false);
    botonOnSmartphone.addEventListener("touchend", endingFunction, false);
    function handlerFunction(event) {
        encenderMotor();
    }
    function endingFunction(event) {
        apagarMotor();
    }

    //CON TECLADO (tecla ESPACIO)
    window.onkeydown = function (e) {
        var claveTecla;
        if (window.event)
            claveTecla = window.event.keyCode;
        else if (e)
            claveTecla = e.which;
        if ((claveTecla == 32))
        {
            encenderMotor();
        }
    }
    window.onkeyup = apagarMotor;

}//TERMINA EL WINDOW.ONLOAD


//FUNCION EMPEZAR EL JUEGO
function start() {
    timer = setInterval(function () {
        moverNave();
    }, dt * 1000);
}

//FUNCION PARAR NAVE Y CONTROLADORES
function stop() {
    clearInterval(timer);
}

//FUNCION PARA QUE CAIGA LA NAVE A TRAVES DE LA PANTALLA
function moverNave() {
    v += a * dt;
    document.getElementById("velocidad").innerHTML = v.toFixed(2);
    y += v * dt;
    document.getElementById("altura").innerHTML = y.toFixed(2);
    //mover hasta que top sea un 70% de la pantalla
    if (y < 70) {
        document.getElementById("nave").style.top = y + "%";
    } else {
        stop();
        finalizarJuego();
    }
}

//HACER QUE LOS DIVS IZQUIERDA Y DERECHA NO RECIBAN EVENTOS ONCLICK
function eventosOff() {
    document.getElementById("izquierda").style.pointerEvents = 'none';
    document.getElementById("derecha").style.pointerEvents = 'none';
}
//HACER QUE LOS DIVS IZQUIERDA Y DERECHA SI RECIBAN EVENTOS ONCLICK
function eventosOn() {
    document.getElementById("izquierda").style.pointerEvents = 'auto';
    document.getElementById("derecha").style.pointerEvents = 'auto';
}

//FUNCION PARA ACABAR EL JUEGO
function finalizarJuego() {
    if (v > 5) {
        switch (modeloNave) {
            case "estandar":
                eventosOff();
                document.getElementById("imgNave").src = "img/nave_rota.gif";
                document.getElementById("gameOver").style.display = "block";
                document.getElementById("intentos").innerHTML = intentos;
                break;
            case "starWars":
                eventosOff();
                document.getElementById("imgNave").src = "img/mod2rota.gif";
                document.getElementById("gameOver").style.display = "block";
                document.getElementById("intentos").innerHTML = intentos;
                break;
        }
    } else {
        document.getElementById("userWin").style.display = "block";
        eventosOff();
    }
}

//FUNCION QUE ACTUA EN CUANTO SE ENCIENDE EL MOTOR
function encenderMotor() {
    a = -g;
    document.getElementById("fuel").innerHTML = porcentajeGasolina();
    document.getElementById("fuel").style.color = "rgb(" + 0 + (100 - porcentajeGasolina()) + "%, 0%, 0%)";
    document.getElementById("imgMotor").style.display = "block";
    if (timerFuel == null) {
        timerFuel = setInterval(function () {
            actualizarGasolina();
        }, 100);
    }
    if (gasolina <= 0) {
        apagarMotor();
        document.getElementById("fuel").innerHTML = 0;
    }
}

//CALCULO EL PORCENTAJE DE GASOLINA QUE QUEDA
function porcentajeGasolina() {
    var result = gasolina * 100 / gasolinaTotal;
    return result.toFixed(0);
}


//FUNCION QUE ACTUALIZA EL MARCADOR DE FUEL
function actualizarGasolina() {
    gasolina -= 1;
    document.getElementById("fuel").innerHTML = porcentajeGasolina();
    document.getElementById("fuel").style.color = "rgb(" + 0 + (100 - porcentajeGasolina()) + "%, 0%, 0%)";
    if (gasolina <= 0) {
        apagarMotor();
        document.getElementById("fuel").innerHTML = 0;
    }
}
//FUNCION QUE RESPONDE AL MOMENTO DE APAGAR EL MOTOR DE LA NAVE
function apagarMotor() {
    a = g;
    document.getElementById("imgMotor").style.display = "none";
    clearInterval(timerFuel);
    timerFuel = null;
}

function mostrarAjustes() {
    pausar();
    eventosOff();
    document.getElementById("settings").style.display = "block";
}
function ocultarAjustes() {
    document.getElementById("settings").style.display = "none";
    eventosOn();
}

function mostrarInstrucciones() {
    pausar();
    eventosOff();
    document.getElementById("menuInstrucciones").style.display = "block";
}

function ocultarInstrucciones() {
    document.getElementById("menuInstrucciones").style.display = "none";
    eventosOn();
}

function restart() {
    stop();
    y = 5; // altura inicial y0=10%, debe leerse al iniciar si queremos que tenga alturas diferentes dependiendo del dispositivo
    v = 0;
    g = 1.622;
    a = g;
    dt = 0.016683;
    gasolina = gasolinaTotal;
    document.getElementById("fuel").innerHTML = porcentajeGasolina();
    document.getElementById("fuel").style.color = "black";
}
//OJO COMPORTAMIENTO ESCRITORIO
function reiniciarJuego() {
    stop();
    document.getElementById("reanudar").style.display = "none";
    document.getElementById("pausa").style.display = "inline-block";
    intentos++;
    y = 5; // altura inicial y0=10%, debe leerse al iniciar si queremos que tenga alturas diferentes dependiendo del dispositivo
    v = 0;
    g = 1.622;
    a = g;
    dt = 0.016683;
    gasolina = gasolinaTotal;
    document.getElementById("fuel").innerHTML = porcentajeGasolina();
    document.getElementById("fuel").style.color = "black";
    reanudar();
    clearInterval(timer);
    start();
    eventosOn();
    document.getElementById("intentos").innerHTML = intentos;
    document.getElementById("gameOver").style.display = "none";
    document.getElementById("userWin").style.display = "none";
    cambiarModeloNave();
}

function reanudar() {
    stop();
    start();
    document.getElementById("reanudar").style.display = "none";
    document.getElementById("pausa").style.display = "inline-block";
}
function pausar() {
    stop();
    document.getElementById("pausa").style.display = "none";
    document.getElementById("reanudar").style.display = "inline-block";
}

//OJO COMPORTAMIENTO SMARTPHONE
function reanudarSmartphone() {
    start();
    document.getElementById("reanudaSmartphone").style.display = "none";
    document.getElementById("pausaSmartphone").style.display = "inline-block";
    document.getElementById("reiniciaSmartphone").style.display = "none";
    document.getElementById("ayudaSmartphone").style.display = "none";
    document.getElementById("botonAjustesSmartphone").style.display = "none";
    document.getElementById('izquierda').style.display = "inline-block";
    document.getElementById('nave').style.display = "inline-block";
    document.getElementById('zonaAterrizaje').style.display = "inline-block";
    document.getElementById('derechaSmartphone').style.backgroundImage = 'url(img/sol.png)';
    document.getElementById('derechaSmartphone').style.backgroundSize = '60%';
    document.getElementById('derechaSmartphone').style.backgroundRepeat = 'no-repeat';
    document.getElementById('derechaSmartphone').style.width = '35%';
}

function pausarSmartphone() {
    stop();
    document.getElementById("pausaSmartphone").style.display = "none";
    document.getElementById("reanudaSmartphone").style.display = "inline-block";
    document.getElementById("reiniciaSmartphone").style.display = "inline-block";
    document.getElementById("ayudaSmartphone").style.display = "inline-block";
    document.getElementById("botonAjustesSmartphone").style.display = "inline-block";
    document.getElementById('derechaSmartphone').style.backgroundImage = 'url(img/fondo_menu.jpg)';
    document.getElementById('derechaSmartphone').style.backgroundSize = 'auto';
    document.getElementById('derechaSmartphone').style.backgroundRepeat = 'repeat';
    document.getElementById('derechaSmartphone').style.width = '100%';
}

function reiniciarJuegoSmartphone() {
    stop();
    intentos++;
    y = 5; // altura inicial y0=10%, debe leerse al iniciar si queremos que tenga alturas diferentes dependiendo del dispositivo
    v = 0;
    g = 1.622;
    a = g;
    dt = 0.016683;
    gasolina = gasolinaTotal;
    document.getElementById("fuel").innerHTML = porcentajeGasolina();
    document.getElementById("fuel").style.color = "black";
    reanudarSmartphone();
    clearInterval(timer);
    start();
    eventosOn();
    document.getElementById("intentos").innerHTML = intentos;
    document.getElementById("gameOver").style.display = "none";
    document.getElementById("userWin").style.display = "none";
    if (modeloNave == 1) {
        document.getElementById("imgNave").src = "img/nave.png";
    } else {
        document.getElementById("imgNave").src = "img/mod2nave.gif";
    }
}

function mostrarAjustesSmartphone() {
    pausarSmartphone();
    document.getElementById("settings").style.display = "block";
}

function mostrarInstruccionesSmartphone() {
    pausarSmartphone();
    document.getElementById("menuInstrucciones").style.display = "block";
}

function cambiarModeloNave() {
    switch (modeloNave) {
        case "starWars":
            document.getElementById("imgNave").src = "img/mod2nave.gif";
            document.getElementById("imgMotor").src = "img/mod2motor.gif";
            document.getElementById("modeloNaveSW").style.color = '#39e600';
            document.getElementById("modeloNaveEst").style.color = '#000000';
            //document.getElementById("modeloNave").innerHTML = "Modelo PodRacer";
            //modeloNave = 2;
            break;
        case "estandar":
            document.getElementById("imgNave").src = "img/nave.png";
            document.getElementById("imgMotor").src = "img/motor.gif";
            document.getElementById("modeloNaveEst").style.color = '#39e600';
            document.getElementById("modeloNaveSW").style.color = '#000000';
            //document.getElementById("modeloNave").innerHTML = "Modelo Estándar";
            //modeloNave = 1;
            break;
    }
}

function cambiarModeloLuna() {
    switch (modeloLuna) {
        case "gris":
            document.getElementById("luna").src = "img/mod2luna.png";
            document.getElementById("modeloLunaGri").style.color = '#39e600';
            document.getElementById("modeloLunaAma").style.color = '#000000';
            //document.getElementById("modeloLuna").innerHTML = "Gris";
            break;
        case "amarilla":
            document.getElementById("luna").src = "img/luna.png";
            document.getElementById("modeloLunaAma").style.color = '#39e600';
            document.getElementById("modeloLunaGri").style.color = '#000000';
            break;
    }
}

function cambiarDificultad() {
    switch (dificultad) {
        case "media":
            gasolina = 50;
            gasolinaTotal = 50;
            document.getElementById("dificultadMed").style.color = '#39e600';
            document.getElementById("dificultadFac").style.color = '#000000';
            document.getElementById("dificultadDif").style.color = '#000000';
            //document.getElementById("dificultad").innerHTML = "Media";
            break;
        case "dificil":
            gasolina = 25;
            gasolinaTotal = 35;
            document.getElementById("dificultadMed").style.color = '#000000';
            document.getElementById("dificultadFac").style.color = '#000000';
            document.getElementById("dificultadDif").style.color = '#39e600';
            //document.getElementById("dificultad").innerHTML = "Difícil";
            break;
        case "facil":
            gasolina = 100;
            gasolinaTotal = 100;
            document.getElementById("dificultadMed").style.color = '#000000';
            document.getElementById("dificultadFac").style.color = '#39e600';
            document.getElementById("dificultadDif").style.color = '#000000';
            //document.getElementById("dificultad").innerHTML = "Fácil";
            break;

    }
    restart();
}

function initConfig() {
//    pausar();
//    stop();
//    var url = "getFileExc";
//    var emess = "Error desconocido";
//
//    $.ajax({
//        method: "GET",
//        url: url,
//        dataType: 'json',
//        success: function (jsn) {
//            $.each(jsn, function (i, item) {
//                var nombre = jsn[i].nombre;
//                var dif = jsn[i].dificultad;
//                var nav = jsn[i].modeloNave;
//                var lun = jsn[i].modeloLuna;
//                addConfigurationToArray(nombre, dif, nav, lun);
//            });
//        },
//        error: function (e) {
//            if (e["responseJSON"] === undefined)
//                alert(emess);
//            else
//                alert(e["responseJSON"]["error"]);
//        }
//    });
}


function saveConfig() {
    var n = $("#nameConfigText").val();
    var d = dificultad;
    var nav = modeloNave;
    var lun = modeloLuna;

    var emess = "Error desconadafdfafdaocido";
    var url = "getFileExc"; //doPost->SaveFile
    if ((n != null) && (n.length != 0)) {
        $.ajax({
            method: "POST",
            url: url,
            data: {nombre: n, dificultad: d, modeloNave: nav, modeloLuna: lun},
            success: function (rsp) {
                addConfigurationToArray(n, d, nav, lun);
                alert(rsp["mess"]);
            },
            error: function (e) {
                if (e["responseJSON"] === undefined)
                    alert(emess);
                else
                    alert(e["responseJSON"]["error"]);
            }
        });
    } else {
        alert("Introduce una nombre para la configuración");
        $("#nameConfigText").focus();
    }
}

function mostrarMenuPrincipal() {
    pausar();
    $("#myModal").modal("show");

}

function addConfigurationToArray(nombre, dificultad, nave, luna) {
    var c = {nombre: nombre, dificultad: dificultad, modeloNave: nave, modeloLuna: luna};
    arrayConfiguraciones.push(c);

    //Add this config to the select
    var txt = nombre + " ----- (" + dificultad + " - " + nave + " - " + luna + ")";
    $('#selectConf').append($('<option>', {
        value: arrayConfiguraciones.length - 1,
        text: txt
    }));
}

function borrarConfiguracion() {
    if ($('#selectConf').length != 0) {
        var emess = "Error descoeeeeeenocido";
        var url = "deleteConfigServlet";
        var i = $("#selectConf option:selected").index();
        alert(arrayConfiguraciones[i].nombre);
//        arrayConfiguraciones.splice(i, 1);
        $("#selectConf option[value='i']").remove();
        repaintSelect();

//        $.ajax({
//            method: "POST",
//            url: url,
//            data: {nombre: n},
//            success: function (rsp) {
//                arrayConfiguraciones.splice(i,1);
//                alert(rsp["mess"]);
//            },
//            error: function (e) {
//                if (e["responseJSON"] === undefined)
//                    alert(emess);
//                else
//                    alert(e["responseJSON"]["error"]);
//            }
//        });
    } else {
        alert("No hay ninguna configuración para borrar");
    }
}

function repaintSelect() {
//        $('#selectConf').find('option')
//                .remove()
//                .end();
//
//        for (i = 0; i < arrayConfiguraciones.length; i++) {
//            var n = arrayConfiguraciones[i].nombre;
//            var d = arrayConfiguraciones[i].nombre;
//            var nav = arrayConfiguraciones[i].nombre;
//            var lun = arrayConfiguraciones[i].nombre;
//            addConfigurationToArray(n, d, nav, lun);
//        }

}



