import org.jboss.soa.esb.message.*

println " Begin Shipping Service "

println "Original XML: " + message.body.get("entireOrderAsXML")
println "Customer: " + message.body.get("customer")
println "Order: " + message.body.get("orderHeader")

println " End Shipping Service "