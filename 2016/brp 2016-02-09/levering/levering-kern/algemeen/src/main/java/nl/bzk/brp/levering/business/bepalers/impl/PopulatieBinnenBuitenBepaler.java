/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import java.util.Set;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.business.bepalers.PopulatieBepaler;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.stereotype.Component;


/**
 * Implementatie die de bepaling doet op basis van de volgende regel: - Valt een persoon nu binnen de populatie.
 */
@Component("populatieBinnenBuitenBepaler")
public class PopulatieBinnenBuitenBepaler extends AbstractBepaler implements PopulatieBepaler {

    private static final Logger LOGGER                                                 = LoggerFactory.getLogger();
    private static final String POPULATIEBEPERKING_EVALUEERT_NAAR_WAARDE_NULL_ONBEKEND =
            "Populatiebeperking evalueert naar waarde NULL (onbekend) voor leveringsautorisatie: '{}' en persoon met id: {}";

    @Regels(Regel.VR00109)
    @Override
    public final Populatie bepaalInUitPopulatie(final PersoonHisVolledig persoon,
            final AdministratieveHandelingModel administratieveHandeling, final Expressie populatiebeperking,
            final Leveringsautorisatie leveringsautorisatie)
    {
        final Set<Long> eventueelToekomstigeActieIds =
                getToekomstigeActieService().geefToekomstigeActieIds(administratieveHandeling, persoon);

        Boolean expressieResultaat = evalueerExpressie(populatiebeperking, persoon, eventueelToekomstigeActieIds);
        if (expressieResultaat == null) {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_VR00109,
                    POPULATIEBEPERKING_EVALUEERT_NAAR_WAARDE_NULL_ONBEKEND, leveringsautorisatie.getID(),
                    persoon.getID());

            expressieResultaat = false;
        }

        return bepaalPopulatieVoorPersoon(expressieResultaat);
    }

    /**
     * @param expressieResultaat Boolean die bepaalt of de persoon nu binnen de expressie valt.
     * @return De populatie
     */
    private Populatie bepaalPopulatieVoorPersoon(final boolean expressieResultaat) {
        final Populatie resultaat;
        if (expressieResultaat) {
            resultaat = Populatie.BINNEN;
        } else {
            resultaat = Populatie.BUITEN;
        }
        return resultaat;
    }
}
