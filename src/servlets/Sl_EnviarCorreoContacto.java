package servlets;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datos.Dt_enviarEmailContacto;

/**
 * Servlet implementation class Sl_EnviarCorreoContacto
 */
@WebServlet("/Sl_EnviarCorreoContacto")
public class Sl_EnviarCorreoContacto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_EnviarCorreoContacto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		Dt_enviarEmailContacto dte = new Dt_enviarEmailContacto();
		
		String nombrescompleto = request.getParameter("nombrescompleto");
		String asunto = request.getParameter("asunto");
		String correo = request.getParameter("email");
		String descripcion = request.getParameter("desc");
		
			try {
				if(dte.enviarEmailVerificacion(nombrescompleto,asunto,correo,descripcion)){
					response.sendRedirect("Contacto.jsp?msj=1");
				}
				else {
					response.sendRedirect("Contacto.jsp?msj=2");
				}
			} catch (MessagingException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		
}
