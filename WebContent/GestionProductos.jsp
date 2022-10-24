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
%>   
<!DOCTYPE html>
 <% response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
 response.setHeader("Pragma","no-cache"); //HTTP 1.0 
 response.setDateHeader ("Expires", 0); //prevents caching at the proxy server  
%>
<%
	//Variable de control de mensajes
	String varMsj = request.getParameter("msj")==null?"":request.getParameter("msj");
%>
<html lang="en">

<head>
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Portal ACP - Gestión Productos</title>
    
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

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Productos</h1>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Gestión Productos</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <div style="text-align:right;"><a href="FormProducto.jsp"><i
                                                class="fas fa-plus-square"></i>&nbsp; Nuevo Producto</div></a>
                                    <%
                                	ArrayList<ViewProducto> listProducto = new ArrayList<ViewProducto>();
                                	Dt_Producto dtp = new Dt_Producto();
                                	listProducto = dtp.listarProductos();                                	
                                     %>
                                    <thead>
                                        <tr>
                                            <th>Producto</th>
                                            <th>Descripción</th>
                                            <th>Multimedia</th>
                                            <th>Estado</th>
                                            <th>Tipo Producto</th>                                                                                    
                                            <th>Opciones</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>Producto</th>
                                            <th>Descripción</th>
                                            <th>Multimedia</th>
                                            <th>Estado</th>
                                            <th>Tipo Producto</th>                                                                                    
                                            <th>Opciones</th>
                                        </tr>
                                    </tfoot>
                                    <tbody>
                                    	<%
                                       		for(ViewProducto pr: listProducto){
                                       	    %> 
                                        <tr>
                                     	   <td><%=pr.getProducto()%></td>          
                                     	   <td><%=pr.getDescripcion()%></td>   
                                     	     <td>&nbsp;&nbsp;<a href="#">
         									<i class="fas fa-camera mostrarImagen" title="<%=pr.getMultimedia() + "?t="+System.currentTimeMillis()%>" onClick="getValue()" data-toggle="modal" data-target="#modalVisualizarImagen"></i>
         									</a></td> 
                                  		   <td><%=pr.getEstadoproductoid()==1?"Disponible":"No disponible" %></td>
                                           <td><%=pr.getTipoproducto()%></td>     
				                           <td>&nbsp;&nbsp;<a href="FormEditarProducto.jsp?idP=<%=pr.getProductoid()%>"><i class="fas fa-edit"></i></a>&nbsp;
                                                        
                                                   &nbsp;&nbsp;<a class="ajax-link" href="javascript:void(0);" 
                                           			onclick="$.jAlert({
                                           		    'type': 'confirm',
                                           		    'confirmQuestion': '¿Estás seguro que deseas eliminar este Producto',
                                           		    'onConfirm': function(e, btn){
                                           		      e.preventDefault();
                                           		      //do something here
                                           		      window.location.href = 'Sl_GestionProducto?idP=<%=pr.getProductoid()%>';
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
                        							<i class="fas fa-trash-alt" title="Eliminar Producto"></i>
                        						</a></i></td>            
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

         	   <!-- MODAL VISUALIZAR IMAGEN -->					
					<div class="modal fade" id="modalVisualizarImagen" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
					  <div class="modal-dialog modal-dialog-centered" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					       <h5 class="modal-title">Visualizar Imagen</h5>				
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					          <span aria-hidden="true">&times;</span>
					        </button>
					      </div>
					      <div class="modal-body">
					    	<div align="center">
									<img id="preview" src="" name="preview"  alt="Imagen Banner" class="img-fluid fill marco"/>
								</div>								
					      </div>					 
					    </div>
					  </div>
					</div>					
					<!-- FIN Modal -->
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
                'content': '¡Producto guardado con éxito!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "GestionProductos.jsp";
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
                    window.location = "GestionProductos.jsp";
                }
              });
        }
        if(mensaje == "3")
        {
            $.jAlert({
                'title': 'Éxito',
                'content': '¡Producto actualizado exitosamente!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "GestionProductos.jsp";
                }
              });
        }  
        if(mensaje == "5")
        {
            $.jAlert({
                'title': 'Éxito',
                'content': '¡Producto eliminado exitosamente!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "GestionProductos.jsp";
                }
              });
        }       
        if(mensaje == "existe")
        {
            $.jAlert({
                'title': 'Error',
                'content': '¡Elemento ya existe!',
                'theme': 'red',
                'onClose': function(OnClose) {               
                    window.location = "GestionProductos.jsp";
                }
              });
        }
               
    });
    function getValue()
    {   	
        var a= event.srcElement.title;
        document.getElementById("preview").src = a;
    }  
</script> 
</body>

</html>