Overview:
=========
  The purpose of the publish_as_webservice quickstart sample is to demonstrate how to 
  publish a esb service as a web service. 

  This quickstart also demonstrates how messages can be validate by JBossESB by configuring
  a service for validation and how schemas imports are used.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. In this folder ("Window1"), type 'ant undeploy'.

To Run from Soapui:
===========================
  1. Use the following URL: 'http://127.0.0.1:8080/Quickstart_publish_as_webservice/ebws/ESBServiceSample/HelloWorldPubService?wsdl'.
  2. Copy the contents of soap-userpass-message.xml.
  3. Exceute the web service call from soapui.

======================================
  1. runtest ant target
	 This target will call the SOAPTest client twice, first call will be with a valid SOAP message
	 and the second call will generate a SOAPFault to be retured. 

What to look for in this quickstart
===================================
  1. Message validation
	 Validation of request and response messages can be enabled by setting the 'validate' attribute to true.
	 For example:
	 <actions  inXsd="/request.xsd" outXsd="/response.xsd" faultXsd="/fault.xsd" validate="true">
		...
	 </actions>

  2. Schema imports
	 Schema import elements are used to add multiple schemas with different target namespaces to a document.
	 All schemas are packaged in a lib/schemas.jar.

	 a) Take a look the import element in request.xsd:
	   <xs:import namespace="http://www.jboss.org/custom-request" schemaLocation="custom-request-type.xsd"/>
	   This custom-request-type.xsd should in this case be packaged in the root of the schemas.jar

	 b) Another example can be found in response.xsd:
	   <xs:import namespace="http://www.jboss.org/custom-response" schemaLocation="imports/custom-response-type.xsd"/>
	   In this case the xsd has is located in a directory named 'imports' in the schemas.jar

