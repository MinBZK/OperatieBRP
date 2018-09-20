/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bijhouding;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.bijhouding.service.BhgOverlijden;
import nl.bzk.brp.brp0200.ActieRegistratieOverlijdenNederlandBijhouding;
import nl.bzk.brp.brp0200.ContainerHandelingActiesOverlijdenInNederlandBijhouding;
import nl.bzk.brp.brp0200.DatumMetOnzekerheid;
import nl.bzk.brp.brp0200.GemeenteCode;
import nl.bzk.brp.brp0200.GroepPersoonOverlijdenNederlandBijhouding;
import nl.bzk.brp.brp0200.HandelingOverlijdenInNederlandBijhouding;
import nl.bzk.brp.brp0200.NaamEnumeratiewaarde;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.ObjecttypeBerichtBijhouding;
import nl.bzk.brp.brp0200.ObjecttypePersoonOverlijdenNederland;
import nl.bzk.brp.brp0200.Ontleningstoelichting;
import nl.bzk.brp.brp0200.OverlijdenRegistreerOverlijdenBijhouding;
import nl.bzk.brp.brp0200.PartijCode;
import nl.bzk.brp.testclient.aanroepers.AbstractAanroeper;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bijhouding.OverlijdenCommand;
import nl.bzk.brp.testclient.util.DatumUtil;


/**
 * Bijhoudings aanroeper die een registratie overlijden bijhoudings bericht kan creeeren en via de test client
 * afvuurt.
 */
public class OverlijdenAanroeper extends AbstractAanroeper {

    private static final String BSN                = "100000009";
    private static final String GEMEENTECODE       = "0599";
    private static final String WOONPLAATSNAAM     = "Rotterdam";
    private static final String AANVANG_GELDIGHEID = "2010-07-19";

    // NB: Let op dat je bij het toevoegen van parameters, deze ook toevoegt in getParameterLijst()
    /** Parameter voor referentienummer van overleden persoon bericht. */
    public static final String PARAMETER_REFERENTIENUMMER         = "REFERENTIENUMMER";
    /** Parameter voor datum aanvang van overleden persoon actie. */
    public static final String PARAMETER_DATUM_AANVANG_GELDIGHEID = "DATUM_AANVANG_GELDIGHEID";
    /** Parameter voor BSN van overleden persoon. */
    public static final String PARAMETER_BSN                      = "BSN";
    /** Parameter voor datum overlijden van overleden persoon. */
    public static final String PARAMETER_DATUM_OVERLIJDEN         = "DATUM_OVERLIJDEN";
    /** Parameter voor gemeentecode van gemeente van overlijden. */
    public static final String PARAMETER_GEMEENTECODE             = "GEMEENTE";
    /** Parameter voor woonplaatsnaam van plaats van overlijden. */
    public static final String PARAMETER_WOONPLAATSNAAM           = "WOONPLAATSNAAM";
    /** Parameter voor landcode van land van overlijden. */
    public static final String PARAMETER_LANDCODE                 = "LANDCODE";

    private final static String DOCUMENT_SOORT_NAAM               = "Overlijdensakte";

    private final ObjectFactory objectFactory = getObjectFactory();

    public OverlijdenAanroeper(final Eigenschappen eigenschappen) {
        super(eigenschappen);
    }

    /**
     * Instantieert een nieuwe overlijden aanroeper.
     *
     *
     * @param eigenschappen de eigenschappen
     * @param bijhoudingPortType
     *@param parameterMap de parameter map  @throws Exception de exception
     */
    public OverlijdenAanroeper(final Eigenschappen eigenschappen, final BhgOverlijden bijhoudingPortType,
                               final Map<String, String> parameterMap)
        throws Exception
    {
        super(eigenschappen, bijhoudingPortType, parameterMap);
    }

    @Override
    public void fire() {
        OverlijdenRegistreerOverlijdenBijhouding bijhouding = creeerOverlijdenRegistratieOverlijdenBijhouding();
        OverlijdenCommand command = new OverlijdenCommand(bijhouding);
        roepAan(command);
    }

