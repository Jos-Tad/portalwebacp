<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="entidades.TipoProducto,
    vistas.ViewProducto,datos.Dt_TipoProducto,datos.Dt_Producto,  entidades.Rol,vistas.ViewRolUsuario, vistas.ViewRolOpcion, datos.Dt_Rol,datos.Dt_RolOpcion, java.util.*;"%>
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
<%            	
	ArrayList<ViewProducto> listProducto = new ArrayList<ViewProducto>();
	Dt_Producto dts = new Dt_Producto();
	listProducto = dts.listarProductos();
	
	ViewProducto pr = new ViewProducto();
	
	int productoid =0;
	
	if(listProducto.size() == 0){
		productoid= 1;	
	}	
	else{			
		pr = listProducto.get(listProducto.size() - 1);
		productoid = pr.getProductoid() +1 ;		
	}
%>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  
     <title>Portal ACP - Formulario Producto</title>
     
     <!-- Icon -->
	 <jsp:include page="imgShortIcon.jsp" />  
	
  
    <!-- Custom fonts for this template -->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">
    
    <!-- jAlert css  -->
	<link rel="stylesheet" href="jAlert/dist/jAlert.css" />

    <!-- Custom styles for this page -->
    <link href="vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

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
                        <div class="row">
                            <div class="col-lg-10 mx-auto m-auto">
                                <div class="card rounded shadow border-0">

                                    <div class="card-header">
                                        <h3 class="card-title text-left">Formulario Producto</h3>
                                    </div>
                                    <div class="card-body">
                                        <form class="Producto" method="post" action="./Sl_GestionProducto" enctype="multipart/form-data" >
                      					<input name="opcion" type="hidden" value="1" />
                      				   	<input name="usuarioid"  type = "hidden" value="<%=usuarioid%>" />                                                                      	              
                       					<input name="productoid" type="hidden" value="<%=productoid%>" />                      					
                                          <div class="mt-1 mb-3">
                                                <label for="nombreCP" class="form-label fw-bolder">Nombre:</label>
                                                <input type="text" class="form-control" id="nombreProducto" name= "nombreProducto" minlength="5" maxlength="200" required>
                                            	<small id="message"></small>
                                            </div>
                                           <div class="form-group">
                                                <div class="form-group">
                                                    <label>Descripción:</label>
 												<textarea class="form-control" rows="3" id ="descripcionProducto" name = "descripcionProducto"  minlength="3" maxlength="500" required ></textarea>                                               	
 												 <small id="message1"></small>
                                                <div id="circle" data-value="0" data-size="30">
                              						<small id="percent"></small>
                                                </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="custom-file">Imagen:</label>
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text">Archivo</span>
                                                    </div>
                                                   <div class="custom-file">
													    <label class="custom-file-label text-left" for="customFile" id="filmultPro">Seleccionar archivo</label>
													    <input type="file" class="custom-file-input" id="multPro" name="multPro" onchange="Test.UpdatePreview(this)" accept="image/jpeg" required>
													</div>
                                                </div>
                                            </div>
                                            <div class="m-3" align="center">
												<img id="preview" src="img/Defecto.jpeg" name="preview"  alt="Imagen Producto"
												class="img-thumbnail" alt="Responsive image" style="width: 400px; height: 324px; border-bottom-color: white; margin: 2px;" />
											</div>
                                             <div class="form-group">
                                             <label>Estado:</label>  
                                                <select class="form-control" name="cbxEstadoProducto" id="cbxEstadoProducto" required>                                            	
                                    			<option value="" selected disabled>Seleccionar</option>                                    			                                            	
                                    			<option value="1">Disponible</option>
                                    			<option value="2">No disponible</option>
                                    	        </select>
                                    	    </div> 
                                    	     <div class="form-group">
                                    	    <%                                            
                                            ArrayList<TipoProducto> listTipoProducto = new ArrayList<TipoProducto>();
                                            Dt_TipoProducto dtp = new Dt_TipoProducto();
                                            listTipoProducto = dtp.listarTipoProductos();
                                            %>
                                             <label>Tipo Producto:</label>  
                                                <select class="form-control" name="cbxTipoProducto" id="cbxTipoProducto"required> 
                                                	<option value="" selected disabled>Seleccionar...</option>                                                                              	
                                    	    <%
                                    		for(TipoProducto tp: listTipoProducto){
                                    	    %>	
                                    		<option value="<%=tp.getTipoproductoid()%>"><%=tp.getNombre()%></option>
                                    	    <%
                                    		}
                                    	    %>    </select>
                                    	    </div>
                                            <div class="mb-3">
                                                <button class="btn btn-primary" style="width: 100%;">Guardar</button>
                                            </div>
                                            <div style="text-align:center;"><a href="GestionProductos.jsp"><i
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
    
    
	<!-- jAlert js -->
	<script src="jAlert/dist/jAlert.min.js"></script>
	<script src="jAlert/dist/jAlert-functions.min.js"></script>
	
	 <!-- Circle progress -->
    <script src="js/circle-progress.js"></script>
	
	<script>		
	    $(document).ready(function() 
		{
	/////////// VARIABLE DE CONTROL MSJ ///////////
		       var mensaje = "";
		       mensaje = "<%=varMsj%>";
		
		       if(mensaje == "existe")
		       {
		           $.jAlert({
		               'title': 'Error',
		               'content': '¡Producto ingresado ya existe!',
		               'theme': 'red',
		               'onClose': function(OnClose) {               
		                   window.location = "FormProducto.jsp";
		               }
		        	});
		       }
	    
	  //Funcion para previsualizar la imagen del banner
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
	    
		 //Caracteres Nombre
		$('#nombreProducto').on("keyup", function(e) {
	            var textLength = $('#nombreProducto').val().replace(' ', '1').length;
	            var maxValue = 200;

	     $("#message").text(textLength+" de "+maxValue+" carácteres permitidos");
	     });
		 //Caracteres Descripcion 
		 $('#descripcionProducto').on("keydown", function(e) {
	         var textLength = $('#descripcionProducto').val().replace(' ', '1').length + 1;
	         var maxValue = 500;

	         console.log(e.keyCode);
	         if (textLength > maxValue) {
					if(e.keyCode != 8){
					e.preventDefault();
					}	                       	
	         }
	      });
		 $('#descripcionProducto').on("keyup", function(e) {
	         var textLength = $('#descripcionProducto').val().replace(' ', '1').length;
	         var maxValue = 500;

	         $("#message1").text(textLength+" de "+maxValue+" carácteres permitidos");

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
	 </script>
    
     <script>  $('#multPro').on("change",function() {
	     var i = $(this).prev('label').clone();
	      var file = $('#multPro')[0].files[0].name;
	   console.log(file);
	      $(this).prev('label').text(file);

	    });
	</script>
</body>
</html>