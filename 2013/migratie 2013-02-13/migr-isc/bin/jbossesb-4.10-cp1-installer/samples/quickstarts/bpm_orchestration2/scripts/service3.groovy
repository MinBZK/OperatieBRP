import org.jboss.soa.esb.message.*

println "** Begin Credit Check - Service 3 **"

println "In: " + message.getBody().get()

message.getBody().add(message.getBody().get() + " 'Credit Check' ")

println "Out: " + message.getBody().get()

println "** End Credit Check - Service 3 **"