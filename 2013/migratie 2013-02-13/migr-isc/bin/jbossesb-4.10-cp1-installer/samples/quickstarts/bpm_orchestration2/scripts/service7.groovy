import org.jboss.soa.esb.message.*

println "** Begin Atlanta - Service 7 **"

println "In: " + message.getBody().get()

message.getBody().add(message.getBody().get() + " 'Atlanta' ")

println "Out: " + message.getBody().get()

println "** End Atlanta - Service 7 **"