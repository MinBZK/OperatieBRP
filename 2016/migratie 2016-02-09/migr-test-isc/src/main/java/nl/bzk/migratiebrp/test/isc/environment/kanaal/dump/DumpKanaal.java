/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.dump;

import java.io.IOException;
import javax.inject.Inject;
import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.sql.SqlHelper;

/**
 * Dump ISC kanaal.
 */
public final class DumpKanaal extends LazyLoadingKanaal {

    /** Kanaal naam. */
    public static final String KANAAL = "dump";

    /**
     * Constructor.
     */
    public DumpKanaal() {
        super(new Worker(), new Configuration(
            "classpath:configuratie.xml",
            "classpath:infra-jmx-routering.xml",
            "classpath:infra-db-brp.xml",
            "classpath:infra-db-isc.xml",
            "classpath:infra-db-sync.xml",
            "classpath:infra-db-voisc.xml",
            "classpath:infra-db-gbav.xml",
            "classpath:infra-sql.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {
        private static final String VOISC = "VOISC";
        private static final String BRP = "BRP";
        private static final String NEW_LINE = "<br />";
        private static final String ISC = "ISC";

        @Inject
        private SqlHelper sqlHelper;
        @Inject
        private MBeanServerConnection routeringServerConnection;

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {

            final StringBuilder berichtInhoud = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><systeemDump>");

            berichtInhoud.append("Queue gegevens:" + NEW_LINE);
            berichtInhoud.append(bepaalAantalBerichtenOpQueue());
            berichtInhoud.append(NEW_LINE + NEW_LINE + "Database gegevens:" + NEW_LINE);
            berichtInhoud.append(dumpDatabases());

            berichtInhoud.append("</systeemDump>");

            final Bericht resultaat = new Bericht();
            resultaat.setInhoud(berichtInhoud.toString());
            return resultaat;
        }

        @Override
        protected boolean vergelijkInhoud(final String verwachteInhoud, final String ontvangenInhoud) {
            return true;
        }

        private String dumpDatabases() {

            final StringBuilder databaseGegevens = new StringBuilder();

            databaseGegevens.append("Gegevens ISC database:" + NEW_LINE);
            dumpIscDatabaseGegevens(databaseGegevens);
            databaseGegevens.append("Gegevens BRP database:" + NEW_LINE);
            dumpBrpDatabaseGegevens(databaseGegevens);
            databaseGegevens.append("Gegevens VOISC database:" + NEW_LINE);
            dumpVoiscDatabaseGegevens(databaseGegevens);

            return databaseGegevens.toString().replaceAll("<\\?xml version=\"1.0\"\\?>\n", "");
        }

        private void dumpIscDatabaseGegevens(final StringBuilder databaseGegevens) {

            try {
                // Berichten
                databaseGegevens.append("Aantal records in MIG_BERICHT" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from mig_bericht") + NEW_LINE);

                // Processen
                databaseGegevens.append("Aantal records in MIG_CORRELATIE" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from mig_correlatie") + NEW_LINE);

                databaseGegevens.append("Aantal records in MIG_EXTRACTIE_PROCES" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from mig_extractie_proces") + NEW_LINE);

                databaseGegevens.append("Aantal records in MIG_FOUT" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from mig_fout") + NEW_LINE);

                databaseGegevens.append("Aantal records in MIG_LOCK" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from mig_lock") + NEW_LINE);

                databaseGegevens.append("Aantal records in MIG_LOCK_ANUMMER" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from mig_lock_anummer") + NEW_LINE);

                databaseGegevens.append("Aantal records in MIG_PROCES_GERELATEERD" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from mig_proces_gerelateerd") + NEW_LINE);

                databaseGegevens.append("Aantal records in MIG_VIRTUEEL_PROCES" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from mig_virtueel_proces") + NEW_LINE);

                databaseGegevens.append("Aantal records in MIG_VIRTUEEL_PROCES_GERELATEERD" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from mig_virtueel_proces_gerelateerd") + NEW_LINE);

                databaseGegevens.append("Aantal records in JBPM_JOB" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from jbpm_job") + NEW_LINE);

                databaseGegevens.append("Aantal records in JBPM_LOG" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from jbpm_log") + NEW_LINE);

                databaseGegevens.append("Aantal records in JBPM_PROCESSINSTANCE" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from jbpm_processinstance") + NEW_LINE);

                databaseGegevens.append("Aantal records in JBPM_TASKINSTANCE" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from jbpm_taskinstance") + NEW_LINE);

                databaseGegevens.append("Aantal records in JBPM_TOKEN" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from jbpm_token") + NEW_LINE);

                // Tellingen
                databaseGegevens.append("Aantal records in MIG_RUNTIME" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from mig_runtime") + NEW_LINE);

                databaseGegevens.append("Aantal records in MIG_TELLING_BERICHT" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from mig_telling_bericht") + NEW_LINE);

                databaseGegevens.append("Aantal records in MIG_TELLING_PROCES" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(ISC, "select count(*) from mig_telling_proces") + NEW_LINE);
            } catch (final KanaalException e) {
                databaseGegevens.append("Fout opgetreden bij dumpen gegevens ISC (is ISC actief?). " + NEW_LINE);
            }

        }

        private void dumpBrpDatabaseGegevens(final StringBuilder databaseGegevens) {

            try {
                databaseGegevens.append("Aantal records in KERN.ADMHND" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(BRP, "select count(*) from kern.admhnd"));

                databaseGegevens.append("Aantal records in KERN.PERS" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(BRP, "select count(*) from kern.pers"));

                databaseGegevens.append("Aantal records in KERN.HIS_PERSIDS" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(BRP, "select count(*) from kern.his_persids"));
            } catch (final KanaalException e) {
                databaseGegevens.append("Fout opgetreden bij dumpen gegevens BRP (is BRP actief?)." + NEW_LINE);
            }

        }

        private void dumpVoiscDatabaseGegevens(final StringBuilder databaseGegevens) {

            try {
                databaseGegevens.append("Aantal records in VOISC.BERICHT" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(VOISC, "select count(*) from voisc.bericht"));

                databaseGegevens.append("Aantal records in VOISC.LOGBOEK" + NEW_LINE);
                databaseGegevens.append(sqlHelper.readSql(VOISC, "select count(*) from voisc.logboek"));
            } catch (final KanaalException e) {
                databaseGegevens.append("Fout opgetreden bij dumpen gegevens VOISC (is VOISC actief?)." + NEW_LINE);
            }

        }

        private String bepaalAantalBerichtenOpQueue() {

            final StringBuilder berichtTellingen = new StringBuilder("<queueGegevens>");

            berichtTellingen.append("Queue brp.ontvangst:" + NEW_LINE);
            telAantalBerichtOpQueue(berichtTellingen, "brp.ontvangst");
            berichtTellingen.append("Queue brp.verzenden:" + NEW_LINE);
            telAantalBerichtOpQueue(berichtTellingen, "brp.verzenden");
            berichtTellingen.append("Queue levering.ontvangst:" + NEW_LINE);
            telAantalBerichtOpQueue(berichtTellingen, "levering.ontvangst");
            berichtTellingen.append("Queue sync.response:" + NEW_LINE);
            telAantalBerichtOpQueue(berichtTellingen, "sync.response");
            berichtTellingen.append("Queue sync.request:" + NEW_LINE);
            telAantalBerichtOpQueue(berichtTellingen, "sync.request");
            berichtTellingen.append("Queue voisc.ontvangst:" + NEW_LINE);
            telAantalBerichtOpQueue(berichtTellingen, "voisc.ontvangst");
            berichtTellingen.append("Queue voisc.verzenden:" + NEW_LINE);
            telAantalBerichtOpQueue(berichtTellingen, "voisc.verzenden");
            berichtTellingen.append("Queue vospg.ontvangst:" + NEW_LINE);
            telAantalBerichtOpQueue(berichtTellingen, "vospg.ontvangst");
            berichtTellingen.append("Queue vospg.verzenden:" + NEW_LINE);
            telAantalBerichtOpQueue(berichtTellingen, "vospg.verzenden");
            berichtTellingen.append("</queueGegevens>");

            return berichtTellingen.toString();
        }

        private void telAantalBerichtOpQueue(final StringBuilder tekst, final String queue) {
            telAantalBerichten(tekst, queue, "Queue");
        }

        private void telAantalBerichten(final StringBuilder tekst, final String resource, final String type) {
            try {
                final ObjectName objectNaam =
                        new ObjectName("org.apache.activemq:type=Broker,brokerName=routeringCentrale,destinationType="
                                       + type
                                       + ",destinationName="
                                       + resource);

                tekst.append("De "
                             + type
                             + " "
                             + resource
                             + " bevat "
                             + routeringServerConnection.getAttribute(objectNaam, "MessageCount")
                             + " berichten."
                             + NEW_LINE);
            } catch (final
                JMException
                | IOException e)
            {
                tekst.append("Fout opgetreden bij bepalen aantal bericht op " + type + " (" + resource + ")." + NEW_LINE + e + NEW_LINE);
            }
        }
    }
}
