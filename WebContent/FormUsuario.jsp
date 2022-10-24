<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
import="vistas.*, entidades.*, datos.*, java.util.*;"%>   
<%
	response.setHeader( "Pragma", "no-cache" );
	response.setHeader( "Cache-Control", "no-store" );
	response.setDateHeader( "Expires", 0 );
	response.setDateHeader( "Expires", -1 );
	
	ViewRolUsuario vru = new ViewRolUsuario();
	Dt_RolOpcion dtro = new Dt_RolOpcion();
	ArrayList<ViewRolOpcion> listOpc = new ArrayList<ViewRolOpcion>();
	
	//OBTENEMOS LA SESION
	vru = (ViewRolUsuario)session.getAttribute("acceso");
	if(vru==null){
		response.sendRedirect("login.jsp?msj=401");
	}
	else{
		//OBTENEMOS LA LISTA DE OPCIONES ASIGNADAS AL ROL
		listOpc = dtro.listaRolOpc(vru.getRolid());
		
		//RECUPERAMOS LA URL = MI OPCION ACTUAL
		int index = request.getRequestURL().lastIndexOf("/");
		String miPagina = request.getRequestURL().substring(index+1);
		boolean permiso = false; //VARIABLE DE CONTROL
		
		//VALIDAR SI EL ROL CONTIENE LA OPCION ACTUAL DENTRO DE LA MATRIZ DE OPCIONES
		for(ViewRolOpcion vrop : listOpc){
			if(vrop.getOpcion().trim().equals(miPagina.trim())){
				permiso = true; //ACCESO CONCEDIDO
				break;
			}
		}
		
		if(!permiso){
			response.sendRedirect("401.jsp");
		}	
	}
	
	//Variable de control de mensajes
	String varMsj = request.getParameter("msj")==null?"":request.getParameter("msj");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

   <title>Portal ACP - Formulario Usuario</title>
   
    <!-- Icon -->
	<jsp:include page="imgShortIcon.jsp" />  
  
    <!-- Custom fonts for this template -->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    
    <!-- jAlert css  -->
	<link rel="stylesheet" href="jAlert/dist/jAlert.css" />
</head>

<body id="page-top">
    <!-- Page Wrapper -->
    <div id="wrapper">

     <!-- Menus -->
	 <jsp:include page="adminMenus.jsp" />    
        
                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Formulario -->
                      <div class="container">
                        <div class="row ">
                            <div class="col-lg-10 mx-auto m-auto">
                                <div class="card rounded shadow border-0">
                                    <div class="card-header">
                                        <h2>
                                           Formulario Usuario
                                        </h2>
                                    </div>
                                    <div class="card-body bg-white rounded">                           
                                <form class="usuario" name="usuario" method="post" action="./Sl_GestionUsuario" onsubmit="return comprobarContra()">
								<!-- El valor de este input es para el Servlet opcion guardar -->
                            	<input name="opcion" type="hidden" value="1" />
                            	<div class="form-group row">
                                    <div class="col-sm-12 mb-3">
                                        <label>Nombres:</label>
                                        <input type="text" class="form-control form-control-user" name="txtNombres" id="txtNombres"
                                            minlength="3" maxlength="80" required>
                                            <small id="message"></small> 
                                    </div>
                                    <div class="col-sm-12 mb-3">
                                       <label>Apellidos:</label>	
                                        <input type="text" class="form-control form-control-user" name="txtApellidos" id="txtApellidos"
                                           minlength="3" maxlength="100" required>
                                           <small id="message1"></small> 
                                    </div>                                   
                               		 <div class="col-sm-12 ">
                                  	   <label>Teléfono(Opcional):</label>                                  
                                        <input type="tel" class="form-control form-control-user" name="txtTelefono" id="txtTelefono"
                                          minlength="8" maxlength="15" pattern="[0-9]{8}">
                                    </div>
                                </div>
                                <div class="form-group row">                                
                                    <div class="col-sm-12 mb-3">
                                  	   <label>Usuario:</label>                                  
                                        <input type="text" class="form-control form-control-user" name="txtUserName" id="txtUserName"
                                          minlength="4" maxlength="40"  required>
                                          <small id="message2"></small> 
                                    </div>
                                    <div class="col-sm-12 mb-3">
                                       <label>Correo:</label>                                                              
                                        <input type="email" class="form-control form-control-user" name="txtEmail" id="txtEmail"
                                            minlength="8" maxlength="75" required>
                                    </div>
                                    <label class="col-sm-12 mb-3 ">Contraseña:</label>
                                    <div class="col-sm-12 mb-3 input-group">
                                        <input type="password" class="form-control form-control-user" name="txtPwd" id="txtPwd"
                                           placeholder="Contraseña" minlength="8" maxlength="32" required>
                                   		    <div class="input-group-append">
									            <button id="show_password" class="btn btn-primary" type="button" onclick="mostrarPassword()"> <span class="fa fa-eye-slash icon"></span> </button>
									          </div>	          
						               </div>
                                    <div class="col-sm-12">
                                        <input type="password" class="form-control form-control-user" name="txtPwd2" id="txtPwd2"
                                           minlength="8" maxlength="32" placeholder="Repetir Contraseña"  required>
                                    </div>
                                </div>
	                            <hr>
	                            <div class="text-center form-group">
	                                <input class="btn btn-primary btn-user btn-block" type="submit"  value="Guardar" />
	                            </div>	                           
	                            <div style="text-align:center;"><a href="GestionUsuario.jsp"><i
                                              class="fas fa-arrow-circle-left"></i>&nbsp;Volver a la tabla</a></div>               
                           	</form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.container-fluid -->
            </div>
            <!-- End of Main Content -->
            
            <!-- Footer -->
            <jsp:include page="adminFooter.jsp" />    
       
    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
    <jsp:include page="adminLogOutModal.jsp" />    
        
    <!-- JAVASCRIPTS -->
    <link rel="stylesheet" href="vendor/datatables/jquery.dataTables.js">

    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="vendor/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="js/demo/datatables-demo.js"></script>
    
    <!-- jAlert js -->
	<script src="jAlert/dist/jAlert.min.js"></script>
	<script src="jAlert/dist/jAlert-functions.min.js"> //optional!!</script>
    
