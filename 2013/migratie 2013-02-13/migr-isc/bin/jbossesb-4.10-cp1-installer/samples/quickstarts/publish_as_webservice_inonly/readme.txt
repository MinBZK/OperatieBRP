Overview:
=========
  The purpose of the publish_as_webservice_inonly quickstart sample is to demonstrate how to
  publish an OneWay (In-Only) esb service as a web service.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Open another command terminal window in this folder ("Window2"), type
     'ant sendesb'.
  4. In this folder ("Window1"), type 'ant undeploy'.

To Run from Soapui:
===========================
  1. Use the following URL: 'http://127.0.0.1:8080/Quickstart_publish_as_webservice_inonly/ebws/ESBServiceSample/HelloWorldPubServiceInOnly?wsdl'.
  2. Copy the contents of soap-userpass-message.xml.
  3. Exceute the web service call from soapui.

======================================
  1. runtest ant target
	 This target will call the SOAPTest client twice, first call will be with a valid SOAP message
	 and the second call will generate a fault.

  2. sendesb ant target
	 This target will call the SendEsbMessage twice. The first call will be with a valid message body content
	 and the second will generate a FaultMessageException.
