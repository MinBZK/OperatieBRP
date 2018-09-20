/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.misc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.aanroepers.bevraging.VraagDetailsPersoonAanroeper;
import nl.bzk.brp.testclient.aanroepers.bevraging.VraagKandidaatVaderAanroeper;
import nl.bzk.brp.testclient.aanroepers.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper;
import nl.bzk.brp.testclient.aanroepers.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper;
import nl.bzk.brp.testclient.aanroepers.bijhouding.BijhoudingAanroeper;
import nl.bzk.brp.testclient.aanroepers.bijhouding.CorrectieAdresAanroeper;
import nl.bzk.brp.testclient.aanroepers.bijhouding.InschrijvingGeboorteAanroeper;
import nl.bzk.brp.testclient.aanroepers.bijhouding.RegistreerHuwelijkEnPartnerschapAanroeper;
import nl.bzk.brp.testclient.aanroepers.bijhouding.VerhuizingAanroeper;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Bericht.TYPE;
import nl.bzk.brp.testclient.testdata.TestdataServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class ScenarioEngineImpl.
 */
public class ScenarioEngineImpl implements ScenarioEngine {

    /** De log. */
    private final Logger    LOG                        = LoggerFactory.getLogger(getClass());

    /** De verhuizing_aanvangdatum. */
    private static Calendar verhuizing_aanvangdatum;

    /** De correctie_adres_huisnummer. */
    private static int      correctie_adres_huisnummer = 0;

    /** De correctie_adres_aanvangdatum. */
    private static Calendar correctie_adres_aanvangdatum;

