import org.jboss.soa.esb.message.*

println "** Begin Inventory Check - Service 4 **"

println "In: " + message.getBody().get()

message.getBody().add(message.getBody().get() + " 'Inventory Check' ")

println "Out: " + message.getBody().get()

println "** End Inventory Check - Service 4 **"