package nl.bzk.brp.testrunner.component.services.dsl

import groovy.transform.TimedInterrupt
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer
import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.codehaus.groovy.control.customizers.SecureASTCustomizer

/**
 * Specifieke implementatie van {@link nl.bzk.brp.datataal.execution.DSLExecutor} die de GroovyShell
 * de configuratie geeft voor het uitvoeren van een PersoonDSL script.
 */
class PersoonDSLExecutor extends DSLExecutor {

    /**
     * Constructor.
     */
    PersoonDSLExecutor() {
        super(compilerConfiguration(), binding())
    }

    private static Binding binding() {
        return new Binding()
    }

    /**
     * Compiler configuratie voor het compileren van de DSL.
     *
     * @return configuratie
     */
    private static CompilerConfiguration compilerConfiguration() {
        def imports = new ImportCustomizer()
        imports.with {
            addStarImports('nl.bzk.brp.datataal.model')
            addStaticStars('nl.bzk.brp.datataal.model.Persoon')
        }

        def secure = new SecureASTCustomizer()
        secure.with {
            closuresAllowed = true
            methodDefinitionAllowed = true
        }

        def interrupt = new ASTTransformationCustomizer([value: 60L], TimedInterrupt)
        def configProperties = new Properties()
        configProperties.put("groovy.source.encoding", "UTF-8")
        configProperties.put("file.encoding", "UTF-8")
        def configuration = new CompilerConfiguration(configProperties)
        configuration.addCompilationCustomizers(interrupt, imports, secure)

        return configuration
    }
}
