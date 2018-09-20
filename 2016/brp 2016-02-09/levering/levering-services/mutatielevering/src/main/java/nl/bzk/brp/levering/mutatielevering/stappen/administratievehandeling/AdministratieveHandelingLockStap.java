/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import javax.inject.Inject;

import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.AdministratieveHandelingVergrendelRepository;
import nl.bzk.brp.levering.mutatielevering.stappen.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.validatie.Melding;

/**
 * In deze stap wordt de administratieve handeling gelockt. Als dit niet lukt wordt gestopt met verwerking.
 */
public class AdministratieveHandelingLockStap extends AbstractAdministratieveHandelingVerwerkingStap {

    @Inject
    private AdministratieveHandelingVergrendelRepository administratieveHandelingLockRepository;

    @Override
    public final boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp,
                               final AdministratieveHandelingVerwerkingContext context,
                               final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        if (isGelockt(onderwerp)) {
            return DOORGAAN;
        }
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001,
                "Administratieve handeling kon niet worden vergrendeld of is reeds verwerkt."));
        return STOPPEN;
    }

    /**
     * Controleert voor de administratieve handeling op het onderwerp of deze gelockt kan worden.
     *
     * @param onderwerp Het onderwerp met daarin het administratieve handeling id.
     * @return true als de handeling gelockt kan worden, anders false.
     */
    private boolean isGelockt(final AdministratieveHandelingMutatie onderwerp) {
        return administratieveHandelingLockRepository
                .vergrendelAlsNogNietIsVerwerkt(onderwerp.getAdministratieveHandelingId());
    }
}
