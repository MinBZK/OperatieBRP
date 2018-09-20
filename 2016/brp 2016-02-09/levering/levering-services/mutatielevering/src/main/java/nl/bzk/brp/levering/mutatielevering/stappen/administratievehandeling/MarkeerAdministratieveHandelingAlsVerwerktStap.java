/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import javax.inject.Inject;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.AdministratieveHandelingVerwerkerRepository;
import nl.bzk.brp.levering.mutatielevering.stappen.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import org.perf4j.aop.Profiled;


/**
 * Deze stap zorgt er voor dat de administratieve handeling als verwerkt wordt aangemerkt in de database.
 *
 * @brp.bedrijfsregel R1988
 */
@Regels(Regel.R1988)
public class MarkeerAdministratieveHandelingAlsVerwerktStap extends AbstractAdministratieveHandelingVerwerkingStap {

    private static final Logger                         LOGGER = LoggerFactory.getLogger();

    @Inject
    private AdministratieveHandelingVerwerkerRepository administratieveHandelingVerwerkerRepository;

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.brp.business.stappen.Stap#voerStapUit(nl.bzk.brp.model.basis.ObjectType,
     * nl.bzk.brp.business.stappen.StappenContext, nl.bzk.brp.business.stappen.StappenResultaat)
     */
    @Override
    @Profiled(tag = "MarkeerAdministratieveHandelingAlsVerwerktStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp,
            final AdministratieveHandelingVerwerkingContext context,
            final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        administratieveHandelingVerwerkerRepository.markeerAdministratieveHandelingAlsVerwerkt(onderwerp
                .getAdministratieveHandelingId());
        LOGGER.debug("Administratieve handeling ");
        return DOORGAAN;
    }

}
