<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="entidades.Rol,vistas.ViewRolUsuario, vistas.ViewRolOpcion, datos.Dt_Rol,datos.Dt_RolOpcion, java.util.*;"%>
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
	ViewRolUsuario vru1 = new ViewRolUsuario();
	vru1 =(ViewRolUsuario) session.getAttribute("acceso");
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
 
    <title>Administración Portal Arboreto Carmelo Palma</title>

	  <!-- Icon -->
	 <jsp:include page="imgShortIcon.jsp" />  
	
    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <script src="https://kit.fontawesome.com/12d21ebefe.js" crossorigin="anonymous"></script>

</head>

<body id="page-top">
    <!-- Page Wrapper -->
    <div id="wrapper">
	
    <!-- Menus -->
	<jsp:include page="adminMenus.jsp" />      

              <!-- Begin Page Content -->
              <div class="container-fluid">

                  <!-- Page Heading -->
                  <div class="d-sm-flex align-items-center justify-content-between mb-4">
                      <h1 class="h3 mb-0 text-gray-800">Sección de Administración</h1>
                  </div>

                  <!-- Content Row -->
                  <div class="row">

                      <div class="col-lg-12 mb-4">

                          <!-- Illustrations -->
                          <div class="card shadow mb-4">
                              <div class="card-header py-3">
                                  <h6 class="m-0 font-weight-bold text-primary">Bienvenida</h6>
                              </div>
                              <div class="card-body">
                                  <div class="text-center">
                                      <img class="img-fluid px-3 px-sm-4 mt-3 mb-4" style="width: 25rem;"
                                          src="img/undraw_posting_photo.svg" alt="">
                                  </div>
                                  <p>Bienvenido, usted ha iniciado en la parte administrativa del Portal Web Carmelo
                                      Palma, diríjase al menú que ve a su izquierda para comenzar con las gestiones
                                      administrativas, donde podrás gestionar toda la información no solo del catálogo
                                      de árboles, eventos y productos, sino también la información extra como la
                                      Misión y visión.</p>
                                  <a target="_blank" rel="nofollow" href="index.jsp">Portal Web Arboreto Carmelo
                                      Palma &rarr;</a>
                              </div>
                          </div>

                          <!-- Approach -->
                          <div class="card shadow mb-4">
                              <div class="card-header py-3">
                                  <h6 class="m-0 font-weight-bold text-primary">Equipo</h6>
                              </div>
                              <div class="card-body">
                                  <p>El equipo de desarrollo Abra, compuesto por Guillermo Antonio Baltodano Vado,
                                      Kevin Fernando González Estrada, Joseph Tadeo Chavarría González, Cesar Antonio
                                      Collado Baca, Marwan Flores estamos agradecidos de que nuestro diseño para el portal sea utilizado. Esperamos que la experiencia de
                                      usuario sea positiva, intuitiva y que sea de su agrado.</p>
                              </div>
                          </div>

                      </div>
                  </div>

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

    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="vendor/chart.js/Chart.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="js/demo/chart-area-demo.js"></script>
    <script src="js/demo/chart-pie-demo.js"></script>

</body>
</html>