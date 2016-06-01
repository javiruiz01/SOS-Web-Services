package es.upm.fi.sos.t3.usermanagement.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import es.upm.fi.sos.t3.usermanagement.client.UserManagementWSStub.User;

public class UserManagementServiceClientII {
	
	public static void main (String[] args) throws RemoteException {
		UserManagementWSStub root = new UserManagementWSStub();
		root._getServiceClient().engageModule("addressing");
		root._getServiceClient().getOptions().setManageSession(true);
		
		// Añadimos a dos usuarios
		User rootUser = new User();
		rootUser.setName("admin");
		rootUser.setPwd("admin");
		
		User javier = new User();
		javier.setName("Javier");
		javier.setPwd("javier");
		
		User yerai = new User();
		yerai.setName("Yerai");
		yerai.setPwd("yerai");
		
		root.login(rootUser);
		root.addUser(javier);
		root.addUser(yerai);
		
		// Ahora hacemos que los dos usuarios se conecten
		UserManagementWSStub javierSesion = new UserManagementWSStub();
		javierSesion._getServiceClient().engageModule("addressing");
		javierSesion._getServiceClient().getOptions().setManageSession(true);
		
		javierSesion.login(javier);
		
		UserManagementWSStub yeraiSesion = new UserManagementWSStub();
		yeraiSesion._getServiceClient().engageModule("addressing");
		yeraiSesion._getServiceClient().getOptions().setManageSession(true);
		
		yeraiSesion.login(yerai);
		
		// Ahora haríamos los dos logout
		
		javierSesion.logout();
		yeraiSesion.logout();		
	}

}
