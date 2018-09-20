/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.isc.esb.bpm.AbstractJbpmTest;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.brp.generated.FoutCode;
import nl.moderniseringgba.isc.esb.message.brp.generated.GevondenPersonenType;
import nl.moderniseringgba.isc.esb.message.brp.generated.GevondenPersoonType;
import nl.moderniseringgba.isc.esb.message.brp.generated.MvGeboorteAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.generated.VerhuizingAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.generated.ZoekPersoonAntwoordType;
import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.MvGeboorteAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.MvGeboorteVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.NotificatieAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.NotificatieVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.impl.NullBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf03Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf01Bericht.Foutreden;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf11Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tv01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Vb01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.sync.SyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.AntwoordFormaatType;
import nl.moderniseringgba.isc.esb.message.sync.generated.ConverteerNaarBrpAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.SearchResultaatType;
import nl.moderniseringgba.isc.esb.message.sync.generated.SynchronisatieStrategieAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchronisatieStrategieAntwoordBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "classpath:/uc307-test-beans.xml" })
public class Uc307Test extends AbstractJbpmTest {

    /**
     * LO3-factory voor het converteren van teletext naar Lo3-bericht.
     */
    private static final Lo3BerichtFactory LO3_FACTORY = new Lo3BerichtFactory();

    /**
     * LO3 persoonslijst als teletext string.
     */
    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    /**
     * Doelgemeente voor de test.
     */
    private static final String DOELGEMEENTE = "5678";

    /**
     * Brongemeente voor de test.
     */
    private static final String BRONGEMEENTE = "1234";

    public Uc307Test() {
        super("/uc307/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @Test
    public void testHappyFlow() throws Exception {
        performHappyFlowProcess(false);

    }

    @Test
    public void testAlternatieveHappyFlow() throws Exception {
        performHappyFlowProcess(true);

    }

    /**
     * Deze test start met een Tb01Bericht waarbij de GEZOCHTE_PERSOON header de (V) vader aangeeft Dit moet resulteren
     * in een Incorrect afgerond process, met een Pf03Bericht en een Vb01Bericht richting de bron Lo3 gemeente
     * 
     * @throws Exception
     */
    @Test
    public void testAlternatiefPadKanNietInschrijvenOpMoederGezochtePersoonV() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01BerichtMetGezochtePersoonV();

        // Start het proces.
        startProcess(tb01Bericht);

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadKanNietInschrijvenOpMoederGeenMoeder() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01BerichtZonderMoeder();

        // Start het proces.
        startProcess(tb01Bericht);

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadKanNietInschrijvenOpMoederTweeVrouwen() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01BerichtTweeVrouwen();

        // Start het proces.
        startProcess(tb01Bericht);

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadOnverwachtBerichtConversieKindPL() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);
        Assert.assertNotNull(converteerNaarBrpVerzoekKindPL);

        // Verstuur 'Onverwacht' bericht naar proces.
        final SynchronisatieStrategieAntwoordType antwoordType = new SynchronisatieStrategieAntwoordType();
        antwoordType.setResultaat(SearchResultaatType.NEGEREN);
        antwoordType.setStatus(nl.moderniseringgba.isc.esb.message.sync.generated.StatusType.FOUT);
        antwoordType.setToelichting("bla");
        final SyncBericht onverwachtBericht = new SynchronisatieStrategieAntwoordBericht(antwoordType);

        signalSync(onverwachtBericht);

        // Onverwacht bericht --> GEEN beheerder
        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    // TODO: moet deze terug naar OpnieuwAanbiedenTB01? of OpnieuwMaakConverteerBerichtVoorPLKind?
    @Test
    public void testAlternatiefPadTimeoutConverteerKindPLBeheerderMaakConverteerBerichtVoorPLKind() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht initieelConverteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);
        Assert.assertNotNull(initieelConverteerNaarBrpVerzoekKindPL);

        // simuleer timeout na x herhalingen
        forceerTimeout(ConverteerNaarBrpVerzoekBericht.class, "SYNC");
        signalHumanTask("restartAtConverteerPLKind");

