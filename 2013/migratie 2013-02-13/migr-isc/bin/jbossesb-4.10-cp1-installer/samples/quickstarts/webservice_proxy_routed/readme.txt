Overview:
=========
  Route to appropriate service to fulfill the awaiting WS client's request.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  	- In your server console, you will see output like this in the server console:

01:01:00,370 INFO  [SOAPProxy] mapped soapaction [""] to binding [{http://webservice_proxy_routed/hello}HelloBinding]
01:01:00,370 INFO  [SOAPProxy] mapped operation [{http://webservice_proxy_routed/hello}sayHello] to binding [{http://webservice_proxy_routed/hello}HelloBinding]
01:01:00,370 INFO  [SOAPProxy] mapped binding [{http://webservice_proxy_routed/hello}HelloBinding] to transport [org.jboss.soa.esb.actions.soap.proxy.HttpSOAPProxyTransport] with endpoint address: [http://localhost:8080/Quickstart_webservice_proxy_routed/HelloWS]
...
01:01:00,571 INFO  [SOAPProxy] mapped soapaction [""] to binding [{http://webservice_proxy_routed/goodbye}GoodbyeBinding]
01:01:00,572 INFO  [SOAPProxy] mapped operation [{http://webservice_proxy_routed/goodbye}sayGoodbye] to binding [{http://webservice_proxy_routed/goodbye}GoodbyeBinding]
01:01:00,572 INFO  [SOAPProxy] mapped binding [{http://webservice_proxy_routed/goodbye}GoodbyeBinding] to transport [org.jboss.soa.esb.actions.soap.proxy.HttpSOAPProxyTransport] with endpoint address: [http://localhost:8080/Quickstart_webservice_proxy_routed/GoodbyeWS]

  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.

  3. Switch back to Application Server console to see the output from the ESB

  4. In this folder ("Window1"), type 'ant undeploy'.

  'runhello' (alias 'runtest') target description:
  - This will exercise the Hello webservice *via* a SOAPProxy, first going
	through a Http Gateway Listener, then a ContentBasedRouter
	to know whih proxied web service to invoke, which in this case is HelloWS.
  - The request url is: http://localhost:8080/Quickstart_webservice_proxy_routed/http/Proxy_Routed/Proxy_CBR
  - Running it will create output like this in the server console:

01:02:33,412 INFO  [STDOUT] Message before ContentBasedRouter: 
01:02:33,412 INFO  [STDOUT] [<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hello="http://webservice_proxy_routed/hello"><soapenv:Header/><soapenv:Body><hello:sayHello><toWhom>dward</toWhom></hello:sayHello></soapenv:Body></soapenv:Envelope>].
01:02:33,429 INFO  [STDOUT] Routing to Hello...
01:02:33,442 INFO  [STDOUT] Message before Hello SOAPProxy: 
01:02:33,442 INFO  [STDOUT] [<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hello="http://webservice_proxy_routed/hello"><soapenv:Header/><soapenv:Body><hello:sayHello><toWhom>dward</toWhom></hello:sayHello></soapenv:Body></soapenv:Envelope>].
01:02:33,450 INFO  [STDOUT] Message after Hello SOAPProxy: 
01:02:33,450 INFO  [STDOUT] [<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:sayHelloResponse xmlns:ns2="http://webservice_proxy_routed/hello"><return>Hello 'dward' on Mon Jul 06 01:02:33 GMT-05:00 2009</return></ns2:sayHelloResponse></env:Body></env:Envelope>].

  ...and this in the client console:

     [java] ****  REQUEST  URL: http://localhost:8080/Quickstart_webservice_proxy_routed/http/Proxy_Routed/Proxy_CBR
     [java] ****  REQUEST BODY: <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hello="http://webservice_proxy_routed/hello"><soapenv:Header/><soapenv:Body><hello:sayHello><toWhom>dward</toWhom></hello:sayHello></soapenv:Body></soapenv:Envelope>
     [java] 01:02:33,380 DEBUG [main][header] >> "POST / HTTP/1.1[\r][\n]"
     [java] 01:02:33,401 DEBUG [main][header] >> "Content-Type: text/xml;charset=UTF-8[\r][\n]"
     [java] 01:02:33,402 DEBUG [main][header] >> "SOAPAction: ""[\r][\n]"
     [java] 01:02:33,402 DEBUG [main][header] >> "User-Agent: Jakarta Commons-HttpClient/3.0.1[\r][\n]"
     [java] 01:02:33,403 DEBUG [main][header] >> "Host: localhost:8080[\r][\n]"
     [java] 01:02:33,403 DEBUG [main][header] >> "Content-Length: 249[\r][\n]"
     [java] 01:02:33,403 DEBUG [main][header] >> "[\r][\n]"
     [java] 01:02:33,403 DEBUG [main][content] >> "<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hello="http://webservice_proxy_routed/hello"><soapenv:Header/><soapenv:Body><hello:sayHello><toWhom>dward</toWhom></hello:sayHello></soapenv:Body></soapenv:Envelope>"
     [java] 01:02:33,460 DEBUG [main][header] << "HTTP/1.1 200 OK[\r][\n]"
     [java] 01:02:33,461 DEBUG [main][header] << "Server: Apache-Coyote/1.1[\r][\n]"
     [java] 01:02:33,462 DEBUG [main][header] << "X-Powered-By: Servlet 2.4; JBoss-4.2.3.GA (build: SVNTag=JBoss_4_2_3_GA date=200807181417)/JBossWeb-2.0[\r][\n]"
     [java] 01:02:33,462 DEBUG [main][header] << "Date: Mon, 06 Jul 2009 06:02:33 GMT[\r][\n]"
     [java] 01:02:33,462 DEBUG [main][header] << "Content-Type: text/xml;charset=UTF-8[\r][\n]"
     [java] 01:02:33,462 DEBUG [main][header] << "Content-Length: 293[\r][\n]"
     [java] **** RESPONSE CODE: 200
     [java] 01:02:33,466 DEBUG [main][content] << "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:sayHelloResponse xmlns:ns2="http://webservice_proxy_routed/hello"><return>Hello 'dward' on Mon Jul 06 01:02:33 GMT-05:00 2009</return></ns2:sayHel"
     [java] 01:02:33,466 DEBUG [main][content] << "loResponse></env:Body></env:Envelope>"
     [java] **** RESPONSE BODY: <env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:sayHelloResponse xmlns:ns2="http://webservice_proxy_routed/hello"><return>Hello 'dward' on Mon Jul 06 01:02:33 GMT-05:00 2009</return></ns2:sayHelloResponse></env:Body></env:Envelope>e>

  'rungoodbye' target description:
  - This will exercise the Goodbye webservice *via* a SOAPProxy, first going
	through a Http Gateway Listener, then a ContentBasedRouter
	to know whih proxied web service to invoke, which in this case is GoodbyeWS.
  - The request url is: http://localhost:8080/Quickstart_webservice_proxy_routed/http/Proxy_Routed/Proxy_CBR
  - Running it will create output like this in the server console:

01:03:32,618 INFO  [STDOUT] Message before ContentBasedRouter: 
01:03:32,618 INFO  [STDOUT] [<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:goodbye="http://webservice_proxy_routed/goodbye"><soapenv:Header/><soapenv:Body><goodbye:sayGoodbye><toWhom>dward</toWhom></goodbye:sayGoodbye></soapenv:Body></soapenv:Envelope>].
01:03:32,626 INFO  [STDOUT] Routing to Goodbye...
01:03:32,724 INFO  [STDOUT] Message before Goodbye SOAPProxy: 
01:03:32,724 INFO  [STDOUT] [<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:goodbye="http://webservice_proxy_routed/goodbye"><soapenv:Header/><soapenv:Body><goodbye:sayGoodbye><toWhom>dward</toWhom></goodbye:sayGoodbye></soapenv:Body></soapenv:Envelope>].
01:03:32,739 INFO  [STDOUT] Message after Goodbye SOAPProxy: 
01:03:32,739 INFO  [STDOUT] [<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:sayGoodbyeResponse xmlns:ns2="http://webservice_proxy_routed/goodbye"><return>Goodbye 'dward' on Mon Jul 06 01:03:32 GMT-05:00 2009</return></ns2:sayGoodbyeResponse></env:Body></env:Envelope>].].

  ...and this in the client console:

     [java] ****  REQUEST  URL: http://localhost:8080/Quickstart_webservice_proxy_routed/http/Proxy_Routed/Proxy_CBR
     [java] ****  REQUEST BODY: <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:goodbye="http://webservice_proxy_routed/goodbye"><soapenv:Header/><soapenv:Body><goodbye:sayGoodbye><toWhom>dward</toWhom></goodbye:sayGoodbye></soapenv:Body></soapenv:Envelope>
     [java] 01:03:32,488 DEBUG [main][header] >> "POST / HTTP/1.1[\r][\n]"
     [java] 01:03:32,520 DEBUG [main][header] >> "Content-Type: text/xml;charset=UTF-8[\r][\n]"
     [java] 01:03:32,520 DEBUG [main][header] >> "SOAPAction: ""[\r][\n]"
     [java] 01:03:32,521 DEBUG [main][header] >> "User-Agent: Jakarta Commons-HttpClient/3.0.1[\r][\n]"
     [java] 01:03:32,528 DEBUG [main][header] >> "Host: localhost:8080[\r][\n]"
     [java] 01:03:32,529 DEBUG [main][header] >> "Content-Length: 261[\r][\n]"
     [java] 01:03:32,529 DEBUG [main][header] >> "[\r][\n]"
     [java] 01:03:32,529 DEBUG [main][content] >> "<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:goodbye="http://webservice_proxy_routed/goodbye"><soapenv:Header/><soapenv:Body><goodbye:sayGoodbye><toWhom>dward</toWhom></goodbye:sayGoodbye></soapenv:Body></soapenv:Envelope>"
     [java] 01:03:32,759 DEBUG [main][header] << "HTTP/1.1 200 OK[\r][\n]"
     [java] 01:03:32,760 DEBUG [main][header] << "Server: Apache-Coyote/1.1[\r][\n]"
     [java] 01:03:32,761 DEBUG [main][header] << "X-Powered-By: Servlet 2.4; JBoss-4.2.3.GA (build: SVNTag=JBoss_4_2_3_GA date=200807181417)/JBossWeb-2.0[\r][\n]"
     [java] 01:03:32,761 DEBUG [main][header] << "Date: Mon, 06 Jul 2009 06:03:32 GMT[\r][\n]"
     [java] 01:03:32,761 DEBUG [main][header] << "Content-Type: text/xml;charset=UTF-8[\r][\n]"
     [java] 01:03:32,761 DEBUG [main][header] << "Content-Length: 301[\r][\n]"
     [java] **** RESPONSE CODE: 200
     [java] 01:03:32,764 DEBUG [main][content] << "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:sayGoodbyeResponse xmlns:ns2="http://webservice_proxy_routed/goodbye"><return>Goodbye 'dward' on Mon Jul 06 01:03:32 GMT-05:00 2009</return></ns2:"
     [java] 01:03:32,764 DEBUG [main][content] << "sayGoodbyeResponse></env:Body></env:Envelope>"
     [java] **** RESPONSE BODY: <env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:sayGoodbyeResponse xmlns:ns2="http://webservice_proxy_routed/goodbye"><return>Goodbye 'dward' on Mon Jul 06 01:03:32 GMT-05:00 2009</return></ns2:sayGoodbyeResponse></env:Body></env:Envelope>

Project file descriptions:
==========================
	jboss-esb.xml:
	A Http Gateway Listener accepts the incoming HTTP call makes it available to the
	Proxy_Routed/Proxy_CBR service, who uses a ContentBasedRouter configured with a drools rules
	file.
	
	jboss-esb.xml wsdl Note:
		The replacement of the http:// wsdl URL with the internal:// format,
		as well as the inclusion of the wsbarrier-service.xml, is so that on
		restart of the ESB server, when Tomcat's HTTP Connector is not yet
		available, the wsdl can still be pulled internally rather than over
		HTTP.  For more details, please refer to:
		https://jira.jboss.org/jira/browse/JBESB-2855 and
		https://jira.jboss.org/jira/browse/JBESB-2947

	Proxy_Routed_Rules.drl:
	Uses xpath expressions to match the body content of the soap envelope, and
	depending on that content, route to either the "hello" destination (which
	invokes the HelloWS via a SOAPProxy), or the "goodbye" desitination
	(which invokes the GoodbyeWS via a SOAPProxy).

	log4j.xml:
	Needed to configure log4J used by the quickstart.

	src/../SendWSMessage.java:
	Sends a SOAP message to the target webservice endpoint (see "ant run*" above).
	
	build.xml:
	Targets and structure description:

	*	the runhello (also runtest) task calls the
		org.jboss.soa.esb.samples.quickstart.webservice_proxy_routed.test.SendWSMessage class and
		passes in and argument representing the *PROXIED* webservice endpoint (via HTTP)
		and an argument representing the string-based message to invoke the webservice with. 
		SendWSMessage will submit a soap request which the CBR will determine to route to the proxied
		(via SOAPProxy) HelloWS.

	*	the rungoodbye task calls the
		org.jboss.soa.esb.samples.quickstart.webservice_proxy_routed.test.SendWSMessage class and
		passes in and argument representing the *PROXIED* webservice endpoint (via HTTP Gateway)
		and an argument representing the string-based message to invoke the webservice with. 
		SendWSMessage will submit a soap request which the CBR will determine to route to the proxied
		(via SOAPProxy) GoodbyeWS.

