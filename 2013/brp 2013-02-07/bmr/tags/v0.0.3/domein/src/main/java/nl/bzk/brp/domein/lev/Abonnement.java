/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.lev;

import nl.bzk.brp.domein.ber.SoortBericht;
import nl.bzk.brp.domein.lev.basis.BasisAbonnement;


public interface Abonnement extends BasisAbonnement {

    /**
     * Voegt een {@link SoortBericht} toe aan het abonnement, waardoor de partij behorend bij dit abonnement gerechtigd
     * is dat soort berichten te versturen.
     *
     * @param soortBericht het soort bericht waarvoor dit abonnement geldt.
     */
    public void voegSoortBerichtToe(final SoortBericht soortBericht);
}
