/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.util.brpkern;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import nl.moderniseringgba.migratie.synchronisatie.util.DBUnitUtil;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.springframework.stereotype.Component;

/**
 * DBUnit
 */
@Component
public class DBUnitBrpUtil extends DBUnitUtil {

    private static final String SCHEMA = "kern";
    private static final String CONVERSIE_SCHEMA = "conversietabel";
    private static final String LOGGING_SCHEMA = "logging";

    private static final RestartBrpSequenceOperation RESTART_SEQUENCE_OPERATION = new RestartBrpSequenceOperation(1);
    private static final TruncateBrpTableOperation TRUNCATE_TABLE_OPERATION = new TruncateBrpTableOperation();

    // Statische Stamtabellen in schema kern, die in het BRP-gegevenswoordenboek zijn getypeerd als 'Stamgegevens -
    // Statisch'
    private static final String[] STATISCHE_STAMGEGEVENS = { "srtpers", "srtactie", "srtbetr", "srtdbobject",
            "adellijketitel", "dbobject", "element", "categoriesrtactie", "categoriesrtdoc", "functieadres",
            "geslachtsaand", "predikaat", "rdnopschorting", "rol", "srtelement", "srtindicatie",
            "srtmultirealiteitregel", "srtpartij", "srtrelatie", "srtverificatie", "verantwoordelijke",
            "wijzegebruikgeslnaam", };

    // Dynamische stamtabellen in schema kern, die in het BRP-gegevenswoordenboek zijn getypeerd als
    // 'Stamgegevens Dynamisch'
    // (volgorde is belangrijk vanwege constraints op de tabellen):
    private static final String[] DYNAMISCHE_STAMGEGEVENS = { "land", "nation", "plaats", "rdnbeeindrelatie",
            "rdnverknlnation", "rdnverliesnlnation", "rdnvervallenreisdoc", "rdnwijzadres", "aangadresh",
            "autvanafgiftereisdoc", "partij", "partijrol", "verblijfsr", "verdrag", "srtdoc", "srtnlreisdoc", };

    // Reguliere tabellen in schema kern, die in het BRP-gegevenswoordenboek zijn getypeerd als
    // 'Operationele gegevens - Dynamisch'
    // (volgorde is belangrijk vanwege constraints op de tabellen):
    private static final String[] OPERATIONELE_TABELLEN = { "his_multirealiteitregel", "multirealiteitregel",
            "his_doc", "his_persuitslnlkiesr", "his_persbijhgem", "his_persbijhverantwoordelijk", "his_relatie",
            "his_persvoornaam", "his_persverificatie", "his_persverblijfsr", "his_perssamengesteldenaam",
            "his_persreisdoc", "his_persadres", "his_perspk", "his_persoverlijden", "his_persopschorting",
            "his_persnation", "his_persinschr", "his_persindicatie", "his_persimmigratie", "his_persids",
            "his_persgeslnaamcomp", "his_persgeslachtsaand", "his_persgeboorte", "his_perseuverkiezingen",
            "his_persaanschr", "his_perssamengesteldenaam", "his_betrouderschap", "his_betrouderlijkgezag", "betr",
            "persvoornaam", "persgeslnaamcomp", "persindicatie", "persnation", "persreisdoc", "persverificatie",
            "persadres", "betr", "relatie", "pers", "bron", "actie", "doc", };

    private static final String[] CONVERSIE_TABELLEN = { "voorvoegsel", "adellijketitelpredikaat",
            "redenverkrijgingverliesnlschap", "aangifteadreshouding", "verblijfstitel", "soortnlreisdocument",
            "redeninhoudingvermissingreisdocument", "autoriteitvanafgifte", "redenopschorting",
            "redenontbindinghuwelijkpartnerschap", };

    private static final String[] LOGGING_TABELLEN = { "lo3Herkomst", "logregel", "berichtlog" };

    @Inject
    private DataSource dataSource;

    private List<String> statischeStamtabellen;

    private List<String> dynamischeStamtabellen;

    private List<String> operationeleTabellen;

    private List<String> conversieTabellen;

    private List<String> loggingTabellen;

    @PostConstruct
    public void init() throws SQLException, DatabaseUnitException {
        initTabellen();
    }

    @Override
    protected Connection createSqlConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Reset alle sequences behorend bij de operationele en log tabellen. De nieuwe beginwaarde van deze sequences wordt
     * op 1 gezet.
     * 
     * @throws DatabaseUnitException
     * @throws SQLException
     */
    @Override
    public void resetSequences() throws SQLException, DatabaseUnitException {
        RESTART_SEQUENCE_OPERATION.execute(createConnection(), operationeleTabellen);
        RESTART_SEQUENCE_OPERATION.execute(createConnection(), loggingTabellen);
    }

    /**
     * Truncate alle tabellen met uitzondering van de statische stamtabellen.
     * 
     * @throws DatabaseUnitException
     * @throws SQLException
     */
    @Override
    public void truncateTables() throws SQLException, DatabaseUnitException {
        TRUNCATE_TABLE_OPERATION.execute(createConnection(), loggingTabellen);
        TRUNCATE_TABLE_OPERATION.execute(createConnection(), operationeleTabellen);
        TRUNCATE_TABLE_OPERATION.execute(createConnection(), dynamischeStamtabellen);
        TRUNCATE_TABLE_OPERATION.execute(createConnection(), conversieTabellen);
    }

    private void initTabellen() throws DataSetException {
        operationeleTabellen = new ArrayList<String>();
        statischeStamtabellen = new ArrayList<String>();
        dynamischeStamtabellen = new ArrayList<String>();
        conversieTabellen = new ArrayList<String>();
        loggingTabellen = new ArrayList<String>();

        for (final String tableName : OPERATIONELE_TABELLEN) {
            final String qualifiedTableName = SCHEMA + '.' + tableName;
            operationeleTabellen.add(qualifiedTableName);
        }

        for (final String tableName : STATISCHE_STAMGEGEVENS) {
            final String qualifiedTableName = SCHEMA + '.' + tableName;
            statischeStamtabellen.add(qualifiedTableName);
        }

        for (final String tableName : DYNAMISCHE_STAMGEGEVENS) {
            final String qualifiedTableName = SCHEMA + '.' + tableName;
            dynamischeStamtabellen.add(qualifiedTableName);
        }

        for (final String tableName : CONVERSIE_TABELLEN) {
            final String qualifiedTableName = CONVERSIE_SCHEMA + '.' + tableName;
            conversieTabellen.add(qualifiedTableName);
        }

        for (final String tableName : LOGGING_TABELLEN) {
            final String qualifiedTableName = LOGGING_SCHEMA + '.' + tableName;
            loggingTabellen.add(qualifiedTableName);
        }

    }
}
