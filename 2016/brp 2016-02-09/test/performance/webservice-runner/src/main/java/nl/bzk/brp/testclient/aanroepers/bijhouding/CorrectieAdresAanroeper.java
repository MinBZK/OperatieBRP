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
import nl.bzk.brp.brp0200.ActieCorrectieAdres;
import nl.bzk.brp.brp0200.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.brp0200.ContainerAdministratieveHandelingGedeblokkeerdeMeldingenBijhouding;
import nl.bzk.brp.brp0200.ContainerHandelingActiesCorrectieAdresBijhouding;
import nl.bzk.brp.brp0200.ContainerPersoonAdressenCorrectieBijhouding;
import nl.bzk.brp.brp0200.DatumMetOnzekerheid;
import nl.bzk.brp.brp0200.GemeenteCode;
import nl.bzk.brp.brp0200.HandelingCorrectieAdres;
import nl.bzk.brp.brp0200.Huisletter;
import nl.bzk.brp.brp0200.Huisnummer;
import nl.bzk.brp.brp0200.Huisnummertoevoeging;
import nl.bzk.brp.brp0200.IdentificatiecodeAdresseerbaarObject;
import nl.bzk.brp.brp0200.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.brp0200.LandGebiedCode;
import nl.bzk.brp.brp0200.MigratieCorrigeerAdresBijhouding;
import nl.bzk.brp.brp0200.NaamEnumeratiewaarde;
import nl.bzk.brp.brp0200.NaamOpenbareRuimte;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.ObjecttypeBerichtBijhouding;
import nl.bzk.brp.brp0200.ObjecttypeGedeblokkeerdeMeldingBijhouding;
import nl.bzk.brp.brp0200.ObjecttypePersoon;
import nl.bzk.brp.brp0200.ObjecttypePersoonAdresCorrectieBijhouding;
import nl.bzk.brp.brp0200.Ontleningstoelichting;
import nl.bzk.brp.brp0200.PartijCode;
import nl.bzk.brp.brp0200.Postcode;
import nl.bzk.brp.brp0200.RedenWijzigingVerblijfCode;
import nl.bzk.brp.brp0200.RegelCode;
import nl.bzk.brp.brp0200.SoortAdresCode;
import nl.bzk.brp.brp0200.SoortAdresCodeS;
import nl.bzk.brp.testclient.aanroepers.AbstractAanroeper;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Constanten;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bijhouding.CorrectieAdresCommand;
import nl.bzk.brp.testclient.util.ObjectSleutelHandler;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Bijhoudings aanroeper die een correctie adres bijhoudings bericht kan creeeren en via de test client
 * afvuurt.
 */
public class CorrectieAdresAanroeper extends AbstractAanroeper {

    // NB: Let op dat je bij het toevoegen van parameters, deze ook toevoegt in getParameterLijst()

    /** Parameter voor BSN verhuizer/te corrigeren persoon. */
    public static final String PARAMETER_BSN                      = "BSN";
    public static final String PARAMETER_PERSID                   = "PERSID";
    /** Parameter voor naam openbare ruimte correctie adres van verhuizer/te corrigeren persoon. */
    public static final String PARAMETER_NAAM_OPENBARE_RUIMTE     = "NAAM_OPENBARE_RUIMTE";
    /** Parameter voor datum aanvang correctie adres van verhuizer/te corrigeren persoon. */
    public static final String PARAMETER_DATUM_AANVANG_GELDIGHEID = "DATUM_AANVANG_GELDIGHEID";
    /** Parameter voor datum einde correctie adres van verhuizer/te corrigeren persoon. */
    public static final String PARAMETER_DATUM_EINDE_GELDIGHEID   = "DATUM_EINDE_GELDIGHEID";
    /** Parameter voor woonplaats correctie adres van verhuizer/te corrigeren persoon. */
    public static final String PARAMETER_WOONPLAATS               = "WOONPLAATS_NAAM";
    /** Parameter voor postcode correctie adres van verhuizer/te corrigeren persoon. */
    public static final String PARAMETER_POSTCODE                 = "POSTCODE";
    /** Parameter voor huisnummer correctie adres van verhuizer/te corrigeren persoon. */
    public static final String PARAMETER_HUISNUMMER               = "HUISNUMMER";
    /** Parameter voor huislettermte correctie adres van verhuizer/te corrigeren persoon. */
    public static final String PARAMETER_HUISLETTER               = "HUISLETTER";
    /** Parameter voor huisnummertoevoeging correctie adres van verhuizer/te corrigeren persoon. */
    public static final String PARAMETER_HUISNUMMERTOEVOEGING     = "HUISNUMMERTOEVOEGING";
    /** Parameter voor gemeente correctie adres van verhuizer/te corrigeren persoon. */
    public static final String PARAMETER_GEMEENTECODE             = "GEMEENTE";
    /** Parameter voor referentienummer correctie adres van verhuizer/te corrigeren persoon. */
    public static final String PARAMETER_REFERENTIENUMMER         = "REFERENTIENUMMER";

