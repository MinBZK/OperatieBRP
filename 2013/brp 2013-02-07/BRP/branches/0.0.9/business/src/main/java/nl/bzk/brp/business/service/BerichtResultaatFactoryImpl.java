/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import org.springframework.stereotype.Component;

/**
 * Factory class die {@link nl.bzk.brp.business.dto.BerichtResultaat} instanties creeert op basis van een opgegeven
 * bericht. de bericht resultaat instanties zijn namelijk bericht specifiek.
 */
@Component
public class BerichtResultaatFactoryImpl implements BerichtResultaatFactory {

    @Override
    public BerichtResultaat creeerBerichtResultaat(final BRPBericht bericht) {
        BerichtResultaat resultaat;

        if (bericht instanceof BijhoudingsBericht) {
            resultaat = new BerichtResultaat(null);
        } else if (bericht instanceof OpvragenPersoonBericht) {
            resultaat = new OpvragenPersoonResultaat(null);
        } else {
            throw new IllegalArgumentException("Onbekend bericht type");
        }
        return resultaat;
    }

}
