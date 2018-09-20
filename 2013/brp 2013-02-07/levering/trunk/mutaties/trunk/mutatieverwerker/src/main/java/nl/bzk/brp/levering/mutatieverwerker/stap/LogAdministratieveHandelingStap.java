/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.stap;

import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.service.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Deze stap logt een aantal gegevens van de Administratieve Handeling.
 */
public class LogAdministratieveHandelingStap extends AbstractAdministratieveHandelingVerwerkingStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAdministratieveHandelingStap.class);

    @Override
    public boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp, final AdministratieveHandelingVerwerkingContext context,
                               final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        final AdministratieveHandelingModel administratieveHandelingModel =
                ((AdministratieveHandelingVerwerkingContext) context).getHuidigeAdministratieveHandeling();
        LOGGER.debug("Administratieve handeling met id {} opgehaald. Soort handeling: {} Partij: {}",
                new Object[]{administratieveHandelingModel.getID(),
                        administratieveHandelingModel.getSoort().toString(),
                        administratieveHandelingModel.getPartij().getNaam().getWaarde()});
        return DOORGAAN;
    }

    @Override
    public void voerNabewerkingStapUit(final AdministratieveHandelingMutatie onderwerp, final AdministratieveHandelingVerwerkingContext context,
                                       final AdministratieveHandelingVerwerkingResultaat resultaat)
    {

    }

}
