import org.jboss.soa.esb.message.*
import org.jboss.soa.esb.store.OrderHeader

println "*********** BEGIN ORDER PRIORITY FIXER ***********"

println "Order: " + message.body.get("orderHeader")
order = (OrderHeader) message.body.get("orderHeader")

message.body.add("order_orderPriority",order.getOrderPriority())


println "************ END ORDER PRIORITY FIXER ************"