import org.jboss.soa.esb.message.*

println "** Begin Los Angeles - Service 5 **"

println "In: " + message.getBody().get()

message.getBody().add(message.getBody().get() + " 'Los Angeles' ")

println "Out: " + message.getBody().get()

println "** End Los Angeles - Service 5 **"