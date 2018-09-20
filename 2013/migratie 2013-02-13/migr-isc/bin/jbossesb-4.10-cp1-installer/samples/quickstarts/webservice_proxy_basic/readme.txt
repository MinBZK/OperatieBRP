Overview:
=========
  Consumption of an external WS endpoint.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  	- In your server console, you will see output like this:

01:18:45,517 INFO  [SOAPProxy] mapped soapaction [""] to binding [{http://webservice_proxy_basic/helloworld}HelloWorldBinding]
01:18:45,518 INFO  [SOAPProxy] mapped operation [{http://webservice_proxy_basic/helloworld}sayHello] to binding [{http://webservice_proxy_basic/helloworld}HelloWorldBinding]
01:18:45,518 INFO  [SOAPProxy] mapped binding [{http://webservice_proxy_basic/helloworld}HelloWorldBinding] to transport [org.jboss.soa.esb.actions.soap.proxy.HttpSOAPProxyTransport] with endpoint address: [http://localhost:8080/Quickstart_webservice_proxy_basic/HelloWorldWS]

  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.

  3. Switch back to Application Server console to see the output from the ESB

  4. In this folder ("Window1"), type 'ant undeploy'.

  'runws' target description:
  - This simply calls the target JBossWS webservice directly.
  - The request url is: http://localhost:8080/Quickstart_webservice_proxy_basic/HelloWorldWS
  - Running it will create output similar to the runhttp output below.
  
  'runhttp' (alias 'runtest') target description:
  - This will exercise the target webservice *via* the SOAPProxy, first going
	through a Http Gateway Listener.
  - The request url is: http://localhost:8080/Quickstart_webservice_proxy_basic/http/Proxy_Basic/Proxy
  - Running it will create output like this in the server console:

01:19:15,983 INFO  [STDOUT] Message before SOAPProxy: 
01:19:15,983 INFO  [STDOUT] [<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hello="http://webservice_proxy_basic/helloworld"><soapenv:Header/><soapenv:Body><hello:sayHello><toWhom>dward</toWhom></hello:sayHello></soapenv:Body></soapenv:Envelope>].
01:19:15,993 INFO  [STDOUT] Message after SOAPProxy: 
01:19:15,993 INFO  [STDOUT] [<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:sayHelloResponse xmlns:ns2="http://webservice_proxy_basic/helloworld"><return>Hello 'dward' on Mon Jul 06 01:19:15 GMT-05:00 2009</return></ns2:sayHelloResponse></env:Body></env:Envelope>].

  ...and this in the client console:

     [java] ****  REQUEST  URL: http://localhost:8080/Quickstart_webservice_proxy_basic/http/Proxy_Basic/Proxy
     [java] ****  REQUEST BODY: <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hello="http://webservice_proxy_basic/helloworld"><soapenv:Header/><soapenv:Body><hello:sayHello><toWhom>dward</toWhom></hello:sayHello></soapenv:Body></soapenv:Envelope>
     [java] 01:19:15,946 DEBUG [main][header] >> "POST / HTTP/1.1[\r][\n]"
     [java] 01:19:15,978 DEBUG [main][header] >> "Content-Type: text/xml;charset=UTF-8[\r][\n]"
     [java] 01:19:15,978 DEBUG [main][header] >> "SOAPAction: ""[\r][\n]"
     [java] 01:19:15,979 DEBUG [main][header] >> "User-Agent: Jakarta Commons-HttpClient/3.0.1[\r][\n]"
     [java] 01:19:15,979 DEBUG [main][header] >> "Host: localhost:8080[\r][\n]"
     [java] 01:19:15,979 DEBUG [main][header] >> "Content-Length: 253[\r][\n]"
     [java] 01:19:15,980 DEBUG [main][header] >> "[\r][\n]"
     [java] 01:19:15,980 DEBUG [main][content] >> "<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hello="http://webservice_proxy_basic/helloworld"><soapenv:Header/><soapenv:Body><hello:sayHello><toWhom>dward</toWhom></hello:sayHello></soapenv:Body></soapenv:Envelope>"
     [java] 01:19:15,994 DEBUG [main][header] << "HTTP/1.1 200 OK[\r][\n]"
     [java] 01:19:15,995 DEBUG [main][header] << "Server: Apache-Coyote/1.1[\r][\n]"
     [java] 01:19:15,996 DEBUG [main][header] << "X-Powered-By: Servlet 2.4; JBoss-4.2.3.GA (build: SVNTag=JBoss_4_2_3_GA date=200807181417)/JBossWeb-2.0[\r][\n]"
     [java] 01:19:15,996 DEBUG [main][header] << "Date: Mon, 06 Jul 2009 06:19:15 GMT[\r][\n]"
     [java] 01:19:15,996 DEBUG [main][header] << "Content-Type: text/xml;charset=UTF-8[\r][\n]"
     [java] 01:19:15,996 DEBUG [main][header] << "Content-Length: 297[\r][\n]"
     [java] **** RESPONSE CODE: 200
     [java] 01:19:16,000 DEBUG [main][content] << "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:sayHelloResponse xmlns:ns2="http://webservice_proxy_basic/helloworld"><return>Hello 'dward' on Mon Jul 06 01:19:15 GMT-05:00 2009</return></ns2:sa"
     [java] 01:19:16,000 DEBUG [main][content] << "yHelloResponse></env:Body></env:Envelope>"
     [java] **** RESPONSE BODY: <env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:sayHelloResponse xmlns:ns2="http://webservice_proxy_basic/helloworld"><return>Hello 'dward' on Mon Jul 06 01:19:15 GMT-05:00 2009</return></ns2:sayHelloResponse></env:Body></env:Envelope>


Project file descriptions:
==========================
	jboss-esb.xml:
	A Http Gateway Listener accepts the incoming HTTP call makes it available to the
	Proxy_Basic/Proxy service, who has a message exchange pattern of RequestResponse.  In the
	action chain, there are 3 actions:
	1) echo out the incoming SOAP request,
	2) use the SOAPProxy class to invoke the original webservice endpoint,
	3) echo out the outgoing SOAP response.
	Configuration:
	- wsdl (required): The original wsdl url whose WS endpoint will get re-written and exposed as
	new wsdl from the ESB:
	Depending upon the <definitions><service><port><soap:address location attribute's protocol
	(for example "http"), a protocol-specific SOAPProxyTransport implementation is used.
	For other possible configuration properties, see the specific SOAPProxyTransport implementations
	themselves.

	jboss-esb.xml wsdl Note:
		The replacement of the http:// wsdl URL with the internal:// format,
		as well as the inclusion of the wsbarrier-service.xml, is so that on
		restart of the ESB server, when Tomcat's HTTP Connector is not yet
		available, the wsdl can still be pulled internally rather than over
		HTTP.  For more details, please refer to:
		https://jira.jboss.org/jira/browse/JBESB-2855 and
		https://jira.jboss.org/jira/browse/JBESB-2947

	log4j.xml:
	Needed to configure log4J used by the quickstart.

	src/../SendWSMessage.java:
	Sends a SOAP message to the target webservice endpoint (see "ant run*" above).
	
	build.xml:
	Targets and structure description:

	*	the runws task calls the
		org.jboss.soa.esb.samples.quickstart.webservice_proxy_basic.test.SendWSMessage class and
		passes in and argument representing the *ORIGINAL* webservice endpoint
		and an argument representing the string-based message to invoke the webservice with. 

	*	the runhttp (also runtest) task calls the
		org.jboss.soa.esb.samples.quickstart.webservice_proxy_basic.test.SendWSMessage class and
		passes in and argument representing the *PROXIED* webservice endpoint (via HTTP)
		and an argument representing the string-based message to invoke the webservice with. 

