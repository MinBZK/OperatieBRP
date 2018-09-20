/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bijhouding;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.JAXBElement;

import nl.bprbzk.brp._0001.Applicatienaam;
import nl.bprbzk.brp._0001.Burgerservicenummer;
import nl.bprbzk.brp._0001.ContainerActieBronnen;
import nl.bprbzk.brp._0001.ContainerBerichtActies;
import nl.bprbzk.brp._0001.ContainerBerichtOverruleMeldingen;
import nl.bprbzk.brp._0001.ContainerPersoonAdressen;
import nl.bprbzk.brp._0001.Datum;
import nl.bprbzk.brp._0001.DatumTijd;
import nl.bprbzk.brp._0001.DocumentIdentificatie;
import nl.bprbzk.brp._0001.DocumentOmschrijving;
import nl.bprbzk.brp._0001.Gemeentecode;
import nl.bprbzk.brp._0001.GroepBerichtStuurgegevens;
import nl.bprbzk.brp._0001.GroepPersoonAfgeleidAdministratief;
import nl.bprbzk.brp._0001.GroepPersoonIdentificatienummers;
import nl.bprbzk.brp._0001.Huisletter;
import nl.bprbzk.brp._0001.Huisnummer;
import nl.bprbzk.brp._0001.Huisnummertoevoeging;
import nl.bprbzk.brp._0001.Ja;
import nl.bprbzk.brp._0001.JaS;
import nl.bprbzk.brp._0001.MigratieCorrectieAdresBinnenNLBijhouding;
import nl.bprbzk.brp._0001.NaamEnumeratiewaarde;
import nl.bprbzk.brp._0001.NaamOpenbareRuimte;
import nl.bprbzk.brp._0001.ObjectFactory;
import nl.bprbzk.brp._0001.ObjecttypeBron;
import nl.bprbzk.brp._0001.ObjecttypeDocument;
import nl.bprbzk.brp._0001.ObjecttypeOverrule;
import nl.bprbzk.brp._0001.ObjecttypePersoon;
import nl.bprbzk.brp._0001.ObjecttypePersoonAdres;
import nl.bprbzk.brp._0001.Organisatienaam;
import nl.bprbzk.brp._0001.Postcode;
import nl.bprbzk.brp._0001.RedenWijzigingAdresCode;
import nl.bprbzk.brp._0001.RedenWijzigingAdresCodeS;
import nl.bprbzk.brp._0001.RegelCode;
import nl.bprbzk.brp._0001.Sleutelwaardetekst;
import nl.bprbzk.brp._0001.SoortAdresCode;
import nl.bprbzk.brp._0001.SoortAdresCodeS;
import nl.bprbzk.brp._0001.ViewCorrectieAdresBinnenNL;
import nl.bprbzk.brp._0001.Woonplaatscode;
import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Bericht;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bijhouding.CorrectieAdresCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class CorrectieAdresAanroeper.
 */
public class CorrectieAdresAanroeper extends BijhoudingAanroeper {

    // NB: Let op dat je bij het toeveogen van parameters, deze ook toevoegt in getParameterList()

    /** De Constante LOG. */
    private static final Logger     LOG                                =
                                                                           LoggerFactory
                                                                                   .getLogger(CorrectieAdresAanroeper.class);

    /** De Constante PARAMETER_BSN. */
    public static final String      PARAMETER_BSN                      = "BSN";

    /** De Constante PARAMETER_NAAM_OPENBARE_RUIMTE. */
    public static final String      PARAMETER_NAAM_OPENBARE_RUIMTE     = "NAAM_OPENBARE_RUIMTE";

    /** De Constante PARAMETER_DATUM_AANVANG_GELDIGHEID. */
    public static final String      PARAMETER_DATUM_AANVANG_GELDIGHEID = "DATUM_AANVANG_GELDIGHEID";

    /** De Constante PARAMETER_DATUM_EINDE_GELDIGHEID. */
    public static final String      PARAMETER_DATUM_EINDE_GELDIGHEID   = "DATUM_EINDE_GELDIGHEID";

    /** De Constante PARAMETER_WOONPLAATS. */
    public static final String      PARAMETER_WOONPLAATS               = "WOONPLAATS_CODE";

