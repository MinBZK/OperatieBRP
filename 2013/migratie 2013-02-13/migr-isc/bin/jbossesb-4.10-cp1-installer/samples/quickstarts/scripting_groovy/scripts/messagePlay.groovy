import org.jboss.soa.esb.message.*


message.body.add("ScriptingBy", "Groovy - the best scripting language on the planet")

println message.getBody().get()

