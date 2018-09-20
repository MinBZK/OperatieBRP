import org.jboss.soa.esb.message.*

println "** Begin Validate Order - Service 2 **"

println "In: " + message.getBody().get()

message.getBody().add(message.getBody().get() + " 'Validate Order' ")

println "Out: " + message.getBody().get()

println "** End Validate Order - Service 2 **"


