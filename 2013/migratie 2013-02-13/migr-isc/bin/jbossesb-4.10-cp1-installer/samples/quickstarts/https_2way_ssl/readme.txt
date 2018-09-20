Overview:
=========
  This quickstart demonstrates how to:
  1.  Configure a HTTPS Endpoint.
  2.  Configure the HttpRouter for performing HTTPS invocations on ESB Unaware
      Endpoints.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.

What to look at in this Quickstart:
===================================
  On running the 'ant runtest' command (step #2 above), the Ant script executes
  the SendJMSMessage class, which sends a JMS message into the "RuntestClientService"
  Service gateway (busidref="runtestGateway").  This Service takes the message payload
  and, using the HttpRouter, makes a synchronous HTTPS invocation on the "MyHTTPSService"
  Service gateway (busidref="https_2way"), which simply replies with the HTTP data
  from the request.

  All security artifacts are generated and deployed by the build.xml script.