<script>
	$(document).ready(function ()
    {    	
		/////////// VARIABLE DE CONTROL MSJ ///////////
        var mensaje = "";
        mensaje = "<%=varMsj%>";
		
        if(mensaje == "existe"){
        	$.jAlert({
	               'title': 'Error',
	               'content': '¡Usuario ingresado ya existe!',
	               'theme': 'red',
	               'onClose': function(OnClose) {               
	                   window.location = "FormUsuario.jsp";
	               }
	        });        	
        }
        if(mensaje == "existec"){
        	$.jAlert({
	               'title': 'Error',
	               'content': '¡Correo ingresado ya existe!',
	               'theme': 'red',
	               'onClose': function(OnClose) {               
	                   window.location = "FormUsuario.jsp";
	               }
	        });        	
        } 
    });
	
	//Validar contrasena
	function comprobarContra(){
	    clave1 = document.usuario.txtPwd.value;
	    clave2 = document.usuario.txtPwd2.value;	
	    if (clave1 != clave2){
	    	errorAlert('Error', '¡Contraseña no coinciden!');
	    	return false;
	    }	    
	}
	
	//Mostrar contrasena en los inputs
    function mostrarPassword(){
		var cambio = document.getElementById("txtPwd");
		if(cambio.type == "password"){
			cambio.type = "text";
			$('.icon').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
		}else{
			cambio.type = "password";
			$('.icon').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
		}
		var cambio2 = document.getElementById("txtPwd2");
 		if(cambio2.type == "password"){
 			cambio2.type = "text";
 			$('.icon').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
 		}else{
 			cambio2.type = "password";
 			$('.icon').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
 		}
	}  
        
  //Funcion para mostrar maximo de caracteres en los inputs y textareas    
    $('#txtNombres').on("keyup", function(e) {
        var textLength = $('#txtNombres').val().replace(' ', '1').length;
        var maxValue = 80;
        $("#message").text(textLength+" de "+maxValue+" carácteres permitidos");
       
    });
    $('#txtApellidos').on("keyup", function(e) {
        var textLength = $('#txtApellidos').val().replace(' ', '1').length;
        var maxValue = 100;
        $("#message1").text(textLength+" de "+maxValue+" carácteres permitidos");
       
    });
    $('#txtUserName').on("keyup", function(e) {
        var textLength = $('#txtUserName').val().replace(' ', '1').length;
        var maxValue = 40;
        $("#message2").text(textLength+" de "+maxValue+" carácteres permitidos");       
    });
</script>
</body>
</html>