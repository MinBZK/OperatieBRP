/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.kern;

import nl.bzk.brp.domein.autaut.Autorisatiebesluit;
import nl.bzk.brp.domein.autaut.Doelbinding;
import nl.bzk.brp.domein.kern.basis.BasisPartij;


public interface Partij extends BasisPartij {

    /**
     * Creëer een nieuwe doelbinding voor deze partij en voeg deze toe aan de verzameling doelbindingen.
     *
     * @param autorisatieBesluit het autorisatiebesluit dat aan deze partij wordt gebonden.
     * @return de doelbinding die gecreëerd wordt.
     */
    public Doelbinding createDoelbinding(final Autorisatiebesluit autorisatieBesluit);
}
