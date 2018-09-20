package nl.bzk.brp.funqmachine.processors

import groovy.sql.Sql
import groovy.sql.GroovyRowResult
import java.sql.SQLException
import java.text.SimpleDateFormat
import net.sf.jsqlparser.JSQLParserException
import net.sf.jsqlparser.parser.CCJSqlParserUtil
import net.sf.jsqlparser.statement.Statement
import net.sf.jsqlparser.statement.Statements
import net.sf.jsqlparser.statement.select.Select
import nl.bzk.brp.funqmachine.configuratie.Database
import nl.bzk.brp.funqmachine.configuratie.DatabaseConfig
import nl.bzk.brp.funqmachine.configuratie.Environment
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SqlProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlProcessor.class)
    public static final String REFERENTIENR = "referentienr"
    public static final String CROSSREFERENTIENR = "crossreferentienr"

    public static final int QUERY_TIMEOUT = 30
    public static final int QUERY_TIMEOUT_VERWIJDEREN_ALLES = 300

    Sql sql

    SqlProcessor(final Database database) {
        final DatabaseConfig databaseConfig = Environment.instance().getGetDatabaseConfig(database)

        def props = [user: databaseConfig.username, password: databaseConfig.password, allowMultiQueries: 'true'] as Properties

        sql = Sql.newInstance(databaseConfig.url, props, databaseConfig.driverClassName)
        sql.withStatement {
            stmt -> stmt.queryTimeout = QUERY_TIMEOUT
        }
    }

    /**
     * Reset test personen in de database.
     * @param bsns_anrs
     */
    void resetPersonen(final List<String> bsns) {
        try {
            // PrepareData
            bsns.each {
                def bsn = it as Long
                def sqlRows = sql.rows("select sql from test.referentie where bsn = ${bsn}")

                def sqlStatements = []
                sqlStatements << geefVerwijderPersoonQueries(bsn)
                if (sqlRows.size()) {
                    sqlRows.each {row ->
                        sqlStatements << row.sql
                    }
                } else {
                    sqlStatements << """
                DELETE FROM kern.relatie r
                USING kern.betr b, kern.pers p
                WHERE r.id = b.relatie
                AND b.pers = p.id
                AND (p.bsn IN (${bsn}) OR p.anr IN (${bsn}));
                DELETE FROM kern.pers p WHERE (p.bsn IN (${bsn}));"""
                }

                sql.execute sqlStatements.join('\n')
            }
        } catch (Exception e) {
            LOGGER.error "kan personen $bsns niet resetten", e
            throw e
        } finally {
            sql?.close()
        }
    }

    private static String geefVerwijderPersoonQueries(final Long bsn) {
        """
        DELETE FROM kern.his_onderzoek WHERE onderzoek IN (SELECT onderzoek FROM kern.personderzoek WHERE pers IN (SELECT id from kern.pers WHERE bsn =
$bsn));
        DELETE FROM kern.his_personderzoek WHERE personderzoek IN (SELECT id FROM kern.personderzoek WHERE pers IN (SELECT id from kern.pers WHERE bsn =
        $bsn));

        DELETE FROM kern.his_partijonderzoek WHERE partijonderzoek IN (SELECT id FROM kern.partijonderzoek WHERE onderzoek IN (SELECT onderzoek FROM
        kern.personderzoek WHERE pers IN (SELECT id from kern.pers WHERE bsn = $bsn)));
        DELETE FROM kern.partijonderzoek WHERE onderzoek IN (SELECT onderzoek FROM kern.personderzoek WHERE pers IN (SELECT id from kern.pers WHERE bsn =
         $bsn));

        DELETE FROM kern.personderzoek WHERE pers IN (SELECT id from kern.pers WHERE bsn = $bsn);
        --DELETE FROM kern.onderzoek WHERE id NOT IN (SELECT onderzoek from kern.personderzoek);
        """
    }

    /**
     * Voert een SQL statement uit.
     *
     * @param statement
     * @return
     */
    def voerUit(String statement) {
        try {
            Statements stmt = CCJSqlParserUtil.parseStatements(statement)
            def metSelect = stmt.statements.any {Statement st ->
                st instanceof Select
            }

            if (metSelect) {
                return select(statement, false)
            } else {
                return execute(statement, false)
            }
        } catch (JSQLParserException pe) {
            LOGGER.error 'statement niet valide, of veel te complex', pe
            throw pe
        }
    }

    /**
     * Voert SQL statements in een bestand uit.
     * @param file
     * @return
     */
    def voerUit(File file) {
        voerUit(file.text)
    }

    def select(final String statement, boolean valideer = true) {
        try {
            if (valideer) {
                CCJSqlParserUtil.parseStatements(statement)
            }

            sql.rows(statement)
        } catch (SQLException se) {
            LOGGER.error 'kan statement niet uitvoeren', se
            throw se
        } catch (JSQLParserException pe) {
            LOGGER.error 'statement niet valide, of veel te complex', pe
            throw pe
        } finally {
            sql?.close()
        }
    }

    def select(final File file, boolean valideer = true) {
        select(file.text, valideer)
    }

    def execute(String statement, boolean valideer = true) {
        try {
            if (valideer) {
                CCJSqlParserUtil.parseStatements(statement)
            }

            sql.execute(statement)
        } catch (SQLException se) {
            LOGGER.error 'kan statement niet uitvoeren', se
            throw se
        } catch (JSQLParserException pe) {
            LOGGER.error 'statement niet valide, of veel te complex', pe
            throw pe
        } finally {
            sql?.close()
        }
    }

    def execute(File file, boolean valideer = true) {
        execute(file.text, valideer)
    }

    /**
     *
     *
     * @param admHndId
     * @return
     */
    GroovyRowResult loadAdmHnd(long admHndId) {
        try {
            GroovyRowResult row = sql.firstRow([id:admHndId], "SELECT a.id, a.tsreg, sa.naam FROM kern.admhnd a left join kern.srtadmhnd sa on (a.srt = " +
                "sa.id) " +
                "WHERE a" +
                ".id" +
                " = :id")
            return row
        } catch (SQLException any) {
            LOGGER.error 'Kan query niet uitvoeren: {}', query
            throw any
        } finally {
            sql?.close()
        }
    }

    /**
     * Reset voor een persoon (BSN) een specifieke levering door de tslev van de
     * bijbehorende handeling op NULL te zetten, ook wordt de identifier van de
     * administratieve handeling teruggegeven.
     *
     * @param bsn Burgerservicenummer van de persoon
     * @param de numerieke index van de handeling, waarbij de laatste bij 1 begint en dan oploopt bij terugtellen.
     * @return de handelingIds die zijn gereset, gesorteerd op tslaatstewijz van de *handeling* (oudste eerst)
     */
    Long resetLevering(String bsn, int indexVanafEinde) {

        // Bepaal administratieve handeling ids, meeste recente als eerste in lijst
        def verkrijgAdmHndId = """
        SELECT a.id, a.tsreg
            FROM kern.admhnd a
            JOIN kern.his_persafgeleidadministrati hp ON (hp.admhnd = a.id)
            WHERE hp.pers = (SELECT id FROM kern.pers where bsn = ${bsn as Long})
            ORDER BY a.tsreg DESC, a.id DESC
        """
        if (indexVanafEinde > -1) {
            verkrijgAdmHndId += " LIMIT ${indexVanafEinde}"
        }

        try {
            def result = sql.rows(verkrijgAdmHndId)

            if (!result.isEmpty()) {
                Long admHndId = result.last().id

                if (!magHandelingVerwerktWorden(admHndId, bsn)) {
                    LOGGER.error("Er zijn nog onverwerkte handelingen voor persoon ${bsn}, " +
                        "verwerken heeft geen zin voor admhnd ${admHndId}")
                }
                // Zet tijdstip levering op null
                def updateAdmHnd = """
                    UPDATE kern.admhnd SET tslev = null WHERE id = ${admHndId as Long};
                    """

                sql.execute(updateAdmHnd)
                return admHndId
            } else {
                LOGGER.warn 'persoon [{}] heeft geen handelingen', bsn
            }
        } catch (Exception e) {
            LOGGER.error 'kan levering voor persoon met bsn: {} niet resetten', bsn, e
            throw e
        } finally {
            sql?.close()
        }

        return null
    }

    boolean magHandelingVerwerktWorden(Long admHndId, String bsn) {
        def geenOnverwerkteHandelingen = """
        SELECT CASE WHEN (count(*) > 0) THEN false ELSE true END
            FROM kern.his_persafgeleidadministrati afad
            JOIN kern.admhnd ah on (afad.admhnd = ah.id)
            WHERE ah.tslev IS NULL
            AND ah.srt = (SELECT id FROM kern.srtadmhnd WHERE naam = 'GBA - Initiele vulling')
            AND ah.tsreg < (SELECT tsreg FROM kern.admhnd WHERE id = ${admHndId as Long})
            AND afad.pers = (SELECT id FROM kern.pers where bsn = ${bsn as Long})
        """

        GroovyRowResult row = sql.firstRow(geenOnverwerkteHandelingen)

        LOGGER.debug "Mag handeling verwerkt worden (dwz geen onverwerkte handelingen in db): {}", row.getAt(0)
        row.getAt(0)
    }

    /**
     * Schoont de pers / relatie en betr tabellen
     */
    void verwijderAllePersonen() {
        try {
            sql.withStatement {
                stmt -> stmt.queryTimeout = QUERY_TIMEOUT_VERWIJDEREN_ALLES
            }

            sql.execute("truncate kern.betr cascade;")
            sql.execute("truncate kern.relatie cascade;")
            sql.execute("truncate kern.onderzoek cascade;")
            sql.execute("truncate kern.pers cascade;")

            sql.execute("delete from verconv.lo3melding;")
            sql.execute("delete from verconv.lo3voorkomen;")
            sql.execute("delete from verconv.lo3ber;")
            sql.execute("delete from ist.autorisatietabel;")
            sql.execute("delete from ist.stapelrelatie;")
            sql.execute("delete from ist.stapelvoorkomen;")
            sql.execute("delete from ist.stapel;")

        } catch (SQLException any) {
            LOGGER.error 'Kan personen met bsns niet verwijderen'
            throw any
        } finally {
            sql?.close()
        }
    }

    /**
     * Verwijdert test personen uit de database.
     *
     * @param bsns identificatie van te verwijderen personen
     *
     * TODO moet dit niet verwijzen naar {@link #resetPersonen(java.util.List)} ?
     */
    void verwijderPersonen(final List<String> bsns) {
        try {
            bsns.each {
                def bsn = it as Long
                sql.execute("DELETE FROM kern.relatie WHERE id IN "
                        + "(SELECT r.id FROM kern.relatie r JOIN kern.betr b ON (b.relatie = r.id) "
                        + "JOIN kern.pers p ON (b.pers = p.id) WHERE (p.bsn IN (${bsn})));")
                sql.execute("DELETE FROM kern.onderzoek WHERE id IN "
                        + "(SELECT o.id FROM kern.onderzoek o JOIN kern.personderzoek po ON (po.onderzoek = o.id) "
                        + "JOIN kern.pers p ON (po.pers = p.id) WHERE (p.bsn IN (${bsn})));")
                sql.execute("DELETE FROM kern.pers p WHERE (p.bsn IN (${bsn}));")
            }
        } catch (final SQLException any) {
            LOGGER.error 'Kan personen met bsns {} niet verwijderen', bsns
            throw any
        } finally {
            sql?.close()
        }
    }

    public boolean isSynchroonBerichtGearchiveerd(final String richting, final String referentienummer) {
        isBerichtGearchiveerd(richting, referentienummer, true)
    }

    public boolean isLeveringBerichtGearchiveerd(final String referentienummer) {
        isBerichtGearchiveerd(Richting.UITGAAND.naam, referentienummer, false)
    }


    /**
     * Geeft het aantal results van de gegeven query.
     * @param query
     * @return
     */
    public int geefAantal(final String query, final Map params) {
        try {
            def data = sql.rows(query, params)
            data.size()
        } catch (SQLException any) {
            LOGGER.error 'Kan query niet uitvoeren: {}', query
            throw any
        } finally {
            sql?.close()
        }
    }

    /**
     * Geeft het aantal results van de gegeven query.
     * @param query
     * @return
     */
    public GroovyRowResult firstRow(final String query) {
        try {
            GroovyRowResult row = sql.firstRow(query)
            return row
        } catch (SQLException any) {
            LOGGER.error 'Kan query niet uitvoeren: {}', query
            throw any
        } finally {
            sql?.close()
        }
    }

    /**
     * Voert een update statement uit.
     * @param query
     * @return
     */
    public void update(final String query, final Map params) {
        try {
            sql.execute(query, params)
        } catch (SQLException any) {
            LOGGER.error 'Kan query niet uitvoeren: {}', query
            throw any
        } finally {
            sql?.close()
        }
    }

    /**
     * Controleert of een bericht gearchiveerd is voor een richting en referentienummer binnen de laatste twee minuten. Zo worden eventuele eerdere
     * runs van dezelfde test uitgesloten van de query.
     *
     * @param richting de richting van het bericht; ingaand of uitgaand
     * @param referentienummer het referentienummer waarmee het bericht verzonden is
     */
    private boolean isBerichtGearchiveerd(final String richting, final String referentienummer, final boolean isSynchroonBericht) {
        // Er wordt een tijdslimiet gezet op het ophalen van het archiveringsbericht, anders zal een herhalende test mogelijk onterecht aangeven
        // dat er gearchiveerd is, terwijl dit van een vorige run is.
        final def aantalMinutenSindsArchivering = 2;

        // Controleren opgegeven richting.
        try {
            final def richtingEnum = Richting.valueOf(richting.toUpperCase());
            final def referentieVeld;
            if (isSynchroonBericht) {
                referentieVeld = bepaalReferentieVeld(richtingEnum)
            } else {
                referentieVeld = REFERENTIENR;
            }
            final def richtingId = richtingEnum.ordinal()
            final def momentVoorArchivering = bepaalMomentVoorArchivering(aantalMinutenSindsArchivering)
            LOGGER.debug "Archivering voor leveringbericht wordt bekeken vanaf: ${momentVoorArchivering}"

            final def query;
            final def driverName = sql.getConnection().getMetaData().getDriverName().toLowerCase();
            if (driverName.contains("postgres")) {
                query = "SELECT b.id FROM ber.ber b WHERE b.richting = ${richtingId} AND b." + referentieVeld + " = ${referentienummer} " +
                        "AND tsverzending > \'${momentVoorArchivering}\';"
            } else if (driverName.contains("hsql")) {
                def formattedMomentVoorArchivering = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(momentVoorArchivering)
                query = "SELECT b.id FROM ber.ber b WHERE b.richting = ${richtingId} AND b." + referentieVeld + "= ${referentienummer} " +
                        "AND tsverzending > TIMESTAMP \'${formattedMomentVoorArchivering}\';"
            } else {
                throw new SQLException("Database driver wordt niet herkend.")
            }

            def result = sql.rows(query)
            return !result.isEmpty()
        } catch (IllegalArgumentException e) {
            LOGGER.error 'Richting van te vinden archiveringbericht is foutief ingevoerd'
            throw e
        } catch (SQLException e) {
            LOGGER.error 'Kan archiveringsbericht niet ophalen voor richting {} en referentienummer {} ', richting, referentienummer
            throw e
        } finally {
            sql?.close()
        }
    }

    public AfnemerIndicatieGeplaatst(final String bsn, final String leveringsAutorisatienaam){
        def AfnemerIndicatieResultaat = sql.rows(
                 """SELECT * from autaut.persafnemerindicatie
                    WHERE pers=(select id from kern.pers where bsn=${bsn as Long})
                    AND levsautorisatie=(select id from autaut.levsautorisatie where naam=\'${leveringsAutorisatienaam}\')""")

        return AfnemerIndicatieResultaat

    }

    static Date bepaalMomentVoorArchivering(final int aantalMinutenSindsArchivering) {
        def cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, -aantalMinutenSindsArchivering);
        cal.getTime()
    }

    static String bepaalReferentieVeld(final Richting richting) {
        if (richting == Richting.INGAAND) {
            REFERENTIENR;
        } else {
            CROSSREFERENTIENR;
        }
    }

    void activeerAbonnementen(final List<String> strings) {
        resetRelevanteAbonnementen()

        // Controle op naam van abonnementen, vangnet voor eventuele typfouten!
        for (String abonnementNaam : strings) {
            def sqlQueryCheckAbo = "select * from autaut.levsautorisatie where naam = ${abonnementNaam}"
            def rows = sql.rows(sqlQueryCheckAbo)
            if (rows.size() == 0) {
                LOGGER.warn("Leveringsautorisatie met naam '{}' bestaat niet, dus kan niet geactiveerd worden.", abonnementNaam)
            }
        }

        StringBuilder inClause = new StringBuilder()
        inClause.append("'")
        inClause.append(strings.join("', '"))
        inClause.append("'")
        def joinsAbonnementen = inClause.toString();
        def sqlQuery = "update autaut.levsautorisatie set indblok = 1 where naam not in (${joinsAbonnementen}) and indblok <> 0"

        sql.executeUpdate(sqlQuery, new ArrayList<Object>())
        LOGGER.info("Leveringsautorisaties worden geactiveerd: {}", joinsAbonnementen)
    }

    void resetRelevanteAbonnementen() {
        sql.executeUpdate("update autaut.levsautorisatie set indblok = 0 where indblok = 1")
    }
}
