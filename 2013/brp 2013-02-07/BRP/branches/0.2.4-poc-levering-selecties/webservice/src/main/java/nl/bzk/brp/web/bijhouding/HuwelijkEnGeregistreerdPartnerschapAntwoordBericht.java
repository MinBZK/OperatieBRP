/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;


/**
 * Het antwoord bericht voor registratie huwelijk en geregistreerd partnerschap.
 */
public class HuwelijkEnGeregistreerdPartnerschapAntwoordBericht extends AbstractBijhoudingAntwoordBericht {

    @Override
    public Soortbericht getSoortBericht() {
        return Soortbericht.HUWELIJKPARTNERSCHAP_REGISTRATIEHUWELIJK_BIJHOUDING_ANTWOORD;
    }
}
