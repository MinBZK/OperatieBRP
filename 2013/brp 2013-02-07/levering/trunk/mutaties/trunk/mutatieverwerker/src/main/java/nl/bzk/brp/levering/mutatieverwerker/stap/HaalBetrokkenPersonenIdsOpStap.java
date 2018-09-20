/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.stap;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.levering.mutatieverwerker.excepties.DataNietAanwezigExceptie;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.repository.AdministratieveHandelingVerwerkerRepository;
import nl.bzk.brp.levering.mutatieverwerker.service.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingResultaat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Deze stap haalt de betrokken persoon id's voor een administratieve handeling op en zet deze op de context.
 */
public class HaalBetrokkenPersonenIdsOpStap extends AbstractAdministratieveHandelingVerwerkingStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAdministratieveHandelingStap.class);

    @Inject
    private AdministratieveHandelingVerwerkerRepository administratieveHandelingVerwerkerRepository;

    @Override
    public boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp, final AdministratieveHandelingVerwerkingContext context,
                               final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        try {
            final List<Integer> betrokkenPersonenIds =
                    haalBetrokkenPersonenIdsOp(onderwerp.getAdministratieveHandelingId());

            zetBetrokkenPersonenIdsOpContext(context, betrokkenPersonenIds);

            return DOORGAAN;
        } catch (Exception exceptie) {
            LOGGER.error("Het ophalen van de betrokken personen voor de administratieve handeling met id "
                    + onderwerp.getAdministratieveHandelingId() + " is mislukt.", exceptie);
            resultaat.setVerwerkingIsSuccesvol(false);
            return STOPPEN;
        }
    }

    @Override
    public void voerNabewerkingStapUit(final AdministratieveHandelingMutatie onderwerp, final AdministratieveHandelingVerwerkingContext context,
                                       final AdministratieveHandelingVerwerkingResultaat resultaat)
    {

    }

    /**
     * Haalt de betrokken persoon id's op voor een administratieve handeling.
     * @param administratieveHandelingId Het id van de administratieve handeling.
     * @return De lijst van betrokken persoon id's.
     */
    private List<Integer> haalBetrokkenPersonenIdsOp(final Long administratieveHandelingId) {
        final List<Integer> betrokkenPersonenIds =
                administratieveHandelingVerwerkerRepository.haalAdministratieveHandelingPersoonIds(
                        administratieveHandelingId);

        if (betrokkenPersonenIds == null || betrokkenPersonenIds.size() == 0) {
            throw new DataNietAanwezigExceptie("Geen betrokken personen gevonden voor Administratieve Handeling met id "
                    + administratieveHandelingId + ".");
        }

        return betrokkenPersonenIds;
    }

    /**
     * Zet de id's van de betrokken personen op de stappen context.
     * @param context De stappen context.
     * @param betrokkenPersonenIds De betrokken persoon id's.
     */
    private void zetBetrokkenPersonenIdsOpContext(final AdministratieveHandelingVerwerkingContext context,
                                                 final List<Integer> betrokkenPersonenIds)
    {
        (context).setBetrokkenPersonenIds(betrokkenPersonenIds);
    }

}
