import org.jboss.soa.esb.message.*

println "** Begin Receive Order - Service 1 **"

println "In: " + message.getBody().get()

message.getBody().add(message.getBody().get() + " 'Receive Order' ")

println "Out: " + message.getBody().get()

println "** End Receive Order - Service 1 **"

