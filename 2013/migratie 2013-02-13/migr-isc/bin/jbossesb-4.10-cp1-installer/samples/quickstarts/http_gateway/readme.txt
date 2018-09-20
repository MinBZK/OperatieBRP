Overview:
=========
  The purpose of the http_gateway quickstart sample is to demonstarte how to 
  configue a http gateway to pass the http request into ESB service. 
  

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run '.esb' archive mode:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open the web brower and send http requests to the following addresses.  Be sure to
     check the Server console after executing each request:
       a) http://localhost:8080/Quickstart_http_gateway/http/sales/a/b/c - Will be routed to the Sales:List
          service.  Will return some details about the request.  This Service's <http-gateway> references a
          "secureFriends" bus, which contains login configurations.  The login username is "kermit" and the
          password is "thefrog".  If you are running this quickstart within SOA-P, you will need to 
	uncomment the line which specifies it in server/<config>/conf/props/jbossws-users.properties.   The request uses the "java:/jaas/JBossWS" security domain, which is already installed
          in your JBoss ESB/App Server.
       b) http://localhost:8080/Quickstart_http_gateway/http/index/XXXX/yyy?a=1,b=2 - Will be routed to the Index:List
          service.  Will return some details about the request.  This Service's <http-gateway> does not refer
          to any bus and so simply uses the "default" http bus.
       c) http://localhost:8080/Quickstart_http_gateway/http/Exceptions/Exception1 - Will be routed to the
          Exceptions:Exception1 service.  This service throws a "MyActionException", resulting in a
          502 (Bad Gateway) status being returned in accordance with the Exception to HTTP status code
          mappings defined globally on the <http-provider>.
       d) http://localhost:8080/Quickstart_http_gateway/http/Exceptions/Exception2 - Will be routed to the
          Exceptions:Exception2 service.  This service also throws a "MyActionException", but the <http-gateway>
          on this service overrides the globally defined Exception to HTTP status code mappings defined globally
          on the <http-provider> to return a 503 (Service Unavailable) status for the "MyActionException" exception.
       e) http://localhost:8080/Quickstart_http_gateway/http/Async/List - Will be routed to the
          Async:List service, which responds asynchronously with a 202 (Accepted) response code and a static
          payload of this readme.txt.
       f) http://localhost:8080/Quickstart_http_gateway/http/soap/ - Will be routed to the
          Soap:List service, which requires message level seurity.  An 401 error will occur.  Now
          switch to the command line and run "ant runtest".  This POSTs a SOAP message, with login credentials
          to the gateway.  The service should respond successfully this time!!
  3. In this folder ("Window1"), type 'ant undeploy'.
