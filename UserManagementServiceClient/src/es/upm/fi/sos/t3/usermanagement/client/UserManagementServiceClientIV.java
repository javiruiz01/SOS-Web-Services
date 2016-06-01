package es.upm.fi.sos.t3.usermanagement.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import es.upm.fi.sos.t3.usermanagement.client.UserManagementWSStub.PasswordPair;
import es.upm.fi.sos.t3.usermanagement.client.UserManagementWSStub.User;
import es.upm.fi.sos.t3.usermanagement.client.UserManagementWSStub.Username;

public class UserManagementServiceClientIV {

	public static void main (String[] args) throws RemoteException {
		UserManagementWSStub stub1 = new UserManagementWSStub();
		stub1._getServiceClient().engageModule("addressing");
		stub1._getServiceClient().getOptions().setManageSession(true);
		
		// En esta clase vamos a probar si podemos cambiar la contraseña de 
		// Un usuario
		
		// Primero conectamos el root
		User root = new User();
		root.setName("admin");
		root.setPwd("admin");
		System.out.println("Connecting root = " + stub1.login(root).getResponse());
		
		// Ahora creamos un nuevo stub (sesión) para añadir al nuevo usuario
		UserManagementWSStub stub2 = new UserManagementWSStub();
		stub2._getServiceClient().engageModule("addressing");
		stub2._getServiceClient().getOptions().setManageSession(true);
		
		// Creamos al nuevo usuario
		User newUser = new User();
		newUser.setName("Javier");
		newUser.setPwd("Ruiz");
		
		// Hacemos que el root lo añada al HashMap de los usuarios
		System.out.println("Adding newUser = " + stub1.addUser(newUser).getResponse());
		
		// Y ahora el nuevo usuario hace login
		System.out.println("Connecting newUser = " + stub2.login(newUser).getResponse());
		
		// Intentamos cambiar la contraseña
		// Creamos la clase PasswordPair
		PasswordPair password = new PasswordPair();
		password.setNewpwd("RuizCalle");
		password.setOldpwd("Ruiz");
		
		// Intentamos cambiarla
		System.out.println("Changing password = " + stub2.changePassword(password).getResponse());
		
		// Ahora intentamos cambiarla con el mismo objeto, lo que nos debería 
		// Devolver falso, ya que la oldPwd ya no es la misma
		System.out.println("Trying to change password = " + stub2.changePassword(password).getResponse());
		
		// Además, por aprovechar la clase, utilizamos removeUser para eliminar a los usuarios
		// Primero hace logout
		stub2.logout();
		
		// Por hacer otra prueba, intentamos cambiar la contraseña de un usuario
		// Que ya ha hecho logout, debería dar falso
		System.out.println("Trying to change password = " + stub2.changePassword(password).getResponse());
		
		// Y ahora lo quitamos del HashMap de los usuarios que existen
		// Esto lo hace el root, y el metodo recibe un Username
		Username username = new Username();
		username.setUsername(newUser.getName());
		System.out.println("Removing user = " + stub1.removeUser(username).getResponse());
		
	}
}
