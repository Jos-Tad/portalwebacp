<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%	//Variable de control de mensajes
String varMsj = request.getParameter("msj")==null?"":request.getParameter("msj");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="ISO-8859-1">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Portal Web Aboreto Carmelo Palma - Contactanos</title>
  
  <!-- Icon -->
  <jsp:include page="imgShortIcon.jsp" />  

  <!-- CSS boostrap-->
  <link rel="stylesheet" href="css/bootstrap.min.css">

  <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

  <link rel="stylesheet" href="css/styles3.css">

  <!-- FONT AWESOME -->
  <script src="https://kit.fontawesome.com/78a455df4c.js" crossorigin="anonymous"></script>

  <!-- jAlert css  -->
  <link rel="stylesheet" href="jAlert/dist/jAlert.css" />
  
  
</head>

<body>

  <!-- Menu -->
  <jsp:include page="mainMenus.jsp" />

  <!-- Contenido -->
  <div class="container contact">
    <form class = "Contacto"  method="post" action="./Sl_EnviarCorreoContacto">       
    <div class="row">
      <div class="col-md-3">
        <div class="contact-info text-light ">
          <img src="https://image.ibb.co/kUASdV/contact-image.png" alt="image" />
          <h4>Contactanos</h4>
          <h4>¡Estamos a tu disposición!</h4>
        </div>
      </div>
      <div class="col-md-9">
        <div class="contact-form">
          <div class="form-group">
            <label>Nombre Completo:</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="nombrescompleto" name="nombrescompleto" minlength="3" maxlength="80" required>
            </div>
          </div>          
          <div class="form-group">
            <label class="control-label col-sm-2" for="email">Correo:</label>
            <div class="col-sm-10">
              <input type="email" class="form-control" id="email" name="email" required>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-sm-2" for="apellidos">Asunto:</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="asunto"  name="asunto" minlength="3" maxlength="100" required>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-sm-2" for="desc">Descripción:</label>
            <div class="col-sm-10">
              <textarea class="form-control" rows="5" id="desc" name="desc" minlength="5" maxlength="500" required></textarea>
            </div>
          </div>
          <div class="form-group"><br>
            <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-default">Enviar</button>
            </div>
          </div>
        </div>
      </div>
      </form>      
    </div>
    </div>
  
</body>

<!-- Javascript -->
<link rel="stylesheet" href="js/bootstrap.min.js">
<script defer src="./js/index.js"></script>

<!-- jAlert js -->
<script src="jAlert/dist/jAlert.min.js"></script>
<script src="jAlert/dist/jAlert-functions.min.js"></script>

<!-- Boostrap -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.6.0/dist/umd/popper.min.js"
  integrity="sha384-KsvD1yqQ1/1+IA7gi3P0tyJcT3vR+NdBTt13hSJ2lnve8agRGXTTyNaBYmCR/Nwi" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.min.js"
  integrity="sha384-nsg8ua9HAw1y0W1btsyWgBklPnCUAFLuTMS2G72MMONqmOymq585AcH49TLBQObG" crossorigin="anonymous"></script>


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
	                'content': '¡Mensaje enviado!',
	                'theme': 'green',
	                'onClose': function(OnClose) {               
	                    window.location = "Contacto.jsp";
	                }
	             });
	         }
	        if(mensaje == "2")
	        {
	            $.jAlert({
	                'title': 'Error',
	                'content': '¡Revise e intente nuevamente!',
	                'theme': 'reed',
	                'onClose': function(OnClose) {               
	                    window.location = "Contacto.jsp";
	                }
	             });
	         }
	      
	    });
</script>
</html>