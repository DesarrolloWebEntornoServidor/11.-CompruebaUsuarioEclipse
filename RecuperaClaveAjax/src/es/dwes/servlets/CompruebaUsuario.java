package es.dwes.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CompruebaUsuario", urlPatterns = { "/CompruebaUsuario" })
public class CompruebaUsuario extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private LinkedHashMap<String, String> credenciales;
	private String usuario;
	private String clave ;
	
	
	protected void procesaSolicitud(HttpServletRequest request,
			HttpServletResponse response) {

		response.setContentType("text/html;charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {

			// Array asociativo de usuarios y claves
			iniciarCredenciales();

			// Comprobar si la petición es mediante Ajax			
														// X-Requested-With
			if ( esAjax(request)) {
				
				
				usuario = request.getParameter("usuario");		
				
				// Comprobar si el usuario es válido
				if (!esUsuarioValido())
					System.out.println("Usuario no válido");
					out.println(clave);
					
			} else {
				out.println("Este servlet solo se puede invocar vía Ajax");
			}
		} catch (IOException e) {

		}
	}

	private boolean  esUsuarioValido() {
		boolean usuarioValido = false;
		clave = "";
		for (String key : credenciales.keySet()) {
			if (usuario.equals(key)) {
				usuarioValido = true;
				clave = credenciales.get(key);
			}
		}
		return usuarioValido;
	}

	private boolean esAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(request
				.getHeader("X-Requested-With"));
	}

	private LinkedHashMap<String, String> iniciarCredenciales() {
		credenciales = new LinkedHashMap<String, String>();
		credenciales.put("buenos", "días");
		credenciales.put("así", "sea");
		credenciales.put("compañeros", "adiós");
		credenciales.put("felices", "vacaciones");
		return credenciales;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		procesaSolicitud(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		procesaSolicitud(request, response);
	}

}