    /** De Constante PARAMETER_POSTCODE. */
    public static final String      PARAMETER_POSTCODE                 = "POSTCODE";

    /** De Constante PARAMETER_HUISNUMMER. */
    public static final String      PARAMETER_HUISNUMMER               = "HUISNUMMER";

    /** De Constante PARAMETER_HUISLETTER. */
    public static final String      PARAMETER_HUISLETTER               = "HUISLETTER";

    /** De Constante PARAMETER_HUISNUMMERTOEVOEGING. */
    public static final String      PARAMETER_HUISNUMMERTOEVOEGING     = "HUISNUMMERTOEVOEGING";

    /** De Constante PARAMETER_GEMEENTECODE. */
    public static final String      PARAMETER_GEMEENTECODE             = "GEMEENTE";

    public static final String  PARAMETER_REFERENTIENUMMER = "REFERENTIENUMMER";

    /** De Constante BSN. */
    private static final String     BSN                                = "100000009";

    /** De Constante ORGANISATIE. */
    private static final String     ORGANISATIE                        = "364";

    /** De Constante APPLICATIE. */
    private static final String     APPLICATIE                         = "Testclient";

    /** De Constante REFERENTIENUMMER. */
    private static final String     REFERENTIENUMMER                   = "12345";

    /** De Constante AANVANG_GELDIGHEID. */
    private static final BigInteger AANVANG_GELDIGHEID                 = BigInteger.valueOf(20100719);

    /** De Constante EINDE_GELDIGHEID. */
    private static final BigInteger EINDE_GELDIGHEID                   = BigInteger.valueOf(20110819);

    /** De Constante TIJDSTIP_ONTLENING. */
    private static final BigInteger TIJDSTIP_ONTLENING                 = BigInteger.valueOf(20120820);

    /** De Constante TIJDSTIP_LAATSTE_WIJZIGING. */
    private static final BigInteger TIJDSTIP_LAATSTE_WIJZIGING         = BigInteger.valueOf(20120812);

    /** De Constante GEMEENTECODE. */
    private static final String     GEMEENTECODE                       = "0599";

    /** De Constante NAAM_OPENBARE_RUIMTE. */
    private static final String     NAAM_OPENBARE_RUIMTE               = "Blaak";

    /** De Constante SOORT_NAAM. */
    private static final String     SOORT_NAAM                         = "Onderzoeksrapport";

    /** De Constante IDENTIFICATIE. */
    private static final String     IDENTIFICATIE                      = "OND201206-0042";

    /** De Constante OMSCHRIJVING. */
    private static final String     OMSCHRIJVING                       = "Onderzoeksrapport adreshouding";

    /** De Constante PARTIJCODE. */
    private static final String     PARTIJCODE                         = "0518";

    /** De Constante POSTCODE. */
    private static final String     POSTCODE                           = "3011GB";

    /** De Constante WOONPLAATS_CODE. */
    private static final String     WOONPLAATS_CODE                    = "3086";

    /** De Constante OVERRULE_REGELCODE. */
    private static final String     OVERRULE_REGELCODE                 = "BRBY0525";

    /**
     * Instantieert een nieuwe correctie adres aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    public CorrectieAdresAanroeper(final Eigenschappen eigenschappen, final Map<String, String> parameterMap)
            throws Exception
    {
        super(eigenschappen, parameterMap);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#fire()
     */
    @Override
    public void fire() {
        MigratieCorrectieAdresBinnenNLBijhouding bijhouding = creeerMigratieCorrectieAdresBinnenNLBijhouding();
        CorrectieAdresCommand command = new CorrectieAdresCommand(bijhouding);
        Long duur = roepAan(command);
        TestClient.statistieken.bijwerkenNaBericht(Bericht.BERICHT.BIJHOUDING_CORRECTIE_ADRES, getStatus(), duur);
    }

