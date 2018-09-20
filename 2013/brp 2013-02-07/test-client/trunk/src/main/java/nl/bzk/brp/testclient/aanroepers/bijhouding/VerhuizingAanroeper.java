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
import nl.bprbzk.brp._0001.ContainerBerichtActies;
import nl.bprbzk.brp._0001.ContainerPersoonAdressen;
import nl.bprbzk.brp._0001.Datum;
import nl.bprbzk.brp._0001.DatumTijd;
import nl.bprbzk.brp._0001.Gemeentecode;
import nl.bprbzk.brp._0001.GroepBerichtStuurgegevens;
import nl.bprbzk.brp._0001.GroepPersoonAfgeleidAdministratief;
import nl.bprbzk.brp._0001.GroepPersoonIdentificatienummers;
import nl.bprbzk.brp._0001.Huisletter;
import nl.bprbzk.brp._0001.Huisnummer;
import nl.bprbzk.brp._0001.Huisnummertoevoeging;
import nl.bprbzk.brp._0001.Ja;
import nl.bprbzk.brp._0001.JaS;
import nl.bprbzk.brp._0001.MigratieVerhuizingBijhouding;
import nl.bprbzk.brp._0001.NaamOpenbareRuimte;
import nl.bprbzk.brp._0001.ObjectFactory;
import nl.bprbzk.brp._0001.ObjecttypePersoon;
import nl.bprbzk.brp._0001.ObjecttypePersoonAdres;
import nl.bprbzk.brp._0001.Organisatienaam;
import nl.bprbzk.brp._0001.Postcode;
import nl.bprbzk.brp._0001.RedenWijzigingAdresCode;
import nl.bprbzk.brp._0001.RedenWijzigingAdresCodeS;
import nl.bprbzk.brp._0001.Sleutelwaardetekst;
import nl.bprbzk.brp._0001.SoortAdresCode;
import nl.bprbzk.brp._0001.SoortAdresCodeS;
import nl.bprbzk.brp._0001.ViewVerhuizing;
import nl.bprbzk.brp._0001.Woonplaatscode;
import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Bericht;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bijhouding.VerhuizingCommand;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;


/**
 * De Class VerhuizingAanroeper.
 */
public class VerhuizingAanroeper extends BijhoudingAanroeper {

    // NB: Let op dat je bij het toevoegen van parameters, deze ook toevoegt in getParameterList()


    public static final String      PARAMETER_BSN                      = "BSN";
    public static final String PARAMETER_GEMEENTECODE = "GEMEENTECODE";
    public static final String      PARAMETER_NAAM_OPENBARE_RUIMTE     = "NAAM_OPENBARE_RUIMTE";
    public static final String      PARAMETER_DATUM_AANVANG_GELDIGHEID = "DATUM_AANVANG_GELDIGHEID";
    public static final String      PARAMETER_HUISNUMMER               = "HUISNUMMER";
    public static final String PARAMETER_HUISLETTER = "HUISLETTER";
    public static final String PARAMETER_HUISNUMMERTOEVOEGING = "HUISNUMMERTOEVOEGING";
    public static final String      PARAMETER_POSTCODE               = "POSTCODE";
    public static final String PARAMETER_WOONPLAATSCODE = "WOONPLAATSCODE";
    private static final String     BSN                                = "100000265";
    private static final String     NAAM_OPENBARE_RUIMTE               = "Vredenburg";
    private static final String     ORGANISATIE                        = "364";
    private static final String     APPLICATIE                         = "Testclient";
    private static final String     REFERENTIENUMMER                   = "12345";
    private static final String     GEMEENTECODE                       = "0344";
    private static final BigInteger AANVANG_GELDIGHEID                 = BigInteger.valueOf(20120819);
    private static final BigInteger TIJDSTIP_ONTLENING                 = BigInteger.valueOf(20120820);
    private static final BigInteger TIJDSTIP_LAATSTE_WIJZIGING         = BigInteger.valueOf(20120828);
    private static final BigInteger DATUM_AANVANG_ADRESHOUDING         = BigInteger.valueOf(20120821);

