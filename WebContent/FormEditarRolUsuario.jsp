<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" 
import="entidades.Rol,entidades.RolUsuario,entidades.Usuario, vistas.ViewRolOpcion,vistas.ViewRolUsuario, datos.Dt_RolOpcion,datos.Dt_RolUsuario,datos.Dt_Usuario,
datos.Dt_RolUsuario, datos.Dt_Rol,java.util.*;" %>
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
	//Variable de control de mensajes
	String varMsj = request.getParameter("msj")==null?"":request.getParameter("msj");	
	
	//Obtener ID del Rol-Usuario
	String rolUsuarioID = "";
	rolUsuarioID = request.getParameter("idRu")==null?"0":request.getParameter("idRu");
							
	//Cargar arreglo de objetos Rol Usuario 
	RolUsuario ru = new RolUsuario();
	Dt_RolUsuario dtrg = new Dt_RolUsuario();
	ru = dtrg.getRolUsuario(Integer.parseInt(rolUsuarioID));

%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  
    <title>Portal ACP - Formulario Rol-Usuario</title>
    
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
                                            Formulario Rol-Usuario
                                        </h2>
                                    </div>
                                    <div class="card-body bg-white rounded">
                                       <form class="Rol-Usuario" method="post" action="./Sl_GestionRolUsuario">
                      				    <input name="rolusuarioid" type="hidden" value="<%=ru.getIdrol_usuario() %>" />                                        
                      					<input name="opcion" type="hidden" value="2" />                               
                                         <div class="form-group">
                                           <div class="col-sm-12 mb-3">
                                             <label>Usuario:</label>                                           
                                            <%
		                                	ArrayList<Usuario> listUser = new ArrayList<Usuario>();
		                                	Dt_Usuario dtu = new Dt_Usuario();
											listUser = dtu.listaUserActivos();
                                			%>
	                                    	<select class="form-control " name="cbxUser" id="cbxUser" disabled required>
	                                 		<option value="" selected disabled>Seleccionar</option>                                  			                                            	                                    		
	                                    	<%
	                                    		for(Usuario u: listUser){
	                                    	%>	
	                                    		<option value="<%=u.getIdUser()%>"><%=u.getUser()%></option>
	                                    	<%
	                                    		}
	                                    	%>
	                                    	</select>
	                                    	<input type="hidden" name="cbxBUser" id="cbxBUser"></input>	                                    	
	                                    	</div>
                                            <div class="col-sm-12 mb-3">
                                            <label>Rol:</label>
                                            <%
		                                	ArrayList<Rol> listRol = new ArrayList<Rol>();
		                                	Dt_Rol dtr = new Dt_Rol();
											listRol = dtr.listaRolActivos();
	                                		%>
	                                    	<select class="form-control" name="cbxRol" id="cbxRol" required>
											<option value="" selected disabled>Seleccionar</option>                                    			                                            	
                                   			<%
	                                    		for(Rol r: listRol){
	                                    	%>	
	                                    		<option value="<%=r.getIdRol()%>"><%=r.getRol()%></option>
	                                    	<%
	                                    		}
	                                    	%>                                    	
                                   			</select>
                                            </div>
                                            </div>
                                         	 <div class="form-group text-center">
				                                <input class="btn btn-primary btn-user btn-block" type="submit" value="Guardar" />
				                            </div>  
                                          <div style="text-align:center;"><a href="GestionRolUsuario.jsp"><i
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
	   	//Setear valores
		$("#cbxUser").val("<%=ru.getUsuarioid()%>");
		$("#cbxBUser").val("<%=ru.getUsuarioid()%>");
		$("#cbxRol").val("<%=ru.getRolid()%>");
		
		var mensaje = "";
        mensaje = "<%=varMsj%>";

        if(mensaje == "existe"){
			$.jAlert({
                'title': 'Error',
                'content': '¡Asignación Rol-Usuario ya existe!',
                'theme': 'red',
                'onClose': function(OnClose) {               
                    window.location = "FormEditarRolUsuario.jsp?idRu=" + <%=ru.getIdrol_usuario()%>;
              }
            });
        }
	});
  </script>
</body>
</html>