    /**
     * Creeer migratie correctie adres binnen nl bijhouding.
     *
     * @return de migratie correctie adres binnen nl bijhouding
     */
    private MigratieCorrectieAdresBinnenNLBijhouding creeerMigratieCorrectieAdresBinnenNLBijhouding() {
        ObjectFactory objectFactory = new ObjectFactory();
        MigratieCorrectieAdresBinnenNLBijhouding migratieCorrectieAdresBinnenNLBijhouding =
            objectFactory.createMigratieCorrectieAdresBinnenNLBijhouding();

        // Stuurgegevens-element
        List<GroepBerichtStuurgegevens> groepBerichtStuurgegevensList =
            migratieCorrectieAdresBinnenNLBijhouding.getStuurgegevens();
        GroepBerichtStuurgegevens groepBerichtStuurgegevens = creeerGroepBerichtStuurgegevens();
        groepBerichtStuurgegevensList.add(groepBerichtStuurgegevens);

        // Overrulemeldingen
        ContainerBerichtOverruleMeldingen overruleBerichten = new ContainerBerichtOverruleMeldingen();
        JAXBElement<ContainerBerichtOverruleMeldingen> jaxbOverruleMeldingen =
            objectFactory.createObjecttypeBerichtOverruleMeldingen(overruleBerichten);
        migratieCorrectieAdresBinnenNLBijhouding.setOverruleMeldingen(jaxbOverruleMeldingen);
        List<ObjecttypeOverrule> overruleList = overruleBerichten.getOverrule();

        // Overrule
        ObjecttypeOverrule overrule = new ObjecttypeOverrule();
        overruleList.add(overrule);

        // Regelcode
        RegelCode regelcode = new RegelCode();
        regelcode.setValue(OVERRULE_REGELCODE);
        overrule.setRegelCode(regelcode);

        // Acties-element
        ContainerBerichtActies containerBerichtActies = objectFactory.createContainerBerichtActies();
        JAXBElement<ContainerBerichtActies> jaxbContainerBerichtActies =
            objectFactory.createObjecttypeBerichtActies(containerBerichtActies);
        migratieCorrectieAdresBinnenNLBijhouding.setActies(jaxbContainerBerichtActies);

        List<ViewCorrectieAdresBinnenNL> correctieAdresList = containerBerichtActies.getCorrectieAdresBinnenNL();
        ViewCorrectieAdresBinnenNL viewCorrectieAdresBinnenNL = objectFactory.createViewCorrectieAdresBinnenNL();
        correctieAdresList.add(viewCorrectieAdresBinnenNL);

        // Aanvang geldigheid
        JAXBElement<Datum> jaxbAanvangGeldigheid = creeerJaxbAanvangGeldigheid();
        viewCorrectieAdresBinnenNL.setDatumAanvangGeldigheid(jaxbAanvangGeldigheid);

        // Einde geldigheid
        JAXBElement<Datum> jaxbEindeGeldigheid = creeerJaxbEindeGeldigheid();
        viewCorrectieAdresBinnenNL.setDatumEindeGeldigheid(jaxbEindeGeldigheid);

        // Tijdstip ontlening
        JAXBElement<DatumTijd> jaxbTijdstipOntlening = creeerJaxbTijdstipOntlening();
        viewCorrectieAdresBinnenNL.setTijdstipOntlening(jaxbTijdstipOntlening);

        // Bronnen
        ContainerActieBronnen bronnen = new ContainerActieBronnen();
        JAXBElement<ContainerActieBronnen> jaxbBronnen = objectFactory.createObjecttypeActieBronnen(bronnen);
        viewCorrectieAdresBinnenNL.setBronnen(jaxbBronnen);
        List<ObjecttypeBron> bronList = bronnen.getBron();
        ObjecttypeBron bron = new ObjecttypeBron();
        bronList.add(bron);

        // Document
        ObjecttypeDocument document = new ObjecttypeDocument();
        bron.setDocument(document);

        // Soortnaam
        NaamEnumeratiewaarde soortNaam = new NaamEnumeratiewaarde();
        soortNaam.setValue(SOORT_NAAM);
        document.setSoortNaam(soortNaam);

        // Identificatie
        DocumentIdentificatie identificatie = new DocumentIdentificatie();
        identificatie.setValue(IDENTIFICATIE);
        JAXBElement<DocumentIdentificatie> jaxbIdentificatie =
            objectFactory.createObjecttypeDocumentIdentificatie(identificatie);
        document.setIdentificatie(jaxbIdentificatie);

        // Omschrijving
        DocumentOmschrijving omschrijving = new DocumentOmschrijving();
        omschrijving.setValue(OMSCHRIJVING);
        JAXBElement<DocumentOmschrijving> jaxbOmschrijving =
            objectFactory.createObjecttypeDocumentOmschrijving(omschrijving);
        document.setOmschrijving(jaxbOmschrijving);

        // Partijcode
        Gemeentecode partijcode = new Gemeentecode();
        partijcode.setValue(PARTIJCODE);
        document.setPartijCode(partijcode);

        // Persoon
        List<ObjecttypePersoon> objecttypePersoonList = viewCorrectieAdresBinnenNL.getPersoon();
        ObjecttypePersoon objecttypePersoon = objectFactory.createObjecttypePersoon();
        objecttypePersoonList.add(objecttypePersoon);

        // Identificatienummers
        List<GroepPersoonIdentificatienummers> groepPersoonIdentificatienummersList =
            objecttypePersoon.getIdentificatienummers();
        GroepPersoonIdentificatienummers groepPersoonIdentificatienummers =
            objectFactory.createGroepPersoonIdentificatienummers();
        groepPersoonIdentificatienummersList.add(groepPersoonIdentificatienummers);

        // BSN
        Burgerservicenummer burgerservicenummer = new Burgerservicenummer();
        burgerservicenummer.setValue(getValueFromValueMap(PARAMETER_BSN, BSN));
        JAXBElement<Burgerservicenummer> jaxbBurgerservicenummer =
            objectFactory.createGroepPersoonIdentificatienummersBurgerservicenummer(burgerservicenummer);
        groepPersoonIdentificatienummers.setBurgerservicenummer(jaxbBurgerservicenummer);

        // Afgeleid administratief
        List<GroepPersoonAfgeleidAdministratief> groepPersoonAfgeleidAdministratiefList =
            objecttypePersoon.getAfgeleidAdministratief();
        GroepPersoonAfgeleidAdministratief groepPersoonAfgeleidAdministratief =
            objectFactory.createGroepPersoonAfgeleidAdministratief();

        // Tijdstip laatste wijziging
        JAXBElement<DatumTijd> jaxbDatumTijd = creeerJaxbDatumTijd();
        groepPersoonAfgeleidAdministratief.setTijdstipLaatsteWijziging(jaxbDatumTijd);
        groepPersoonAfgeleidAdministratiefList.add(groepPersoonAfgeleidAdministratief);

        // Adressen
        ContainerPersoonAdressen containerPersoonAdressen = objectFactory.createContainerPersoonAdressen();
        List<ObjecttypePersoonAdres> objecttypePersoonAdresList = containerPersoonAdressen.getAdres();
        ObjecttypePersoonAdres objecttypePersoonAdres = objectFactory.createObjecttypePersoonAdres();
        objecttypePersoonAdresList.add(objecttypePersoonAdres);
        JAXBElement<ContainerPersoonAdressen> jaxbContainerPersoonAdressen =
            objectFactory.createObjecttypePersoonAdressen(containerPersoonAdressen);
        objecttypePersoon.setAdressen(jaxbContainerPersoonAdressen);

        // Soort code
        JAXBElement<SoortAdresCode> jaxbSoortAdresCode = creeerJaxbSoortAdresCode();
        objecttypePersoonAdres.setSoortCode(jaxbSoortAdresCode);

        // Reden wijziging adrescode
        JAXBElement<RedenWijzigingAdresCode> jaxbRedenWijzigingAdresCode = creeerJaxbRedenWijzigingAdresCode();
        objecttypePersoonAdres.setRedenWijzigingCode(jaxbRedenWijzigingAdresCode);

        // Datum aanvang adreshouding
        JAXBElement<Datum> jaxbDatumAanvangAdreshouding = creeerJaxbDatumAanvangAdreshouding();
        objecttypePersoonAdres.setDatumAanvangAdreshouding(jaxbDatumAanvangAdreshouding);

        // Gemeentecode
        JAXBElement<Gemeentecode> jaxbGemeentecode = creeerJaxbGemeentecode();
        objecttypePersoonAdres.setGemeenteCode(jaxbGemeentecode);

        // Naam Openbare Ruimte
        JAXBElement<NaamOpenbareRuimte> jaxbNaamOpenbareRuimte = creeerJaxbNaamOpenbareRuimte();
        objecttypePersoonAdres.setNaamOpenbareRuimte(jaxbNaamOpenbareRuimte);

        // Huisnummer
        JAXBElement<Huisnummer> jaxbHuisnummer = creeerJaxbHuisnummer();
        objecttypePersoonAdres.setHuisnummer(jaxbHuisnummer);

        // Huisletter
        JAXBElement<Huisletter> jaxbHuisletter = creeerJaxbHuisletter();
        objecttypePersoonAdres.setHuisletter(jaxbHuisletter);

        // Huisnummertoevoeging
        JAXBElement<Huisnummertoevoeging> jaxbHuisnummertoevoeging = creeerJaxbHuisnummertoevoeging();
        objecttypePersoonAdres.setHuisnummertoevoeging(jaxbHuisnummertoevoeging);

        // Postcode
        Postcode postcode = new Postcode();
        postcode.setValue(getValueFromValueMap(PARAMETER_POSTCODE, POSTCODE));
        JAXBElement<Postcode> jaxbPostcode = objectFactory.createObjecttypePersoonAdresPostcode(postcode);
        objecttypePersoonAdres.setPostcode(jaxbPostcode);

        // Woonplaats code
        Woonplaatscode woonplaatsCode = new Woonplaatscode();
        woonplaatsCode.setValue(getValueFromValueMap(PARAMETER_WOONPLAATS, WOONPLAATS_CODE));
        JAXBElement<Woonplaatscode> jaxbWoonplaatsCode =
            objectFactory.createObjecttypePersoonAdresWoonplaatsCode(woonplaatsCode);
        objecttypePersoonAdres.setWoonplaatsCode(jaxbWoonplaatsCode);

        return migratieCorrectieAdresBinnenNLBijhouding;
    }

