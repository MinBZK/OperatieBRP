/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.component.DatabaseDocker;
import nl.bzk.brp.dockertest.component.DockerNaam;
import nl.bzk.brp.test.common.TestclientExceptie;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;

/**
 * JBehave steps voor het uitvoeren van database updates.
 */
public class DatabaseSteps extends Steps {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * ==== Database prepareren met sql statement
     * Geeft de mogelijkheid om de database aan te passen. Dat kan in dit geval
     * door middel van een opgegeven SQL statement. Aangezien het een _Given_ step is, wordt
     * dit gebruikt als equivalent van een _PreQuery_, voor/aan het begin van het uitvoeren
     * van een scenario. +
     * NB: Met het resultaat van een select statement wordt niets gedaan.
     * @param statement het SQL statement dat wordt uitgevoerd
     */
    @Given("de database is aangepast met: $statement")
    public void preQueryStatement(String statement) {
        LOGGER.info("Uitvoeren query: {}", statement);
        JBehaveState.get().brpDatabase().template().readwrite(jdbcTemplate -> jdbcTemplate.execute(statement));
    }

    /**
     * Selectiedatabase aanpassen met een SQL statement.
     * @param statement het SQL statement dat wordt uitgevoerd
     */
    @Given("de selectiedatabase is aangepast met: $statement")
    public void selectieDatabaseAanpassen(String statement) {
        LOGGER.info("Uitvoeren query: {}", statement);
        JBehaveState.get().selectieDatabase().template().readwrite(jdbcTemplate -> jdbcTemplate.execute(statement));
    }


    @Given("een databasedump uit bestand $bestandsnaam in database $database")
    public void laadDatabasedump(String bestandsnaam, String database) {
        LOGGER.info("Laad databasedump uit bestand {} in {} database", bestandsnaam, database);
        DatabaseDocker databaseDocker = bepaalDatabase(database);
        databaseDocker.laadDump(database, bestandsnaam, databaseDocker.getLogischeNaam());
    }

    /**
     * Test de waarde van bepaalde veldnamen van de tabeltuple.
     * @param database de database waarop de query uitgevoerd dient te worden
     * @param query de query die wordt uitgevoerd as is
     * @param gegevensRegels de tabel met veldnamen en waardes
     */
    @Then("in $database heeft $query de volgende gegevens: $gegevensRegels")
    public void executeDatabaseQueryAndCheckTheResult(String database, String query, List<GegevensRegels> gegevensRegels) {

        final DatabaseDocker databaseDocker = bepaalDatabase(database);
        databaseDocker.template().readonly(jdbcTemplate -> {
            final List<Map<String, Object>> list = jdbcTemplate.queryForList(query);
            if (list.isEmpty()) {
                throw new TestclientExceptie("geen database row teruggekregen");
            }
            final Map<String, Object> stringObjectMap = list.get(0);
            GegevensRegels.check(gegevensRegels, stringObjectMap);
        });
    }

    /**
     *
     * @param query
     * @param database
     */
    @Then("print $query voor $database")
    public void printQuery(String query, String database) {

        final DatabaseDocker databaseDocker = bepaalDatabase(database);
        databaseDocker.template().readonly(jdbcTemplate -> {
            final List<Map<String, Object>> list = jdbcTemplate.queryForList(query);
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw);
            list.forEach(stringObjectMap -> pw.println(stringObjectMap.toString()));
            LOGGER.info(String.format("Op database %s geeft de query %s de volgende output %n %s", database, query, sw.toString()));
        });
    }


    private DatabaseDocker bepaalDatabase(final String database) {
        final BrpOmgeving brpOmgeving = JBehaveState.get();
        DatabaseDocker databaseDocker;
        switch (database) {
            case "ber":
                databaseDocker = brpOmgeving.geefDocker(DockerNaam.ARCHIVERINGDB);
                break;
            case "prot":
                databaseDocker = brpOmgeving.geefDocker(DockerNaam.PROTOCOLLERINGDB);
                break;
            case "afnemervb":
                databaseDocker = brpOmgeving.geefDocker(DockerNaam.AFNEMERVOORBEELDDB);
                break;
            case "selectie":
                databaseDocker = brpOmgeving.geefDocker(DockerNaam.SELECTIEBLOB_DATABASE);
                break;
            default:
                databaseDocker = brpOmgeving.geefDocker(DockerNaam.BRPDB);
        }
        return databaseDocker;
    }

}
