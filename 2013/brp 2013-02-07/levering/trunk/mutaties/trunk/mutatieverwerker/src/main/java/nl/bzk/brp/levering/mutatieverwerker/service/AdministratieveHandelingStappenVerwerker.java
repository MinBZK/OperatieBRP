/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.service;

import nl.bzk.brp.business.stappen.verwerker.AbstractVerwerker;
import nl.bzk.brp.business.stappen.verwerker.StappenVerwerker;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dit is de klasse die de stappenverwerking voor de administratieve handelingen verzorgd.
 */
public class AdministratieveHandelingStappenVerwerker extends
        AbstractVerwerker<AdministratieveHandelingMutatie, AdministratieveHandelingVerwerkingContext,
                AdministratieveHandelingVerwerkingResultaat, AdministratieveHandelingVerwerkingStap>
        implements StappenVerwerker<AdministratieveHandelingMutatie, AdministratieveHandelingVerwerkingContext,
        AdministratieveHandelingVerwerkingResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratieveHandelingStappenVerwerker.class);

    @Override
    protected void valideer(final AdministratieveHandelingMutatie onderwerp,
                            final AdministratieveHandelingVerwerkingResultaat resultaat)
    {

    }

    @Override
    protected void voerNaVerwerkingStappenUit(final AdministratieveHandelingMutatie onderwerp,
                                              final AdministratieveHandelingVerwerkingContext context,
                                              final AdministratieveHandelingVerwerkingResultaat resultaat,
                                              final int laatsteStapIndex)
    {
        LOGGER.debug("Stappen klaar voor administratieve handeling met id {}.",
                ((AdministratieveHandelingMutatie) onderwerp).getAdministratieveHandelingId());
    }

    @Override
    protected AdministratieveHandelingVerwerkingResultaat creeerResultaat(
            final AdministratieveHandelingMutatie onderwerp,
            final AdministratieveHandelingVerwerkingContext context)
    {
        return new AdministratieveHandelingVerwerkingResultaat(null);
    }
}