    /**
     * Creeer jaxb huisnummer.
     *
     * @return de jAXB element
     */
    private JAXBElement<Huisnummer> creeerJaxbHuisnummer() {
        ObjectFactory objectFactory = new ObjectFactory();
        Huisnummer huisnummer = objectFactory.createHuisnummer();
        huisnummer.setValue(getValueFromValueMap(PARAMETER_HUISNUMMER, creeerRandomHuisnummer()));
        JAXBElement<Huisnummer> jaxbHuisnummer = objectFactory.createObjecttypePersoonAdresHuisnummer(huisnummer);
        return jaxbHuisnummer;
    }

    /**
     * Creeer jaxb huisletter.
     *
     * @return de jAXB element
     */
    private JAXBElement<Huisletter> creeerJaxbHuisletter() {
        ObjectFactory objectFactory = new ObjectFactory();
        Huisletter huisletter = objectFactory.createHuisletter();
        huisletter.setValue(getValueFromValueMap(PARAMETER_HUISLETTER, null));
        JAXBElement<Huisletter> jaxbHuisletter = objectFactory.createObjecttypePersoonAdresHuisletter(huisletter);
        return jaxbHuisletter;
    }

    private JAXBElement<Huisnummertoevoeging> creeerJaxbHuisnummertoevoeging() {
        ObjectFactory objectFactory = new ObjectFactory();
        Huisnummertoevoeging huisnummertoevoeging = objectFactory.createHuisnummertoevoeging();
        huisnummertoevoeging.setValue(getValueFromValueMap(PARAMETER_HUISNUMMERTOEVOEGING, null));
        JAXBElement<Huisnummertoevoeging> jaxbHuisnummertoevoeging = objectFactory.createObjecttypePersoonAdresHuisnummertoevoeging(huisnummertoevoeging);
        return jaxbHuisnummertoevoeging;
    }

