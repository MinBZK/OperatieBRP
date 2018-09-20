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
 * Implementatie die de bepaling doet op basis van de volgende regels:
 * <p/>
 * <p/>
 * 0. bepaal de huidige afgeleid administratief aan de hand van de gegeven administratieve handeling.
 * <p/>
 * <p/>
 * 1. Indien er 1 administratieve handeling is, evalueer de expressie tegen de {@link nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView} en bepaal
 * de positie ten opzichte van de populatie.
 * <p/>
 * <p/>
 * 2. Als er meer administratieve handelingen zijn, neem de vorige administratieve handeling (tov de bepaalde bij stap 0). Evalueer twee {@link
 * nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView}s met tijdstip van de administratieve handelingen tegen de expressie. De uitkomst van de
 * evaluaties bepalen de positie ten opzichte van de populatie.
 */
@Component("populatieTransitieBepaler")
public class PopulatieTransitieBepaler extends AbstractBepaler implements PopulatieBepaler {

    private static final Logger LOGGER                                                 = LoggerFactory.getLogger();
    private static final String POPULATIEBEPERKING_EVALUEERT_NAAR_WAARDE_NULL_ONBEKEND =
        "Populatiebeperking evalueert naar waarde NULL (onbekend) voor leveringsautorisatie: '{}' en persoon met id: {}";

    @Regels(Regel.VR00109)
    @Override
    public final Populatie bepaalInUitPopulatie(final PersoonHisVolledig persoon,
        final AdministratieveHandelingModel administratieveHandeling, final Expressie populatiebeperking,
        final Leveringsautorisatie leveringsautorisatie)
    {
        final Populatie resultaat;

        final Set<Long> eventueelToekomstigeActieIds =
            getToekomstigeActieService().geefToekomstigeActieIds(administratieveHandeling, persoon);
        final Boolean huidigExpressieResultaat =
            getExpressieResultaat(persoon, populatiebeperking, leveringsautorisatie, eventueelToekomstigeActieIds);

        final boolean isNieuwPersoon = persoon.getPersoonAfgeleidAdministratiefHistorie().getAantal() == 1;
        if (isNieuwPersoon) {
            resultaat = bepaalPopulatieVoorNieuwPersoon(huidigExpressieResultaat);
        } else {
            final Set<Long> eventueelToekomstigeActieIdsPlusHuidigeHandeling =
                getToekomstigeActieService().geefToekomstigeActieIdsPlusHuidigeHandeling(administratieveHandeling,
                    persoon);
            final Boolean vorigExpressieResultaat =
                getExpressieResultaat(persoon, populatiebeperking, leveringsautorisatie,
                    eventueelToekomstigeActieIdsPlusHuidigeHandeling);
            resultaat = bepaalPopulatieVoorBestaandPersoon(huidigExpressieResultaat, vorigExpressieResultaat);
        }

        return resultaat;
    }

    private Boolean getExpressieResultaat(final PersoonHisVolledig persoon, final Expressie populatiebeperking,
        final Leveringsautorisatie la, final Set<Long> actieIds)
    {
        Boolean resultaat = evalueerExpressie(populatiebeperking, persoon, actieIds);
        if (resultaat == null) {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_VR00109,
                POPULATIEBEPERKING_EVALUEERT_NAAR_WAARDE_NULL_ONBEKEND, la.getID(),
                persoon.getID());
            resultaat = false;
        }
        return resultaat;
    }

    /**
     * Bepaalt voor een nieuw persoon (zonder vorige afgeleidAdministratief) de populatie.
     *
     * @param huidigExpressieResultaat Boolean die bepaalt of de persoon nu binnen de expressie valt.
     * @return De populatie.
     */
    @Regels({ Regel.VR00056, Regel.VR00057 })
    private Populatie bepaalPopulatieVoorNieuwPersoon(final boolean huidigExpressieResultaat) {
        final Populatie resultaat;
        if (huidigExpressieResultaat) {
            resultaat = Populatie.BETREEDT;
        } else {
            resultaat = Populatie.BUITEN;
        }
        return resultaat;
    }

    /**
     * Bepaalt voor een bestaand persoon de populatie.
     *
     * @param huidigExpressieResultaat Boolean die bepaalt of de persoon nu binnen de expressie valt.
     * @param vorigExpressieResultaat  Boolean die bepaalt of de persoon vorige keer binnen de expressie viel.
     * @return De populatie.
     */
    @Regels({ Regel.VR00056, Regel.VR00057 })
    private Populatie bepaalPopulatieVoorBestaandPersoon(final boolean huidigExpressieResultaat,
        final boolean vorigExpressieResultaat)
    {
        final Populatie resultaat;
        if (huidigExpressieResultaat && vorigExpressieResultaat) {
            resultaat = Populatie.BINNEN;
        } else if (!vorigExpressieResultaat && huidigExpressieResultaat) {
            resultaat = Populatie.BETREEDT;
        } else if (vorigExpressieResultaat && !huidigExpressieResultaat) {
            resultaat = Populatie.VERLAAT;
        } else {
            resultaat = Populatie.BUITEN;
        }
        return resultaat;
    }

}
