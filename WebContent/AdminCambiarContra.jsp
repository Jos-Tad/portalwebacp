<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" 
import="vistas.ViewPais, datos.Dt_Pais,  entidades.Rol,vistas.ViewRolUsuario, 
datos.Dt_Region, entidades.Region,vistas.ViewRolOpcion, datos.Dt_Rol,datos.Dt_RolOpcion,datos.Dt_Usuario,entidades.Usuario,java.util.*;" %>
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
	
	int usuarioid = 0;
	String imagen = null;
	String usuario = null;
	String email = null;
	String nombres = null;
	String apellidos = null;
	String telefono = null;
	
	if(vru != null){
		usuarioid = vru.getUsuarioid();
		Usuario user = new Usuario();
		Dt_Usuario dtu = new Dt_Usuario();
		user = dtu.getUsuario((usuarioid));
		
		//Cargar datos
		imagen = user.getUrl_foto();
		usuario = user.getUser();
		email = user.getEmail();
		nombres = user.getNombre();
		apellidos = user.getApellido();
		telefono = user.getTelefono();
	}		
	
	
	String varMsj = request.getParameter("msj")==null?"":request.getParameter("msj");	
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  
    <title>Portal ACP - Cambiar Contraseña</title>
    
     <!-- Icon -->
	 <jsp:include page="imgShortIcon.jsp" />  
    
    <link rel="icon" href="img/logo_jesuita.ico" type="img/ico">
  
    <!-- Custom fonts for this template -->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    
    <!-- Caracteres -->
	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
	<link href="css/progressCircle.css" rel="stylesheet" type="text/css">
	
	  <!-- jAlert css  -->
	<link rel="stylesheet" href="jAlert/dist/jAlert.css" />
	
<style>		
	.form-control {
	    border: 1px solid #cfd1d8;
	    -webkit-border-radius: 2px;
	    -moz-border-radius: 2px;
	    border-radius: 2px;
	    font-size: .825rem;
	    background: #ffffff;
	    color: #2e323c;
	}
	
	.card {
	    background: #ffffff;
	    -webkit-border-radius: 5px;
	    -moz-border-radius: 5px;
	    border-radius: 5px;
	    border: 0;
	    margin-bottom: 1rem;
	}	
	</style>
</head>
<body id="page-top" onload="actualizarImagen()">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Menus -->
  		 <jsp:include page="adminMenus.jsp" />   
        
                <!-- Begin Page Content -->
                <div class="container-fluid">
				<div class="container">				
				<form class="usuario" name="usuario" method="post" action="./Sl_RecuperarContra" onsubmit="return comprobarContra()">
              	<input name="opcion" type="hidden" value="3" />	
              	<input name="usuarioV" type="hidden" value="<%=usuario%>" />	              	
              	<input name="usuarioid" type="hidden" value="<%=usuarioid%>" />	
              	<div class="row">													
				<div class="col-lg-10 mx-auto">				
				<div class="card card rounded shadow border-0">
					<div class="card-body">
						<div class="row justify-content-md-center">						
							<div class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
								<h6 class="text-primary">Cambiar contraseña</h6>
							</div>	
							<div class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
							<label for="txtContra">Contraseña Actual:</label>
								<div class="form-group input-group">									
									<input type="password" class="form-control" id="txtContraActual" name="txtContraActual" minlength="8" maxlength="32" >
									 <div class="input-group-append">
									    <button id="show_passwordA" class="btn btn-primary btn-sm" type="button" onclick="mostrarPassword1()"> <span class="fa fa-eye-slash icon1"></span> </button>
									 </div>
								</div>
							</div>								
							<div class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
							<label for="txtContra">Nueva Contraseña:</label>
								<div class="form-group input-group">									
									<input type="password" class="form-control" id="txtContra" name="txtContra" minlength="8" maxlength="32" >
									 <div class="input-group-append">
									    <button id="show_password" class="btn btn-primary btn-sm" type="button" onclick="mostrarPassword()"> <span class="fa fa-eye-slash icon"></span> </button>
									 </div>
								</div>
							</div>
							<div class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
							<label for="txtContra2">Repetir Contraseña:</label>
								<div class="form-group input-group">									
									<input type="password" class="form-control" id="txtContra2" name="txtContra2" minlength="8" maxlength="32">
								</div>
							</div>															
						</div>										
						<div class="row gutters">
							<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
								<div class="text-center">
									<button type="submit" class="btn btn-primary" >Guardar</button>
								</div>								 
							</div>
						</div>										
					</div>
				</div>
				</div>
				</div>
				</form>
				</div>
				</div>				 							           
                <!-- Termina Formulario -->
                </div>
                <!-- /.container-fluid -->

        
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
    
    	<!-- Circle Progress -->
	<script src="js/circle-progress.js"></script>
    
     <!-- jAlert js -->
	<script src="jAlert/dist/jAlert.min.js"></script>
	<script src="jAlert/dist/jAlert-functions.min.js"></script>
	
    
<script>
	
	$(document).ready(function() 
		{			
		    var mensaje = "";
	        mensaje = "<%=varMsj%>";
			
	        if(mensaje == "1"){
	        	$.jAlert({
		               'title': 'Éxito',
		               'content': '¡Contraseña actualizada con éxito!',
		               'theme': 'green',
		               'onClose': function(OnClose) {               
		                   window.location = "AdminCambiarContra.jsp";
		               }
		         });
	        }
	        if(mensaje == "2"){
	        	$.jAlert({
		               'title': 'Error',
		               'content': '¡Revise los datos e intente nuevamente!',
		               'theme': 'red',
		               'onClose': function(OnClose) {               
		                   window.location = "AdminCambiarContra.jsp";
		               }
		         });
	        }
	        if(mensaje == "pwdE"){
	        	$.jAlert({
		               'title': 'Error',
		               'content': '¡Contraseña actual incorrecta!',
		               'theme': 'red',
		               'onClose': function(OnClose) {               
		                   window.location = "AdminCambiarContra.jsp";
		               }
		         });
	        }
	       		        		    	
	 });
		
	//Validar contrasena
	function comprobarContra(){
	    clave1 = document.usuario.txtContra.value
	    clave2 = document.usuario.txtContra2.value
	
	    if (clave1 != clave2){
        	errorAlert('Error', '¡Contraseña no coinciden!');
        	return false;
	    }	    
	}
	
	//Mostrar contrasena en los inputs
    function mostrarPassword(){
		var cambio = document.getElementById("txtContra");
		if(cambio.type == "password"){
			cambio.type = "text";
			$('.icon').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
		}else{
			cambio.type = "password";
			$('.icon').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
		}
		var cambio2 = document.getElementById("txtContra2");
 		if(cambio2.type == "password"){
 			cambio2.type = "text";
 			$('.icon').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
 		}else{
 			cambio2.type = "password";
 			$('.icon').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
 		}
	}
    function mostrarPassword1(){
		var cambio = document.getElementById("txtContraActual");
		if(cambio.type == "password"){
			cambio.type = "text";
			$('.icon1').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
		}else{
			cambio.type = "password";
			$('.icon1').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
		}		
	}  
 </script>  
</body>
</html>