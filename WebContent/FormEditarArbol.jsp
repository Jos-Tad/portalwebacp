<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
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
	
	String arID = "";
 	arID = request.getParameter("idA")==null?"0":request.getParameter("idA");
	    					
	Arbol ar = new Arbol();
	Dt_Arbol dta = new Dt_Arbol();
	ar = dta.getArbol(Integer.parseInt(arID));	
%>



<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

   <title>Portal ACP - Formulario Editar Árbol</title>
   
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
                        <div class="row">
                            <div class="col-lg-10 mx-auto m-auto">
                                <div class="card rounded shadow border-0">

                                    <div class="card-header">
                                        <h2>
                                            Formulario Editar Árbol
                                        </h2>

                                    </div>
                                    <div class="card-body bg-white rounded">
                                    
                                        <form class="Arbol" method="post" action="./Sl_GestionArbol" enctype="multipart/form-data">
                                        <input name="opcion" type="hidden" value="2" />
                                        <input name="usuarioid" type="hidden" value="<%=usuarioid %>" />                                     
                                        <input name="arbolid" type="hidden" value="<%=ar.getArbolID()%>" />                                       
                                            <div class="form-group">
                                                <label>Nombre común:</label>
                                                <input class="form-control"  name="txtNombreComun" id="txtNombreComun" minlength="5" maxlength="150" required>
                                                 <small id="message"></small>

                                            </div>
                                            <div class="form-group">
                                                <label>Nombre científico:</label>
                                                <input class="form-control" name="txtNombreCientifico" id="txtNombreCientifico" minlength="5" maxlength="150" required>
                                                 <small id="message1"></small>

                                            </div>
                                            <div class="form-group">
                                                <label>Descripción:</label>
                                                <textarea class="form-control" rows="3"  name="txtDescripcionArbol" id="txtDescripcionArbol" minlength="5" maxlength="500"></textarea>
                                                    <small id="message2"></small>
	                                             <div id="circle1" data-value="0" data-size="30">
	                              						<small id="percent1"></small>
                                                </div>  
                                             <div class="form-group">
                                                <label for="custom-file">Multimedia:</label>
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text">Archivo</span>
                                                    </div>
                                                   <div class="custom-file">
													    <label class="custom-file-label text-left" for="customFile" id="filmultArb" name="filmultArb">Seleccionar Archivo</label>
													    <input type="file" class="custom-file-input" id="multArbol" name="multArbol" onchange="Test.UpdatePreview(this)" accept="image/jpeg" >
														<input type="hidden" name="url_foto" value="<%=ar.getMultimedia()%>">																		
													</div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                            <%                                            
                                            ArrayList<Genero> listGenero = new ArrayList<Genero>();
                                            Dt_Genero dtu = new Dt_Genero();
                                            listGenero = dtu.listaGenero();
                                            %>
                                                <label>Género del árbol: &nbsp;<a href="GestionGenero.jsp"><i
                                                class="fas fa-plus-square"></i></a></label>  
                                                <select class="form-control" name="GeneroID" id="GeneroID">
                                            <%
                                    		for(Genero lg: listGenero){
                                    	    %>	
                                    		<option value="<%=lg.getGeneroID()%>"><%=lg.getNombre()%></option>
                                    	    <%
                                    		}
                                    	    %>  
                                                </select>
                                            </div>   
                                             <%                                            
                                            ArrayList<Familia> listFamilia = new ArrayList<Familia>();
                                            Dt_Familia dtuf = new Dt_Familia();
                                            listFamilia = dtuf.listaFamilia();
                                            %>                             
                                            <div class="form-group">
                                                <label>Familia del árbol:  &nbsp;<a href="GestionFamilia.jsp"><i
                                                class="fas fa-plus-square"></i></a></label>
                                                <select class="form-control" name="FamiliaID" id="FamiliaID">
                                            <%
                                    		for(Familia lfam: listFamilia){
                                    	    %>	
                                    		<option value="<%=lfam.getFamiliaID()%>"><%=lfam.getNombre()%></option>
                                    	    <%
                                    		}
                                    	    %>  
                                                </select>
                                            </div>
                                             <%                                            
                                            ArrayList<Floracion> listFloracion = new ArrayList<Floracion>();
                                            Dt_Floracion dtf = new Dt_Floracion();
                                            listFloracion = dtf.listaFloracion();
                                            %>
                                            <div class="form-group">
                                                <label>Floración del árbol:</label>
                                                <select class="form-control" name="FloracionID" id="FloracionID">
                                            <%
                                    		for(Floracion lflo: listFloracion){
                                    	    %>	
                                    		<option value="<%=lflo.getFloracionID()%>"><%=lflo.getNombre()%></option>
                                    	    <%
                                    		}
                                    	    %>  
                                                </select>
                                            </div>                                                                                                                                
                                            <div class="mb-3">
                                                 <input class="btn btn-primary btn-user btn-block" type="submit" value="Guardar" />
                                            </div>
                                            <div style="text-align:center;"><a href="GestionArbol.jsp"><i
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

        <!-- End of Content Wrapper -->

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
	

	$('#multArbol').on("change",function() {
	     var i = $(this).prev('label').clone();
	      var file = $('#multArbol')[0].files[0].name;
	   console.log(file);
	      $(this).prev('label').text(file);

	    });
	
	  

	</script>	
	<script>  
	
	
	  $(document).ready(function()
		{
			$("#txtNombreComun").val("<%=ar.getNombreComun()%>");
			$("#txtNombreCientifico").val("<%=ar.getNombreCientifico()%>");
			$("#txtDescripcionArbol").val("<%=ar.getDescripcion()%>");
			$("#GeneroID").val("<%=ar.getGeneroID()%>");
			$("#FamiliaID").val("<%=ar.getFamiliaID()%>");
			$("#FloracionID").val("<%=ar.getFloracionID()%>");
			$("#Multimedia").val("<%=ar.getMultimedia()%>");
		});
	  
	  
	  $('#txtNombreComun').on("keydown", function(e) {
	        var textLength = $('#txtNombreComun').val().replace(' ', '1').length + 1;
	        var maxValue = 150;
	        
	        console.log(e.keyCode);
	        if (textLength > maxValue) {
				if(e.keyCode != 8){
				e.preventDefault();
				}                     	
	        }

	     });
	    $('#txtNombreComun').on("keyup", function(e) {
	        var textLength = $('#txtNombreComun').val().replace(' ', '1').length;
	        var maxValue = 150;

	        $("#message").text(textLength+" de "+maxValue+" carácteres permitidos");
	       
	    });
	    
		  $('#txtNombreCientifico').on("keydown", function(e) {
		        var textLength = $('#txtNombreCientifico').val().replace(' ', '1').length + 1;
		        var maxValue = 150;
		        
		        console.log(e.keyCode);
		        if (textLength > maxValue) {
					if(e.keyCode != 8){
					e.preventDefault();
					}                     	
		        }

		     });
		    $('#txtNombreCientifico').on("keyup", function(e) {
		        var textLength = $('#txtNombreCientifico').val().replace(' ', '1').length;
		        var maxValue = 150;

		        $("#message1").text(textLength+" de "+maxValue+" carácteres permitidos");
		       
		    });
	    
	    $('#txtDescripcionArbol').on("keydown", function(e) {
	        var textLength = $('#txtDescripcionArbol').val().replace(' ', '1').length + 1;
	        var maxValue = 500;
	        
	        console.log(e.keyCode);
	        if (textLength > maxValue) {
				if(e.keyCode != 8){
				e.preventDefault();
				}                     	
	        }

	     });
	    $('#txtDescripcionArbol').on("keyup", function(e) {
	        var textLength = $('#txtDescripcionArbol').val().replace(' ', '1').length;
	        var maxValue = 500;

	        $("#message2").text(textLength+" de "+maxValue+" carácteres permitidos");
	        
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