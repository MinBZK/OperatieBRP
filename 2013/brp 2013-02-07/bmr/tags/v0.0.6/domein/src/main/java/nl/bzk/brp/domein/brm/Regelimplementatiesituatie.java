/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.brm;

import nl.bzk.brp.domein.brm.basis.BasisRegelimplementatiesituatie;


public interface Regelimplementatiesituatie extends BasisRegelimplementatiesituatie {

    /**
     * @param gedrag het gedrag waarmee dit gedrag moet worden vergeleken
     * @return true als dit gedrag specifieker is dan het gegeven gedrag
     */
    boolean isSpecifiekerDan(Regelimplementatiesituatie regelimplementatiesituatie);
}
