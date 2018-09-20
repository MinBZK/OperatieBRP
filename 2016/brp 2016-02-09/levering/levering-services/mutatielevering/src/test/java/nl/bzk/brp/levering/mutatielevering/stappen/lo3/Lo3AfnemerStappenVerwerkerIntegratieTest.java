/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.lo3;

import java.util.Arrays;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerpImpl;
import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.mutatielevering.Lo3LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.mutatielevering.Lo3LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.mutatielevering.stappen.AfnemerStappenVerwerker;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import support.PersoonHisVolledigUtil;

public class Lo3AfnemerStappenVerwerkerIntegratieTest extends AbstractIntegratieTest {

    private static final String AFNEMER_34 = "AFNEMER-34";
    private static final String GEEN_BERICHT_ONTVANGEN = "Geen bericht ontvangen op queue";
    private static final String AFNEMER_XML_BERICHT = "afnemerXmlBericht";
    private static final String NAAM = "Naam";
    private static final String ID = "iD";
    private static final String ROTTERDAM = "Rotterdam";
    private static final String STRING_00038 = "00038";
    private static final String STRING_01 = "01";
    private static final String STRING_033 = "033";
    private static final String STRING_0110 = "0110";
    private static final String STRING_010 = "010";
    private static final String STRING_1234567890 = "1234567890";
    private static final String STRING_0120 = "0120";
    private static final String STRING_009 = "009";
    private static final String STRING_123456789 = "123456789";
    private static final String I = "I";
    private static final String STRING_518 = "518";
    private static final String A = "A";
    private static final String P = "P";
    private static final String STRING_6030 = "6030";

    @Inject
    @Named("lo3AfnemerStappenVerwerker")
    private AfnemerStappenVerwerker lo3AfnemerStappenVerwerker;

    @Inject
    private Lo3FilterRubriekRepository lo3FilterRubriekRepository;

    @Inject
    private JmsTemplate afnemersJmsTemplate;

    /**
     * Test Ag11: Vulbericht na automatisch plaatsen afnemersindicatie (attendering met plaatsen).
     */
    @Ignore("TODO: nog maken")
    @Test
    public void testAg11() {

    }

    /**
     * Test Ag21: Vulbericht op basis van sleutelrubriek (attendering zonder plaatsen).
     */
    @Test
    @Ignore("TODO")
    public void testAg21() throws ReflectiveOperationException, JMSException {
        // Input
        final LeveringautorisatieStappenOnderwerp onderwerp = maakOnderwerp(SoortDienst.ATTENDERING, false);
        final Lo3LeveringsautorisatieVerwerkingContext context = maakContext(SoortAdministratieveHandeling.CORRECTIE_NAAMGEBRUIK);

        // Execute
        lo3AfnemerStappenVerwerker.verwerk(onderwerp, context);

        // Verify
        final Message message = getBerichtVanQueue(AFNEMER_34);
        Assert.assertNotNull(GEEN_BERICHT_ONTVANGEN, message);
        final String bericht = message.getStringProperty(AFNEMER_XML_BERICHT);

        // Ag21: Conditionele levering bericht
        final String header = "00000000Ag21A00000000";
        final String berichtLengte = STRING_00038;
        // Categorie 01: Persoon
        final String categorie01 = STRING_01;
        final String categorie01lengte = STRING_033;
        // Element 01.10: A-nummer
        final String element0110 = STRING_0110;
        final String element0110lengte = STRING_010;
        final String element0110waarde = STRING_1234567890;
        // Element 01.20: BSN
        final String element0120 = STRING_0120;
        final String element0120lengte = STRING_009;
        final String element0120waarde = STRING_123456789;

        final String verwachtBericht =
                header
                                       + berichtLengte
                                       + categorie01
                                       + categorie01lengte
                                       + element0110
                                       + element0110lengte
                                       + element0110waarde
                                       + element0120
                                       + element0120lengte
                                       + element0120waarde;
        Assert.assertEquals(verwachtBericht, bericht);
    }

