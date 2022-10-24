<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import= "entidades.Publicacion, datos.Dt_Publicacion, entidades.Rol,vistas.ViewRolUsuario, vistas.ViewRolOpcion, datos.Dt_Rol,datos.Dt_RolOpcion,java.util.*"%>
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
<html lang="es">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
  
    <title>Portal ACP - Formulario Publicación</title>
    
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
    
    <!-- Caracteres -->
	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
	<link href="css/progressCircle.css" rel="stylesheet" type="text/css">

</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">
    
    <%            	
	ArrayList<Publicacion> listPost = new ArrayList<Publicacion>();
	Dt_Publicacion dtp = new Dt_Publicacion();
    listPost = dtp.ListaPost();
	
	Publicacion p = new Publicacion();
	
	int publicacionid =0;
	
	if(listPost.size() == 0){
		publicacionid= 1;	
	}	
	else{			
		p = listPost.get(listPost.size() - 1);
		publicacionid = p.getPublicacionid()+1 ;		
	}
%>
        <!-- Menus -->
  		 <jsp:include page="adminMenus.jsp" />    
        
                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Formulario -->
   			   		 <div class="container">
                        <div class="row">
                            <div class="col-lg-10 mx-auto m-auto">
                            <div class="card rounded shadow border-0">

                                <div class="card-header">
                                    <h2>
                                       Formulario Publicación
                                    </h2>

                                </div>
                                <div class="card-body bg-white rounded">
                              		   <form class="Publicacion" method="post" action="./Sl_GestionPublicacion">
                      					<input name="opcion" type="hidden" value="1" />
                      					<input name="publicacionid" type="hidden" value="<%=publicacionid%>" />  
                      					<input name="usuarioid" type="hidden" value="<%=usuarioid%>" />                     					
                                              <div class="form-group">
                                            <label>Titulo:</label>
                                            <input type="text" class="form-control" name= "txtTituloPost" id="txtTituloPost" minlength="10" maxlength="300" required>
                                            	 <small id="message"></small>

                                        </div>
                                        <div class="form-group">      
                                        <label>Descripción:</label>
                                      <textarea rows="4" class="form-control" name = "txtDescripcionPost" id="txtDescripcionPost" minlength="10" maxlength="3000" required></textarea>
                                            	 <small id="message1"></small>
	                                             <div id="circle1" data-value="0" data-size="3000">
	                              						<small id="percent1"></small>
	                        					  </div>
                                         
                                        </div>                                        
                                        <div class="form-group">
                                                    <label for="formGroupExampleInput">Tipo:</label>
                                                    <select class="form-control" id= "cbxEstadoPost" name= "cbxEstadoPost" required>
                                                        <option value= "" selected disabled>Seleccionar...</option>                                                        
                                                        <option value="1">Visible</option>
                                                        <option value="2">No Visible</option>
                                                        <option value="3">Borrador</option>
                                                    </select>
                                                </div>
                                           <div class="text-center">
				                                <input class="btn btn-primary btn-user btn-block" type="submit" value="Guardar" />
				                            </div>
				                            <br>
                                        <div style="text-align:center;"><a href="GestionPublicacion.jsp"><i
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
	
	<script>		
	    $(document).ready(function() 
		{
	    	/////////// VARIABLE DE CONTROL MSJ ///////////
	        var mensaje = "";
	        mensaje = "<%=varMsj%>";

	        if(mensaje == "existe")
	        {
	            errorAlert('Error', 'El Publicación que esta intentando registrar ya existe en la base de datos!');
	        }
	    });
	    
	    $('#txtTituloPost').on("keydown", function(e) {
	        var textLength = $('#txtTituloPost').val().replace(' ', '1').length + 1;
	        var maxValue = 300;
	        
	        console.log(e.keyCode);
	        if (textLength > maxValue) {
				if(e.keyCode != 8){
				e.preventDefault();
				}                     	
	        }

	     });
	    $('#txtTituloPost').on("keyup", function(e) {
	        var textLength = $('#txtTituloPost').val().replace(' ', '1').length;
	        var maxValue = 300;

	        $("#message").text(textLength+" de "+maxValue+" carácteres permitidos");
	       
	    });
	    
	    $('#txtDescripcionPost').on("keydown", function(e) {
	        var textLength = $('#txtDescripcionPost').val().replace(' ', '1').length + 1;
	        var maxValue = 3000;

	        console.log(e.keyCode);
	        if (textLength > maxValue) {
				if(e.keyCode != 8){
				e.preventDefault();
				}               	        	
	        }

	     });
	    
	    $('#txtDescripcionPost').on("keyup", function(e) {
	        var textLength = $('#txtDescripcionPost').val().replace(' ', '1').length;
	        var maxValue = 3000;

	        $("#message1").text(textLength+" de "+maxValue+" carácteres permitidos");

	        var percent = (textLength * 100) / maxValue;
	        var circlePercent = ((textLength * 100) / maxValue) / 100;

	        $('#circle1').circleProgress({
	            animationStartValue: $('#oldValue').val(),
	            value: circlePercent,
	            size: 30,
	            fill: {
	                gradient: ["green", "lime"]
	            },
	        });

	        percent = percent > 100 ? 100 : percent;

	        $("#percent1").text(percent.toFixed(2)+"%");
	        $('#oldValue').val(circlePercent);
	    });	
	 </script>
</body>
</html>