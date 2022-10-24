<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<%
//Variable de control de mensajes
String varMsj = request.getParameter("msj")==null?"":request.getParameter("msj");

//Recuperacion de credenciales
String userName = "";
userName = request.getParameter("us");
userName = userName==null?"":userName;
String userEmail = "";
userEmail = request.getParameter("em");
userEmail = userEmail==null?"":userEmail;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
 

    <title>Portal ACP - Cambiar Contraseña</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Font Awesome Custom -->
    <script src="https://kit.fontawesome.com/12d21ebefe.js" crossorigin="anonymous"></script>

    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/styles1.css">

	<!-- Icon -->
	<jsp:include page="imgShortIcon.jsp" />  
		
	<!-- jAlert css  -->
	<link rel="stylesheet" href="jAlert/dist/jAlert.css" />
</head>

<body>

    <div class="container">

        <!-- Outer Row -->
        <div class="row justify-content-center">

            <div class="col-xl-10 col-lg-12 col-md-9 mt-5">

                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0">
                        <!-- Nested Row within Card Body -->
                        <div class="row">
                            <div class="col-lg-6 d-none d-lg-block bg-password-image"></div>
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-2">
                                            Cambiar Contraseña</h1>
                                        <p class="mb-4">Ingrese nueva contraseña</p>
                                    </div>
                                    <form class="usuario" name="usuario" method="post" action="./Sl_RecuperarContra" onsubmit="return comprobarContra()">
                                       <input type="hidden" name="opcion" value="2">
                                       	<input type="hidden" name="user" value="<%=userName%>">
                                       	<input type="hidden" name="email" value="<%=userEmail%>">                                        
                                        <div class="form-group input-group">                                       
                                         <input type="password" class="form-control form-control-user"
                                                id="newPwd" name="newPwd1"
                                                placeholder="Ingresa su contraseña.." required>
                                               <div class="input-group-append">
									            <button id="show_password" class="btn btn-primary" type="button" onclick="mostrarPassword()"> <span class="fa fa-eye-slash icon"></span> </button>
									          </div>
                                         </div>
                                          <div class="form-group">
                                            <input type="password" class="form-control form-control-user"
                                                id="newPwd2" name="newPwd2"
                                                placeholder="Ingrese nuevamente su contraseña..." required>
                                        </div> 
                                        <input type="submit" class="btn btn-primary btn-user btn-block" value="Cambiar Contraseña">
                                    </form>
                                    <hr>
                                    <div class="text-center">
                                        <a class="small" href="login.jsp"><i class="fas fa-arrow-circle-left"></i>&nbsp;Volver</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>
    
    <!-- jAlert js -->
	<script src="jAlert/dist/jAlert.min.js"></script>
	<script src="jAlert/dist/jAlert-functions.min.js"></script>
	
	<script>
	//Mostrar contrasena en los inputs
    function mostrarPassword(){
		var cambio = document.getElementById("newPwd");
		if(cambio.type == "password"){
			cambio.type = "text";
			$('.icon').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
		}else{
			cambio.type = "password";
			$('.icon').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
		}
		var cambio2 = document.getElementById("newPwd2");
 		if(cambio2.type == "password"){
 			cambio2.type = "text";
 			$('.icon').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
 		}else{
 			cambio2.type = "password";
 			$('.icon').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
 		}
	}
  //Validar contrasena
	function comprobarContra(){
	    clave1 = document.usuario.newPwd.value;
	    clave2 = document.usuario.newPwd2.value;	
	    if (clave1 != clave2){
	    	errorAlert('Error', '¡Contraseñas no coinciden!');
	    	return false;
	    }	    
	}
	</script>
</body>
</html>