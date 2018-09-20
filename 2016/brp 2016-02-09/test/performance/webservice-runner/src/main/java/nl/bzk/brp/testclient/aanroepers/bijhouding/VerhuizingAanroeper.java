/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bijhouding;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.JAXBElement;

import nl.bzk.brp.bijhouding.service.BhgVerblijfAdres;
import nl.bzk.brp.brp0200.AangeverCode;
import nl.bzk.brp.brp0200.ActieRegistratieAdres;
import nl.bzk.brp.brp0200.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.brp0200.ContainerHandelingActiesVerhuizingIntergemeentelijk;
import nl.bzk.brp.brp0200.ContainerPersoonAdressen;
import nl.bzk.brp.brp0200.DatumMetOnzekerheid;
import nl.bzk.brp.brp0200.GemeenteCode;
import nl.bzk.brp.brp0200.HandelingVerhuizingIntergemeentelijk;
import nl.bzk.brp.brp0200.Huisletter;
import nl.bzk.brp.brp0200.Huisnummer;
import nl.bzk.brp.brp0200.Huisnummertoevoeging;
import nl.bzk.brp.brp0200.IdentificatiecodeAdresseerbaarObject;
import nl.bzk.brp.brp0200.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.brp0200.MigratieRegistreerVerhuizingBijhouding;
import nl.bzk.brp.brp0200.NaamEnumeratiewaarde;
import nl.bzk.brp.brp0200.NaamOpenbareRuimte;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.ObjecttypeBerichtBijhouding;
import nl.bzk.brp.brp0200.ObjecttypePersoon;
import nl.bzk.brp.brp0200.ObjecttypePersoonAdres;
import nl.bzk.brp.brp0200.Ontleningstoelichting;
import nl.bzk.brp.brp0200.PartijCode;
import nl.bzk.brp.brp0200.Postcode;
import nl.bzk.brp.brp0200.RedenWijzigingVerblijfCode;
import nl.bzk.brp.brp0200.SoortAdresCode;
import nl.bzk.brp.brp0200.SoortAdresCodeS;
import nl.bzk.brp.testclient.aanroepers.AbstractAanroeper;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bijhouding.VerhuizingCommand;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;


/**
 * Bijhoudings aanroeper die een registratie verhuizing bijhoudings bericht kan creeeren en via de test client
 * afvuurt.
 */
public class VerhuizingAanroeper extends AbstractAanroeper<BhgVerblijfAdres> {

    // NB: Let op dat je bij het toevoegen van parameters, deze ook toevoegt in getParameterLijst()
    /** Parameter voor BSN verhuizer. */
    public static final String PARAMETER_BSN                      = "BSN";
    /** Parameter voor gemeente nieuw adres van verhuizer. */
    public static final String PARAMETER_GEMEENTECODE             = "GEMEENTECODE";
    /** Parameter voor naam openbare ruimte nieuw adres van verhuizer. */
    public static final String PARAMETER_NAAM_OPENBARE_RUIMTE     = "NAAM_OPENBARE_RUIMTE";
    /** Parameter voor datum aanvang nieuw adres van verhuizer. */
    public static final String PARAMETER_DATUM_AANVANG_GELDIGHEID = "DATUM_AANVANG_GELDIGHEID";
    /** Parameter voor huisnummer nieuw adres van verhuizer. */
    public static final String PARAMETER_HUISNUMMER               = "HUISNUMMER";
    /** Parameter voor huisletter nieuw adres van verhuizer. */
    public static final String PARAMETER_HUISLETTER               = "HUISLETTER";
    /** Parameter voor huisnummertoevoeging nieuw adres van verhuizer. */
    public static final String PARAMETER_HUISNUMMERTOEVOEGING     = "HUISNUMMERTOEVOEGING";
    /** Parameter voor postcode nieuw adres van verhuizer. */
    public static final String PARAMETER_POSTCODE                 = "POSTCODE";
    /** Parameter voor woonplaats nieuw adres van verhuizer. */
    public static final String PARAMETER_WOONPLAATSNAAM           = "WOONPLAATSNAAM";
    public static final String PARAMETER_ADRESEERBAAROBJECT           = "ADRESEERBAAROBJECT";
    private static final String DOCUMENT_SOORT_NAAM = "Aangifte met betrekking tot verblijfplaats";

