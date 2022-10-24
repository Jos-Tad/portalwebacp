<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "entidades.Footer, datos.Dt_Footer, entidades.Rol,vistas.ViewRolUsuario, vistas.ViewRolOpcion, datos.Dt_Rol,datos.Dt_RolOpcion,java.util.*;"
%>
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
	
	ViewRolUsuario vrgu = new ViewRolUsuario();
	vrgu =(ViewRolUsuario) session.getAttribute("acceso");
	
	//Control Usuario
	int usuarioid =0;
	
	if((ViewRolUsuario) session.getAttribute("acceso") == null){
		
	}else{
		vrgu =(ViewRolUsuario) session.getAttribute("acceso");
		usuarioid = vrgu.getUsuarioid();
	}
	//Variable de control de mensajes
	String varMsj = request.getParameter("msj")==null?"":request.getParameter("msj");
	
	//Recuperar informacion del Pie de pagina
	ArrayList<Footer> listFooter = new ArrayList<Footer>();
	Dt_Footer dtf = new Dt_Footer();
	listFooter = dtf.listFooter();										
    String Logo = null;									
	Footer ft = new Footer();
	ft = listFooter.get(0);	
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
 
    <title>Portal ACP - Gestión Pie de Página</title>
    
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
                          <div class="col-lg-11 m-auto">
                              <div class="card rounded shadow border-0">									 
				 			  <div class="card-header">
                                      <h2>
                                          Pie de Página
                                      </h2>
                                  </div>
                                  <div class="card-body bg-white rounded">
                                      <form class="PiePagina" method="post" action="./Sl_GestionPiePagina" enctype="multipart/form-data">
                                      	<!-- El valor de estos input es para el Servlet opcion editar -->                			
                                      	<input name="idFooter" type="hidden" value="<%=ft.getFooterID()%>" />
                                      	<input name="idUsuario" type="hidden" value="<%=usuarioid%>" />  
                                      	<input name="opcion" type="hidden" value="1" />
                                      	                                      	
                                          <div class="form-group">
                                              <label>Dirección:</label>
                                              <input class="form-control" id="direccionFooter" name = "direccionFooter" value="<%=ft.getDescripcion()%>" minlength="10" maxlength="150" required>
                                          	<small id="message"></small>                                            	
                                          </div>
                                          <div class="form-group">
                                              <label>Correo:</label>
                                              <input class="form-control" id = "correoFooter" name = "correoFooter" value="<%=ft.getCorreo()%>" minlength="10" maxlength="75" required>
                                          </div>
                                          <div class="form-group">
                                              <label>Teléfono:</label>
                                              <input type="tel" class="form-control form-control-user" name="telefonoFooter" id="telefonoFooter" value="<%=ft.getTelefono()%>" minlength="8" maxlength="8" pattern="[0-9]{8}">
                                          </div>
                                          <div class="form-group">
                                              <label>Extensión:</label>
                                              <input type="text" class="form-control form-control-user" name="extensionFooter" id="extensionFooter" value="<%=ft.getExtencion()%>" minlength="1" maxlength="5" >
                                          </div>
                                         <div class="form-group">
                                              <label for="custom-file">Imagen:</label>
                                              <div class="input-group mb-3">
                                                  <div class="input-group-prepend">
                                                      <span class="input-group-text">Archivo</span>
                                                  </div>
                                                 <div class="custom-file">
											    <label class="custom-file-label text-left" for="customFile" id="filmultFooter">Footer.png</label>
											    <input type="file" class="custom-file-input" id="multFooter" name="multFooter" onchange="Test.UpdatePreview(this)" accept="image/png" title="Footer.png">
											</div>
                                              </div>
                                          </div> 
                                            <div class="m-3" align="center">
										<img id="preview" src="<%=ft.getLogo()+"?t="+System.currentTimeMillis()%>" name="preview"  alt="Imagen Pie de Página"
											class="img-fluid bg-dark" alt="Responsive image" style="width: 350px; height: 200px; border-bottom-color: white; margin: 2px;" />
										<input type="hidden" name="url_foto" value="<%=ft.getLogo()%>">
									</div>										
                                    	 <div class="text-center">
		                                <input class="btn btn-primary btn-user btn-block" type="submit" value="Guardar" />
		                            </div>                             
                                      </form>
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
                <!-- Termina Formulario -->
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
	<script src="jAlert/dist/jAlert-functions.min.js"></script>
    
<script>		
	    $(document).ready(function() 
		{
	    	
		 /////////// VARIABLE DE CONTROL MSJ ///////////
	        var mensaje = "";
	        mensaje = "<%=varMsj%>";

	        if(mensaje == "1")
	        {
	            $.jAlert({
	                'title': 'Éxito',
	                'content': '¡Información de pie de página actualizada exitosamente!',
	                'theme': 'green',
	                'onClose': function(OnClose) {               
	                    window.location = "GestionPiePagina.jsp";
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
	                    window.location = "GestionPiePagina.jsp";
	                }
	              });
	        }
	        
		 //Función para previsualizar la imagen del banner
	    	Test = {
	    	        UpdatePreview: function(obj)
	    	        {
	    	          // if IE < 10 doesn't support FileReader
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
	    
	   //Actualizar nombre del archivo 
	   $('#multFooter').on("change",function() {
		     var i = $(this).prev('label').clone();
		      var file = $('#multFooter')[0].files[0].name;
		      $(this).prev('label').text(file);
		 });
	   
	  //Funcion para mostrar maximo de caracteres en los inputs y textarea
	   $('#direccionFooter').on("keydown", function(e) {
	       var textLength = $('#direccionFooter').val().replace(' ', '1').length + 1;
	       var maxValue = 150;	       
	       console.log(e.keyCode);
	       if (textLength > maxValue) {
			if(e.keyCode != 8){
			e.preventDefault();
			}                     	
	       }	
	    });
	   
	   $('#direccionFooter').on("keyup", function(e) {
	       var textLength = $('#direccionFooter').val().replace(' ', '1').length;
	       var maxValue = 150;	
	       $("#message").text(textLength+" de "+maxValue+" carácteres permitidos");	      
	   });     
</script>
</body>
</html>