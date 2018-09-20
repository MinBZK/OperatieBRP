/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.List;

import nl.moderniseringgba.isc.esb.bpm.AbstractJbpmTest;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.mvi.impl.PlSyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.generated.SearchResultaatType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:/uc202-test-beans.xml")
public class Uc202Test extends AbstractJbpmTest {
    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    public Uc202Test() {
        super("/uc202/processdefinition.xml");
    }

    private static Lo3Persoonslijst maakLo3Persoonslijst() throws Exception {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);
        final Lo3Persoonslijst result = new Lo3PersoonslijstParser().parse(categorieen);
        return result;
    }

    @Test
    public void testHappyToevoegenFlow() throws Exception {
        final PlSyncBericht input = new PlSyncBericht();
        input.setLo3Persoonslijst(maakLo3Persoonslijst());

        startProcess(input);

        // Verwacht: bepalen PL
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync berichten verwacht", 1, getSyncBerichten().size());

        final SyncBericht searchBericht = getSyncBerichten().remove(0);
        Assert.assertNotNull(searchBericht);
        Assert.assertEquals("SynchronisatieStrategieVerzoek bericht verwacht", "SynchronisatieStrategieVerzoek",
                searchBericht.getBerichtType());

        // Verzend: search response
        final SynchronisatieStrategieAntwoordBericht searchResponse = new SynchronisatieStrategieAntwoordBericht();
        searchResponse.setStatus(StatusType.OK);
        searchResponse.setResultaat(SearchResultaatType.TOEVOEGEN);
        signalSync(searchResponse);

        // Verwacht: sync
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync berichten verwacht", 1, getSyncBerichten().size());

        final SyncBericht syncBericht = getSyncBerichten().remove(0);
        Assert.assertNotNull(syncBericht);
        Assert.assertEquals("Store bericht verwacht", "SynchroniseerNaarBrpVerzoek", syncBericht.getBerichtType());

        // Verzend: query response
        final SynchroniseerNaarBrpAntwoordBericht storeResponse =
                new SynchroniseerNaarBrpAntwoordBericht(syncBericht.getMessageId());
        signalSync(storeResponse);

        // Niets meer verwacht: MVI koppeling is fire-and-forget
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testHappyVervangenEnVerhuizenFlow() throws Exception {
        final PlSyncBericht input = new PlSyncBericht();
        input.setLo3Persoonslijst(maakLo3Persoonslijst());

        startProcess(input);

        // Verwacht: bepalen PL
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync berichten verwacht", 1, getSyncBerichten().size());

        final SyncBericht searchBericht = getSyncBerichten().remove(0);
        Assert.assertNotNull(searchBericht);
        Assert.assertEquals("SynchronisatieStrategieVerzoek bericht verwacht", "SynchronisatieStrategieVerzoek",
                searchBericht.getBerichtType());

        // Verzend: search response
        final SynchronisatieStrategieAntwoordBericht searchResponse = new SynchronisatieStrategieAntwoordBericht();
        searchResponse.setStatus(StatusType.OK);
        searchResponse.setResultaat(SearchResultaatType.VERVANGEN);
        searchResponse.setLo3Persoonslijst(maakLo3Persoonslijst());
        signalSync(searchResponse);

        // Verwacht: BRP blokkering info
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG bericht verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync bericht verwacht", 1, getSyncBerichten().size());

        final SyncBericht blokkeringInfoBericht = getSyncBerichten().remove(0);
        Assert.assertEquals("Blokkeringinfobericht verwacht", "BlokkeringInfoVerzoek",
                blokkeringInfoBericht.getBerichtType());
        System.out.println("Blokkering info: " + blokkeringInfoBericht);

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord = new BlokkeringInfoAntwoordBericht();
        blokkeringInfoAntwoord.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA); // optioneel
                                                                                                               // veld
        blokkeringInfoAntwoord.setProcessId("23423423");
        signalSync(blokkeringInfoAntwoord);

        // Verwacht: sync
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync bericht verwacht", 1, getSyncBerichten().size());

        final SyncBericht syncBericht = getSyncBerichten().remove(0);
        Assert.assertNotNull(syncBericht);
        Assert.assertEquals("Store bericht verwacht", "SynchroniseerNaarBrpVerzoek", syncBericht.getBerichtType());

        // Verzend: query response
        final SynchroniseerNaarBrpAntwoordBericht storeResponse =
                new SynchroniseerNaarBrpAntwoordBericht(syncBericht.getMessageId());
        signalSync(storeResponse);

        // Verwacht deblokkering
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG bericht verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync bericht verwacht", 1, getSyncBerichten().size());

        final SyncBericht deblokkeringBericht = getSyncBerichten().remove(0);
        Assert.assertEquals("Deblokkeringericht verwacht", "DeblokkeringVerzoek",
                deblokkeringBericht.getBerichtType());

        final DeblokkeringAntwoordBericht deblokkeringAntwoord = new DeblokkeringAntwoordBericht();
        blokkeringInfoAntwoord.setCorrelationId(deblokkeringBericht.getMessageId());
        signalSync(deblokkeringAntwoord);

        // Niets meer verwacht: MVI koppeling is fire-and-forget
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testHappyVervangenFlow() throws Exception {
        final PlSyncBericht input = new PlSyncBericht();
        input.setLo3Persoonslijst(maakLo3Persoonslijst());

        startProcess(input);

        // Verwacht: bepalen PL
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync berichten verwacht", 1, getSyncBerichten().size());

        final SyncBericht searchBericht = getSyncBerichten().remove(0);
        Assert.assertNotNull(searchBericht);
        Assert.assertEquals("SynchronisatieStrategieVerzoek bericht verwacht", "SynchronisatieStrategieVerzoek",
                searchBericht.getBerichtType());

        // Verzend: search response
        final SynchronisatieStrategieAntwoordBericht searchResponse = new SynchronisatieStrategieAntwoordBericht();
        searchResponse.setStatus(StatusType.OK);
        searchResponse.setResultaat(SearchResultaatType.VERVANGEN);
        searchResponse.setLo3Persoonslijst(maakLo3Persoonslijst());
        signalSync(searchResponse);

        // Verwacht: BRP blokkering info
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG bericht verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync bericht verwacht", 1, getSyncBerichten().size());

        final SyncBericht blokkeringInfoBericht = getSyncBerichten().remove(0);
        Assert.assertEquals("Blokkeringinfobericht verwacht", "BlokkeringInfoVerzoek",
                blokkeringInfoBericht.getBerichtType());
        System.out.println("Blokkering info: " + blokkeringInfoBericht);

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoord = new BlokkeringInfoAntwoordBericht();
        signalSync(blokkeringInfoAntwoord);

        // Verwacht: sync
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync berichten verwacht", 1, getSyncBerichten().size());

        final SyncBericht syncBericht = getSyncBerichten().remove(0);
        Assert.assertNotNull(syncBericht);
        Assert.assertEquals("Store bericht verwacht", "SynchroniseerNaarBrpVerzoek", syncBericht.getBerichtType());

        // Verzend: query response
        final SynchroniseerNaarBrpAntwoordBericht storeResponse =
                new SynchroniseerNaarBrpAntwoordBericht(syncBericht.getMessageId());
        signalSync(storeResponse);

        // Niets meer verwacht: MVI koppeling is fire-and-forget
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        Assert.assertTrue(processEnded());
    }

}
