/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.populatiebepaling;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import org.springframework.stereotype.Component;


/**
 * Implementatie die de bepaling doet op basis van de volgende regels:
 * <p>
 * <p>
 * 0. bepaal de huidige afgeleid administratief aan de hand van de gegeven administratieve handeling.
 * <p>
 * <p>
 * 1. Indien er 1 administratieve handeling is, evalueer de expressie tegen de {@link Persoonslijst} en bepaal de positie ten opzichte van de populatie.
 * <p>
 * <p>
 * 2. Als er meer administratieve handelingen zijn, neem de vorige administratieve handeling (tov de bepaalde bij stap 0). Evalueer twee {@link
 * Persoonslijst}s met tijdstip van de administratieve handelingen tegen de expressie. De uitkomst van de evaluaties bepalen de positie ten opzichte van de
 * populatie.
 */
@Component("PopulatieTransitieBepaler")
final class PopulatieTransitieBepalerImpl implements PopulatieBepaler {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String POPULATIEBEPERKING_EVALUEERT_NAAR_WAARDE_NULL_ONBEKEND =
            "Populatiebeperking evalueert naar waarde NULL (onbekend) voor leveringsautorisatie: '{}' en persoon met id: {}";

    @Inject
    private ExpressieService expressieService;

    private PopulatieTransitieBepalerImpl() {

    }

    @Bedrijfsregel(Regel.R1559)
    @Override
    public Populatie bepaalInUitPopulatie(final Persoonslijst persoon, final Expressie populatiebeperking,
                                          final Leveringsautorisatie leveringsautorisatie) {
        final Populatie resultaat;
        final Boolean huidigExpressieResultaat =
                getExpressieResultaat(persoon, populatiebeperking, leveringsautorisatie);

        final boolean isNieuwPersoon = persoon.getAdministratieveHandelingen().size() == 1;
        if (isNieuwPersoon) {
            resultaat = bepaalPopulatieVoorNieuwPersoon(huidigExpressieResultaat);
        } else {
            final Boolean vorigExpressieResultaat =
                    getExpressieResultaat(persoon.beeldVan().vorigeHandeling(), populatiebeperking, leveringsautorisatie);
            resultaat = bepaalPopulatieVoorBestaandPersoon(huidigExpressieResultaat, vorigExpressieResultaat);
        }

        return resultaat;
    }

    private Boolean getExpressieResultaat(final Persoonslijst persoon, final Expressie populatiebeperking, final Leveringsautorisatie la) {
        Boolean resultaat = expressieService.evalueer(populatiebeperking, persoon);
        if (resultaat == null) {
            LOGGER.info(POPULATIEBEPERKING_EVALUEERT_NAAR_WAARDE_NULL_ONBEKEND, la.getId(),
                    persoon.getMetaObject().getObjectsleutel());
            resultaat = false;
        }
        return resultaat;
    }

    /**
     * Bepaalt voor een nieuw persoon (zonder vorige afgeleidAdministratief) de populatie.
     * @param huidigExpressieResultaat Boolean die bepaalt of de persoon nu binnen de expressie valt.
     * @return De populatie.
     */
    @Bedrijfsregel(Regel.R1348)
    @Bedrijfsregel(Regel.R1333)
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
     * @param huidigExpressieResultaat Boolean die bepaalt of de persoon nu binnen de expressie valt.
     * @param vorigExpressieResultaat Boolean die bepaalt of de persoon vorige keer binnen de expressie viel.
     * @return De populatie.
     */
    @Bedrijfsregel(Regel.R1348)
    @Bedrijfsregel(Regel.R1333)
    private Populatie bepaalPopulatieVoorBestaandPersoon(final boolean huidigExpressieResultaat,
                                                         final boolean vorigExpressieResultaat) {
        final Populatie resultaat;
        if (huidigExpressieResultaat && vorigExpressieResultaat) {
            resultaat = Populatie.BINNEN;
        } else if (!vorigExpressieResultaat && huidigExpressieResultaat) {
            resultaat = Populatie.BETREEDT;
        } else if (vorigExpressieResultaat) {
            resultaat = Populatie.VERLAAT;
        } else {
            resultaat = Populatie.BUITEN;
        }
        return resultaat;
    }

}
