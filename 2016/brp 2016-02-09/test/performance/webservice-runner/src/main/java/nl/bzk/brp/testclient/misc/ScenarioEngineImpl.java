/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.misc;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import nl.bzk.brp.bijhouding.service.BhgAfstamming;
import nl.bzk.brp.bijhouding.service.BhgBevraging;
import nl.bzk.brp.bijhouding.service.BhgHuwelijkGeregisteerdPartnerschap;
import nl.bzk.brp.bijhouding.service.BhgOverlijden;
import nl.bzk.brp.bijhouding.service.BhgVerblijfAdres;
import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.aanroepers.AbstractAanroeper;
import nl.bzk.brp.testclient.aanroepers.bevraging.VraagDetailsPersoonAanroeper;
import nl.bzk.brp.testclient.aanroepers.bevraging.VraagKandidaatVaderAanroeper;
import nl.bzk.brp.testclient.aanroepers.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper;
import nl.bzk.brp.testclient.aanroepers.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper;
import nl.bzk.brp.testclient.aanroepers.bijhouding.AdoptieAanroeper;
import nl.bzk.brp.testclient.aanroepers.bijhouding.CorrectieAdresAanroeper;
import nl.bzk.brp.testclient.aanroepers.bijhouding.InschrijvingGeboorteAanroeper;
import nl.bzk.brp.testclient.aanroepers.bijhouding.InschrijvingGeboorteMetErkenningAanroeper;
import nl.bzk.brp.testclient.aanroepers.bijhouding.OverlijdenAanroeper;
import nl.bzk.brp.testclient.aanroepers.bijhouding.RegistreerHuwelijkEnPartnerschapAanroeper;
import nl.bzk.brp.testclient.aanroepers.bijhouding.VerhuizingAanroeper;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Bericht.TYPE;
import nl.bzk.brp.testclient.testdata.BsnPers;
import nl.bzk.brp.testclient.testdata.TestdataServiceImpl;
import nl.bzk.brp.testclient.util.DatumUtil;
import nl.bzk.brp.testclient.util.ObjectSleutelHandler;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class ScenarioEngineImpl.
 */
public class ScenarioEngineImpl implements ScenarioEngine {

    /** De Constante LOG. */
    private static final Logger LOG                      = LoggerFactory.getLogger(ScenarioEngineImpl.class);

    /** De Constante URL. */
    public static final String BEVRAGING_BIJHOUDING_URL = "%s://%s:%s/%s/BijhoudingBevragingService/bhgBevraging";
    /** De Constante URL. */
    public static final String BIJHOUDING_AFSTAMMING_URL = "%s://%s:%s/%s/BijhoudingService/bhgAfstamming";
    public static final String BIJHOUDING_HUWELIJK_GP_URL = "%s://%s:%s/%s/BijhoudingService/bhgHuwelijkGeregisteerdPartnerschap";
    public static final String BIJHOUDING_OVERLIJDEN_URL = "%s://%s:%s/%s/BijhoudingService/bhgOverlijden";
    public static final String BIJHOUDING_VERBLIJFADRES_URL = "%s://%s:%s/%s/BijhoudingService/bhgVerblijfAdres";

    private final Calendar verhuizingAanvangdatum;
    private Calendar correctieAdresAanvangdatum;
    private int correctieAdresHuisnummer = 0;

    private BhgAfstamming bhgAfstammingPortType;
    private BhgBevraging bevragingPortType;
    private BhgHuwelijkGeregisteerdPartnerschap bhgHuwelijkGeregisteerdPartnerschapPortType;
    private BhgOverlijden bhgOverlijdenPortType;
    private BhgVerblijfAdres bhgVerblijfAdresPortType;