    /**
     * Test Ag31: Foutherstel; Bij een correctie (in het verleden) kan geen LO3 mutatie worden vertuurd, maar wordt een
     * foutherstel bericht verstuurd.
     */
    @Test
    @Ignore("TODO")
    public void testAg31() throws ReflectiveOperationException, JMSException {
        // Input
        final LeveringautorisatieStappenOnderwerp onderwerp = maakOnderwerp(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, false);
        final Lo3LeveringsautorisatieVerwerkingContext context = maakContext(SoortAdministratieveHandeling.CORRECTIE_NAAMGEBRUIK);

        // Execute
        lo3AfnemerStappenVerwerker.verwerk(onderwerp, context);

        // Verify
        final Message message = getBerichtVanQueue(AFNEMER_34);
        Assert.assertNotNull(GEEN_BERICHT_ONTVANGEN, message);
        final String bericht = message.getStringProperty(AFNEMER_XML_BERICHT);

        // Ag31: Foutherstel bericht
        final String header = "00000000Ag31A00000000";
        final String berichtLengte = STRING_00038;
        // Categorie 01: Persoon
        final String categorie01 = STRING_01;
        final String categorie01lengte = STRING_033;
        // Element 01.10: A-nummer
        final String element0110 = STRING_0110;
        final String element0110lengte = STRING_010;
        final String element0110waarde = STRING_1234567890;
        // Element 01.20: BSN
        final String element0120 = STRING_0120;
        final String element0120lengte = STRING_009;
        final String element0120waarde = STRING_123456789;

        final String verwachtBericht =
                header
                                       + berichtLengte
                                       + categorie01
                                       + categorie01lengte
                                       + element0110
                                       + element0110lengte
                                       + element0110waarde
                                       + element0120
                                       + element0120lengte
                                       + element0120waarde;
        Assert.assertEquals(verwachtBericht, bericht);
    }

    /**
     * Test Gv01: Mutatiebericht (mutatielevering obv afnemersindicatie).
     */
    @Test
    @Ignore("TODO")
    public void testGv01() throws ReflectiveOperationException, JMSException {
        // Input
        final LeveringautorisatieStappenOnderwerp onderwerp = maakOnderwerp(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, true);

        final Lo3LeveringsautorisatieVerwerkingContext context = maakContext(SoortAdministratieveHandeling.OVERLIJDEN_IN_NEDERLAND);

        // Execute
        lo3AfnemerStappenVerwerker.verwerk(onderwerp, context);

        // Verify
        final Message message = getBerichtVanQueue(AFNEMER_34);
        Assert.assertNotNull(GEEN_BERICHT_ONTVANGEN, message);
        final String bericht = message.getStringProperty(AFNEMER_XML_BERICHT);
        Assert.assertNotNull(bericht);
        Assert.assertEquals("Gv01", bericht.substring(8, 12));
    }

    /**
     * Test Gv01: Mutatiebericht agv infrastructurele wijziging (mutatielevering obv afnemersindicatie).
     *
     * @throws javax.jms.JMSException
     */
    @Test
    @Ignore("TODO")
    public void testGv02() throws ReflectiveOperationException, JMSException {
        // Input
        final LeveringautorisatieStappenOnderwerp onderwerp = maakOnderwerp(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, true);

        final Lo3LeveringsautorisatieVerwerkingContext context = maakContext(SoortAdministratieveHandeling.WIJZIGING_ADRES_INFRASTRUCTUREEL);

        // Execute
        lo3AfnemerStappenVerwerker.verwerk(onderwerp, context);

        // Verify
        final Message message = getBerichtVanQueue(AFNEMER_34);
        Assert.assertNotNull(GEEN_BERICHT_ONTVANGEN, message);
        final String bericht = message.getStringProperty(AFNEMER_XML_BERICHT);
        Assert.assertNotNull(bericht);
        Assert.assertEquals("Gv02", bericht.substring(8, 12));
    }

    /**
     * Test Ng01: Kennisgeving afvoeren PL (mutatielevering obv afnemersindicatie).
     */
    @Ignore("TODO")
    @Test
    public void testNg01() {
        // Nog geen administratieve handeling voor afvoeren PL (geen mapping in BerichtFactoryImpl in
        // lo3conversie)
    }

    /**
     * Test Wa11: Kennisgeving wijzigen a-nummer (mutatielevering obv afnemersindicatie).
     */
    @Ignore("TODO")
    @Test
    public void testWa11() {
        // Nog geen administratieve handeling voor wijzigen a-nummer (geen mapping in BerichtFactoryImpl in
        // lo3conversie)
    }

