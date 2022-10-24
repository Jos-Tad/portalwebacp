package servlets;

import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datos.Dt_Usuario;
import datos.Dt_enviarEmailUsuario;
import datos.Encrypt;
import entidades.Usuario;
import vistas.ViewRolUsuario;

/**
 * Servlet implementation class Sl_RecuperarContra
 */
@WebServlet("/Sl_RecuperarContra")
public class Sl_RecuperarContra extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_RecuperarContra() {
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
		
		//Declaracion de objetos
		Dt_enviarEmailUsuario dtem = new Dt_enviarEmailUsuario();
		Dt_Usuario dtu = new Dt_Usuario();
		Usuario us = new Usuario();
		
		//Declaracion de variable
		int opc = 0;
		String usuario = "";
		String email = "";
		String newPwd = "";
		String oldPwd = "";
		String usuarioV = "";
		
		opc = Integer.parseInt(request.getParameter("opcion"));
		
		switch(opc){
			case 1:
				try {
					usuario = request.getParameter("user");
					email = request.getParameter("email");
					us = dtu.getUserPwd(usuario, email);
					if(us.getIdUser()!=0){
						dtem.recuperarContra(us.getUser(), us.getEmail());
						response.sendRedirect("RecuperarContra.jsp?msj=1");
					}
					else {
						response.sendRedirect("RecuperarContra.jsp?msj=2");
					}
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.getMessage();
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					newPwd = request.getParameter("newPwd1");
					usuario = request.getParameter("user");
					email = request.getParameter("email");
					us = dtu.getUserPwd(usuario, email);
					if(dtu.updatePwd(us, newPwd)){
						response.sendRedirect("login.jsp?msj=1");
					}
					else {
						response.sendRedirect("CambiarContra.jsp?msj=2");
					}
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.getMessage();
					e.printStackTrace();
				}
				
				break;
			case 3: 
				try {
					oldPwd = request.getParameter("txtContraActual");
					newPwd = request.getParameter("txtContra");
					usuarioV = request.getParameter("usuarioV");
					
					ViewRolUsuario vwru = new ViewRolUsuario();
					Encrypt enc = new Encrypt();
					vwru = dtu.dtGetRU(usuarioV);
					String pwdDecrypt = "";
					String pwdEncrypt = "";
					boolean contraV = false;
					
					pwdEncrypt = vwru.getContra();
					pwdDecrypt = enc.getAESDecrypt(pwdEncrypt,vwru.getKey_encriptacion());
					
					if(oldPwd.equals(pwdEncrypt)){
						contraV= true;
					}else if(oldPwd.equals(pwdDecrypt)){
						contraV = true;
					}
					
					if(contraV){
					us = dtu.getUserPwd(vwru.getUsuario(), vwru.getEmail());
					if(dtu.updatePwd(us, newPwd)){
						response.sendRedirect("AdminCambiarContra.jsp?msj=1");
					}
					else {
						response.sendRedirect("AdminCambiarContra.jsp?msj=2");
					}
					}else{
						response.sendRedirect("AdminCambiarContra.jsp?msj=pwdE");
					}
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.getMessage();
					e.printStackTrace();
				}
				
				break;
			default:
				response.sendRedirect("RecuperarContra.jsp?msj=4");
				break;
			}
		
		
	}

}
