<%@ page language="java" contentType="text/html; charset=" 
	pageEncoding="ISO-8859-1" import = "entidades.Banner, datos.Dt_Banner, entidades.Rol,vistas.ViewRolUsuario, vistas.ViewRolOpcion, datos.Dt_Rol,datos.Dt_RolOpcion, java.util.*;"%>
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
	
	//Cargar arreglo de objetos Banner
    ArrayList<Banner> listBanner = new ArrayList<Banner>();
    Dt_Banner dtb = new Dt_Banner();
    listBanner = dtb.ListarBanner();
   
    //Posicion elemento
	Banner ban = new Banner();
	int posicion = 0;
  	if(listBanner.size() == 0){
    posicion= 1;	
  	}	
  	else{			
	ban = listBanner.get(listBanner.size() - 1);
		posicion = ban.getPosicion();		
   	}    
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Portal ACP - Gestión Banner</title>
    
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

<style type="text/css">	
	.marco {
	  width: 600px;
	  height: 350px;
	  border: 1px solid #000;
	  margin: 10px 0;
	}
		
	.fill {
	  object-fit: fill;
	}
</style>
</head>

<body id="page-top">

 <!-- Page Wrapper -->
 <div id="wrapper">

    <!-- Menus -->
	<jsp:include page="adminMenus.jsp" />      
    
    <!-- Begin Page Content -->
    <div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-2 text-gray-800">Banner</h1>

     <!-- DataTales Banner -->
     <div class="card shadow mb-4">
         <div class="card-header py-3">
             <h6 class="m-0 font-weight-bold text-primary">Gestión Banner</h6>
         </div>
         <div class="card-body">
             <div class="table-responsive">
                 <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                     <div style="text-align:right;"><a href="FormBanner.jsp?posicion=<%=ban.getPosicion()%>"><i
                                 class="fas fa-plus-square"></i>&nbsp; Nuevo Elemento</div></a>
                     <thead>
                         <tr>
                              <th>Titulo</th>
                             <th>Descripción</th>
                             <th>Multimedia</th>
                             <th>Opciones</th>
                         </tr>
                     </thead>
                     <tfoot>
                         <tr>
                               <th>Titulo</th>
                             <th>Descripción</th>
                             <th>Multimedia</th>
                             <th>Opciones</th>
                         </tr>
                     </tfoot>
                     <tbody>
                     		<%
                        		for(Banner bn: listBanner){                                       		
                        	%>
                        <tr>
                            <td><%=bn.getTitulobanner() %></td>
                            <td style="max-width: 120px;white-space: nowrap;text-overflow: ellipsis;word-break: break-all; overflow: hidden;"><%=bn.getDescripcion() %></td>
                            <td>&nbsp;&nbsp;<a href="#">
         							<i class="fas fa-camera mostrarImagen" title="<%=bn.getMultimedia() + "?t="+System.currentTimeMillis()%>" onClick="getValue()" data-toggle="modal" data-target="#modalVisualizarImagen"></i>
         							</a></td>                              
                                  <td>
                                  &nbsp;&nbsp;<a href="FormEditarBanner.jsp?idB=<%=bn.getBannerID()%>"><i class="fas fa-edit" title="Editar Banner" ></i></a>&nbsp;&nbsp;                                       
                                  
                                  <a class="ajax-link" href="javascript:void(0);" 
                            		onclick="$.jAlert({
                            		    'type': 'confirm',
                            		    'confirmQuestion': '¿Estás seguro que deseas eliminar este elemento?',
                            		    'onConfirm': function(e, btn){
                            		      e.preventDefault();
                            		      //do something here

                            		      window.location.href = 'Sl_GestionBanner?idB=<%=bn.getBannerID()%>';
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
         							<i class="fas fa-trash-alt" title="Eliminar Elemento"></i>
         						</a></i>                       						                        						
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
                'content': '¡Elemento guardado con éxito!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "GestionBanner.jsp";
                }
              });
        }
        if(mensaje == "2" || mensaje =="4")
        {
            $.jAlert({
                'title': 'Error',
                'content': '¡Revise los datos e intente nuevamente!',
                'theme': 'red',
                'onClose': function(OnClose) {               
                    window.location = "GestionBanner.jsp";
                }
              });
        }
        if(mensaje == "3")
        {
            $.jAlert({
                'title': 'Éxito',
                'content': '¡Elemento actualizado exitosamente!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "GestionBanner.jsp";
                }
              });
        }  
        if(mensaje == "5")
        {
            $.jAlert({
                'title': 'Éxito',
                'content': '¡Elemento eliminado exitosamente!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "GestionBanner.jsp";
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
                    window.location = "GestionBanner.jsp";
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