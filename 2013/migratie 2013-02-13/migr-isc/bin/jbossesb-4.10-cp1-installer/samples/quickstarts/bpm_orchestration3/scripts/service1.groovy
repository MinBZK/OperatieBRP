import org.jboss.soa.esb.message.*
import org.jboss.soa.esb.addressing.*
import org.jboss.internal.soa.esb.addressing.helpers.EPRHelper
import java.io.*

println "** Begin Service 1 **"

println "In: " + message.getBody().get()
xmlEPR = EPRHelper.toXMLString(message.getHeader().getCall().getReplyTo())

// println "replyTo EPR: \n" + xmlEPR

def writer=new File("replyToEPR.xml").newPrintWriter()
writer.print(xmlEPR)
writer.close()


message.getBody().add(message.getBody().get() + " 'Service 1' ")

println "Out: " + message.getBody().get()

println "** End Service 1 **"

