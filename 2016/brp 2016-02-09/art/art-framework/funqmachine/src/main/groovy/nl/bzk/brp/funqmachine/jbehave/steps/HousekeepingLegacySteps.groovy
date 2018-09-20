package nl.bzk.brp.funqmachine.jbehave.steps

import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioRunContext
import nl.bzk.brp.funqmachine.processors.SqlProcessor
import nl.bzk.brp.funqmachine.processors.TemplateProcessor
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

/**
 * Pre en post processing die onafhankelijk van een scenario gedaan kan worden.
 * Alleen ten behoeve van legacy ARTs.
 */
@Steps
class HousekeepingLegacySteps {
    static final Logger logger = LoggerFactory.getLogger(HousekeepingLegacySteps)

    @Autowired
    private ScenarioRunContext runContext

    /**
     * ==== Database prepareren met sql-bestand
     * Geeft de mogelijkheid om de *KERN* database aan te passen. Dat kan in dit geval
     * door middel van de SQL statements in het opgegeven bestand. Aangezien het een _Given_
     * step is, wordt dit gebruikt als equivalent van een _PreQuery_, voor/aan het begin van
     * het uitvoeren van een scenario. Het bestand wordt behandeld als een template, dus
     * variabelen en Freemarker methods & directives kunnen gebruikt worden. +
     * NB: Met het resultaat van een select statement wordt niets gedaan.
     *
     * @param file het bestand met SQL statements dat wordt uitgevoerd
     */
    @Given("de database is aangepast dmv \$file")
    @Deprecated
    void preQueryFile(File file) {
        logger.debug "Uitvoeren (pre)query uit $file"
        executeSqlKern(file.text)
        runContext.setCacheHeeftRefreshNodig(true)
    }

    /**
     * ==== Database opruimen met sql-bestand
     * Geeft de mogelijkheid om de *KERN* database aan te passen. Dat kan in dit geval
     * door middel van de SQL statements in het opgegeven bestand. Aangezien het een _Then_
     * step is, wordt dit gebruikt als equivalent van een _PostQuery_, na/aan het einde van
     * het uitvoeren van een scenario. Het bestand wordt behandeld als een template, dus
     * variabelen en Freemarker methods & directives kunnen gebruikt worden. +
     * NB: Met het resultaat van een select statement wordt niets gedaan.
     *
     * @param file het SQL statement dat wordt uitgevoerd
     */
    @Then("de database wordt opgeruimd dmv \$file")
    @Deprecated
    void postQueryFile(File file) {
        logger.debug "Uitvoeren (post)query uit $file"
        executeSqlKern(file.text)
        runContext.setCacheHeeftRefreshNodig(true)
    }

    /**
     * ==== Database prepareren met sql statement
     * Geeft de mogelijkheid om de *KERN* database aan te passen. Dat kan in dit geval
     * door middel van een opgegeven SQL statement. Aangezien het een _Given_ step is, wordt
     * dit gebruikt als equivalent van een _PreQuery_, voor/aan het begin van het uitvoeren
     * van een scenario. +
     * NB: Met het resultaat van een select statement wordt niets gedaan.
     *
     * @param statement het SQL statement dat wordt uitgevoerd
     */
    @Given("de database is aangepast met: \$statement")
    @Deprecated
    void preQueryStatement(String statement) {
        logger.info "Uitvoeren query: {}", statement
        executeSqlKern(statement)
        runContext.setCacheHeeftRefreshNodig(true)
    }

    /**
     * ==== Database opruimen met sql statement
     * Geeft de mogelijkheid om de *KERN* database aan te passen. Dat kan in dit geval
     * door middel van een opgegeven SQL statement. Aangezien het een Then step is, wordt
     * dit gebruikt als equivalent van een _PostQuery_, aan het einde van het uitvoeren
     * van een scenario. +
     * NB: Met het resultaat van een select statement wordt niets gedaan.
     *
     * @param statement het SQL statement dat wordt uitgevoerd
     */
    @Then("de database wordt opgeruimd met: \$statement")
    @Deprecated
    void postQueryStatement(String statement) {
        logger.debug "Uitvoeren (post)query: {}", statement
        executeSqlKern(statement)
        runContext.setCacheHeeftRefreshNodig(true)
    }

    private void executeSqlKern(String sqlStatement) {
        executeSql(sqlStatement, Database.KERN)
    }

    private void executeSql(String sql, Database database) {
        def templateProcessor = new TemplateProcessor()
        def sqlStatements = templateProcessor.verwerkTemplateString(sql, runContext.data)

        new SqlProcessor(database).voerUit(sqlStatements)
    }

}
