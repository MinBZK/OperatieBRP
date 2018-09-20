import org.jboss.soa.esb.message.*

println "1********** Begin Service 1 ***********"

println "In: " + new String(message.getBody().get())

message.getBody().add("Service 1 " + new String(message.getBody().get()))

println "Out: " + message.getBody().get()

println "************ End Service 1 ************"