    /** Standaard parameter naam voor entiteit type administratieve handeling. */
    public static final String STUF_ENTITEITTYPE_ADMINISTRATIEVE_HANDELING = "AdministratieveHandeling";


    private static final String REDENWIJZIGINGADRESCODE    = "P";
    private static final String BSN                        = "100000265";
    private static final String NAAM_OPENBARE_RUIMTE       = "Vredenburg";
    private static final String AANVANG_GELDIGHEID         = "2012-08-19";
    private static final int    WILLEKEURIG_HUISNUMMER_MAX = 1000;
    private static final int    WILLEKEURIG_POSTCODE_MAX   = 1000;
    private static final int    WILLEKEURIG_POSTCODE_BASIS = 1099;

    private final ObjectFactory objectFactory = getObjectFactory();


    public VerhuizingAanroeper(final Eigenschappen eigenschappen) {
        super(eigenschappen);
    }
    /**
     * Instantieert een nieuwe verhuizing aanroeper.
     *
     *
     * @param eigenschappen de eigenschappen
     * @param bijhoudingPortType
     *@param parameterMap de parameter map  @throws Exception de exception
     */
    public VerhuizingAanroeper(final Eigenschappen eigenschappen, final BhgVerblijfAdres bijhoudingPortType,
                               final Map<String, String> parameterMap) throws Exception
    {
        super(eigenschappen, bijhoudingPortType, parameterMap);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#fire()
     */
    @Override
    public void fire() {
        MigratieRegistreerVerhuizingBijhouding bijhouding = creeerMigratieVerhuizingBijhouding();
        VerhuizingCommand command = new VerhuizingCommand(bijhouding);
        roepAan(command);
    }

    /**
     * Creeer migratie verhuizing bijhouding.
     *
     * @return de migratie verhuizing bijhouding
     */
    public MigratieRegistreerVerhuizingBijhouding creeerMigratieVerhuizingBijhouding() {
        // PartijCode
        final PartijCode partijCode = objectFactory.createPartijCode();
        partijCode.setValue(PARTIJ_CODE);

        final MigratieRegistreerVerhuizingBijhouding bijhouding =
            objectFactory.createMigratieRegistreerVerhuizingBijhouding();

        // Stuurgegevens en parameters
        Boolean prevalidatie = getValueFromValueMap(PARAMETER_PREVALIDATIE, "").equalsIgnoreCase("J");
        zetStuurgegevensEnParameters(bijhouding, prevalidatie);

        // AdministratieveHandeling
        final HandelingVerhuizingIntergemeentelijk intergemeentelijkeVerhuizing =
            objectFactory.createHandelingVerhuizingIntergemeentelijk();
        bijhouding.setVerhuizingIntergemeentelijk(
                objectFactory.createObjecttypeBerichtVerhuizingIntergemeentelijk(
                        intergemeentelijkeVerhuizing));
        intergemeentelijkeVerhuizing.setCommunicatieID(nextCommunicatieId());
        intergemeentelijkeVerhuizing.setObjecttype(STUF_ENTITEITTYPE_ADMINISTRATIEVE_HANDELING);

        intergemeentelijkeVerhuizing.setPartijCode(
                objectFactory.createObjecttypeAdministratieveHandelingPartijCode(partijCode));

        // Zet toelichting
        final Ontleningstoelichting ontleningstoelichting = new Ontleningstoelichting();
        ontleningstoelichting.setValue("Verhuizing");
        intergemeentelijkeVerhuizing.setToelichtingOntlening(
                objectFactory.createObjecttypeAdministratieveHandelingToelichtingOntlening(ontleningstoelichting));

        //Bijgehouden documenten
        final String documentCommunicatieID =
                super.setBijgehoudenDocumenten(intergemeentelijkeVerhuizing, partijCode, DOCUMENT_SOORT_NAAM);

        //Acties
        final ContainerHandelingActiesVerhuizingIntergemeentelijk acties =
            objectFactory.createContainerHandelingActiesVerhuizingIntergemeentelijk();
        intergemeentelijkeVerhuizing.setActies(
                objectFactory.createHandelingVerhuizingIntergemeentelijkActies(acties));

        // Verhuizing
        final ActieRegistratieAdres actieRegistratieAdres = objectFactory.createActieRegistratieAdres();
        acties.setRegistratieAdres(actieRegistratieAdres);
        actieRegistratieAdres.setDatumAanvangGeldigheid(BijhoudingUtil.maakJaxbDatumAanvangGeldigheid(
                getValueFromValueMap(PARAMETER_DATUM_AANVANG_GELDIGHEID, AANVANG_GELDIGHEID)));
        actieRegistratieAdres.setCommunicatieID(nextCommunicatieId());
        actieRegistratieAdres.setObjecttype(OBJECTTYPE_ACTIE);
        BijhoudingUtil.setBronnen(actieRegistratieAdres, documentCommunicatieID, nextCommunicatieId(),
                                  nextCommunicatieId());

        // Persoon
        final ObjecttypePersoon persoon = objectFactory.createObjecttypePersoon();
        actieRegistratieAdres.setPersoon(objectFactory.createObjecttypeActiePersoon(persoon));

        persoon.setObjectSleutel(getValueFromValueMap(PARAMETER_BSN, BSN));
        persoon.setObjecttype(OBJECTTYPE_PERSOON);
        persoon.setCommunicatieID(nextCommunicatieId());

        final ContainerPersoonAdressen adressen =
            objectFactory.createContainerPersoonAdressenNederlandBijhouding();
        persoon.setAdressen(objectFactory.createObjecttypePersoonAdressen(adressen));

        // Adres
        final ObjecttypePersoonAdres adres =
            objectFactory.createObjecttypePersoonAdres();
        adressen.getAdres().add(adres);

        adres.setObjecttype(OBJECTTYPE_PERSOONADRES);
        adres.setCommunicatieID(nextCommunicatieId());

        adres.setSoortCode(creeerJaxbSoortAdresCode());
        adres.setRedenWijzigingCode(creeerJaxbRedenWijzigingVerblijfCode());

        final AangeverCode aangever = new AangeverCode();
        aangever.setValue("P");
        adres.setAangeverAdreshoudingCode(
            objectFactory.createObjecttypePersoonAdresAangeverAdreshoudingCode(aangever));

        adres.setDatumAanvangAdreshouding(creeerJaxbDatumAanvangAdreshouding());

        adres.setNaamOpenbareRuimte(creeerJaxbNaamOpenbareRuimte());

        adres.setHuisnummer(creeerJaxbHuisnummer());
        adres.setHuisletter(creeerJaxbHuisletter());
        adres.setHuisnummertoevoeging(creeerJaxbHuisnummertoevoeging());
        adres.setPostcode(creeerJaxbPostcode());
        adres.setWoonplaatsnaam(creeerJaxbWoonplaatsnaam());
        adres.setGemeenteCode(creeerJaxbGemeentecode());
        adres.setAfgekorteNaamOpenbareRuimte(creeerJaxbAfgekorteNaamOpenbareRuimte());
        adres.setIdentificatiecodeAdresseerbaarObject(creeerJaxbAdresseerbaarObject());
        adres.setIdentificatiecodeNummeraanduiding(creeerJaxbIdentificatiecodeNummeraanduiding());

        return bijhouding;
    }

    /**
     * Zet de stuurgegevens en parameters op het bijhoudingsbericht.
     *
     * @param objecttypeBerichtBijhouding het bijhoudingsbericht.
     * @param prevalidatie prevalidatie
     */
    protected void zetStuurgegevensEnParameters(final ObjecttypeBerichtBijhouding objecttypeBerichtBijhouding,
                                                final Boolean prevalidatie)
    {
        zetStuurgegevens(objecttypeBerichtBijhouding);
        objecttypeBerichtBijhouding.setParameters(BijhoudingUtil.maakBerichtParameters(prevalidatie));
    }

    private JAXBElement<AfgekorteNaamOpenbareRuimte> creeerJaxbAfgekorteNaamOpenbareRuimte() {
        AfgekorteNaamOpenbareRuimte straat = objectFactory.createAfgekorteNaamOpenbareRuimte();
        straat.setValue(getValueFromValueMap("afgekortenor", "straat xxxxx"));
        return objectFactory.createObjecttypePersoonAdresAfgekorteNaamOpenbareRuimte(straat);
    }

    private JAXBElement<GemeenteCode> creeerJaxbGemeentecode() {
        GemeenteCode object = objectFactory.createGemeenteCode();
        object.setValue(getValueFromValueMap(PARAMETER_GEMEENTECODE, null));
        return objectFactory.createObjecttypePersoonAdresGemeenteCode(object);
    }

    private JAXBElement<IdentificatiecodeAdresseerbaarObject> creeerJaxbAdresseerbaarObject() {
        IdentificatiecodeAdresseerbaarObject object = objectFactory.createIdentificatiecodeAdresseerbaarObject();
        object.setValue(RandomStringUtils.randomNumeric(16));
        return objectFactory.createObjecttypePersoonAdresIdentificatiecodeAdresseerbaarObject(object);
    }
    private JAXBElement<IdentificatiecodeNummeraanduiding> creeerJaxbIdentificatiecodeNummeraanduiding() {
        IdentificatiecodeNummeraanduiding object = objectFactory.createIdentificatiecodeNummeraanduiding();
        object.setValue(RandomStringUtils.randomNumeric(16));
        //object.setValue(getValueFromValueMap("identcodenraand", null));
        return objectFactory.createObjecttypePersoonAdresIdentificatiecodeNummeraanduiding(object);
    }

    /**
     * Creeer jaxb woonplaats code.
     *
     * @return een JAXB element voor voonplaats code.
     */
    private JAXBElement<NaamEnumeratiewaarde> creeerJaxbWoonplaatsnaam() {
        NaamEnumeratiewaarde woonplaatsnaam = objectFactory.createNaamEnumeratiewaarde();
        woonplaatsnaam.setValue(getValueFromValueMap(PARAMETER_WOONPLAATSNAAM, "Rotterdam"));
        return objectFactory.createObjecttypePersoonAdresWoonplaatsnaam(woonplaatsnaam);
    }

    /**
     * Creeer jaxb huisnummertoevoeging.
     *
     * @return een JAXB element voor huisnummertoevoeging.
     */
    private JAXBElement<Huisnummertoevoeging> creeerJaxbHuisnummertoevoeging() {
        Huisnummertoevoeging huisnummertoevoeging = objectFactory.createHuisnummertoevoeging();
        huisnummertoevoeging.setValue(getValueFromValueMap(PARAMETER_HUISNUMMERTOEVOEGING, "I"));
        return objectFactory.createObjecttypePersoonAdresHuisnummertoevoeging(huisnummertoevoeging);
    }

    /**
     * Creeer jaxb huisletter.
     *
     * @return een JAXB element voor huisletter.
     */
    private JAXBElement<Huisletter> creeerJaxbHuisletter() {
        Huisletter huisletter = objectFactory.createHuisletter();
        huisletter.setValue(getValueFromValueMap(PARAMETER_HUISLETTER, "A"));
        return objectFactory.createObjecttypePersoonAdresHuisletter(huisletter);
    }

    /**
     * Creeer jaxb postcode.
     *
     * @return een JAXB element voor postcode.
     */
    private JAXBElement<Postcode> creeerJaxbPostcode() {
        Postcode postcode = objectFactory.createPostcode();
        postcode.setValue(getValueFromValueMap(PARAMETER_POSTCODE, creeerRandomPostcode()));
        return objectFactory.createObjecttypePersoonAdresPostcode(postcode);
    }

    /**
     * Retourneert een willekeurig gegenereerd poscode.
     *
     * @return een willekeurig postcode.
     */
    private String creeerRandomPostcode() {
        String postcode = "" + (new Random().nextInt(WILLEKEURIG_POSTCODE_BASIS) + WILLEKEURIG_POSTCODE_MAX);
        postcode += StringUtils.upperCase(RandomStringUtils.randomAlphabetic(2));
        return postcode;
    }

    /**
     * Creeer jaxb huisnummer.
     *
     * @return de jAXB element
     */
    private JAXBElement<Huisnummer> creeerJaxbHuisnummer() {
        Huisnummer huisnummer = objectFactory.createHuisnummer();
        huisnummer.setValue(getValueFromValueMap(PARAMETER_HUISNUMMER, creeerRandomHuisnummer()));
        return objectFactory.createObjecttypePersoonAdresHuisnummer(huisnummer);
    }

    /**
     * Creeer random huisnummer.
     *
     * @return de string
     */
    private String creeerRandomHuisnummer() {
        return "" + new Random().nextInt(WILLEKEURIG_HUISNUMMER_MAX);
    }

    /**
     * Creeer jaxb naam openbare ruimte.
     *
     * @return de jAXB element
     */
    private JAXBElement<NaamOpenbareRuimte> creeerJaxbNaamOpenbareRuimte() {
        NaamOpenbareRuimte naamOpenbareRuimte = objectFactory.createNaamOpenbareRuimte();
        naamOpenbareRuimte.setValue(getValueFromValueMap(PARAMETER_NAAM_OPENBARE_RUIMTE, NAAM_OPENBARE_RUIMTE));
        return objectFactory.createObjecttypePersoonAdresNaamOpenbareRuimte(naamOpenbareRuimte);
    }

    /**
     * Creeer jaxb datum aanvang adreshouding.
     *
     * @return de jAXB element
     */
    private JAXBElement<DatumMetOnzekerheid> creeerJaxbDatumAanvangAdreshouding() {
        final DatumMetOnzekerheid datumMetOnzekerheid = new DatumMetOnzekerheid();
        datumMetOnzekerheid.setValue(getValueFromValueMap(PARAMETER_DATUM_AANVANG_GELDIGHEID, AANVANG_GELDIGHEID));

        return objectFactory.createObjecttypePersoonAdresDatumAanvangAdreshouding(datumMetOnzekerheid);
    }

    /**
     * Creeer jaxb reden wijziging adres code.
     *
     * @return de jAXB element
     */
    private JAXBElement<RedenWijzigingVerblijfCode> creeerJaxbRedenWijzigingVerblijfCode() {
        RedenWijzigingVerblijfCode redenWijzigingVerblijfCode = objectFactory.createRedenWijzigingVerblijfCode();
        redenWijzigingVerblijfCode.setValue(REDENWIJZIGINGADRESCODE);
        return objectFactory.createObjecttypePersoonAdresRedenWijzigingCode(redenWijzigingVerblijfCode);
    }

    /**
     * Creeer jaxb soort adres code.
     *
     * @return de jAXB element
     */
    private JAXBElement<SoortAdresCode> creeerJaxbSoortAdresCode() {
        SoortAdresCode soortAdresCode = objectFactory.createSoortAdresCode();
        soortAdresCode.setValue(SoortAdresCodeS.W);
        return objectFactory.createObjecttypePersoonAdresSoortCode(soortAdresCode);
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#getParameterLijst()
     */
    @Override
    protected List<String> getParameterLijst() {
        return Arrays.asList(PARAMETER_PREVALIDATIE, PARAMETER_BSN, PARAMETER_GEMEENTECODE,
            PARAMETER_NAAM_OPENBARE_RUIMTE, PARAMETER_HUISNUMMER, PARAMETER_HUISLETTER,
            PARAMETER_HUISNUMMERTOEVOEGING, PARAMETER_DATUM_AANVANG_GELDIGHEID, PARAMETER_POSTCODE,
            PARAMETER_WOONPLAATSNAAM);
    }
    @Override
    public BERICHT getBericht() {
        return BERICHT.BIJHOUDING_VERHUIZING;
    }
}
