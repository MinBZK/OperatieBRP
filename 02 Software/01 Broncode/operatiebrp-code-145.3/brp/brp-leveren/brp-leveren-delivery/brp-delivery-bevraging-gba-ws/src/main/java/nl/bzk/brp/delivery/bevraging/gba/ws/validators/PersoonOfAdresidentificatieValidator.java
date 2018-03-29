/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import java.util.Optional;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.AdhocWebserviceVraagBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.Antwoorden;
import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;

/**
 * Valideert of de zoekcriteria persoonsidentificerende gegevens bevat.
 */
public class PersoonOfAdresidentificatieValidator implements Validator<AdhocWebserviceVraagBericht> {
    @Override
    public Optional<Antwoord> apply(final AdhocWebserviceVraagBericht vraag) {
        return vraag.isPersoonIdentificatie() || vraag.isAdresIdentificatie()
                ? Optional.empty()
                : Optional.of(Antwoorden.foutief(AntwoordBerichtResultaat.TECHNISCHE_FOUT_020));
    }
}
