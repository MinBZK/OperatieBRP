import org.jboss.soa.esb.message.*
import org.jboss.soa.esb.addressing.*
import org.jboss.internal.soa.esb.addressing.helpers.EPRHelper
import org.jboss.soa.esb.client.ServiceInvoker
import org.jboss.soa.esb.addressing.eprs.LogicalEPR

println "** Begin Setup To **"

// the XML representation of the EPR is in the body of the file
// file contents show up as a byte array, hence the need for new String(message.getBody().get())
epr = EPRHelper.fromXMLString(new String(message.getBody().get()))
//  need to set the To on the message object
lepr = new LogicalEPR(epr)

println "To/replyTo EPR: \n" + epr

message.getHeader().getCall().setTo(epr);
message.getBody().add("") // clear the body content

// now route this updated message to the callbackservice
si = lepr.getServiceInvoker()

si.deliverAsync(message)

println "** End Setup To **"