    /**
     * Instantieert een nieuwe scenario engine impl.
     */
    public ScenarioEngineImpl() {
        // Startpunt Aanvangdatum verhuizing
        verhuizingAanvangdatum = Calendar.getInstance();
        verhuizingAanvangdatum.setTime(new Date());
        verhuizingAanvangdatum.add(Calendar.YEAR, -Constanten.DRIE);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.misc.ScenarioEngine#runScenario(nl.bzk.brp.testclient.misc.Bericht.BERICHT)
     */
    @Override
    public void runScenario(final Bericht.BERICHT bericht, final Eigenschappen eigenschappen) throws Exception {
        
        // LOG.info("Start: " + bericht.name());
        
        switch (bericht) {
//            case BIJHOUDING_CORRECTIE_ADRES:
//                runScenarioBijhoudingCorrectieAdres(eigenschappen);
//                break;
            case BIJHOUDING_GEBOORTE:
                runScenarioBijhoudingGeboorte(eigenschappen);
                break;
            case BIJHOUDING_HUWELIJK:
                runScenarioBijhoudingHuwelijk(eigenschappen);
                break;
            case BIJHOUDING_OVERLIJDEN:
                runScenarioBijhoudingOverlijden(eigenschappen);
                break;
            case BIJHOUDING_VERHUIZING:
                runScenarioBijhoudingVerhuizing(eigenschappen);
                break;
//            case BIJHOUDING_ADOPTIE:
//                runScenarioBijhoudingAdoptie(eigenschappen);
//                break;
            case BIJHOUDING_GEBOORTE_MET_ERKENNING:
                runScenarioBijhoudingGeboorteMetErkenning(eigenschappen);
                break;
            default:
                break;
        }
        
        // LOG.info("Einde: " + bericht.name());
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
            case VRAAG_PERSONEN_ADRES:
                runVraagPersonenAdres(eigenschappen);
                break;
            case VRAAG_PERSONEN_ADRES_VIA_BSN:
                runVraagPersonenAdresViaBsn(eigenschappen);
                break;
            case BIJHOUDING_CORRECTIE_ADRES:
                runBijhoudingCorrectieAdres(eigenschappen);
                break;
            case BIJHOUDING_GEBOORTE:
                runBijhoudingGeboorte(eigenschappen);
                break;
            case BIJHOUDING_HUWELIJK:
                runBijhoudingHuwelijk(eigenschappen);
                break;
            case BIJHOUDING_OVERLIJDEN:
                runBijhoudingOverlijden(eigenschappen);
                break;
            case BIJHOUDING_VERHUIZING:
                runBijhoudingVerhuizing(eigenschappen);
                break;
            case BIJHOUDING_ADOPTIE:
                runBijhoudingAdoptie(eigenschappen);
                break;
            case BIJHOUDING_GEBOORTE_MET_ERKENNING:
                runBijhoudingGeboorteMetErkenning(eigenschappen);
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
        for (BERICHT bericht : Bericht.getBerichtenVanType(type)) {
            runBericht(bericht, eigenschappen);
        }
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
        HashMap<String, String> parameterMap1 = new HashMap<>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsn);
        new VraagDetailsPersoonAanroeper(eigenschappen, getBhgBevragingPortType(eigenschappen), parameterMap1).fire();

        // 2: Opvragen medebewoners op adres op basis van bsn
        HashMap<String, String> parameterMap2 = new HashMap<>();
        parameterMap2.put(VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper.PARAMETER_BSN, bsn);
        new VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper(eigenschappen,
                                                                       getBhgBevragingPortType(eigenschappen),
                                                                       parameterMap2).fire();

        // Random Aanvangdatum correctie adres
        correctieAdresAanvangdatum = Calendar.getInstance();
        correctieAdresAanvangdatum.setTime(new Date());
        Random random = new Random();
        correctieAdresAanvangdatum.add(Calendar.DAY_OF_YEAR, -(random.nextInt(Constanten.DUIZEND) + Constanten.ZESTIG));
        Date datumAanvangDate = correctieAdresAanvangdatum.getTime();
        String datumAanvang = DatumUtil.datumNaarXmlString(datumAanvangDate);

        // Einde geldigheid is 30 dagen later
        Calendar correctieEindeGeldigheid = Calendar.getInstance();
        correctieEindeGeldigheid.setTime(datumAanvangDate);
        correctieEindeGeldigheid.add(Calendar.DAY_OF_YEAR, Constanten.DERTIG);
        Date eindeGeldigheidDate = correctieEindeGeldigheid.getTime();
        String datumEinde = DatumUtil.datumNaarXmlString(eindeGeldigheidDate);

        // Random geldig adres
        Map<String, String> adres = TestClient.testdataService.getAdres();
        String huisnummer = adres.get(TestdataServiceImpl.HUISNUMMER);
        String huisletter = adres.get(TestdataServiceImpl.HUISLETTER);
        String huisnummerToevoeging = adres.get(TestdataServiceImpl.HUISNUMMERTOEVOEGING);
        String postcode = adres.get(TestdataServiceImpl.POSTCODE);
        String nor = adres.get(TestdataServiceImpl.NOR);
        String wpl = adres.get(TestdataServiceImpl.WOONPLAATSNAAM);
        String gemeente = adres.get(TestdataServiceImpl.GEMEENTECODE);

        // TODO: analyseren waar deze sporadische melding door komt:
        // datum aanvang: 20120311 - datumEinde: 20120410 - gemeente: 0344 - wpl: 3295
        // Melding CorrectieAdresCommand referentienummer 12345:
        // "De gemeente en gemeentecode is niet (of niet volledig) geldig binnen de opgegeven periode.
        // (Gemeentecode = 0344)."
        // (regel: BRBY0527)
        // Melding CorrectieAdresCommand referentienummer 12345:
        // "Datum einde geldigheid dient na datum aanvang geldigheid te liggen." (regel: BRBY0032)

        // log.debug("datum aanvang: " + datumAanvang + " - datumEinde: " + datumEinde + " - gemeente: " + gemeente +
        // " - wpl: " + wpl);

        // 3: Prevalidatie correctie adres
        HashMap<String, String> parameterMap3 = new HashMap<>();
        parameterMap3.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");
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
        new CorrectieAdresAanroeper(eigenschappen, getBhgVerblijfAdresPortType(eigenschappen), parameterMap3).fire();

        if (!eigenschappen.getFlowPrevalidatie()) {
            // 4: Correctie adres
            parameterMap3.remove(AbstractAanroeper.PARAMETER_PREVALIDATIE);
        }
        new CorrectieAdresAanroeper(eigenschappen, getBhgVerblijfAdresPortType(eigenschappen), parameterMap3).fire();

        if (correctieAdresHuisnummer > 9999) {
            correctieAdresHuisnummer = 0;
        }
    }

    /**
     * Creeer port type voor verblijfadres.
     *
     * @param eigenschappen de eigenschappen
     * @return de bijhouding port type
     * @throws Exception de exception
     */
    public BhgVerblijfAdres getBhgVerblijfAdresPortType(final Eigenschappen eigenschappen) throws Exception {
        if (bhgVerblijfAdresPortType == null) {
            bhgVerblijfAdresPortType =
                    (BhgVerblijfAdres) maakBijhoudingPortType(BhgVerblijfAdres.class, eigenschappen,
                                                              BIJHOUDING_VERBLIJFADRES_URL);
        }
        return bhgVerblijfAdresPortType;
    }

    /**
     * Creeer port type voor overlijden.
     *
     * @param eigenschappen de eigenschappen
     * @return de bijhouding port type
     * @throws Exception de exception
     */
    public BhgOverlijden getBhgOverlijdenPortType(final Eigenschappen eigenschappen) throws Exception {
        if (bhgOverlijdenPortType == null) {
            bhgOverlijdenPortType = (BhgOverlijden) maakBijhoudingPortType(BhgOverlijden.class, eigenschappen,
                                                                           BIJHOUDING_OVERLIJDEN_URL);
        }
        return bhgOverlijdenPortType;
    }

    /**
     * Creeer port type voor afstamming.
     *
     * @param eigenschappen de eigenschappen
     * @return de bijhouding port type
     * @throws Exception de exception
     */
    public BhgAfstamming getBhgAfstammingPortType(final Eigenschappen eigenschappen) throws Exception {
        if (bhgAfstammingPortType == null) {
            bhgAfstammingPortType = (BhgAfstamming) maakBijhoudingPortType(BhgAfstamming.class, eigenschappen,
                                                                           BIJHOUDING_AFSTAMMING_URL);
        }
        return bhgAfstammingPortType;
    }

    /**
     * Creeer port type voor huwelijk en geregistreerd partnerschap.
     *
     * @param eigenschappen de eigenschappen
     * @return de bijhouding port type
     * @throws Exception de exception
     */
    public BhgHuwelijkGeregisteerdPartnerschap getBhgHuwelijkGeregisteerdPartnerschapPortType(
            final Eigenschappen eigenschappen) throws Exception {
        if (bhgHuwelijkGeregisteerdPartnerschapPortType == null) {
            bhgHuwelijkGeregisteerdPartnerschapPortType = (BhgHuwelijkGeregisteerdPartnerschap)
                    maakBijhoudingPortType(BhgHuwelijkGeregisteerdPartnerschap.class, eigenschappen,
                                           BIJHOUDING_HUWELIJK_GP_URL);
        }
        return bhgHuwelijkGeregisteerdPartnerschapPortType;
    }