    /**
     * Creeer overlijden bijhouding.
     *
     * @return de overlijden bijhouding
     */
    public OverlijdenRegistreerOverlijdenBijhouding creeerOverlijdenRegistratieOverlijdenBijhouding() {
        final PartijCode partijCode = objectFactory.createPartijCode();
        partijCode.setValue(PARTIJ_CODE);

        OverlijdenRegistreerOverlijdenBijhouding overlijden =
            objectFactory.createOverlijdenRegistreerOverlijdenBijhouding();

        // Stuurgegevens en parameters.
        Boolean prevalidatie = getValueFromValueMap(PARAMETER_PREVALIDATIE, "").equalsIgnoreCase("J");
        zetStuurgegevensEnParameters(overlijden, prevalidatie);

        //Administratieve handeling
        HandelingOverlijdenInNederlandBijhouding handelingRegistratieOverlijdenNL =
            objectFactory.createHandelingOverlijdenInNederlandBijhouding();

        overlijden.setOverlijdenInNederland(
            objectFactory.createObjecttypeBerichtOverlijdenInNederland(handelingRegistratieOverlijdenNL));
        handelingRegistratieOverlijdenNL.setObjecttype(OBJECTTYPE_ADMINISTRATIEVE_HANDELING);
        handelingRegistratieOverlijdenNL.setCommunicatieID(nextCommunicatieId());

        String documentCommunicatieID =
                setBijgehoudenDocumenten(handelingRegistratieOverlijdenNL, partijCode, DOCUMENT_SOORT_NAAM);

        handelingRegistratieOverlijdenNL.setPartijCode(
                objectFactory.createObjecttypeAdministratieveHandelingPartijCode(partijCode));

        // Zet toelichting
        final Ontleningstoelichting ontleningstoelichting = new Ontleningstoelichting();
        ontleningstoelichting.setValue("Overlijden");
        handelingRegistratieOverlijdenNL.setToelichtingOntlening(
                objectFactory.createObjecttypeAdministratieveHandelingToelichtingOntlening(ontleningstoelichting));

        // Acties
        ContainerHandelingActiesOverlijdenInNederlandBijhouding acties =
            objectFactory.createContainerHandelingActiesOverlijdenInNederlandBijhouding();
        handelingRegistratieOverlijdenNL.setActies(
            objectFactory.createHandelingOverlijdenInNederlandActies(acties));

        //Registratie overlijden
        ActieRegistratieOverlijdenNederlandBijhouding registratieOverlijden =
            objectFactory.createActieRegistratieOverlijdenNederlandBijhouding();
        acties.setRegistratieOverlijden(registratieOverlijden);
        registratieOverlijden.setObjecttype(OBJECTTYPE_ACTIE);
        registratieOverlijden.setCommunicatieID(nextCommunicatieId());
        BijhoudingUtil.setBronnen(registratieOverlijden, documentCommunicatieID, nextCommunicatieId(),
                                  nextCommunicatieId());

        // Aanvang geldigheid
        registratieOverlijden.setDatumAanvangGeldigheid(BijhoudingUtil.maakJaxbDatumAanvangGeldigheid(
            getValueFromValueMap(PARAMETER_DATUM_AANVANG_GELDIGHEID, AANVANG_GELDIGHEID)
        ));

        // Persoon
        ObjecttypePersoonOverlijdenNederland persoon = objectFactory.createObjecttypePersoonOverlijdenNederland();
        persoon.setObjecttype(OBJECTTYPE_PERSOON);
        persoon.setCommunicatieID(nextCommunicatieId());
        registratieOverlijden.setPersoon(objectFactory.createObjecttypeActiePersoon(persoon));

        // Identificatienummers
        String bsn = getValueFromValueMap(PARAMETER_BSN, BSN);
        persoon.setObjectSleutel(bsn);

        // Overlijden
        GroepPersoonOverlijdenNederlandBijhouding overlijdenGroep =
            objectFactory.createGroepPersoonOverlijdenNederlandBijhouding();
        persoon.getOverlijden().add(overlijdenGroep);
        overlijdenGroep.setCommunicatieID(nextCommunicatieId());

        // Datum
        final DatumMetOnzekerheid datumOverlijden = new DatumMetOnzekerheid();
        datumOverlijden.setValue(DatumUtil.vandaagXmlString());
        overlijdenGroep.setDatum(objectFactory.createGroepPersoonOverlijdenDatum(datumOverlijden));

        // Gemeentecode
        GemeenteCode gemeentecode = objectFactory.createGemeenteCode();
        gemeentecode.setValue(getValueFromValueMap(PARAMETER_GEMEENTECODE, GEMEENTECODE));
        overlijdenGroep.setGemeenteCode(objectFactory.createGroepPersoonOverlijdenGemeenteCode(gemeentecode));

        // Woonplaatsnaam
        NaamEnumeratiewaarde woonplaatsNaam = new NaamEnumeratiewaarde();
        woonplaatsNaam.setValue(getValueFromValueMap(PARAMETER_WOONPLAATSNAAM, WOONPLAATSNAAM));
        overlijdenGroep.setWoonplaatsnaam(objectFactory.createGroepPersoonOverlijdenWoonplaatsnaam(woonplaatsNaam));

        return overlijden;
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

    @Override
    protected List<String> getParameterLijst() {
        return Arrays.asList(PARAMETER_REFERENTIENUMMER, PARAMETER_PREVALIDATIE, PARAMETER_BSN,
                             PARAMETER_DATUM_AANVANG_GELDIGHEID, PARAMETER_DATUM_OVERLIJDEN, PARAMETER_GEMEENTECODE,
                             PARAMETER_WOONPLAATSNAAM, PARAMETER_LANDCODE);
    }

    @Override
    public BERICHT getBericht() {
        return BERICHT.BIJHOUDING_OVERLIJDEN;
    }
}
