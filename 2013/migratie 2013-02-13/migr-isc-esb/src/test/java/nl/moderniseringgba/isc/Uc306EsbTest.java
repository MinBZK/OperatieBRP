/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.BerichtId;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.impl.NotificatieAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.NotificatieVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3Format;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tv01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;
import nl.moderniseringgba.isc.uc306.BepaalAdresbepalendeOuderAction;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Builder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Assert;
import org.junit.Test;

public class Uc306EsbTest extends AbstractEsbTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final Lo3BerichtFactory lo3BerichtFactory = new Lo3BerichtFactory();
    private final BrpBerichtFactory brpBerichtFactory = BrpBerichtFactory.SINGLETON;
    private final SyncBerichtFactory syncBerichtFactory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testHappyFlow() throws BerichtSyntaxException, BerichtOnbekendException, BerichtInhoudException,
            InterruptedException {
        // //////////////////////////////////////////////////////////////////
        // BRP WERELD
        // //////////////////////////////////////////////////////////////////

        final Bericht input = new Bericht();
        input.setMessageId(BerichtId.generateMessageId());
        input.setInhoud(readResourceAsString("uc306.xml"));

        LOG.info("Verstuur BRP geboortebericht: " + input);
        verstuurBrpBericht(input);

        Thread.sleep(1000);

        // Verwacht: SYNC Converteer
        final Bericht converteerBericht = this.ontvangSyncBericht();
        LOG.info("Ontvangen SYNC converteer bericht: " + converteerBericht);

        Assert.assertNotNull("Geen Sync converteer bericht ontvangen", converteerBericht);

        final SyncBericht parsedConverteerBericht = syncBerichtFactory.getBericht(converteerBericht.getInhoud());
        Assert.assertEquals("Converteer bericht verwacht maar niet ontvangen", "Converteer",
                parsedConverteerBericht.getBerichtType());

        // Verzend: Converteerresponse bericht
        final Bericht converteerAntwoord = new Bericht();
        converteerAntwoord.setMessageId(BerichtId.generateMessageId());
        converteerAntwoord.setCorrelatieId(converteerBericht.getMessageId());

        final ConverteerNaarLo3AntwoordBericht converteerResponse =
                maakConverteerNaarLo3AntwoordBericht(converteerBericht.getMessageId());

        converteerAntwoord.setInhoud(converteerResponse.format());
        converteerAntwoord.setOriginator(converteerBericht.getRecipient());
        converteerAntwoord.setRecipient(converteerBericht.getOriginator());

        LOG.info("Verstuur converteerResponse: " + converteerAntwoord);
        verstuurSyncBericht(converteerAntwoord);

        Thread.sleep(1000);

        // Verwacht: Tb01
        final Bericht tb01Bericht = ontvangVospgBericht();
        LOG.info("Ontvangen tb01 bericht: " + tb01Bericht);
        Assert.assertNotNull("Geen tb01 bericht ontvangen", tb01Bericht);
        final Lo3Bericht parsedTb01Bericht = lo3BerichtFactory.getBericht(tb01Bericht.getInhoud());
        Assert.assertEquals("Tb01 bericht verwacht", "Tb01", parsedTb01Bericht.getBerichtType());

        // Verzend: Tv01

        final Tv01Bericht tv01Bericht =
                (Tv01Bericht) lo3BerichtFactory.getBericht(buildTv01Bericht(buildCat21ElementenLijst()).format());
        tv01Bericht.setBronGemeente(tb01Bericht.getOriginator());
        tv01Bericht.setDoelGemeente(tb01Bericht.getRecipient());

        final Bericht tv01Wrapper = new Bericht();
        tv01Wrapper.setInhoud(tv01Bericht.format());
        tv01Wrapper.setMessageId(BerichtId.generateMessageId());
        tv01Wrapper.setCorrelatieId(tb01Bericht.getMessageId());
        tv01Wrapper.setOriginator(tb01Bericht.getRecipient());
        tv01Wrapper.setRecipient(tb01Bericht.getOriginator());

        LOG.info("Verstuur tv01: " + tv01Wrapper);
        verstuurVospgBericht(tv01Wrapper);

        Thread.sleep(1000);

        // Verwacht: Null bericht richting Lo3 gemeente
        final Bericht nullBericht = ontvangVospgBericht();
        LOG.info("Ontvangen null bericht: " + nullBericht);
        Assert.assertNotNull(nullBericht);
        final Lo3Bericht parsedNullBericht = lo3BerichtFactory.getBericht(nullBericht.getInhoud());
        Assert.assertEquals("Null bericht verwacht", "Null", parsedNullBericht.getBerichtType());

        // Verwacht: GeboorteAntwoord bericht richting BRP
        final Bericht geboorteAntwoordBericht = ontvangBrpBericht();
        LOG.info("Ontvangen GeboorteAntwoord bericht: " + geboorteAntwoordBericht);
        Assert.assertNotNull(geboorteAntwoordBericht);
        final BrpBericht parsedGeboorteAntwoordBericht =
                brpBerichtFactory.getBericht(geboorteAntwoordBericht.getInhoud());
        Assert.assertEquals("GeboorteAntwoord bericht verwacht", "GeboorteAntwoord",
                parsedGeboorteAntwoordBericht.getBerichtType());
    }

    @Test
    public void testVerwachtGeboorteAntwoordBerichtAdresbepalendeOuderNietGevondenRedenMeerDan1OuderAdresgevend()
            throws Exception {

        final Bericht input = new Bericht();
        input.setMessageId(BerichtId.generateMessageId());
        input.setInhoud(readResourceAsString("uc306_beideOudersAdresbepalend.xml"));

        LOG.info("Verstuur BRP geboortebericht: " + input);
        verstuurBrpBericht(input);

        Thread.sleep(1000);

        // Controleer geboorte antwoord
        final Bericht brpBericht = ontvangBrpBericht();
        Assert.assertNotNull(brpBericht);

        final NotificatieVerzoekBericht notificatieAdresbepalendeOuderNietGevonden =
                (NotificatieVerzoekBericht) brpBerichtFactory.getBericht(brpBericht.getInhoud());

        Assert.assertEquals(NotificatieVerzoekBericht.class, notificatieAdresbepalendeOuderNietGevonden.getClass());
        Assert.assertEquals(String.format(BepaalAdresbepalendeOuderAction.TOELICHTING,
                BepaalAdresbepalendeOuderAction.REDEN_MEER_DAN_1_OUDER_ADRESGEVEND),
                notificatieAdresbepalendeOuderNietGevonden.getNotificatie());

        // antwoord
        final NotificatieAntwoordBericht notificatieAntwoordBericht = new NotificatieAntwoordBericht();
        notificatieAntwoordBericht.setStatus(StatusType.OK);

        final Bericht antwoord = new Bericht();
        antwoord.setMessageId(BerichtId.generateMessageId());
        antwoord.setInhoud(notificatieAntwoordBericht.format());
        antwoord.setCorrelatieId(brpBericht.getMessageId());
        verstuurBrpBericht(antwoord);
    }

    private ConverteerNaarLo3AntwoordBericht maakConverteerNaarLo3AntwoordBericht(final String messageId) {
        return new ConverteerNaarLo3AntwoordBericht(messageId, maakGeboorte(maakLo3PersoonInhoud()));
    }

    private static Lo3Persoonslijst maakGeboorte(final Lo3PersoonInhoud lo3PersoonInhoud) {
        final Lo3Historie historie = Lo3Builder.createLo3Historie("S", 20121101, 20121103);
        final Lo3Categorie<Lo3PersoonInhoud> persoon1 =
                new Lo3Categorie<Lo3PersoonInhoud>(lo3PersoonInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel = new Lo3Stapel<Lo3PersoonInhoud>(Arrays.asList(persoon1));
        final Lo3Stapel<Lo3OuderInhoud> ouder = VerplichteStapel.createOuderStapel();
        return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel).ouder1Stapel(ouder).ouder2Stapel(ouder)
                .build();
    }

	private Lo3VerwijzingInhoud buildLo3VerwijzingInhoud() {
        return new Lo3VerwijzingInhoud(2349326344L, // aNummer
                123456789L, // burgerservicenummer
                "Henk Jan", // voornamen
                null, // adellijkeTitelPredikaatCode
                "van", // voorvoegselGeslachtsnaam
                "Dalen", // geslachtsnaam
                new Lo3Datum(20121024), // geboortedatum
                new Lo3GemeenteCode("1234"), // geboorteGemeenteCode
                new Lo3LandCode(Lo3LandCode.CODE_NEDERLAND), // geboorteLandCode
                new Lo3GemeenteCode("1234"), // gemeenteInschrijving
                new Lo3Datum(20121025), // datumInschrijving
                "Lange poten", // straatnaam
                null, // naamOpenbareRuimte
                new Lo3Huisnummer(14), // huisnummer
                null, // huisletter
                null, // huisnummertoevoeging
                null, // aanduidingHuisnummer
                "2543WW", // postcode
                "Den Haag", // woonplaatsnaam
                null, // identificatiecodeVerblijfplaats
                null, // identificatiecodeNummeraanduiding
                null, // locatieBeschrijving
                Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement() // indicatieGeheimCode
        );
    }

	private List<Lo3CategorieWaarde> buildCat21ElementenLijst() {
		
		List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();
		Lo3CategorieWaarde cat21 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_21, 0, 0);

		cat21.addElement(Lo3ElementEnum.ELEMENT_0110, Lo3Format.format(2349326344L));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0120, Lo3Format.format(123456789L));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format("Henk Jan"));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0220, null);
		cat21.addElement(Lo3ElementEnum.ELEMENT_0230, Lo3Format.format("van"));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0240, Lo3Format.format("Dalen"));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0310, Lo3Format.format(new Lo3Datum(20121024)));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0320, Lo3Format.format(new Lo3GemeenteCode("1234")));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0330, Lo3Format.format(new Lo3LandCode(Lo3LandCode.CODE_NEDERLAND)));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0910, Lo3Format.format(new Lo3GemeenteCode("1234")));
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

    private static Lo3PersoonInhoud maakLo3PersoonInhoud() {
        final Long aNummer = 2349326344L;
        final Long burgerservicenummer = 123456789L;
        final String voornamen = "Henk Jan";
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = null;
        final String voorvoegselGeslachtsnaam = "van";
        final String geslachtsnaam = "Dalen";
        final Lo3Datum geboortedatum = new Lo3Datum(20121024);
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode("1234");
        final Lo3LandCode geboorteLandCode = new Lo3LandCode("6030");
        final Lo3Geslachtsaanduiding geslachtsaanduiding = new Lo3Geslachtsaanduiding("M");
        final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode = new Lo3AanduidingNaamgebruikCode("E");
        final Long vorigANummer = null;
        final Long volgendANummer = null;

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                        voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode,
                        geboorteLandCode, geslachtsaanduiding, aanduidingNaamgebruikCode, vorigANummer,
                        volgendANummer);
        return persoonInhoud;
    }
}
