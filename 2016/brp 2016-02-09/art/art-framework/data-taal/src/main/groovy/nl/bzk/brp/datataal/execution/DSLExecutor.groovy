package nl.bzk.brp.datataal.execution

import org.codehaus.groovy.control.CompilerConfiguration
import org.springframework.core.io.Resource

/**
 * DSLExecutor is verantwoordelijk voor het uitvoeren van een Groovy script.
 * Het zorgt voor een {@link GroovyShell} en de configuratie ervan, om een
 * PersoonDSL script uit te voeren.
 *
 * @see http://www.eclecticlogic.com/2014/09/26/groovy-dsl-executor/
 * @see http://groovy.dzone.com/news/groovy-dsl-scratch-2-hours
 */
abstract class DSLExecutor {
    final Binding binding
    final CompilerConfiguration compilerConfiguration

    DSLExecutor(CompilerConfiguration config, Binding bind) {
        this.binding = bind
        this.compilerConfiguration = config
    }

    /**
     * Execute some DSL script, from a resource.
     *
     * @param resource Groovy file to execute.
     * @param handler Handler that provides implementations for methods and properties the script references.
     * @return A map of variables returned. The keys are the names of the variables.
     */
    Map<String, Object> execute(Resource resource, Object handler = null) {
        String scriptSource = resource.inputStream.getText("UTF-8")

        return doExecute(scriptSource, handler)
    }

    /**
     * Execute some DSL script.
     *
     * @param source DSL snippet
     * @param handler Handler that provides implementations for methods and properties the script references.
     * @return A map of variables returned. The keys are the names of the variables.
     */
    Map<String, Object> execute(String source, Object handler = null) {
        return doExecute(source, handler)
    }

    /**
     * Internal execution.
     *
     * @param source DSL snippet
     * @param handler Handler that provides implementations for methods and properties the script references.
     * @return A map of variables returned. The keys are the names of the variables.
     */
    private Map<String, Object> doExecute(String source, Object handler) {

        Binding binding = this.binding
        CompilerConfiguration configuration = this.compilerConfiguration

        // Instantieer de scriptRunner vóór het parsen van het script, om de kans op timeouts te verkleinen.
        ScriptRunner scriptRunner = new ScriptRunner()

        Script script = new GroovyShell(binding, configuration).parse(source, 'DSL')

        if (handler) {
            // We use the metaclass method and property resolution to route calls to the handler.
            // Route missing methods to the handler.
            script.metaClass.methodMissing = { String name, args ->
                MetaMethod method = handler.metaClass.getMetaMethod(name, args)
                if (method) {
                    method.invoke(handler, args)
                } else {
                    throw new MissingMethodException("No method $name found", handler.class, args)
                }
            }

            // route missing properties to the handler
            script.metaClass.propertyMissing = { String name, value ->
                MetaProperty property = handler.metaClass.getMetaProperty(name)
                if (property) {
                    return property.getProperty(handler)
                } else {
                    throw new MissingPropertyException("No property $name found", handler.class)
                }
            }
        }

        scriptRunner.runScript(script)
        return binding.getVariables()
    }
}
