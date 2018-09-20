import org.jboss.soa.esb.message.*
import org.jboss.soa.esb.store.OrderHeader

println "*********** BEGIN ORDER DISCOUNT FIXER ***********"

println "Order: " + message.body.get("orderHeader")
order = (OrderHeader) message.body.get("orderHeader")

message.body.add("order_orderDiscount",order.getOrderDiscount())


println "************ END ORDER DISCOUNT FIXER ************"