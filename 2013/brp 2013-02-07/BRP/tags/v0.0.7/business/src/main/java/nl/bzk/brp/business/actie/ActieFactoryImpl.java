/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import javax.inject.Inject;

import nl.bzk.brp.model.logisch.BRPActie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Standaard implementatie van de {@link ActieFactory}. Deze implementatie
 * geeft de correcte {@link ActieUitvoerder} instanties voor een
 * opgegeven {@link BRPActie}.
 */
@Component
public class ActieFactoryImpl implements ActieFactory {

    private static final Logger  LOGGER = LoggerFactory.getLogger(ActieFactoryImpl.class);

    @Inject
    private VerhuizingUitvoerder verhuizingUitvoerder;
    @Inject
    private InschrijvingGeboorteUitvoerder inschrijvingGeboorteUitvoerder;

    @Override
    public ActieUitvoerder getActieUitvoerder(final BRPActie actie) {
        ActieUitvoerder actieUitvoerder = null;
        switch (actie.getSoort()) {
            case VERHUIZING:
                actieUitvoerder = verhuizingUitvoerder;
                break;
            case AANGIFTE_GEBOORTE:
                actieUitvoerder = inschrijvingGeboorteUitvoerder;
                break;
            default:
                String msg =
                    String.format("Geen correcte actieuitvoerder gevonden voor de opgegeven actie soort: %s",
                            actie.getSoort());
                LOGGER.error(msg);
                throw new IllegalArgumentException(msg);
        }
        return actieUitvoerder;
    }
}
