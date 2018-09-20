Overview:
=========
  This quickstart aims to demonstrate how certificate based authentication can be configured in JBossESB.
  Authentication is done by verifying that the certificate that is passed to the ESB by the calling client
  can be verified against a certificate in a local keystore.
  The certifcate to be verified against is specified using an alias which is configured in jboss-esb.xml

  When executing this quickstart two calls will be made to the ESB service:
  1. This call will use the content of the following file as its payload
	 src/org/jboss/soa/esb/samples/quickstart/securitycert/test/soap_message_01.xml
	 soap_messsag_01.xml contains a security header with the certificate of the alias 'certtest'.
	 This certificate matches the certificate in our keystore and this call will succeed.

  2. This call will use the content of the following file as its payload
	 src/org/jboss/soa/esb/samples/quickstart/securitycert/test/soap_message_02.xml
	 soap_messsag_01.xml contains a security header with the certificate of the alias 'certtest2'.
	 This certificate does not match the certificate in our keystore and this call will not succeed.

  The keystore used for this quickstart is named 'keystore' and can be found in the same directory as this
  readme.txt file.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
=======
  1. Type 'ant deploy'.
  2. Copy the xml element from build/login-config.xml and paste it into your servers conf/login-config.xml
	 This contains the login module configuration used by this quickstart.
  3. Restart you jbossesb server. This is needed so that the security configuration get picked up.
  4. Type 'ant runtest'.
  5. Switch back to Application Server console to see the output from the ESB
  6. In this folder ("Window1"), type 'ant undeploy'.

Things to look for in this quickstart:
======================================
  1.Security configuration in jboss-esb.xml
	<security moduleName="CertLogin" rolesAllowed="worker" callbackHandler="org.jboss.soa.esb.services.security.auth.login.CertCallbackHandler">
		<property name="alias" value="certtest"/>
	</security>
	# 'moduleName' identified the JAAS Login Module to use. This is an index into the file login-config.xml file.
	# 'rolesAllowed' lists the roles that are allowed to execute this service. To see how the roles are mapped please see item 3 below.
	# 'alias' specifies the alias that will be used to identify a certificate in the keystore.
	# 'callbackHandler' is the an ESB implementation of a JAAS Callback handler which provides access to the authentication request and also the above security configuration.

  2.JAAS Configuration
    <application-policy name = "CertLogin">
        <authentication>
             <login-module code = "org.jboss.soa.esb.services.security.auth.login.CertificateLoginModule" flag = "required" >
                <module-option name = "keyStoreURL">file://@KEYSTORE_PATH@</module-option>
                <module-option name = "keyStorePassword">storepassword</module-option>
                <module-option name = "rolesPropertiesFile">file://@ROLES_FILE_PATH@</module-option>
          </login-module>
        </authentication>
    </application-policy>
	# CertificateLoginModule is the login module that will be used.
	# 'keyStoreURL' is the path to the keystore that will be used to verify the certificates. This can be a file on the local file system or on the classpath.
	# 'keyStorePassword' is the password to the keystore.
	# 'rolesPropertiesFile' path to a file containing role mappings. Please see the next item for more information about the roles mapping.

  3.Role Mapping
	This file is can be optionally specified in login-config.xml by using the 'rolesPropertiesFile'. This can point to a file on the local file system or to
	a file on the classpath.
	The is an example of such a file:
		# user=role1,role2,...
		guest=guest
		esbuser=esbrole

		# The current implementation will use the Common Name(CN) specified for the certificate as the
		# user name. The unicode escape is needed only if your CN contains a space.
		Daniel\u0020Bevenius=esbrole,worker