    private static final String BSN                     = "100000009";
    private static final String GEMEENTECODE            = "0599";
    private static final String NAAM_OPENBARE_RUIMTE    = "Blaak";
    private static final String DOCUMENT_SOORT_NAAM     = "Aangifte met betrekking tot verblijfplaats";
    private static final String PARTIJCODE              = "036101";
    private static final String POSTCODE                = "3011GB";
    private static final String WOONPLAATS_NAAM         = "Rotterdam";
    private static final String OVERRULE_REGELCODE      = "BRBY0525";
    private static final String AANVANG_GELDIGHEID      = "2010-07-19";
    private static final String EINDE_GELDIGHEID        = "2011-08-19";
    private static final String REDENWIJZIGINGADRESCODE = "A";
    private static final int    RANDOM_HUISNUMMER_MAX   = 1000;

    private final ObjectFactory objectFactory = getObjectFactory();

    /**
     * Instantieert een nieuwe Correctie adres aanroeper.
     *
     * @param eigenschappen eigenschappen
     */
    public CorrectieAdresAanroeper(final Eigenschappen eigenschappen) {
        super(eigenschappen);
    }

    /**
     * Instantieert een nieuwe correctie adres aanroeper.
     *
     *
     * @param eigenschappen de eigenschappen
     * @param bijhoudingPortType bijhouding port type
     * @param parameterMap de parameter map  @throws Exception de exception
     * @throws Exception exception
     */
    public CorrectieAdresAanroeper(final Eigenschappen eigenschappen, final BhgVerblijfAdres bijhoudingPortType,
                                   final Map<String, String> parameterMap) throws Exception
    {
        super(eigenschappen, bijhoudingPortType, parameterMap);
    }

    @Override
    public void fire() {
        MigratieCorrigeerAdresBijhouding bijhouding = creeerMigratieCorrigeerAdresBijhouding();
        CorrectieAdresCommand command = new CorrectieAdresCommand(bijhouding);
        roepAan(command);
    }

