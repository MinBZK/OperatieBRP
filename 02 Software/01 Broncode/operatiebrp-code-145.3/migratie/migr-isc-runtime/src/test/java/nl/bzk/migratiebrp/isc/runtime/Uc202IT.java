/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import java.util.List;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BeheerdersKeuzeType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType.Kandidaat;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.uc202.MaakBeheerderskeuzesAction;
import org.hibernate.Session;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Assert;
import org.junit.Test;

public class Uc202IT extends AbstractIT {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void testHappyFlow() throws BerichtInhoudException {
        LOG.info("Starting test");

        // Valideer
        controleerAlleProcessenBeeindigd();

        final String aNummer = "1231231234";

        // Start
        final Lo3Bericht input = maakLg01(aNummer, aNummer, null, null, "042901", "0429");
        input.setMessageId("lg01-happy-001");
        putMessage(VOISC_ONTVANGST_QUEUE, input, 6756L, null);

        // Sync naar BRP
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = expectMessage(SYNC_VERZOEK_QUEUE, SynchroniseerNaarBrpVerzoekBericht.class);
        final SyncBericht synchroniseerNaarBrpAntwoord = maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.TOEGEVOEGD, null);
        putMessage(SYNC_ANTWOORD_QUEUE, synchroniseerNaarBrpAntwoord);

