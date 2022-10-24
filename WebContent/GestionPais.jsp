<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" 
import="vistas.ViewPais, datos.Dt_Pais,  entidades.Rol,vistas.ViewRolUsuario, vistas.ViewRolOpcion, datos.Dt_Rol,datos.Dt_RolOpcion,java.util.*;" %>
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

%>
<!DOCTYPE html>
<html lang="es">

	
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Portal ACP - Gestión País</title>
    
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
	
	<!-- Menu -->
    <jsp:include page="adminMenus.jsp" />  
    

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">País</h1>

                    <!-- DataTales -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Gestión País</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <div style="text-align:right;"><a href="FormPais.jsp"><i
                                                class="fas fa-plus-square"></i>&nbsp; Nuevo País</div></a>
                                    <%
                                	ArrayList<ViewPais> listPais = new ArrayList<ViewPais>();
                                	Dt_Pais dtu = new Dt_Pais();
                                	listPais = dtu.listaViewPais();                                	
                                     %>
                                    <thead>
                                        <tr>
                                            <th>Nombre</th>
                                            <th>Descripción</th>
                                            <th>Región</th>
                                            <th>Opciones</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>Nombre</th>
                                            <th>Descripción</th>
                                            <th>Región</th>
                                            <th>Opciones</th>
                                        </tr>
                                    </tfoot>
                                    <tbody>
                                            <%
                                       		for(ViewPais lp: listPais){
                                       	    %> 
                                        <tr>                                           
                                       	    <td><%=lp.getNombre() %></td>                                           
                                            <td><%=lp.getDescripcion() %></td>
                                            <td><%=lp.getRegion() %></td>                                            
                                            <td>&nbsp;&nbsp;<a href="FormEditarPais.jsp?idP=<%=lp.getPaisID()%>"><i class="fas fa-edit" title="Editar País"></i></a>&nbsp;&nbsp;
                                                      &nbsp;&nbsp;  
                                                   <a class="ajax-link" href="javascript:void(0);" 
                                           			onclick="$.jAlert({
                                           		    'type': 'confirm',
                                           		    'confirmQuestion': '¿Estás seguro que deseas eliminar este país?',
                                           		    'onConfirm': function(e, btn){
                                           		      e.preventDefault();
                                           		      //do something here

                                           		      window.location.href = 'Sl_GestionPais?idP=<%=lp.getPaisID()%>';
                                           		      btn.parents('.jAlert').closeAlert();
                                           		      return false;
                                           		    },
                                           		    'onDeny': function(e, btn){
                                           		      e.preventDefault();
                                           		      //do something here
                                           		      btn.parents('.jAlert').closeAlert();
                                           		      return false;
                                           		    }
                                           		  });">
                        							<i class="fas fa-trash-alt" title="Eliminar País"></i>
                        						</a>
                        						</td>
                        						</tr>   
                                            <%
                                       		}
                                           %>                         
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
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
    
    <!-- jAlert js -->
	<script src="jAlert/dist/jAlert.min.js"></script>
	<script src="jAlert/dist/jAlert-functions.min.js"></script>
	
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
                 'content': '¡País guardado con éxito!',
                 'theme': 'green',
                 'onClose': function(OnClose) {               
                     window.location = "GestionPais.jsp";
                 }
               });
        }
        if(mensaje == "2" || mensaje == "4")
        {
        	 $.jAlert({
                 'title': 'Error',
                 'content': '¡Revise los datos e intente nuevamente!',
                 'theme': 'red',
                 'onClose': function(OnClose) {               
                     window.location = "GestionPais.jsp";
                 }
               });
        }
        if(mensaje == "3")
        {
            $.jAlert({
                'title': 'Éxito',
                'content': '¡País actualizado exitosamente!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "GestionPais.jsp";
                }
              });
        }  
        if(mensaje == "5")
        {
        	 $.jAlert({
                 'title': 'Éxito',
                 'content': '¡País eliminado exitosamente!',
                 'theme': 'green',
                 'onClose': function(OnClose) {               
                     window.location = "GestionPais.jsp";
                 }
               });       
        }       
	    if(mensaje == "existe")
	    {
	        $.jAlert({
	            'title': 'Error',
	            'content': '¡País ya existe!',
	            'theme': 'red',
	            'onClose': function(OnClose) {               
	                window.location = "GestionPais.jsp";
	            }
	          });
	    }
	    });  
	</script>
</body>
</html>