    /**
     * Creeer random huisnummer.
     *
     * @return de string
     */
    private String creeerRandomHuisnummer() {
        return "" + new Random().nextInt(1000);
    }

    /**
     * Creeer jaxb naam openbare ruimte.
     *
     * @return de jAXB element
     */
    private JAXBElement<NaamOpenbareRuimte> creeerJaxbNaamOpenbareRuimte() {
        ObjectFactory objectFactory = new ObjectFactory();
        NaamOpenbareRuimte naamOpenbareRuimte = objectFactory.createNaamOpenbareRuimte();
        naamOpenbareRuimte.setValue(getValueFromValueMap(PARAMETER_NAAM_OPENBARE_RUIMTE, NAAM_OPENBARE_RUIMTE));
        JAXBElement<NaamOpenbareRuimte> jaxbNaamOpenbareRuimte =
            objectFactory.createObjecttypePersoonAdresNaamOpenbareRuimte(naamOpenbareRuimte);
        return jaxbNaamOpenbareRuimte;
    }

    /**
     * Creeer jaxb tijdstip ontlening.
     *
     * @return de jAXB element
     */
    private JAXBElement<DatumTijd> creeerJaxbTijdstipOntlening() {
        ObjectFactory objectFactory = new ObjectFactory();
        DatumTijd tijdstipOntlening = objectFactory.createDatumTijd();
        tijdstipOntlening.setValue(TIJDSTIP_ONTLENING);
        JAXBElement<DatumTijd> jaxbTijdstipOntlening =
            objectFactory.createObjecttypeActieTijdstipOntlening(tijdstipOntlening);
        return jaxbTijdstipOntlening;
    }

