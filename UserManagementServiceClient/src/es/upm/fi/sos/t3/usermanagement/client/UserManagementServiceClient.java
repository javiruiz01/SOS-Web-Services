package es.upm.fi.sos.t3.usermanagement.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import es.upm.fi.sos.t3.usermanagement.client.UserManagementWSStub.User;

public class UserManagementServiceClient {
	
	public static void main (String[] args) throws RemoteException {
		// Creamos el stub, que va a ser un cliente, y le decimos que mantenga la sesión
		UserManagementWSStub stub1 = new UserManagementWSStub();
		stub1._getServiceClient().engageModule("addressing");
		stub1._getServiceClient().getOptions().setManageSession(true);
		
		User root = new User();
		root.setName("admin");
		root.setPwd("admin");
		
		System.out.println("First login: " + stub1.login(root).getResponse());
		
		// Ahora probamos a añadir un usuario
		User javier = new User();
		javier.setName("Javier");
		javier.setPwd("1qazxsw2");
		System.out.println("Add user: " + javier.getName() + ": " + stub1.addUser(javier).getResponse());
		
		
		UserManagementWSStub stub2 = new UserManagementWSStub();
		stub2._getServiceClient().engageModule("addressing");
		stub2._getServiceClient().getOptions().setManageSession(true);
		
		
		// Hacemos login con el usuario ya creado
		System.out.println("Hacemos login del usuario: " + javier.getName() + " = " + stub2.login(javier).getResponse());
		
		// Ahora vamos a probar a añadir un usuario siendo un usuario normal, tiene que dar falso
		UserManagementWSStub stub3 = new UserManagementWSStub();
		stub3._getServiceClient().engageModule("addressing");
		stub3._getServiceClient().getOptions().setManageSession(true);
		
		User yerai = new User();
		yerai.setName("Yerai");
		yerai.setPwd("fiestaencasa");		
		
		// Ahora añadimos a Yerai, cogiendo al root
		System.out.println("Añadimos usuario: " + yerai.getName() + " = " + stub1.addUser(yerai).getResponse());
		// Y hacemos login de Yerai
		System.out.println("login: " + yerai.getName() + " = " + stub3.login(yerai).getResponse());
		// Ahora hacemos logout de todos, incluso del root, pero este será el último
		
//		System.out.println("First logout");

		System.out.println("Second logout");
		stub3.logout();
		System.out.println("Third logout");
		stub2.logout();
	}

}