    private LeveringautorisatieStappenOnderwerp maakOnderwerp(final SoortDienst soortDienst, final boolean inclusiefOverlijden)
        throws ReflectiveOperationException
    {
        // // Maak abonnement
        // final Abonnement s =
        // ReflectionUtils.instantiate(
        // Abonnement.class,
        // new NaamEnumeratiewaardeAttribuut(NAAM),
        // null,
        // Protocolleringsniveau.GEEN_BEPERKINGEN,
        // null,
        // new DatumEvtDeelsOnbekendAttribuut(20000101),
        // null,
        // Toestand.DEFINITIEF,
        // new JaNeeAttribuut(true),
        // null);
        // ReflectionUtils.setField(abonnement, ID, 42);
        //
        // final List<ExpressietekstAttribuut> aboFilterExpressies = new ArrayList<>();
        // aboFilterExpressies.add(new ExpressietekstAttribuut("01.01.10"));
        // aboFilterExpressies.add(new ExpressietekstAttribuut("01.01.20"));
        // if (inclusiefOverlijden) {
        // aboFilterExpressies.add(new ExpressietekstAttribuut("06.08.10"));
        // aboFilterExpressies.add(new ExpressietekstAttribuut("06.08.20"));
        // aboFilterExpressies.add(new ExpressietekstAttribuut("06.08.30"));
        // }

        final Leveringsautorisatie leveringsautorisatie =
                TestLeveringsautorisatieBuilder.maker()
                                               .metNaam("TestLeveringsautorisatie")
                                               .metPopulatiebeperking("WAAR")
                                               .metProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN)
                                               .metDatumIngang(DatumAttribuut.gisteren())
                                               .maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie =
                TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(leveringsautorisatie).maak();

        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(soortDienst).maak();

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(toegangLeveringsautorisatie, dienst);
        return new LeveringautorisatieStappenOnderwerpImpl(leveringAutorisatie, 42L, Stelsel.GBA);
    }

    private Lo3LeveringsautorisatieVerwerkingContext maakContext(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        final Partij partij = PersoonHisVolledigUtil.maakPartij();

        final ActieModel actieGeboorte =
                PersoonHisVolledigUtil.maakActie(
                    1L,
                    SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND,
                    SoortActie.REGISTRATIE_GEBOORTE,
                    19200101,
                    partij);
        final ActieModel actieVerhuizen =
                PersoonHisVolledigUtil.maakActie(
                    2L,
                    SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK,
                    SoortActie.REGISTRATIE_ADRES,
                    19470307,
                    partij);
        final ActieModel actieOverlijden =
                PersoonHisVolledigUtil.maakActie(3L, soortAdministratieveHandeling, SoortActie.REGISTRATIE_OVERLIJDEN, 20140711, partij);
        /* SoortAdministratieveHandeling.OVERLIJDEN_IN_NEDERLAND */

        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        voegGeboorteToe(builder, actieGeboorte);
        voegVerhuizingToe(builder, actieVerhuizen);
        voegOverlijdenToe(builder, actieOverlijden);

        final PersoonHisVolledig persoon = builder.build();
        PersoonHisVolledigUtil.maakVerantwoording(persoon, actieGeboorte, actieVerhuizen, actieOverlijden);
        return new Lo3LeveringsautorisatieVerwerkingContextImpl(actieOverlijden.getAdministratieveHandeling(), Arrays.asList(persoon), null, new ConversieCache());

    }

    private void voegGeboorteToe(final PersoonHisVolledigImplBuilder builder, final ActieModel actieGeboorte) {
        // Geboorte
        builder.voegPersoonAdresToe(
            new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(actieGeboorte)
                                                    .aangeverAdreshouding(I)
                                                    .datumAanvangAdreshouding(actieGeboorte.getDatumAanvangGeldigheid())
                                                    .gemeente(new Short(STRING_518))
                                                    .gemeentedeel("deel vd gemeente")
                                                    .huisletter(A)
                                                    .huisnummer(10)
                                                    .huisnummertoevoeging(A)
                                                    .landGebied((short) 6030)
                                                    .naamOpenbareRuimte(NAAM)
                                                    .postcode("2245HJ")
                                                    .redenWijziging(P)
                                                    .soort(FunctieAdres.WOONADRES)
                                                    .woonplaatsnaam(ROTTERDAM)
                                                    .eindeRecord()
                                                    .build());
        builder.nieuwGeboorteRecord(actieGeboorte)
               .datumGeboorte(actieGeboorte.getDatumAanvangGeldigheid())
               .gemeenteGeboorte(new Short(STRING_518))
               .landGebiedGeboorte(new Short(STRING_6030))
               .eindeRecord();
        builder.nieuwGeslachtsaanduidingRecord(actieGeboorte).geslachtsaanduiding(Geslachtsaanduiding.MAN).eindeRecord();
        builder.nieuwIdentificatienummersRecord(actieGeboorte).administratienummer(1234567890L).burgerservicenummer(123456789).eindeRecord();
        builder.nieuwInschrijvingRecord(actieGeboorte)
               .datumInschrijving(actieGeboorte.getDatumAanvangGeldigheid())
               .versienummer(1L)
               .datumtijdstempel(new Date(123))
               .eindeRecord();
        builder.nieuwNaamgebruikRecord(actieGeboorte).indicatieNaamgebruikAfgeleid(true).naamgebruik(Naamgebruik.EIGEN).eindeRecord();
        builder.nieuwAfgeleidAdministratiefRecord(actieGeboorte)
               .indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(false)
               .tijdstipLaatsteWijziging(actieGeboorte.getTijdstipRegistratie())
               .tijdstipLaatsteWijzigingGBASystematiek(actieGeboorte.getTijdstipRegistratie())
               .eindeRecord();
        builder.nieuwSamengesteldeNaamRecord(actieGeboorte)
               .geslachtsnaamstam("geslachtsnaam")
               .voorvoegsel("de")
               .voornamen("Voornaam1 Voornaam2")
               .scheidingsteken(" ")
               .eindeRecord();
    }

    private void voegVerhuizingToe(final PersoonHisVolledigImplBuilder builder, final ActieModel actieVerhuizing) {
        final String hisVolledigImplVeldNaam = "hisVolledigImpl";
        final PersoonHisVolledigImpl hisVolledigImpl = (PersoonHisVolledigImpl) ReflectionTestUtils.getField(builder, hisVolledigImplVeldNaam);

        final PersoonAdresHisVolledigImpl adresHisVolledigImpl = hisVolledigImpl.getAdressen().iterator().next();

        final PersoonAdresHisVolledigImplBuilder adresBuilder = new PersoonAdresHisVolledigImplBuilder();
        ReflectionTestUtils.setField(adresBuilder, hisVolledigImplVeldNaam, adresHisVolledigImpl);

        adresBuilder.nieuwStandaardRecord(actieVerhuizing)
                    .aangeverAdreshouding(I)
                    .datumAanvangAdreshouding(actieVerhuizing.getDatumAanvangGeldigheid())
                    .gemeente(new Short(STRING_518))
                    .huisnummer(46)
                    .landGebied((short) 6030)
                    .naamOpenbareRuimte("Pippelingstraat")
                    .postcode("2522HT")
                    .redenWijziging(P)
                    .soort(FunctieAdres.WOONADRES)
                    .woonplaatsnaam(ROTTERDAM)
                    .eindeRecord();
    }

    private void voegOverlijdenToe(final PersoonHisVolledigImplBuilder builder, final ActieModel actieOverlijden) {
        // Overlijden
        builder.nieuwOverlijdenRecord(actieOverlijden)
               .datumOverlijden(actieOverlijden.getDatumAanvangGeldigheid())
               .gemeenteOverlijden(new Short(STRING_518))
               .landGebiedOverlijden(new Short(STRING_6030))
               .eindeRecord();
        builder.nieuwAfgeleidAdministratiefRecord(actieOverlijden)
               .tijdstipLaatsteWijziging(actieOverlijden.getTijdstipRegistratie())
               .tijdstipLaatsteWijzigingGBASystematiek(actieOverlijden.getTijdstipRegistratie())
               .eindeRecord();
    }

    private Message getBerichtVanQueue(final String queueNaam) {
        afnemersJmsTemplate.setReceiveTimeout(10000);
        return afnemersJmsTemplate.receive(queueNaam);
    }

}
