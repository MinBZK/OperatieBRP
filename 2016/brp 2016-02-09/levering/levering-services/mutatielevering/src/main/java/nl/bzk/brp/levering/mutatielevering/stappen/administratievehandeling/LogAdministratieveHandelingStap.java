/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatielevering.stappen.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;


/**
 * Deze stap logt een aantal gegevens van de Administratieve Handeling.
 */
public class LogAdministratieveHandelingStap extends AbstractAdministratieveHandelingVerwerkingStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp,
            final AdministratieveHandelingVerwerkingContext context,
            final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        final AdministratieveHandelingModel administratieveHandelingModel =
            context.getHuidigeAdministratieveHandeling();
        LOGGER.debug("Administratieve handeling met id {} opgehaald. Soort handeling: {} Partij: {}",
                administratieveHandelingModel.getID(), administratieveHandelingModel.getSoort().toString(),
                administratieveHandelingModel.getPartij().getWaarde().getNaam().getWaarde());
        return DOORGAAN;
    }

}