    /**
     * Maak bijhouding port type.
     *
     * @param portTypeClass the port type class
     * @param eigenschappen the eigenschappen
     * @return the object
     */
    private Object maakBijhoudingPortType(final Class portTypeClass, final Eigenschappen eigenschappen,
                                          final String endpoint) {
        final JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        proxyFactory.setServiceClass(portTypeClass);
        final String url = String.format(endpoint, eigenschappen.getProtocolBijhouding(),
                                   eigenschappen.getHostBijhouding(), eigenschappen.getPortBijhouding(),
                                   eigenschappen.getContextRootBijhouding());
        proxyFactory.setAddress(url);
        return proxyFactory.create();
    }


    /**
     * Creeer BhgBevraging port type.
     *
     * @param eigenschappen de eigenschappen
     * @return de bevraging port type
     * @throws Exception de exception
     */
    private BhgBevraging getBhgBevragingPortType(final Eigenschappen eigenschappen) throws Exception {
        if (bevragingPortType == null) {
            JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
            proxyFactory.setServiceClass(BhgBevraging.class);
            String url = String.format(BEVRAGING_BIJHOUDING_URL, eigenschappen.getProtocolBevraging(),
                                       eigenschappen.getHostBevraging(), eigenschappen.getPortBevraging(),
                                       eigenschappen.getContextRootBevraging());
            proxyFactory.setAddress(url);
            bevragingPortType = (BhgBevraging) proxyFactory.create();
        }
        return bevragingPortType;
    }


    /**
     * Run scenario bijhouding inschrijving geboorte.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runScenarioBijhoudingGeboorte(final Eigenschappen eigenschappen) throws Exception {
        Map<String, String> huwelijk = TestClient.testdataService.getHuwelijk();
        String bsnVader = huwelijk.get(TestdataServiceImpl.HGP_MAN_BSN);
        String bsnMoeder = huwelijk.get(TestdataServiceImpl.HGP_VROUW_BSN);
        String naam = huwelijk.get(TestdataServiceImpl.HGP_MAN_NAAM);
        String voorvoegsel = huwelijk.get(TestdataServiceImpl.HGP_MAN_VOORVOEGSEL);
        String scheidingsteken = huwelijk.get(TestdataServiceImpl.HGP_MAN_SCHEIDINGSTEKEN);
        String metKinderenMetAndereNaam = huwelijk.get(TestdataServiceImpl.HGP_MET_KINDEREN_MET_ANDERE_NAAM);

        // 1: Vraagdetails aangever geboorte
        HashMap<String, String> parameterMap1 = new HashMap<>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsnVader);
        new VraagDetailsPersoonAanroeper(eigenschappen, getBhgBevragingPortType(eigenschappen), parameterMap1).fire();

        // 2: Vraagdetails moeder
        HashMap<String, String> parameterMap2 = new HashMap<>();
        parameterMap2.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsnMoeder);
        new VraagDetailsPersoonAanroeper(eigenschappen, getBhgBevragingPortType(eigenschappen), parameterMap2).fire();

        // 3: Vraag kandidaat vader
        HashMap<String, String> parameterMap3 = new HashMap<>();
        parameterMap3.put(VraagKandidaatVaderAanroeper.PARAMETER_BSN, bsnMoeder);
        new VraagKandidaatVaderAanroeper(eigenschappen, getBhgBevragingPortType(eigenschappen), parameterMap3).fire();

        // 4: Prevalidatie aangifte geborene
        String bsn = TestClient.testdataService.getNieuweBsn();
        String anr = TestClient.testdataService.getNieuwANummer();

        HashMap<String, String> parameterMap4 = new HashMap<>();
        parameterMap4.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN, bsn);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_NAAM, naam);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_VOORVOEGSEL, voorvoegsel);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_SCHEIDINGSTEKEN, scheidingsteken);
        
        Long ouder1_persId = Long.valueOf(huwelijk.get(TestdataServiceImpl.HGP_MAN_ID));
        String ouder1_persId_crypted = ObjectSleutelHandler.genereerObjectSleutelString(ouder1_persId, Integer.valueOf("101"), System.currentTimeMillis());
        
        Long ouder2_persId = Long.valueOf(huwelijk.get(TestdataServiceImpl.HGP_VROUW_ID));
        String ouder2_persId_crypted = ObjectSleutelHandler.genereerObjectSleutelString(ouder2_persId, Integer.valueOf("101"), System.currentTimeMillis());
        
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN_OUDER1, ouder1_persId_crypted);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN_OUDER2, ouder2_persId_crypted);
        
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_ANUMMER, anr);
        if (metKinderenMetAndereNaam != null && Boolean.parseBoolean(metKinderenMetAndereNaam)) {
            parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_KINDEREN_MET_ANDERE_NAAM,
                              Boolean.TRUE.toString());
        }

        new InschrijvingGeboorteAanroeper(eigenschappen, getBhgAfstammingPortType(eigenschappen), parameterMap4).fire();

        // 5: Aangifte geborene
        if (!eigenschappen.getFlowPrevalidatie()) {
            parameterMap4.remove(AbstractAanroeper.PARAMETER_PREVALIDATIE);
        }
        new InschrijvingGeboorteAanroeper(eigenschappen, getBhgAfstammingPortType(eigenschappen), parameterMap4).fire();
    }

    /**
     * Run scenario bijhouding inschrijving geboorte met erkenning.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runScenarioBijhoudingGeboorteMetErkenning(final Eigenschappen eigenschappen) throws Exception {
        Map<String, String> geregistreerdpartnerschap = TestClient.testdataService.getGeregistreerdPartnerschap();
        String bsnVader = geregistreerdpartnerschap.get(TestdataServiceImpl.HGP_MAN_BSN);
        String bsnMoeder = geregistreerdpartnerschap.get(TestdataServiceImpl.HGP_VROUW_BSN);
        String naam = geregistreerdpartnerschap.get(TestdataServiceImpl.HGP_MAN_NAAM);
        String voorvoegsel = geregistreerdpartnerschap.get(TestdataServiceImpl.HGP_MAN_VOORVOEGSEL);
        String scheidingsteken = geregistreerdpartnerschap.get(TestdataServiceImpl.HGP_MAN_SCHEIDINGSTEKEN);
        String metKinderenMetAndereNaam =
                geregistreerdpartnerschap.get(TestdataServiceImpl.HGP_MET_KINDEREN_MET_ANDERE_NAAM);

        // 1: Vraagdetails aangever geboorte
        HashMap<String, String> parameterMap1 = new HashMap<>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsnVader);
        new VraagDetailsPersoonAanroeper(eigenschappen, getBhgBevragingPortType(eigenschappen), parameterMap1).fire();

        // 2: Vraagdetails moeder
        HashMap<String, String> parameterMap2 = new HashMap<>();
        parameterMap2.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsnMoeder);
        new VraagDetailsPersoonAanroeper(eigenschappen, getBhgBevragingPortType(eigenschappen), parameterMap2).fire();

        // 3: Vraag kandidaat vader
        HashMap<String, String> parameterMap3 = new HashMap<>();
        parameterMap3.put(VraagKandidaatVaderAanroeper.PARAMETER_BSN, bsnMoeder);
        new VraagKandidaatVaderAanroeper(eigenschappen, getBhgBevragingPortType(eigenschappen), parameterMap3).fire();

	// 4: Prevalidatie
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");

        String bsn = TestClient.testdataService.getNieuweBsn();
        String referentienummer = "" + new Random().nextInt(Constanten.DUIZEND);

        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_REFERENTIENUMMER, referentienummer);
        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN, bsn);
        parameters
		.put(InschrijvingGeboorteAanroeper.PARAMETER_ANUMMER, TestClient.testdataService.getNieuwANummer());
        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_NAAM, naam);
        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_VOORVOEGSEL, voorvoegsel);
        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_SCHEIDINGSTEKEN, scheidingsteken);
        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN_OUDER1, bsnVader);
        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN_OUDER2, bsnMoeder);
        if (metKinderenMetAndereNaam != null && Boolean.parseBoolean(metKinderenMetAndereNaam)) {
            parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_KINDEREN_MET_ANDERE_NAAM,
                           Boolean.TRUE.toString());
        }

        new InschrijvingGeboorteMetErkenningAanroeper(eigenschappen,
                                                      getBhgAfstammingPortType(eigenschappen),
                                                      parameters).fire();

	// 5: Aangifte geborene met erkenning
	if (!eigenschappen.getFlowPrevalidatie()) {
	    parameters.remove(AbstractAanroeper.PARAMETER_PREVALIDATIE);
	}
	new InschrijvingGeboorteMetErkenningAanroeper(eigenschappen,
						      getBhgAfstammingPortType(eigenschappen),
						      parameters).fire();
    }

    /**
     * Run scenario bijhouding registreer huwelijk.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runScenarioBijhoudingHuwelijk(final Eigenschappen eigenschappen) throws Exception {
        BsnPers bsn1 = TestClient.testdataService.getVrijgezelBsn();

        // 1: Details persoon partner1
        HashMap<String, String> parameterMap1 = new HashMap<>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsn1.bsn.toString());
        new VraagDetailsPersoonAanroeper(eigenschappen, getBhgBevragingPortType(eigenschappen), parameterMap1).fire();

        BsnPers bsn2 = TestClient.testdataService.getVrijgezelBsn();

        // 2: Details persoon partner2
        HashMap<String, String> parameterMap2 = new HashMap<>();
        parameterMap2.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsn2.bsn.toString());
        new VraagDetailsPersoonAanroeper(eigenschappen, getBhgBevragingPortType(eigenschappen), parameterMap2).fire();

        Random random = new Random();
        int referentienummer = random.nextInt(Constanten.TIENDUIZEND);

        // 3: Prevalidatie huwelijk
        HashMap<String, String> parameterMap3 = new HashMap<>();
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_BSN1, bsn1.persId);
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_BSN2, bsn2.persId);
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_BSN_NAAMGEBRUIK, bsn1.persId);
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_REFERENTIENUMMER, "" + referentienummer);
        parameterMap3.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");
        new RegistreerHuwelijkEnPartnerschapAanroeper(eigenschappen,
                                                      getBhgHuwelijkGeregisteerdPartnerschapPortType(eigenschappen),
                                                      parameterMap3).fire();

        // 4: Huwelijk
        if (!eigenschappen.getFlowPrevalidatie()) {
            parameterMap3.remove(AbstractAanroeper.PARAMETER_PREVALIDATIE);
        }
        new RegistreerHuwelijkEnPartnerschapAanroeper(eigenschappen,
                                                      getBhgHuwelijkGeregisteerdPartnerschapPortType(eigenschappen),
                                                      parameterMap3).fire();
    }

    /**
     * Run scenario bijhouding overlijden.
     *
     * @param eigenschappen eigenschappen
     * @throws Exception exception
     */
    private void runScenarioBijhoudingOverlijden(final Eigenschappen eigenschappen) throws Exception {
        // was een willekeurig test persoon, maar geeft probleme, omdat bij overlijden ook het huwelijk werd
        // ontbonden en de server kan niet tegen bepaalde situaties.
        // Voor zover ik zag was deze persoon gescheiden (en officieel is huwelijk beeindigt), maar de server probeert
        // nog steeds het huwelijk te ontbinden.
        // Dus totdat die bug is opgelost, gebruiken we de vrijgezellen lijst.
        BsnPers bsn = TestClient.testdataService.getVrijgezelBsn();

        // 1: Details persoon
        
        
        HashMap<String, String> parameterMap1 = new HashMap<>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsn.bsn.toString());
        new VraagDetailsPersoonAanroeper(eigenschappen, getBhgBevragingPortType(eigenschappen), parameterMap1).fire();

