<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
 import="entidades.Servicio, datos.Dt_Servicio, entidades.Rol,vistas.ViewRolUsuario, vistas.ViewRolOpcion, datos.Dt_Rol,datos.Dt_RolOpcion,java.util.*;" %>
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
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  
      <title>Portal ACP - Formulario Editar Servicio</title>
      
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
							  <%
                            	String srID = "";
							 	srID = request.getParameter("idS")==null?"0":request.getParameter("idS");
														
								Servicio sr = new Servicio();
								Dt_Servicio dts = new Dt_Servicio();
								sr = dts.getServicio(Integer.parseInt(srID));						
                         	   %>
                    <!-- Formulario -->
   			   		  <div class="container">
                        <header class="text-center text-white">
                            <script src="https://kit.fontawesome.com/a41f4b8198.js" crossorigin="anonymous"></script>
                        </header>
                        <div class="row">
                            <div class="col-lg-10 mx-auto m-auto">
                                <div class="card rounded shadow border-0">

                                    <div class="card-header">
                                        <h3 class="card-title text-left">Formulario Editar Servicio</h3>
                                    </div>
                                    <div class="card-body">
                                      <form class="Servicio" method="post" action="./Sl_GestionServicio" enctype="multipart/form-data">
                                         <input name="servicioid" type="hidden" value="<%=sr.getServicioid()%>" />  
                                       	<input name="usuarioid" type="hidden" value="<%=usuarioid%>" />                    					                                                      
                      					<input name="opcion" type="hidden" value="2" />
                                      <div class="form-group">
                                                <label for="nombreS" class="form-label fw-bolder">Nombre:</label>
                                                <input type="text" class="form-control" id="nombreServicio" name="nombreServicio" minlength="5" maxlength="200" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="descripciónS"
                                                    class="form-label fw-bolder">Descripción:</label>
                                                <textarea rows="4" class="form-control" id="descripcionServicio" name= "descripcionServicio" minlength="5" maxlength="350" required></textarea>
                                            </div>
                                            <div class="form-group">
                                                <label for="custom-file">Imagen:</label>
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text">Archivo</span>
                                                    </div>
                                                   <div class="custom-file">
													    <label class="custom-file-label text-left" for="customFile" id="filmultSer" name="filmultSer">Servicio.jpg</label>
													    <input type="file" class="custom-file-input" id="multSer" name="multSer" onchange="Test.UpdatePreview(this)" accept="image/jpeg" title="Servicio.jpg" >
														<input type="hidden" name="url_foto" value="<%=sr.getMultimedia()%>">									
													</div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                             <label>Estado:</label>  
                                                <select class="form-control"  id="cbxEstadoServicio" name="cbxEstadoServicio">                                            	
                                    			<option value="">Seleccionar</option>                                    			                                            	
                                    			<option value="1">Disponible</option>
                                    			<option value="2">No disponible</option>
                                    	        </select>
                                    	      </div>                                            
                                            <div class="mb-3">
                                                <button class="btn btn-primary" style="width: 100%;">Guardar</button>
                                            </div>
                                            <div style="text-align:center;"><a href="GestionServicio.jsp"><i
                                                        class="fas fa-arrow-circle-left"></i>&nbsp;Volver a la tabla</a></div>
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
	<script src="jAlert/dist/jAlert-functions.min.js"></script>

<script>  
	  $(document).ready(function()
		{
		  	//Setear valroes
			$("#nombreServicio").val("<%=sr.getNombre()%>");
			$("#descripcionServicio").val("<%=sr.getDescripcion()%>");	
			$("#cbxEstadoServicio").val("<%=sr.getEstadoservicio()%>");
			
			/////////// VARIABLE DE CONTROL MSJ ///////////
	        var mensaje = "";
	        mensaje = "<%=varMsj%>";

	        if(mensaje == "existe")
	        {
	            $.jAlert({
	                'title': 'Error',
	                'content': '¡Servicio ingresado ya existe!',
	                'theme': 'red',
	                'onClose': function(OnClose) {               
	                    window.location = "FormEditarServicio.jsp?idS=" + <%=sr.getServicioid()%> ;
	                }
	              });
	        }
		});
</script>  
	 
<script>
$('#multSer').on("change",function() {
    var i = $(this).prev('label').clone();
     var file = $('#multSer')[0].files[0].name;
     $(this).prev('label').text(file);
});
</script>	
</body>
</html>