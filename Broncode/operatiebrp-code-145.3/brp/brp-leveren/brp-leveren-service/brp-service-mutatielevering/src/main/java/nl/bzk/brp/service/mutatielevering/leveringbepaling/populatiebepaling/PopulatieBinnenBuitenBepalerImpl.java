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
 * Implementatie die de bepaling doet op basis van de volgende regel: - Valt een persoon nu binnen de populatie.
 */
@Component("PopulatieBinnenBuitenBepaler")
final class PopulatieBinnenBuitenBepalerImpl implements PopulatieBepaler {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String POPULATIEBEPERKING_EVALUEERT_NAAR_WAARDE_NULL_ONBEKEND =
            "Populatiebeperking evalueert naar waarde NULL (onbekend) voor leveringsautorisatie: '{}' en persoon met id: {}";

    @Inject
    private ExpressieService expressieService;

    private PopulatieBinnenBuitenBepalerImpl() {

    }

    @Bedrijfsregel(Regel.R1559)
    @Override
    public Populatie bepaalInUitPopulatie(final Persoonslijst persoonslijst, final Expressie populatiebeperking,
                                          final Leveringsautorisatie leveringsautorisatie) {
        Boolean expressieResultaat = expressieService.evalueer(populatiebeperking, persoonslijst);
        if (expressieResultaat == null) {
            LOGGER.info(POPULATIEBEPERKING_EVALUEERT_NAAR_WAARDE_NULL_ONBEKEND, leveringsautorisatie.getId(),
                    persoonslijst.getId());

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
