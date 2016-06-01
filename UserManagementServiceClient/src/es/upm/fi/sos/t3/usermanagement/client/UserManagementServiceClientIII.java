package es.upm.fi.sos.t3.usermanagement.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import es.upm.fi.sos.t3.usermanagement.client.UserManagementWSStub.User;

public class UserManagementServiceClientIII {
	
	public static void main (String[] args) throws RemoteException  {
		// Ahora vamos a demostrar que no te puedes conectar directamente si 
		// Primero no ha hecho login el usuario
		
		UserManagementWSStub stub = new UserManagementWSStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		
		User user = new User();
		user.setName("Holita");
		user.setPwd("byebye");
		
		System.out.println("Intentamos login:" + stub.login(user).getResponse());
		
		// Hemos visto que no se puede logear porque no existe
		// Por lo que intentamos añadirlo
		// No podremos porque no somos root 
		
		System.out.println("Intentamos addUser: " + stub.addUser(user).getResponse());
	}

}
