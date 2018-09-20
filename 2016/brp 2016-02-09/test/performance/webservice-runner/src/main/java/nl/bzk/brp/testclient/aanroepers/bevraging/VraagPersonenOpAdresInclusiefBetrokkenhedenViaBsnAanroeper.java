/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bevraging;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.bijhouding.service.BhgBevraging;
import nl.bzk.brp.brp0200.BevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek;
import nl.bzk.brp.brp0200.GroepBerichtZoekcriteriaPersoonBijhoudingBevragingPersonenOpAdresMetBetrokkenheden;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bevraging.PersonenOpAdresCommand;


/** De Class VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper. */
public class VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper extends AbstractBevragingAanroeper {

    // NB: Let op dat je bij het toeveogen van parameters, deze ook toevoegt in getParameterLijst()

    /**
     * Parameter voor BSN.
     */
    public static final  String PARAMETER_BSN    = "BSN";
    private static final String BSN              = "100000344";

    private final ObjectFactory objectFactory = getObjectFactory();

    /**
     * Instantieert een nieuwe vraag personen op adres inclusief betrokkenheden via bsn aanroeper.
     *
     *
     * @param eigenschappen de eigenschappen
     * @param bevragingPortType bevraging port type
     * @param parameterMap de parameter map
     * @throws Exception exception
     */
    public VraagPersonenOpAdresInclusiefBetrokkenhedenViaBsnAanroeper(final Eigenschappen eigenschappen,
                                                                      final BhgBevraging bevragingPortType,
                                                                      final Map<String, String> parameterMap)
        throws Exception
    {
        super(eigenschappen, bevragingPortType, parameterMap);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#fire()
     */
    @Override
    public void fire() {
        final BevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek vraag =
                creeerBevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek();
        final PersonenOpAdresCommand command = new PersonenOpAdresCommand(vraag);
        roepAan(command);
    }

    /**
     * Creeer bevraging personen op adres inclusief betrokkenheden.
     *
     * @return de bevraging personen op adres inclusief betrokkenheden
     */
    private BevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek
    creeerBevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek()
    {
        final BevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek bevraging =
            new BevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek();

        // Stuurgegevens-element
        zetStuurgegevens(bevraging);

        // Vraag-element toevoegen
        final GroepBerichtZoekcriteriaPersoonBijhoudingBevragingPersonenOpAdresMetBetrokkenheden zoekcriteriaPersoon =
                objectFactory.createGroepBerichtZoekcriteriaPersoonBijhoudingBevragingPersonenOpAdresMetBetrokkenheden();
        bevraging.setZoekcriteriaPersoon(objectFactory.createObjecttypeBerichtZoekcriteriaPersoon(zoekcriteriaPersoon));

        zoekcriteriaPersoon.setCommunicatieID("zoekcriteriaComId");

        // BSN toevoegen aan zoekcriteria
        zoekcriteriaPersoon.getRest().add(bouwBsnElement(getValueFromValueMap(PARAMETER_BSN, BSN)));

        return bevraging;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.aanroepers.AbstractAanroeper#getParameterLijst()
     */
    @Override
    protected List<String> getParameterLijst() {
        return Arrays.asList(PARAMETER_BSN);
    }

    @Override
    public BERICHT getBericht() {
        return BERICHT.VRAAG_PERSONEN_ADRES_VIA_BSN;
    }
}
