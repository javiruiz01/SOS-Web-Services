#! /bin/bash

# Borramos el .aar de services
echo "Removing from tomcat services"
cd ~/software2015_linux/apache-tomcat-7.0.59/webapps/axis2/WEB-INF/services/
rm -i UserManagementWS.aar
# Y ahora lo borramos del workspace
echo "Removing from workspace"
cd ~/workspace/UserManagementService/build/lib/
rm -i UserManagementWS.aar
cd ~/workspace/UserManagementService/
echo "Compiling & Generating .aar file"
ant
echo "Deploying to webService folder in apache tomcat"
cp build/lib/UserManagementWS.aar ~/software2015_linux/apache-tomcat-7.0.59/webapps/axis2/WEB-INF/services/
echo "Done"
