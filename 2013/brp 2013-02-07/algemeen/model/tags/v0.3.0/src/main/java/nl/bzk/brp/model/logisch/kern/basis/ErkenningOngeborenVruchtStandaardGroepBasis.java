/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.basis.Groep;


/**
 *
 *
 */
public interface ErkenningOngeborenVruchtStandaardGroepBasis extends Groep {

    /**
     * Retourneert Datum erkenning ongeboren vrucht van Standaard.
     *
     * @return Datum erkenning ongeboren vrucht.
     */
    Datum getDatumErkenningOngeborenVrucht();

}
