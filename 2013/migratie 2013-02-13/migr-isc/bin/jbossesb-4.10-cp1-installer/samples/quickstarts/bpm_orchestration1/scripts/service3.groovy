import org.jboss.soa.esb.message.*

println "3********** Begin Service 3 ***********"

println "In: " + new String(message.getBody().get())

message.getBody().add("Service 3 " + new String(message.getBody().get()))

println "Out: " + message.getBody().get()
println "************ End Service 3 ************"