        // 2: Prevalidatie overlijden
        HashMap<String, String> parameterMap2 = new HashMap<>();
        parameterMap2.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");

        String referentienummer = "" + new Random().nextInt(Constanten.DUIZEND);

        Date datumAanvangDate = new Date();
        String datumAanvang = DatumUtil.datumNaarXmlString(datumAanvangDate);

        // Random geldig adres
        Map<String, String> adres = TestClient.testdataService.getAdres();
        String wplnaam = adres.get(TestdataServiceImpl.WOONPLAATSNAAM);
        String gemeentecode = adres.get(TestdataServiceImpl.GEMEENTECODE);
        String landcode = adres.get(TestdataServiceImpl.LANDCODE);

        parameterMap2.put(OverlijdenAanroeper.PARAMETER_REFERENTIENUMMER, referentienummer);
        parameterMap2.put(OverlijdenAanroeper.PARAMETER_BSN, bsn.persId.toString());
        parameterMap2.put(OverlijdenAanroeper.PARAMETER_DATUM_AANVANG_GELDIGHEID, datumAanvang);
        parameterMap2.put(OverlijdenAanroeper.PARAMETER_DATUM_OVERLIJDEN, datumAanvang);
        parameterMap2.put(OverlijdenAanroeper.PARAMETER_GEMEENTECODE, gemeentecode);
        parameterMap2.put(OverlijdenAanroeper.PARAMETER_LANDCODE, landcode);
        parameterMap2.put(OverlijdenAanroeper.PARAMETER_WOONPLAATSNAAM, wplnaam);

        new OverlijdenAanroeper(eigenschappen, getBhgOverlijdenPortType(eigenschappen), parameterMap2).fire();

