/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Iv01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonAntwoordType.UniekGevondenPersoon;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractJbpmTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:/uc301-test-beans.xml")
public class Uc301Test extends AbstractJbpmTest {

    private static final String
            LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 "
                    +
                    "A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    public Uc301Test() {
        super("/uc301/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    private static Lo3Persoonslijst maakLo3Persoonslijst() throws Exception {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);
        return new Lo3PersoonslijstParser().parse(categorieen);
    }

    @Before
    public void setupPartijRegister() {
        final List<Partij> partijen = new ArrayList<>();
        partijen.add(new Partij("039901", "0399", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("049901", "0499", intToDate(20090101), Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        setPartijRegister(new PartijRegisterImpl(partijen));
    }

    @Test
    public void testHappyFlow() throws Exception {
        // Start process: Ii01
        final Ii01Bericht input = new Ii01Bericht();
        input.setMessageId("TstBer001");
        // input.setANummer("1234579892");
        input.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER, "123457892");
        input.setBronPartijCode("039901");
        input.setDoelPartijCode("049901");
        startProcess(input);

        // Verwacht: BRP zoekpersoon
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen VOISC bericht verwacht", 0, getVoiscBerichten().size());
        Assert.assertEquals("Een (1) sync bericht verwacht", 1, getSyncBerichten().size());

        final SyncBericht zoekPersoonBericht = getSyncBerichten().remove(0);
        Assert.assertEquals("ZoekPersoonBericht verwacht", "ZoekPersoonOpActueleGegevensVerzoek", zoekPersoonBericht.getBerichtType());
        System.out.println("ZoekPersoonBericht: " + zoekPersoonBericht);

        // Verzend: antwoord op zoek persoon bericht
        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        zoekPersoonAntwoordType.setUniekGevondenPersoon(new UniekGevondenPersoon());
        zoekPersoonAntwoordType.getUniekGevondenPersoon().setPersoonId(1);
        zoekPersoonAntwoordType.getUniekGevondenPersoon().setActueelANummer("1234567890");
        zoekPersoonAntwoordType.getUniekGevondenPersoon().setBijhoudingsgemeente("0399");
        zoekPersoonAntwoordType.setResultaat(ZoekPersoonResultaatType.GEVONDEN);
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht = new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);
        zoekPersoonAntwoordBericht.setMessageId("TstBer002");
        zoekPersoonAntwoordBericht.setCorrelationId(zoekPersoonBericht.getMessageId());
        System.out.println("ZoekPersoonAntwoord: " + zoekPersoonAntwoordBericht);
        signalSync(zoekPersoonAntwoordBericht);

        // Verwacht: 1 blokkering
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen VOISC berichten verwacht", 0, getVoiscBerichten().size());
        Assert.assertEquals("Een (1) sync bericht verwacht", 1, getSyncBerichten().size());

        final SyncBericht blokkeringBericht = getSyncBerichten().remove(0);
        Assert.assertEquals("Blokkering bericht verwacht", "BlokkeringVerzoek", blokkeringBericht.getBerichtType());
        System.out.println("Blokkering: " + blokkeringBericht);

        // Verzend: blokkeringAntwoordBericht
        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = new BlokkeringAntwoordBericht();
        blokkeringAntwoordBericht.setMessageId("TstBer003");
        blokkeringAntwoordBericht.setCorrelationId(blokkeringBericht.getMessageId());
        System.out.println("blokkeringAntwoordBericht: " + blokkeringAntwoordBericht);

        signalSync(blokkeringAntwoordBericht);

        // Verwacht: sync bericht opvragen PL
        Assert.assertEquals("Geen BRP bericht verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen VOISC berichten verwacht", 0, getVoiscBerichten().size());
        Assert.assertEquals("Een (1) sync berichten verwacht", 1, getSyncBerichten().size());

        final SyncBericht leesUitBrpVerzoekBericht = getSyncBerichten().remove(0);
        Assert.assertNotNull(leesUitBrpVerzoekBericht);
        Assert.assertEquals("LeesUitBrpVerzoek verwacht", "LeesUitBrpVerzoek", leesUitBrpVerzoekBericht.getBerichtType());
        System.out.println("LeesUitBrpVerzoekBericht: " + leesUitBrpVerzoekBericht);

        // Verzend: antwoord op opvragen PL
        final LeesUitBrpAntwoordBericht syncResponse =
                new LeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht.getMessageId(), Uc301Test.maakLo3Persoonslijst());
        syncResponse.setMessageId("TstBer004");
        // // lees voor het gemak een lg01 om de persoonslijst aan te maken
        // final String lg01String = IOUtils.toString(this.getClass().getResourceAsStream("lg01.txt"));
        // final Lg01Bericht bericht = new Lg01Bericht();
        // bericht.parse(lg01String);
        // final Lo3Persoonslijst pl = bericht.getLo3Persoonslijst();
        System.out.println("LeesUitBrpAntwoordBericht: " + syncResponse);
        signalSync(syncResponse);

        // Verwacht: ib01 bericht
        Assert.assertEquals("Geen BRP bericht verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Een (1) VOISC berichten verwacht", 1, getVoiscBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        final Lo3Bericht ib01Bericht = getVoiscBerichten().remove(0);
        Assert.assertEquals("ib01Bericht verwacht", "Ib01", ib01Bericht.getBerichtType());
        System.out.println("ib01Bericht: " + ib01Bericht);

        // Verzend: iv01 bericht als antwoord op ib01
        final Lo3VerwijzingInhoud inhoud =
                new Lo3VerwijzingInhoud(
                        Lo3Long.wrap(2349326344L),
                        new Lo3Integer(546589734),
                        new Lo3String("Jaap"),
                        null,
                        null,
                        new Lo3String("Appelenberg"),
                        new Lo3Datum(19540307),
                        new Lo3GemeenteCode("0518"),
                        new Lo3LandCode(Lo3LandCode.CODE_NEDERLAND),
                        new Lo3GemeenteCode("0518"),
                        new Lo3Datum(19540309),
                        Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement());

        final Iv01Bericht iv01Bericht = new Iv01Bericht();
        iv01Bericht.setMessageId("TstBer005");
        iv01Bericht.setVerwijzing(inhoud);
        iv01Bericht.setCorrelationId(ib01Bericht.getMessageId());
        iv01Bericht.setBronPartijCode(ib01Bericht.getDoelPartijCode());
        iv01Bericht.setDoelPartijCode(ib01Bericht.getBronPartijCode());

        System.out.println("Iv01: " + iv01Bericht);
        signalVoisc(iv01Bericht);

        // Verwacht: null-bericht en deblokkeerantwoord bericht
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Een (1) VOISC bericht verwacht", 1, getVoiscBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        final Lo3Bericht nullBericht = getVoiscBerichten().remove(0);
        Assert.assertEquals("nullbericht verwacht", "Null", nullBericht.getBerichtType());
        System.out.println("nullbericht:" + nullBericht);
        Assert.assertEquals("nullbericht correlatie niet juist", iv01Bericht.getMessageId(), nullBericht.getCorrelationId());

        Assert.assertTrue(processEnded());
    }
}
