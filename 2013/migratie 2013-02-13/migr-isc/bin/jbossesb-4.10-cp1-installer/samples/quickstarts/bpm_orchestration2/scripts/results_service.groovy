import org.jboss.soa.esb.message.*

println "***** Ship It *****"

println "LA_Result: " + message.getBody().get("LA_Result")
println "Dallas_Result: " + message.getBody().get("Dallas_Result")
println "Atlanta_Result: " + message.getBody().get("Atlanta_Result")

message.getBody().add(message.getBody().get("LA_Result") + " 'Shipped' ")

println "***** End Ship It *****"