        // vervolg 'gewone' flow
        final ConverteerNaarBrpVerzoekBericht tweedeConverteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);
        Assert.assertNotNull(tweedeConverteerNaarBrpVerzoekKindPL);

        // negeer de rest van het process, wordt in andere tests getest.
    }

    @Test
    public void testAlternatiefPadConverteerKindPLControleerAntwoordFoutAfbrekenConversie() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);
        Assert.assertNotNull(converteerNaarBrpVerzoekKindPL);

        final ConverteerNaarBrpAntwoordType converteerAntwoordType = new ConverteerNaarBrpAntwoordType();
        converteerAntwoordType.setStatus(nl.moderniseringgba.isc.esb.message.sync.generated.StatusType.FOUT);
        converteerAntwoordType.setFoutmelding("Er is iets fout gegaan...");

        final ConverteerNaarBrpAntwoordBericht antwoordMetStatusFOUT =
                new ConverteerNaarBrpAntwoordBericht(converteerAntwoordType);
        signalSync(antwoordMetStatusFOUT);

        signalHumanTask("end");

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadTimeoutConverteerKindPLBeheerderAfbrekenConversie() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht initieelConverteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        Assert.assertNotNull(initieelConverteerNaarBrpVerzoekKindPL);

        // simuleer timeout na x herhalingen
        forceerTimeout(ConverteerNaarBrpVerzoekBericht.class, "SYNC");
        signalHumanTask("end");

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadTimeoutOntvangAntwoordOpTv01BeheerderEnd() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekPersoonBericht = getZoekPersoonVerzoekBericht();

        // Verzend: antwoord op zoek persoon binnen gemeente bericht
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht =
                maakZoekPersoonAntwoordBericht(zoekPersoonBericht);
        signalBrp(zoekPersoonAntwoordBericht);

        // expect LeesUitBrpVerzoekBericht, om PL van moeder op te halen
        checkBerichten(0, 0, 0, 1);

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBerichtMoeder =
                getLeesUitBrpVerzoekBericht(zoekPersoonAntwoordBericht);

        // Verzend LeesUitBrpAntwoordBericht
        signalSync(maakLeesUitBrpAntwoordBericht(leesUitBrpVerzoekBerichtMoeder, AntwoordFormaatType.BRP, false));

        // expect MVGeboorteBericht
        checkBerichten(1, 0, 0, 0);

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = getMvGeboortVerzoekBericht();

        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht =
                maakMvGeboorteAntwoordBericht(mvGeboorteVerzoekBericht);
        signalBrp(mvGeboorteAntwoordBericht);

        // expect LeesUitBrpVerzoekBericht, om PL van het kind op te halen
        checkBerichten(0, 0, 0, 1);

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht =
                getLeesUitBrpVerzoekBericht(mvGeboorteAntwoordBericht);

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                maakLeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht, AntwoordFormaatType.LO_3, true);
        signalSync(leesUitBrpAntwoordBericht);

        checkBerichten(0, 0, 1, 0);

        final Tv01Bericht initieelTv01Bericht = getBericht(Tv01Bericht.class);
        Assert.assertNotNull(initieelTv01Bericht);

        // simuleer timeout na x herhalingen
        forceerTimeout(Tv01Bericht.class, "VOSPG");
        signalHumanTask("end");

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadTimoutZoekMoederBinnenGemeenteAfbrekenCyclus() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekPersoonBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekPersoonBericht);

        forceerTimeout(ZoekPersoonVerzoekBericht.class, "BRP");
        signalHumanTask("end");

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadTimoutZoekMoederBinnenGemeenteNogmaalsZoeken() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht eersteZoekPersoonBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(eersteZoekPersoonBericht);

        forceerTimeout(ZoekPersoonVerzoekBericht.class, "BRP");
        signalHumanTask("restartAtZoekMoederInBrp");

        final ZoekPersoonVerzoekBericht tweedeZoekPersoonBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(tweedeZoekPersoonBericht);

        // negeer de rest van het process, wordt in andere tests getest.
    }

    @Test
    public void testAlternatiefPadZoekMoederBinnenGemeenteFoutAfbrekenCyclus() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekPersoonBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekPersoonBericht);

        final ZoekPersoonAntwoordType antwoordType = new ZoekPersoonAntwoordType();
        antwoordType.setStatus(StatusType.FOUT);
        antwoordType.setToelichting("bla");
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordFOUT = new ZoekPersoonAntwoordBericht(antwoordType);
        signalBrp(zoekPersoonAntwoordFOUT);

        signalHumanTask("end");

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadZoekMoederBinnenGemeenteNietsGevondenTimeoutZoekenBuitenGemeenteAfbrekenCyclus()
            throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekMoederBinnenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBinnenGemeenteBericht);

        final ZoekPersoonAntwoordType antwoordType = new ZoekPersoonAntwoordType();
        antwoordType.setStatus(StatusType.OK);
        antwoordType.setGevondenPersonen(new GevondenPersonenType());
        antwoordType.setToelichting("Niets gevonden");
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordNietsGevonden =
                new ZoekPersoonAntwoordBericht(antwoordType);
        signalBrp(zoekPersoonAntwoordNietsGevonden);

        final ZoekPersoonVerzoekBericht zoekMoederBuitenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBuitenGemeenteBericht);

        // simuleer timeout na x herhalingen
        forceerTimeout(ZoekPersoonVerzoekBericht.class, "BRP");
        signalHumanTask("end");

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadZoekMoederBinnenGemeenteNietsGevondenTimeoutZoekenBuitenGemeenteNogmaalsZoeken()
            throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekMoederBinnenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBinnenGemeenteBericht);

        final ZoekPersoonAntwoordType antwoordType = new ZoekPersoonAntwoordType();
        antwoordType.setStatus(StatusType.OK);
        antwoordType.setGevondenPersonen(new GevondenPersonenType());
        antwoordType.setToelichting("Niets gevonden");
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordNietsGevonden =
                new ZoekPersoonAntwoordBericht(antwoordType);
        signalBrp(zoekPersoonAntwoordNietsGevonden);

        final ZoekPersoonVerzoekBericht zoekMoederBuitenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBuitenGemeenteBericht);

        // simuleer timeout na x herhalingen
        forceerTimeout(ZoekPersoonVerzoekBericht.class, "BRP");
        signalHumanTask("restartAtZoekMoederInBrp");

        final ZoekPersoonVerzoekBericht tweedeZoekPersoonBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(tweedeZoekPersoonBericht);

        // negeer de rest van het process, wordt in andere tests getest.
    }

    @Test
    public void testAlternatiefPadZoekMoederBinnenGemeenteOnverwachtBerichtCyclusAfbreken() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekMoederBinnenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBinnenGemeenteBericht);

        // Verstuur 'Onverwacht' bericht naar proces.
        final VerhuizingAntwoordType antwoordType = new VerhuizingAntwoordType();
        antwoordType.setStatus(StatusType.FOUT);
        antwoordType.setToelichting("bla");
        final VerhuizingAntwoordBericht onverwachtBericht = new VerhuizingAntwoordBericht(antwoordType);

        signalBrp(onverwachtBericht);

        // Onverwacht bericht --> GEEN beheerder
        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadZoekMoederBinnenGemeenteNietsGevondenZoekenBuitenGemeenteFoutAfbrekenCyclus()
            throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekMoederBinnenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBinnenGemeenteBericht);

        final ZoekPersoonAntwoordType antwoordTypeBinnenGemeente = new ZoekPersoonAntwoordType();
        antwoordTypeBinnenGemeente.setStatus(StatusType.OK);
        antwoordTypeBinnenGemeente.setGevondenPersonen(new GevondenPersonenType());
        antwoordTypeBinnenGemeente.setToelichting("Niets gevonden");
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordNietsGevonden =
                new ZoekPersoonAntwoordBericht(antwoordTypeBinnenGemeente);
        signalBrp(zoekPersoonAntwoordNietsGevonden);

        final ZoekPersoonVerzoekBericht zoekMoederBuitenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBuitenGemeenteBericht);

        final ZoekPersoonAntwoordType antwoordTypeBuitenGemeente = new ZoekPersoonAntwoordType();
        antwoordTypeBuitenGemeente.setStatus(StatusType.FOUT);
        antwoordTypeBuitenGemeente.setToelichting("FOUT");
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordFOUT =
                new ZoekPersoonAntwoordBericht(antwoordTypeBuitenGemeente);
        signalBrp(zoekPersoonAntwoordFOUT);

        signalHumanTask("end");

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadZoekMoederBinnenGemeenteNietsGevondenZoekenBuitenGemeenteOnverwachtBericht()
            throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekMoederBinnenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBinnenGemeenteBericht);

        final ZoekPersoonAntwoordType antwoordTypeBinnenGemeente = new ZoekPersoonAntwoordType();
        antwoordTypeBinnenGemeente.setStatus(StatusType.OK);
        antwoordTypeBinnenGemeente.setGevondenPersonen(new GevondenPersonenType());
        antwoordTypeBinnenGemeente.setToelichting("Niets gevonden");
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordNietsGevonden =
                new ZoekPersoonAntwoordBericht(antwoordTypeBinnenGemeente);
        signalBrp(zoekPersoonAntwoordNietsGevonden);

        final ZoekPersoonVerzoekBericht zoekMoederBuitenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBuitenGemeenteBericht);

        // Verstuur 'Onverwacht' bericht naar proces.
        final VerhuizingAntwoordType antwoordType = new VerhuizingAntwoordType();
        antwoordType.setStatus(StatusType.FOUT);
        antwoordType.setToelichting("bla");
        final VerhuizingAntwoordBericht onverwachtBericht = new VerhuizingAntwoordBericht(antwoordType);

        signalBrp(onverwachtBericht);

        // Onverwacht bericht --> GEEN beheerder
        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadZoekMoederBinnenGemeenteNietsGevondenZoekenBuitenGemeenteTf01V() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekMoederBinnenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBinnenGemeenteBericht);

        final ZoekPersoonAntwoordType antwoordTypeBinnenGemeente = new ZoekPersoonAntwoordType();
        antwoordTypeBinnenGemeente.setStatus(StatusType.OK);
        antwoordTypeBinnenGemeente.setGevondenPersonen(new GevondenPersonenType());
        antwoordTypeBinnenGemeente.setToelichting("Niets gevonden");
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordNietsGevonden =
                new ZoekPersoonAntwoordBericht(antwoordTypeBinnenGemeente);
        signalBrp(zoekPersoonAntwoordNietsGevonden);

        final ZoekPersoonVerzoekBericht zoekMoederBuitenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBuitenGemeenteBericht);

        final ZoekPersoonAntwoordType antwoordTypeBuitenGemeente = new ZoekPersoonAntwoordType();
        antwoordTypeBuitenGemeente.setStatus(StatusType.OK);
        final GevondenPersonenType gevondenPersonen = new GevondenPersonenType();
        final GevondenPersoonType gevondenPersoon = new GevondenPersoonType();
        gevondenPersoon.setANummer("12345678");
        gevondenPersoon.setBijhoudingsgemeente(DOELGEMEENTE);
        gevondenPersonen.getGevondenPersoon().add(gevondenPersoon);
        antwoordTypeBuitenGemeente.setGevondenPersonen(gevondenPersonen);

        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBuitenGemeenteGevonden =
                new ZoekPersoonAntwoordBericht(antwoordTypeBuitenGemeente);
        signalBrp(zoekPersoonAntwoordBuitenGemeenteGevonden);

        // verwacht Tf01 code 'V'
        checkBerichten(0, 0, 1, 0);

        final Tf01Bericht tf01Bericht = getBericht(Tf01Bericht.class);
        Assert.assertNotNull(tf01Bericht);

        Assert.assertEquals(tb01Bericht.getDoelGemeente(), tf01Bericht.getBronGemeente());
        Assert.assertEquals(tb01Bericht.getBronGemeente(), tf01Bericht.getDoelGemeente());
        Assert.assertEquals(Foutreden.V, tf01Bericht.getFoutreden());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadZoekMoederBinnenGemeenteNietsGevondenZoekenBuitenGemeenteMoederTf01G()
            throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekMoederBinnenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBinnenGemeenteBericht);

        final ZoekPersoonAntwoordType antwoordTypeBinnenGemeente = new ZoekPersoonAntwoordType();
        antwoordTypeBinnenGemeente.setStatus(StatusType.OK);
        antwoordTypeBinnenGemeente.setGevondenPersonen(new GevondenPersonenType());
        antwoordTypeBinnenGemeente.setToelichting("Niets gevonden");
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordNietsGevonden =
                new ZoekPersoonAntwoordBericht(antwoordTypeBinnenGemeente);
        signalBrp(zoekPersoonAntwoordNietsGevonden);

        final ZoekPersoonVerzoekBericht zoekMoederBuitenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBuitenGemeenteBericht);

        final ZoekPersoonAntwoordType antwoordTypeBuitenGemeente = new ZoekPersoonAntwoordType();
        antwoordTypeBuitenGemeente.setStatus(StatusType.OK);
        // NIETS GEVONDEN, dus geen 'gevondenPersoon' toevoegen

        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBuitenGemeenteGevonden =
                new ZoekPersoonAntwoordBericht(antwoordTypeBuitenGemeente);
        signalBrp(zoekPersoonAntwoordBuitenGemeenteGevonden);

        // verwacht Tf01 code 'G'
        checkBerichten(0, 0, 1, 0);

        final Tf01Bericht tf01Bericht = getBericht(Tf01Bericht.class);
        Assert.assertNotNull(tf01Bericht);

        Assert.assertEquals(tb01Bericht.getDoelGemeente(), tf01Bericht.getBronGemeente());
        Assert.assertEquals(tb01Bericht.getBronGemeente(), tf01Bericht.getDoelGemeente());
        Assert.assertEquals(Foutreden.G, tf01Bericht.getFoutreden());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadZoekMoederBinnenGemeenteNietsGevondenZoekenBuitenGemeenteTF01U() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekMoederBinnenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBinnenGemeenteBericht);

        final ZoekPersoonAntwoordType antwoordTypeBinnenGemeente = new ZoekPersoonAntwoordType();
        antwoordTypeBinnenGemeente.setStatus(StatusType.OK);
        antwoordTypeBinnenGemeente.setGevondenPersonen(new GevondenPersonenType());
        antwoordTypeBinnenGemeente.setToelichting("Niets gevonden");
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordNietsGevonden =
                new ZoekPersoonAntwoordBericht(antwoordTypeBinnenGemeente);
        signalBrp(zoekPersoonAntwoordNietsGevonden);

        final ZoekPersoonVerzoekBericht zoekMoederBuitenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBuitenGemeenteBericht);

        final ZoekPersoonAntwoordType antwoordTypeBuitenGemeente = new ZoekPersoonAntwoordType();
        antwoordTypeBuitenGemeente.setStatus(StatusType.OK);

        final GevondenPersonenType gevondenPersonen = new GevondenPersonenType();
        final GevondenPersoonType gevondenPersoon1 = new GevondenPersoonType();
        gevondenPersoon1.setANummer("12345678");
        gevondenPersoon1.setBijhoudingsgemeente(DOELGEMEENTE);
        gevondenPersonen.getGevondenPersoon().add(gevondenPersoon1);
        final GevondenPersoonType gevondenPersoon2 = new GevondenPersoonType();
        gevondenPersoon2.setANummer("87654321");
        gevondenPersoon2.setBijhoudingsgemeente(DOELGEMEENTE);
        gevondenPersonen.getGevondenPersoon().add(gevondenPersoon2);
        antwoordTypeBuitenGemeente.setGevondenPersonen(gevondenPersonen);

        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBuitenGemeenteGevonden =
                new ZoekPersoonAntwoordBericht(antwoordTypeBuitenGemeente);
        signalBrp(zoekPersoonAntwoordBuitenGemeenteGevonden);

        // verwacht Tf01 code 'U'
        checkBerichten(0, 0, 1, 0);

        final Tf01Bericht tf01Bericht = getBericht(Tf01Bericht.class);
        Assert.assertNotNull(tf01Bericht);

        Assert.assertEquals(tb01Bericht.getDoelGemeente(), tf01Bericht.getBronGemeente());
        Assert.assertEquals(tb01Bericht.getBronGemeente(), tf01Bericht.getDoelGemeente());
        Assert.assertEquals(Foutreden.U, tf01Bericht.getFoutreden());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadZoekMoederBinnenGemeenteTf01U() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekMoederBinnenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBinnenGemeenteBericht);

        final ZoekPersoonAntwoordType antwoordTypeBinnenGemeente = new ZoekPersoonAntwoordType();
        antwoordTypeBinnenGemeente.setStatus(StatusType.OK);

        antwoordTypeBinnenGemeente.setGevondenPersonen(new GevondenPersonenType());
        antwoordTypeBinnenGemeente.setToelichting("Niet uniek");

        final GevondenPersonenType gevondenPersonen = new GevondenPersonenType();
        final GevondenPersoonType gevondenPersoon1 = new GevondenPersoonType();
        gevondenPersoon1.setANummer("12345678");
        gevondenPersoon1.setBijhoudingsgemeente(DOELGEMEENTE);
        gevondenPersonen.getGevondenPersoon().add(gevondenPersoon1);
        final GevondenPersoonType gevondenPersoon2 = new GevondenPersoonType();
        gevondenPersoon2.setANummer("87654321");
        gevondenPersoon2.setBijhoudingsgemeente(DOELGEMEENTE);
        gevondenPersonen.getGevondenPersoon().add(gevondenPersoon2);
        antwoordTypeBinnenGemeente.setGevondenPersonen(gevondenPersonen);

        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordNietUniek =
                new ZoekPersoonAntwoordBericht(antwoordTypeBinnenGemeente);
        signalBrp(zoekPersoonAntwoordNietUniek);
        // verwacht Tf01 code 'U'
        checkBerichten(0, 0, 1, 0);

        final Tf01Bericht tf01Bericht = getBericht(Tf01Bericht.class);
        Assert.assertNotNull(tf01Bericht);

        Assert.assertEquals(tb01Bericht.getDoelGemeente(), tf01Bericht.getBronGemeente());
        Assert.assertEquals(tb01Bericht.getBronGemeente(), tf01Bericht.getDoelGemeente());
        Assert.assertEquals(Foutreden.U, tf01Bericht.getFoutreden());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadLeesMoederPLUitBrpTimeoutAfbrekenCyclus() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekMoederBinnenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBinnenGemeenteBericht);

        // Verzend: antwoord op zoek persoon binnen gemeente bericht
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht =
                maakZoekPersoonAntwoordBericht(zoekMoederBinnenGemeenteBericht);
        signalBrp(zoekPersoonAntwoordBericht);

        // expect LeesUitBrpVerzoekBericht, om PL van moeder op te halen
        checkBerichten(0, 0, 0, 1);

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBerichtMoeder =
                getLeesUitBrpVerzoekBericht(zoekPersoonAntwoordBericht);
        Assert.assertNotNull(leesUitBrpVerzoekBerichtMoeder);

        // simuleer timeout na x herhalingen
        forceerTimeout(LeesUitBrpVerzoekBericht.class, "SYNC");
        signalHumanTask("end");

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadLeesMoederPLUitBrpOnverwachtBerichtAfbrekenCyclus() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekMoederBinnenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBinnenGemeenteBericht);

        // Verzend: antwoord op zoek persoon binnen gemeente bericht
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht =
                maakZoekPersoonAntwoordBericht(zoekMoederBinnenGemeenteBericht);
        signalBrp(zoekPersoonAntwoordBericht);

        // expect LeesUitBrpVerzoekBericht, om PL van moeder op te halen
        checkBerichten(0, 0, 0, 1);

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBerichtMoeder =
                getLeesUitBrpVerzoekBericht(zoekPersoonAntwoordBericht);
        Assert.assertNotNull(leesUitBrpVerzoekBerichtMoeder);

        // Verstuur 'Onverwacht' bericht naar proces.
        final SynchronisatieStrategieAntwoordType antwoordType = new SynchronisatieStrategieAntwoordType();
        antwoordType.setResultaat(SearchResultaatType.NEGEREN);
        antwoordType.setStatus(nl.moderniseringgba.isc.esb.message.sync.generated.StatusType.FOUT);
        antwoordType.setToelichting("bla");
        final SyncBericht onverwachtBericht = new SynchronisatieStrategieAntwoordBericht(antwoordType);

        signalSync(onverwachtBericht);

        // Onverwacht bericht --> GEEN beheerder

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadLeesMoederPLUitBrpFOUTAfbrekenCyclus() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekMoederBinnenGemeenteBericht = getZoekPersoonVerzoekBericht();
        Assert.assertNotNull(zoekMoederBinnenGemeenteBericht);

        // Verzend: antwoord op zoek persoon binnen gemeente bericht
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht =
                maakZoekPersoonAntwoordBericht(zoekMoederBinnenGemeenteBericht);
        signalBrp(zoekPersoonAntwoordBericht);

        // expect LeesUitBrpVerzoekBericht, om PL van moeder op te halen
        checkBerichten(0, 0, 0, 1);

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBerichtMoeder =
                getLeesUitBrpVerzoekBericht(zoekPersoonAntwoordBericht);

        // Verzend LeesUitBrpAntwoordBericht
        signalSync(maakFOUTLeesUitBrpAntwoordBericht(leesUitBrpVerzoekBerichtMoeder));

        // signalHumanTask("end");

        // verwacht notificatie richting BRP
        checkBerichten(1, 0, 0, 0);
        final NotificatieVerzoekBericht notificatieVerzoek = getBericht(NotificatieVerzoekBericht.class);
        Assert.assertNotNull(notificatieVerzoek);

        final NotificatieAntwoordBericht notificatieAntwoord = new NotificatieAntwoordBericht();
        signalBrp(notificatieAntwoord);

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadMVGeboorteBerichtTimeoutAfbrekenCyclus() throws Exception {
        LOG.info("start testHappyFlow");
        final Tb01Bericht tb01Bericht = createTb01Bericht();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = happyFlowTotMvGeboorteAntwoordBericht(tb01Bericht);
        Assert.assertNotNull(mvGeboorteVerzoekBericht);

        forceerTimeout(MvGeboorteVerzoekBericht.class, "BRP");
        signalHumanTask("end");
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadMVGeboorteBerichtOnverwachtBerichtAfbrekenCyclus() throws Exception {
        LOG.info("start testHappyFlow");
        final Tb01Bericht tb01Bericht = createTb01Bericht();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = happyFlowTotMvGeboorteAntwoordBericht(tb01Bericht);
        Assert.assertNotNull(mvGeboorteVerzoekBericht);

        // Verzend: antwoord op zoek persoon binnen gemeente bericht
        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
        final GevondenPersonenType gevondenPersonen = new GevondenPersonenType();
        final GevondenPersoonType gevondenPersoon = new GevondenPersoonType();
        gevondenPersoon.setANummer("12345678");
        gevondenPersoon.setBijhoudingsgemeente(DOELGEMEENTE);
        gevondenPersonen.getGevondenPersoon().add(gevondenPersoon);

        zoekPersoonAntwoordType.setGevondenPersonen(gevondenPersonen);
        final ZoekPersoonAntwoordBericht onverwachtBericht = new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);
        // zoekPersoonAntwoordBericht.setCorrelationId(zoekPersoonBericht.getMessageId());

        signalBrp(onverwachtBericht);

        // Onverwacht bericht --> GEEN beheerder
        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadMVGeboorteBerichtTimeoutOpnieuwAanbieden() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();
        final MvGeboorteVerzoekBericht initieelMvGeboorteVerzoekBericht =
                happyFlowTotMvGeboorteAntwoordBericht(tb01Bericht);
        Assert.assertNotNull(initieelMvGeboorteVerzoekBericht);

        forceerTimeout(MvGeboorteVerzoekBericht.class, "BRP");
        signalHumanTask("restartAtOpvragenMoeder");

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekPersoonBericht = getZoekPersoonVerzoekBericht();

        // Verzend: antwoord op zoek persoon binnen gemeente bericht
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht =
                maakZoekPersoonAntwoordBericht(zoekPersoonBericht);
        signalBrp(zoekPersoonAntwoordBericht);

        // expect LeesUitBrpVerzoekBericht, om PL van moeder op te halen
        checkBerichten(0, 0, 0, 1);

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBerichtMoeder =
                getLeesUitBrpVerzoekBericht(zoekPersoonAntwoordBericht);

        // Verzend LeesUitBrpAntwoordBericht
        signalSync(maakLeesUitBrpAntwoordBericht(leesUitBrpVerzoekBerichtMoeder, AntwoordFormaatType.BRP, false));

        // expect MVGeboorteBericht
        checkBerichten(1, 0, 0, 0);

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = getMvGeboortVerzoekBericht();

        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht =
                maakMvGeboorteAntwoordBericht(mvGeboorteVerzoekBericht);
        signalBrp(mvGeboorteAntwoordBericht);

        // expect LeesUitBrpVerzoekBericht, om PL van het kind op te halen
        checkBerichten(0, 0, 0, 1);

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht =
                getLeesUitBrpVerzoekBericht(mvGeboorteAntwoordBericht);

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                maakLeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht, AntwoordFormaatType.LO_3, true);
        signalSync(leesUitBrpAntwoordBericht);

        checkBerichten(0, 0, 1, 0);

        final Tv01Bericht tv01Bericht = getBericht(Tv01Bericht.class);
        Assert.assertNotNull(tv01Bericht);

        final NullBericht nullBericht = new NullBericht(tv01Bericht.getMessageId());
        LOG.info("NullBericht: " + nullBericht);

        signalVospg(nullBericht);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadMVGeboorteBerichtFoutCodeA() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = happyFlowTotMvGeboorteAntwoordBericht(tb01Bericht);

        // Stuur MvGeboorteAntwoord met FoutCode A terug
        final MvGeboorteAntwoordBericht antwoordFoutCodeA =
                maakMvGeboorteAntwoordBerichtMetFoutCode(mvGeboorteVerzoekBericht, FoutCode.A);

        signalBrp(antwoordFoutCodeA);

        final Tf01Bericht tf01Bericht = getBericht(Tf01Bericht.class);
        checkLo3BerichtCorrelatieEnAdressering(tb01Bericht, tf01Bericht);
        Assert.assertEquals(Foutreden.A, tf01Bericht.getFoutreden());
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadMVGeboorteBerichtFoutCodeG() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = happyFlowTotMvGeboorteAntwoordBericht(tb01Bericht);

        // Stuur MvGeboorteAntwoord met FoutCode G terug
        final MvGeboorteAntwoordBericht antwoordFoutCodeG =
                maakMvGeboorteAntwoordBerichtMetFoutCode(mvGeboorteVerzoekBericht, FoutCode.G);

        signalBrp(antwoordFoutCodeG);

        final Tf01Bericht tf01Bericht = getBericht(Tf01Bericht.class);
        checkLo3BerichtCorrelatieEnAdressering(tb01Bericht, tf01Bericht);
        Assert.assertEquals(Foutreden.G, tf01Bericht.getFoutreden());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadMVGeboorteBerichtFoutCodeU() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = happyFlowTotMvGeboorteAntwoordBericht(tb01Bericht);

        // Stuur MvGeboorteAntwoord met FoutCode U terug
        final MvGeboorteAntwoordBericht antwoordFoutCodeU =
                maakMvGeboorteAntwoordBerichtMetFoutCode(mvGeboorteVerzoekBericht, FoutCode.U);

        signalBrp(antwoordFoutCodeU);

        final Tf01Bericht tf01Bericht = getBericht(Tf01Bericht.class);
        checkLo3BerichtCorrelatieEnAdressering(tb01Bericht, tf01Bericht);
        Assert.assertEquals(Foutreden.U, tf01Bericht.getFoutreden());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadMVGeboorteBerichtFoutCodeB() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = happyFlowTotMvGeboorteAntwoordBericht(tb01Bericht);

        // Stuur MvGeboorteAntwoord met FoutCode B terug
        final MvGeboorteAntwoordBericht antwoordFoutCodeB =
                maakMvGeboorteAntwoordBerichtMetFoutCode(mvGeboorteVerzoekBericht, FoutCode.B);

        signalBrp(antwoordFoutCodeB);

        final Tf01Bericht tf01Bericht = getBericht(Tf01Bericht.class);
        checkLo3BerichtCorrelatieEnAdressering(tb01Bericht, tf01Bericht);
        Assert.assertEquals(Foutreden.B, tf01Bericht.getFoutreden());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadMVGeboorteBerichtFoutCodeO() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = happyFlowTotMvGeboorteAntwoordBericht(tb01Bericht);

        // Stuur MvGeboorteAntwoord met FoutCode O terug
        final MvGeboorteAntwoordBericht antwoordFoutCodeO =
                maakMvGeboorteAntwoordBerichtMetFoutCode(mvGeboorteVerzoekBericht, FoutCode.O);

        signalBrp(antwoordFoutCodeO);

        final Tf01Bericht tf01Bericht = getBericht(Tf01Bericht.class);
        checkLo3BerichtCorrelatieEnAdressering(tb01Bericht, tf01Bericht);
        Assert.assertEquals(Foutreden.O, tf01Bericht.getFoutreden());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadMVGeboorteBerichtFoutCodeE() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = happyFlowTotMvGeboorteAntwoordBericht(tb01Bericht);

        // Stuur MvGeboorteAntwoord met FoutCode E terug
        final MvGeboorteAntwoordBericht antwoordFoutCodeE =
                maakMvGeboorteAntwoordBerichtMetFoutCode(mvGeboorteVerzoekBericht, FoutCode.E);

        signalBrp(antwoordFoutCodeE);

        final Tf01Bericht tf01Bericht = getBericht(Tf01Bericht.class);
        checkLo3BerichtCorrelatieEnAdressering(tb01Bericht, tf01Bericht);
        Assert.assertEquals(Foutreden.E, tf01Bericht.getFoutreden());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadMVGeboorteBerichtFoutCodeM() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = happyFlowTotMvGeboorteAntwoordBericht(tb01Bericht);

        // Stuur MvGeboorteAntwoord met FoutCode M terug
        final MvGeboorteAntwoordBericht antwoordFoutCodeM =
                maakMvGeboorteAntwoordBerichtMetFoutCode(mvGeboorteVerzoekBericht, FoutCode.M);

        signalBrp(antwoordFoutCodeM);

        final Tf01Bericht tf01Bericht = getBericht(Tf01Bericht.class);
        checkLo3BerichtCorrelatieEnAdressering(tb01Bericht, tf01Bericht);
        Assert.assertEquals(Foutreden.M, tf01Bericht.getFoutreden());

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadMVGeboorteBerichtStatusCodeFout() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = happyFlowTotMvGeboorteAntwoordBericht(tb01Bericht);

        // Stuur MvGeboorteAntwoord met StatusCode.FOUT terug
        final MvGeboorteAntwoordType antwoordType = new MvGeboorteAntwoordType();
        antwoordType.setANummer("1234567898");
        antwoordType.setStatus(StatusType.FOUT);
        antwoordType.setToelichting("Er is iets fout gegaan... niet bekend wat ;-)");
        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht = new MvGeboorteAntwoordBericht(antwoordType);
        mvGeboorteAntwoordBericht.setCorrelationId(mvGeboorteVerzoekBericht.getMessageId());

        signalBrp(mvGeboorteAntwoordBericht);

        checkPf03EnVb01Berichten(tb01Bericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadTimeoutAntwoordOpTv01RestartAtTv01HappyEnd() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = happyFlowTotMvGeboorteAntwoordBericht(tb01Bericht);
        Assert.assertNotNull(mvGeboorteVerzoekBericht);

        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht =
                maakMvGeboorteAntwoordBericht(mvGeboorteVerzoekBericht);
        signalBrp(mvGeboorteAntwoordBericht);

        // expect LeesUitBrpVerzoekBericht, om PL van het kind op te halen
        checkBerichten(0, 0, 0, 1);

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht =
                getLeesUitBrpVerzoekBericht(mvGeboorteAntwoordBericht);

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                maakLeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht, AntwoordFormaatType.LO_3, true);
        signalSync(leesUitBrpAntwoordBericht);

        checkBerichten(0, 0, 1, 0);

        final Tv01Bericht tv01Bericht = getBericht(Tv01Bericht.class);
        Assert.assertNotNull(tv01Bericht);

        forceerTimeout(Tv01Bericht.class, "VOSPG");

        // expect
        signalHumanTask("restartAtTv01");

        checkBerichten(0, 0, 1, 0);

        final Tv01Bericht tweedeTv01Bericht = getBericht(Tv01Bericht.class);
        Assert.assertNotNull(tweedeTv01Bericht);

        final NullBericht nullBericht = new NullBericht(tweedeTv01Bericht.getMessageId());
        LOG.info("NullBericht: " + nullBericht);

        signalVospg(nullBericht);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void testAlternatiefPadTimeoutOntvangAntwoordPlInBrp() throws Exception {
        final Tb01Bericht tb01Bericht = createTb01Bericht();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = happyFlowTotMvGeboorteAntwoordBericht(tb01Bericht);
        Assert.assertNotNull(mvGeboorteVerzoekBericht);

        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht =
                maakMvGeboorteAntwoordBericht(mvGeboorteVerzoekBericht);
        signalBrp(mvGeboorteAntwoordBericht);

        // expect LeesUitBrpVerzoekBericht, om PL van het kind op te halen
        checkBerichten(0, 0, 0, 1);

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht =
                getLeesUitBrpVerzoekBericht(mvGeboorteAntwoordBericht);
        Assert.assertNotNull(leesUitBrpVerzoekBericht);

        forceerTimeout(LeesUitBrpVerzoekBericht.class, "SYNC");
        signalHumanTask("restartAtOpvragen");

        // expect LeesUitBrpVerzoekBericht, om PL van het kind op te halen
        checkBerichten(0, 0, 0, 1);

        final LeesUitBrpVerzoekBericht tweedeLeesUitBrpVerzoekBericht =
                getLeesUitBrpVerzoekBericht(mvGeboorteAntwoordBericht);

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                maakLeesUitBrpAntwoordBericht(tweedeLeesUitBrpVerzoekBericht, AntwoordFormaatType.LO_3, true);
        signalSync(leesUitBrpAntwoordBericht);

        checkBerichten(0, 0, 1, 0);

        final Tv01Bericht tv01Bericht = getBericht(Tv01Bericht.class);
        Assert.assertNotNull(tv01Bericht);

        final NullBericht nullBericht = new NullBericht(tv01Bericht.getMessageId());
        LOG.info("NullBericht: " + nullBericht);

        signalVospg(nullBericht);

        Assert.assertTrue(processEnded());
    }

    private MvGeboorteVerzoekBericht happyFlowTotMvGeboorteAntwoordBericht(final Tb01Bericht tb01Bericht)
            throws Exception {

        // Start het proces.
        startProcess(tb01Bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekPersoonBericht = getZoekPersoonVerzoekBericht();

        // Verzend: antwoord op zoek persoon binnen gemeente bericht
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht =
                maakZoekPersoonAntwoordBericht(zoekPersoonBericht);
        signalBrp(zoekPersoonAntwoordBericht);

        // expect LeesUitBrpVerzoekBericht, om PL van moeder op te halen
        checkBerichten(0, 0, 0, 1);

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBerichtMoeder =
                getLeesUitBrpVerzoekBericht(zoekPersoonAntwoordBericht);

        // Verzend LeesUitBrpAntwoordBericht
        signalSync(maakLeesUitBrpAntwoordBericht(leesUitBrpVerzoekBerichtMoeder, AntwoordFormaatType.BRP, false));

        // expect MVGeboorteBericht
        checkBerichten(1, 0, 0, 0);

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = getMvGeboortVerzoekBericht();
        Assert.assertNotNull(mvGeboorteVerzoekBericht);
        return mvGeboorteVerzoekBericht;
    }

    /**
     * Voert het proces uit, waarbij het mogelijk is om te kiezen voor de alternatieve happy flow (Tf11-bericht uit
     * LO3).
     * 
     * @param alternatieveHappyFlow
     *            Indien false, volgen we de standaard happy flow, indien true de alternatieve.
     * @throws Exception
     *             Indien er een exceptie optreedt.
     */
    private void performHappyFlowProcess(final boolean alternatieveHappyFlow) throws Exception {
        LOG.info("start testHappyFlow");
        final Tb01Bericht tb01_bericht = createTb01Bericht();

        // Start het proces.
        startProcess(tb01_bericht);

        // Verwacht: ConverteerNaarBrpVerzoekBerichten, Kind PL
        checkBerichten(0, 0, 0, 1);

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekKindPL =
                getBericht(ConverteerNaarBrpVerzoekBericht.class);

        // Converteer de kind PL naar BRP formaat.
        signalSync(maakConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekKindPL, true));

        // Verwacht: ZoekPersoonBericht, gegevens van moeder uit PL kind. Zoeken binnen gemeente
        checkBerichten(1, 0, 0, 0);

        final ZoekPersoonVerzoekBericht zoekPersoonBericht = getZoekPersoonVerzoekBericht();

        // Verzend: antwoord op zoek persoon binnen gemeente bericht
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht =
                maakZoekPersoonAntwoordBericht(zoekPersoonBericht);
        signalBrp(zoekPersoonAntwoordBericht);

        // expect LeesUitBrpVerzoekBericht, om PL van moeder op te halen
        checkBerichten(0, 0, 0, 1);

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBerichtMoeder =
                getLeesUitBrpVerzoekBericht(zoekPersoonAntwoordBericht);

        // Verzend LeesUitBrpAntwoordBericht
        signalSync(maakLeesUitBrpAntwoordBericht(leesUitBrpVerzoekBerichtMoeder, AntwoordFormaatType.BRP, false));

        // expect MVGeboorteBericht
        checkBerichten(1, 0, 0, 0);

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = getMvGeboortVerzoekBericht();

        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht =
                maakMvGeboorteAntwoordBericht(mvGeboorteVerzoekBericht);
        signalBrp(mvGeboorteAntwoordBericht);

        // expect LeesUitBrpVerzoekBericht, om PL van het kind op te halen
        checkBerichten(0, 0, 0, 1);

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht =
                getLeesUitBrpVerzoekBericht(mvGeboorteAntwoordBericht);

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                maakLeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht, AntwoordFormaatType.LO_3, true);
        signalSync(leesUitBrpAntwoordBericht);

        checkBerichten(0, 0, 1, 0);

        final Tv01Bericht tv01Bericht = getBericht(Tv01Bericht.class);
        Assert.assertNotNull(tv01Bericht);

        if (alternatieveHappyFlow) {
            final Tf11Bericht tf11Bericht = new Tf11Bericht();
            LOG.info("Tf11Bericht: " + tf11Bericht);

            signalVospg(tf11Bericht);

            checkBerichten(1, 0, 0, 0);

            final GeboorteAntwoordBericht geboorteAntwoordBericht = getBericht(GeboorteAntwoordBericht.class);
            Assert.assertNotNull(geboorteAntwoordBericht);
            Assert.assertEquals(StatusType.WAARSCHUWING, geboorteAntwoordBericht.getStatus());

        } else {
            final NullBericht nullBericht = new NullBericht(tv01Bericht.getMessageId());
            LOG.info("NullBericht: " + nullBericht);

            signalVospg(nullBericht);
        }

        Assert.assertTrue(processEnded());
    }

    private void checkLo3BerichtCorrelatieEnAdressering(final Tb01Bericht tb01Bericht, final Lo3Bericht lo3Bericht) {
        Assert.assertNotNull(lo3Bericht);
        Assert.assertEquals(tb01Bericht.getMessageId(), lo3Bericht.getCorrelationId());
        Assert.assertEquals(tb01Bericht.getDoelGemeente(), lo3Bericht.getBronGemeente());
        Assert.assertEquals(tb01Bericht.getBronGemeente(), lo3Bericht.getDoelGemeente());
    }

    private void checkPf03EnVb01Berichten(final Tb01Bericht tb01Bericht) {
        // Verwacht: Pf03Bericht en Vb01Bericht, beide Lo3
        checkBerichten(0, 0, 2, 0);

        final Pf03Bericht pf03Bericht = getBericht(Pf03Bericht.class);
        Assert.assertEquals(tb01Bericht.getMessageId(), pf03Bericht.getCorrelationId());
        Assert.assertEquals(tb01Bericht.getBronGemeente(), pf03Bericht.getDoelGemeente());
        Assert.assertEquals(tb01Bericht.getDoelGemeente(), pf03Bericht.getBronGemeente());

        final Vb01Bericht vb01Bericht = getBericht(Vb01Bericht.class);
        // Het Vb01Bericht moet gecorreleerd zijn aan het voorafgaande Pf03Bericht.
        Assert.assertEquals(pf03Bericht.getMessageId(), vb01Bericht.getCorrelationId());
    }

    private LeesUitBrpVerzoekBericht getLeesUitBrpVerzoekBericht(
            final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht) {
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht = getBericht(LeesUitBrpVerzoekBericht.class);
        Assert.assertNotNull(leesUitBrpVerzoekBericht);
        Assert.assertEquals((Long) Long.parseLong(mvGeboorteAntwoordBericht.getANummer()),
                leesUitBrpVerzoekBericht.getANummer());
        return leesUitBrpVerzoekBericht;
    }

    private MvGeboorteVerzoekBericht getMvGeboortVerzoekBericht() {
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = getBericht(MvGeboorteVerzoekBericht.class);
        Assert.assertNotNull(mvGeboorteVerzoekBericht);
        return mvGeboorteVerzoekBericht;
    }

    private MvGeboorteAntwoordBericht maakMvGeboorteAntwoordBericht(
            final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht) {
        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht = new MvGeboorteAntwoordBericht();
        mvGeboorteAntwoordBericht.setANummer("123456789");
        mvGeboorteAntwoordBericht.setCorrelationId(mvGeboorteVerzoekBericht.getMessageId());
        return mvGeboorteAntwoordBericht;
    }

    private MvGeboorteAntwoordBericht maakMvGeboorteAntwoordBerichtMetFoutCode(
            final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht,
            final FoutCode foutCode) {
        final MvGeboorteAntwoordType antwoordType = new MvGeboorteAntwoordType();
        antwoordType.setANummer("1234567898");
        antwoordType.setStatus(StatusType.FOUT);
        antwoordType.setFoutCode(foutCode);
        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht = new MvGeboorteAntwoordBericht(antwoordType);
        mvGeboorteAntwoordBericht.setCorrelationId(mvGeboorteVerzoekBericht.getMessageId());

        return mvGeboorteAntwoordBericht;
    }

    private ConverteerNaarBrpAntwoordBericht maakConverteerNaarBrpAntwoordBericht(
            final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekMoederPL,
            final Boolean isPlKind) {
        final ConverteerNaarBrpAntwoordBericht converteerAntwoordMoederPL = new ConverteerNaarBrpAntwoordBericht();
        converteerAntwoordMoederPL.setBrpPersoonslijst(createBrpPersoonslijst(isPlKind));
        converteerAntwoordMoederPL.setCorrelationId(converteerNaarBrpVerzoekMoederPL.getMessageId());
        return converteerAntwoordMoederPL;
    }

    private ZoekPersoonAntwoordBericht maakZoekPersoonAntwoordBericht(
            final ZoekPersoonVerzoekBericht zoekPersoonBericht) {
        final ZoekPersoonAntwoordType zoekPersoonAntwoordType = new ZoekPersoonAntwoordType();
        zoekPersoonAntwoordType.setStatus(StatusType.OK);
        final GevondenPersonenType gevondenPersonen = new GevondenPersonenType();
        final GevondenPersoonType gevondenPersoon = new GevondenPersoonType();
        gevondenPersoon.setANummer("12345678");
        gevondenPersoon.setBijhoudingsgemeente(DOELGEMEENTE);
        gevondenPersonen.getGevondenPersoon().add(gevondenPersoon);

        zoekPersoonAntwoordType.setGevondenPersonen(gevondenPersonen);
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht =
                new ZoekPersoonAntwoordBericht(zoekPersoonAntwoordType);
        zoekPersoonAntwoordBericht.setCorrelationId(zoekPersoonBericht.getMessageId());
        LOG.info("ZoekPersoonAntwoord: " + zoekPersoonAntwoordBericht);
        return zoekPersoonAntwoordBericht;
    }

    private ZoekPersoonVerzoekBericht getZoekPersoonVerzoekBericht() {
        final ZoekPersoonVerzoekBericht zoekPersoonBericht = getBericht(ZoekPersoonVerzoekBericht.class);
        Assert.assertNotNull(zoekPersoonBericht);
        Assert.assertNotNull(zoekPersoonBericht.getBijhoudingsGemeente());
        return zoekPersoonBericht;
    }

    private LeesUitBrpVerzoekBericht getLeesUitBrpVerzoekBericht(
            final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht) {
        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht = getBericht(LeesUitBrpVerzoekBericht.class);
        Assert.assertNotNull(leesUitBrpVerzoekBericht);
        Assert.assertEquals((Long) Long.parseLong(zoekPersoonAntwoordBericht.getSingleAnummer()),
                leesUitBrpVerzoekBericht.getANummer());
        return leesUitBrpVerzoekBericht;
    }

    private LeesUitBrpAntwoordBericht maakLeesUitBrpAntwoordBericht(
            final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht,
            final AntwoordFormaatType antwoordFormaat,
            final boolean isKind) throws Exception {
        if (AntwoordFormaatType.BRP.equals(antwoordFormaat)) {
            return new LeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht, createBrpPersoonslijst(isKind));
        } else {
            return new LeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht.getMessageId(), maakLo3Persoonslijst());
        }
    }

    private LeesUitBrpAntwoordBericht maakFOUTLeesUitBrpAntwoordBericht(
            final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBerichtMoeder) throws Exception {
        return new LeesUitBrpAntwoordBericht(leesUitBrpVerzoekBerichtMoeder.getMessageId(),
                "Er is iets fout gegaan...");
    }

    private Tb01Bericht createTb01Bericht() throws BerichtSyntaxException, BerichtOnbekendException,
            BerichtInhoudException {
        // Tb01 bericht als teletext string.
        final String tb01_text =
                "00000000Tb010M1 A123400317010990210011Maarten Jan0230003van0240005Haren03100082012110103200040600033000460300410001M81200071 A1234021030210013"
                        + "Gina Jennifer0230003van0240006Lloyds03100081990100103200040599033000460300410001V621000820121101031000210011Hans Herman0230003van0240005Haren0"
                        + "3100081989010103200040599033000460300410001M621000820121101";
        System.out.println("TB01 text: " + tb01_text);

        // Stel het Tb01 bericht op basis van de teletext string op.
        final Tb01Bericht tb01_bericht = (Tb01Bericht) LO3_FACTORY.getBericht(tb01_text);
        tb01_bericht.setDoelGemeente(DOELGEMEENTE);
        tb01_bericht.setBronGemeente(BRONGEMEENTE);
        Assert.assertNotNull(tb01_bericht);
        return tb01_bericht;
    }

    private Tb01Bericht createTb01BerichtMetGezochtePersoonV() throws BerichtSyntaxException,
            BerichtOnbekendException, BerichtInhoudException {
        // Tb01 bericht als teletext string.
        final String tb01_text =
                "00000000Tb010V1 A123400317010990210011Maarten Jan0230003van0240005Haren03100082012110103200040600033000460300410001M81200071 A1234021030210013"
                        + "Gina Jennifer0230003van0240006Lloyds03100081990100103200040599033000460300410001V621000820121101031000210011Hans Herman0230003van0240005Haren0"
                        + "3100081989010103200040599033000460300410001M621000820121101";
        System.out.println("TB01 text: " + tb01_text);

        // Stel het Tb01 bericht op basis van de teletext string op.
        final Tb01Bericht tb01_bericht = (Tb01Bericht) LO3_FACTORY.getBericht(tb01_text);
        tb01_bericht.setDoelGemeente(DOELGEMEENTE);
        tb01_bericht.setBronGemeente(BRONGEMEENTE);
        Assert.assertNotNull(tb01_bericht);
        return tb01_bericht;
    }

    private Tb01Bericht createTb01BerichtZonderMoeder() throws BerichtSyntaxException, BerichtOnbekendException,
            BerichtInhoudException {
        // Tb01 bericht als teletext string.
        final String tb01_text =
                "00000000Tb010M1 A123400317010990210011Maarten Jan0230003van0240005Haren03100082012110103200040600033000460300410001M81200071 A1234021030210013"
                        + "Keesje Jantje0230003van0240006Lloyds03100081990100103200040599033000460300410001M621000820121101031000210011Hans Herman0230003van0240005Haren0"
                        + "3100081989010103200040599033000460300410001M621000820121101";
        System.out.println("TB01 text: " + tb01_text);

        // Stel het Tb01 bericht op basis van de teletext string op.
        final Tb01Bericht tb01_bericht = (Tb01Bericht) LO3_FACTORY.getBericht(tb01_text);
        tb01_bericht.setDoelGemeente(DOELGEMEENTE);
        tb01_bericht.setBronGemeente(BRONGEMEENTE);
        Assert.assertNotNull(tb01_bericht);
        return tb01_bericht;
    }

    private Tb01Bericht createTb01BerichtTweeVrouwen() throws BerichtSyntaxException, BerichtOnbekendException,
            BerichtInhoudException {
        // Tb01 bericht als teletext string.
        final String tb01_text =
                "00000000Tb010M1 A123400317010990210011Maarten Jan0230003van0240005Haren03100082012110103200040600033000460300410001M81200071 A1234021030210013"
                        + "Marion Jantje0230003van0240006Lloyds03100081990100103200040599033000460300410001V621000820121101031000210011Anja Herman0230003van0240005Haren0"
                        + "3100081989010103200040599033000460300410001V621000820121101";
        System.out.println("TB01 text: " + tb01_text);

        // Stel het Tb01 bericht op basis van de teletext string op.
        final Tb01Bericht tb01_bericht = (Tb01Bericht) LO3_FACTORY.getBericht(tb01_text);
        tb01_bericht.setDoelGemeente(DOELGEMEENTE);
        tb01_bericht.setBronGemeente(BRONGEMEENTE);
        Assert.assertNotNull(tb01_bericht);
        return tb01_bericht;
    }

    private static Lo3Persoonslijst maakLo3Persoonslijst() throws Exception {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);
        return new Lo3PersoonslijstParser().parse(categorieen);
    }

    public static BrpPersoonslijst createBrpPersoonslijst(final boolean isKindPersoonslijst) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        final BrpStapel<BrpGeslachtsnaamcomponentInhoud> geslachtsnaamcomponentStapel =
                maakGeslachtsnaamcomponentStapel("van", "Leeuwen", new Character(' '));

        if (!isKindPersoonslijst) {
            final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                    maakIdentificatienummersStapel(123456789L, 987654321L);
            final BrpStapel<BrpGeboorteInhoud> geboorteStapel =
                    maakGeboorteStapel(new BrpDatum(19750511), new BrpGemeenteCode(new BigDecimal("1904")), null,
                            new BrpLandCode(Integer.valueOf("3010")));
            final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel =
                    maakGeslachtsaanduidingStapel(BrpGeslachtsaanduidingCode.VROUW);
            final BrpStapel<BrpVoornaamInhoud> voornaamStapel = maakVoornamenStapel("Carla");

            builder.geboorteStapel(geboorteStapel);
            builder.geslachtsaanduidingStapel(geslachtsaanduidingStapel);
            builder.geslachtsnaamcomponentStapel(geslachtsnaamcomponentStapel);
            builder.voornaamStapel(voornaamStapel);
            builder.identificatienummersStapel(identificatienummersStapel);
        } else {
            final BrpStapel<BrpGeboorteInhoud> geboorteStapel =
                    maakGeboorteStapel(new BrpDatum(20120511), new BrpGemeenteCode(new BigDecimal("1904")), null,
                            new BrpLandCode(Integer.valueOf("3010")));
            final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel =
                    maakGeslachtsaanduidingStapel(BrpGeslachtsaanduidingCode.MAN);
            final BrpStapel<BrpVoornaamInhoud> voornaamStapel = maakVoornamenStapel("Kees");

            builder.geboorteStapel(geboorteStapel);
            builder.geslachtsaanduidingStapel(geslachtsaanduidingStapel);
            builder.geslachtsnaamcomponentStapel(geslachtsnaamcomponentStapel);
            builder.voornaamStapel(voornaamStapel);
        }

        return builder.build();
    }

    private static BrpStapel<BrpIdentificatienummersInhoud> maakIdentificatienummersStapel(
            final Long aNummer,
            final Long bsn) {
        final List<BrpGroep<BrpIdentificatienummersInhoud>> identificatienummersGroepLijst =
                new ArrayList<BrpGroep<BrpIdentificatienummersInhoud>>();

        final BrpIdentificatienummersInhoud identificatienummersInhoud =
                new BrpIdentificatienummersInhoud(aNummer, bsn);

        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(20121106), null, BrpDatumTijd.fromDatum(20121106), null);
        final BrpActie actieInhoud =
                new BrpActie(1234L, BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode("Testgemeente",
                        Integer.valueOf(6030)), null, BrpDatumTijd.fromDatum(20121106),
                        BrpDatumTijd.fromDatum(20121106), null, 1, null);

        final BrpGroep<BrpIdentificatienummersInhoud> identificatienummersGroep =
                new BrpGroep<BrpIdentificatienummersInhoud>(identificatienummersInhoud, historie, actieInhoud, null,
                        null);

        identificatienummersGroepLijst.add(identificatienummersGroep);

        return new BrpStapel<BrpIdentificatienummersInhoud>(identificatienummersGroepLijst);
    }

    private static BrpStapel<BrpGeboorteInhoud> maakGeboorteStapel(
            final BrpDatum geboorteDatum,
            final BrpGemeenteCode gemeenteCode,
            final BrpPlaatsCode plaatsCode,
            final BrpLandCode landCode) {
        final List<BrpGroep<BrpGeboorteInhoud>> geboorteGroepLijst = new ArrayList<BrpGroep<BrpGeboorteInhoud>>();

        final BrpGeboorteInhoud geboorteInhoud =
                new BrpGeboorteInhoud(geboorteDatum, gemeenteCode, plaatsCode, null, null, landCode, null);

        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(20121106), null, BrpDatumTijd.fromDatum(20121106), null);
        final BrpActie actieInhoud =
                new BrpActie(1234L, BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode("Testgemeente",
                        Integer.valueOf(6030)), null, BrpDatumTijd.fromDatum(20121106),
                        BrpDatumTijd.fromDatum(20121106), null, 1, null);

        final BrpGroep<BrpGeboorteInhoud> geboorteGroep =
                new BrpGroep<BrpGeboorteInhoud>(geboorteInhoud, historie, actieInhoud, null, null);

        geboorteGroepLijst.add(geboorteGroep);

        return new BrpStapel<BrpGeboorteInhoud>(geboorteGroepLijst);
    }

    private static BrpStapel<BrpVoornaamInhoud> maakVoornamenStapel(final String voornamen) {
        final List<BrpGroep<BrpVoornaamInhoud>> voornamenGroepLijst = new ArrayList<BrpGroep<BrpVoornaamInhoud>>();

        final BrpVoornaamInhoud voornamenInhoud = new BrpVoornaamInhoud(voornamen, 1);

        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(20121106), null, BrpDatumTijd.fromDatum(20121106), null);
        final BrpActie actieInhoud =
                new BrpActie(1234L, BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode("Testgemeente",
                        Integer.valueOf(6030)), null, BrpDatumTijd.fromDatum(20121106),
                        BrpDatumTijd.fromDatum(20121106), null, 1, null);

        final BrpGroep<BrpVoornaamInhoud> voornamenGroep =
                new BrpGroep<BrpVoornaamInhoud>(voornamenInhoud, historie, actieInhoud, null, null);

        voornamenGroepLijst.add(voornamenGroep);

        return new BrpStapel<BrpVoornaamInhoud>(voornamenGroepLijst);
    }

    private static BrpStapel<BrpGeslachtsaanduidingInhoud> maakGeslachtsaanduidingStapel(
            final BrpGeslachtsaanduidingCode geslachtsaanduidingCode) {
        final List<BrpGroep<BrpGeslachtsaanduidingInhoud>> geslachtsaanduidingGroepLijst =
                new ArrayList<BrpGroep<BrpGeslachtsaanduidingInhoud>>();

        final BrpGeslachtsaanduidingInhoud geslachtsaanduidingInhoud =
                new BrpGeslachtsaanduidingInhoud(geslachtsaanduidingCode);

        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(20121106), null, BrpDatumTijd.fromDatum(20121106), null);
        final BrpActie actieInhoud =
                new BrpActie(1234L, BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode("Testgemeente",
                        Integer.valueOf(6030)), null, BrpDatumTijd.fromDatum(20121106),
                        BrpDatumTijd.fromDatum(20121106), null, 1, null);

        final BrpGroep<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingGroep =
                new BrpGroep<BrpGeslachtsaanduidingInhoud>(geslachtsaanduidingInhoud, historie, actieInhoud, null,
                        null);

        geslachtsaanduidingGroepLijst.add(geslachtsaanduidingGroep);

        return new BrpStapel<BrpGeslachtsaanduidingInhoud>(geslachtsaanduidingGroepLijst);
    }

    private static BrpStapel<BrpGeslachtsnaamcomponentInhoud> maakGeslachtsnaamcomponentStapel(
            final String voorvoegsel,
            final String geslachtsnaam,
            final Character scheidingsteken) {
        final List<BrpGroep<BrpGeslachtsnaamcomponentInhoud>> geslachtsaanduidingGroepLijst =
                new ArrayList<BrpGroep<BrpGeslachtsnaamcomponentInhoud>>();

        final BrpGeslachtsnaamcomponentInhoud geslachtsaanduidingInhoud =
                new BrpGeslachtsnaamcomponentInhoud(voorvoegsel, scheidingsteken, geslachtsnaam, null, null, 1);

        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(20121106), null, BrpDatumTijd.fromDatum(20121106), null);
        final BrpActie actieInhoud =
                new BrpActie(1234L, BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode("Testgemeente",
                        Integer.valueOf(6030)), null, BrpDatumTijd.fromDatum(20121106),
                        BrpDatumTijd.fromDatum(20121106), null, 1, null);

        final BrpGroep<BrpGeslachtsnaamcomponentInhoud> geslachtsaanduidingGroep =
                new BrpGroep<BrpGeslachtsnaamcomponentInhoud>(geslachtsaanduidingInhoud, historie, actieInhoud, null,
                        null);

        geslachtsaanduidingGroepLijst.add(geslachtsaanduidingGroep);

        return new BrpStapel<BrpGeslachtsnaamcomponentInhoud>(geslachtsaanduidingGroepLijst);
    }

}
