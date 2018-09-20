/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.bpm.AbstractJbpmTest;
import nl.moderniseringgba.isc.esb.message.BerichtId;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.NotificatieAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.NotificatieVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3Format;
import nl.moderniseringgba.isc.esb.message.lo3.impl.NullBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf11Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tv01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3VerzoekBericht;
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

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "classpath:/uc306-test-beans.xml" })
public class Uc306Test extends AbstractJbpmTest {

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Inject
    private GemeenteService gemeenteService;

    public Uc306Test() {
        super("/uc306/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @Test
    @Ignore
    public void testVerwachtGeboorteAntwoordBerichtAdresbepalendeOuderNietGevondenRedenMeerDan1OuderAdresgevend()
            throws Exception {

        // given
        final String origineel =
                IOUtils.toString(Uc306Test.class.getResourceAsStream("uc306_beideOudersAdresbepalend.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        // when
        Mockito.when(gemeenteService.geefStelselVoorGemeente(1234)).thenReturn(Stelsel.GBA);
        Mockito.when(gemeenteService.geefStelselVoorGemeente(5678)).thenReturn(Stelsel.BRP);

        // Start het process.
        startProcess(bericht);

        // expect Notificatie REDEN_MEER_DAN_1_OUDER_ADRESGEVEND
        final NotificatieVerzoekBericht notificatieAdresbepalendeOuderNietGevonden =
                getBericht(NotificatieVerzoekBericht.class);
        Assert.assertNotNull(notificatieAdresbepalendeOuderNietGevonden);
        Assert.assertEquals(NotificatieVerzoekBericht.class, notificatieAdresbepalendeOuderNietGevonden.getClass());
        Assert.assertEquals(String.format(BepaalAdresbepalendeOuderAction.TOELICHTING,
                BepaalAdresbepalendeOuderAction.REDEN_MEER_DAN_1_OUDER_ADRESGEVEND),
                notificatieAdresbepalendeOuderNietGevonden.getNotificatie());
        Thread.sleep(1000);

        // antwoord
        final NotificatieAntwoordBericht notificatieAntwoordBericht = new NotificatieAntwoordBericht();
        notificatieAntwoordBericht.setCorrelationId(notificatieAdresbepalendeOuderNietGevonden.getMessageId());
        notificatieAntwoordBericht.setMessageId(BerichtId.generateMessageId());
        notificatieAntwoordBericht.setStatus(StatusType.OK);
        // signalBrp(notificatieAntwoordBericht);
        signalProcess(notificatieAntwoordBericht, "notificatieAntwoordBericht");
        Thread.sleep(1000);

        // Check output
        Assert.assertEquals("Geen BRP berichten verwacht", 0, getBrpBerichten().size());
        Assert.assertEquals("Geen MVI berichten verwacht", 0, getMviBerichten().size());
        Assert.assertEquals("Geen VOSPG berichten verwacht", 0, getVospgBerichten().size());
        Assert.assertEquals("Geen sync berichten verwacht", 0, getSyncBerichten().size());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testHappyFlow() throws Exception {

        // given
        final String origineel = IOUtils.toString(Uc306Test.class.getResourceAsStream("uc306_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        // when
        Mockito.when(gemeenteService.geefStelselVoorGemeente(1234)).thenReturn(Stelsel.GBA);
        Mockito.when(gemeenteService.geefStelselVoorGemeente(5678)).thenReturn(Stelsel.BRP);

        // Start het process.
        startProcess(bericht);

        // Controleer sync bericht
        final SyncBericht converteerNaarLo3Verzoek = getBericht(ConverteerNaarLo3VerzoekBericht.class);
        Assert.assertNotNull(converteerNaarLo3Verzoek);
        Assert.assertEquals(ConverteerNaarLo3VerzoekBericht.class, converteerNaarLo3Verzoek.getClass());

        final Lo3Persoonslijst geboorte =
                Lo3PersoonslijstTestUtils.maakGeboorte(Lo3PersoonslijstTestUtils.maakLo3PersoonInhoud());
        //
        // // Verstuur converteer ersponse
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3Antwoord =
                new ConverteerNaarLo3AntwoordBericht(converteerNaarLo3Verzoek.getMessageId(), geboorte);

        System.out.println("ConverteerNaarLo3AntwoordBericht: " + converteerNaarLo3Antwoord);
        signalSync(converteerNaarLo3Antwoord);

        // Controleer tb01 bericht
        final Lo3Bericht tb01Bericht = getBericht(Tb01Bericht.class);
        Assert.assertNotNull(tb01Bericht);
        Assert.assertEquals(Tb01Bericht.class, tb01Bericht.getClass());

        // Verstuur tv01

        final Tv01Bericht tv01Bericht = buildTv01Bericht(buildCat21ElementenLijst());
        tv01Bericht.setCorrelationId(tb01Bericht.getMessageId());

        signalVospg(tv01Bericht);

        // Controleer null
        final Lo3Bericht nullBericht = getBericht(NullBericht.class);
        Assert.assertNotNull(nullBericht);
        Assert.assertEquals(NullBericht.class, nullBericht.getClass());

        // Controleer geboorte antwoord
        final BrpBericht geboorteAntwoordBericht = getBericht(GeboorteAntwoordBericht.class);
        Assert.assertNotNull(geboorteAntwoordBericht);
        Assert.assertEquals(GeboorteAntwoordBericht.class, geboorteAntwoordBericht.getClass());
        Assert.assertEquals(StatusType.OK, ((GeboorteAntwoordBericht) geboorteAntwoordBericht).getStatus());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testNotSoHappyFlow() throws Exception {

        // given
        final String origineel = IOUtils.toString(Uc306Test.class.getResourceAsStream("uc306_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        // when
        Mockito.when(gemeenteService.geefStelselVoorGemeente(1234)).thenReturn(Stelsel.GBA);
        Mockito.when(gemeenteService.geefStelselVoorGemeente(5678)).thenReturn(Stelsel.BRP);

        // Start het process.
        startProcess(bericht);

        // Controleer sync bericht
        final SyncBericht converteerNaarLo3Verzoek = getBericht(ConverteerNaarLo3VerzoekBericht.class);
        Assert.assertNotNull(converteerNaarLo3Verzoek);
        Assert.assertEquals(ConverteerNaarLo3VerzoekBericht.class, converteerNaarLo3Verzoek.getClass());

        final Lo3Persoonslijst geboorte =
                Lo3PersoonslijstTestUtils.maakGeboorte(Lo3PersoonslijstTestUtils.maakLo3PersoonInhoud());
        //
        // Verstuur converteer response
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3Antwoord =
                new ConverteerNaarLo3AntwoordBericht(converteerNaarLo3Verzoek.getMessageId(), geboorte);

        System.out.println("ConverteerNaarLo3AntwoordBericht: " + converteerNaarLo3Antwoord);
        signalSync(converteerNaarLo3Antwoord);

        // Controleer tb01 bericht
        final Lo3Bericht tb01Bericht = getBericht(Tb01Bericht.class);
        Assert.assertNotNull(tb01Bericht);
        Assert.assertEquals(Tb01Bericht.class, tb01Bericht.getClass());

        // Verstuur tv01

        final Tv01Bericht tv01Bericht = buildTv01Bericht(buildAfwijkendeCat21ElementenLijst());
        tv01Bericht.setCorrelationId(tb01Bericht.getMessageId());

        signalVospg(tv01Bericht);

        // Controleer tf11
        final Lo3Bericht tf11Bericht = getBericht(Tf11Bericht.class);
        Assert.assertNotNull(tf11Bericht);
        Assert.assertEquals(Tf11Bericht.class, tf11Bericht.getClass());

        // Controleer geboorte antwoord
        final BrpBericht geboorteAntwoordBericht = getBericht(GeboorteAntwoordBericht.class);
        Assert.assertNotNull(geboorteAntwoordBericht);
        Assert.assertEquals(GeboorteAntwoordBericht.class, geboorteAntwoordBericht.getClass());
        Assert.assertEquals(StatusType.WAARSCHUWING, ((GeboorteAntwoordBericht) geboorteAntwoordBericht).getStatus());

        Assert.assertTrue(processEnded());
    }

	private List<Lo3CategorieWaarde> buildCat21ElementenLijst() {
		
		List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();
		Lo3CategorieWaarde cat21 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_21, 0, 0);

		cat21.addElement(Lo3ElementEnum.ELEMENT_0110, Lo3Format.format(2349326344L));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0120, Lo3Format.format(123456789L));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format("Billy"));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0220, null);
		cat21.addElement(Lo3ElementEnum.ELEMENT_0230, null);
		cat21.addElement(Lo3ElementEnum.ELEMENT_0240, Lo3Format.format("Barendsen"));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0310, Lo3Format.format(new Lo3Datum(20121024)));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0320, Lo3Format.format(new Lo3GemeenteCode("0518")));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0330, Lo3Format.format(new Lo3LandCode(Lo3LandCode.CODE_NEDERLAND)));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0910, Lo3Format.format(new Lo3GemeenteCode("0518")));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0920, Lo3Format.format(new Lo3Datum(20121025)));

		cat21.addElement(Lo3ElementEnum.ELEMENT_7010, Lo3Format.format(Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement()));

		cat21.addElement(Lo3ElementEnum.ELEMENT_8310, null); // TODO: uitzoeken waar dit vandaan moet komen
		cat21.addElement(Lo3ElementEnum.ELEMENT_8320, null);
		cat21.addElement(Lo3ElementEnum.ELEMENT_8330, null);

		cat21.addElement(Lo3ElementEnum.ELEMENT_8510, Lo3Format.format(new Lo3Datum(20000101)));

		categorieen.add(cat21);
		return categorieen;
	}
	
	private List<Lo3CategorieWaarde> buildAfwijkendeCat21ElementenLijst() {
		
		List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();
		Lo3CategorieWaarde cat21 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_21, 0, 0);

		cat21.addElement(Lo3ElementEnum.ELEMENT_0110, Lo3Format.format(2349326344L));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0120, Lo3Format.format(123456789L));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format("Billy"));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0220, null);
		cat21.addElement(Lo3ElementEnum.ELEMENT_0230, null);
		cat21.addElement(Lo3ElementEnum.ELEMENT_0240, Lo3Format.format("Barendsen"));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0310, Lo3Format.format(new Lo3Datum(20121004)));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0320, Lo3Format.format(new Lo3GemeenteCode("0518")));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0330, Lo3Format.format(new Lo3LandCode(Lo3LandCode.CODE_NEDERLAND)));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0910, Lo3Format.format(new Lo3GemeenteCode("0518")));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0920, Lo3Format.format(new Lo3Datum(20121025)));

		cat21.addElement(Lo3ElementEnum.ELEMENT_7010, Lo3Format.format(Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement()));

		cat21.addElement(Lo3ElementEnum.ELEMENT_8310, null); // TODO: uitzoeken waar dit vandaan moet komen
		cat21.addElement(Lo3ElementEnum.ELEMENT_8320, null);
		cat21.addElement(Lo3ElementEnum.ELEMENT_8330, null);

		cat21.addElement(Lo3ElementEnum.ELEMENT_8510, Lo3Format.format(new Lo3Datum(20000101)));

		categorieen.add(cat21);
		return categorieen;
	}
	
    private Tv01Bericht buildTv01Bericht(final List<Lo3CategorieWaarde> categorien) {
        final Tv01Bericht tv01Bericht = new Tv01Bericht(categorien);
        return tv01Bericht;
    }    

}