        // 3: Overlijden
        if (!eigenschappen.getFlowPrevalidatie()) {
            parameterMap2.remove(AbstractAanroeper.PARAMETER_PREVALIDATIE);
        }
        new OverlijdenAanroeper(eigenschappen, getBhgOverlijdenPortType(eigenschappen), parameterMap2).fire();
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
        HashMap<String, String> parameterMap1 = new HashMap<>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsn);
        new VraagDetailsPersoonAanroeper(eigenschappen, getBhgBevragingPortType(eigenschappen), parameterMap1).fire();

        // 2: Opvragen medeverhuizers op huidig adres op basis van bsn
        HashMap<String, String> parameterMap2 = new HashMap<>();
        parameterMap2.put(VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper.PARAMETER_BSN, bsn);
        new VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper(eigenschappen,
                                                                       getBhgBevragingPortType(eigenschappen),
                                                                       parameterMap2).fire();

        Map<String, String> adres = TestClient.testdataService.getAdres();
        String huisnummer = adres.get(TestdataServiceImpl.HUISNUMMER);
        String huisletter = adres.get(TestdataServiceImpl.HUISLETTER);
        String huisnummertoevoeging = adres.get(TestdataServiceImpl.HUISNUMMERTOEVOEGING);
        String postcode = adres.get(TestdataServiceImpl.POSTCODE);

        // 3: Opvragen bewoners nieuw adres op basis van adres
        HashMap<String, String> parameterMap3 = new HashMap<>();
        parameterMap3.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_HUISNUMMER, huisnummer);
        parameterMap3.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_HUISLETTER, huisletter);
        parameterMap3.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_HUISNUMMERTOEVOEGING,
                huisnummertoevoeging);
        parameterMap3.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_POSTCODE, postcode);
        new VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper(eigenschappen,
                                                                 getBhgBevragingPortType(eigenschappen),
                                                                 parameterMap3).fire();

	// Veilig, omdat we ander ook in de problemen komen met niet geldige woonplaatsen, gemeentes, landen.
	verhuizingAanvangdatum.setTime(new Date());
	verhuizingAanvangdatum.add(Calendar.DATE, -Constanten.TWEE);
	final Date datumAanvangDate = verhuizingAanvangdatum.getTime();
	final String datumAanvang = DatumUtil.datumNaarXmlString(datumAanvangDate);

        final Map<String, String> adresNieuw = TestClient.testdataService.getAdres();
        String huisnummerNieuw = adresNieuw.get(TestdataServiceImpl.HUISNUMMER);
        String huisletterNieuw = adresNieuw.get(TestdataServiceImpl.HUISLETTER);
        String huisnummerToevoegingNieuw = adresNieuw.get(TestdataServiceImpl.HUISNUMMERTOEVOEGING);
        String postcodeNieuw = adresNieuw.get(TestdataServiceImpl.POSTCODE);
        String norNieuw = adresNieuw.get(TestdataServiceImpl.NOR);
        String wplNieuw = adresNieuw.get(TestdataServiceImpl.WOONPLAATSNAAM);
        String gemeenteNieuw = adresNieuw.get(TestdataServiceImpl.GEMEENTECODE);

        // 4: Prevalidatie verhuizing
        final HashMap<String, String> parameterMap4 = new HashMap<>();
        parameterMap4.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_DATUM_AANVANG_GELDIGHEID, datumAanvang);
        
        Long persId = TestClient.testdataService.getPersIdByBsn(Long.valueOf(bsn));
        String crypted = ObjectSleutelHandler.genereerObjectSleutelString(persId, Integer.valueOf("101"), System.currentTimeMillis());
        
        //parameterMap4.put(VerhuizingAanroeper.PARAMETER_BSN, bsn);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_BSN, crypted);
        
        
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_GEMEENTECODE, gemeenteNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_NAAM_OPENBARE_RUIMTE, norNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_WOONPLAATSNAAM, wplNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_HUISNUMMER, huisnummerNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_HUISLETTER, huisletterNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_HUISNUMMERTOEVOEGING, huisnummerToevoegingNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_POSTCODE, postcodeNieuw);

        new VerhuizingAanroeper(eigenschappen, getBhgVerblijfAdresPortType(eigenschappen), parameterMap4).fire();

        // 5: Verhuizing
        if (!eigenschappen.getFlowPrevalidatie()) {
            parameterMap4.remove(AbstractAanroeper.PARAMETER_PREVALIDATIE);
        }
        new VerhuizingAanroeper(eigenschappen, getBhgVerblijfAdresPortType(eigenschappen), parameterMap4).fire();
    }

    /**
     * Run scenario bijhouding adoptie.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runScenarioBijhoudingAdoptie(final Eigenschappen eigenschappen) throws Exception {
	final Map<String, String> adoptieKind = TestClient.testdataService.getAdoptieKind();
	final String bsnKind = adoptieKind.get(TestdataServiceImpl.ADOPTIE_KIND_BSN);

        final Map<String, String> huwelijk = TestClient.testdataService.getHuwelijk();
        final String bsnVader = huwelijk.get(TestdataServiceImpl.HGP_MAN_BSN);
        final String bsnMoeder = huwelijk.get(TestdataServiceImpl.HGP_VROUW_BSN);

	final String relatieId = adoptieKind.get(TestdataServiceImpl.ADOPTIE_RELATIE_ID);
	final String kindBetrId = adoptieKind.get(TestdataServiceImpl.ADOPTIE_KIND_BETR_ID);

        final HashMap<String, String> parameterMapVraagDetails = new HashMap<>();

        // 1: Details persoon kind
        parameterMapVraagDetails.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsnKind);
        new VraagDetailsPersoonAanroeper(eigenschappen,
                                         getBhgBevragingPortType(eigenschappen),
                                         parameterMapVraagDetails).fire();

        // 2: Details persoon Vader
        parameterMapVraagDetails.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsnVader);
        new VraagDetailsPersoonAanroeper(eigenschappen,
                                         getBhgBevragingPortType(eigenschappen),
                                         parameterMapVraagDetails).fire();

        // 3: Details persoon Moeder
        parameterMapVraagDetails.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsnMoeder);
        new VraagDetailsPersoonAanroeper(eigenschappen,
                                         getBhgBevragingPortType(eigenschappen),
                                         parameterMapVraagDetails).fire();

        // 3: Prevalidatie adoptie
        final HashMap<String, String> parameterMapAdoptie = new HashMap<>();
        parameterMapAdoptie.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");
        parameterMapAdoptie.put(AdoptieAanroeper.PARAMETER_BSN, bsnKind);
        parameterMapAdoptie.put(AdoptieAanroeper.PARAMETER_BSN_OUDER1, bsnVader);
        parameterMapAdoptie.put(AdoptieAanroeper.PARAMETER_BSN_OUDER2, bsnMoeder);
	parameterMapAdoptie.put(AdoptieAanroeper.PARAMETER_ID_RELATIE, relatieId);
	parameterMapAdoptie.put(AdoptieAanroeper.PARAMETER_ID_BETR, kindBetrId);

        new AdoptieAanroeper(eigenschappen,
                             getBhgAfstammingPortType(eigenschappen),
                             parameterMapAdoptie).fire();

        // 4: Adoptie
        if (!eigenschappen.getFlowPrevalidatie()) {
            parameterMapAdoptie.remove(AbstractAanroeper.PARAMETER_PREVALIDATIE);
        }
        new AdoptieAanroeper(eigenschappen, getBhgAfstammingPortType(eigenschappen), parameterMapAdoptie).fire();
    }

    /**
     * Run vraag details persoon.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runVraagDetailsPersoon(final Eigenschappen eigenschappen) throws Exception {
        String bsn = TestClient.testdataService.getBsn();

        HashMap<String, String> parameterMap1 = new HashMap<>();
        parameterMap1.put(VraagDetailsPersoonAanroeper.PARAMETER_BSN, bsn);
        new VraagDetailsPersoonAanroeper(eigenschappen, getBhgBevragingPortType(eigenschappen), parameterMap1).fire();
    }

    /**
     * Run vraag kandidaat vader.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runVraagKandidaatVader(final Eigenschappen eigenschappen) throws Exception {
        Map<String, String> huwelijk = TestClient.testdataService.getHuwelijk();
        String bsnMoeder = huwelijk.get(TestdataServiceImpl.HGP_VROUW_BSN);

        HashMap<String, String> parameterMap = new HashMap<>();
        parameterMap.put(VraagKandidaatVaderAanroeper.PARAMETER_BSN, bsnMoeder);
        new VraagKandidaatVaderAanroeper(eigenschappen, getBhgBevragingPortType(eigenschappen), parameterMap).fire();
    }

    /**
     * Run vraag personen op adres.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runVraagPersonenAdres(final Eigenschappen eigenschappen) throws Exception {
        Map<String, String> adres = TestClient.testdataService.getAdres();
        String huisnummer = adres.get(TestdataServiceImpl.HUISNUMMER);
        String huisletter = adres.get(TestdataServiceImpl.HUISLETTER);
        String huisnummertoevoeging = adres.get(TestdataServiceImpl.HUISNUMMERTOEVOEGING);
        String postcode = adres.get(TestdataServiceImpl.POSTCODE);

        // 3: Opvragen bewoners nieuw adres op basis van adres
        HashMap<String, String> parameterMap = new HashMap<>();
        parameterMap.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_HUISNUMMER, huisnummer);
        parameterMap.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_HUISLETTER, huisletter);
        parameterMap.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_HUISNUMMERTOEVOEGING,
                huisnummertoevoeging);
        parameterMap.put(VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper.PARAMETER_POSTCODE, postcode);
        new VraagPersonenOpAdresInclusiefBetrokkenhedenAanroeper(eigenschappen,
                                                                 getBhgBevragingPortType(eigenschappen),
                                                                 parameterMap).fire();
    }

    /**
     * Run vraag personen op adres via bsn.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runVraagPersonenAdresViaBsn(final Eigenschappen eigenschappen) throws Exception {
        String bsn = TestClient.testdataService.getBsn();

        HashMap<String, String> parameterMap = new HashMap<>();
        parameterMap.put(VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper.PARAMETER_BSN, bsn);
        new VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper(eigenschappen,
                                                                       getBhgBevragingPortType(eigenschappen),
                                                                       parameterMap).fire();
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
        correctieAdresAanvangdatum = Calendar.getInstance();
        correctieAdresAanvangdatum.setTime(new Date());
        Random random = new Random();
        correctieAdresAanvangdatum.add(Calendar.DAY_OF_YEAR, -(random.nextInt(Constanten.DUIZEND) + Constanten.ZESTIG));
        Date datumAanvangDate = correctieAdresAanvangdatum.getTime();
        String datumAanvang = DatumUtil.datumNaarXmlString(datumAanvangDate);

        // Einde geldigheid is 30 dagen later
        Calendar correctieEindeGeldigheid = Calendar.getInstance();
        correctieEindeGeldigheid.setTime(datumAanvangDate);
        correctieEindeGeldigheid.add(Calendar.DAY_OF_YEAR, Constanten.DERTIG);
        Date eindeGeldigheidDate = correctieEindeGeldigheid.getTime();
        String datumEinde = DatumUtil.datumNaarXmlString(eindeGeldigheidDate);

        // Random geldig adres
        Map<String, String> adres = TestClient.testdataService.getAdres();
        String huisnummer = adres.get(TestdataServiceImpl.HUISNUMMER);
        String huisletter = adres.get(TestdataServiceImpl.HUISLETTER);
        String huisnummertoevoeging = adres.get(TestdataServiceImpl.HUISNUMMERTOEVOEGING);
        String postcode = adres.get(TestdataServiceImpl.POSTCODE);
        String nor = adres.get(TestdataServiceImpl.NOR);
        String wpl = adres.get(TestdataServiceImpl.WOONPLAATSNAAM);
        String gemeente = adres.get(TestdataServiceImpl.GEMEENTECODE);

        int referentienummer = random.nextInt(Constanten.TIENDUIZEND);

        HashMap<String, String> parameterMap3 = new HashMap<>();
        if (eigenschappen.getFlowPrevalidatie()) {
            parameterMap3.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");
        }
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_REFERENTIENUMMER, "" + referentienummer);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_BSN, bsn);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_PERSID, "" + TestClient.testdataService.getPersIdByBsn(Long.valueOf(bsn)));
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_NAAM_OPENBARE_RUIMTE, nor);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_POSTCODE, postcode);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_WOONPLAATS, wpl);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_HUISNUMMER, huisnummer);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_HUISLETTER, huisletter);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_HUISNUMMERTOEVOEGING, huisnummertoevoeging);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_GEMEENTECODE, gemeente);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_DATUM_AANVANG_GELDIGHEID, datumAanvang);
        parameterMap3.put(CorrectieAdresAanroeper.PARAMETER_DATUM_EINDE_GELDIGHEID, datumEinde);

        new CorrectieAdresAanroeper(eigenschappen, getBhgVerblijfAdresPortType(eigenschappen), parameterMap3).fire();
    }

    /**
     * Run bijhouding inschrijving geboorte.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runBijhoudingGeboorte(final Eigenschappen eigenschappen) throws Exception {
        Map<String, String> huwelijk = TestClient.testdataService.getHuwelijk();
        String bsnVader = huwelijk.get(TestdataServiceImpl.HGP_MAN_BSN);
        String bsnMoeder = huwelijk.get(TestdataServiceImpl.HGP_VROUW_BSN);
        String naam = huwelijk.get(TestdataServiceImpl.HGP_MAN_NAAM);
        String voorvoegsel = huwelijk.get(TestdataServiceImpl.HGP_MAN_VOORVOEGSEL);
        String scheidingsteken = huwelijk.get(TestdataServiceImpl.HGP_MAN_SCHEIDINGSTEKEN);
        String metKinderenMetAndereNaam = huwelijk.get(TestdataServiceImpl.HGP_MET_KINDEREN_MET_ANDERE_NAAM);

        HashMap<String, String> parameterMap4 = new HashMap<>();
        if (eigenschappen.getFlowPrevalidatie()) {
            parameterMap4.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");
        }

        String bsn = TestClient.testdataService.getNieuweBsn();
        String anr = TestClient.testdataService.getNieuwANummer();
        String referentienummer = "" + new Random().nextInt(Constanten.DUIZEND);

        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_REFERENTIENUMMER, referentienummer);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN, bsn);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_ANUMMER, anr);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_NAAM, naam);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_VOORVOEGSEL, voorvoegsel);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_SCHEIDINGSTEKEN, scheidingsteken);
        
        
        //converteer BSN naar PERS_ID's
        
        Long ouder1_persId = TestClient.testdataService.getPersIdByBsn(Long.valueOf(bsnVader));
        String ouder1_persId_crypted = ObjectSleutelHandler.genereerObjectSleutelString(ouder1_persId, Integer.valueOf("101"), System.currentTimeMillis());
        
        Long ouder2_persId = TestClient.testdataService.getPersIdByBsn(Long.valueOf(bsnMoeder));
        String ouder2_persId_crypted = ObjectSleutelHandler.genereerObjectSleutelString(ouder2_persId, Integer.valueOf("101"), System.currentTimeMillis());

        
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN_OUDER1, ouder1_persId_crypted);
        parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN_OUDER2, ouder2_persId_crypted);
        if (metKinderenMetAndereNaam != null && Boolean.parseBoolean(metKinderenMetAndereNaam)) {
            parameterMap4.put(InschrijvingGeboorteAanroeper.PARAMETER_KINDEREN_MET_ANDERE_NAAM,
                              Boolean.TRUE.toString());
        }

        new InschrijvingGeboorteAanroeper(eigenschappen,
                                          getBhgAfstammingPortType(eigenschappen),
                                          parameterMap4).fire();
    }

    /**
     * Run bijhouding inschrijving geboorte met erkenning.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runBijhoudingGeboorteMetErkenning(final Eigenschappen eigenschappen) throws Exception {
        Map<String, String> geregistreerdpartnerschap = TestClient.testdataService.getGeregistreerdPartnerschap();
        String bsnVader = geregistreerdpartnerschap.get(TestdataServiceImpl.HGP_MAN_BSN);
        String bsnMoeder = geregistreerdpartnerschap.get(TestdataServiceImpl.HGP_VROUW_BSN);
        String naam = geregistreerdpartnerschap.get(TestdataServiceImpl.HGP_MAN_NAAM);
        String voorvoegsel = geregistreerdpartnerschap.get(TestdataServiceImpl.HGP_MAN_VOORVOEGSEL);
        String scheidingsteken = geregistreerdpartnerschap.get(TestdataServiceImpl.HGP_MAN_SCHEIDINGSTEKEN);
        String metKinderenMetAndereNaam =
                geregistreerdpartnerschap.get(TestdataServiceImpl.HGP_MET_KINDEREN_MET_ANDERE_NAAM);

        HashMap<String, String> parameters = new HashMap<>();
        if (eigenschappen.getFlowPrevalidatie()) {
            parameters.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");
        }

        String bsn = TestClient.testdataService.getNieuweBsn();
        String referentienummer = "" + new Random().nextInt(Constanten.DUIZEND);

        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_REFERENTIENUMMER, referentienummer);
        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN, bsn);
        parameters
                .put(InschrijvingGeboorteAanroeper.PARAMETER_ANUMMER, TestClient.testdataService.getNieuwANummer());
        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_NAAM, naam);
        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_VOORVOEGSEL, voorvoegsel);
        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_SCHEIDINGSTEKEN, scheidingsteken);
        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN_OUDER1, bsnVader);
        parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_BSN_OUDER2, bsnMoeder);
        if (metKinderenMetAndereNaam != null && Boolean.parseBoolean(metKinderenMetAndereNaam)) {
            parameters.put(InschrijvingGeboorteAanroeper.PARAMETER_KINDEREN_MET_ANDERE_NAAM,
                Boolean.TRUE.toString());
        }

        new InschrijvingGeboorteMetErkenningAanroeper(eigenschappen,
                                                      getBhgAfstammingPortType(eigenschappen),
                                                      parameters).fire();
    }

    /**
     * Run bijhouding registreer huwelijk.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runBijhoudingHuwelijk(final Eigenschappen eigenschappen) throws Exception {
        BsnPers bsn1 = TestClient.testdataService.getVrijgezelBsn();

        BsnPers bsn2 = TestClient.testdataService.getVrijgezelBsn();

        Random random = new Random();
        int referentienummer = random.nextInt(Constanten.TIENDUIZEND);

        HashMap<String, String> parameterMap3 = new HashMap<>();
        if (eigenschappen.getFlowPrevalidatie()) {
            parameterMap3.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");
        }
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_BSN1, bsn1.persId);
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_BSN2, bsn2.persId);
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_BSN_NAAMGEBRUIK, bsn1.persId);
        parameterMap3.put(RegistreerHuwelijkEnPartnerschapAanroeper.PARAMETER_REFERENTIENUMMER, "" + referentienummer);

        new RegistreerHuwelijkEnPartnerschapAanroeper(eigenschappen,
                                                      getBhgHuwelijkGeregisteerdPartnerschapPortType(eigenschappen),
                                                      parameterMap3).fire();
    }

    /**
     * Run bijhouding overlijden.
     *
     * @param eigenschappen eigenschappen
     * @throws Exception exception
     */
    private void runBijhoudingOverlijden(final Eigenschappen eigenschappen) throws Exception {
        HashMap<String, String> parameterMap4 = new HashMap<>();
        if (eigenschappen.getFlowPrevalidatie()) {
            parameterMap4.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");
        }

        String referentienummer = "" + new Random().nextInt(Constanten.DUIZEND);

        // was een willekeurig test persoon, maar geeft probleme, omdat bij overlijden ook het huwelijk werd
        // ontbonden en de server kan niet tegen bepaalde situaties.
        // Voor zover ik zag was deze persoon gescheiden (en officieel is huwelijk beeindigt), maar de server probeert
        // nog steeds het huwelijk te ontbinden.
        // Dus totdat die bug is opgelost, gebruiken we de vrijgezellen lijst.
        BsnPers bsn = TestClient.testdataService.getVrijgezelBsn();

        String datumAanvang = DatumUtil.vandaagXmlString();

        // Random geldig adres
        Map<String, String> adres = TestClient.testdataService.getAdres();
        String wplnaam = adres.get(TestdataServiceImpl.WOONPLAATSNAAM);
        String gemeentecode = adres.get(TestdataServiceImpl.GEMEENTECODE);
        String landcode = adres.get(TestdataServiceImpl.LANDCODE);

        parameterMap4.put(OverlijdenAanroeper.PARAMETER_REFERENTIENUMMER, referentienummer);
        
        //String crypted = ObjectSleutelHandler.genereerObjectSleutelString(Long.valueOf(bsn), Integer.valueOf("101"), System.currentTimeMillis());
        
        parameterMap4.put(OverlijdenAanroeper.PARAMETER_BSN, bsn.persId);
        
        parameterMap4.put(OverlijdenAanroeper.PARAMETER_DATUM_AANVANG_GELDIGHEID, datumAanvang);
        parameterMap4.put(OverlijdenAanroeper.PARAMETER_DATUM_OVERLIJDEN, datumAanvang);
        parameterMap4.put(OverlijdenAanroeper.PARAMETER_GEMEENTECODE, gemeentecode);
        parameterMap4.put(OverlijdenAanroeper.PARAMETER_LANDCODE, landcode);
        parameterMap4.put(OverlijdenAanroeper.PARAMETER_WOONPLAATSNAAM, wplnaam);

        new OverlijdenAanroeper(eigenschappen, getBhgOverlijdenPortType(eigenschappen), parameterMap4).fire();
    }

    /**
     * Run bijhouding verhuizing.
     *
     * @param eigenschappen de eigenschappen
     * @throws Exception de exception
     */
    private void runBijhoudingVerhuizing(final Eigenschappen eigenschappen) throws Exception {
        String bsn = TestClient.testdataService.getBsn();

        // Veilig, omdat we ander ook in de problemen komen met niet geldige woonplaatsen, gemeentes, landen.
        verhuizingAanvangdatum.setTime(new Date());
        verhuizingAanvangdatum.add(Calendar.DATE, -Constanten.TWEE);
        Date datumAanvangDate = verhuizingAanvangdatum.getTime();
        String datumAanvang = DatumUtil.datumNaarXmlString(datumAanvangDate);

        Map<String, String> adresNieuw = TestClient.testdataService.getAdres();
        String huisnummerNieuw = adresNieuw.get(TestdataServiceImpl.HUISNUMMER);
        String huisletterNieuw = adresNieuw.get(TestdataServiceImpl.HUISLETTER);
        String huisnummerToevoegingNieuw = adresNieuw.get(TestdataServiceImpl.HUISNUMMERTOEVOEGING);
        String postcodeNieuw = adresNieuw.get(TestdataServiceImpl.POSTCODE);
        String norNieuw = adresNieuw.get(TestdataServiceImpl.NOR);
        String wplNieuw = adresNieuw.get(TestdataServiceImpl.WOONPLAATSNAAM);
        String gemeenteNieuw = adresNieuw.get(TestdataServiceImpl.GEMEENTECODE);

        // 4: Prevalidatie verhuizing
        HashMap<String, String> parameterMap4 = new HashMap<>();
        if (eigenschappen.getFlowPrevalidatie()) {
            parameterMap4.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");
        }
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_DATUM_AANVANG_GELDIGHEID, datumAanvang);
        
        Long persId = TestClient.testdataService.getPersIdByBsn(Long.valueOf(bsn));
        String crypted = ObjectSleutelHandler.genereerObjectSleutelString(persId, Integer.valueOf("101"), System.currentTimeMillis());
        
        //parameterMap4.put(VerhuizingAanroeper.PARAMETER_BSN, bsn);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_BSN, crypted);
        
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_GEMEENTECODE, gemeenteNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_NAAM_OPENBARE_RUIMTE, norNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_WOONPLAATSNAAM, wplNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_HUISNUMMER, huisnummerNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_HUISLETTER, huisletterNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_HUISNUMMERTOEVOEGING, huisnummerToevoegingNieuw);
        parameterMap4.put(VerhuizingAanroeper.PARAMETER_POSTCODE, postcodeNieuw);

        new VerhuizingAanroeper(eigenschappen, getBhgVerblijfAdresPortType(eigenschappen), parameterMap4).fire();
    }

    /**
     * Run bijhouding adoptie.
     *
     * @param eigenschappen eigenschappen
     * @throws Exception exception
     */
    private void runBijhoudingAdoptie(final Eigenschappen eigenschappen) throws Exception {
        HashMap<String, String> parameterMap = new HashMap<>();
        if (eigenschappen.getFlowPrevalidatie()) {
            parameterMap.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");
        }

        final Map<String, String> adoptieKind = TestClient.testdataService.getAdoptieKind();

        final Map<String, String> huwelijk = TestClient.testdataService.getHuwelijk();
        String bsnVader = huwelijk.get(TestdataServiceImpl.HGP_MAN_BSN);
        String bsnMoeder = huwelijk.get(TestdataServiceImpl.HGP_VROUW_BSN);

        final String relatieId = adoptieKind.get(TestdataServiceImpl.ADOPTIE_RELATIE_ID);
        final String geadopteerdeBsn = adoptieKind.get(TestdataServiceImpl.ADOPTIE_KIND_BSN);
        final String kindBetrId = adoptieKind.get(TestdataServiceImpl.ADOPTIE_KIND_BETR_ID);

        parameterMap.put(AdoptieAanroeper.PARAMETER_BSN_OUDER1, bsnVader);
        parameterMap.put(AdoptieAanroeper.PARAMETER_BSN_OUDER2, bsnMoeder);
        parameterMap.put(AdoptieAanroeper.PARAMETER_BSN, geadopteerdeBsn);
        parameterMap.put(AdoptieAanroeper.PARAMETER_ID_RELATIE, relatieId);
        parameterMap.put(AdoptieAanroeper.PARAMETER_ID_BETR, kindBetrId);

//        LOG.debug("bsn kind: " + geadopteerdeBsn + ", bsn ouder1: " + bsnVader + ", bsn ouder2: " + bsnMoeder
//                  + ", bsn echteOuder1: " + adoptieKind.get("ECHTE_OUDER_1") + ", bsn echteOuder2: " +
//                          adoptieKind.get("ECHTE_OUDER_2") + ", relatie id: " + relatieId);

        new AdoptieAanroeper(eigenschappen, getBhgAfstammingPortType(eigenschappen), parameterMap).fire();
    }
}
