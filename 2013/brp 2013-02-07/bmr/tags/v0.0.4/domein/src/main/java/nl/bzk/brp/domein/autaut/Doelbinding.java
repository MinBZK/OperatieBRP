/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.autaut;

import nl.bzk.brp.domein.autaut.basis.BasisDoelbinding;
import nl.bzk.brp.domein.lev.Abonnement;
import nl.bzk.brp.domein.lev.SoortAbonnement;


public interface Doelbinding extends BasisDoelbinding {

    /**
     * Creëer een abonnement bij deze doelbinding.
     *
     * @param soortAbonnement het soort abonnement dat gecreëerd wordt.
     * @return het abonnement dat gecreëerd is.
     */
    public Abonnement createAbonnement(final SoortAbonnement soortAbonnement);
}