    /**
     * Creeer migratie correctie adres binnen nl bijhouding.
     *
     * @return de migratie correctie adres binnen nl bijhouding
     */
    public MigratieCorrigeerAdresBijhouding creeerMigratieCorrigeerAdresBijhouding() {
        final PartijCode partijCode = objectFactory.createPartijCode();
        partijCode.setValue(PARTIJCODE);

        final MigratieCorrigeerAdresBijhouding bijhouding =
            objectFactory.createMigratieCorrigeerAdresBijhouding();

        // Stuurgegevens en parameters.
        final Boolean prevalidatie = getValueFromValueMap(PARAMETER_PREVALIDATIE, "").equalsIgnoreCase("J");
        zetStuurgegevensEnParameters(bijhouding, prevalidatie);

        //Administratieve handeling Correctie adres
        final HandelingCorrectieAdres handelingCorrectieAdres = objectFactory.createHandelingCorrectieAdres();
        final JAXBElement<HandelingCorrectieAdres> correctieAdres =
                objectFactory.createObjecttypeBerichtCorrectieAdres(handelingCorrectieAdres);
        bijhouding.setCorrectieAdres(correctieAdres);
        final String admHndComId = nextCommunicatieId();
        handelingCorrectieAdres.setCommunicatieID(admHndComId);
        handelingCorrectieAdres.setObjecttype(OBJECTTYPE_ADMINISTRATIEVE_HANDELING);

        handelingCorrectieAdres.setPartijCode(
                objectFactory.createObjecttypeAdministratieveHandelingPartijCode(partijCode));

        // Zet toelichting
        final Ontleningstoelichting ontleningstoelichting = new Ontleningstoelichting();
        ontleningstoelichting.setValue("Correctie adres");
        handelingCorrectieAdres.setToelichtingOntlening(
                objectFactory.createObjecttypeAdministratieveHandelingToelichtingOntlening(ontleningstoelichting));

        // Gedeblokkeerde meldingen.
        final ContainerAdministratieveHandelingGedeblokkeerdeMeldingenBijhouding gedeblokkeerdeMeldingenBijhouding =
            objectFactory.createContainerAdministratieveHandelingGedeblokkeerdeMeldingenBijhouding();
        handelingCorrectieAdres.setGedeblokkeerdeMeldingen(
            objectFactory.createObjecttypeAdministratieveHandelingGedeblokkeerdeMeldingen(
                gedeblokkeerdeMeldingenBijhouding));

        final ObjecttypeGedeblokkeerdeMeldingBijhouding gedeblokkeerdeMeldingBijhouding =
            objectFactory.createObjecttypeGedeblokkeerdeMeldingBijhouding();
        gedeblokkeerdeMeldingBijhouding.setObjecttype(OBJECTTYPE_GEDEBLOKKEERDE_MELDING);
        gedeblokkeerdeMeldingBijhouding.setCommunicatieID(nextCommunicatieId());
        gedeblokkeerdeMeldingBijhouding.setReferentieID(admHndComId);

        final RegelCode regelCode = new RegelCode();
        regelCode.setValue(OVERRULE_REGELCODE);
        gedeblokkeerdeMeldingBijhouding.setRegelCode(
            objectFactory.createObjecttypeGedeblokkeerdeMeldingRegelCode(regelCode));
        gedeblokkeerdeMeldingenBijhouding.getGedeblokkeerdeMelding().add(gedeblokkeerdeMeldingBijhouding);

        final String documentCommunicatieId =
                setBijgehoudenDocumenten(handelingCorrectieAdres, partijCode, DOCUMENT_SOORT_NAAM);

        // Acties
        final ContainerHandelingActiesCorrectieAdresBijhouding
            containerHandelingActiesCorrectieAdresBijhouding =
            objectFactory.createContainerHandelingActiesCorrectieAdresBijhouding();
        handelingCorrectieAdres.setActies(
                objectFactory.createHandelingCorrectieAdresActies(containerHandelingActiesCorrectieAdresBijhouding));

        //Correctie Adres NL
        final ActieCorrectieAdres actieCorrectieAdres = objectFactory.createActieCorrectieAdres();
        containerHandelingActiesCorrectieAdresBijhouding.getCorrectieAdres().add(actieCorrectieAdres);
        actieCorrectieAdres.setCommunicatieID(nextCommunicatieId());
        actieCorrectieAdres.setObjecttype(OBJECTTYPE_ACTIE);

        // Aanvang geldigheid
        actieCorrectieAdres.setDatumAanvangGeldigheid(
                BijhoudingUtil.maakJaxbDatumAanvangGeldigheid(
                        getValueFromValueMap(PARAMETER_DATUM_AANVANG_GELDIGHEID, AANVANG_GELDIGHEID)));

        // Einde geldigheid
        actieCorrectieAdres.setDatumEindeGeldigheid(creeerJaxbEindeGeldigheid());

        BijhoudingUtil.setBronnen(actieCorrectieAdres, documentCommunicatieId, nextCommunicatieId(),
                                  nextCommunicatieId());

        // Persoon
        final ObjecttypePersoon persoon = new ObjecttypePersoon();
        actieCorrectieAdres.setPersoon(objectFactory.createObjecttypeActiePersoon(persoon));

        //Technische sleutel van persoon
        persoon.setObjecttype(OBJECTTYPE_PERSOON);
        
        
        long persoonId = Long.valueOf(getValueFromValueMap(PARAMETER_PERSID, null));
        String encodedPersoonId = ObjectSleutelHandler.genereerObjectSleutelString(persoonId, Integer.valueOf("101"), System.currentTimeMillis());
        
        persoon.setObjectSleutel(encodedPersoonId);
        
        
        persoon.setCommunicatieID(nextCommunicatieId());

        // Adressen
        ContainerPersoonAdressenCorrectieBijhouding persoonAdressenNederlandBijhouding =
            objectFactory.createContainerPersoonAdressenCorrectieBijhouding();
        persoon.setAdressen(
            objectFactory.createObjecttypePersoonAdressen(persoonAdressenNederlandBijhouding));

        ObjecttypePersoonAdresCorrectieBijhouding adresNederlandBijhouding =
            objectFactory.createObjecttypePersoonAdresCorrectieBijhouding();
        persoonAdressenNederlandBijhouding.getAdres().add(adresNederlandBijhouding);

        adresNederlandBijhouding.setSoortCode(creeerJaxbSoortAdresCode());
        adresNederlandBijhouding.setRedenWijzigingCode(creeerJaxbRedenWijzigingAdresCode());
        adresNederlandBijhouding.setDatumAanvangAdreshouding(creeerJaxbDatumAanvangAdreshouding());
        adresNederlandBijhouding.setIdentificatiecodeAdresseerbaarObject(creeerJaxbAdresseerbaarObject());
        adresNederlandBijhouding.setIdentificatiecodeNummeraanduiding(creeerJaxbIdentificatiecodeNummeraanduiding());
        adresNederlandBijhouding.setGemeenteCode(creeerJaxbGemeentecode());
        adresNederlandBijhouding.setAfgekorteNaamOpenbareRuimte(creeerJaxbAfgekorteNaamOpenbareRuimte());
        adresNederlandBijhouding.setNaamOpenbareRuimte(creeerJaxbNaamOpenbareRuimte());
        adresNederlandBijhouding.setHuisnummer(creeerJaxbHuisnummer());
        adresNederlandBijhouding.setHuisletter(creeerJaxbHuisletter());
        adresNederlandBijhouding.setHuisnummertoevoeging(creeerJaxbHuisnummertoevoeging());

        // Postcode
        Postcode postcode = new Postcode();
        postcode.setValue(getValueFromValueMap(PARAMETER_POSTCODE, POSTCODE));
        adresNederlandBijhouding.setPostcode(objectFactory.createObjecttypePersoonAdresPostcode(postcode));

        // Woonplaats code
        NaamEnumeratiewaarde woonplaatsNaam = new NaamEnumeratiewaarde();
        woonplaatsNaam.setValue(getValueFromValueMap(PARAMETER_WOONPLAATS, WOONPLAATS_NAAM));
        JAXBElement<NaamEnumeratiewaarde> jaxbWoonplaatsNaam =
            objectFactory.createObjecttypePersoonAdresWoonplaatsnaam(woonplaatsNaam);
        adresNederlandBijhouding.setWoonplaatsnaam(jaxbWoonplaatsNaam);

        final LandGebiedCode landGebiedNederland = new LandGebiedCode();
        landGebiedNederland.setValue("6030");
        adresNederlandBijhouding.setLandGebiedCode(
                objectFactory.createObjecttypePersoonAdresLandGebiedCode(landGebiedNederland));

        adresNederlandBijhouding.setObjecttype(OBJECTTYPE_PERSOONADRES);
        adresNederlandBijhouding.setCommunicatieID(nextCommunicatieId());

        return bijhouding;
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
     * Creeer jaxb huisletter.
     *
     * @return de jAXB element
     */
    private JAXBElement<Huisletter> creeerJaxbHuisletter() {
        Huisletter huisletter = objectFactory.createHuisletter();
        huisletter.setValue(getValueFromValueMap(PARAMETER_HUISLETTER, "A"));
        return objectFactory.createObjecttypePersoonAdresHuisletter(huisletter);
    }

    /**
     * Creeer jaxb huisnummertoevoeging.
     *
     * @return de jAXB element
     */
    private JAXBElement<Huisnummertoevoeging> creeerJaxbHuisnummertoevoeging() {
        Huisnummertoevoeging huisnummertoevoeging = objectFactory.createHuisnummertoevoeging();
        huisnummertoevoeging.setValue(getValueFromValueMap(PARAMETER_HUISNUMMERTOEVOEGING, "I"));
        return objectFactory.createObjecttypePersoonAdresHuisnummertoevoeging(huisnummertoevoeging);
    }

    /**
     * Creeer random huisnummer.
     *
     * @return de string
     */
    private String creeerRandomHuisnummer() {
        return "" + new Random().nextInt(RANDOM_HUISNUMMER_MAX);
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
     * Creeer jaxb gemeentecode.
     *
     * @return de jAXB element
     */
    private JAXBElement<GemeenteCode> creeerJaxbGemeentecode() {
        GemeenteCode gemeentecode = objectFactory.createGemeenteCode();
        gemeentecode.setValue(getValueFromValueMap(PARAMETER_GEMEENTECODE, GEMEENTECODE));
        return objectFactory.createObjecttypePersoonAdresGemeenteCode(gemeentecode);
    }

    /**
     * Creeer jaxb datum aanvang adreshouding.
     *
     * @return de jAXB element
     */
    private JAXBElement<DatumMetOnzekerheid> creeerJaxbDatumAanvangAdreshouding() {
        final DatumMetOnzekerheid datumAanvangAdreshouding = new DatumMetOnzekerheid();
        datumAanvangAdreshouding.setValue(getValueFromValueMap(
                PARAMETER_DATUM_AANVANG_GELDIGHEID, AANVANG_GELDIGHEID));

        return objectFactory.createObjecttypePersoonAdresDatumAanvangAdreshouding(datumAanvangAdreshouding);
    }

    /**
     * Creeer jaxb reden wijziging adres code.
     *
     * @return de jAXB element
     */
    private JAXBElement<RedenWijzigingVerblijfCode> creeerJaxbRedenWijzigingAdresCode() {
        RedenWijzigingVerblijfCode redenWijzigingAdresCode = objectFactory.createRedenWijzigingVerblijfCode();
        redenWijzigingAdresCode.setValue(REDENWIJZIGINGADRESCODE);
        return objectFactory.createObjecttypePersoonAdresRedenWijzigingCode(redenWijzigingAdresCode);
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

    /**
     * Creeer jaxb einde geldigheid.
     *
     * @return de jAXB element
     */
    private JAXBElement<DatumMetOnzekerheid> creeerJaxbEindeGeldigheid() {
        final DatumMetOnzekerheid datumEindeGeldigheid = new DatumMetOnzekerheid();
        datumEindeGeldigheid.setValue(getValueFromValueMap(PARAMETER_DATUM_EINDE_GELDIGHEID, EINDE_GELDIGHEID));

        return objectFactory.createDatumEindeGeldigheid(datumEindeGeldigheid);
    }

    /**
     * Creeer jaxb adresseerbaar object.
     *
     * @return jAXB element
     */
    private JAXBElement<IdentificatiecodeAdresseerbaarObject> creeerJaxbAdresseerbaarObject() {
        IdentificatiecodeAdresseerbaarObject object = objectFactory.createIdentificatiecodeAdresseerbaarObject();
        object.setValue(RandomStringUtils.randomNumeric(Constanten.ZESTIEN));
        return objectFactory.createObjecttypePersoonAdresIdentificatiecodeAdresseerbaarObject(object);
    }

    /**
     * Creeer jaxb identificatiecode nummeraanduiding.
     *
     * @return jAXB element
     */
    private JAXBElement<IdentificatiecodeNummeraanduiding> creeerJaxbIdentificatiecodeNummeraanduiding() {
        IdentificatiecodeNummeraanduiding object = objectFactory.createIdentificatiecodeNummeraanduiding();
        object.setValue(RandomStringUtils.randomNumeric(Constanten.ZESTIEN));
        return objectFactory.createObjecttypePersoonAdresIdentificatiecodeNummeraanduiding(object);
    }

    /**
     * Creeer jaxb afgekorte naam openbare ruimte.
     *
     * @return jAXB element
     */
    private JAXBElement<AfgekorteNaamOpenbareRuimte> creeerJaxbAfgekorteNaamOpenbareRuimte() {
        AfgekorteNaamOpenbareRuimte straat = objectFactory.createAfgekorteNaamOpenbareRuimte();
        // Ik weet het, het zou de afgek. NOR zijn en niet NOR
        straat.setValue(getValueFromValueMap(PARAMETER_NAAM_OPENBARE_RUIMTE, "Straat "
                + RandomStringUtils.randomNumeric(Constanten.DRIE)));
        return objectFactory.createObjecttypePersoonAdresAfgekorteNaamOpenbareRuimte(straat);
    }

    @Override
    protected List<String> getParameterLijst() {
        return Arrays.asList(PARAMETER_PREVALIDATIE, PARAMETER_REFERENTIENUMMER,
            PARAMETER_BSN, PARAMETER_PERSID, PARAMETER_NAAM_OPENBARE_RUIMTE, PARAMETER_DATUM_AANVANG_GELDIGHEID,
            PARAMETER_DATUM_EINDE_GELDIGHEID, PARAMETER_HUISNUMMER, PARAMETER_HUISLETTER,
            PARAMETER_HUISNUMMERTOEVOEGING, PARAMETER_POSTCODE, PARAMETER_WOONPLAATS,
            PARAMETER_GEMEENTECODE);
    }

    @Override
    public BERICHT getBericht() {
        return BERICHT.BIJHOUDING_CORRECTIE_ADRES;
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
}