    /**
     * Creeer jaxb datum tijd.
     *
     * @return de jAXB element
     */
    private JAXBElement<DatumTijd> creeerJaxbDatumTijd() {
        ObjectFactory objectFactory = new ObjectFactory();
        DatumTijd datumTijd = objectFactory.createDatumTijd();
        datumTijd.setValue(TIJDSTIP_LAATSTE_WIJZIGING);
        JAXBElement<DatumTijd> jaxbDatumTijd =
            objectFactory.createGroepPersoonAfgeleidAdministratiefTijdstipLaatsteWijziging(datumTijd);
        return jaxbDatumTijd;
    }

    /**
     * Creeer jaxb gemeentecode.
     *
     * @return de jAXB element
     */
    private JAXBElement<Gemeentecode> creeerJaxbGemeentecode() {
        ObjectFactory objectFactory = new ObjectFactory();
        Gemeentecode gemeentecode = objectFactory.createGemeentecode();
        gemeentecode.setValue(getValueFromValueMap(PARAMETER_GEMEENTECODE, GEMEENTECODE));

        JAXBElement<Gemeentecode> jaxbGemeentecode =
            objectFactory.createObjecttypePersoonAdresGemeenteCode(gemeentecode);
        return jaxbGemeentecode;
    }

    /**
     * Creeer jaxb datum aanvang adreshouding.
     *
     * @return de jAXB element
     */
    private JAXBElement<Datum> creeerJaxbDatumAanvangAdreshouding() {
        ObjectFactory objectFactory = new ObjectFactory();
        Datum datumAanvangAdreshouding = objectFactory.createDatum();
        datumAanvangAdreshouding.setValue(BigInteger.valueOf(Long.parseLong(getValueFromValueMap(
                PARAMETER_DATUM_AANVANG_GELDIGHEID, "" + AANVANG_GELDIGHEID))));
        JAXBElement<Datum> jaxbDatumAanvangAdreshouding =
            objectFactory.createObjecttypePersoonAdresDatumAanvangAdreshouding(datumAanvangAdreshouding);
        return jaxbDatumAanvangAdreshouding;
    }

    /**
     * Creeer jaxb reden wijziging adres code.
     *
     * @return de jAXB element
     */
    private JAXBElement<RedenWijzigingAdresCode> creeerJaxbRedenWijzigingAdresCode() {
        ObjectFactory objectFactory = new ObjectFactory();
        RedenWijzigingAdresCode redenWijzigingAdresCode = objectFactory.createRedenWijzigingAdresCode();
        redenWijzigingAdresCode.setValue(RedenWijzigingAdresCodeS.A);
        JAXBElement<RedenWijzigingAdresCode> jaxbRedenWijzigingAdresCode =
            objectFactory.createObjecttypePersoonAdresRedenWijzigingCode(redenWijzigingAdresCode);
        return jaxbRedenWijzigingAdresCode;
    }

