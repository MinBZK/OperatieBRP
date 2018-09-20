import org.jboss.soa.esb.message.*

println " == BEGIN =="
println "orderHeader: " + message.body.get("orderHeader")
println "customer: " + message.body.get("customer")
println " ==  END  =="