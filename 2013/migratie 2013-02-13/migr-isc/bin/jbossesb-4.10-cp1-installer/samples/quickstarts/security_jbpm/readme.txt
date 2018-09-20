Overview:
=========
  The purpose of the security_jbpm quickstart demonstrate security integration with jBPM and JBossESB.
  There are three services in this quickstart and a jBPM process to orchestrate these services.

  All of the services print the Subject instance information to show the security Principals.
  
Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. Type 'ant deploy'			-- Will deploy the ESB process archive(.esb). And copy esb-users.properties and esb-roles.properties to <serverName>config/props
  2. Copy the contents of login-config.xml to <serverName>/conf/login-config.xml -- This adds the login module.
  3. Start server				-- Required so that the properties files will be picked up.
  4. Type 'ant deployProcess'	-- Will deploy the jBPM HelloWorld processdefinition.
  5. Type 'ant runtest'			-- Will invoke the service by using the ServiceInvoker.


What to look for in this quickstart
===================================
	Flow of events:
	1. 'ant runtest' invokes the ESB using the ServiceInoker and calls the Service 'HelloWorldStartServiceProcess'.
		This ESB service prints the following to the server console window:
		INFO  [STDOUT] 'ServiceName 'HelloWorldStartServiceProcess'. Subject:Subject:
			Principal: esbuser
			Principal: Roles(members:esbrole)
			Principal: [groupName=Roles, members=[[roleName=adminRole]]]

	2. HelloWorldStartServiceProcess start the jBPM process. The process-definition-name is 'helloworld' and 
	   can be found in processDefinitions/processdefinition.xml.

	3. The jBPM process will call out to the ESB service named 'HelloWorldService1'.
	   This ESB service prints the following to the server console window:
	   INFO  [STDOUT] 'ServiceName 'HelloWorldService1'. Subject:Subject:
			Principal: esbuser
			Principal: Roles(members:esbrole)
			Principal: [groupName=Roles, members=[[roleName=adminRole]]]
	   Next this service will route to 'HelloWorldService2'

	4. This ESB service prints the following to the server console window:
	   INFO  [STDOUT] 'ServiceName 'HelloWorldService2'. Subject:Subject:
			Principal: esbuser
			Principal: Roles(members:esbrole)
			Principal: [groupName=Roles, members=[[roleName=adminRole]]]

	Things to try:
	* Try commenting out the security configuration for 'HelloWorldService1'. Notice how the subject is not set for 
	  the service in this case, but the following service still works as expected.

	

		


