<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
import="entidades.Rol, datos.Dt_Rol, java.util.*;"%>
<%
	response.setHeader( "Pragma", "no-cache" );
	response.setHeader( "Cache-Control", "no-store" );
	response.setDateHeader( "Expires", 0 );
	response.setDateHeader( "Expires", -1 );
	
	String mensaje = request.getParameter("msj");
	mensaje=mensaje==null?"":mensaje;
	
	HttpSession hts = request.getSession(false);
	hts.removeAttribute("acceso");
	hts.invalidate();
	
	//Variable de control de mensajes
	String varMsj = request.getParameter("msj")==null?"":request.getParameter("msj");
	
	String codigo = request.getParameter("codverif");
	codigo=codigo==null?"":codigo;
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Portal Arboreto Carmelo Palma</title>
     
     <!-- Icon -->
	<jsp:include page="imgShortIcon.jsp" />  
	

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom  CSS -->
    <link rel="stylesheet" href="css/styles1.css">
    
     <!-- jAlert css  -->
	<link rel="stylesheet" href="jAlert/dist/jAlert.css" />    
</head>

<body>
    <div class="container">

        <!-- Outer Row -->
        <div class="row justify-content-center mt-5">

            <div class="col-xl-10 col-lg-12 col-md-9">

                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0">
                        <!-- Nested Row within Card Body -->
                        <div class="row">
                            <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-4 font-weight-bold">¡Bienvenido!</h1>
                                    </div>
                                    <form class="user" method="post" action="./Sl_Login">
                                        <div class="form-group">
                                        	<input type="hidden" name="codverificacion" value="<%=codigo%>">
                                            <input type="text" class="form-control form-control-user"
                                                id="userName" name="userName" aria-describedby="emailHelp"
                                                placeholder="Usuario">
                                        </div>
                                        <div class="form-group input-group">
                                            <input type="password" class="form-control form-control-user"
                                                id="pwd" name="pwd" placeholder="Contraseña">
                                                <div class="input-group-append">
									            <button id="show_password" class="border border-dark" type="button" style="opacity: 0.5;" onclick="mostrarPassword()"> <span class="fa fa-eye-slash icon"></span> </button>
									          </div>
                                        </div>
                                         <div class="form-group">
                                            <%
			                                	ArrayList<Rol> listRol = new ArrayList<Rol>();
			                                	Dt_Rol dtr = new Dt_Rol();
												listRol = dtr.listaRolActivos();
                                			%>
                                    		<select class=" form-control-user w-100" name="cbxRol" id="cbxRol" required>
		                                    	<option value="" selected disabled>Seleccione...</option>
		                                    	<%
		                                    		for(Rol r: listRol){
		                                    	%>	
		                                    		<option value="<%=r.getIdRol()%>"><%=r.getRol()%></option>
		                                    	<%
		                                    		}
		                                    	%>
                                    		</select>
                                        </div>
                                        <input type="submit" value="Iniciar sesión" class="btn btn-success btn-user btn-block" />
                                        <input type="submit" value="Cancelar" class="btn btn-google btn-user btn-block" />
                                        <hr>
                                    </form>
                                    <hr>
                                    <div class="text-center">
                                        <a class="small" href="RecuperarContra.jsp">¿Olvidar contraseña?</a>
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
	 function mostrarPassword(){
			var cambio = document.getElementById("pwd");
			if(cambio.type == "password"){
				cambio.type = "text";
				$('.icon').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
			}else{
				cambio.type = "password";
				$('.icon').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
			}
		}   
	 
    $(document).ready(function ()
    {        
	/////////// VARIABLE DE CONTROL MSJ ///////////
        var mensaje = "";
        mensaje = "<%=varMsj%>";
	
        if(mensaje == "1")
        {
            $.jAlert({
	               'title': 'Éxito',
	               'content': '¡Contraseña actualizada exitosamente!',
	               'theme': 'green',
	               'onClose': function(OnClose) {               
	                   window.location = "login.jsp";
	               }
	         });
        }
        if(mensaje == "403")
        {
            $.jAlert({
	               'title': 'Error',
	               'content': '¡Revise los datos e intente nuevamente!',
	               'theme': 'red',
	               'onClose': function(OnClose) {               
	                   window.location = "login.jsp";
	               }
	         });            
        }
    });
	</script>
</body>

</html>