import org.jboss.soa.esb.message.*

def messageText = message.getBody().get();
def order = new XmlParser().parseText(messageText);

if(order.OrderLines.size() == 1) {
    message.getProperties().setProperty(Properties.MESSAGE_PROFILE, "from:dvdstore");
} else if(order.LineItems.size() == 1) {
    message.getProperties().setProperty(Properties.MESSAGE_PROFILE, "from:petstore");
}

println "Message from: " + message.getProperties().getProperty("from");
