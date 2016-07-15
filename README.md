# SOS-Web-Services

Authors: [Javier Ruiz Calle] (https://github.com/javiruiz01) and [Yerai Zamorano] (https://github.com/yerai).

Assignment for the subject **Service Oriented Systems** (SOS) of the Technical University of Madrid.

The assignment consisted in making a Web Service that emulated an API for the administritation of different users, where there was an administrator and users.

The following actions were possible:

1. For the *administrator*:
  1. Add user
  2. Remove user
2. For the *users*:
  1. Login
  2. Logout
  3. Change password
  
It's made possible by using Axis2 and SOAP, with the scope set as SoapSession for session managament. 

### To-Do:
- [ ] Improve the efficiency of the program, it takes too long when submitted to a stress test.
- [ ] Do not allow session hijacking.
