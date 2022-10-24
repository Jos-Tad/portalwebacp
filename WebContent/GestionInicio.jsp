<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "entidades.Home, datos.Dt_Home,entidades.Rol,vistas.ViewRolUsuario, vistas.ViewRolOpcion, datos.Dt_Rol,datos.Dt_RolOpcion,java.util.*;"%>
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
	
	//Recuperar informacion del Inicio
	ArrayList<Home> listHome = new ArrayList<Home>();
	Dt_Home dth = new Dt_Home();
	listHome = dth.ListarHome();									
	Home hm = new Home();
	hm = listHome.get(0);	
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
 <title>Portal ACP - Gestión Inicio</title>
    
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
     <title>Portal ACP - Gestión Inicio</title>
    
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

    <!-- Menus -->
	<jsp:include page="adminMenus.jsp" />      
	
                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Formulario -->
                    <div class="container">
                        <header class="text-center text-white">
                            <script src="https://kit.fontawesome.com/a41f4b8198.js" crossorigin="anonymous"></script>
                        </header>
                        <div class="row ">
                            <div class="col-lg-10 m-auto">
                                <div class="card rounded shadow border-0">
                                    <div class="card-header">
                                        <h2 class="card-title text-center">Inicio</h2>
                                    </div>
                                    <div class="card-body">
                                        <form class="Inicio" method="post" action="./Sl_GestionInicio" enctype="multipart/form-data">
                							<!-- El valor de estos input es para el Servlet opcion editar -->                			
                                        	<input name="idInicio" type="hidden" value="<%=hm.getHomeID()%>" />
                                        	<input name="idUsuario" type="hidden" value="<%=usuarioid%>" />                                        	
                            				<input name="opcion" type="hidden" value="1" />
                            				<input type="hidden" id="oldValue" value="0">  
                                            <h3>Historia</h3>
                                            <hr class="bg-dark w-auto">
                                            <div class="form-group">
                                                <label for="custom-file">Imagen:</label>
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text">Archivo</span>
                                                    </div>
                                                   <div class="custom-file">
													    <label class="custom-file-label text-left" for="customFile" id="filmultHistoria">Historia.jpg</label>
													    <input type="file" class="custom-file-input" id="multHistoria" name="multHistoria" accept="image/jpeg" title="Historia.jpg">
													   <input type="hidden" name="urlfotoHistoria" value="<%=hm.getImg_historia()%>">									
													</div>
                                                </div>
                                            </div> 
                                            <div class="mb-3">
                                                <label for="nombreCP" class="form-label fw-bolder">Descripción:</label>
                                                <textarea id="descripcionHis" name = "descripcionHis"   rows="8" class="form-control" minlength="100" maxlength="1000" required><%=hm.getHistoria() %></textarea>
                                            	 <small id="message"></small>
	                                             <div id="circle" data-value="0" data-size="30">
	                              						<small id="percent"></small>
	                        					  </div>					  	                                                                	
                                            </div>
                                            <h3>Misión</h3>
                                            <hr class="bg-dark w-auto">
                                           <div class="form-group">
                                                <label for="custom-file">Imagen:</label>
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text">Archivo</span>
                                                    </div>
                                                   <div class="custom-file">
													    <label class="custom-file-label text-left" for="customFile" id="filmultMision">Mision.jpg</label>
													    <input type="file" class="custom-file-input" id="multMision" name="multMision"  accept="image/jpeg" title="Mision.jpg">
													    <input type="hidden" name="urlfotoMision" value="<%=hm.getImg_mision()%>">																		
													</div>
                                                </div>
                                            </div> 
                                            <div class="mb-3">
                                                <label for="nombreCP" class="form-label fw-bolder">Descripción:</label>
                                                <textarea id="descripcionMis" 	name = "descripcionMis" rows="8" class="form-control" minlength="80" maxlength="1000" required><%=hm.getMision() %></textarea>
                                            	<small id="message1"></small>
	                                             <div id="circle1" data-value="0" data-size="30">
	                              						<small id="percent1"></small>
	                        					  </div>
                                            </div>

                                            <h3>Visión</h3>
                                            <hr class="bg-dark w-auto">
                                            <div class="form-group">
                                                <label for="custom-file">Imagen:</label>
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text">Archivo</span>
                                                    </div>
                                                   <div class="custom-file">
													    <label class="custom-file-label text-left" for="customFile" id="filmultVision">Vision.jpg</label>
													    <input type="file" class="custom-file-input" id="multVision" name="multVision"  accept="image/jpeg" title="Vision.jpg">
													    <input type="hidden" name="urlfotoVision" value="<%=hm.getImg_vision()%>">																												
													</div>
                                                </div>
                                            </div> 
                                            <div class="mb-3">
                                                <label for="nombreCP" class="form-label fw-bolder">Descripción:</label>
                                                <textarea id="descripcionVis" name = "descripcionVis" rows="8" class="form-control" minlength="80" maxlength="1000" required><%=hm.getVision() %></textarea>
                               	                <small id="message2"></small>
	                                             <div id="circle2" data-value="0" data-size="30">
	                              						<small id="percent2"></small>
	                        					  </div>                                           	
                                            </div>
                                      	 	<div class="text-center">
				                                <input class="btn btn-primary btn-user btn-block" type="submit" value="Guardar" />
				                            </div>  				                            
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <!--termina formulario-->
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
   $(document).ready(function ()
    {      
	/////////// VARIABLE DE CONTROL MSJ ///////////
        var mensaje = "";
        mensaje = "<%=varMsj%>";

        if(mensaje == "1")
        {
            $.jAlert({
                'title': 'Éxito',
                'content': '¡Información actualizada exitosamente!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "GestionInicio.jsp";
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
                    window.location = "GestionInicio.jsp";
                }
             });
        }        
     });
   
   //Actualizar nombre del archivo 
   $('#multHistoria').on("change",function() {
	     var i = $(this).prev('label').clone();
	      var file = $('#multHistoria')[0].files[0].name;
	      $(this).prev('label').text(file);
   });
   
   $('#multMision').on("change",function() {
	     var i = $(this).prev('label').clone();
	      var file = $('#multMision')[0].files[0].name;
	      $(this).prev('label').text(file);
    });
   
   $('#multVision').on("change",function() {
	     var i = $(this).prev('label').clone();
	      var file = $('#multVision')[0].files[0].name;
	      $(this).prev('label').text(file);
    });  	    
  
   //Funcion para mostrar maximo de caracteres en los inputs y textarea
    $('#descripcionHis').on("keydown", function(e) {
        var textLength = $('#descripcionHis').val().replace(' ', '1').length + 1;
        var maxValue = 1000;
        console.log(e.keyCode);
        if (textLength > maxValue) {
			if(e.keyCode != 8){
			e.preventDefault();
			}                      	
        }
     });
   
    $('#descripcionHis').on("keyup", function(e) {
        var textLength = $('#descripcionHis').val().replace(' ', '1').length;
        var maxValue = 1000;
        $("#message").text(textLength+" de "+maxValue+" carácteres permitidos");
        var percent = (textLength * 100) / maxValue;
        var circlePercent = ((textLength * 100) / maxValue) / 100;
        $('#circle').circleProgress({
            animationStartValue: $('#oldValue').val(),
            value: circlePercent,
            size: 30,
            fill: {
                gradient: ["green", "lime"]
            },
        });
        percent = percent > 100 ? 100 : percent;
        $("#percent").text(percent+"%");
        $('#oldValue').val(circlePercent);
    });	
        
    $('#descripcionMis').on("keydown", function(e) {
        var textLength = $('#descripcionMis').val().replace(' ', '1').length + 1;
        var maxValue = 1000;
        console.log(e.keyCode);
        if (textLength > maxValue) {
			if(e.keyCode != 8){
			e.preventDefault();
		}	                        	
        }
     });
    
    $('#descripcionMis').on("keyup", function(e) {
        var textLength = $('#descripcionMis').val().replace(' ', '1').length;
        var maxValue = 1000;
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
    
    $('#descripcionVis').on("keydown", function(e) {
        var textLength = $('#descripcionVis').val().replace(' ', '1').length + 1;
        var maxValue = 1000;
        console.log(e.keyCode);
        if (textLength > maxValue) {
			if(e.keyCode != 8){
			e.preventDefault();
			}	                        	
        }
     });
    
    $('#descripcionVis').on("keyup", function(e) {
        var textLength = $('#descripcionVis').val().replace(' ', '1').length;
        var maxValue = 1000;
        $("#message2").text(textLength+" de "+maxValue+" carácteres permitidos");
        var percent = (textLength * 100) / maxValue;
        var circlePercent = ((textLength * 100) / maxValue) / 100;
        $('#circle2').circleProgress({
            animationStartValue: $('#oldValue').val(),
            value: circlePercent,
            size: 30,
            fill: {
                gradient: ["green", "lime"]
            },
        });
        percent = percent > 100 ? 100 : percent;
        $("#percent2").text(percent+"%");
        $('#oldValue').val(circlePercent);
    });
</script>
</body>
</html>