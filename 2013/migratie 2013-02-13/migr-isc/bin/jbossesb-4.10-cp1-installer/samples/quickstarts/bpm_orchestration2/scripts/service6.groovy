import org.jboss.soa.esb.message.*

println "** Begin Dallas - Service 6 **"

println "In: " + message.getBody().get()

message.getBody().add(message.getBody().get() + " 'Dallas' ")

println "Out: " + message.getBody().get()

println "** End Dallas - Service 6 **"