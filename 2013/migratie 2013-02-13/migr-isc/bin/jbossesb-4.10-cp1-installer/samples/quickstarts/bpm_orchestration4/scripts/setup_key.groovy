import org.jboss.soa.esb.message.*

println "*********** BEGIN ORDER KEY SETUP ***********"

def messageText = message.getBody().get();
def order = new XmlParser().parseText(messageText);
def key = order['@orderId']
println "Key: " + key
message.getBody().add("businessKey",key);

println "************ END ORDER KEY SETUP ************"