<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1" import="entidades.Banner, datos.Dt_Banner, entidades.Rol,vistas.ViewRolUsuario, vistas.ViewRolOpcion, datos.Dt_Rol,datos.Dt_RolOpcion, java.util.*;"%>
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
	
	//Recuperar ID del elemento del banner
	String idBanner = "";
	idBanner = request.getParameter("idB")==null?"0":request.getParameter("idB");
	
	//Cargar los datos del elementos
	ArrayList<Banner> listBanner = new ArrayList<Banner>();
	Dt_Banner dtb = new Dt_Banner();
	listBanner = dtb.ListarBanner();  
	Banner bn = dtb.getBanner(Integer.parseInt(idBanner));	
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Portal ACP - Formulario Editar Banner </title>
    
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
	
	<!-- Caracteres -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
	<link href="css/progressCircle.css" rel="stylesheet" type="text/css">
</head>

<body id="page-top">
    <!-- Page Wrapper -->
    <div id="wrapper">

     <!-- Menu -->
     <jsp:include page="adminMenus.jsp" />    
     
      <!-- Begin Page Content -->
      <div class="container-fluid">
 						
          <!--Formulario-->
          <div class="container">
              <header class="text-center text-white">
                  <script src="https://kit.fontawesome.com/a41f4b8198.js" crossorigin="anonymous"></script>
              </header>
              <div class="row">
                  <div class="col-lg-10 mx-auto m-auto">
                      <div class="card rounded shadow border-0">

                          <div class="card-header">
                              <h3 class="card-title text-left">Formulario Editar Banner</h3>
                          </div>
                          <div class="card-body">
                              <form class = "Banner"  method="post" action="./Sl_GestionBanner" > 
                               <input name="opcion" type="hidden" value="2"/>
                          	<input name="bannerID"  type = "hidden" value="<%=bn.getBannerID()%>" />
                         	   	<input name="usuarioid"  type = "hidden" value="<%=usuarioid%>" />                                                                      	              
                              <div class="form-group">
                                      <label for="formGroupExampleInput">Titulo:</label>
                                      <input type="text" class="form-control" name= "txtEditTituloBanner" id="txtEditTituloBanner" minlength="3" maxlength="80" required>
                              		<small id="message"></small>  
                                  </div>
                                  <div class="mb-3">
                                      <label for="nombreCP" class="form-label fw-bolder">Descripción:</label>
                                      <textarea rows="4" class="form-control" name = "txtEditDescripcionBanner" id="txtEditDescripcionBanner" minlength="3" maxlength="100" required></textarea>
                                  	 <small id="message1"></small>
                                    <div id="circle1" data-value="0" data-size="30">
                     						<small id="percent1"></small>
               					  </div>   
                                  </div>                                                                                    		                                
                               	  	<div class="mb-2 text-center">
			                           <input class="btn btn-primary btn-user btn-block" type="submit" value="Guardar" />
			                       </div>
                      				<br>				                         
                                  <div style="text-align:center;"><a href="GestionBanner.jsp"><i
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
	
    <!-- Circle Progress -->
	<script src="js/circle-progress.js"></script>
	
<script>  
	   $(document).ready(function() 
		{
		  	//Setear valores del elemento del banner
			$("#txtEditTituloBanner").val("<%=bn.getTitulobanner()%>");
		  	$("#txtEditDescripcionBanner").val("<%=bn.getDescripcion()%>");	
			
		    var mensaje = "";
	        mensaje = "<%=varMsj%>";

	        if(mensaje == "existe")
	        {
	            $.jAlert({
		               'title': 'Error',
		               'content': '¡Titulo ingresado ya existe!',
		               'theme': 'red',
		               'onClose': function(OnClose) {               
		                   window.location = "FormEditarBanner.jsp?idB=" + <%=bn.getBannerID()%>;
		               }
		        });
	        }
	     });
		
	    //Funcion para mostrar maximo de caracteres en los inputs y textarea
	    $('#txtEditTituloBanner').on("keydown", function(e) {
	        var textLength = $('#txtEditTituloBanner').val().replace(' ', '1').length + 1;
	        var maxValue = 80;	        
	        console.log(e.keyCode);
	        if (textLength > maxValue) {
				if(e.keyCode != 8){
				e.preventDefault();
				}                     	
	        }
	    });
	  
	    $('#txtEditTituloBanner').on("keyup", function(e) {
	        var textLength = $('#txtEditTituloBanner').val().replace(' ', '1').length;
	        var maxValue = 80;

	        $("#message").text(textLength+" de "+maxValue+" carácteres permitidos");
	    });
	   
	    $('#txtDescripcionBanner').on("keydown", function(e) {
	        var textLength = $('#txtDescripcionBanner').val().replace(' ', '1').length + 1;
	        var maxValue = 100;
	        console.log(e.keyCode);
	        if (textLength > maxValue) {
				if(e.keyCode != 8){
				e.preventDefault();
				}               	        	
	        }
	     });
	    
	    $('#txtEditDescripcionBanner').on("keyup", function(e) {
	        var textLength = $('#txtEditDescripcionBanner').val().replace(' ', '1').length;
	        var maxValue = 100;
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
	        $("#percent1").text(percent+"%");
	        $('#oldValue').val(circlePercent);
	    });		       
</script> 
</body>
</html>