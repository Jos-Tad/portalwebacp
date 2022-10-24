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
  
    <title>Portal ACP - Perfil</title>
    
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
	.account-settings .user-profile {
	    margin: 0 0 1rem 0;
	    padding-bottom: 1rem;
	    text-align: center;
	}
	.account-settings .user-profile .user-avatar {
	    margin: 0 0 1rem 0;
	}
	.account-settings .user-profile .user-avatar img {
	    width: 100px;
	    height: 100px;
	    -webkit-border-radius: 120px;
	    -moz-border-radius: 120px;
	    border-radius: 120px;
	}
	.account-settings .user-profile h5.user-name {
	    margin: 0 0 0.5rem 0;
	}
	.account-settings .user-profile h6.user-email {
	    margin: 0;
	    font-size: 0.8rem;
	    font-weight: 400;
	    color: #9fa8b9;
	}
	
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
				<form class="usuario" name="usuario" method="post" action="./Sl_EditPerfilUsuario" onsubmit="return comprobarContra()" enctype="multipart/form-data">
              	<input name="opcion" type="hidden" value="1" />	
                <input name="usuarioV" type="hidden" value="<%=usuario%>" />	              	
              	<input name="usuarioid" type="hidden" value="<%=usuarioid%>" />	
              	<div class="row">													
				<div class="col-lg-10 mx-auto">				
				<div class="card card rounded shadow border-0">
					<div class="card-body">
					<div class="account-settings">
							<div class="user-profile">						
								<div class="user-avatar">
									<img id="preview" src="<%=imagen==null?"img/user.svg":imagen%>" alt="Imagen Usuario">
								</div>
								<h5 class="user-name"><%=usuario%></h5>
								<h6 class="user-email"><%=email %></h6>
							</div>											
						</div>
						<div class="row justify-content-md-center">						
							<div class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
								<h6 class="text-primary">Información del Perfil</h6>
							</div>							
							<div class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
								<div class="form-group">
									<label for="txtNombres">Nombres:</label>
									<input type="text" class="form-control" id="txtNombres" name="txtNombres" minlength="3" maxlength="80" required>
								</div>
							</div>
							<div class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
								<div class="form-group">
									<label for="txtApellidos">Apellidos:</label>
									<input type="text" class="form-control" id="txtApellidos" name="txtApellidos" minlength="3" maxlength="100" required>
								</div>
							</div>
							<div class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
								<div class="form-group">
									<label for="txtTelefono">Teléfono:</label>
									<input type="tel" class="form-control" id="txtTelefono" name="txtTelefono"  minlength="8" maxlength="15" pattern="[0-9]{8}">
								</div>
							</div>
							<div class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
								<div class="form-group">
									<label for="txtUsuario">Usuario:</label>
									<input type="text" class="form-control" id="txtUsuario" name="txtUsuario" minlength="4" maxlength="40" required>
								</div>
							</div>
							<div class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
								<div class="form-group">
									<label for="txtCorreo">Correo:</label>
									<input type="email" class="form-control" id="txtCorreo" name="txtCorreo"  minlength="8" maxlength="75" required>
								</div>
							</div>
							<div class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
								<div class="form-group">
									<label for="phone">Imagen:</label>									
                                     <div class="custom-file">
									    <label class="custom-file-label text-left" for="customFile" id="filmultUs" name="filmultUs">ImagenUsuario.jpg</label>
									    <input type="file" class="custom-file-input" id="multUs" name="multUsuario" onchange="Test.UpdatePreview(this)" accept="image/jpeg" title="ImagenUsuario.jpg">
										<input type="hidden" name="url_foto" value="<%=imagen%>">
									</div>
								</div>
							</div>
							<div class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
							<label for="txtContra">Contraseña Actual:</label>
								<div class="form-group input-group">									
									<input type="password" class="form-control" id="txtContra" name="txtContra" minlength="8" maxlength="32" required >
									 <div class="input-group-append">
									    <button id="show_password" class="btn btn-primary btn-sm" type="button" onclick="mostrarPassword()"> <span class="fa fa-eye-slash icon"></span> </button>
									 </div>
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
        

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

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
			//Setear valores
			$("#txtNombres").val("<%=nombres%>");
			$("#txtApellidos").val("<%=apellidos%>");
			$("#txtTelefono").val("<%=telefono%>");
			$("#txtUsuario").val("<%=usuario%>");
			$("#txtCorreo").val("<%=email%>");			
			
			
		    var mensaje = "";
	        mensaje = "<%=varMsj%>";
			
	        if(mensaje == "1"){
	        	$.jAlert({
		               'title': 'Éxito',
		               'content': '¡Información actualizada con éxito!',
		               'theme': 'green',
		               'onClose': function(OnClose) {               
		                   window.location = "Perfil.jsp";
		               }
		         });
	        }
	        if(mensaje == "2"){
	        	$.jAlert({
		               'title': 'Error',
		               'content': '¡Revise los datos e intente nuevamente!',
		               'theme': 'red',
		               'onClose': function(OnClose) {               
		                   window.location = "Perfil.jsp";
		               }
		         });
	        }
	        if(mensaje == "existe"){
	        	$.jAlert({
		               'title': 'Error',
		               'content': '¡Usuario ingresado ya existe!',
		               'theme': 'red',
		               'onClose': function(OnClose) {               
		                   window.location = "Perfil.jsp";
		               }
		         });	        	
	        }
	        if(mensaje == "pwdE"){
	        	$.jAlert({
		               'title': 'Error',
		               'content': '¡Contraseña incorrecta!',
		               'theme': 'red',
		               'onClose': function(OnClose) {               
		                   window.location = "Perfil.jsp";
		               }
		         });	        	
	        }
		        
	    	Test = {
	    	        UpdatePreview: function(obj)
	    	        {
	    	          if(!window.FileReader)
	    	          {
	    	             
	    	          } 
	    	          else 
	    	          {
	    	             var reader = new FileReader();
	    	             var target = null;
	    	             
	    	             reader.onload = function(e) 
	    	             {
	    	              target =  e.target || e.srcElement;
	    	               $("#preview").prop("src", target.result);
	    	             };
	    	              reader.readAsDataURL(obj.files[0]);
	    	          }
	    	        }
	    	    };    		    	
	 });
		
		
	//Cambiar vista de input file
	$('#multUs').on("change",function() {
	     var i = $(this).prev('label').clone();
	      var file = $('#multUs')[0].files[0].name;
	      $(this).prev('label').text(file);
	    });
		
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
 
	//Cache imagen
	function actualizarImagen(){		  
 		 var img = document.getElementById('preview').getAttribute('src');
		 var valor = img+"?t="+ new Date().getTime();
		 document.getElementById("preview").src = valor;		
	}
 </script>  
</body>
</html>