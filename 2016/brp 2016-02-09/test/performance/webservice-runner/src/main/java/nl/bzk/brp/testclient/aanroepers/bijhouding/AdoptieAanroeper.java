/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bijhouding;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import nl.bzk.brp.bijhouding.service.BhgAfstamming;
import nl.bzk.brp.brp0200.ActieRegistratieOuder;
import nl.bzk.brp.brp0200.AfstammingRegistreerAdoptieBijhouding;
import nl.bzk.brp.brp0200.ContainerHandelingActiesAdoptieIngezeteneBijhouding;
import nl.bzk.brp.brp0200.ContainerRelatieBetrokkenheden;
import nl.bzk.brp.brp0200.DatumMetOnzekerheid;
import nl.bzk.brp.brp0200.HandelingAdoptieIngezeteneBijhouding;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.ObjecttypeBerichtBijhouding;
import nl.bzk.brp.brp0200.ObjecttypeBetrokkenheid;
import nl.bzk.brp.brp0200.ObjecttypeFamilierechtelijkeBetrekking;
import nl.bzk.brp.brp0200.ObjecttypeOuderWijzigingBijhouding;
import nl.bzk.brp.brp0200.ObjecttypePersoon;
import nl.bzk.brp.brp0200.ObjecttypePersoonAdoptieIngezeteneBijhouding;
import nl.bzk.brp.brp0200.Ontleningstoelichting;
import nl.bzk.brp.brp0200.PartijCode;
import nl.bzk.brp.testclient.aanroepers.AbstractAanroeper;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bijhouding.AdoptieCommand;
import nl.bzk.brp.testclient.util.DatumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bijhoudings aanroeper die een registratie adoptie bijhoudings bericht kan creeeren en via de test client
 * afvuurt.
 */
public class AdoptieAanroeper extends AbstractAanroeper {

    /** De Constante LOG. */
    private static final Logger LOG                      = LoggerFactory.getLogger(AdoptieAanroeper.class);

    /** Parameter voor BSN van het kind dat geadopteerd wordt. */
    public static final  String     PARAMETER_BSN                  = "BSN";
    /** Betr identifier van fam betrekking waar kind al in deelneemt. */
    public static final  String     PARAMETER_ID_BETR           = "ID_BETR_KIND";
    /** Parameter voor BSN van de 'eerste' ouder van het kind dat geadopteerd wordt. */
    public static final  String     PARAMETER_BSN_OUDER1           = "BSN_OUDER1";
    /** Parameter voor BSN van de 'tweede' ouder van het kind dat geadopteerd wordt. */
    public static final  String     PARAMETER_BSN_OUDER2           = "BSN_OUDER2";
    /** Relatie identifier van fam betrekking waar kind in deelneemt. */
    public static final  String     PARAMETER_ID_RELATIE           = "ID_RELATIE";

    //Defaults
    private static final String     BSN                            = "100000011";
    private static final String     BSN_OUDER1                     = "100000010";
    private static final String     BSN_OUDER2                     = "100000009";
    private static final String     DOCUMENT_SOORT_NAAM            = "Nederlandse rechterlijke uitspraak";

    private final ObjectFactory objectFactory = getObjectFactory();

    /**
     * Instantieert een nieuwe Adoptie aanroeper.
     *
     * @param eigenschappen the eigenschappen
     */
    public AdoptieAanroeper(final Eigenschappen eigenschappen) {
        super(eigenschappen);
    }

