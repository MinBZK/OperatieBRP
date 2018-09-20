import org.jboss.soa.esb.message.*

println "2********** Begin Service 2 ***********"

println "In: " + new String(message.getBody().get())

message.getBody().add("Service 2 " + new String(message.getBody().get()))

println "Out: " + message.getBody().get()

println "************ End Service 2 ************"


