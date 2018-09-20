/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.AdministratieveHandelingVerwerkerRepository;
import nl.bzk.brp.levering.mutatielevering.excepties.DataNietAanwezigExceptie;
import nl.bzk.brp.levering.mutatielevering.stappen.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

import org.perf4j.aop.Profiled;


/**
 * Deze stap haalt de bijgehouden persoon id's voor een administratieve handeling op en zet deze op de context.
 */
public class HaalBijgehoudenPersonenIdsOpStap extends AbstractAdministratieveHandelingVerwerkingStap {

    private static final Logger                         LOGGER = LoggerFactory.getLogger();

    @Inject
    private AdministratieveHandelingVerwerkerRepository administratieveHandelingVerwerkerRepository;

    @Override
    @Profiled(tag = "HaalBijgehoudenPersonenIdsOpStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp,
            final AdministratieveHandelingVerwerkingContext context,
            final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        final List<Integer> bijgehoudenPersoonIds =
            haalBijgehoudenPersoonIdsOp(onderwerp.getAdministratieveHandelingId());

        context.setBijgehoudenPersoonIds(bijgehoudenPersoonIds);
        LOGGER.info("Administratieve handeling mutatie van {} personen wordt verwerkt", bijgehoudenPersoonIds.size());
        return DOORGAAN;
    }

    /**
     * Haalt de id's op voor de bijgehouden personen van een administratieve handeling.
     *
     * @param administratieveHandelingId Het id van de administratieve handeling.
     * @return De lijst van bijgehouden persoon id's.
     */
    private List<Integer> haalBijgehoudenPersoonIdsOp(final Long administratieveHandelingId) {
        final List<Integer> bijgehoudenPersoonIds =
                administratieveHandelingVerwerkerRepository
                    .haalAdministratieveHandelingPersoonIds(administratieveHandelingId);

        if (bijgehoudenPersoonIds == null || bijgehoudenPersoonIds.size() == 0) {
            throw new DataNietAanwezigExceptie(
                    "Geen bijgehouden personen gevonden voor Administratieve Handeling met id "
                        + administratieveHandelingId + ".");
        }

        return bijgehoudenPersoonIds;
    }

}
