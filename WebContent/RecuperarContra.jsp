<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<%
//Variable de control de mensajes
String varMsj = request.getParameter("msj")==null?"":request.getParameter("msj");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
 

    <title>Portal ACP - Contraseña</title>

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
                                            ¿Olvidaste tu contraseña?</h1>
                                        <p class="mb-4">Lo entendemos, pasan cosas. Sólo tienes que introducir tu
                                            usuario y  dirección de correo electrónico a continuación
                                            ¡Y le enviaremos un enlace para restablecer su contraseña!</p>
                                    </div>
                                    <form class="user" method="post" action="./Sl_RecuperarContra">
                                       <input type="hidden" name="opcion" value="1">  
                                        <div class="form-group">                                       
                                         <input type="text" class="form-control form-control-user"
                                                id="user" name="user"
                                                placeholder="Ingresa usuario" required>
                                         </div>
                                          <div class="form-group">
                                            <input type="email" class="form-control form-control-user"
                                                id="email" name="email"
                                                placeholder="Ingresa Correo electrónico" required>
                                        </div> 
                                        <input type="submit" class="btn btn-primary btn-user btn-block" value="Recuperar contraseña">
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
	  $(document).ready(function ()
	    {
	        
		/////////// VARIABLE DE CONTROL MSJ ///////////
	        var mensaje = "";
	        mensaje = "<%=varMsj%>";
	                             
	        if(mensaje == "1")
	        {
	            $.jAlert({
	                'title': 'Éxito',
	                'content': '¡Se ha enviado un correo electronico. Sigue las instrucciones para restablecer la contraseña!',
	                'theme': 'green',
	                'onClose': function(OnClose) {               
	                    window.location = "RecuperarContra.jsp";
	                }
	              });
	        }
	        if(mensaje == "2")
	        {
	            $.jAlert({
	                'title': 'Error',
	                'content': '¡Revise los datos e intente nuevamente!',
	                'theme': 'red',
	                'onClose': function(OnClose) {               
	                    window.location = "RecuperarContra.jsp";
	                }
	              });
	        }
	  }); 
	</script>

</body>

</html>