    /**
     * Instantieert een nieuwe verhuizing aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    public VerhuizingAanroeper(final Eigenschappen eigenschappen, final Map<String, String> parameterMap)
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
        MigratieVerhuizingBijhouding bijhouding = creeerMigratieVerhuizingBijhouding();
        VerhuizingCommand command = new VerhuizingCommand(bijhouding);
        Long duur = roepAan(command);
        TestClient.statistieken.bijwerkenNaBericht(Bericht.BERICHT.BIJHOUDING_VERHUIZING, getStatus(), duur);
    }

    /**
     * Creeer migratie verhuizing bijhouding.
     *
     * @return de migratie verhuizing bijhouding
     */
    private MigratieVerhuizingBijhouding creeerMigratieVerhuizingBijhouding() {
        ObjectFactory objectFactory = new ObjectFactory();
        MigratieVerhuizingBijhouding migratieVerhuizingBijhouding = objectFactory.createMigratieVerhuizingBijhouding();

        // Stuurgegevens-element
        List<GroepBerichtStuurgegevens> groepBerichtStuurgegevensList = migratieVerhuizingBijhouding.getStuurgegevens();
        GroepBerichtStuurgegevens groepBerichtStuurgegevens = creeerGroepBerichtStuurgegevens();
        groepBerichtStuurgegevensList.add(groepBerichtStuurgegevens);

        // Acties-element
        ContainerBerichtActies containerBerichtActies = objectFactory.createContainerBerichtActies();
        JAXBElement<ContainerBerichtActies> jaxbContainerBerichtActies =
            objectFactory.createObjecttypeBerichtActies(containerBerichtActies);
        migratieVerhuizingBijhouding.setActies(jaxbContainerBerichtActies);

        // Verhuizing
        List<ViewVerhuizing> verhuizingList = containerBerichtActies.getVerhuizing();
        ViewVerhuizing viewVerhuizing = objectFactory.createViewVerhuizing();
        verhuizingList.add(viewVerhuizing);

        // Aanvang geldigheid verhuizing
        JAXBElement<Datum> jaxbAanvangGeldigheid = creeerJaxbAanvangGeldigheid();
        viewVerhuizing.setDatumAanvangGeldigheid(jaxbAanvangGeldigheid);

        // Tijdstip ontlening
        JAXBElement<DatumTijd> jaxbTijdstipOntlening = creeerJaxbTijdstipOntlening();
        viewVerhuizing.setTijdstipOntlening(jaxbTijdstipOntlening);

        // Persoon
        List<ObjecttypePersoon> objecttypePersoonList = viewVerhuizing.getPersoon();
        ObjecttypePersoon objecttypePersoon = objectFactory.createObjecttypePersoon();
        objecttypePersoonList.add(objecttypePersoon);

        // Identificatienummers
        List<GroepPersoonIdentificatienummers> groepPersoonIdentificatienummersList =
            objecttypePersoon.getIdentificatienummers();

        // BSN
        Burgerservicenummer burgerservicenummer = new Burgerservicenummer();
        burgerservicenummer.setValue(getValueFromValueMap(PARAMETER_BSN, BSN));
        JAXBElement<Burgerservicenummer> jaxbBurgerservicenummer =
            objectFactory.createGroepPersoonIdentificatienummersBurgerservicenummer(burgerservicenummer);

        GroepPersoonIdentificatienummers groepPersoonIdentificatienummers =
            objectFactory.createGroepPersoonIdentificatienummers();
        groepPersoonIdentificatienummers.setBurgerservicenummer(jaxbBurgerservicenummer);
        groepPersoonIdentificatienummersList.add(groepPersoonIdentificatienummers);

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

        // Soort adres code
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
        JAXBElement<Postcode> jaxbPostcode = creeerJaxbPostcode();
        objecttypePersoonAdres.setPostcode(jaxbPostcode);

        // Woonplaatscode
        JAXBElement<Woonplaatscode> jaxbWoonplaatscode = creeerJaxbWoonplaatscode();
        objecttypePersoonAdres.setWoonplaatsCode(jaxbWoonplaatscode);

        return migratieVerhuizingBijhouding;
    }

