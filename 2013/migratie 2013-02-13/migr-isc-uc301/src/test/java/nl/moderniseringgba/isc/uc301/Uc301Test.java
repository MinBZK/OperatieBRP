/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.bpm.AbstractJbpmTest;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.generated.GevondenPersonenType;
import nl.moderniseringgba.isc.esb.message.brp.generated.GevondenPersoonType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ZoekPersoonAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Iv01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.migratie.service.GemeenteService;
import nl.moderniseringgba.isc.migratie.service.Stelsel;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:/uc301-test-beans.xml")
public class Uc301Test extends AbstractJbpmTest {

    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    @Inject
    private GemeenteService gemeenteService;

    public Uc301Test() {
        super("/uc301/processdefinition.xml,/foutafhandeling/processdefinition.xml");
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
        // Start process: Ii01
        final Ii01Bericht input = new Ii01Bericht();
        // input.setANummer("1234579892");
        input.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER, "123457892");
        input.setBronGemeente("0399");
        input.setDoelGemeente("0499");

        Mockito.when(gemeenteService.geefStelselVoorGemeente(399)).thenReturn(Stelsel.GBA);
        Mockito.when(gemeenteService.geefStelselVoorGemeente(499)).thenReturn(Stelsel.BRP);

        startProcess(input);

        // Verwacht: BRP zoekpersoon
        Assert.assertEquals("Een (1) BRP berichten verwacht", 1, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG bericht verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        final BrpBericht zoekPersoonBericht = getBrpBerichten().remove(0);
        Assert.assertEquals("ZoekPersoonBericht verwacht", "ZoekPersoonVerzoek", zoekPersoonBericht.getBerichtType());
        System.out.println("ZoekPersoonBericht: " + zoekPersoonBericht);

        // Verzend: antwoord op zoek persoon bericht
        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        final GevondenPersonenType gevondenPersonenType = new GevondenPersonenType();
        final List<GevondenPersoonType> gevondenPersoonTypeLijst = gevondenPersonenType.getGevondenPersoon();
        final GevondenPersoonType gevondenPersoon = new GevondenPersoonType();
        gevondenPersoon.setANummer("1234567890");
        gevondenPersoon.setBijhoudingsgemeente("0399");
        gevondenPersoonTypeLijst.add(gevondenPersoon);
        zoekPersoonAntwoordType.setGevondenPersonen(gevondenPersonenType);
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
        zoekPersoonAntwoordType.setToelichting("blablabla");
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht =
                new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);
        zoekPersoonAntwoordBericht.setCorrelationId(zoekPersoonBericht.getMessageId());
        System.out.println("ZoekPersoonAntwoord: " + zoekPersoonAntwoordBericht);
        signalBrp(zoekPersoonAntwoordBericht);

        // Verwacht: 1 blokkering
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync bericht verwacht", 1, getSyncBerichten().size());

        final SyncBericht blokkeringBericht = getSyncBerichten().remove(0);
        Assert.assertEquals("Blokkering bericht verwacht", "BlokkeringVerzoek", blokkeringBericht.getBerichtType());
        System.out.println("Blokkering: " + blokkeringBericht);

        // Verzend: blokkeringAntwoordBericht
        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = new BlokkeringAntwoordBericht();
        blokkeringAntwoordBericht.setCorrelationId(blokkeringBericht.getMessageId());
        System.out.println("blokkeringAntwoordBericht: " + blokkeringAntwoordBericht);

        signalSync(blokkeringAntwoordBericht);

        // Verwacht: sync bericht opvragen PL
        Assert.assertEquals("Geen BRP bericht verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Een (1) sync berichten verwacht", 1, getSyncBerichten().size());

        final SyncBericht leesUitBrpVerzoekBericht = getSyncBerichten().remove(0);
        Assert.assertNotNull(leesUitBrpVerzoekBericht);
        Assert.assertEquals("LeesUitBrpVerzoek verwacht", "LeesUitBrpVerzoek",
                leesUitBrpVerzoekBericht.getBerichtType());
        System.out.println("LeesUitBrpVerzoekBericht: " + leesUitBrpVerzoekBericht);

        // Verzend: antwoord op opvragen PL
        final LeesUitBrpAntwoordBericht syncResponse =
                new LeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht.getMessageId(), maakLo3Persoonslijst());
        // // lees voor het gemak een lg01 om de persoonslijst aan te maken
        // final String lg01String = IOUtils.toString(this.getClass().getResourceAsStream("lg01.txt"));
        // final Lg01Bericht bericht = new Lg01Bericht();
        // bericht.parse(lg01String);
        // final Lo3Persoonslijst pl = bericht.getLo3Persoonslijst();
        System.out.println("LeesUitBrpAntwoordBericht: " + syncResponse);
        signalSync(syncResponse);

        // Verwacht: ib01 bericht
        Assert.assertEquals("Geen BRP bericht verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Een (1) VOSPG berichten verwacht", 1, getVospgBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        final Lo3Bericht ib01Bericht = getVospgBerichten().remove(0);
        Assert.assertEquals("ib01Bericht verwacht", "Ib01", ib01Bericht.getBerichtType());
        System.out.println("ib01Bericht: " + ib01Bericht);

        // Verzend: iv01 bericht als antwoord op ib01
        final Lo3VerwijzingInhoud inhoud =
                new Lo3VerwijzingInhoud(2349326344L, 546589734L, "Jaap", null, null, "Appelenberg", new Lo3Datum(
                        19540307), new Lo3GemeenteCode("0518"), new Lo3LandCode(Lo3LandCode.CODE_NEDERLAND),
                        new Lo3GemeenteCode("0518"), new Lo3Datum(19540309), "Lange poten", null, new Lo3Huisnummer(
                                14), null, null, null, "2543WW", "Den Haag", null, null, null,
                        Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement());

        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(20000101), new Lo3Datum(20000102));

        final Lo3Categorie<Lo3VerwijzingInhoud> categorie =
                new Lo3Categorie<Lo3VerwijzingInhoud>(inhoud, null, historie, null);

        final Iv01Bericht iv01Bericht = new Iv01Bericht();
        iv01Bericht.setVerwijzing(categorie);
        iv01Bericht.setCorrelationId(ib01Bericht.getMessageId());
        iv01Bericht.setBronGemeente(ib01Bericht.getDoelGemeente());
        iv01Bericht.setDoelGemeente(ib01Bericht.getBronGemeente());

        System.out.println("Iv01: " + iv01Bericht);
        signalVospg(iv01Bericht);

        // Verwacht: null-bericht en deblokkeerantwoord bericht
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Een (1) VOSPG bericht verwacht", 1, getVospgBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        final Lo3Bericht nullBericht = getVospgBerichten().remove(0);
        Assert.assertEquals("nullbericht verwacht", "Null", nullBericht.getBerichtType());
        System.out.println("nullbericht:" + nullBericht);
        Assert.assertEquals("nullbericht correlatie niet juist", iv01Bericht.getMessageId(),
                nullBericht.getCorrelationId());

        Assert.assertTrue(processEnded());
    }
}
