package es.upm.fi.sos.t3.usermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserManagementWSSkeleton{

	private static List<User> users = new ArrayList<User>();
	private boolean instance = false;
	// No puede ser un randomUUID, sería el username?
	// Técnicamente el username es lo mismo que el user.getName()
	private static Map<UUID, User> connected = new HashMap<UUID, User>();
	private UUID userID;
	private static UUID rootID; // para saber si está conectado
	// Podríamos hacer que con el metodo init, cada vez que se cree una sesión
	// nueva, hacer something

	// Funciona un constructor aqui o no sirve para nada?
	public UserManagementWSSkeleton() {
		if (instance == false) {
			// Preguntar si el admin ya está dentro
			if (connected.get(rootID) == null) {
				// Si no está dentro, lo añadimos, y borramos todo lo que hubiera ya que 
				// el root siempre está conectado y dentro
				users.clear();
				connected.clear();
				User root = new User();
				root.setName("admin");
				root.setPwd("admin");
				users.add(root);
				rootID = UUID.randomUUID();
				connected.put(rootID, root);
				instance = true;
			}
		}
	}

	// Comprueba si el usuario existe, no si está conectado
	private boolean exists (User user) {
		boolean result = false;
		for (User user1: users) {
			if (user1.getName().equals(user.getName())) {
				result = true;
				break;
			}
		}
		return result;
	}

	public void logout(	)
	{
		// LLamada es ignorada si el usuario no estaba loggeado
		connected.remove(userID);
	}

	public es.upm.fi.sos.t3.usermanagement.Response login	(
			es.upm.fi.sos.t3.usermanagement.User user
			)
	{
		// Hay que validar el usuario?? --> YES YES
		Response response = new Response();
		if ((exists(user) == false) || (connected.get(user) != null)) {
			response.setResponse(false);
		} else {
			// Comprobación de la contraseña
			
			userID = UUID.randomUUID();
			connected.put(userID, user);
			response.setResponse(true);
		}
		return response;
	}

	public es.upm.fi.sos.t3.usermanagement.Response addUser (
			es.upm.fi.sos.t3.usermanagement.User user1
			)
	{
		Response response = new Response();
		if (exists(user1) == false) {
			response.setResponse(false);
		} else {
			users.add(user1);
			response.setResponse(true);
		}
		return response;
	}

	public es.upm.fi.sos.t3.usermanagement.Response changePassword (
			es.upm.fi.sos.t3.usermanagement.PasswordPair passwordPair
			)
	{
		// Como sé quien llama a esta funcion?
		// Y lo mismo para todas las que deberían ser solo llamadas por el superuser

		// Comprobar si mi userID está en connected, y luego comprobar que soy el root
		Response response = new Response();
		return response;
	}

	public es.upm.fi.sos.t3.usermanagement.Response removeUser (
			es.upm.fi.sos.t3.usermanagement.Username username
			)
	{
		Response response = new Response ();
		response.setResponse(false);
		for (User userToDelete: users) {
			if (userToDelete.getName().equals(username)) {
				response.setResponse(true);
				users.remove(userToDelete);
				return response;
			}
		}
		return response;
	}
}
