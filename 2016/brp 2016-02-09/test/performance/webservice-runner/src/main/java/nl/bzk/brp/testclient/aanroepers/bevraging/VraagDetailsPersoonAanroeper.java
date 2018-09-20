/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bevraging;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import nl.bzk.brp.bijhouding.service.BhgBevraging;
import nl.bzk.brp.brp0200.BijhoudingBevragingGeefDetailsPersoonVerzoek;
import nl.bzk.brp.brp0200.GroepBerichtZoekcriteriaPersoon;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bevraging.DetailsPersoonCommand;


/** De Class VraagDetailsPersoonAanroeper. */
public class VraagDetailsPersoonAanroeper extends AbstractBevragingAanroeper {

    /**
     * Parameter voor BSN.
     */
    public static final  String PARAMETER_BSN    = "BSN";
    private static final String BSN              = "100000009";

    private final ObjectFactory objectFactory = getObjectFactory();

    /**
     * Instantieert een nieuwe vraag details persoon aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param bevragingPortType bevraging port type
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    public VraagDetailsPersoonAanroeper(final Eigenschappen eigenschappen,
                                        final BhgBevraging bevragingPortType,
                                        final Map<String, String> parameterMap) throws Exception
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
        final BijhoudingBevragingGeefDetailsPersoonVerzoek vraag = creeerBevragingDetailsPersoonVerzoek();
        final DetailsPersoonCommand command = new DetailsPersoonCommand(vraag);
        roepAan(command);
    }

    /**
     * Creeer bevraging details persoon.
     *
     * @return de bevraging details persoon
     */
    private BijhoudingBevragingGeefDetailsPersoonVerzoek creeerBevragingDetailsPersoonVerzoek() {
        final BijhoudingBevragingGeefDetailsPersoonVerzoek bevraging = new BijhoudingBevragingGeefDetailsPersoonVerzoek();

        // Stuurgegevens-element
        zetStuurgegevens(bevraging);

        // Vraag-element toevoegen
        final GroepBerichtZoekcriteriaPersoon groepBerichtZoekcriteriaPersoon = new GroepBerichtZoekcriteriaPersoon();
        groepBerichtZoekcriteriaPersoon.setCommunicatieID("zoekCriteriaPersoonComId");
        groepBerichtZoekcriteriaPersoon.getRest().add(bouwBsnElement(getValueFromValueMap(PARAMETER_BSN, BSN)));

        final JAXBElement<GroepBerichtZoekcriteriaPersoon> zoekCriteriaPersoon = objectFactory
                .createObjecttypeBerichtZoekcriteriaPersoon(groepBerichtZoekcriteriaPersoon);
        bevraging.setZoekcriteriaPersoon(zoekCriteriaPersoon);

        return bevraging;
    }

    @Override
    protected List<String> getParameterLijst() {
        return Arrays.asList(PARAMETER_BSN);
    }
    @Override
    public BERICHT getBericht() {
        return BERICHT.VRAAG_DETAILS;
    }
}