    private JAXBElement<Woonplaatscode> creeerJaxbWoonplaatscode() {
        ObjectFactory objectFactory = new ObjectFactory();
        Woonplaatscode woonplaatscode = objectFactory.createWoonplaatscode();
        woonplaatscode.setValue(getValueFromValueMap(PARAMETER_WOONPLAATSCODE, null));
        JAXBElement<Woonplaatscode> jaxbWoonplaatscode = objectFactory.createObjecttypePersoonAdresWoonplaatsCode(woonplaatscode);
        return jaxbWoonplaatscode;
    }

    private JAXBElement<Huisnummertoevoeging> creeerJaxbHuisnummertoevoeging() {
        ObjectFactory objectFactory = new ObjectFactory();
        Huisnummertoevoeging huisnummertoevoeging = objectFactory.createHuisnummertoevoeging();
        huisnummertoevoeging.setValue(getValueFromValueMap(PARAMETER_HUISNUMMERTOEVOEGING, null));
        JAXBElement<Huisnummertoevoeging> jaxbHuisnummertoevoeging = objectFactory.createObjecttypePersoonAdresHuisnummertoevoeging(huisnummertoevoeging);
        return jaxbHuisnummertoevoeging;
    }

    private JAXBElement<Huisletter> creeerJaxbHuisletter() {
        ObjectFactory objectFactory = new ObjectFactory();
        Huisletter huisletter = objectFactory.createHuisletter();
        huisletter.setValue(getValueFromValueMap(PARAMETER_HUISLETTER, null));
        JAXBElement<Huisletter> jaxbHuisletter = objectFactory.createObjecttypePersoonAdresHuisletter(huisletter);
        return jaxbHuisletter;
    }

    private JAXBElement<Postcode> creeerJaxbPostcode() {
        ObjectFactory objectFactory = new ObjectFactory();
        Postcode postcode = objectFactory.createPostcode();
        postcode.setValue(getValueFromValueMap(PARAMETER_POSTCODE, creeerRandomPostcode()));
        JAXBElement<Postcode> jaxbPostcode = objectFactory.createObjecttypePersoonAdresPostcode(postcode);
        return jaxbPostcode;
    }

    private String creeerRandomPostcode() {
        String postcode = "" + (new Random().nextInt(1099) + 1000);
        postcode += StringUtils.upperCase(RandomStringUtils.randomAlphabetic(2));
        return postcode;
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
        datumAanvangAdreshouding.setValue(DATUM_AANVANG_ADRESHOUDING);
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
                PARAMETER_DATUM_AANVANG_GELDIGHEID, AANVANG_GELDIGHEID.toString()))));
        JAXBElement<Datum> jaxbAanvangGeldigheid =
            objectFactory.createObjecttypeActieDatumAanvangGeldigheid(aanvangGeldigheid);
        return jaxbAanvangGeldigheid;
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
        sleutelwaardetekst.setValue(REFERENTIENUMMER);
        groepBerichtStuurgegevens.setReferentienummer(sleutelwaardetekst);

        if (valueMap.containsKey(PARAMETER_PREVALIDATIE)) {
            Ja ja = new Ja();
            ja.setValue(JaS.J);
            JAXBElement<Ja> jaxbJa = objectFactory.createGroepBerichtStuurgegevensIndicatiePrevalidatie(ja);
            groepBerichtStuurgegevens.setIndicatiePrevalidatie(jaxbJa);
        }

        return groepBerichtStuurgegevens;
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#getParameterList()
     */
    @Override
    protected List<String> getParameterList() {
        return Arrays.asList(new String[] { BijhoudingAanroeper.PARAMETER_PREVALIDATIE, PARAMETER_BSN, PARAMETER_GEMEENTECODE,
            PARAMETER_NAAM_OPENBARE_RUIMTE, PARAMETER_HUISNUMMER, PARAMETER_HUISLETTER, PARAMETER_HUISNUMMERTOEVOEGING, PARAMETER_DATUM_AANVANG_GELDIGHEID, PARAMETER_POSTCODE, PARAMETER_WOONPLAATSCODE});
    }
}
