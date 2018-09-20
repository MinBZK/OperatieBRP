Overview:
=========
  This quickstart demonstrates basic security in JBossESB. The following will be demonstrated by this quickstart:
	* Per service authentication configuration
	* Configuring the roles allowed to execute the service
	* Invoking a service via a gateway
	* Invoking a service directly (using the ServiceInvoker)

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. Copy+Paste the contents of ./login-config.xml *into* <server>/<configname>/conf/login-config.xml
  2. Start the server.
  3. Type 'ant deploy'.
  4. 'ant runtest' will send a HTTP request to a JBossRemoting Gatway
  5. 'ant sendesb' will invoke the Service directly using the ServiceInvoker

What to look for in this quickstart
===================================
  # Security configuration
	 <service category="Security" name="SimpleListenerSecured" description="Hello World">
            <security moduleName="jbossesb" rolesAllowed="esbrole"/>
	The security element declares that this service requires authentication to be executed and that the 
	authenticated users be a member in the role 'esbrole'. The role is determined by the JAAS login
	module being used, in this case by the 'jbossesb' module specified in login-config.xml.

  # src/org/jboss/soa/esb/samples/quickstart/securitybasic/test/HttpClient.java
	This is a JBossRemoting client that uses http to invoke the JBossRemoting gateway.

  # src/org/jboss/soa/esb/samples/quickstart/securitybasic/test/SendEsbMessage.java 
	This class uses the ServiceInvoker to invoke the ESB service directly, by-passing the gateway.
	You can see how an AuthenticationRequest is created then encrypted, and passed to the ESB
	by attaching the authentication request to the ESB Message object.

  # src/org/jboss/soa/esb/samples/quickstart/securitybasic/MyListenerAction.java 
	This ESB Action show how the currently autenticated JAAS Subject can be accessed:
	System.out.println("Subject in MyListenerAction : " + Subject.getSubject(AccessController.getContext()));

  # esb-users-properties
	User/password configuration.

  # esb-roles.properties
	Roles/users configuration.
	
  # Please refer to the "Security" section of the ServiceGuide.pdf for more details on the security features of JBossESB.


