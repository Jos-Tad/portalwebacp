<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" 
import="vistas.ViewArbol,datos.Dt_Arbol, entidades.Rol,vistas.ViewRolUsuario, vistas.ViewRolOpcion, datos.Dt_Rol,datos.Dt_RolOpcion,java.util.*;" %>
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
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Portal ACP - Restauración Árbol</title>

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
                    <h1 class="h3 mb-2 text-gray-800">Restauración</h1>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Restauración Árbol</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                              <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0"> 
                                    <%
                                    ArrayList<ViewArbol> listArbolR = new ArrayList<ViewArbol>();
                                    Dt_Arbol dtu = new Dt_Arbol();
                                    listArbolR = dtu.listaArbolR();
                                    %>
                                    <thead>
                                        <tr>
                                            <th>Nombre común</th>
                                            <th>Nombre científico</th>
                                            <th>Descripción</th>
                                            <th>Multimedia</th>
                                            <th>Genero</th>
                                            <th>Familia</th>
                                            <th>Floración</th>
                                            <th>Estado</th>                                                                                                                           
                                            <th>Opciones</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>Nombre común</th>
                                            <th>Nombre científico</th>
                                            <th>Descripción</th>
                                            <th>Multimedia</th>
                                            <th>Genero</th>
                                            <th>Familia</th>
                                            <th>Floración</th>
                                            <th>Estado</th>                                                            
                                            <th>Opciones</th>
                                        </tr>
                                    </tfoot>
                                    <tbody>
                                         <%
                                         	for(ViewArbol ar: listArbolR){                                         	
                                         %>
                                       	<tr> 
                                       	    <td><%=ar.getNombreComun() %></td>
                                       	    <td><%=ar.getNombreCientifico() %></td>                                           
                                            <td><%=ar.getDescripcion() %></td>                                 
                                            <td>&nbsp;&nbsp;<a href="#" data-toggle="modal" data-target="#modalVisualizarImagen" >
                        							<i class="fas fa-camera mostrarImagen" title="<%=ar.getMultimedia()%>" onClick="getValue()"></i>
                        							</a></td>
                                           <td><%=ar.getNombreGenero() %></td>                                            
                                            <td><%=ar.getNombreFam() %></td>
                                            <td><%=ar.getNombreFlo() %></td> 
                                            <td><%="Inactivo" %></td>                                                                                                                                                                                                                                                            
                        			       <td>
                        			       		&nbsp;&nbsp;<a class="ajax-link" href="javascript:void(0);" 
                                           			onclick="$.jAlert({
                                           		    'type': 'confirm',
                                           		    'confirmQuestion': '¿Estás seguro que deseas restaurar este árbol?',
                                           		    'onConfirm': function(e, btn){
                                           		      e.preventDefault();
                                           		      //do something here

                                           		      window.location.href = 'Sl_RestaurarArbol?idA=<%=ar.getArbolID()%>';
                                           		      btn.parents('.jAlert').closeAlert();
                                           		      return false;
                                           		    },
                                           		    'onDeny': function(e, btn){
                                           		      e.preventDefault();
                                           		      //do something here
                                           		      btn.parents('.jAlert').closeAlert();
                                           		      return false;
                                           		    }
                                           		  });"><i class="fas fa-redo" title="Restaurar Elemento"></i></a>                                              
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
                <div style="text-align:center;"><a href="GestionArbol.jsp"><i class="fas fa-arrow-circle-left"></i>&nbsp;Volver</a></div>
                
                
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
									<img id="preview" src="" name="preview"  alt="Imagen Arbol"
										class = "img-fluid"; border-bottom-color: white; margin: 2px;"/>
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
	
	
	<script type="text/javascript">
	function verRptArbol()
	{
		window.open("Sl_RptArbol", '_blank');
	}	
    $(document).ready(function ()
    {
        
	/////////// VARIABLE DE CONTROL MSJ ///////////
        var mensaje = "";
        mensaje = "<%=varMsj%>";

        if(mensaje == "1")
        {
            successAlert('Exito', 'Árbol restaurado con éxito');
            $.jAlert({
                'title': 'Éxito',
                'content': '¡Árbol restaurado con éxito!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "RestaurarArbol.jsp";
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
                    window.location = "RestaurarArbol.jsp";
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