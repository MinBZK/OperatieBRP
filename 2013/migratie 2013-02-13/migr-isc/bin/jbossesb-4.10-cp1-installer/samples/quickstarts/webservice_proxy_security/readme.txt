Overview:
=========
  Consumption of an external WS endpoint protected by BASIC Auth + SSL.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. *BEFORE* you start the server:
     - type 'ant genkey'
     - copy the contents of build/server.xml *into* 
	   <JBOSS_AS_HOME>/server/default/deploy/jboss-web.deployer/server.xml
	   (or if deploying to JBoss AS 5.1.0.GA, 
	   <JBOSS_AS_HOME>/server/default/deploy/jbossweb.sar/server.xml) 

  2. start the server in <JBOSS_AS_HOME>/bin/ with ./run.sh

  3. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  	- In your server console, you will see output like this:

15:10:03,777 INFO  [SOAPProxy] mapped soapaction [""] to binding [{http://webservice_proxy_security/helloworld}HelloWorldBinding]
15:10:03,777 INFO  [SOAPProxy] mapped operation [{http://webservice_proxy_security/helloworld}sayHello] to binding [{http://webservice_proxy_security/helloworld}HelloWorldBinding]
15:10:03,796 INFO  [SOAPProxy] mapped binding [{http://webservice_proxy_security/helloworld}HelloWorldBinding] to transport [org.jboss.soa.esb.actions.soap.proxy.HttpSOAPProxyTransport] with endpoint address: [https://localhost:8443/webservice_proxy_security/HelloWorldWS]

  4. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.

  5. Switch back to Application Server console to see the output from the ESB

  6. In this folder ("Window1"), type 'ant undeploy'.

  'runws' target description:
  - This calls the target JBossWS webservice directly setting the
	Authorization header and using HTTPClient for SSL.
  - The request url is:
	https://localhost:8443/webservice_proxy_security/HelloWorldWS
  - Running it will create output similar to the runhttps output below.
  
  'runhttps' (alias 'runtest') target description:
  - This will exercise the target webservice *via* the SOAPProxy, first going
	through a Http Gateway Listener.
  - The request url is: https://localhost:8443/Quickstart_webservice_proxy_security/http/ProxyWS
  - Running it will create output like this in the server console:

15:11:11,693 INFO  [STDOUT] Message before SOAPProxy: 
15:11:11,693 INFO  [STDOUT] [<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hello="http://webservice_proxy_security/helloworld"><soapenv:Header/><soapenv:Body><hello:sayHello><toWhom>dward</toWhom></hello:sayHello></soapenv:Body></soapenv:Envelope>].
15:11:11,969 INFO  [STDOUT] Message after SOAPProxy: 
15:11:11,969 INFO  [STDOUT] [<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:sayHelloResponse xmlns:ns2="http://webservice_proxy_security/helloworld"><return>Hello 'dward' on Wed Jul 29 15:11:11 GMT-05:00 2009</return></ns2:sayHelloResponse></env:Body></env:Envelope>].

  ...and this in the client console:

     [java] ****  REQUEST  URL: https://localhost:8443/Quickstart_webservice_proxy_security/http/ProxyWS
     [java] ****  REQUEST BODY: <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hello="http://webservice_proxy_security/helloworld"><soapenv:Header/><soapenv:Body><hello:sayHello><toWhom>dward</toWhom></hello:sayHello></soapenv:Body></soapenv:Envelope>
     [java] 15:11:11,392 DEBUG [main][header] >> "POST /Quickstart_webservice_proxy_security/http/ProxyWS HTTP/1.1[\r][\n]"
     [java] 15:11:11,426 DEBUG [main][header] >> "Content-Type: text/xml;charset=UTF-8[\r][\n]"
     [java] 15:11:11,427 DEBUG [main][header] >> "Authorization: Basic YWRtaW46YWRtaW4=[\r][\n]"
     [java] 15:11:11,427 DEBUG [main][header] >> "SOAPAction: ""[\r][\n]"
     [java] 15:11:11,427 DEBUG [main][header] >> "User-Agent: Jakarta Commons-HttpClient/3.0.1[\r][\n]"
     [java] 15:11:11,428 DEBUG [main][header] >> "Host: localhost:8443[\r][\n]"
     [java] 15:11:11,428 DEBUG [main][header] >> "Content-Length: 254[\r][\n]"
     [java] 15:11:11,428 DEBUG [main][header] >> "[\r][\n]"
     [java] 15:11:11,428 DEBUG [main][content] >> "<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hello="http://webservice_proxy_security/helloworld"><soapenv:Header/><soapenv:Body><hello:sayHello><toWhom>dward</toWhom></hello:sayHello></soapenv:Body></soapenv:Envelope>"
     [java] 15:11:11,972 DEBUG [main][header] << "HTTP/1.1 200 OK[\r][\n]"
     [java] 15:11:11,974 DEBUG [main][header] << "Server: Apache-Coyote/1.1[\r][\n]"
     [java] 15:11:11,974 DEBUG [main][header] << "X-Powered-By: Servlet 2.4; JBoss-4.2.3.GA (build: SVNTag=JBoss_4_2_3_GA date=200807181417)/JBossWeb-2.0[\r][\n]"
     [java] 15:11:11,974 DEBUG [main][header] << "Date: Wed, 29 Jul 2009 20:11:11 GMT[\r][\n]"
     [java] 15:11:11,974 DEBUG [main][header] << "Content-Type: text/xml;charset=UTF-8[\r][\n]"
     [java] 15:11:11,974 DEBUG [main][header] << "Content-Length: 298[\r][\n]"
     [java] 15:11:11,974 DEBUG [main][header] << "Connection: close[\r][\n]"
     [java] **** RESPONSE CODE: 200
     [java] 15:11:11,976 DEBUG [main][content] << "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:sayHelloResponse xmlns:ns2="http://webservice_proxy_security/helloworld"><return>Hello 'dward' on Wed Jul 29 15:11:11 GMT-05:00 2009</return></ns2:s"
     [java] 15:11:11,976 DEBUG [main][content] << "ayHelloResponse></env:Body></env:Envelope>"
     [java] **** RESPONSE BODY: <env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:sayHelloResponse xmlns:ns2="http://webservice_proxy_security/helloworld"><return>Hello 'dward' on Wed Jul 29 15:11:11 GMT-05:00 2009</return></ns2:sayHelloResponse></env:Body></env:Envelope>

NOTE:
=====

If you get a client-side exception that looks like this:
	Exception in thread "main" javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target

Then read the explanation and follow the instructions outlined here to resolve it:
	http://dreamingthings.blogspot.com/2006/12/no-more-unable-to-find-valid.html

You can also read more about the behavior of JSSE here:
	http://java.sun.com/j2se/1.5.0/docs/guide/security/jsse/JSSERefGuide.html#X509TrustManager

Project file descriptions:
==========================
	jboss-esb-template.xml (transformed to build/jboss-esb.xml after "genkey" target):
	A Http Gateway Listener accepts the incoming HTTPS call and makes it available to the
	Proxy_Security/Proxy service, who has a message exchange pattern of RequestResponse.  In the
	action chain, there are 3 actions:
	1) echo out the incoming SOAP request,
	2) use the SOAPProxy class to invoke the original webservice endpoint,
	also using SSL and passing along BASIC Auth credentials.
	3) echo out the outgoing SOAP response.
	Configuration:
	- wsdl: the wsdl of the original endpoint behind SSL + Basic Auth
	- endpointUrl: required to make sure "localhost" is used instead of "127.0.0.1" so that the cert matches
	- file: the Apache Commons HTTPClient file which allows the proxy to talk over SSL to the original endpoint

	jboss-esb.xml wsdl Note:
		The replacement of the https:// wsdl URL with the internal:// format,
		as well as the inclusion of the wsbarrier-service.xml, is so that on
		restart of the ESB server, when Tomcat's HTTP Connector is not yet
		available, the wsdl can still be pulled internally rather than over
		HTTP.  For more details, please refer to:
		https://jira.jboss.org/jira/browse/JBESB-2855 and
		https://jira.jboss.org/jira/browse/JBESB-2947

	For an explanation of all possible configuration properties, please refer to the SOAPProxy section of the Programmer's Guide.

	log4j.xml:
	Needed to configure log4J used by the quickstart.

	build/httpclient-8443.properties:
	HTTPClient config file used to talk BASI Auth + SSL

	src/../SendWSMessage.java:
	Sends a SOAP message to the target webservice endpoint (see "ant run*" above) over
	SSL + Basic Auth.

	build.xml:
	Targets and structure description:

	*	the runws task calls the
		org.jboss.soa.esb.samples.quickstart.webservice_proxy_security.test.SendWSMessage class and
		passes in and argument representing the *ORIGINAL* webservice endpoint
		and an argument representing the string-based message to invoke the webservice with. 

	*	the runhttps (also runtest) task calls the
		org.jboss.soa.esb.samples.quickstart.webservice_proxy_security.test.SendWSMessage class and
		passes in and argument representing the *PROXIED* webservice endpoint (via HTTP)
		and an argument representing the string-based message to invoke the webservice with. 


