Overview:
=========
  Versioning of proxied endpoints is an important aspect of an ESB.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  	- In your server console, you will see output like this in your server console:

01:05:02,128 INFO  [SOAPProxy] mapped soapaction [""] to binding [{http://webservice_proxy_versioning/invoicing}InvoicingBinding]
01:05:02,128 INFO  [SOAPProxy] mapped operation [{http://webservice_proxy_versioning/invoicing}processInvoice] to binding [{http://webservice_proxy_versioning/invoicing}InvoicingBinding]
01:05:02,128 INFO  [SOAPProxy] mapped binding [{http://webservice_proxy_versioning/invoicing}InvoicingBinding] to transport [org.jboss.soa.esb.actions.soap.proxy.HttpSOAPProxyTransport] with endpoint address: [http://localhost:8080/Quickstart_webservice_proxy_versioning/InvoicingWS]
....
01:05:02,158 INFO  [SOAPProxy] mapped soapaction [""] to binding [{http://webservice_proxy_versioning/invoicing}InvoicingBinding]
01:05:02,159 INFO  [SOAPProxy] mapped operation [{http://webservice_proxy_versioning/invoicing}processInvoice] to binding [{http://webservice_proxy_versioning/invoicing}InvoicingBinding]
01:05:02,159 INFO  [SOAPProxy] mapped binding [{http://webservice_proxy_versioning/invoicing}InvoicingBinding] to transport [org.jboss.soa.esb.actions.soap.proxy.HttpSOAPProxyTransport] with endpoint address: [http://localhost:8080/Quickstart_webservice_proxy_versioning/InvoicingWS]

  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.

  3. Switch back to Application Server console to see the output from the ESB

  4. In this folder ("Window1"), type 'ant undeploy'.

  'runws' target description:
  - This simply calls the target JBossWS webservice directly.
  - The request url is: http://localhost:8080/Quickstart_webservice_proxy_versioning/InvoicingWS
  - Running it will create output similar to the runnew output below.
  
  'runold' (alias 'runtest') target description:
  - This will exercise the target webservice *via* the SOAPProxy, first going through a Http Gateway Listener
    and having it's incoming SOAP request be *transformed* and having a <processDate> elemented added to the body!
  - Note that the wsl is transformed as well (see "Project file descriptions" below)!
  - The request url is: http://localhost:8080/Quickstart_webservice_proxy_versioning/http/Proxy_Versioning/Proxy-OldVersion
  - Running it will create output like this in the server console:

01:05:59,535 INFO  [STDOUT] Message before Transformation: 
01:05:59,535 INFO  [STDOUT] [<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:inv="http://webservice_proxy_versioning/invoicing"><soapenv:Header/><soapenv:Body><inv:processInvoice><invoiceNumber>0123456789</invoiceNumber></inv:processInvoice></soapenv:Body></soapenv:Envelope>].
01:05:59,543 INFO  [STDOUT] Message after Transformation but before SOAPProxy: 
01:05:59,543 INFO  [STDOUT] [<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:inv="http://webservice_proxy_versioning/invoicing">
<soapenv:Header/>
<soapenv:Body>
<inv:processInvoice>
<invoiceNumber>0123456789</invoiceNumber>
<processDate>2005-12-13T14:13:28.443+01:00</processDate>
</inv:processInvoice>
</soapenv:Body>
</soapenv:Envelope>
].
01:05:59,561 INFO  [InvoicingWS] processInvoice called with invoiceNumber [0123456789] and processDate [Tue Dec 13 08:13:28 GMT-05:00 2005]
01:05:59,565 INFO  [STDOUT] Message after SOAPProxy: 
01:05:59,565 INFO  [STDOUT] [<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:processInvoiceResponse xmlns:ns2="http://webservice_proxy_versioning/invoicing"><success>true</success></ns2:processInvoiceResponse></env:Body></env:Envelope>].

  - And this in the client console:

     [java] ****  REQUEST  URL: http://localhost:8080/Quickstart_webservice_proxy_versioning/http/Proxy_Versioning/Proxy-OldVersion
     [java] ****  REQUEST BODY: <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:inv="http://webservice_proxy_versioning/invoicing"><soapenv:Header/><soapenv:Body><inv:processInvoice><invoiceNumber>0123456789</invoiceNumber></inv:processInvoice></soapenv:Body></soapenv:Envelope>
     [java] 01:05:59,443 DEBUG [main][header] >> "POST / HTTP/1.1[\r][\n]"
     [java] 01:05:59,464 DEBUG [main][header] >> "Content-Type: text/xml;charset=UTF-8[\r][\n]"
     [java] 01:05:59,464 DEBUG [main][header] >> "SOAPAction: ""[\r][\n]"
     [java] 01:05:59,465 DEBUG [main][header] >> "User-Agent: Jakarta Commons-HttpClient/3.0.1[\r][\n]"
     [java] 01:05:59,465 DEBUG [main][header] >> "Host: localhost:8080[\r][\n]"
     [java] 01:05:59,465 DEBUG [main][header] >> "Content-Length: 282[\r][\n]"
     [java] 01:05:59,465 DEBUG [main][header] >> "[\r][\n]"
     [java] 01:05:59,466 DEBUG [main][content] >> "<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:inv="http://webservice_proxy_versioning/invoicing"><soapenv:Header/><soapenv:Body><inv:processInvoice><invoiceNumber>0123456789</invoiceNumber></inv:processInvoice></soapenv:Body></soapenv:Envelope>"
     [java] 01:05:59,574 DEBUG [main][header] << "HTTP/1.1 200 OK[\r][\n]"
     [java] 01:05:59,577 DEBUG [main][header] << "Server: Apache-Coyote/1.1[\r][\n]"
     [java] 01:05:59,577 DEBUG [main][header] << "X-Powered-By: Servlet 2.4; JBoss-4.2.3.GA (build: SVNTag=JBoss_4_2_3_GA date=200807181417)/JBossWeb-2.0[\r][\n]"
     [java] 01:05:59,577 DEBUG [main][header] << "Date: Mon, 06 Jul 2009 06:05:59 GMT[\r][\n]"
     [java] 01:05:59,577 DEBUG [main][header] << "Content-Type: text/xml;charset=UTF-8[\r][\n]"
     [java] 01:05:59,578 DEBUG [main][header] << "Content-Length: 268[\r][\n]"
     [java] **** RESPONSE CODE: 200
     [java] 01:05:59,581 DEBUG [main][content] << "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:processInvoiceResponse xmlns:ns2="http://webservice_proxy_versioning/invoicing"><success>true</success></ns2:processInvoiceResponse></env:Body></e"
     [java] 01:05:59,581 DEBUG [main][content] << "nv:Envelope>"
     [java] **** RESPONSE BODY: <env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:processInvoiceResponse xmlns:ns2="http://webservice_proxy_versioning/invoicing"><success>true</success></ns2:processInvoiceResponse></env:Body></env:Envelope>

  'runnew' target description:
  - This will exercise the target webservice *via* the SOAPProxy, first going through a Http Gateway Listener.
  - Nothing interesting here; it is just like the webservice_proxy_basic quickstart.
  - The request url is: http://localhost:8080/Quickstart_webservice_proxy_versioning/http/Proxy_Versioning/Proxy-NewVersion
  - Running it will create output like this in the server console:

01:06:52,304 INFO  [STDOUT] Message before SOAPProxy: 
01:06:52,304 INFO  [STDOUT] [<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:inv="http://webservice_proxy_versioning/invoicing"><soapenv:Header/><soapenv:Body><inv:processInvoice><invoiceNumber>0123456789</invoiceNumber><processDate>2005-12-13T14:13:28.443+01:00</processDate></inv:processInvoice></soapenv:Body></soapenv:Envelope>].
01:06:52,309 INFO  [InvoicingWS] processInvoice called with invoiceNumber [0123456789] and processDate [Tue Dec 13 08:13:28 GMT-05:00 2005]
01:06:52,311 INFO  [STDOUT] Message after SOAPProxy: 
01:06:52,311 INFO  [STDOUT] [<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:processInvoiceResponse xmlns:ns2="http://webservice_proxy_versioning/invoicing"><success>true</success></ns2:processInvoiceResponse></env:Body></env:Envelope>].

  - And this in the client console:

     [java] ****  REQUEST  URL: http://localhost:8080/Quickstart_webservice_proxy_versioning/http/Proxy_Versioning/Proxy-NewVersion
     [java] ****  REQUEST BODY: <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:inv="http://webservice_proxy_versioning/invoicing"><soapenv:Header/><soapenv:Body><inv:processInvoice><invoiceNumber>0123456789</invoiceNumber><processDate>2005-12-13T14:13:28.443+01:00</processDate></inv:processInvoice></soapenv:Body></soapenv:Envelope>
     [java] 01:06:52,210 DEBUG [main][header] >> "POST / HTTP/1.1[\r][\n]"
     [java] 01:06:52,248 DEBUG [main][header] >> "Content-Type: text/xml;charset=UTF-8[\r][\n]"
     [java] 01:06:52,249 DEBUG [main][header] >> "SOAPAction: ""[\r][\n]"
     [java] 01:06:52,249 DEBUG [main][header] >> "User-Agent: Jakarta Commons-HttpClient/3.0.1[\r][\n]"
     [java] 01:06:52,250 DEBUG [main][header] >> "Host: localhost:9999[\r][\n]"
     [java] 01:06:52,250 DEBUG [main][header] >> "Content-Length: 338[\r][\n]"
     [java] 01:06:52,250 DEBUG [main][header] >> "[\r][\n]"
     [java] 01:06:52,251 DEBUG [main][content] >> "<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:inv="http://webservice_proxy_versioning/invoicing"><soapenv:Header/><soapenv:Body><inv:processInvoice><invoiceNumber>0123456789</invoiceNumber><processDate>2005-12-13T14:13:28.443+01:00</processDate></inv:processInvoice></soapenv:Body></soapenv:Envelope>"
     [java] 01:06:52,312 DEBUG [main][header] << "HTTP/1.1 200 OK[\r][\n]"
     [java] 01:06:52,314 DEBUG [main][header] << "Server: Apache-Coyote/1.1[\r][\n]"
     [java] 01:06:52,314 DEBUG [main][header] << "X-Powered-By: Servlet 2.4; JBoss-4.2.3.GA (build: SVNTag=JBoss_4_2_3_GA date=200807181417)/JBossWeb-2.0[\r][\n]"
     [java] 01:06:52,314 DEBUG [main][header] << "Date: Mon, 06 Jul 2009 06:06:52 GMT[\r][\n]"
     [java] 01:06:52,314 DEBUG [main][header] << "Content-Type: text/xml;charset=UTF-8[\r][\n]"
     [java] 01:06:52,314 DEBUG [main][header] << "Content-Length: 268[\r][\n]"
     [java] **** RESPONSE CODE: 200
     [java] 01:06:52,317 DEBUG [main][content] << "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:processInvoiceResponse xmlns:ns2="http://webservice_proxy_versioning/invoicing"><success>true</success></ns2:processInvoiceResponse></env:Body></e"
     [java] 01:06:52,318 DEBUG [main][content] << "nv:Envelope>"
     [java] **** RESPONSE BODY: <env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:processInvoiceResponse xmlns:ns2="http://webservice_proxy_versioning/invoicing"><success>true</success></ns2:processInvoiceResponse></env:Body></env:Envelope>

Project file descriptions:
==========================
	jboss-esb.xml:
	*   the "Proxy_Versioning/Proxy-OldVersion" service's purpose is to
	    allow old webservice clients to continue using that old endpoint
		without change.  It uses an XsltAction to apply a transform on the
		soap request (see Proxy_Versioning_RequestTransform.xsl below), and
		also specifies:
			- a wsdl to the new endpoint
			- a wsdlTransform to transform the wsdl to what old clients are
			  expecting (see Proxy_Versioning_WsdlTransform.xml below).
	*   the "Proxy_Versioning/Proxy-NewVersion" service's purpose is to
	    simply allow new webservice clients to start using the new endpoint,
		however still benefiting from the proxy abstracting the real endpoint
		from them.

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

	Proxy_Versioning_RequestTransform.xsl:
	Tranforms the incoming SOAPRequest to the old service endpoint to it can be forwarded
	along to the new service endpoint.
	It uses Smooks to add the newly added <processDate> element.

	Proxy_Versioning_WsdlTransform.xml:
	Transforms the new service endpoing WSDL so it can be used in the old service endpoint.
	It uses Smooks to remove the newly added <processDate> element.
	http://localhost:8080/Quickstart_webservice_proxy_versioning/http/Proxy_Versioning/Proxy-OldVersion?wsdl

	build.xml:
	Targets and structure description:

	*	the runws task calls the
		org.jboss.soa.esb.samples.quickstart.webservice_proxy_versioning.test.SendWSMessage class and
		executes the real JBossWS webservice directly.

	*	the runold (also runtest) task calls the
		org.jboss.soa.esb.samples.quickstart.webservice_proxy_versioning.test.SendWSMessage class and
		executes the "Proxy_Versioning/Proxy-OldVersion" service (see jboss-esb.xml description above).

	*	the runnew task calls the
		org.jboss.soa.esb.samples.quickstart.webservice_proxy_versioning.test.SendWSMessage class and
		executes the "Proxy_Versioning/Proxy-NewVersion" service (see jboss-esb.xml description above).

