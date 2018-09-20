/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc308;

import java.util.Arrays;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.bpm.AbstractJbpmTest;
import nl.moderniseringgba.isc.esb.message.BerichtId;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.impl.OverlijdenVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.NullBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02ErkenningBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02ErkenningNotarieelBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02GerechtelijkeVaststellingVaderschapBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02GeslachtsnaamwijzigingBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02OntkenningVaderschapBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02OntkenningVaderschapDoorMoederBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02OverlijdenBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02TransseksualiteitBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02VernietigingErkenningBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02VoornaamswijzigingBericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3VerzoekBericht;
import nl.moderniseringgba.isc.migratie.service.GemeenteService;
import nl.moderniseringgba.isc.migratie.service.Stelsel;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Builder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "classpath:/uc308-test-beans.xml" })
public class Uc308Test extends AbstractJbpmTest {

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Inject
    private GemeenteService gemeenteService;

    public Uc308Test() {
        super("/uc308/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    /**
     * Erkenning verzoek
     */

    @Test
    public void testHappyFlowErkenningVerzoek() throws Exception {

        // given
        final String origineel =
                IOUtils.toString(Uc308Test.class.getResourceAsStream("uc308_erkenningVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final Tb02Bericht antwoordBericht = doorloopProces(bericht);
        Assert.assertTrue(antwoordBericht instanceof Tb02ErkenningBericht);
    }

    /**
     * Erkenning notarieel verzoek
     */

    @Test
    public void testHappyFlowErkenningNotarieelVerzoek() throws Exception {

        // given
        final String origineel =
                IOUtils.toString(Uc308Test.class
                        .getResourceAsStream("uc308_erkenningNotarieelVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final Tb02Bericht antwoordBericht = doorloopProces(bericht);
        Assert.assertTrue(antwoordBericht instanceof Tb02ErkenningNotarieelBericht);
    }

    /**
     * Erkenning vernietiging verzoek
     */

    @Test
    public void testHappyFlowErkenningVernietigingVerzoek() throws Exception {

        // given
        final String origineel =
                IOUtils.toString(Uc308Test.class
                        .getResourceAsStream("uc308_erkenningVernietigingVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final Tb02Bericht antwoordBericht = doorloopProces(bericht);
        Assert.assertTrue(antwoordBericht instanceof Tb02VernietigingErkenningBericht);
    }

    /**
     * Ontkenning vaderschap verzoek
     */

    @Test
    public void testHappyFlowOntkenningVaderschapVerzoek() throws Exception {

        // given
        final String origineel =
                IOUtils.toString(Uc308Test.class
                        .getResourceAsStream("uc308_ontkenningVaderschapVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final Tb02Bericht antwoordBericht = doorloopProces(bericht);
        Assert.assertTrue(antwoordBericht instanceof Tb02OntkenningVaderschapBericht);
    }

    /**
     * Ontkenning vaderschap door moeder verzoek
     */

    @Test
    public void testHappyFlowOntkenningVaderschapDoorMoederVerzoek() throws Exception {

        // given
        final String origineel =
                IOUtils.toString(Uc308Test.class
                        .getResourceAsStream("uc308_ontkenningVaderschapDoorMoederVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final Tb02Bericht antwoordBericht = doorloopProces(bericht);
        Assert.assertTrue(antwoordBericht instanceof Tb02OntkenningVaderschapDoorMoederBericht);
    }

    /**
     * Geslachtsnaam wijziging
     */

    @Test
    public void testHappyFlowGeslachtsnaamWijzigingVerzoek() throws Exception {

        // given
        final String origineel =
                IOUtils.toString(Uc308Test.class
                        .getResourceAsStream("uc308_geslachtsnaamWijzigingVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final Tb02Bericht antwoordBericht = doorloopProces(bericht);
        Assert.assertTrue(antwoordBericht instanceof Tb02GeslachtsnaamwijzigingBericht);
    }

    /**
     * Voornaams wijziging
     */

    @Test
    public void testHappyFlowVoornaamsWijzigingVerzoek() throws Exception {

        // given
        final String origineel =
                IOUtils.toString(Uc308Test.class
                        .getResourceAsStream("uc308_voornaamsWijzigingVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final Tb02Bericht antwoordBericht = doorloopProces(bericht);
        Assert.assertTrue(antwoordBericht instanceof Tb02VoornaamswijzigingBericht);
    }

    /**
     * Transseksualiteit
     */

    @Test
    public void testHappyFlowTransseksualiteitVerzoek() throws Exception {

        // given
        final String origineel =
                IOUtils.toString(Uc308Test.class
                        .getResourceAsStream("uc308_transseksualiteitVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final Tb02Bericht antwoordBericht = doorloopProces(bericht);
        Assert.assertTrue(antwoordBericht instanceof Tb02TransseksualiteitBericht);
    }

    /**
     * Gerechtelijke vaststelling vaderschap
     */

    @Test
    public void testHappyFlowGerechtelijkeVaststellingVaderschapVerzoek() throws Exception {

        // given
        final String origineel =
                IOUtils.toString(Uc308Test.class
                        .getResourceAsStream("uc308_gerechtelijkeVaststellingVaderschapVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final Tb02Bericht antwoordBericht = doorloopProces(bericht);
        Assert.assertTrue(antwoordBericht instanceof Tb02GerechtelijkeVaststellingVaderschapBericht);
    }

    /**
     * Overlijden
     */

    @Test
    public void testHappyFlowOverlijdenVerzoek() throws Exception {

        // given
        final String origineel =
                IOUtils.toString(Uc308Test.class.getResourceAsStream("uc308_overlijdenVerzoekBericht_goed.xml"));
        final BrpBericht bericht = factory.getBericht(origineel);

        final Tb02Bericht antwoordBericht = doorloopProces(bericht);
        Assert.assertTrue(antwoordBericht instanceof Tb02OverlijdenBericht);
    }

    private Tb02Bericht doorloopProces(final BrpBericht bericht) throws Exception {

        final boolean persoonIsOverleden = bericht instanceof OverlijdenVerzoekBericht;

        // when
        Mockito.when(gemeenteService.geefStelselVoorGemeente(1234)).thenReturn(Stelsel.GBA);
        Mockito.when(gemeenteService.geefStelselVoorGemeente(5678)).thenReturn(Stelsel.BRP);

        // Start het process.
        startProcess(bericht);

        // Verwacht converteer naar Lo3 Verzoekbericht.
        checkBerichten(0, 0, 0, 1);
        final ConverteerNaarLo3VerzoekBericht converteerNaarLo3VerzoekBericht =
                getBericht(ConverteerNaarLo3VerzoekBericht.class);
        Thread.sleep(1000);

        // antwoord
        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3AntwoordBericht =
                new ConverteerNaarLo3AntwoordBericht(converteerNaarLo3VerzoekBericht.getMessageId(),
                        maakLo3Persoonslijst(persoonIsOverleden));
        converteerNaarLo3AntwoordBericht.setMessageId(BerichtId.generateMessageId());
        converteerNaarLo3AntwoordBericht.setStatus(StatusType.OK);
        // signalBrp(notificatieAntwoordBericht);
        signalSync(converteerNaarLo3AntwoordBericht);
        Thread.sleep(1000);

        checkBerichten(0, 0, 1, 0);

        // Tb02 Bericht in LO3.
        final Tb02Bericht tb02Bericht = getBericht(Tb02Bericht.class);

        final NullBericht nullBericht = new NullBericht(tb02Bericht.getMessageId());
        signalVospg(nullBericht);

        checkBerichten(1, 0, 0, 0);

        Assert.assertTrue(processEnded());

        return tb02Bericht;
    }

    private static Lo3Persoonslijst maakLo3Persoonslijst(final boolean persoonIsOverleden) {
        final Lo3PersoonInhoud lo3PersoonInhoud = maakLo3PersoonInhoud();
        final Lo3Historie historie = Lo3Builder.createLo3Historie("S", 20121101, 20121103);
        final Lo3Categorie<Lo3PersoonInhoud> persoon1 =
                new Lo3Categorie<Lo3PersoonInhoud>(lo3PersoonInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel = new Lo3Stapel<Lo3PersoonInhoud>(Arrays.asList(persoon1));
        final Lo3Stapel<Lo3OuderInhoud> ouder = VerplichteStapel.createOuderStapel();
        if (persoonIsOverleden) {
            final Lo3OverlijdenInhoud lo3OverlijdenInhoud = maakLo3OverlijdenInhoud();
            final Lo3Categorie<Lo3OverlijdenInhoud> overlijden =
                    new Lo3Categorie<Lo3OverlijdenInhoud>(lo3OverlijdenInhoud, null, historie, new Lo3Herkomst(
                            Lo3CategorieEnum.CATEGORIE_06, 0, 0));
            @SuppressWarnings("unchecked")
            final Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel =
                    new Lo3Stapel<Lo3OverlijdenInhoud>(Arrays.asList(overlijden));
            return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel).ouder1Stapel(ouder)
                    .ouder2Stapel(null).overlijdenStapel(overlijdenStapel).build();
        } else {
            return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel).ouder1Stapel(ouder)
                    .ouder2Stapel(null).build();
        }
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

    private static Lo3OverlijdenInhoud maakLo3OverlijdenInhoud() {
        final Lo3Datum overlijdensdatum = new Lo3Datum(20121024);
        final Lo3GemeenteCode overlijdensGemeenteCode = new Lo3GemeenteCode("1234");
        final Lo3LandCode overlijdensLandCode = new Lo3LandCode("6030");

        final Lo3OverlijdenInhoud overlijdenInhoud =
                new Lo3OverlijdenInhoud(overlijdensdatum, overlijdensGemeenteCode, overlijdensLandCode);
        return overlijdenInhoud;
    }
}
