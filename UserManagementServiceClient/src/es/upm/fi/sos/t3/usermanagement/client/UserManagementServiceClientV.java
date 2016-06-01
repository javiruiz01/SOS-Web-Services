package es.upm.fi.sos.t3.usermanagement.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import es.upm.fi.sos.t3.usermanagement.client.UserManagementWSStub.User;

public class UserManagementServiceClientV {
	
	public static void main (String[] args) throws RemoteException {
		
		// Vamos a intentar agregar dos usuarios con el mismo nombre
		
		// Empezamos haciendo login del root
		UserManagementWSStub stub1 = new UserManagementWSStub();
		stub1._getServiceClient().engageModule("addressing");
		stub1._getServiceClient().getOptions().setManageSession(true);
		
		User root = new User();
		root.setName("admin");
		root.setPwd("admin");
		System.out.println("Superuser logging in = " + stub1.login(root).getResponse());
		
		// Ahora creamos el segundo usuario, que añadiremos sin ningún problema
		UserManagementWSStub stub2 = new UserManagementWSStub();
		stub2._getServiceClient().engageModule("addressing");
		stub2._getServiceClient().getOptions().setManageSession(true);
		
		User user1 = new User();
		user1.setName("Yerai");
		user1.setPwd("Zamorano");
		
		// El superuser lo añade
		System.out.println("Adding new User = " + stub1.addUser(user1).getResponse());
		// Y ahora el user1 hace login
		System.out.println("Logging in = " + stub2.login(user1).getResponse());
		
		// Ahora creamos un nuevo stub, que se encargará de tener al user2
		UserManagementWSStub stub3 = new UserManagementWSStub();
		stub3._getServiceClient().engageModule("addressing");
		stub3._getServiceClient().getOptions().setManageSession(true);
		
		User user2 = new User();
		user2.setName("Yerai");
		user2.setPwd("Zamorano");
		
		// Intentamos que el superuser lo añada al HashMap de usuarios
		// Existentes, debería dar falso
		System.out.println("Trying to add new user = " + stub1.addUser(user2).getResponse());
	}

}
