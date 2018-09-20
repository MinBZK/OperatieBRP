/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.service.impl;

import javax.inject.Inject;

import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContextImpl;
import nl.bzk.brp.levering.mutatielevering.service.AdministratieveHandelingVerwerkerService;
import nl.bzk.brp.levering.mutatielevering.stappen.AdministratieveHandelingStappenVerwerker;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.validatie.Melding;
import org.springframework.stereotype.Service;

/**
 * Implementatie klasse van de AdministratieveHandendelingVerwerkerService. Verzorgt de verwerking van mutaties/
 * administratieve handelingen.
 */
@Service
public class AdministratieveHandelingVerwerkerServiceImpl implements AdministratieveHandelingVerwerkerService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private AdministratieveHandelingStappenVerwerker administratieveHandelingStappenVerwerker;

    @Override
    public final void verwerkAdministratieveHandeling(final Long administratieveHandelingId) {

        final AdministratieveHandelingMutatie administratieveHandelingMutatie =
                new AdministratieveHandelingMutatie(administratieveHandelingId);
        Thread.currentThread().setName("Mutatielevering-AH-" + administratieveHandelingMutatie.getAdministratieveHandelingId());
        MDC.put(MDCVeld.MDC_ADMINISTRATIEVE_HANDELING, String.valueOf(administratieveHandelingId));
        LOGGER.info("Start verwerking van administratieve handeling {}", administratieveHandelingId);
        final AdministratieveHandelingVerwerkingResultaat stappenResultaat =
                verwerkStappen(administratieveHandelingMutatie);

        LOGGER.info("Verwerking van mutatie naar aanleiding van administratieve handeling ({}) resultaat succesvol: {}",
                administratieveHandelingId, stappenResultaat.isSuccesvol());
        if (!stappenResultaat.isSuccesvol()) {
            LOGGER.warn("Verwerking mislukt, zie onderstaande meldingen.");
            for (final Melding melding : stappenResultaat.getMeldingen()) {
                LOGGER.warn("Soort melding: {}, meldingtekst {}, regel {}.", melding.getSoort(),
                        melding.getMeldingTekst(), melding.getRegel());
            }
        }

        MDC.remove(MDCVeld.MDC_ADMINISTRATIEVE_HANDELING);
    }

    /**
     * Start de stappenverwerking voor een administratieve handeling mutatie en geef het resultaat terug.
     *
     * @param administratieveHandelingMutatie
     *         De administratieve handeling mutatie.
     * @return het stappen resultaat
     */
    private AdministratieveHandelingVerwerkingResultaat verwerkStappen(
            final AdministratieveHandelingMutatie administratieveHandelingMutatie)
    {
        return administratieveHandelingStappenVerwerker.verwerk(
                administratieveHandelingMutatie, new AdministratieveHandelingVerwerkingContextImpl());
    }


}
