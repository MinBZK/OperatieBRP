Overview:
=========
  Demonstrates the use of the BusinessRulesProcessor which allows for
  modification of the POJOs attached to an ESB Message.  The example uses Rules
  to calculate the discount of an inbound order based on the customer's past 
  history of orders. As such it is an example of a stateful rule service.

  Make sure you have run simple_cbr, transformation_XML2POJO, fun_cbr, and
  business_rules_service quickstarts as their principles are used in this
  more complex example.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

  A comprehensive description of message transformation can be found in
  ServicesGuide.pdf, located in the docs/services folder.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.

What to look at in this Quickstart:
===================================
  Look at the jboss-esb.xml and notice how the stateful="true" is set on the 
  BusinessRulesProcessor action of the OrderDiscountService. Review the 
  OrderDiscountOnMultipleOrders.drl file to see how accumulation works. 
  
  Stateful rule services must be told via message properties when to continue with
  the current stateful session and when to dispose of it. This is accomplished via 
  the SetupMessage action based on the orderStatus provided with the inbound XML.

  Because there is no persistence in this example, the customer object created from
  each XML message is actually a different Java object (even though the values are 
  all the same). These are separately inserted into the stateful session, and the
  rules treat them as different objects. A real life implementation would detect
  that the customer already existed, and only insert one customer object into the 
  engine stateful session.


Expected Results :
===================================
What you should see in this quickstart is that when the previous order total
is greater than or equal to $100, any subsequent order will get a 10% discount.
Order2 will receive no discount because the previous total was 64.92, but
Order3 should receive a discount.

<cut>
15:17:07,539 INFO  [SetupMessage] Moved the transformed Order Header and Customer
15:17:07,541 INFO  [STDOUT] set discount of 0.0 on order 2 for customer user1
15:17:07,541 INFO  [STDOUT] set discount of 0.0 on order 1 for customer user1
15:17:07,544 INFO  [STDOUT] Customer user1 now has a shopping total of 129.84
15:17:07,545 INFO  [STDOUT] set discount of 0.0 on order 2 for customer user1
15:17:07,545 INFO  [STDOUT] Customer user1 now has a shopping total of 129.84
15:17:07,545 INFO  [STDOUT] { ================ After Order Discount
15:17:07,545 INFO  [STDOUT] Customer: user1,Harry,Fletcher,SD,0
15:17:07,545 INFO  [STDOUT] Order Priority: 1
15:17:07,545 INFO  [STDOUT] Order Discount: 0.0
15:17:07,545 INFO  [STDOUT] Order Total: 64.92
15:17:07,546 INFO  [STDOUT] } ================ After Order Discount
15:17:07,551 INFO  [STDOUT] Message before transformation: 
15:17:07,551 INFO  [STDOUT] [<Order orderId="3" orderDate="Wed Nov 15 13:45:28 EST 2006" statusCode="2" 
netAmount="59.97" totalAmount="64.92" tax="4.95">

	<Customer userName="user1" firstName="Harry" lastName="Fletcher" state="SD"/>
	<OrderLines>
		<OrderLine position="1" quantity="1">
			<Product productId="567" title="X-MEn" price="29.98"/>
		</OrderLine>
		<OrderLine position="2" quantity="1">
			<Product productId="499" title="X-Man 2" price="29.99"/>
		</OrderLine>
	</OrderLines>
</Order>
].
15:17:07,557 INFO  [SetupMessage] Moved the transformed Order Header and Customer
15:17:07,558 INFO  [STDOUT] set discount of 0.0 on order 3 for customer user1
15:17:07,558 INFO  [STDOUT] set discount of 0.0 on order 2 for customer user1
15:17:07,558 INFO  [STDOUT] set discount of 0.0 on order 1 for customer user1
15:17:07,558 INFO  [STDOUT] Customer user1 now has a shopping total of 194.76
15:17:07,558 INFO  [STDOUT] set discount of 10.0 on order 3 for customer user1
15:17:07,558 INFO  [STDOUT] set discount of 10.0 on order 3 for customer user1
15:17:07,559 INFO  [STDOUT] Customer user1 now has a shopping total of 194.76
15:17:07,559 INFO  [STDOUT] Customer user1 now has a shopping total of 194.76
15:17:07,559 INFO  [STDOUT] { ================ After Order Discount
15:17:07,559 INFO  [STDOUT] Customer: user1,Harry,Fletcher,SD,0
15:17:07,559 INFO  [STDOUT] Order Priority: 1
15:17:07,559 INFO  [STDOUT] Order Discount: 10.0
15:17:07,559 INFO  [STDOUT] Order Total: 64.92
15:17:07,559 INFO  [STDOUT] } ================ After Order Discount