        // Controleer
        controleerAlleProcessenBeeindigd();
    }

    @Test
    public void testBeheerdersKeuzeNieuw() throws BerichtInhoudException, InterruptedException {
        LOG.info("\n***\n***\n *** START\n****\n***");

        // Valideer
        controleerAlleProcessenBeeindigd();

        final String aNummer = "8956545";

        // Start
        LOG.info("\n***\n***\n *** LG-001\n****\n***");
        final Lo3Bericht input = maakLg01(aNummer, aNummer, null, null, "042901", "0429");
        input.setMessageId("lg01-verv-001");
        putMessage(VOISC_ONTVANGST_QUEUE, input, 98756L, null);

        // Sync naar BRP
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = expectMessage(SYNC_VERZOEK_QUEUE, SynchroniseerNaarBrpVerzoekBericht.class);
        LOG.info("\n***\n***\n *** SYNC-001\n****\n***");
        final SyncBericht synchroniseerNaarBrpAntwoord = maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.ONDUIDELIJK, null);
        putMessage(SYNC_ANTWOORD_QUEUE, synchroniseerNaarBrpAntwoord);

        // Tijd om te verwerken
        TimeUnit.MILLISECONDS.sleep(2000);

        // Beheerderskeuze - NIEUW
        LOG.info("\n***\n***\n *** TASK-001\n****\n***");
        executeInJbpmInTransaction(jbpmContext -> {
            final Session hibernateSession = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);

            final List<TaskInstance> taskList = hibernateSession.createQuery("from org.jbpm.taskmgmt.exe.TaskInstance ti where ti.end is null").list();
            // final List<TaskInstance> taskList = jbpmContext.getTaskList();
            Assert.assertEquals("Slechts 1 taak verwacht", 1, taskList.size());

            // Zoek task
            final TaskInstance taskInstance = taskList.iterator().next();

            // Verwerk task
            taskInstance.start();
            taskInstance.setVariable("restart", MaakBeheerderskeuzesAction.KEUZE_NIEUW);
            taskInstance.end();

            // Done
            return null;
        });

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek2 = expectMessage(SYNC_VERZOEK_QUEUE, SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertEquals(BeheerdersKeuzeType.TOEVOEGEN, synchroniseerNaarBrpVerzoek2.getBeheerderKeuze().getKeuze());
        LOG.info("\n***\n***\n *** SYNC-002\n****\n***");
        final SyncBericht synchroniseerNaarBrpAntwoord2 = maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek2, StatusType.TOEGEVOEGD, null);
        putMessage(SYNC_ANTWOORD_QUEUE, synchroniseerNaarBrpAntwoord2);

        // Controleer
        controleerAlleProcessenBeeindigd();
    }

    @Test
    public void testAutomatischVerwerkenBeheerderstaken() throws InterruptedException {
        LOG.info("\n***\n***\n *** START\n****\n***");

        // Valideer
        controleerAlleProcessenBeeindigd();

        final String aNummer = "8565466";

        // Start (syncbericht 1)
        LOG.info("\n***\n***\n *** LG-001\n****\n***");
        final Lo3Bericht input1 = maakLg01(aNummer, aNummer, null, null, "042901", "0429");
        input1.setMessageId("lg01-autom-001");
        putMessage(VOISC_ONTVANGST_QUEUE, input1, 123L, null);

        // Sync naar BRP
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek1 = expectMessage(SYNC_VERZOEK_QUEUE, SynchroniseerNaarBrpVerzoekBericht.class);
        LOG.info("\n***\n***\n *** SYNC-001\n****\n***");
        Assert.assertEquals(1, bepaalAantalLopendeProcessen());
        final SyncBericht synchroniseerNaarBrpAntwoord1 = maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek1, StatusType.ONDUIDELIJK, null);
        putMessage(SYNC_ANTWOORD_QUEUE, synchroniseerNaarBrpAntwoord1);
        TimeUnit.MILLISECONDS.sleep(2000);

        // Controleer (uc202 + fout)
        Assert.assertEquals(2, bepaalAantalLopendeProcessen());

        // Start (syncbericht 2)
        LOG.info("\n***\n***\n *** LG-002\n****\n***");
        final Lo3Bericht input2 = maakLg01(aNummer, aNummer, null, null, "042901", "0429");
        input2.setMessageId("lg01-autom-002");
        putMessage(VOISC_ONTVANGST_QUEUE, input2, 53445L, null);

        // Sync naar BRP
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek2 = expectMessage(SYNC_VERZOEK_QUEUE, SynchroniseerNaarBrpVerzoekBericht.class);
        LOG.info("\n***\n***\n *** SYNC-002\n****\n***");
        Assert.assertEquals(3, bepaalAantalLopendeProcessen());
        final SyncBericht synchroniseerNaarBrpAntwoord2 = maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek2, StatusType.VERVANGEN, null);
        putMessage(SYNC_ANTWOORD_QUEUE, synchroniseerNaarBrpAntwoord2);

        // Controleer (sync bericht 2 zou nu verwerkt moeten zijn;
        // sync bericht 1 final zou nu automatisch final herhaald moeten zijn)
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek1b = expectMessage(SYNC_VERZOEK_QUEUE, SynchroniseerNaarBrpVerzoekBericht.class);
        Assert.assertEquals(1, bepaalAantalLopendeProcessen());
        LOG.info("\n***\n***\n *** SYNC-001b\n****\n***");
        final SyncBericht synchroniseerNaarBrpAntwoord1b = maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek1b, StatusType.GENEGEERD, null);
        putMessage(SYNC_ANTWOORD_QUEUE, synchroniseerNaarBrpAntwoord1b);

        // Controleer (alles beeindigd)
        controleerAlleProcessenBeeindigd();
        LOG.info("\n***\n***\n *** END\n****\n***");
    }

    /*
     * *** LG01
     * ***************************************************************************************
     */

    protected Lg01Bericht maakLg01(final String aNummerKop, final String aNummerInhoud, final String vorigANummerKop, final String vorigANummerInhoud,
                                   final String gemeenteKop, final String gemeenteInhoud) {

        final Lg01Bericht lg01 = new Lg01Bericht();
        lg01.setMessageId(generateMessageId());
        lg01.setHeader(Lo3HeaderVeld.A_NUMMER, aNummerKop);
        lg01.setHeader(Lo3HeaderVeld.OUD_A_NUMMER, vorigANummerKop);
        lg01.setLo3Persoonslijst(maakPersoonslijst(aNummerInhoud, vorigANummerInhoud, gemeenteInhoud));
        lg01.setBronPartijCode(gemeenteKop);
        lg01.setDoelPartijCode("199902");

        final String formattedLg01 = lg01.format();

        try {
            lg01.parse(formattedLg01);
        } catch (BerichtSyntaxException | BerichtInhoudException e) {
            e.printStackTrace();
            return null;
        }

        return lg01;
    }

    protected Lo3Persoonslijst maakPersoonslijst(final String aNummerInhoud, final String vorigANummerInhoud, final String gemeenteInhoud) {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Persoon(aNummerInhoud, null, "Jan", null, null, "Jansen", 19700101, "0518", "6030", "M", vorigANummerInhoud, null, "E"),
                Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(19700101), new Lo3Herkomst(Lo3CategorieEnum.PERSOON, 0, 0))));

        builder.verblijfplaatsStapel(Lo3StapelHelper
                .lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats(gemeenteInhoud, 1970101, 1970101, "Straat", 15, "9876AA", "I"), null,
                        Lo3StapelHelper.lo3His(19700101), new Lo3Herkomst(Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0))));
        // @formatter:on

        return builder.build();
    }

    /*
     * *** BLOKKERING INFO
     * *****************************************************************************************
     */

    protected BlokkeringInfoAntwoordBericht maakBlokkeringInfoAntwoordBericht(final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek,
                                                                              final PersoonsaanduidingType persoonsaanduiding, final String processId,
                                                                              final String gemeenteNaar) {
        final BlokkeringInfoAntwoordBericht result = new BlokkeringInfoAntwoordBericht();
        result.setStatus(StatusType.OK);
        result.setPersoonsaanduiding(persoonsaanduiding);
        result.setProcessId(processId);
        result.setGemeenteNaar(gemeenteNaar);
        result.setMessageId(generateMessageId());
        result.setCorrelationId(blokkeringInfoVerzoek.getMessageId());

        return result;
    }

    /*
     * *** SYNC NAAR
     * BRP******************************************************************************************
     * **
     */

    protected SynchroniseerNaarBrpAntwoordBericht maakSynchroniseerNaarBrpAntwoordBericht(final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek,
                                                                                          final StatusType status, final List<Kandidaat> kandidaten) {
        final SynchroniseerNaarBrpAntwoordBericht result = new SynchroniseerNaarBrpAntwoordBericht();
        result.setStatus(status);
        result.setMessageId(generateMessageId());
        result.setCorrelationId(synchroniseerNaarBrpVerzoek.getMessageId());
        result.setKandidaten(kandidaten);

        return result;
    }

}
