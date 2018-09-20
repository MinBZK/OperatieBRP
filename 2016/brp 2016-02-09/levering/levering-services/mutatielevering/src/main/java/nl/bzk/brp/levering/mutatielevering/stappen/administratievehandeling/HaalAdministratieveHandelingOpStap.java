/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import javax.inject.Inject;

import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatielevering.excepties.DataNietAanwezigExceptie;
import nl.bzk.brp.levering.mutatielevering.stappen.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

import org.perf4j.aop.Profiled;


/**
 * Deze stap haalt een Administratieve Handeling op ahv het id en zet hem op de context.
 */
public class HaalAdministratieveHandelingOpStap extends AbstractAdministratieveHandelingVerwerkingStap {

    private static final Logger                LOGGER = LoggerFactory.getLogger();

    @Inject
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @Override
    @Profiled(tag = "HaalAdministratieveHandelingOpStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp,
            final AdministratieveHandelingVerwerkingContext context,
            final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        final AdministratieveHandelingModel administratieveHandelingModel =
            haalAdministratieveHandeling(onderwerp.getAdministratieveHandelingId());

        zetAdministratieveHandelingOpContext(context, administratieveHandelingModel);

        LOGGER.debug("Administratieve handeling opgehaald.");

        return DOORGAAN;
    }

    /**
     * Haalt de administratieve handeling op uit de database.
     *
     * @param administratieveHandelingId Het id van de administratieve handeling.
     * @return De administratieve handeling.
     */
    private AdministratieveHandelingModel haalAdministratieveHandeling(final Long administratieveHandelingId) {
        final AdministratieveHandelingModel administratieveHandelingModel =
                administratieveHandelingRepository.haalAdministratieveHandeling(administratieveHandelingId);
        if (administratieveHandelingModel == null) {
            throw new DataNietAanwezigExceptie("Administratieve Handeling met id " + administratieveHandelingId
                + " niet gevonden.");
        }
        return administratieveHandelingModel;
    }

    /**
     * Zet de administratieve handeling op de stappen context.
     *
     * @param context De stappen context.
     * @param administratieveHandelingModel De administrative handeling.
     */
    private void zetAdministratieveHandelingOpContext(final AdministratieveHandelingVerwerkingContext context,
            final AdministratieveHandelingModel administratieveHandelingModel)
    {
        context.setHuidigeAdministratieveHandeling(administratieveHandelingModel);
    }

}
