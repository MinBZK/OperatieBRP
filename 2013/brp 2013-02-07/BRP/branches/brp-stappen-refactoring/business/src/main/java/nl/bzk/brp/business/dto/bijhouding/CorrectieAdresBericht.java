/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bijhouding;


import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;

/**
 * Klasse voor correctie adres berichten.
 */
public class CorrectieAdresBericht extends AbstractBijhoudingsBericht {

    public CorrectieAdresBericht() {
        super(SoortBericht.M_I_G_CORRIGEER_ADRES_B);
    }
}