    /**
     * Instantieert een nieuwe scenario engine impl.
     */
    public ScenarioEngineImpl() {
        // Startpunt Aanvangdatum verhuizing
        verhuizing_aanvangdatum = Calendar.getInstance();
        verhuizing_aanvangdatum.setTime(new Date());
        verhuizing_aanvangdatum.add(Calendar.YEAR, -3);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.misc.ScenarioEngine#runScenario(nl.bzk.brp.testclient.misc.Bericht.BERICHT)
     */
    @Override
    public void runScenario(final Bericht.BERICHT bericht, final Eigenschappen eigenschappen) throws Exception {
        switch (bericht) {
            case BIJHOUDING_CORRECTIE_ADRES:
                runScenarioBijhoudingCorrectieAdres(eigenschappen);
                break;
            case BIJHOUDING_INSCHRIJVING_GEBOORTE:
                runScenarioBijhoudingInschrijvingGeboorte(eigenschappen);
                break;
            case BIJHOUDING_REGISTREER_HUWELIJK:
                runScenarioBijhoudingRegistreerHuwelijk(eigenschappen);
                break;
            case BIJHOUDING_VERHUIZING:
                runScenarioBijhoudingVerhuizing(eigenschappen);
                break;
            default:
                break;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.misc.ScenarioEngine#runBericht(nl.bzk.brp.testclient.misc.Bericht.BERICHT,
     * nl.bzk.brp.testclient.misc.Eigenschappen)
     */
    @Override
    public void runBericht(final BERICHT bericht, final Eigenschappen eigenschappen) throws Exception {
        switch (bericht) {
            case VRAAG_DETAILS:
                runVraagDetailsPersoon(eigenschappen);
                break;
            case VRAAG_KANDIDAAT_VADER:
                runVraagKandidaatVader(eigenschappen);
                break;
            case VRAAG_PERSONEN_OP_ADRES:
                runVraagPersonenOpAdres(eigenschappen);
                break;
            case VRAAG_PERSONEN_OP_ADRES_VIA_BSN:
                runVraagPersonenOpAdresViaBsn(eigenschappen);
                break;
            case BIJHOUDING_CORRECTIE_ADRES:
                runBijhoudingCorrectieAdres(eigenschappen);
                break;
            case BIJHOUDING_INSCHRIJVING_GEBOORTE:
                runBijhoudingInschrijvingGeboorte(eigenschappen);
                break;
            case BIJHOUDING_REGISTREER_HUWELIJK:
                runBijhoudingRegistreerHuwelijk(eigenschappen);
                break;
            case BIJHOUDING_VERHUIZING:
                runBijhoudingVerhuizing(eigenschappen);
                break;
            default:
                break;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.misc.ScenarioEngine#runType(nl.bzk.brp.testclient.misc.Bericht.TYPE,
     * nl.bzk.brp.testclient.misc.Eigenschappen)
     */
    @Override
    public void runType(final TYPE type, final Eigenschappen eigenschappen) throws Exception {
        switch (type) {
            case ALLES:
                runBevraging(eigenschappen);
                runBijhouding(eigenschappen);
                break;
            case BEVRAGING:
                runBevraging(eigenschappen);
                break;
            case BIJHOUDING:
                runBijhouding(eigenschappen);
                break;
            default:
                break;
        }
    }

    private void runBevraging(final Eigenschappen eigenschappen) throws Exception {
        runVraagDetailsPersoon(eigenschappen);
        runVraagKandidaatVader(eigenschappen);
        runVraagPersonenOpAdres(eigenschappen);
        runVraagPersonenOpAdresViaBsn(eigenschappen);
    }

    private void runBijhouding(final Eigenschappen eigenschappen) throws Exception {
        runBijhoudingCorrectieAdres(eigenschappen);
        runBijhoudingInschrijvingGeboorte(eigenschappen);
        runBijhoudingRegistreerHuwelijk(eigenschappen);
        runBijhoudingVerhuizing(eigenschappen);
    }

    /**
     * Run scenario bijhouding correctie adres.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runScenarioBijhoudingCorrectieAdres(final Eigenschappen eigenschappen) throws Exception {
        String bsn = TestClient.testdataService.getBsn();

        // 1: Details persoon aangever
        HashMap<String, String> parameterMap1 = new HashMap<String, String>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsn);
        new VraagDetailsPersoonAanroeper(eigenschappen, parameterMap1).fire();

        // 2: Opvragen medebewoners op adres op basis van bsn
        HashMap<String, String> parameterMap2 = new HashMap<String, String>();
        parameterMap2.put(VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper.PARAMETER_BSN, bsn);
        new VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper(eigenschappen, parameterMap2).fire();

        // Random Aanvangdatum correctie adres
        correctie_adres_aanvangdatum = Calendar.getInstance();
        correctie_adres_aanvangdatum.setTime(new Date());
        Random random = new Random();
        correctie_adres_aanvangdatum.add(Calendar.DAY_OF_YEAR, -(random.nextInt(1000) + 60));
        Date datumAanvangDate = correctie_adres_aanvangdatum.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datumAanvang = sdf.format(datumAanvangDate);

        // Einde geldigheid is 30 dagen later
        Calendar correctie_einde_geldigheid = Calendar.getInstance();
        correctie_einde_geldigheid.setTime(datumAanvangDate);
        correctie_einde_geldigheid.add(Calendar.DAY_OF_YEAR, 30);
        Date eindeGeldigheidDate = correctie_einde_geldigheid.getTime();
        String datumEinde = sdf.format(eindeGeldigheidDate);

        // Random geldig adres
        Map<String, String> adres = TestClient.testdataService.getAdres();
        String huisnummer = adres.get(TestdataServiceImpl.HUISNUMMER);
        String huisletter = adres.get(TestdataServiceImpl.HUISLETTER);
        String huisnummerToevoeging = adres.get(TestdataServiceImpl.HUISNUMMERTOEVOEGING);
        String postcode = adres.get(TestdataServiceImpl.POSTCODE);
        String nor = adres.get(TestdataServiceImpl.NOR);
        String wpl = adres.get(TestdataServiceImpl.WOONPLAATSCODE);
        String gemeente = adres.get(TestdataServiceImpl.GEMEENTECODE);

        // TODO: analyseren waar deze sporadische melding door komt:
        // datum aanvang: 20120311 - datumEinde: 20120410 - gemeente: 0344 - wpl: 3295
        // Melding CorrectieAdresCommand referentienummer 12345:
        // "De gemeente en gemeentecode is niet (of niet volledig) geldig binnen de opgegeven periode. (Gemeentecode = 0344)."
        // (regel: BRBY0527)
        // Melding CorrectieAdresCommand referentienummer 12345:
        // "Datum einde geldigheid dient na datum aanvang geldigheid te liggen." (regel: BRBY0032)

        // log.debug("datum aanvang: " + datumAanvang + " - datumEinde: " + datumEinde + " - gemeente: " + gemeente +
        // " - wpl: " + wpl);

        // 3: Prevalidatie correctie adres
        HashMap<String, String> parameterMap3 = new HashMap<String, String>();
        parameterMap3.put(BijhoudingAanroeper.PARAMETER_PREVALIDATIE, "J");
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_BSN, bsn);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_NAAM_OPENBARE_RUIMTE, nor);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_POSTCODE, postcode);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_WOONPLAATS, wpl);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_HUISNUMMER, huisnummer);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_HUISLETTER, huisletter);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_HUISNUMMERTOEVOEGING, huisnummerToevoeging);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_GEMEENTECODE, gemeente);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_DATUM_AANVANG_GELDIGHEID, datumAanvang);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_DATUM_EINDE_GELDIGHEID, datumEinde);
        new CorrectieAdresAanroeper(eigenschappen, parameterMap3).fire();

        if (!eigenschappen.getFlowPrevalidatie()) {
            // 4: Correctie adres
            parameterMap3.remove(BijhoudingAanroeper.PARAMETER_PREVALIDATIE);
        }
        new CorrectieAdresAanroeper(eigenschappen, parameterMap3).fire();

        if (correctie_adres_huisnummer > 9999) {
            correctie_adres_huisnummer = 0;
        }
    }

    /**
     * Run scenario bijhouding inschrijving geboorte.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runScenarioBijhoudingInschrijvingGeboorte(final Eigenschappen eigenschappen) throws Exception {
        Map<String, String> huwelijk = TestClient.testdataService.getHuwelijk();
        String bsnVader = huwelijk.get(TestdataServiceImpl.HUWELIJK_PARTNER1);
        String bsnMoeder = huwelijk.get(TestdataServiceImpl.HUWELIJK_PARTNER2);
        String naam = huwelijk.get(TestdataServiceImpl.HUWELIJK_NAAM);
        String voorvoegsel = huwelijk.get(TestdataServiceImpl.HUWELIJK_VOORVOEGSEL);

        // 1: Vraagdetails aangever geboorte
        HashMap<String, String> parameterMap1 = new HashMap<String, String>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsnVader);
        new VraagDetailsPersoonAanroeper(eigenschappen, parameterMap1).fire();

        // 2: Vraagdetails moeder
        HashMap<String, String> parameterMap2 = new HashMap<String, String>();
        parameterMap2.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsnMoeder);
        new VraagDetailsPersoonAanroeper(eigenschappen, parameterMap2).fire();

        // 3: Vraag kandidaat vader
        HashMap<String, String> parameterMap3 = new HashMap<String, String>();
        parameterMap3.put(VraagKandidaatVaderAanroeper.PARAMETER_BSN, bsnMoeder);
        new VraagKandidaatVaderAanroeper(eigenschappen, parameterMap3).fire();

        // 4: Prevalidatie aangifte geborene
        HashMap<String, String> parameterMap4 = new HashMap<String, String>();
        parameterMap4.put(BijhoudingAanroeper.PARAMETER_PREVALIDATIE, "J");
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN, TestClient.testdataService.getNieuweBsn());
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_NAAM, naam);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_VOORVOEGSEL, voorvoegsel);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN_OUDER1, bsnVader);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN_OUDER2, bsnMoeder);
        parameterMap4
                .put(InschrijvingGeboorteAanroeper.PARAMETER_ANUMMER, TestClient.testdataService.getNieuwANummer());
        new InschrijvingGeboorteAanroeper(eigenschappen, parameterMap4).fire();

        // 5: Aangifte geborene
        if (!eigenschappen.getFlowPrevalidatie()) {
            parameterMap4.remove(BijhoudingAanroeper.PARAMETER_PREVALIDATIE);
        }
        new InschrijvingGeboorteAanroeper(eigenschappen, parameterMap4).fire();
    }

    /**
     * Run scenario bijhouding registreer huwelijk.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runScenarioBijhoudingRegistreerHuwelijk(final Eigenschappen eigenschappen) throws Exception {
        String bsn1 = TestClient.testdataService.getVrijgezelBsn();

        // 1: Details persoon partner1
        HashMap<String, String> parameterMap1 = new HashMap<String, String>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsn1);
        new VraagDetailsPersoonAanroeper(eigenschappen, parameterMap1).fire();

        String bsn2 = TestClient.testdataService.getVrijgezelBsn();

        // 2: Details persoon partner2
        HashMap<String, String> parameterMap2 = new HashMap<String, String>();
        parameterMap2.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsn2);
        new VraagDetailsPersoonAanroeper(eigenschappen, parameterMap2).fire();

        Random random = new Random();
        int referentienummer = random.nextInt(10000);

        // 3: Prevalidatie huwelijk
        HashMap<String, String> parameterMap3 = new HashMap<String, String>();
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_BSN1, bsn1);
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_BSN2, bsn2);
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_BSN_NAAMGEBRUIK, bsn1);
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_REFERENTIENUMMER, "" + referentienummer);
        parameterMap3.put(BijhoudingAanroeper.PARAMETER_PREVALIDATIE, "J");
        new RegistreerHuwelijkEnPartnerschapAanroeper(eigenschappen, parameterMap3).fire();

        // 4: Huwelijk
        if (!eigenschappen.getFlowPrevalidatie()) {
            parameterMap3.remove(BijhoudingAanroeper.PARAMETER_PREVALIDATIE);
        }
        new RegistreerHuwelijkEnPartnerschapAanroeper(eigenschappen, parameterMap3).fire();
    }

    /**
     * Run scenario bijhouding verhuizing.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runScenarioBijhoudingVerhuizing(final Eigenschappen eigenschappen) throws Exception {
        String bsn = TestClient.testdataService.getBsn();

        // 1: Details persoon aangever
        HashMap<String, String> parameterMap1 = new HashMap<String, String>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsn);
        new VraagDetailsPersoonAanroeper(eigenschappen, parameterMap1).fire();

        // 2: Opvragen medeverhuizers op huidig adres op basis van bsn
        HashMap<String, String> parameterMap2 = new HashMap<String, String>();
        parameterMap2.put(VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper.PARAMETER_BSN, bsn);
        new VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper(eigenschappen, parameterMap2).fire();

        Map<String, String> adres = TestClient.testdataService.getAdres();
        String huisnummer = adres.get(TestdataServiceImpl.HUISNUMMER);
        String huisletter = adres.get(TestdataServiceImpl.HUISLETTER);
        String huisnummertoevoeging = adres.get(TestdataServiceImpl.HUISNUMMERTOEVOEGING);
        String postcode = adres.get(TestdataServiceImpl.POSTCODE);

        // 3: Opvragen bewoners nieuw adres op basis van adres
        HashMap<String, String> parameterMap3 = new HashMap<String, String>();
        parameterMap3.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_HUISNUMMER, huisnummer);
        parameterMap3.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_HUISLETTER, huisletter);
        parameterMap3.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_HUISNUMMERTOEVOEGING,
                huisnummertoevoeging);
        parameterMap3.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_POSTCODE, postcode);
        new VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper(eigenschappen, parameterMap3).fire();

        // Wijzig aanvangsdatum om lazy initialization errors te voorkomen
        verhuizing_aanvangdatum.add(Calendar.DAY_OF_MONTH, 1);
        Date datumAanvangDate = verhuizing_aanvangdatum.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datumAanvang = sdf.format(datumAanvangDate);

        Map<String, String> adresNieuw = TestClient.testdataService.getAdres();
        String huisnummerNieuw = adresNieuw.get(TestdataServiceImpl.HUISNUMMER);
        String huisletterNieuw = adresNieuw.get(TestdataServiceImpl.HUISLETTER);
        String huisnummerToevoegingNieuw = adresNieuw.get(TestdataServiceImpl.HUISNUMMERTOEVOEGING);
        String postcodeNieuw = adresNieuw.get(TestdataServiceImpl.POSTCODE);
        String norNieuw = adresNieuw.get(TestdataServiceImpl.NOR);
        String wplNieuw = adresNieuw.get(TestdataServiceImpl.WOONPLAATSCODE);
        String gemeenteNieuw = adresNieuw.get(TestdataServiceImpl.GEMEENTECODE);

        // 4: Prevalidatie verhuizing
        HashMap<String, String> parameterMap4 = new HashMap<String, String>();
        parameterMap4.put(BijhoudingAanroeper.PARAMETER_PREVALIDATIE, "J");
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_DATUM_AANVANG_GELDIGHEID, datumAanvang);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_BSN, bsn);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_GEMEENTECODE, gemeenteNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_NAAM_OPENBARE_RUIMTE, norNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_WOONPLAATSCODE, wplNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_HUISNUMMER, huisnummerNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_HUISLETTER, huisletterNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_HUISNUMMERTOEVOEGING, huisnummerToevoegingNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_POSTCODE, postcodeNieuw);

        new VerhuizingAanroeper(eigenschappen, parameterMap4).fire();

        // 5: Verhuizing
        if (!eigenschappen.getFlowPrevalidatie()) {
            parameterMap4.remove(BijhoudingAanroeper.PARAMETER_PREVALIDATIE);
        }
        new VerhuizingAanroeper(eigenschappen, parameterMap4).fire();
    }

    /**
     * Run vraag details persoon.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runVraagDetailsPersoon(final Eigenschappen eigenschappen) throws Exception {
        String bsn = TestClient.testdataService.getBsn();

        HashMap<String, String> parameterMap1 = new HashMap<String, String>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsn);
        new VraagDetailsPersoonAanroeper(eigenschappen, parameterMap1).fire();
    }

    /**
     * Run vraag kandidaat vader.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runVraagKandidaatVader(final Eigenschappen eigenschappen) throws Exception {
        Map<String, String> huwelijk = TestClient.testdataService.getHuwelijk();
        String bsnMoeder = huwelijk.get(TestdataServiceImpl.HUWELIJK_PARTNER2);

        HashMap<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put(VraagKandidaatVaderAanroeper.PARAMETER_BSN, bsnMoeder);
        new VraagKandidaatVaderAanroeper(eigenschappen, parameterMap).fire();
    }

    /**
     * Run vraag personen op adres.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runVraagPersonenOpAdres(final Eigenschappen eigenschappen) throws Exception {
        Map<String, String> adres = TestClient.testdataService.getAdres();
        String huisnummer = adres.get(TestdataServiceImpl.HUISNUMMER);
        String huisletter = adres.get(TestdataServiceImpl.HUISLETTER);
        String huisnummertoevoeging = adres.get(TestdataServiceImpl.HUISNUMMERTOEVOEGING);
        String postcode = adres.get(TestdataServiceImpl.POSTCODE);

        // 3: Opvragen bewoners nieuw adres op basis van adres
        HashMap<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_HUISNUMMER, huisnummer);
        parameterMap.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_HUISLETTER, huisletter);
        parameterMap.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_HUISNUMMERTOEVOEGING,
                huisnummertoevoeging);
        parameterMap.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_POSTCODE, postcode);
        new VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper(eigenschappen, parameterMap).fire();
    }

    /**
     * Run vraag personen op adres via bsn.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runVraagPersonenOpAdresViaBsn(final Eigenschappen eigenschappen) throws Exception {
        String bsn = TestClient.testdataService.getBsn();

        HashMap<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put(VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper.PARAMETER_BSN, bsn);
        new VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper(eigenschappen, parameterMap).fire();
    }

    /**
     * Run bijhouding correctie adres.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runBijhoudingCorrectieAdres(final Eigenschappen eigenschappen) throws Exception {
        String bsn = TestClient.testdataService.getBsn();

        // Random Aanvangdatum correctie adres
        correctie_adres_aanvangdatum = Calendar.getInstance();
        correctie_adres_aanvangdatum.setTime(new Date());
        Random random = new Random();
        correctie_adres_aanvangdatum.add(Calendar.DAY_OF_YEAR, -(random.nextInt(1000) + 60));
        Date datumAanvangDate = correctie_adres_aanvangdatum.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datumAanvang = sdf.format(datumAanvangDate);

        // Einde geldigheid is 30 dagen later
        Calendar correctie_einde_geldigheid = Calendar.getInstance();
        correctie_einde_geldigheid.setTime(datumAanvangDate);
        correctie_einde_geldigheid.add(Calendar.DAY_OF_YEAR, 30);
        Date eindeGeldigheidDate = correctie_einde_geldigheid.getTime();
        String datumEinde = sdf.format(eindeGeldigheidDate);

        // Random geldig adres
        Map<String, String> adres = TestClient.testdataService.getAdres();
        String huisnummer = adres.get(TestdataServiceImpl.HUISNUMMER);
        String huisletter = adres.get(TestdataServiceImpl.HUISLETTER);
        String huisnummertoevoeging = adres.get(TestdataServiceImpl.HUISNUMMERTOEVOEGING);
        String postcode = adres.get(TestdataServiceImpl.POSTCODE);
        String nor = adres.get(TestdataServiceImpl.NOR);
        String wpl = adres.get(TestdataServiceImpl.WOONPLAATSCODE);
        String gemeente = adres.get(TestdataServiceImpl.GEMEENTECODE);

        int referentienummer = random.nextInt(10000);

        HashMap<String, String> parameterMap3 = new HashMap<String, String>();
        if (eigenschappen.getFlowPrevalidatie()) {
            parameterMap3.put(BijhoudingAanroeper.PARAMETER_PREVALIDATIE, "J");
        }
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_REFERENTIENUMMER, "" + referentienummer);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_BSN, bsn);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_NAAM_OPENBARE_RUIMTE, nor);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_POSTCODE, postcode);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_WOONPLAATS, wpl);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_HUISNUMMER, huisnummer);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_HUISLETTER, huisletter);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_HUISNUMMERTOEVOEGING, huisnummertoevoeging);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_GEMEENTECODE, gemeente);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_DATUM_AANVANG_GELDIGHEID, datumAanvang);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_DATUM_EINDE_GELDIGHEID, datumEinde);

        new CorrectieAdresAanroeper(eigenschappen, parameterMap3).fire();
    }

    /**
     * Run bijhouding inschrijving geboorte.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runBijhoudingInschrijvingGeboorte(final Eigenschappen eigenschappen) throws Exception {
        Map<String, String> huwelijk = TestClient.testdataService.getHuwelijk();
        String bsnVader = huwelijk.get(TestdataServiceImpl.HUWELIJK_PARTNER1);
        String bsnMoeder = huwelijk.get(TestdataServiceImpl.HUWELIJK_PARTNER2);
        String naam = huwelijk.get(TestdataServiceImpl.HUWELIJK_NAAM);
        String voorvoegsel = huwelijk.get(TestdataServiceImpl.HUWELIJK_VOORVOEGSEL);

        HashMap<String, String> parameterMap4 = new HashMap<String, String>();
        if (eigenschappen.getFlowPrevalidatie()) {
            parameterMap4.put(BijhoudingAanroeper.PARAMETER_PREVALIDATIE, "J");
        }

        String bsn = TestClient.testdataService.getNieuweBsn();
        String referentienummer = "" + new Random().nextInt(1000);

        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_REFERENTIENUMMER, referentienummer);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN, bsn);
        parameterMap4
                .put(InschrijvingGeboorteAanroeper.PARAMETER_ANUMMER, TestClient.testdataService.getNieuwANummer());
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_NAAM, naam);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_VOORVOEGSEL, voorvoegsel);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN_OUDER1, bsnVader);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN_OUDER2, bsnMoeder);

        new InschrijvingGeboorteAanroeper(eigenschappen, parameterMap4).fire();
    }

    /**
     * Run bijhouding registreer huwelijk.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runBijhoudingRegistreerHuwelijk(final Eigenschappen eigenschappen) throws Exception {
        String bsn1 = TestClient.testdataService.getVrijgezelBsn();

        String bsn2 = TestClient.testdataService.getVrijgezelBsn();

        Random random = new Random();
        int referentienummer = random.nextInt(10000);

        HashMap<String, String> parameterMap3 = new HashMap<String, String>();
        if (eigenschappen.getFlowPrevalidatie()) {
            parameterMap3.put(BijhoudingAanroeper.PARAMETER_PREVALIDATIE, "J");
        }
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_BSN1, bsn1);
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_BSN2, bsn2);
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_BSN_NAAMGEBRUIK, bsn1);
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_REFERENTIENUMMER, "" + referentienummer);

        new RegistreerHuwelijkEnPartnerschapAanroeper(eigenschappen, parameterMap3).fire();
    }

    /**
     * Run bijhouding verhuizing.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runBijhoudingVerhuizing(final Eigenschappen eigenschappen) throws Exception {
        String bsn = TestClient.testdataService.getBsn();

        // Wijzig aanvangsdatum om lazy initialization errors te voorkomen
        verhuizing_aanvangdatum.add(Calendar.DAY_OF_MONTH, 1);
        Date datumAanvangDate = verhuizing_aanvangdatum.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datumAanvang = sdf.format(datumAanvangDate);

        Map<String, String> adresNieuw = TestClient.testdataService.getAdres();
        String huisnummerNieuw = adresNieuw.get(TestdataServiceImpl.HUISNUMMER);
        String huisletterNieuw = adresNieuw.get(TestdataServiceImpl.HUISLETTER);
        String huisnummerToevoegingNieuw = adresNieuw.get(TestdataServiceImpl.HUISNUMMERTOEVOEGING);
        String postcodeNieuw = adresNieuw.get(TestdataServiceImpl.POSTCODE);
        String norNieuw = adresNieuw.get(TestdataServiceImpl.NOR);
        String wplNieuw = adresNieuw.get(TestdataServiceImpl.WOONPLAATSCODE);
        String gemeenteNieuw = adresNieuw.get(TestdataServiceImpl.GEMEENTECODE);

        // 4: Prevalidatie verhuizing
        HashMap<String, String> parameterMap4 = new HashMap<String, String>();
        if (eigenschappen.getFlowPrevalidatie()) {
            parameterMap4.put(BijhoudingAanroeper.PARAMETER_PREVALIDATIE, "J");
        }
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_DATUM_AANVANG_GELDIGHEID, datumAanvang);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_BSN, bsn);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_GEMEENTECODE, gemeenteNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_NAAM_OPENBARE_RUIMTE, norNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_WOONPLAATSCODE, wplNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_HUISNUMMER, huisnummerNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_HUISLETTER, huisletterNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_HUISNUMMERTOEVOEGING, huisnummerToevoegingNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_POSTCODE, postcodeNieuw);

        new VerhuizingAanroeper(eigenschappen, parameterMap4).fire();
    }
}
