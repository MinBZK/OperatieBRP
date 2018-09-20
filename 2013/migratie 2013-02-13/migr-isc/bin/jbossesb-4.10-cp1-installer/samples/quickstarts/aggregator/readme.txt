Overview:
=========
  The purpose of this particular example is to demonstrate the Enterprise
  Integration Patterns of Splitter and Aggregator. In addition, this example
  demonstrates the concepts of multiple JVMs, each running unique services but
  all working in concert, a federated model that shares a common registry and
  uses JMS between the JVMs.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run standalone mode:
=======================
  1. In a command terminal window in the quickstart folder type
     'ant deploy-jms-dests'.
  2. In a command terminal window in this folder ("Window1"), type 'ant run'.
     This executes the splitter.
  3. In a command terminal window in this folder ("Window2"), type
     'ant runRedService'.  This rips out the Customer.
  4. In a command terminal window in this folder ("Window3"), type
     'ant runGreenService'.  This rips out the LineItems.
  5. In a command terminal window in this folder ("Window4"), type
     'ant runBlueService'.  This rips out the OrderHeader.
  6. In a command terminal window in this folder ("Window5"), type
     'ant runAggregatorService'.  Displays individual parts together.
  7. In a command terminal window in this folder ("Window6"), type
     'ant receiveRed'.
  8. In a command terminal window in this folder ("Window7"), type
     'ant receiveGreen'.
  9. In a command terminal window in this folder ("Window8"), type
     'ant receiveBlue'.
  10. Open another command terminal window in this folder ("Window9"), type
     'ant runtest'.
  11. Switch back to the previous windows to see the output.
  12. When finished, interrupt the ESB, services and receivers using Ctrl-C.
  13. Undeploy the JMS configuration, type 'ant undeploy-jms-dests'.

What to look at in this Quickstart:
===================================
  The aggregation action (SystemPrintln in this case) needs to contain logic
  for handling the "parts". The trick is to use ActionUtils.getTaskObject() and
  loop through the attachments:

  public Message process(Message message) throws ActionProcessingException {
    Object messageObject = ActionUtils.getTaskObject(message);

    if(messageObject instanceof byte[]) {
      System.out.println(printlnMessage + ": \n[" +
        format(new String((byte[])messageObject)) + "].");
    } else {
      if (messageObject!=null)
        System.out.println(printlnMessage + ": \n[" +
          format(messageObject.toString()) + "].");
      for (int i=0; i<message.getAttachment().getUnnamedCount(); i++) {
        Message attachedMessage = (Message) message.getAttachment().itemAt(i);
        System.out.println("attachment " + i + ": [" +
          new String(attachedMessage.getBody().getContents()) + "].");
      }
    }
    return message;
  }
