/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bijhouding;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.bijhouding.service.BhgHuwelijkGeregisteerdPartnerschap;
import nl.bzk.brp.brp0200.ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.brp0200.ActieRegistratieNaamgebruik;
import nl.bzk.brp.brp0200.ContainerHandelingActiesVoltrekkingHuwelijkInNederland;
import nl.bzk.brp.brp0200.ContainerRelatieBetrokkenhedenHuwelijkPartnerschapBijhouding;
import nl.bzk.brp.brp0200.DatumMetOnzekerheid;
import nl.bzk.brp.brp0200.GemeenteCode;
import nl.bzk.brp.brp0200.GroepHuwelijkGeregistreerdPartnerschapRelatie;
import nl.bzk.brp.brp0200.GroepPersoonNaamgebruik;
import nl.bzk.brp.brp0200.HandelingVoltrekkingHuwelijkInNederlandBijhouding;
import nl.bzk.brp.brp0200.HuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhouding;
import nl.bzk.brp.brp0200.JaNee;
import nl.bzk.brp.brp0200.JaNeeS;
import nl.bzk.brp.brp0200.NaamgebruikCode;
import nl.bzk.brp.brp0200.NaamgebruikCodeS;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.ObjecttypeBerichtBijhouding;
import nl.bzk.brp.brp0200.ObjecttypeHuwelijk;
import nl.bzk.brp.brp0200.ObjecttypePartnerBijhouding;
import nl.bzk.brp.brp0200.ObjecttypePersoon;
import nl.bzk.brp.brp0200.Ontleningstoelichting;
import nl.bzk.brp.brp0200.PartijCode;
import nl.bzk.brp.brp0200.PersoonPartnerBijhouding;
import nl.bzk.brp.testclient.aanroepers.AbstractAanroeper;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bijhouding.HuwelijkCommand;
import nl.bzk.brp.testclient.util.DatumUtil;


/**
 * Bijhoudings aanroeper die een registratie huwelijk bijhoudings bericht kan creeeren en via de test client
 * afvuurt.
 */
public class RegistreerHuwelijkEnPartnerschapAanroeper extends AbstractAanroeper {

    // NB: Let op dat je bij het toevoegen van parameters, deze ook toevoegt in getParameterLijst()
    /** Parameter voor BSN van partner 1. */
    public static final String      PARAMETER_BSN1                      = "BSN1";
    /** Parameter voor BSN van partner 2. */
    public static final String      PARAMETER_BSN2                      = "BSN2";
    /** Parameter voor naam gebruik. */
    public static final String      PARAMETER_BSN_NAAMGEBRUIK           = "BSN_NAAMGEBRUIK";
    /** Parameter voor referentienummer. */
    public static final String      PARAMETER_REFERENTIENUMMER          = "REFERENTIENUMMER";
    private static final String     BSN1                                = "100075290";
    private static final String     BSN2                                = "100027933";
    private static final String     GEMEENTECODE                        =  "0001";//"0530";
    private static final String     BSN_NAAMGEBRUIK                     = "100027933";
    private static final String     DATUM_AANVANG_GELDIGHEID            = DatumUtil.vandaagXmlString();
    private static final String     DOCUMENT_SOORT_NAAM                 = "Huwelijksakte";

    public RegistreerHuwelijkEnPartnerschapAanroeper(final Eigenschappen eigenschappen) {
        super(eigenschappen);
    }

