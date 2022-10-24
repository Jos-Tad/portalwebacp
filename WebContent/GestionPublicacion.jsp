<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import = "entidades.Publicacion, datos.Dt_Publicacion, entidades.Rol,vistas.ViewRolUsuario, vistas.ViewRolOpcion, datos.Dt_Rol,datos.Dt_RolOpcion,java.text.SimpleDateFormat,java.util.*;"%>
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
<html lang="es">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Portal ACP - Gestión Publicación</title>
    
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
                    <h1 class="h3 mb-2 text-gray-800">Publicación</h1>
                    
                    			<%
                                	ArrayList<Publicacion> listPost = new ArrayList<Publicacion>();
                                	Dt_Publicacion dtp = new Dt_Publicacion();
                                	listPost = dtp.ListaPost();                               	                   
                                %>

                    <!-- DataTales Publicación  -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Gestión Publicación</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <div style="text-align:right;"><a href="FormPublicacion.jsp"><i
                                                class="fas fa-plus-square"></i>&nbsp; Nueva Publicación</div></a>
                                    <thead>
                                        <tr>
                                            <th>Título</th>
                                            <th>Descripción</th>
                                            <th>Fecha</th>
                                            <th>Estado</th>
                                            <th>Opciones</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>Título</th>
                     				        <th>Descripción</th>
                                            <th>Fecha</th>
                                            <th>Estado</th>
                                            <th>Opciones</th>
                                        </tr>
                                    </tfoot>
                                    <tbody>
                                   		 <%
                                       		for(Publicacion post: listPost){
                                       			String control = null;
                                       			switch(post.getEstadopublicacion()) {
                                       			  case 1:{ 
                                       				control = "Visible";   
                                       			    break;
                                       			}
                                       			  case 2: {
                                       				control = "No Visible";
                                       			    break;
                                       			}
                                       			 case 3: {
                                       				control = "Borrador";
                                       			    break;
                                       			}
                                       			}
                                       			String fechaCreacion = new SimpleDateFormat("dd/MM/yyyy").format(post.getFcreacion());
                                       	%>
                                        <tr>
                                            <td><%=post.getTitulo() %></td>
                                            <td style="max-width: 120px;white-space: nowrap;text-overflow: ellipsis;word-break: break-all;
											overflow: hidden;"><%=post.getDescripcion()%></td>                                           
                                            <td><%=fechaCreacion%></td>
                                            <td><%=control%></td>                                                   
                                             <td>
                                        		&nbsp;&nbsp;<a href="ImagenPublicacion.jsp?idIP=<%=post.getPublicacionid()%>"><i class="fas fa-camera"></i></a>   
                        						&nbsp;&nbsp;<a href="PdfPublicacion.jsp?idIP=<%=post.getPublicacionid()%>"><i class="far fa-file-pdf"></i></a>
                        						&nbsp;&nbsp;<a href="VideoPublicacion.jsp?idIP=<%=post.getPublicacionid()%>"><i class="fas fa-video"></i></a>                        						                     						
                                    	
                                                 &nbsp;&nbsp;<a href="FormEditarPublicacion.jsp?idP=<%=post.getPublicacionid()%>"><i class="fas fa-edit"></i></a>&nbsp;&nbsp;                                       
                                                 
                                                 <a class="ajax-link" href="javascript:void(0);" 
                                           		onclick="$.jAlert({
                                           		    'type': 'confirm',
                                           		    'confirmQuestion': '¿Estás seguro que deseas eliminar este elemento?',
                                           		    'onConfirm': function(e, btn){
                                           		      e.preventDefault();
                                           		      //do something here

                                           		      window.location.href = 'Sl_GestionPublicacion?idP=<%=post.getPublicacionid()%>';
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
                'content': '¡Publicación guardada exitosamente!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "GestionPublicacion.jsp";
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
                    window.location = "GestionPublicacion.jsp";
                }
              });
        }
        if(mensaje == "3")
        {
            $.jAlert({
                'title': 'Éxito',
                'content': '¡Publicación actualizada exitosamente!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "GestionPublicacion.jsp";
                }
              });
        }  
        if(mensaje == "4")
        {
            $.jAlert({
                'title': 'Error',
                'content': '¡La Publicación que desea actualizar ya existe!',
                'theme': 'red',
                'onClose': function(OnClose) {               
                    window.location = "GestionPublicacion.jsp";
                }
              });
        }
        if(mensaje == "5")
        {
            $.jAlert({
                'title': 'Éxito',
                'content': '¡Publicación eliminada exitosamente!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "GestionPublicacion.jsp";
                }
              });
        }       
        if(mensaje == "existe")
        {
            $.jAlert({
                'title': 'Error',
                'content': '¡Publicación ingresada ya existe!',
                'theme': 'red',
                'onClose': function(OnClose) {               
                    window.location = "GestionPublicacion.jsp";
                }
              });
        } 
        if(mensaje == "6")
        {
            $.jAlert({
                'title': 'Éxito',
                'content': '¡Imagen guardada con exito!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "GestionPublicacion.jsp";
                }
              });
        }  
        if(mensaje == "7")
        {
            $.jAlert({
                'title': 'Éxito',
                'content': '¡PDF guardado con exito!',
                'theme': 'green',
                'onClose': function(OnClose) {               
                    window.location = "GestionPublicacion.jsp";
                }
              });
        }  
    });
    function getValue()
    {   	
        var a= event.srcElement.title;
        console.log(a);
        document.getElementById("preview").src = a;
    }   
	</script>
</body>	
</html>