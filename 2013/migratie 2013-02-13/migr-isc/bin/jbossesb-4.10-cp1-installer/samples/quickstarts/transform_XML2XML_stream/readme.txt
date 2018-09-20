Overview:
=========
  This is a very basic example of how to split an XML message into fragments and
  route each of the fragments as XML (of a different format) to an ESB Service.

  This quickstart uses Smooks to split the message.  But input message is XML,
  but it could just as easily be EDI, CSV etc.  Likewise, the output format
  could be of a different format by simply adjusting the FreeMarker template.

  For more details on splitting messages with Smooks, see the SmooksUserGuide.pdf
  in the ESB documentation.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

  A comprehensive description of message transformation can be found in
  the "Message Transformation" chapter in the ServicesGuide, located in the docs folder.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.
	