    /**
     * Instantieert een nieuwe registreer huwelijk en partnerschap aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param bijhoudingPortType bijhouding port type
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    public RegistreerHuwelijkEnPartnerschapAanroeper(final Eigenschappen eigenschappen,
                                                     final BhgHuwelijkGeregisteerdPartnerschap bijhoudingPortType,
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
        final HuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhouding bijhouding =
                creeerHuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhouding();
        final HuwelijkCommand command = new HuwelijkCommand(bijhouding);
        roepAan(command);
    }

    /**
     * Creeer huwelijk partnerschap registratie huwelijk bijhouding.
     *
     * @return de huwelijk partnerschap registratie huwelijk bijhouding
     */
    public HuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhouding
    creeerHuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhouding()
    {
        final ObjectFactory objectFactory = getObjectFactory();
        final HuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhouding bhgHuwelijkGeregisteerdPartnerschap =
            objectFactory.createHuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhouding();

        // Stuurgegevens en parameters.
        Boolean prevalidatie = getValueFromValueMap(PARAMETER_PREVALIDATIE, "").equalsIgnoreCase("J");
        zetStuurgegevensEnParameters(bhgHuwelijkGeregisteerdPartnerschap, prevalidatie);

        //Administratieve handeling
        HandelingVoltrekkingHuwelijkInNederlandBijhouding handelingSluitingHuwelijkNederlandBijhouding =
                objectFactory.createHandelingVoltrekkingHuwelijkInNederlandBijhouding();
        bhgHuwelijkGeregisteerdPartnerschap.setVoltrekkingHuwelijkInNederland(
                objectFactory.createObjecttypeBerichtVoltrekkingHuwelijkInNederland(
                        handelingSluitingHuwelijkNederlandBijhouding));

        PartijCode partijCode = objectFactory.createPartijCode();
        partijCode.setValue(PARTIJ_CODE);
        handelingSluitingHuwelijkNederlandBijhouding.setPartijCode(
                objectFactory.createObjecttypeAdministratieveHandelingPartijCode(partijCode));

        // Zet toelichting
        final Ontleningstoelichting ontleningstoelichting = new Ontleningstoelichting();
        ontleningstoelichting.setValue("Huwelijk");
        handelingSluitingHuwelijkNederlandBijhouding.setToelichtingOntlening(
                objectFactory.createObjecttypeAdministratieveHandelingToelichtingOntlening(ontleningstoelichting));

        handelingSluitingHuwelijkNederlandBijhouding.setObjecttype(OBJECTTYPE_ADMINISTRATIEVE_HANDELING);
        handelingSluitingHuwelijkNederlandBijhouding.setCommunicatieID(nextCommunicatieId());

        //Bijgehouden documenten
        final String documentCommunicatieID =
                super.setBijgehoudenDocumenten(handelingSluitingHuwelijkNederlandBijhouding, partijCode, DOCUMENT_SOORT_NAAM);

        // Acties-element
        ContainerHandelingActiesVoltrekkingHuwelijkInNederland containerHandelingActiesVoltrekkingHuwelijkInNederland =
                objectFactory.createContainerHandelingActiesVoltrekkingHuwelijkInNederland();
        handelingSluitingHuwelijkNederlandBijhouding.setActies(
                objectFactory.createHandelingVoltrekkingHuwelijkInNederlandActies(
                        containerHandelingActiesVoltrekkingHuwelijkInNederland));


        // Sluiting huwelijk nederland
        ActieRegistratieAanvangHuwelijkGeregistreerdPartnerschap registratieHuwelijk =
                objectFactory.createActieRegistratieAanvangHuwelijkGeregistreerdPartnerschap();
        registratieHuwelijk.setObjecttype(OBJECTTYPE_ACTIE);
        registratieHuwelijk.setCommunicatieID(nextCommunicatieId());
        registratieHuwelijk.setDatumAanvangGeldigheid(
                BijhoudingUtil.maakJaxbDatumAanvangGeldigheid(DATUM_AANVANG_GELDIGHEID));
        containerHandelingActiesVoltrekkingHuwelijkInNederland
                .setRegistratieAanvangHuwelijkGeregistreerdPartnerschap(registratieHuwelijk);

        // Bronnen
        BijhoudingUtil.setBronnen(registratieHuwelijk, documentCommunicatieID, nextCommunicatieId(),
                                  nextCommunicatieId());

        // Huwelijk
        final ObjecttypeHuwelijk objecttypeHuwelijkNLBijhouding =
                objectFactory.createObjecttypeHuwelijkNederlandBijhouding();
        registratieHuwelijk.setHuwelijk(objectFactory.createObjecttypeActieHuwelijk(objecttypeHuwelijkNLBijhouding));

        objecttypeHuwelijkNLBijhouding.setObjecttype(OBJECTTYPE_RELATIE);
        objecttypeHuwelijkNLBijhouding.setCommunicatieID(nextCommunicatieId());

        GroepHuwelijkGeregistreerdPartnerschapRelatie groepHuwelijkRelatieSluitingNL =
                objectFactory.createGroepHuwelijkGeregistreerdPartnerschapRelatie();
        objecttypeHuwelijkNLBijhouding.getRelatie().add(groepHuwelijkRelatieSluitingNL);

        // Datum aanvang
        final DatumMetOnzekerheid datumHuwelijk = new DatumMetOnzekerheid();
        datumHuwelijk.setValue(DATUM_AANVANG_GELDIGHEID);
        groepHuwelijkRelatieSluitingNL.setDatumAanvang(
                objectFactory.createGroepHuwelijkGeregistreerdPartnerschapRelatieDatumAanvang(datumHuwelijk));
        groepHuwelijkRelatieSluitingNL.setCommunicatieID(nextCommunicatieId());

        // Gemeente Aanvang Code
        GemeenteCode gemeenteAanvangCode = new GemeenteCode();
        gemeenteAanvangCode.setValue(GEMEENTECODE);
        groepHuwelijkRelatieSluitingNL.setGemeenteAanvangCode(
                objectFactory.createGroepHuwelijkGeregistreerdPartnerschapRelatieGemeenteAanvangCode(
                        gemeenteAanvangCode));

        // Betrokkenheden
        ContainerRelatieBetrokkenhedenHuwelijkPartnerschapBijhouding betrokkenheden =
                objectFactory.createContainerRelatieBetrokkenhedenHuwelijkPartnerschapBijhouding();
        objecttypeHuwelijkNLBijhouding.setBetrokkenheden(
                objectFactory.createObjecttypeRelatieBetrokkenheden(betrokkenheden));

        // Partner 1
        ObjecttypePartnerBijhouding partner1 = objectFactory.createObjecttypePartnerBijhouding();
        partner1.setObjecttype(OBJECTTYPE_BETROKKENHEID);
        partner1.setCommunicatieID(nextCommunicatieId());
        betrokkenheden.getErkennerOrInstemmerOrKind().add(partner1);
        PersoonPartnerBijhouding persoon1 = objectFactory.createPersoonPartnerBijhouding();
        partner1.setPersoon(objectFactory.createObjecttypeBetrokkenheidPersoon(persoon1));

        // Identificatienummers partner 1
        persoon1.setObjectSleutel(getValueFromValueMap(PARAMETER_BSN1, BSN1));
        persoon1.setObjecttype(OBJECTTYPE_PERSOON);
        persoon1.setCommunicatieID(nextCommunicatieId());

        // Partner 2
        ObjecttypePartnerBijhouding partner2 = objectFactory.createObjecttypePartnerBijhouding();
        partner2.setObjecttype(OBJECTTYPE_BETROKKENHEID);
        partner2.setCommunicatieID(nextCommunicatieId());
        betrokkenheden.getErkennerOrInstemmerOrKind().add(partner2);
        PersoonPartnerBijhouding persoon2 = objectFactory.createPersoonPartnerBijhouding();
        partner2.setPersoon(objectFactory.createObjecttypeBetrokkenheidPersoon(persoon2));

        // Identificatienummers partner 2
        persoon2.setObjectSleutel(getValueFromValueMap(PARAMETER_BSN2, BSN2));
        persoon2.setObjecttype(OBJECTTYPE_PERSOON);
        persoon2.setCommunicatieID(nextCommunicatieId());


        // Registratie aanschrijving
        ActieRegistratieNaamgebruik registratieAanschrijving =
                objectFactory.createActieRegistratieNaamgebruikBijIngeschreveneBijhouding();
        containerHandelingActiesVoltrekkingHuwelijkInNederland.getRegistratieNaamgebruik().add(registratieAanschrijving);
        registratieAanschrijving.setObjecttype(OBJECTTYPE_ACTIE);
        registratieAanschrijving.setCommunicatieID(nextCommunicatieId());
        registratieAanschrijving.setDatumAanvangGeldigheid(
                BijhoudingUtil.maakJaxbDatumAanvangGeldigheid(DATUM_AANVANG_GELDIGHEID));

        // Bronnen
        BijhoudingUtil.setBronnen(registratieAanschrijving, documentCommunicatieID, nextCommunicatieId(),
                                  nextCommunicatieId());



        // Persoon
	    final ObjecttypePersoon persoon = objectFactory.createObjecttypePersoon();
	    registratieAanschrijving.setPersoon(objectFactory.createObjecttypeActiePersoon(persoon));
        persoon.setObjecttype(OBJECTTYPE_PERSOON);
        persoon.setCommunicatieID(nextCommunicatieId());

        // Technische sleutel
        persoon.setObjectSleutel(getValueFromValueMap(PARAMETER_BSN_NAAMGEBRUIK, BSN_NAAMGEBRUIK));

        // Aanschrijving
        final GroepPersoonNaamgebruik aanschrijving = objectFactory.createGroepPersoonNaamgebruik();
        persoon.getNaamgebruik().add(aanschrijving);
        aanschrijving.setCommunicatieID(nextCommunicatieId());


        // Wijze van gebruik
        final NaamgebruikCode wijzeGebruikGeslachtsnaamCode =
                objectFactory.createNaamgebruikCode();
        wijzeGebruikGeslachtsnaamCode.setValue(NaamgebruikCodeS.P);
        aanschrijving.setCode(objectFactory.createGroepPersoonNaamgebruikCode(wijzeGebruikGeslachtsnaamCode));

        // Indicatie algoritmisch afgeleid
        final JaNee indicatieAlgoritmischAfgeleid = objectFactory.createJaNee();
        indicatieAlgoritmischAfgeleid.setValue(JaNeeS.J);
        aanschrijving.setIndicatieAfgeleid(
                objectFactory.createGroepPersoonNaamgebruikIndicatieAfgeleid(indicatieAlgoritmischAfgeleid));

        return bhgHuwelijkGeregisteerdPartnerschap;
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

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#getParameterLijst()
     */
    @Override
    protected List<String> getParameterLijst() {
        return Arrays.asList(PARAMETER_PREVALIDATIE, PARAMETER_BSN1, PARAMETER_BSN2,
                             PARAMETER_BSN_NAAMGEBRUIK, PARAMETER_REFERENTIENUMMER);
    }
    @Override
    public BERICHT getBericht() {
        return BERICHT.BIJHOUDING_HUWELIJK;
    }
}
