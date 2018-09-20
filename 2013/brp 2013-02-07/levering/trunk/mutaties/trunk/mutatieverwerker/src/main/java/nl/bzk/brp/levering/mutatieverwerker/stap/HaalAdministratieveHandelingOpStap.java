/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.stap;

import javax.inject.Inject;

import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.levering.mutatieverwerker.excepties.DataNietAanwezigExceptie;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.service.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Deze stap haalt een Administratieve Handeling op ahv het id en zet hem op de context.
 */
public class HaalAdministratieveHandelingOpStap extends AbstractAdministratieveHandelingVerwerkingStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAdministratieveHandelingStap.class);

    @Inject
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @Override
    public boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp, final AdministratieveHandelingVerwerkingContext context,
                               final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        try {
            final AdministratieveHandelingModel administratieveHandelingModel =
                    haalAdministratieveHandeling(onderwerp.getAdministratieveHandelingId());

            zetAdministratieveHandelingOpContext(context, administratieveHandelingModel);

            return DOORGAAN;
        } catch (Exception exceptie) {
            LOGGER.error("Het ophalen van de administratieve handeling met id "
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
     * Haalt de administratieve handeling op uit de database.
     *
     * @param administratieveHandelingId Het id van de administratieve handeling.
     * @return De administratieve handeling.
     */
    private AdministratieveHandelingModel haalAdministratieveHandeling(final Long administratieveHandelingId) {
        final AdministratieveHandelingModel administratieveHandelingModel =
                administratieveHandelingRepository.haalAdministratieveHandeling(administratieveHandelingId);
        if (administratieveHandelingModel == null) {
            throw new DataNietAanwezigExceptie("Administratieve Handeling met id "
                    + administratieveHandelingId + " niet gevonden.");
        }
        return administratieveHandelingModel;
    }

    /**
     * Zet de administratieve handeling op de stappen context.
     *
     * @param context                       De stappen context.
     * @param administratieveHandelingModel De administrative handeling.
     */
    private void zetAdministratieveHandelingOpContext(final AdministratieveHandelingVerwerkingContext context,
                                                      final AdministratieveHandelingModel administratieveHandelingModel)
    {
        final AdministratieveHandelingVerwerkingContext administratieveHandelingVerwerkingContext =
                (AdministratieveHandelingVerwerkingContext) context;
        administratieveHandelingVerwerkingContext.setHuidigeAdministratieveHandeling(administratieveHandelingModel);
    }

}
