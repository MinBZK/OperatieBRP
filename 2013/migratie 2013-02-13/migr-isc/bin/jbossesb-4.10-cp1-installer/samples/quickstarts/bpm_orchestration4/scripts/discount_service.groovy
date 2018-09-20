import org.jboss.soa.esb.message.*

println " Begin Discount Service "

println "default location: " + message.body.get()
println "Order Header: " + message.body.get("orderHeader")
println "Customer: " + message.body.get("customer")

println " End Discount Service "