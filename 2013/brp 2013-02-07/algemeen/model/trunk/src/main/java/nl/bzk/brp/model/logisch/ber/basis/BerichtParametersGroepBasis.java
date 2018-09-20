/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.basis.Groep;


/**
 *
 *
 */
public interface BerichtParametersGroepBasis extends Groep {

    /**
     * Retourneert Verwerkingswijze van Parameters.
     *
     * @return Verwerkingswijze.
     */
    Verwerkingswijze getVerwerkingswijze();

    /**
     * Retourneert Peilmoment Materieel van Parameters.
     *
     * @return Peilmoment Materieel.
     */
    Datum getPeilmomentMaterieel();

    /**
     * Retourneert Peilmoment Formeel van Parameters.
     *
     * @return Peilmoment Formeel.
     */
    DatumTijd getPeilmomentFormeel();

    /**
     * Retourneert Aanschouwer van Parameters.
     *
     * @return Aanschouwer.
     */
    Burgerservicenummer getAanschouwer();

}
