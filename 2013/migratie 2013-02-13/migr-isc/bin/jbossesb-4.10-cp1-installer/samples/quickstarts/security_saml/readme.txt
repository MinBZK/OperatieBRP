Overview:
=========
  This quickstart demonstrates JBossESB support for SAML. SAML support is provided by 
  using the PicketLink project's Security Token Service (PicketLinkSTS).

  The following will be demonstrated by this quickstart:
  * Using the PicketLink Project's STSIssuingLoginModule to isssue a SAML Assertion from PicketLinkSTS.
  * Using the PicketLink Project's STSValidatingLoginModule to validate a SAML Assertion from PicketLinkSTS.
  * Injecting the SAML Assertion into a SOAP Message.
  * Using SOAPProcessor to invoke an externa Web Service that is secured by PicketLinkSTS.

  (Note that this quickstart only works with AS 5.1.0.GA or higher)

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
========================
  1. Type 'ant deploy'.
  2. 'ant runtest' will send a HTTP request to a JBossRemoting Gatway

  # Please refer to the "Security" section of the ServiceGuide.pdf for more details on the security features of JBossESB.
	and http://www.jboss.org/community/wiki/JBossESBSAMLSupport for SAML specific information.

Quickstart content
==================
# picketlink-sts.war
The PicketLinkSTS.war is the PicketLink WS-Trust Security Token Service implementation.

# picketlink-sts-client.properties
The configuration for the both STSIssuingLoginModule and STSValidatingLoginModule.
Note that the username and password in this file is only used by the STSValidatingLoginModule. 
The STSIssuingLoginModule uses callbacks to retreive the username and password from the authentication 
request, which is extraced from the SOAP Security header (see soap-request.xml below).

# login-config-unfiltered.xml
The JBoss security configuration fragment required for this quickstart. This will be filtered by Ant
and the added to the esb archive.

# jboss-service.xml
Will dynamically add the login modules configured in login-config.xml.

# soap-request.xml
The soap request sent to the esb. The UsernameToken security header information is used by the STSIssuingLoginModule
as the username/credential for the user for whom a security token should be issued.

