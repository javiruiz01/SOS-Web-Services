package es.upm.fi.sos.t3.usermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManagementWSSkeleton{

	private static Map<Username, User> users = new HashMap<Username, User>();
	private static boolean instance = false;
	private static List<User> connected = new ArrayList<User>();
	private static User root = new User();
	private static Username usernameRoot = new Username(); 
	private User userID;

	public UserManagementWSSkeleton() {
		if ((!instance) && (!isConnected(root))) {
			usernameRoot.setUsername("admin");
			root.setName("admin");
			root.setPwd("admin");
			users.put(usernameRoot, root);
			connected.add(root);
			instance = true;
		}
	}
	
	private boolean isConnected (User user) {
		boolean result = false;
		for (User user1: connected) {
			if (user1.equals(user)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	private boolean iAmRoot() {
		if (this.userID.equals(root)) {
			return true;
		}
		return false;
	}
	
	public void logout(	)
	{
		connected.remove(this.userID);
	}

	public es.upm.fi.sos.t3.usermanagement.Response login	(
			es.upm.fi.sos.t3.usermanagement.User user
			)
	{
		Response response = new Response();
		response.setResponse(false);
		Username username = new Username();
		username.setUsername(user.getName());
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
		Username username1 = new Username();
		username1.setUsername(user1.getName());
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
		
		
		return response;
	}

	public es.upm.fi.sos.t3.usermanagement.Response removeUser (
			es.upm.fi.sos.t3.usermanagement.Username username
			)
	{
		//TODO : fill this with the necessary business logic
		throw new  java.lang.UnsupportedOperationException("Please implement " + this.getClass().getName() + "#removeUser");
	}

}