    /**
     * Instantieert een nieuwe bijhouding aanroeper.
     *
     *
     * @param eigenschappen de eigenschappen
     * @param bijhoudingPortType the bijhouding port type
     * @param parameterMap de parameter map
     * @throws Exception exception
     */
    public AdoptieAanroeper(final Eigenschappen eigenschappen,
                            final BhgAfstamming bijhoudingPortType,
                            final Map<String, String> parameterMap)
        throws Exception
    {
        super(eigenschappen, bijhoudingPortType, parameterMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fire() {
        final AfstammingRegistreerAdoptieBijhouding bijhouding = creeerAfstammingRegistreerAdoptieBijhouding();
        final AdoptieCommand command = new AdoptieCommand(bijhouding);
        roepAan(command);
    }

    /**
     * Creeert het bijhoudings bericht.
     *
     * @return het bijhoudings bericht.
     */
    public AfstammingRegistreerAdoptieBijhouding creeerAfstammingRegistreerAdoptieBijhouding() {
        final PartijCode partijCode = objectFactory.createPartijCode();
        partijCode.setValue(PARTIJ_CODE);

        AfstammingRegistreerAdoptieBijhouding bijhouding = objectFactory.createAfstammingRegistreerAdoptieBijhouding();

        // Stuurgegevens en parameters.
        Boolean prevalidatie = getValueFromValueMap(PARAMETER_PREVALIDATIE, "").equalsIgnoreCase("J");
        zetStuurgegevensEnParameters(bijhouding, prevalidatie);

        // Administratieve handeling
        HandelingAdoptieIngezeteneBijhouding handelingRegistratieAdoptieBijhouding =
                objectFactory.createHandelingAdoptieIngezeteneBijhouding();
        handelingRegistratieAdoptieBijhouding.setObjecttype(OBJECTTYPE_ADMINISTRATIEVE_HANDELING);
        handelingRegistratieAdoptieBijhouding.setCommunicatieID(nextCommunicatieId());
        bijhouding.setAdoptieIngezetene(
                objectFactory.createObjecttypeBerichtAdoptieIngezetene(handelingRegistratieAdoptieBijhouding));

        handelingRegistratieAdoptieBijhouding.setPartijCode(
                objectFactory.createObjecttypeAdministratieveHandelingPartijCode(partijCode));

        // Zet toelichting
        final Ontleningstoelichting ontleningstoelichting = new Ontleningstoelichting();
        ontleningstoelichting.setValue("Adoptie");
        handelingRegistratieAdoptieBijhouding.setToelichtingOntlening(
                objectFactory.createObjecttypeAdministratieveHandelingToelichtingOntlening(ontleningstoelichting));

        // Bijgehouden documenten
        final String documentCommunicatieId = setBijgehoudenDocumenten(
            handelingRegistratieAdoptieBijhouding, partijCode, DOCUMENT_SOORT_NAAM);

        // Acties-element
        final ContainerHandelingActiesAdoptieIngezeteneBijhouding
                containerHandelingActiesAdoptieIngezeteneBijhouding =
                objectFactory.createContainerHandelingActiesAdoptieIngezeteneBijhouding();
        handelingRegistratieAdoptieBijhouding.setActies(
                objectFactory.createHandelingAdoptieIngezeteneActies(
                        containerHandelingActiesAdoptieIngezeteneBijhouding)
        );

        // Actie registratie adoptie
        final ActieRegistratieOuder actieRegistratieOuder = new ActieRegistratieOuder();
        containerHandelingActiesAdoptieIngezeteneBijhouding.setRegistratieOuder(actieRegistratieOuder);

        DatumMetOnzekerheid datumAanvanggeldigheid = new DatumMetOnzekerheid();
        datumAanvanggeldigheid.setValue(DatumUtil.vandaagXmlString());


        actieRegistratieOuder.setDatumAanvangGeldigheid(
                objectFactory.createDatumAanvangGeldigheid(datumAanvanggeldigheid));
        actieRegistratieOuder.setObjecttype(OBJECTTYPE_ACTIE);
        actieRegistratieOuder.setCommunicatieID(nextCommunicatieId());

        BijhoudingUtil.setBronnen(actieRegistratieOuder, documentCommunicatieId, nextCommunicatieId(),
                                  nextCommunicatieId());

        // Familie rechtelijke betrekking
        ObjecttypeFamilierechtelijkeBetrekking familierechtelijkeBetrekkingAdoptie =
                objectFactory.createObjecttypeFamilierechtelijkeBetrekkingAdoptieIngezeteneBijhouding();
        actieRegistratieOuder.setFamilierechtelijkeBetrekking(
                objectFactory.createObjecttypeActieFamilierechtelijkeBetrekking(
                        familierechtelijkeBetrekkingAdoptie)
        );
        familierechtelijkeBetrekkingAdoptie.setObjecttype(OBJECTTYPE_RELATIE);
        familierechtelijkeBetrekkingAdoptie.setCommunicatieID(nextCommunicatieId());

        // Betrokkenheden
        final ContainerRelatieBetrokkenheden betrokkenheden = new ContainerRelatieBetrokkenheden();
        familierechtelijkeBetrekkingAdoptie
            .setBetrokkenheden(objectFactory.createObjecttypeRelatieBetrokkenheden(betrokkenheden));
        familierechtelijkeBetrekkingAdoptie.setObjectSleutel(getValueFromValueMap(PARAMETER_ID_RELATIE, BSN));

        // Kind + betrokkenheid
        ObjecttypeBetrokkenheid kindBetr = objectFactory.createObjecttypeKindAdoptieIngezeteneBijhouding();
        betrokkenheden.getErkennerOrInstemmerOrKind().add(kindBetr);
        kindBetr.setObjecttype(OBJECTTYPE_BETROKKENHEID);
        kindBetr.setCommunicatieID(nextCommunicatieId());
        kindBetr.setObjectSleutel(getValueFromValueMap(PARAMETER_ID_BETR, "kindbetr1"));

        final ObjecttypePersoonAdoptieIngezeteneBijhouding kind =
                objectFactory.createObjecttypePersoonAdoptieIngezeteneBijhouding();
        kind.setObjecttype(OBJECTTYPE_PERSOON);
        kind.setCommunicatieID(nextCommunicatieId());
        final JAXBElement<ObjecttypePersoon> persoonElement = objectFactory.createObjecttypeBetrokkenheidPersoon(kind);
        kindBetr.setPersoon(persoonElement);
        kind.setObjectSleutel(getValueFromValueMap(PARAMETER_BSN, BSN));

        // Ouder1 + betrokkenheid
        voegOuderBetrokkenheidToe(betrokkenheden, PARAMETER_BSN_OUDER1, BSN_OUDER1);

        // Ouder2 + betrokkenheid
        voegOuderBetrokkenheidToe(betrokkenheden, PARAMETER_BSN_OUDER2, BSN_OUDER2);

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

    /**
     * Voegt een ouder betrokkenheid toe.
     *
     * @param betrokkenheden de betrokkenheden waar de betrokkenheid aan toegevoegd moet worden.
     * @param parameterBsnOuder de parameter naam voor de bsn van de ouder.
     * @param bsnOuder de standaard waarde voor de bsn van de ouder indien parameter niet opgegeven is.
     */
    private void voegOuderBetrokkenheidToe(final ContainerRelatieBetrokkenheden betrokkenheden,
        final String parameterBsnOuder, final String bsnOuder)
    {
        final ObjecttypeOuderWijzigingBijhouding ouderBetrokkenheid =
            objectFactory.createObjecttypeOuderWijzigingBijhouding();
        ouderBetrokkenheid.setCommunicatieID(nextCommunicatieId());
        ouderBetrokkenheid.setObjecttype(OBJECTTYPE_BETROKKENHEID);
        betrokkenheden.getErkennerOrInstemmerOrKind().add(ouderBetrokkenheid);
        // Persoon
	    final ObjecttypePersoon ouder = objectFactory.createObjecttypePersoon();
        ouder.setCommunicatieID(nextCommunicatieId());
        ouder.setObjecttype(OBJECTTYPE_PERSOON);
        ouder.setObjectSleutel(getValueFromValueMap(parameterBsnOuder, bsnOuder));
        ouderBetrokkenheid.setPersoon(objectFactory.createObjecttypeBetrokkenheidPersoon(ouder));
    }

    @Override
    protected List<String> getParameterLijst() {
        return Arrays.asList(PARAMETER_PREVALIDATIE, PARAMETER_BSN, PARAMETER_ID_BETR,
                             PARAMETER_BSN_OUDER1, PARAMETER_BSN_OUDER2, PARAMETER_ID_RELATIE);
    }
    @Override
    public BERICHT getBericht() {
        return BERICHT.BIJHOUDING_ADOPTIE;
    }
}