    /**
     * Creeer jaxb soort adres code.
     *
     * @return de jAXB element
     */
    private JAXBElement<SoortAdresCode> creeerJaxbSoortAdresCode() {
        ObjectFactory objectFactory = new ObjectFactory();
        SoortAdresCode soortAdresCode = objectFactory.createSoortAdresCode();
        soortAdresCode.setValue(SoortAdresCodeS.W);
        JAXBElement<SoortAdresCode> jaxbSoortAdresCode =
            objectFactory.createObjecttypePersoonAdresSoortCode(soortAdresCode);
        return jaxbSoortAdresCode;
    }

    /**
     * Creeer jaxb aanvang geldigheid.
     *
     * @return de jAXB element
     */
    private JAXBElement<Datum> creeerJaxbAanvangGeldigheid() {
        ObjectFactory objectFactory = new ObjectFactory();
        Datum aanvangGeldigheid = objectFactory.createDatum();
        aanvangGeldigheid.setValue(BigInteger.valueOf(Long.parseLong(getValueFromValueMap(
                PARAMETER_DATUM_AANVANG_GELDIGHEID, "" + AANVANG_GELDIGHEID))));
        JAXBElement<Datum> jaxbAanvangGeldigheid =
            objectFactory.createObjecttypeActieDatumAanvangGeldigheid(aanvangGeldigheid);
        return jaxbAanvangGeldigheid;
    }

    /**
     * Creeer jaxb einde geldigheid.
     *
     * @return de jAXB element
     */
    private JAXBElement<Datum> creeerJaxbEindeGeldigheid() {
        ObjectFactory objectFactory = new ObjectFactory();
        Datum eindeGeldigheid = objectFactory.createDatum();
        eindeGeldigheid.setValue(BigInteger.valueOf(Long.parseLong(getValueFromValueMap(
                PARAMETER_DATUM_EINDE_GELDIGHEID, "" + EINDE_GELDIGHEID))));
        JAXBElement<Datum> jaxbEindeGeldigheid =
            objectFactory.createObjecttypeActieDatumEindeGeldigheid(eindeGeldigheid);
        return jaxbEindeGeldigheid;
    }

    /**
     * Creeer groep bericht stuurgegevens.
     *
     * @return de groep bericht stuurgegevens
     */
    private GroepBerichtStuurgegevens creeerGroepBerichtStuurgegevens() {
        ObjectFactory objectFactory = new ObjectFactory();

        GroepBerichtStuurgegevens groepBerichtStuurgegevens = new GroepBerichtStuurgegevens();

        Organisatienaam organisatienaam = new Organisatienaam();
        organisatienaam.setValue(ORGANISATIE);
        groepBerichtStuurgegevens.setOrganisatie(organisatienaam);

        Applicatienaam applicatienaam = new Applicatienaam();
        applicatienaam.setValue(APPLICATIE);
        groepBerichtStuurgegevens.setApplicatie(applicatienaam);

        Sleutelwaardetekst sleutelwaardetekst = new Sleutelwaardetekst();
        sleutelwaardetekst.setValue(getValueFromValueMap(PARAMETER_REFERENTIENUMMER, REFERENTIENUMMER));
        groepBerichtStuurgegevens.setReferentienummer(sleutelwaardetekst);

        if (valueMap.containsKey(PARAMETER_PREVALIDATIE)) {
            Ja ja = new Ja();
            ja.setValue(JaS.J);
            JAXBElement<Ja> jaxbJa = objectFactory.createGroepBerichtStuurgegevensIndicatiePrevalidatie(ja);
            groepBerichtStuurgegevens.setIndicatiePrevalidatie(jaxbJa);
        }

        return groepBerichtStuurgegevens;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#getParameterList()
     */
    @Override
    protected List<String> getParameterList() {
        return Arrays.asList(new String[] { BijhoudingAanroeper.PARAMETER_PREVALIDATIE, PARAMETER_REFERENTIENUMMER, PARAMETER_BSN,
            PARAMETER_NAAM_OPENBARE_RUIMTE, PARAMETER_DATUM_AANVANG_GELDIGHEID, PARAMETER_DATUM_EINDE_GELDIGHEID,
            PARAMETER_HUISNUMMER, PARAMETER_HUISLETTER, PARAMETER_HUISNUMMERTOEVOEGING, PARAMETER_POSTCODE,
            PARAMETER_WOONPLAATS, PARAMETER_GEMEENTECODE });
    }

}
