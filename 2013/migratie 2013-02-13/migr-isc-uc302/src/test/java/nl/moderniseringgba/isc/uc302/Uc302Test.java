/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.math.BigDecimal;
import java.util.List;

import nl.moderniseringgba.isc.esb.bpm.AbstractJbpmTest;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.MvVerhuizingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ib01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.NullBericht;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:/uc302-test-beans.xml")
public class Uc302Test extends AbstractJbpmTest {

    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    public Uc302Test() {
        super("/uc302/processdefinition.xml");
    }

    private static Lo3Persoonslijst maakLo3Persoonslijst() throws Exception {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);
        final Lo3Persoonslijst result = new Lo3PersoonslijstParser().parse(categorieen);
        return result;
    }

    // CHECKSTYLE:OFF - NCSS - Lang proces
    @Test
    public void testHappyFlow() throws Exception {
        // CHECKSTYLE:ON
        // Start process: verhuisbericht final String origineel =

        final VerhuizingVerzoekBericht input = new VerhuizingVerzoekBericht();
        input.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("1904")));
        input.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1905")));
        input.setAfzender("Jaap");
        input.setANummer("1234567892");
        input.setBsn("567545445");
        input.setHuidigeGemeente("1904");
        input.setNieuweGemeente("1905");
        input.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal(input.getHuidigeGemeente())));
        input.setBrpGemeente(new BrpGemeenteCode(new BigDecimal(input.getNieuweGemeente())));
        input.setDatumInschrijving("20120921");
        input.setLocatieOmschrijving("Onder de derde brug");
        System.out.println("Verhuisbericht: " + input);
        startProcess(input);

        // Verwacht: BRP blokkering
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG bericht verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync bericht verwacht", 1, getSyncBerichten().size());

        final SyncBericht blokkeringBericht = getSyncBerichten().remove(0);
        Assert.assertEquals("Blokkeringsbericht verwacht", "BlokkeringVerzoek", blokkeringBericht.getBerichtType());
        System.out.println("Blokkering: " + blokkeringBericht);

        // Verzend: bevestiging
        final BlokkeringAntwoordBericht bevestigingOpBlokkering = new BlokkeringAntwoordBericht();
        bevestigingOpBlokkering.setCorrelationId(blokkeringBericht.getMessageId());
        System.out.println("Bevestiging: " + bevestigingOpBlokkering);
        signalSync(bevestigingOpBlokkering);

        // Verwacht: ii01
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Een (1) VOSPG bericht verwacht", 1, getVospgBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        final Lo3Bericht ii01Bericht = getVospgBerichten().remove(0);
        Assert.assertEquals("Ii01 bericht verwacht", "Ii01", ii01Bericht.getBerichtType());
        System.out.println("Ii01: " + ii01Bericht);

        // Verzend: ib01
        final Ib01Bericht ib01Bericht = new Ib01Bericht();
        ib01Bericht.setLo3Persoonslijst(maakLo3Persoonslijst());
        // final String ib01String = IOUtils.toString(this.getClass().getResourceAsStream("ib01.txt"));
        // ib01Bericht.parse(ib01String);
        // ib01Bericht.setCorrelationId(ii01Bericht.getMessageId());
        // System.out.println("Ib01: " + ib01Bericht);

        signalVospg(ib01Bericht);

        // Verwacht: sync
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync bericht verwacht", 1, getSyncBerichten().size());

        final SyncBericht syncBericht = getSyncBerichten().remove(0);
        Assert.assertNotNull(syncBericht);
        Assert.assertEquals("Store bericht verwacht", "SynchroniseerNaarBrpVerzoek", syncBericht.getBerichtType());
        System.out.println("Store: " + syncBericht);

        // Verzend: store response
        final SynchroniseerNaarBrpAntwoordBericht syncResponse =
                new SynchroniseerNaarBrpAntwoordBericht(syncBericht.getMessageId());
        syncResponse.setCorrelationId(syncBericht.getMessageId());
        System.out.println("StoreResponse: " + syncResponse);
        signalSync(syncResponse);

        // Verwacht: verhuisbericht
        Assert.assertEquals("Een (1) BRP bericht verwacht", 1, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        final BrpBericht verhuisBericht = getBrpBerichten().remove(0);
        Assert.assertEquals("Verhuisbericht verwacht", "MvVerhuizingVerzoek", verhuisBericht.getBerichtType());
        System.out.println("Verhuisbericht: " + verhuisBericht);

        // Verzend: bevestiging
        final MvVerhuizingAntwoordBericht bevestigingOpVerhuizing = new MvVerhuizingAntwoordBericht();
        bevestigingOpVerhuizing.setCorrelationId(verhuisBericht.getMessageId());
        System.out.println("Bevestiging: " + bevestigingOpVerhuizing);
        signalBrp(bevestigingOpVerhuizing);

        // Verwacht: deblokkering
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG bericht verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync bericht verwacht", 1, getSyncBerichten().size());

        final SyncBericht deblokkeringBericht = getSyncBerichten().remove(0);
        Assert.assertEquals("Deblokkeringsbericht verwacht", "DeblokkeringVerzoek",
                deblokkeringBericht.getBerichtType());
        System.out.println("Deblokkering:" + deblokkeringBericht);

        // Verzend: bevestiging
        final DeblokkeringAntwoordBericht bevestigingOpDeblokkering = new DeblokkeringAntwoordBericht();
        bevestigingOpDeblokkering.setCorrelationId(deblokkeringBericht.getMessageId());
        System.out.println("Bevestiging:" + bevestigingOpDeblokkering);
        signalSync(bevestigingOpDeblokkering);

        // Verwacht: notificatie
        Assert.assertEquals("Een (1) BRP berichten verwacht", 1, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Een (1) VOSPG bericht verwacht", 1, getVospgBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        final BrpBericht verhuisAntwoord = getBrpBerichten().remove(0);
        Assert.assertEquals("VerhuizingAntwoord verwacht", "VerhuizingAntwoord", verhuisAntwoord.getBerichtType());
        System.out.println("VerhuizingAntwoord:" + verhuisAntwoord);
        Assert.assertEquals("VerhuizingAntwoord correlatie niet juist", input.getMessageId(),
                verhuisAntwoord.getCorrelationId());

        final Lo3Bericht iv01Bericht = getVospgBerichten().remove(0);
        Assert.assertEquals("Iv01 bericht verwacht", "Iv01", iv01Bericht.getBerichtType());
        Assert.assertEquals("Iv01 correlatie niet juist", ib01Bericht.getMessageId(), iv01Bericht.getCorrelationId());

        // Verzend: null
        final Lo3Bericht nullBericht = new NullBericht();
        nullBericht.setCorrelationId(iv01Bericht.getMessageId());

        signalVospg(nullBericht);

        // Check output
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testHerhaalIi01Bericht() {
        // Start process: verhuisbericht
        final VerhuizingVerzoekBericht input = new VerhuizingVerzoekBericht();
        input.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("1904")));
        input.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1905")));
        input.setAfzender("Jaap");
        input.setANummer("1234567892");
        input.setBsn("567545445");
        input.setHuidigeGemeente("1904");
        input.setNieuweGemeente("1905");
        input.setDatumInschrijving("20120921");
        input.setLocatieOmschrijving("Onder de derde brug");
        input.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal(input.getHuidigeGemeente())));
        input.setBrpGemeente(new BrpGemeenteCode(new BigDecimal(input.getNieuweGemeente())));
        System.out.println("Verhuisbericht: " + input);
        startProcess(input);

        // Verwacht: BRP blokkering
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG bericht verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync bericht verwacht", 1, getSyncBerichten().size());

        final SyncBericht blokkeringBericht = getSyncBerichten().remove(0);
        Assert.assertEquals("Blokkeringsbericht verwacht", "BlokkeringVerzoek", blokkeringBericht.getBerichtType());
        System.out.println("Blokkering: " + blokkeringBericht);

        // Verzend: bevestiging
        final BlokkeringAntwoordBericht bevestigingOpBlokkering = new BlokkeringAntwoordBericht();
        bevestigingOpBlokkering.setCorrelationId(blokkeringBericht.getMessageId());
        System.out.println("Bevestiging: " + bevestigingOpBlokkering);
        signalSync(bevestigingOpBlokkering);

        // Verwacht: ii01
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Een (1) VOSPG bericht verwacht", 1, getVospgBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        final Lo3Bericht ii01Bericht = getVospgBerichten().remove(0);
        Assert.assertEquals("Ii01 bericht verwacht", "Ii01", ii01Bericht.getBerichtType());
        // Assert.assertEquals(0, ii01Bericht.getHeader(Lo3HeaderVeld.HERHALING));
        System.out.println("Ii01: " + ii01Bericht);

        // Simuleer timeout
        signalProcess("timeout");

        // Verwacht: ii01
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Een (1) VOSPG bericht verwacht", 1, getVospgBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        final Lo3Bericht ii01Bericht2 = getVospgBerichten().remove(0);
        Assert.assertEquals("Ii01 bericht verwacht", "Ii01", ii01Bericht2.getBerichtType());
        System.out.println("Ii01 (2): " + ii01Bericht2);
        Assert.assertEquals("1", ii01Bericht2.getHeader(Lo3HeaderVeld.HERHALING));
        Assert.assertEquals(ii01Bericht.getMessageId(), ii01Bericht2.getMessageId());

    }
}
