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
import nl.bzk.brp.brp0200.BijhoudingBevragingBepaalKandidaatVaderVerzoek;
import nl.bzk.brp.brp0200.DatumMetOnzekerheid;
import nl.bzk.brp.brp0200.GroepBerichtZoekcriteriaPersoon;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bevraging.KandidaatVaderCommand;
import nl.bzk.brp.testclient.util.DatumUtil;


/** De Class VraagKandidaatVaderAanroeper. */
public class VraagKandidaatVaderAanroeper extends AbstractBevragingAanroeper {

    // NB: Let op dat je bij het toevoegen van parameters, deze ook toevoegt in getParameterLijst()

    /**
     * Paramater voor BSN.
     */
    public static final  String PARAMETER_BSN    = "BSN";
    private static final String BSN              = "100000034";

    private final ObjectFactory objectFactory = getObjectFactory();

    /**
     * Instantieert een nieuwe vraag kandidaat vader aanroeper.
     *
     *
     * @param eigenschappen de eigenschappen
     * @param bevragingPortType bevraging port type
     * @param parameterMap de parameter map  @throws Exception de exception
     * @throws Exception exception
     */
    public VraagKandidaatVaderAanroeper(final Eigenschappen eigenschappen,
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
        final BijhoudingBevragingBepaalKandidaatVaderVerzoek vraag = creeerBevragingKandidaatVader();
        final KandidaatVaderCommand command = new KandidaatVaderCommand(vraag);
        roepAan(command);
    }

    /**
     * Creeer bevraging kandidaat vader.
     *
     * @return de bevraging kandidaat vader
     */
    private BijhoudingBevragingBepaalKandidaatVaderVerzoek creeerBevragingKandidaatVader() {
        BijhoudingBevragingBepaalKandidaatVaderVerzoek bevraging = new BijhoudingBevragingBepaalKandidaatVaderVerzoek();

        // Stuurgegevens-element
        zetStuurgegevens(bevraging);

        // Vraag-element toevoegen
        final GroepBerichtZoekcriteriaPersoon zoekcriteriaPersoon =
                objectFactory.createGroepBerichtZoekcriteriaPersoon();
        bevraging.setZoekcriteriaPersoon(objectFactory.createObjecttypeBerichtZoekcriteriaPersoon(zoekcriteriaPersoon));

        // Zoek criteria element toevoegen
        zoekcriteriaPersoon.setCommunicatieID("zoekcriteriaComId");

        // BSN toevoegen aan zoekcriteria
        zoekcriteriaPersoon.getRest().add(bouwBsnElement(getValueFromValueMap(PARAMETER_BSN, BSN)));

        // Geboorte datum kind toevoegen aan zoekcriteria
        final DatumMetOnzekerheid datumKind = new DatumMetOnzekerheid();
        datumKind.setValue(DatumUtil.vandaagXmlString());
        final JAXBElement<DatumMetOnzekerheid> geboortedatumKindElement =
            objectFactory.createGroepBerichtZoekcriteriaPersoonGeboortedatumKind(datumKind);
        zoekcriteriaPersoon.getRest().add(geboortedatumKindElement);

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
        return BERICHT.VRAAG_KANDIDAAT_VADER;
    }
}
