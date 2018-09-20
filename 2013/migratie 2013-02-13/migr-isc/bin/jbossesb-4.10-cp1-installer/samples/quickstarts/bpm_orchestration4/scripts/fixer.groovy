import org.jboss.soa.esb.message.*

println "*********** BEGIN FIXER ***********"

println "In: " + new String(message.body.contents)

message.body.add(new String(message.body.contents))

println "Out: " + new String(message.body.get())

println "************ END FIXER ************"