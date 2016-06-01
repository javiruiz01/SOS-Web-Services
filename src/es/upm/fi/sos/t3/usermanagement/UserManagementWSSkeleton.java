package es.upm.fi.sos.t3.usermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManagementWSSkeleton{

	private static Map<String, User> users = new HashMap<String, User>();
	private static boolean instance = false;
	private static List<User> connected = new ArrayList<User>();
	private static User root = new User();
	private static String usernameRoot; 
	private User userID;

	public UserManagementWSSkeleton() {
		// Hay que ver bien cuando se ejecuta el constructor, en principio lo 
		// Hace axis2, pero no sabemos cuando
		if ((!instance) && (connected.isEmpty())) {
			root.setName("admin");
			root.setPwd("admin");
			usernameRoot = root.getName();
			users.put(usernameRoot, root);
			// connected.add(root);
			instance = true;
			// Como el root se añade solo al mapa de usuarios que existen
			// Se le asignará el userID cuando haga login
			// this.userID = root;
		}
	}
	
	public void logout(	)
	{
		// Solo borramos de la lista de connected
		// Ya que el usuario sigue existiendo, aunque no esté conectado
		for (User userOut: connected) {
			if (same(userOut, this.userID)) {
				connected.remove(userOut);
			}
		}
	}

	public es.upm.fi.sos.t3.usermanagement.Response login	(
			es.upm.fi.sos.t3.usermanagement.User user
			)
	{
		Response response = new Response();
		response.setResponse(false);
		String username = user.getName();
		// Hay que comprobar que el usuario no está conectado ya y que existe
		if (!isConnected(user) && users.containsKey(username)) {
			User user1 = users.get(username);
			// Comprobamos credenciales
			if (user1.getPwd().equals(user.getPwd())) {
				connected.add(user);
				response.setResponse(true);
				this.userID = user;
			}
		}
		return response;
	}

	public es.upm.fi.sos.t3.usermanagement.Response addUser (
			es.upm.fi.sos.t3.usermanagement.User user1
			)
	{
		Response response = new Response();
		// Username es el nombre del usuario
		String username1 = user1.getName();
		// Comprobamos si somos root y que el usuario no existía ya
		if (iAmRoot() && (!users.containsKey(username1))) {
			// Añadimos al usuario a la lista de creados
			users.put(username1, user1);	
			response.setResponse(true);
		} else {
			response.setResponse(false);
		}
		return response;
	}

	public es.upm.fi.sos.t3.usermanagement.Response changePassword (
			es.upm.fi.sos.t3.usermanagement.PasswordPair passwordPair
			)
	{
		Response response = new Response();
		response.setResponse(false);
		// Comprobar si mi userID está en connected, y luego ver si soy root
		if (iAmRoot() && isConnected(this.userID)) {
			// Comprobamos que la oldPwd de passwordPair 
			// Se corresponde con la contraseña del usuario
			if (this.userID.getPwd().equals(passwordPair.getOldpwd())) {
				// Si entramos aquí, es que coinciden y está bien
				response.setResponse(true);
				this.userID.setPwd(passwordPair.getNewpwd());
				// Falta actualizar el mapa y la lista?
				// Empezamos por el mapa
				users.put(this.userID.getName(), this.userID);
				// Y ahora la lista
				for (User update: connected) {
					if (same(update, this.userID)) {
						connected.remove(update);
						connected.add(this.userID);
					}
				}
			}
		}
		return response;
	}

	public es.upm.fi.sos.t3.usermanagement.Response removeUser (
			es.upm.fi.sos.t3.usermanagement.Username username
			)
	{
		Response response = new Response();
		response.setResponse(false);
		String username1 = username.getUsername();
		// Comprobamos que somos root y que nuestro username está dentro del mapa
		if (iAmRoot() && users.containsKey(username1)) {
			// Borramos del mapa al usuario
			users.remove(username1);
			response.setResponse(true);
		}
		return response;
	}
	
	private boolean isConnected (User user) {
		boolean result = false;
		for (User user1: connected) {
			if (same(user1, user)) {
				result = true;
			}
		}
		return result;
	}
	
	private boolean iAmRoot() {
		return same(this.userID, root) ? true: false;
	}
	
	private boolean same (User user1, User user2) {
		return (user1.getName().equals(user2.getName()) &&
				user1.getPwd().equals(user2.getPwd())) ? true: false;
	}
}
