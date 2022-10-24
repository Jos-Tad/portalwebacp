<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" 
import="vistas.ViewPais, datos.Dt_Pais,  entidades.Rol,vistas.ViewRolUsuario, entidades.Region,datos.Dt_Region, entidades.Pais, vistas.ViewRolOpcion, datos.Dt_Rol,datos.Dt_RolOpcion,java.util.*;" %>
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
%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

   <title>Portal ACP - Formulario Editar País</title>
   
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

        <!-- Menus -->
  		 <jsp:include page="adminMenus.jsp" />    
        
                <!-- Begin Page Content -->
                <div class="container-fluid">
                                <%
                            	String pID = "";
							 	pID = request.getParameter("idP")==null?"0":request.getParameter("idP");
														
								Pais p = new Pais();
								Dt_Pais dtf = new Dt_Pais();
								p = dtf.getPais(Integer.parseInt(pID));						
                         	   %>
                    <!-- Formulario -->
                      <div class="container">
                        <div class="row ">
                            <div class="col-lg-10 mx-auto m-auto">
                                <div class="card rounded shadow border-0">
                                    <div class="card-header">
                                        <h2>
                                          Formulario Editar País
                                        </h2>
                                    </div>
                                    <div class="card-body bg-white rounded">                           
                                        <form role="form" class = "Pais" method="post" action="./Sl_GestionPais">
                      					<input name="opcion" type="hidden" value="2" />
                      						<input name="idpais" type="hidden" value="<%=p.getPaisID()%>" />
                                         <div class="form-group">
                                                <label>Nombre del país:</label>
                                                <input class="form-control" name = "txtNombrePais" id ="txtNombrePais" minlength="5" maxlength="150" required>
                                                 <small id="message"></small>
                                                <div class="form-group">
                                                    <label>Descripción:</label>
                                                    <textarea class="form-control" rows="3" name = "txtDescripcionPais" id = "txtDescripcionPais" inlength="5" maxlength="250"></textarea>
                                                    <small id="message2"></small>
	                                             <div id="circle1" data-value="0" data-size="30">
	                              						<small id="percent1"></small>
                                                </div>          
                                            </div>               
                                                 <div class="form-group">
                                            <%                                            
                                            ArrayList<Region> listRegion = new ArrayList<Region>();
                                            Dt_Region dtu = new Dt_Region();
                                            listRegion = dtu.listaRegion();
                                            %>
                                                <label>País:</label>
                                                <select class="form-control" name = "txtNombreRegion" id ="txtNombreRegion">
                                            <%
                                    		for(Region u: listRegion){
                                    	    %>	
                                    		<option value="<%=u.getRegionID()%>"><%=u.getNombre()%></option>
                                    	    <%
                                    		}
                                    	    %>                                    	
                                            </select>
                                            </div>
                                            </div>                                           
                                         	 <div class="text-center">
				                                <input class="btn btn-primary btn-user btn-block" type="submit" value="Guardar" />
				                            </div>				                              
				                            <br>
                                            <div style="text-align:center;"><a href="GestionPais.jsp"><i
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
			$("#txtNombrePais").val("<%=p.getNombre()%>");
			$("#txtDescripcionPais").val("<%=p.getDescripcion()%>");
			$("#txtNombreRegion").val("<%=p.getRegionID()%>");	
		});
	  
	    $('#txtNombrePais').on("keydown", function(e) {
	        var textLength = $('#txtNombrePais').val().replace(' ', '1').length + 1;
	        var maxValue = 150;
	        
	        console.log(e.keyCode);
	        if (textLength > maxValue) {
				if(e.keyCode != 8){
				e.preventDefault();
				}                     	
	        }

	     });
	    $('#txtNombrePais').on("keyup", function(e) {
	        var textLength = $('#txtNombrePais').val().replace(' ', '1').length;
	        var maxValue = 150;

	        $("#message").text(textLength+" de "+maxValue+" carácteres permitidos");
	       
	    });
	    
	    $('#txtDescripcionGenero').on("keydown", function(e) {
	        var textLength = $('#txtDescripcionGenero').val().replace(' ', '1').length + 1;
	        var maxValue = 250;
	        
	        console.log(e.keyCode);
	        if (textLength > maxValue) {
				if(e.keyCode != 8){
				e.preventDefault();
				}                     	
	        }

	     });
	    $('#txtDescripcionPais').on("keyup", function(e) {
	        var textLength = $('#txtDescripcionPais').val().replace(' ', '1').length;
	        var maxValue = 250;

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